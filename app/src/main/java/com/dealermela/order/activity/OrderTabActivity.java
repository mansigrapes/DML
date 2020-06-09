package com.dealermela.order.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.order.adapter.TabOrderAdapter;

public class OrderTabActivity extends DealerMelaBaseActivity {

    private TabLayout tabLayoutOrders;
    private ViewPager viewPagerOrder;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_tab;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        bindToolBar("Orders");
        tabLayoutOrders = findViewById(R.id.tabLayoutOrders);
        viewPagerOrder = findViewById(R.id.viewPagerOrder);
    }

    @Override
    public void postInitView() {
        TabOrderAdapter tabOrderAdapter = new TabOrderAdapter(getSupportFragmentManager());
        viewPagerOrder.setAdapter(tabOrderAdapter);
        tabLayoutOrders.setupWithViewPager(viewPagerOrder);
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {
    }
}
