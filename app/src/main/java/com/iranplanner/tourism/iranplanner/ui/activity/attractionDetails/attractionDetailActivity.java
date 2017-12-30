package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coinpany.core.android.widget.CTouchyWebView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.BuildConfig;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.OnCutImageListener;
import com.iranplanner.tourism.iranplanner.ui.activity.PhotoCropFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import entity.InterestResult;
import entity.ItineraryLodgingCity;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultWidget;
import entity.ResultWidgetFull;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import tools.Constants;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.graphics.BitmapFactory.decodeFile;

public class attractionDetailActivity extends StandardActivity implements OnMapReadyCallback, View.OnClickListener, AttractionDetailContract.View, AttractionListMoreContract.View
        , OnCutImageListener {
    private static final int REQUEST_CAMERA = 0;
    @Inject
    AttractionDetailPresenter attractionDetailPresenter;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    private GoogleMap mMap;
    ResulAttraction resulAttraction;
    Marker marker;
    SupportMapFragment mapFragment;
    Boolean showMore = true;
    String myData;
    boolean ratingHolderFlag = false;
    String rotateImage;
    RotateAnimation rotate;
    List<ResultWidget> resultWidget;
    ProgressDialog progressDialog;
    int BookmarkValue;
    int LikeValue;
    int VisitedValue;
    int WishValue;

    @BindView(R.id.contentFullDescription)
    CTouchyWebView contentFullDescription;
    @BindView(R.id.attractionPlace)
    TextView attractionPlace;
    @BindView(R.id.textTimeDuration)
    TextView textTimeDuration;
    @BindView(R.id.textEntranceFee)
    TextView textEntranceFee;
    @BindView(R.id.attractionType)
    TextView attractionType;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.imageTypeAttraction)
    ImageView imageTypeAttraction;
    @BindView(R.id.imageAttraction)
    ImageView imageAttraction;
    @BindView(R.id.commentHolder)
    LinearLayout commentHolder;
    @BindView(R.id.rateHolder)
    LinearLayout rateHolder;
    @BindView(R.id.doneHolder)
    LinearLayout doneHolder;
    @BindView(R.id.nowVisitedHolder)
    LinearLayout nowVisitedHolder;
    @BindView(R.id.beftorVisitedHolder)
    LinearLayout beftorVisitedHolder;
    @BindView(R.id.dislikeHolder)
    LinearLayout dislikeHolder;
    @BindView(R.id.okHolder)
    LinearLayout okHolder;
    @BindView(R.id.likeHolder)
    LinearLayout likeHolder;
    @BindView(R.id.bookmarkHolder)
    LinearLayout bookmarkHolder;
    @BindView(R.id.ratingHolder)
    RelativeLayout ratingHolder;
    @BindView(R.id.GroupHolder)
    RelativeLayout GroupHolder;
    @BindView(R.id.interestingLayout)
    RelativeLayout interestingLayout;
    @BindView(R.id.VisitedLayout)
    RelativeLayout VisitedLayout;
    @BindView(R.id.LikeLayout)
    RelativeLayout LikeLayout;
    @BindView(R.id.MoreInoText)
    TextView MoreInoText;
    @BindView(R.id.bookmarkImg)
    ImageView bookmarkImg;
    @BindView(R.id.doneImg)
    ImageView doneImg;
    @BindView(R.id.dislikeImg)
    ImageView dislikeImg;
    @BindView(R.id.okImg)
    ImageView okImg;
    @BindView(R.id.likeImg)
    ImageView likeImg;
    @BindView(R.id.rateImg)
    ImageView rateImg;
    @BindView(R.id.beftorVisitedImg)
    ImageView beftorVisitedImg;
    @BindView(R.id.nowVisitedImg)
    ImageView nowVisitedImg;
    @BindView(R.id.wishImg)
    ImageView wishImg;
    @BindView(R.id.triangleShowAttraction)
    ImageView triangleShowAttraction;
    @BindView(R.id.cameraaa)
    ImageView cameraaa;
    @BindView(R.id.recyclerBestAttraction)
    RecyclerView recyclerBestAttraction;
    DaggerAtractionDetailComponent.Builder builder;
    private List<ResultAttractionList> resultAttractionList;
    private ProgressDialog progressBar;
    private int FROMCAMERA = 4;
    private int FROMGALLERY = 1;
    String imgPath;
    private Uri mImageUri;

    private void findView() {
//        setContentView(R.layout.activity_attraction_detail);
//        setContentView(R.layout.fragment_attraction_detail);
        ButterKnife.bind(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    private void overrideFont() {
        // for Override font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setImageHolder() {
        if (resulAttraction.getAttractionImgUrl() != null) {
            String url = resulAttraction.getAttractionImgUrl();
            Glide.with(getApplicationContext())
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
                    .into(imageAttraction);

        } else {
            Glide.clear(imageAttraction);
            imageAttraction.setImageDrawable(null);
        }
    }

    private void setAttractionTypeImage() {
        if (resulAttraction.getAttractionItineraryTypeId() != null) {
            if (resulAttraction.getAttractionItineraryTypeId().equals("2930")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mazhabi));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2931")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_natural));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2932")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_historical));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2933")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sport));
            }
        }
    }

    private void setWebViewContent(String myData) {
        contentFullDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        contentFullDescription.setLongClickable(false);
        contentFullDescription.setHapticFeedbackEnabled(false);

        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSansMobile.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish + myData + pas;
        contentFullDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
    }

    private String getShowMoreString(String myData) {
        int count = 0;
        int position = 0;
        for (count = 0; count < 40; count++) {
            position = myData.indexOf(" ", position + 1);
        }
        return myData.substring(0, position) + "...";
    }

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        attraction = (entity.ResultItineraryAttraction) bundle.getSerializable("ResultItineraryAttraction");
        resulAttraction = (ResulAttraction) bundle.getSerializable("resulAttraction");
        resultAttractionList = (List<ResultAttractionList>) bundle.getSerializable("resultAttractionList");
        resultWidget = (List<ResultWidget>) bundle.getSerializable("resultWidget");
        if (resultWidget != null) {
            setInterestResponce(resultWidget);
        }
    }

    private void setNearAttraction(List<ResultAttractionList> resultAttractions) {
        NearAttractionAdapter attractionHomeAdapter = new NearAttractionAdapter(resultAttractions, getApplicationContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerBestAttraction.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestAttraction.setAdapter(attractionHomeAdapter);
        recyclerBestAttraction.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                attractionListMorePresenter.getAttractionDetailNear("full", resultAttractionList.get(position).getResulAttraction().getAttractionId(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

            }
        }));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        overrideFont();
        getExtra();
        setNearAttraction(resultAttractionList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resulAttraction.getAttractionTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        attractionPlace.setText(resulAttraction.getProvinceTitle() + " - " + resulAttraction.getCityTitle());
        if (resulAttraction.getAttractionEstimatedTime() != null) {
            int totalMinute = Integer.parseInt(resulAttraction.getAttractionEstimatedTime());
            Util.convertMinuteToHour(totalMinute, textTimeDuration);
        }

        setImageHolder();
        myData = resulAttraction.getAttractionBody();
        if (myData != null) {
            setWebViewContent(getShowMoreString(myData));
        }

        if (resulAttraction.getAttractionPrice() == null) {
            textEntranceFee.setText("هزینه ورودی : رایگان");
        } else {
            textEntranceFee.setText("هزینه ورودی : " + Util.persianNumbers(resulAttraction.getAttractionPrice().toString()) + "تومان");
        }
        attractionType.setText(resulAttraction.getAttractionItineraryTypeTitle());
        setAttractionTypeImage();
        txtAddress.setText(resulAttraction.getAttractionAddress());
        interestingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = interestingLayout.getWidth();
                int height = interestingLayout.getHeight();
                if (width > 0 && height > 0) {
                    VisitedLayout.setVisibility(View.INVISIBLE);
                    LikeLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
        mapFragment.getMapAsync(this);
        MoreInoText.setOnClickListener(this);
        ratingHolder.setOnClickListener(this);
        rateHolder.setOnClickListener(this);
        doneHolder.setOnClickListener(this);
        likeImg.setOnClickListener(this);
        okImg.setOnClickListener(this);
        dislikeImg.setOnClickListener(this);
        nowVisitedImg.setOnClickListener(this);
        wishImg.setOnClickListener(this);
        beftorVisitedImg.setOnClickListener(this);
        bookmarkHolder.setOnClickListener(this);
        commentHolder.setOnClickListener(this);

        builder = DaggerAtractionDetailComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .attractionDetailModule(new AttractionDetailModule(this, this));
        builder.build().inject(this);
        attractionDetailPresenter.getWidgetResult("nodeuser", resulAttraction.getAttractionId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "attraction", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        cameraaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldAskPermissions()) {
                    askPermissions();
                } else {
                    selectImage();
                }

            }
        });
    }

    Intent CamIntent, GalIntent, CropIntent;
    File file;
    Uri uri;
    public static final int RequestPermissionCode = 1;
    Bitmap selectedImage = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == FROMGALLERY) {

            if (data != null) {

                uri = data.getData();

                final Uri imageUri = data.getData();

                try {
                    final InputStream imageStream;
                    imageStream = getContentResolver().openInputStream(imageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final PhotoCropFragment photoCropFragment = new PhotoCropFragment(this);
                Bundle bundle = new Bundle();
//                Bitmap selectedImagegrab= grabImage(selectedImage,uri);
//               if(selectedImagegrab!=null){
//
//                   bundle.putParcelable("IMAGE_TO_CROP", selectedImagegrab);
//
//               }else {
//                   bundle.putParcelable("IMAGE_TO_CROP", selectedImage);
//
//               }

                bundle.putParcelable("IMAGE_TO_CROP", selectedImage);
                bundle.putParcelable("path", imageUri);
                photoCropFragment.setArguments(bundle);
                loadFragment(this, photoCropFragment, R.id.pe_container, true, 0, 0);


            }
        } else if (requestCode == FROMCAMERA) {

            selectedImage = decodeFile(imgPath);

            final PhotoCropFragment photoCropFragment = new PhotoCropFragment(this);
            Bundle bundle = new Bundle();
            bundle.putParcelable("IMAGE_TO_CROP", grabImage(selectedImage, imgPath));
            photoCropFragment.setArguments(bundle);
            loadFragment(this, photoCropFragment, R.id.pe_container, true, 0, 0);

        }


//        if (resultCode == Activity.RESULT_OK) {
//            Bitmap bm = null;
//            if (requestCode == FROMCAMERA) {
//                bm = grabImage();
//            }
//
//            if (bm != null) {
//                final PhotoCropFragment photoCropFragment = new PhotoCropFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("IMAGE_TO_CROP", bm);
//
//
//
//            }
//        }
    }


    public Bitmap grabImage(Bitmap bitmap, String path) {
//        ContentResolver cr = this.getContentResolver();
//        Bitmap bitmap = null;
        int tryCount = 0;
        try {
//            while ((bitmap = MediaStore.Images.Media.getBitmap(cr, path)) == null) {
//                Thread.sleep(10);
//                tryCount++;
//                if (tryCount > 500) {
//                    return null;
//                }
//            }

//            bitmap = MediaStore.Images.Media.getBitmap(cr, path);
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

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
            case 200: {
                selectImage();
            }
        }
    }


//    String mCurrentPhotoPath;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                Manifest.permission.CAMERA

        };
        int requestCode = 200;
        ActivityCompat.requestPermissions(attractionDetailActivity.this, permissions, requestCode);

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

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

                    mImageUri = FileProvider.getUriForFile(getApplicationContext(), "dreamgo.corp.provider", photo);
                    CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(CamIntent, FROMCAMERA);
