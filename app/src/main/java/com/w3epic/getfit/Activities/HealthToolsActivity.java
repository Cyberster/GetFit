package com.w3epic.getfit.Activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.w3epic.getfit.Fragments.BMICalculatorFragment;
import com.w3epic.getfit.Fragments.FatPercentageFragment;
import com.w3epic.getfit.Adapter.PagerAdapter;
import com.w3epic.getfit.Fragments.PedometerFragment;
import com.w3epic.getfit.R;


public  class HealthToolsActivity extends AppCompatActivity  implements
        PedometerFragment.OnFragmentInteractionListener,
        BMICalculatorFragment.OnFragmentInteractionListener,
        FatPercentageFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tools);
        setTitle("Health tools");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("PedometerFragment"));
        tabLayout.addTab(tabLayout.newTab().setText("BMI Calculator"));
        tabLayout.addTab(tabLayout.newTab().setText("FAT % Calculator"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {

            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // selecting tab after redirection
        // https://stackoverflow.com/questions/36583719/intent-to-open-a-specific-tab-of-tabbed-activity
        int defaultValue = 0;
        int page = getIntent().getIntExtra("tab_index", defaultValue);
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onFragmentInteraction(Uri uri){
    }
}