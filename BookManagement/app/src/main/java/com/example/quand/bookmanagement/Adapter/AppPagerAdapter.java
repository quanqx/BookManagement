package com.example.quand.bookmanagement.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.quand.bookmanagement.Fragment.AlarmFragment;
import com.example.quand.bookmanagement.Fragment.BookFragment;

public class AppPagerAdapter extends FragmentPagerAdapter{

    BookFragment bookFragment;
    AlarmFragment alarmFragment;

    public AppPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        bookFragment = new BookFragment();
        alarmFragment = new AlarmFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment selectedFragment = null;

        switch (i){
            case 0:
                selectedFragment = bookFragment;
                break;
            case 1:
                selectedFragment = alarmFragment;
                break;
            default:
                selectedFragment = bookFragment;
        }

        return selectedFragment;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//
//        switch (position){
//            case 0:
//                title = "Book";
//                break;
//            case 1:
//                title = "Alarm";
//                break;
//            default:
//                title = "Untitled";
//        }
//
//        return title;
//    }


}