//                } catch (Exception e) {
//
//                }


            } else {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageUri = Uri.fromFile(photo);
                    imgPath = mImageUri.getPath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, FROMCAMERA);
                } catch (Exception e) {

                }

            }
        }
    }


    public void getImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, FROMGALLERY);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private Menu menu = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attraction_detail, menu);
        this.menu = menu;
//        menu.findItem(R.id.menuAttractionShare).setVisible(true);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menuAttractionLike:
//                Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
//                OnClickedIntrestedWidget("like", Constants.intrestDefault, null);
////                menu.findItem(R.id.menuAttractionLike).setIcon(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
//                item.setIcon(R.mipmap.ic_like_off);
//                return true;
//            case R.id.menuAttractionShare:
//                Toast.makeText(this, "Shared!", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void setInterestResponce(List<ResultWidget> resultWidget) {
        if (resultWidget.get(0).getWidgetBookmarkValue() != null && resultWidget.get(0).getWidgetBookmarkValue() == 1) {
            bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmarkgreen));
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_off));
            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_off));
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 2) {
            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_off));
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 3) {
            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_off));
        }
        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 1) {
            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));
        }
        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 2) {
            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));
        }
        if (resultWidget.get(0).getWidgetWishValue() != null && resultWidget.get(0).getWidgetWishValue() == 1) {
            wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_pink));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        if (resulAttraction.getAttractionPositionLat() != null && resulAttraction.getAttractionPositionLon() != null) {
            float lan = Float.valueOf(resulAttraction.getAttractionPositionLat());
            float lon = Float.valueOf(resulAttraction.getAttractionPositionLon());
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));

            marker = mMap.addMarker(markerOptions
                    .position(new LatLng(lan, lon))
                    .title(resulAttraction.getCityTitle())
                    .snippet(":)"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 12.0f));

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.e("map is ckicked", "true");
                    Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
                    ItineraryLodgingCity i = new ItineraryLodgingCity();
                    i.setCityPositionLat(resulAttraction.getAttractionPositionLat());
                    i.setCityPositionLon(resulAttraction.getAttractionPositionLon());
                    List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
                    lodgingCities.add(i);
                    intent.putExtra("lodgingCities", (Serializable) lodgingCities);
                    intent.putExtra("resulAttraction", (Serializable) resulAttraction);
                    startActivity(intent);
                }
            });
        }

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(attractionDetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentHolder:
                showProgressDialog();
                builder.build().inject(this);
                attractionDetailPresenter.getAttractionCommentList("pagecomments", resulAttraction.getAttractionId(), "attraction", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                break;
            case R.id.MoreInoText:
                if (showMore) {
                    setWebViewContent(myData);
                    MoreInoText.setText("مطلب کوتاه");
                    showMore = false;
                } else {
                    if (resulAttraction.getAttractionBody() != null) {
                        setWebViewContent(getShowMoreString(myData));
                        MoreInoText.setText("بیشتر بخوانید");
                        showMore = true;
                    }
                }

                break;
            case R.id.ratingHolder:
                if (ratingHolderFlag) {
//                    translateUp();
                }
                break;
            case R.id.rateHolder:
                if (!ratingHolderFlag) {
                    VisitedLayout.setVisibility(View.INVISIBLE);
                    LikeLayout.setVisibility(View.VISIBLE);
                    rotateImage = "rateImg";
//                    translateDown();
                    break;
                } else {
//                    translateUp();
                    break;
                }

            case R.id.doneHolder:
                if (!ratingHolderFlag) {
                    LikeLayout.setVisibility(View.INVISIBLE);
                    VisitedLayout.setVisibility(View.VISIBLE);
                    rotateImage = "doneImg";
//                    translateDown();
                    break;
                } else {
//                    translateUp();
                    break;
                }

            case R.id.likeImg:
                rotateImage = "likeImg";
                if (LikeValue == 1) {
                    OnClickedIntrestedWidget("like", Constants.intrestDefault, likeImg);
                } else {
                    OnClickedIntrestedWidget("like", Constants.likeImg, likeImg);
                }
                break;
            case R.id.okImg:
                rotateImage = "okImg";
                if (LikeValue == 2) {
                    OnClickedIntrestedWidget("like", Constants.intrestDefault, okImg);
                } else {
                    OnClickedIntrestedWidget("like", Constants.okImg, okImg);
                }
                break;
            case R.id.dislikeImg:
                rotateImage = "dislikeImg";
                if (LikeValue == 2) {
                    OnClickedIntrestedWidget("like", Constants.intrestDefault, dislikeImg);
                } else {
                    OnClickedIntrestedWidget("like", Constants.dislikeImg, dislikeImg);
                }
                break;
            case R.id.wishImg:
                rotateImage = "wishImg";
                if (WishValue == 1) {
                    OnClickedIntrestedWidget("wish", Constants.intrestDefault, wishImg);
                } else {
                    OnClickedIntrestedWidget("wish", Constants.wishImg, wishImg);
                }
                break;
            case R.id.nowVisitedImg:
                rotateImage = "nowVisitedImg";

                if (VisitedValue == 1) {
                    OnClickedIntrestedWidget("visited", Constants.intrestDefault, nowVisitedImg);
                } else {
                    OnClickedIntrestedWidget("visited", Constants.nowVisitedImg, nowVisitedImg);
                }
                break;
            case R.id.beftorVisitedImg:
                rotateImage = "beftorVisitedImg";
                if (VisitedValue == 2) {
                    OnClickedIntrestedWidget("visited", Constants.intrestDefault, beftorVisitedImg);
                } else {
                    OnClickedIntrestedWidget("visited", Constants.beftorVisitedImg, beftorVisitedImg);
                }
                break;
            case R.id.bookmarkHolder:

                rotateImage = "bookmarkImg";

                if (BookmarkValue == 1) {
                    OnClickedIntrestedWidget("bookmark", Constants.intrestDefault, bookmarkImg);
                } else {
                    OnClickedIntrestedWidget("bookmark", Constants.bookmarkImg, bookmarkImg);
                }
                break;


        }
    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
//            attractionDetailPresenter.doWaitingAnimation(imageView);
            attractionDetailPresenter.getInterest("widget", Util.getUseRIdFromShareprefrence(getApplicationContext()), "1", "attraction", resulAttraction.getAttractionId(), gType, gValue, Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

        } else {
            Log.e("user is not login", "error");
            Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
        }
    }

    private void translateDown() {

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(ratingHolder, "translationY", interestingLayout.getHeight()),
                ObjectAnimator.ofFloat(GroupHolder, "translationY", interestingLayout.getHeight()),
                ObjectAnimator.ofFloat(triangleShowAttraction, "translationY", -55));
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
        ratingHolderFlag = true;

    }

    private void translateUp() {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(ratingHolder, "translationY", 0),
                ObjectAnimator.ofFloat(GroupHolder, "translationY", 0),
                ObjectAnimator.ofFloat(triangleShowAttraction, "translationY", 0));
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
        ratingHolderFlag = false;
    }

    private void animWaiting(ImageView image) {
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(5);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                translateUp();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void checkWhichImageIntrested(String imageView) {

        String im = imageView;
        switch (im) {
            case "bookmarkImg":
                bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmarkgreen));
                break;
            case "nowVisitedImg":
                nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
                doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
                beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));

                break;
            case "beftorVisitedImg":
                beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
                doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
                nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));

                break;
            case "dislikeImg":
                dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
                okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_off));
                likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));

                break;
            case "okImg":
                okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
                likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
                dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_off));
                break;
            case "likeImg":
                likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
                dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_off));
                okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_off));
                break;
            case "wishImg":
                wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_pink));
                break;
            default:
                break;
        }
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressBar = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", attractionDetailActivity.this);
    }

    @Override
    public void dismissProgress() {
        progressBar.dismiss();
    }


    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
        ResulAttraction resulAttraction = showAttractionFull.getResultAttractionFull().getResulAttraction();
        List<ResultAttractionList> resultAttractions = (List<ResultAttractionList>) showAttractionFull.getResultAttractionFull().getResultAttractionList();
        Intent intent = new Intent(this, attractionDetailActivity.class);
        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
        intent.putExtra("resultAttractionList", (Serializable) resultAttractions);
        startActivity(intent);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }


    @Override
    public void showComment(ResultCommentList resultCommentList, String commentType) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(attractionDetailActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("attraction", (Serializable) resulAttraction);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", commentType);
        startActivity(intent);
        progressDialog.dismiss();
    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {
        List<ResultWidget> resultWidget = resultWidgetFull.getResultWidget();
        setWidgetValue(resultWidget);
    }

    @Override
    public void setIntrestedWidget(InterestResult InterestResult) {
//        ResultData resultData = InterestResult.getResultData();
//        //// TODO: 14/02/2017
////        rotate.setRepeatCount(0);
//        checkWhichImageIntrested(rotateImage);
//        menu.findItem(R.id.menuAttractionLike).setIcon(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));

    }

    @Override
    public void showAnimationWhenWaiting() {
        ratingHolderFlag = attractionDetailPresenter.doTranslateAnimationUp(ratingHolder, GroupHolder, triangleShowAttraction);

    }

    @Override
    public void setIntrestValue(InterestResult InterestResult) {

    }

    @Override
    public void showDirectionOnMap(PolylineOptions rectLine) {
        mMap.addPolyline(rectLine);

    }

    private void setWidgetValue(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetBookmarkValue() != null) {
            BookmarkValue = resultWidget.get(0).getWidgetBookmarkValue();
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null) {
            LikeValue = resultWidget.get(0).getWidgetLikeValue();
        }
        if (resultWidget.get(0).getWidgetVisitedValue() != null) {
            VisitedValue = resultWidget.get(0).getWidgetVisitedValue();
        }
        if (resultWidget.get(0).getWidgetWishValue() != null) {
            WishValue = resultWidget.get(0).getWidgetWishValue();
        }


        if (resultWidget.get(0).getWidgetBookmarkValue() != null && resultWidget.get(0).getWidgetBookmarkValue() == 1) {
            bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmarkgreen));
        } else if (resultWidget.get(0).getWidgetBookmarkValue() == null || resultWidget.get(0).getWidgetBookmarkValue() == 0) {
            bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_grey));
        }
        if (resultWidget.get(0).getWidgetWishValue() != null && resultWidget.get(0).getWidgetWishValue() == 1) {
            wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_pink));
        } else if (resultWidget.get(0).getWidgetBookmarkValue() == null || resultWidget.get(0).getWidgetBookmarkValue() == 0) {
            wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_grey));
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 2) {
            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_off));

        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 3) {
            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_off));

        }
        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 1) {
            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_now_seen_on));
        } else if (resultWidget.get(0).getWidgetVisitedValue() == null || resultWidget.get(0).getWidgetVisitedValue() == 0) {
            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));

        }
        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 2) {
            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_on));
        } else if (resultWidget.get(0).getWidgetVisitedValue() == null || resultWidget.get(0).getWidgetVisitedValue() == 0) {
            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_before_seen_off));

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attraction_detail;
    }

    @Override
    public void onCropImage(Bitmap bitmap) {
        imageAttraction.setImageBitmap(bitmap);
    }

    private void selectImage() {
        final CharSequence[] items = {"از دوربین", "از گالری", "انصراف"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("انتخاب عکس");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("از دوربین")) {
//                    requestCameraPermission();
                    getImageFromCamera();

                } else if (items[item].equals("از گالری")) {
                    getImageFromGallery();
                } else if (items[item].equals("انصراف")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void showCamera() {
//        Log.i(TAG, "Show camera button pressed. Checking permission.");
        // BEGIN_INCLUDE(camera_permission)
        // Check if the Camera permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCameraPermission();

        } else {

            // Camera permissions is already available, show the camera preview.
            Log.i(TAG,
                    "CAMERA permission has already been granted. Displaying camera preview.");
//            showCameraPreview();
//            if (shouldAskPermissions()) {
//                askPermissions();
//            }

        }
        // END_INCLUDE(camera_permission)

    }

    private void requestCameraPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
//            Snackbar.make(mLayout, "hey",
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
            ActivityCompat.requestPermissions(attractionDetailActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
//
//                        }
//                    })
//                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }

}
