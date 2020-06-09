package com.dealermela.authentication.myaccount.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.LoginAct;
import com.dealermela.util.SharedPreferences;
import static com.dealermela.home.activity.MainActivity.customerId;

public class LogoutDialogClass extends Dialog implements android.view.View.OnClickListener {

    private final Activity activity;
    public Dialog d;
    private Button yes;
    private Button no;

    public LogoutDialogClass(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_logout);
        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                logout();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void logout() {
        SharedPreferences sharedPreferences = new SharedPreferences(activity);
        sharedPreferences.saveLoginData("");
        sharedPreferences.saveShipping("");
        sharedPreferences.saveBillingAddress("");
//        sharedPreferences.saveRemember("");
        customerId="";
        activity.startActivity(new Intent(activity,LoginAct.class));
//        activity.finishAffinity();
    }


//    used
//    CustomDialogClass cdd=new CustomDialogClass(Values.this);
//cdd.show();



}
