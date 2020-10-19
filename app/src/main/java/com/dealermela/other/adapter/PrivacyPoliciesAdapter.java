package com.dealermela.other.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dealermela.other.fragment.PolicyFrg;
import com.dealermela.util.AppConstants;

public class PrivacyPoliciesAdapter extends FragmentPagerAdapter {

    public PrivacyPoliciesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new PolicyFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME,"shipping");
            fragment.setArguments(bundle);
        }
        else if (position == 1)
        {
            fragment = new PolicyFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME,"return");
            fragment.setArguments(bundle);
        }
        else if (position == 2)
        {
            fragment = new PolicyFrg();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.NAME,"privacy");
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
        if (position == 0)
        {
            title = "Shipping Policy";
        }
        else if (position == 1)
        {
            title = "Return Policy";
        }
        else if (position == 2)
        {
            title = "Privacy Policy";
        }
        return title;
    }
}
