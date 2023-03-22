package com.e.bodobhasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("NUMBERS"));
        tabLayout.addTab(tabLayout.newTab().setText("FAMILY"));
        tabLayout.addTab(tabLayout.newTab().setText("COLORS"));
        tabLayout.addTab(tabLayout.newTab().setText("PHRASES"));


        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),getLifecycle(),tabLayout.getTabCount());

        viewPager2.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

//    public void numbersActivity(View view){
//        Intent intent = new Intent(this, NumbersActivity.class);
//        startActivity(intent);
//    }
    }
}