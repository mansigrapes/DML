package com.dealermela.other.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.other.adapter.PrivacyPoliciesAdapter;

public class PolicyAct extends DealerMelaBaseActivity {
    private TabLayout tabLayoutPolicies;
    private ViewPager viewPagerPolicies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_policy;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        bindToolBar("Policies");
        tabLayoutPolicies=findViewById(R.id.tabLayoutPolicies);
        viewPagerPolicies=findViewById(R.id.viewPagerPolicies);
    }

    @Override
    public void postInitView() {
        PrivacyPoliciesAdapter privacyPoliciesAdapter = new PrivacyPoliciesAdapter(getSupportFragmentManager());
        viewPagerPolicies.setAdapter(privacyPoliciesAdapter);
        tabLayoutPolicies.setupWithViewPager(viewPagerPolicies);

    }

    @Override
    public void addListener() {

    }

    @Override
    public void loadData() {

    }



}
