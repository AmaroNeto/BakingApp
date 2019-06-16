package com.amaro.bakingapp.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Step;
import com.amaro.bakingapp.view.fragment.StepDetailFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_STEPS = "extra_steps";

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.bt_next)
    Button mNextButton;
    @BindView(R.id.bt_previous)
    Button mPreviousButton;

    private ArrayList<Step> mSteps;
    private int position;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            mSteps = intent.getParcelableArrayListExtra(EXTRA_STEPS);
            position = intent.getIntExtra(EXTRA_POSITION,0);
        }

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(position);

            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPager.getCurrentItem() < mSteps.size()) {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            });

            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPager.getCurrentItem() > 0) {
                        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                    }
                }
            });

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return StepDetailFragment.newInstance(mSteps.get(position));
        }

        @Override
        public int getCount() {
            return mSteps.size();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //Reload fragment
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
            setButtonsVisibility(View.GONE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {

            //Reload fragment
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
            setButtonsVisibility(View.VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
        }

    }

    private void setButtonsVisibility(int visilibilty) {
        mPreviousButton.setVisibility(visilibilty);
        mNextButton.setVisibility(visilibilty);
    }
}
