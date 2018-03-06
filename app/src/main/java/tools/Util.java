package tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import server.Config;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Hoda on 10/01/2017.
 */
public class Util {

    public static void overrideFont() {
        // for Override font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    public static File createImageFiles() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File file = new File(App.getInstance().getImagesPath(), imageFileName);

        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;

    }

    public static float density = 1;
    public static Point displaySize = new Point();
    private static Boolean isTablet = null;

    public static boolean isTablet() {
        if (isTablet == null) {
            isTablet = App.getInstance().getResources().getBoolean(R.bool.isTablet);
        }
        return isTablet;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @SuppressLint("NewApi")
    public static String getPath(final Uri uri) {
        try {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            if (isKitKat && DocumentsContract.isDocumentUri(App.getInstance(), uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(App.getInstance(), contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if (type.equals("image")) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    } else if (type.equals("video")) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                    } else if (type.equals("audio")) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(App.getInstance(), contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(App.getInstance(), uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } catch (Exception e) {
            Log.e("tmessages", e.getMessage());
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            Log.e("tmessages", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    static {
        density = App.getInstance().getResources().getDisplayMetrics().density;
        checkDisplaySize();
    }
    public static void checkDisplaySize() {
        try {
            WindowManager manager = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    if (Build.VERSION.SDK_INT < 13) {
                        displaySize.set(display.getWidth(), display.getHeight());
                    } else {
                        display.getSize(displaySize);
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    public static int dp(float value) {


        return (int) Math.ceil(density * value);
    }
    public static boolean isNetworkAvailable(Context context) {
//            ConnectivityManager connectivityManager
//                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public static AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("عدم دسترسی به اینترنت");
        builder.setMessage("برای استفاده از برنامه به اینترنت وصل شوید");

        builder.setPositiveButton("خب", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        return builder;
    }

    public static void setImageView(String url, Context context, ImageView imageView, ProgressBar loading) {


//            imgItineraryListMore.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //// TODO: 22/01/2017  get defeult picture
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }

    }

    //    else {
//            Glide.clear(imgItineraryListMore);
//            imgItineraryListMore.setImageDrawable(null);
//
//        }
//    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static boolean checkLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public static void saveDataINShareprefrence(Context context, String email, String lastName, String gender, String userId) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF_USER, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", email);
        editor.putString("fname", lastName);
        editor.putString("gender", gender);
        editor.putString("gender", gender);
        editor.putString("userId", userId);
//        editor.putString("userCityId", userCityId);
//        editor.putString("userCityName", userCityName);
//        editor.putString("userEmailStatus", userEmailStatus);

        editor.commit();
    }

   //--------------------

    public static final String PREFS_NAME = "IRAN_PLANNER_CONFIG";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME,0);
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = settings.edit(); //2
        editor.clear();
        editor.commit();
    }

    public static void saveInPreferences(String key, String value, boolean secure,Context context) {
        SharedPreferences settings =getSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = settings.edit(); //2
        editor.remove(key);
        editor.putString(key, secure ? value : value); //3
        editor.commit(); //4
    }

    public static String getFromPreferences(String key, String defaultValue, boolean secure,Context context) {
        SharedPreferences settings = getSharedPreferences(context);
        String value = settings.getString(key, defaultValue);
        return secure && value != null ? value : value;
    }

    //----------------------

    public static final String displayFirebaseRegId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        return regId;
    }

    ProgressDialog progressDialog;

    public static final ProgressDialog showProgressDialog(Context context, /*ProgressDialog progressDialog,*/String message, Activity activity) {

            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);  return progressDialog;




    }

    public static void dismissProgress(ProgressDialog progressDialog) {
        if (progressDialog.isShowing() == true || progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }

    public static final Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static final String getUseRIdFromShareprefrence(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF_USER, 0);
        String userId = pref.getString("userId", "");
        return userId;
    }

    public static final String getUserNameFromShareprefrence(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF_USER, 0);
        String userName = pref.getString("fname", "");
        return userName;
    }

    public static final String getEmailFromShareprefrence(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF_USER, 0);
        String email = pref.getString("email", "");
        return email;
    }

    public static final String getTokenFromSharedPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", "");
        return regId;
    }

    public static final String getAndroidIdFromSharedPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String andId = pref.getString("andId", "");
        return andId;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Typeface getFontAwesome(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
        return font;
    }

    public static void convertMinuteToHour(int totalMinute, TextView textView) {
        if (totalMinute >= 60) {
            int hours = (int) Math.floor(totalMinute / 60);
            totalMinute %= 60;
            if (totalMinute == 0) {
                textView.setText("طول بازدید: " + Util.persianNumbers(String.valueOf(hours)) + " ساعت ");
            } else {
                textView.setText("طول بازدید: " + Util.persianNumbers(String.valueOf(hours)) + " ساعت و " + Util.persianNumbers(String.valueOf(totalMinute)) + " دقیقه ");
            }
        } else {
            textView.setText("طول بازدید: " + Util.persianNumbers(String.valueOf(totalMinute)) + " دقیقه ");
        }
    }

    public static String convertToHexString(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static byte[] convertToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    public static String englishNumbers(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String persianNumbers(String str) {
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        String englishNumbers = "0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (englishNumbers.contains(String.valueOf(str.charAt(i)))) {
                builder.append(arabicChars[(int) (str.charAt(i)) - 48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static float dpToPx(Context context, int dpp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpp, r.getDisplayMetrics());
    }

    /**
     * Use this method with an activity context
     *
     * @param context
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static String getPriceInToman(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return persianNumbers(formatter.format(price));
    }

}
