package com.iranplanner.tourism.iranplanner.ui.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;

import com.iranplanner.tourism.iranplanner.BuildConfig;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import java.io.File;
import java.io.IOException;

import tools.Util;

/**
 * Created by h.vahidimehr on 24/01/2018.
 */

public class PhotoUtils {
    Context mContext;
    Activity activity;
    OnImageUriSelect onImageUriSelect;
    private static final int SELECT_FILE = 14;
    public  PhotoUtils(Activity activity,OnImageUriSelect onImageUriSelect) {
        this.mContext=activity;
        this.activity=activity;
        this.onImageUriSelect=onImageUriSelect;
    }

    public void selectImage() {
        final CharSequence[] items = {"از دوربین", "از گالری", "انصراف"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("انتخاب عکس");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("از دوربین")) {
                    dispatchTakePictureIntent();
                } else if (items[item].equals("از گالری")) {
                    if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        activity.startActivityForResult(
                                Intent.createChooser(intent, "انتخاب عکس"),
                                SELECT_FILE);
                    } else {
                        App.createPermissionDialog(activity, activity.getString(R.string.app_name), "permission");
                    }
                } else if (items[item].equals("انصراف")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    Intent CamIntent;
    private Uri mImageUri;
    private static final int REQUEST_CAMERA = 0;
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = null;

        try {
            photo = Util.createImageFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photo != null) {
            if (Build.VERSION.SDK_INT > 21) {
                CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", photo);
                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                if(onImageUriSelect!=null) {
                    onImageUriSelect.onSelectImage(mImageUri);
                }
                activity.startActivityForResult(CamIntent, REQUEST_CAMERA);
            } else {
                mImageUri = Uri.fromFile(photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                //start camera intent
                if(onImageUriSelect!=null) {
                    onImageUriSelect.onSelectImage(mImageUri);
                }
                activity.startActivityForResult(intent, REQUEST_CAMERA);
            }

        }

    }


    public interface OnImageUriSelect {
        void onSelectImage(Uri uri);
    }

    public Bitmap grabImage() {
        ContentResolver cr = activity.getContentResolver();
        Bitmap bitmap = null;
        int tryCount = 0;
        try {
            while ((bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri)) == null) {
                Thread.sleep(10);
                tryCount++;
                if (tryCount > 500) {
                    return null;
                }
            }

            //bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
            int orientation = 0;
            Matrix matrix = new Matrix();
            try {
                ExifInterface ei = new ExifInterface(mImageUri.getPath());
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
