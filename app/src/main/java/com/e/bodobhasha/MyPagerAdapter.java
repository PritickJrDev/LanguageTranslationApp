package com.e.bodobhasha;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {

    int numberOfTabs;

    public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int numberOfTabs) {
        super(fragmentManager, lifecycle);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NumberFragment();

            case 1:
                return new FamilyFragment();

            case 2:
                return new ColorFragment();

            case 3:
                return new PhrasesFragment();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numberOfTabs;
    }
}
