package com.dealermela.authentication.myaccount.activity;

import android.os.Bundle;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;

public class TermsConditionAct extends DealerMelaBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_terms_condition;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        bindToolBar("Terms and Condition");
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {
    }
}
