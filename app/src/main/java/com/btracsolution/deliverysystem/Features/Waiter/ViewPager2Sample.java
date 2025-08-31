package com.btracsolution.deliverysystem.Features.Waiter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

public class ViewPager2Sample extends AppCompatActivity {

    ViewPager2 myViewPager2;
    ViewPagerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2_sample);

        myViewPager2 = findViewById(R.id.viewPager2);

        myAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        // add Fragments in your ViewPagerFragmentAdapter class
//        myAdapter.addFragment(new first_fragment());
//        myAdapter.addFragment(new second_fragment());
//        myAdapter.addFragment(new third_fragment());

        // set Orientation in your ViewPager2
        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        myViewPager2.setAdapter(myAdapter);
    }





}