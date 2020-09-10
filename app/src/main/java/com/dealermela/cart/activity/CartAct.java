package com.dealermela.cart.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.cart.fragment.ShippingFrg;
import com.dealermela.cart.fragment.ShoppingFrg;

import static com.dealermela.authentication.myaccount.activity.LoginAct.cartbackFlag;
import static com.dealermela.authentication.myaccount.activity.LoginAct.fragment;

import static com.dealermela.listing_and_detail.activity.ProductDetailAct.cartCheckBugNowFlag;
import static com.dealermela.other.activity.SplashAct.loginFlag;

public class CartAct extends DealerMelaBaseActivity implements View.OnClickListener {
    private TextView tvMyCart, tvShipping, tvPayment;
    private ImageView imgMyCart, imgShipping, imgPayment;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_cart;
    }

    @Override
    public void init() {
        closeOptionsMenu();
    }

    @Override
    public void initView() {
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping Cart");
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

// id declaration
        tvMyCart = findViewById(R.id.tvMyCart);
        tvShipping = findViewById(R.id.tvShipping);
        tvPayment = findViewById(R.id.tvPayment);
        imgMyCart = findViewById(R.id.imgMyCart);
        imgShipping = findViewById(R.id.imgShipping);
        imgPayment = findViewById(R.id.imgPayment);
    }

    @Override
    public void postInitView() {

        Fragment fragment = null;
        if (cartCheckBugNowFlag == 0) {
            fragment = new ShoppingFrg();
            replaceCartFragment(fragment);
        } else if (cartCheckBugNowFlag == 1) {
            fragment = new ShippingFrg();
            replaceCartFragment(fragment);
        }
//        imgMyCart.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
    }

    @Override
    public void addListener() {
        tvMyCart.setOnClickListener(this);
        tvShipping.setOnClickListener(this);
        tvPayment.setOnClickListener(this);
    }

    @Override
    public void loadData() {
    }

    public void replaceCartFragment(Fragment fragment) {
        hideUnSelectFragment();
        FragmentManager fm = getSupportFragmentManager();
        assert fm != null;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameCart, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMyCart:
//                fragment = new ShoppingFrg();
//                replaceCartFragment(fragment);
//                imgMyCart.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
//                tvShipping.setTextColor(R.color.in_active_item_color);
//                tvShipping.setTextColor(getResources().getColor(R.color.in_active_item_color));
//                tvPayment.setTextColor(getResources().getColor(R.color.in_active_item_color));
                break;

            case R.id.tvShipping:
//                fragment = new ShippingFrg();
//                replaceCartFragment(fragment);
//                imgShipping.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
//                tvMyCart.setTextColor(getResources().getColor(R.color.in_active_item_color));
//                tvPayment.setTextColor(getResources().getColor(R.color.in_active_item_color));
                break;

            case R.id.tvPayment:
//                fragment = new PaymentFrg();
//                replaceCartFragment(fragment);
//               imgPayment.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
//                tvMyCart.setTextColor(getResources().getColor(R.color.in_active_item_color));
//                tvShipping.setTextColor(getResources().getColor(R.color.in_active_item_color));
                break;
        }
    }

    private void hideUnSelectFragment() {
        imgMyCart.setImageResource(0);
        imgShipping.setImageResource(0);
        imgPayment.setImageResource(0);
    }

    @Override
    public void onBackPressed() {
        loginFlag = 0;
        cartCheckBugNowFlag=0;

        if(cartbackFlag == 1){
//            cartbackFlag = 0;
//            finish();
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                cartbackFlag = 2;
                Fragment fragment = null;
                fragment = new ShoppingFrg();
                replaceCartFragment(fragment);

//                finish();
//                getSupportFragmentManager().popBackStack();
//                super.onBackPressed();
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
//                super.onBackPressed();
            } else {
                super.onBackPressed();
            }
//            super.onBackPressed();
        }else if(cartbackFlag == 2){
            cartbackFlag = 0;
            finish();
        } else {
//            finish();
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
//        finish();
    }

    //Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_cart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
