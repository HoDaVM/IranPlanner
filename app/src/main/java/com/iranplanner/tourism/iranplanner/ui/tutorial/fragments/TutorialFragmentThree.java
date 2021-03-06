package com.iranplanner.tourism.iranplanner.ui.tutorial.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.tutorial.Car;
import com.iranplanner.tourism.iranplanner.ui.tutorial.TutorialActivity;

import tools.Util;

/**
 * Created by MrCode on 9/25/17.
 */

public class TutorialFragmentThree extends Fragment implements View.OnClickListener {

    private ImageView tent, trumpet;
    private TextView tvDesc ;
    private Car car;
    private Button btnOk;

    private View container;

    private int tentHeight, trumpetHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial_fragment_three, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        tent = (ImageView) view.findViewById(R.id.tutTentIv);
        trumpet = (ImageView) view.findViewById(R.id.tutTrumpetIv);

        tvDesc = (TextView) view.findViewById(R.id.tutDescThreeTv);

        container = view.findViewById(R.id.tutMasterContainer);

        btnOk = (Button) view.findViewById(R.id.okBtn);
        hideOkBtn();

        tent.bringToFront();
        trumpet.bringToFront();

        tentHeight = (int) (Util.dpToPx(getContext(), 150) / 2);
        trumpetHeight = (int) (Util.dpToPx(getContext(), 80) / 2);

        tvDesc.setAlpha(0f);

        tent.setTranslationY(tentHeight);
        tent.setAlpha(0f);

        trumpet.setTranslationY(trumpetHeight);
        trumpet.setScaleX(0f);
        trumpet.setScaleY(0f);

        car = new Car(view.findViewById(R.id.tutWholeVanRl), getActivity());

    }

    private void init() {
        car.driveIn();
        car.spinTheWheels();
        car.stopTheWheels();

        tent.animate().translationYBy(-tentHeight).alpha(1f).setDuration(600);
        trumpet.animate().translationYBy(-trumpetHeight).scaleY(1f).scaleX(1f).setStartDelay(400).setDuration(600);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                trumpet.clearAnimation();
                YoYo.with(Techniques.Tada)
                        .repeat(YoYo.INFINITE)
                        .playOn(trumpet);
            }
        }, 1000);

        tvDesc.animate().alpha(1f).setDuration(500).setStartDelay(2000);

        showOkBtn();
    }

    private void hideOkBtnAnimate() {
        btnOk.setOnClickListener(null);
        btnOk.animate().translationYBy(Util.dpToPx(getContext(), 120)).setStartDelay(500);
        tvDesc.animate().alpha(0f).setDuration(500).setStartDelay(500);
    }

    private void hideOkBtn() {
        btnOk.setTranslationY(Util.dpToPx(getContext(), 120));
    }

    private void showOkBtn() {
        btnOk.setOnClickListener(this);
        btnOk.animate().translationYBy(Util.dpToPx(getContext(), -120)).setStartDelay(2000);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            init();
    }

    @Override
    public void onClick(View view) {
        car.spinTheWheels();
        car.driveOut();
        hideOkBtnAnimate();
        container.animate().alpha(0f).setDuration(1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TutorialActivity) getActivity()).nextPage();
            }
        }, 2000);
    }
}
