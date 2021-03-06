package com.heavymetal.giphy.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.heavymetal.giphy.adapter.IntroAdapter;
import com.heavymetal.giphy.manager.PrefManager;
import com.heavymetal.giphy.ui.view.CarouselEffectTransformer;
import com.heavymetal.giphy.ui.view.ClickableViewPager;
import com.heavymetal.giphy.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ClickableViewPager view_pager_slide;
    private IntroAdapter slide_adapter;
    private List<Integer> slideList= new ArrayList<>();
    private ViewPagerIndicator view_pager_indicator;
    private RelativeLayout relative_layout_slide;
    private LinearLayout linear_layout_skip;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        prefManager= new PrefManager(getApplicationContext());

        slideList.add(1);
        slideList.add(2);
        slideList.add(3);
        slideList.add(4);
        slideList.add(5);
        slideList.add(6);
        slideList.add(7);
        slideList.add(8);

        this.linear_layout_skip=(LinearLayout) findViewById(R.id.linear_layout_skip);
        this.view_pager_indicator=(ViewPagerIndicator) findViewById(R.id.view_pager_indicator);
        this.view_pager_slide=(ClickableViewPager) findViewById(R.id.view_pager_slide);
        this.relative_layout_slide=(RelativeLayout) findViewById(R.id.relative_layout_slide);
        slide_adapter = new IntroAdapter(getApplicationContext(),slideList);
        view_pager_slide.setAdapter(this.slide_adapter);
        view_pager_slide.setOffscreenPageLimit(1);
        view_pager_slide.setPageTransformer(false, new CarouselEffectTransformer(IntroActivity.this)); // Set transformer


        view_pager_slide.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position <7){
                    view_pager_slide.setCurrentItem(position+1);
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(IntroActivity.this,   Manifest.permission.READ_CONTACTS)) {
                                Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                                startActivity(intent_status);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                                finish();
                            } else {
                                Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                                startActivity(intent_status);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                                finish();
                            }
                        }else{
                            Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        }

                    }else{

                            Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();

                    }
                }
            }
        });
        this.linear_layout_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(IntroActivity.this,   Manifest.permission.READ_CONTACTS)) {
                            Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                            startActivity(intent_status);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        } else {
                            Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                            startActivity(intent_status);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        }
                    }else{
                        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        finish();
                    }

                }else{

                        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        finish();


                }

            }
        });
        this.view_pager_slide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#f45e5e"));
                        break;
                    case 1:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#034182"));
                        break;
                    case 2:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#1a2634"));
                        break;
                    case 3:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#61519f"));
                        break;
                    case 4:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#288fb4"));
                        break;
                    case 5:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#920a84"));
                        break;
                    case 6:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#C51062"));
                        break;
                    case 7:
                        relative_layout_slide.setBackgroundColor(Color.parseColor("#449187"));
                        break;
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        this.view_pager_slide.setClipToPadding(false);
        this.view_pager_slide.setPageMargin(0);
        view_pager_indicator.setupWithViewPager(view_pager_slide);
    }

}
