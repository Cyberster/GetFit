package com.w3epic.getfit.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w3epic.getfit.Fragments.BMICalculatorFragment;
import com.w3epic.getfit.Fragments.FatPercentageFragment;
import com.w3epic.getfit.Fragments.PedometerFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                PedometerFragment pedometerFragment = new PedometerFragment();
                return pedometerFragment;
            case 1:
                BMICalculatorFragment bmIcalculator = new BMICalculatorFragment();
                return bmIcalculator;
            case 2:
                FatPercentageFragment fatPercentageFragment = new FatPercentageFragment();
                return fatPercentageFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
