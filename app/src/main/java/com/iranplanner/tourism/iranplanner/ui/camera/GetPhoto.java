package com.iranplanner.tourism.iranplanner.ui.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.iranplanner.tourism.iranplanner.di.model.App;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by h.vahidimehr on 31/12/2017.
 */

public class GetPhoto {
    private int FROMCAMERA = 4;
    private int FROMGALLERY = 1;
    private Intent CamIntent;
    private Uri mImageUri;
    Context mContext;
    Activity activity;

    public GetPhoto(Context context, Activity activity) {
        this.mContext = context;
        this.activity = activity;
    }


    public void getImageFromCamera() {


        File photo = null;
        try {

            photo = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photo != null) {
            if (Build.VERSION.SDK_INT > 21) {
//                try {
                CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                imgPath = photo.getAbsolutePath();
                mImageUri = FileProvider.getUriForFile(mContext, "dreamgo.corp.provider", photo);
                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                CamIntent.putExtra("getPath", imgPath);
                activity.startActivityForResult(CamIntent, FROMCAMERA);

            } else {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageUri = Uri.fromFile(photo);
                    imgPath = mImageUri.getPath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    activity.startActivityForResult(intent, FROMCAMERA);
                } catch (Exception e) {

                }

            }
        }
    }

    String imgPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File file = new File(App.getInstance().getImagesPath(), imageFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        imgPath = file.getAbsolutePath();
        return file;
    }

    public void getImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, FROMGALLERY);
    }

    public Bitmap grabImage(Bitmap bitmap, String path) {

        try {

            int orientation = 0;
            Matrix matrix = new Matrix();
            try {
                ExifInterface ei = new ExifInterface(path);

                int exif = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (exif) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        orientation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        orientation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        orientation = 270;
                        break;
                }
                matrix.preRotate(orientation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Rotate image bitmap to correct orientation.
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            double ratio = (double) width / (double) height;


//        if ((width > height) && (width > MAX_IMAGE_TO_CROP_PIXEL)) {
//            width = MAX_IMAGE_TO_CROP_PIXEL;
//            height = (int) ((double) MAX_IMAGE_TO_CROP_PIXEL / ratio);
//        } else if (height > MAX_IMAGE_TO_CROP_PIXEL) {
//            height = MAX_IMAGE_TO_CROP_PIXEL;
//            width = (int) ((double) MAX_IMAGE_TO_CROP_PIXEL * ratio);
//        }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
