package com.dealermela.authentication.myaccount.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dealermela.R;
import com.dealermela.util.SharedPreferences;
import static com.dealermela.home.activity.MainActivity.customerId;

public class MaintenanceDialogClass extends Dialog implements View.OnClickListener {

    private final Activity activity;
    public Dialog d;
    private Button btnOk;

    public MaintenanceDialogClass(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_maintenance);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yes) {
            activity.finish();
        }
        dismiss();
    }

//    used
//    CustomDialogClass cdd=new CustomDialogClass(Values.this);
//cdd.show();
}
