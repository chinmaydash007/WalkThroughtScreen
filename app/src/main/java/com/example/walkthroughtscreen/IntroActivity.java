package com.example.walkthroughtscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
ViewPager screenpager;
IntroViewPagerAdapter introViewPagerAdapter;
TabLayout tabLayout;
Button button_next;
int position=0;
Button button_getstarted;
Animation get_stated_btn_animation;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(restorePrefData()){
            Intent intent=new Intent(IntroActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        //make activity fullscreen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        setContentView(R.layout.activity_intro);
        tabLayout=findViewById(R.id.tabLayout);
        button_next=findViewById(R.id.button_next);
        button_getstarted=findViewById(R.id.button);
        get_stated_btn_animation= AnimationUtils.loadAnimation(this,R.anim.get_started_btn_anim);




        final List<ScreenItem> mList= new ArrayList<>();
        mList.add(new ScreenItem("Title 1","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it",R.drawable.undraw_personal_notebook_sobb));
        mList.add(new ScreenItem("Title 2","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it",R.drawable.undraw_sunlight_tn7t));
        mList.add(new ScreenItem("Title 3","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it",R.drawable.undraw_trends_a5mf));

        screenpager=findViewById(R.id.viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mList);
        screenpager.setAdapter(introViewPagerAdapter);

        tabLayout.setupWithViewPager(screenpager);
        tabLayout.setSelectedTabIndicator(R.drawable.indicator_selected);
        tabLayout.getTabAt(0).setIcon(R.drawable.indicator_selected);

        button_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntroActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                savePrefData();
                finish();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.indicator_selected);
                if(tab.getPosition()==mList.size()-1){
                    loadLastPage();
                }
                if(tab.getPosition()!=mList.size()-1){
                    button_getstarted.setVisibility(View.INVISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    button_next.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.indicator_default);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {



            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenpager.getCurrentItem();
                if(position<mList.size()){
                    position++;
                    screenpager.setCurrentItem(position);

                }
                if(position==mList.size()-1){
                    loadLastPage();
                }

            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("myprefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore=preferences.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefData() {
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();

    }

    private void loadLastPage() {
        button_next.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        button_getstarted.setAnimation(get_stated_btn_animation);
        button_getstarted.setVisibility(View.VISIBLE);

    }
}
