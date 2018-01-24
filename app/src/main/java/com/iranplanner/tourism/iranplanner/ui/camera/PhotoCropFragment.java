package com.iranplanner.tourism.iranplanner.ui.camera;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.OnCutImageListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import tools.Util;

public class PhotoCropFragment extends Fragment  implements UploadPhotoContract.View{
    private static String photoPath;
    public Bitmap imageToCrop;
    PhotoCropView photoCropView;
    private BitmapDrawable drawable;
    private PhotoEditActivityDelegate photoEditActivityDelegate;
    OnCutImageListener onCutImageListener;
    @Inject
    UploadPhotoPresenter uploadPhotoPresenter;
//    DaggerAtractionDetailComponent.Builder builder;
    DaggerUploadPhotoComponent.Builder builder;
    public PhotoCropFragment(OnCutImageListener onCutImageListener) {
        this.onCutImageListener=onCutImageListener;
    }

    public PhotoCropFragment() {
    }

    public static Bitmap loadBitmap(String path, Uri uri, float maxWidth, float maxHeight, boolean useMaxScale) {
        photoPath=path;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        FileDescriptor fileDescriptor = null;
        ParcelFileDescriptor parcelFD = null;

        if (path == null && uri != null && uri.getScheme() != null) {
            String imageFilePath = null;
            if (uri.getScheme().contains("file")) {
                path = uri.getPath();
            } else {
                try {
                    path = Util.getPath(uri);
                } catch (Throwable e) {
                    Log.e("tmessages", e.getMessage());
                }
            }
        }

        if (path != null) {
            BitmapFactory.decodeFile(path, bmOptions);
        } else if (uri != null) {
            boolean error = false;
            try {
                parcelFD = App.getInstance().getContentResolver().openFileDescriptor(uri, "r");
                fileDescriptor = parcelFD.getFileDescriptor();
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, bmOptions);
            } catch (Throwable e) {
                Log.e("tmessages", e.getMessage());
                return null;
            }
        }
        float photoW = bmOptions.outWidth;
        float photoH = bmOptions.outHeight;
        float scaleFactor = useMaxScale ? Math.max(photoW / maxWidth, photoH / maxHeight) : Math.min(photoW / maxWidth, photoH / maxHeight);
        if (scaleFactor < 1) {
            scaleFactor = 1;
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = (int) scaleFactor;

        String exifPath = null;
        if (path != null) {
            exifPath = path;
        } else if (uri != null) {
            exifPath = Util.getPath(uri);
        }

        Matrix matrix = null;

        if (exifPath != null) {
            ExifInterface exif;
            try {
                exif = new ExifInterface(exifPath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                matrix = new Matrix();
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;
                }
            } catch (Throwable e) {
                Log.e("tmessages", e.getMessage());
            }
        }

        Bitmap b = null;
        if (path != null) {
            try {
                b = BitmapFactory.decodeFile(path, bmOptions);
                if (b != null) {
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                }
            } catch (Throwable e) {
                Log.e("tmessages", e.getMessage());
//                ImageLoader.getInstance().clearMemory();
                try {
                    if (b == null) {
                        b = BitmapFactory.decodeFile(path, bmOptions);
                    }
                    if (b != null) {
                        b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    }
                } catch (Throwable e2) {
                    Log.e("tmessages", e2.getMessage());
                }
            }
        } else if (uri != null) {
            try {
                b = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, bmOptions);
                if (b != null) {
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                }
            } catch (Throwable e) {
                Log.e("tmessages", e.getMessage());
            } finally {
                try {
                    parcelFD.close();
                } catch (Throwable e) {
                    Log.e("tmessages", e.getMessage());
                }
            }
        }

        return b;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_crop, null);
        photoCropView = (PhotoCropView) rootView.findViewById(R.id.photoCropView);


        photoCropView.setImageToCrop(imageToCrop, drawable);

        rootView.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onCutImageListener!=null) {
                    sendImageToServer(photoCropView.getBitmap());
                    getActivity().onBackPressed();
                    onCutImageListener.onCropImage(photoCropView.getBitmap());

                }

            }
        });


        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });




        return rootView;


//        return rootView;
    }

    private void sendImageToServer(Bitmap bitmap){



        DaggerUploadPhotoComponent.builder().netComponent(((App) getContext().getApplicationContext()).getNetComponent())
                .uploadPhotoModule(new UploadPhotoModule(this))
                .build().inject(this);
        File f = null;
        try {
            f = Util.createImageFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", f.getName(), reqFile);

            String descriptionString = "hello, this is description speaking";
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);
            uploadPhotoPresenter.upload(description, body);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            e.printStackTrace();
        }

    }
    public void setPhotoEditActivityDelegate(PhotoEditActivityDelegate photoEditActivityDelegate) {
        this.photoEditActivityDelegate = photoEditActivityDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageToCrop = (Bitmap) getArguments().getParcelable("IMAGE_TO_CROP");
        photoPath =  getArguments().getString("path");
        if (imageToCrop == null) {
            String photoPath = getArguments().getString("photoPath");
            Uri photoUri = getArguments().getParcelable("photoUri");
            if (photoPath == null && photoUri == null) {
                return;
            }
            if (photoPath != null) {
                File f = new File(photoPath);
                if (!f.exists()) {
                    return;
                }
            }
            int size;
            if (Util.isTablet()) {
                size = Util.dp(520);
            } else {
                size = Math.max(Util.displaySize.x, Util.displaySize.y);
            }
            imageToCrop = loadBitmap(photoPath, photoUri, size, size, true);
            if (imageToCrop == null) {
                return;
            }
        }

        drawable = new BitmapDrawable(imageToCrop);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        drawable = null;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }



    @Override
    public void showProgress() {

    }


    @Override
    public void dismissProgress() {

    }



    public interface PhotoEditActivityDelegate {
        void didFinishEdit(Bitmap bitmap);


    }


    public void setOnCropListener() {

    }



}
