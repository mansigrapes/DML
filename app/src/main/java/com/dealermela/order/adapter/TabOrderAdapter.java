package com.dealermela.order.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dealermela.order.fragment.MyOrderFrg;
import com.dealermela.other.fragment.PolicyFrg;
import com.dealermela.util.AppConstants;

public class TabOrderAdapter extends FragmentPagerAdapter {

    public TabOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new MyOrderFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME, "AllOrder");
            fragment.setArguments(bundle);

        } else if (position == 1) {
            fragment = new MyOrderFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME, "MyOrder");
            fragment.setArguments(bundle);

        } else if (position == 2) {
            fragment = new MyOrderFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME, "CustomerOrder");
            fragment.setArguments(bundle);

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "All Order";
        } else if (position == 1) {
            title = "My Order";
        } else if (position == 2) {
            title = "Customer Order";
        }
        return title;
    }
}
