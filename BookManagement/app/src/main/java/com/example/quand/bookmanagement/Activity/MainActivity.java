package com.example.quand.bookmanagement.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.quand.bookmanagement.Adapter.AppPagerAdapter;
import com.example.quand.bookmanagement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.view_pager_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        AppPagerAdapter adapter = new AppPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_library_books_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_timer_black_24dp);
        tabLayout.setUnboundedRipple(true);
    }
}
