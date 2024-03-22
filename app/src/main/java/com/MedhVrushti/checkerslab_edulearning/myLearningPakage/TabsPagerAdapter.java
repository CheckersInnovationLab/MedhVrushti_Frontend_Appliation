package com.MedhVrushti.checkerslab_edulearning.myLearningPakage;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    public TabsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new MyLearning_Tab1_Fragment();
            case 1: return new MYLearning_Tab2_Fragment();
            default: return new MyLearning_Tab3_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "In Progress";
            case 1: return "Explore";
            default: return "Incoming";
        }
    }
}
