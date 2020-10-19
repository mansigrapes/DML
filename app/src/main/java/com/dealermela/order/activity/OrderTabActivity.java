package com.dealermela.order.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

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
        bindToolBar("Order List");
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

    //Not require In this page so comment it
//    @Override
//    public void onBackPressed(){
//        AppLogger.e("TabOrderActivity ","Back Pressed---");
//        if(Orderflag == 1){
//            Orderflag = 0;
//            startNewActivity(MainActivity.class);
//        }else {
//            startNewActivity(MainActivity.class);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

}
