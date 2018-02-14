package com.iranplanner.tourism.iranplanner.photoViewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iranplanner.tourism.iranplanner.R;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import entity.CustomImage;
import entity.ResultImage;

/**
 * Created by h.vahidimehr on 25/02/2017.
 */

public class GridImageActivity extends AppCompatActivity {
    GridView grid;
    List<ResultImage> resultImages;
    String[] list;

    List<CustomImage> customImages;
    ImageOverlayView overlayView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grid);
        Bundle extras = getIntent().getExtras();
        overlayView = new ImageOverlayView(GridImageActivity.this);
        resultImages = (List<ResultImage>) extras.getSerializable("ResultImagesList");
        ImageGridAdapter adapter = new ImageGridAdapter(GridImageActivity.this, resultImages);
        grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        list = new String[]{resultImages.get(0).getImgUrl().toString(),
                resultImages.get(1).getImgUrl().toString(),
                resultImages.get(2).getImgUrl().toString()
        };
        customImages = new ArrayList<>();


        for (ResultImage resultImage : resultImages) {
            customImages.add(new CustomImage(resultImage.getImgUrl().toString(), resultImage.getImgTitle().toString()));

        }
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                overlayView = new ImageOverlayView(GridImageActivity.this);
                new ImageViewer.Builder<>(GridImageActivity.this, customImages)
                        .setFormatter(getCustomFormatter())
                        .setImageChangeListener(getImageChangeListener())
                        .setOverlayView(overlayView)
                        .setOnDismissListener(getDismissListener())
                        .setStartPosition(position)
                        .show();
            }
        });

    }


    private ImageViewer.Formatter<CustomImage> getCustomFormatter() {
        return new ImageViewer.Formatter<CustomImage>() {
            @Override
            public String format(CustomImage customImage) {
                return customImage.getUrl();
            }
        };
    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                CustomImage image = customImages.get(position);
                overlayView.setShareText(image.getUrl());
                overlayView.setDescription(image.getDescription());
            }
        };
    }


    private ImageViewer.OnDismissListener getDismissListener() {
        return new ImageViewer.OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        };
    }

}
