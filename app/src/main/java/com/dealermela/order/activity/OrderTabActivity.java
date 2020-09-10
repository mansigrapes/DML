package com.dealermela.order.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.order.adapter.TabOrderAdapter;
import com.dealermela.util.AppLogger;

import static com.dealermela.cart.activity.OrderSummaryAct.Orderflag;

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
}
