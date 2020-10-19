package com.dealermela;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.dealermela.cart.activity.CartAct;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.interfaces.ViewBindingListener;
import com.dealermela.other.activity.SearchAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.ThemePreferences;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.diamondValue;
import static com.dealermela.listing_and_detail.adapter.BangleAdapter.bangleProductId;
import static com.dealermela.listing_and_detail.adapter.BraceletsAdapter.braceletProductId;
import static com.dealermela.listing_and_detail.adapter.CaratAdapter.caratValue;
import static com.dealermela.listing_and_detail.adapter.MetalAdapter.metalValue;
import static com.dealermela.listing_and_detail.adapter.PendentSetsAdapter.pendentProId;
import static com.dealermela.listing_and_detail.adapter.RingAdapter.ringValue;

public abstract class DealerMelaBaseActivity extends AppCompatActivity implements ViewBindingListener {

    private static String changeLang = "";
    protected static final String TAG = DealerMelaBaseActivity.class.getSimpleName();
    protected ProgressDialog mProgressDialog;
    private KProgressHUD hud;
    public static TextView textCartItemCount ;
    Switch switchAB;
    public static int cartCount = 0;
    public static int downloadCount = 0;
//    int totalitem = 0;

    private SharedPreferences sharedPreferences;
    private DatabaseCartAdapter databaseCartAdapter;
    //    private RelativeLayout relCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//    setTheme(R.style.BlackTheme_FullScreen);
        super.onCreate(savedInstanceState);
        databaseCartAdapter = new DatabaseCartAdapter(DealerMelaBaseActivity.this);
        sharedPreferences = new SharedPreferences(DealerMelaBaseActivity.this);
        ThemePreferences themePreferences = new ThemePreferences(DealerMelaBaseActivity.this);
        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            setTheme(R.style.BlackTheme_FullScreen);
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            setTheme(R.style.WhiteTheme_FullScreen);
        } else {
            setTheme(R.style.WhiteTheme_FullScreen);
        }
        Fresco.initialize(this);

        setContentView(getLayoutResourceId());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (NetworkUtils.isNetworkConnected(DealerMelaBaseActivity.this)) {

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        hud = new KProgressHUD(DealerMelaBaseActivity.this);
        init();
        initView();
        postInitView();
        addListener();
        loadData();
        //mProgressDialog = new ProgressDialog(this , R.style.MyProgressDialogStyle);
    }

    protected abstract int getLayoutResourceId();

    protected void startNewActivity(Class<? extends DealerMelaBaseActivity> activity) {
        startActivity(new Intent(this, activity));
        overridePendingTransitionEnter();
    }

    protected void startNewActivityWithIntent(Intent intent) {
        startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void bindToolBar(String headerTitle) {
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(headerTitle);
        if (headerTitle.equalsIgnoreCase("")) {

        } else {
            Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/montserrat_semibold.ttf");
            ((TextView) toolbar.getChildAt(1)).setTypeface(fromAsset);
        }
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
                // perform whatever you want on back arrow click
                caratValue = "14K";
                metalValue = "Yellow Gold";
                diamondValue = "SI-IJ";
                ringValue = "12";
                braceletProductId = "";
                bangleProductId = "";
                pendentProId = "";
                finish();
            }
        });


      /*  relCart = toolbar.findViewById(R.id.relCart);

        relCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(CartAct.class);
            }
        });*/
    }

    private void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();

        caratValue = "14K";
        metalValue = "Yellow Gold";
        diamondValue = "SI-IJ";
        ringValue = "12";
        braceletProductId = "";
        bangleProductId = "";
        pendentProId = "";
//        mapFilter.clear();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void showProgressDialog(String title) {

        if (hud != null) {
            hud.dismiss();
        }
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
//                .setDetailsLabel(message)
                .show();


        /*if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, title, message);*/
    }

    protected void hideProgressDialog() {
        /*if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }*/

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
            hud = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // hide progress dialog to prevent leaks
        hideProgressDialog();
//    hud.dismiss();
    }

    //Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainwithcart, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

/*
        //using for theme change
        MenuItem item = (MenuItem) menu.findItem(R.id.switchId);
        item.setActionView(R.layout.switch_layout);
        Switch switchAB = item
                .getActionView().findViewById(R.id.switchAB);

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            switchAB.setChecked(false);
        } else {
            switchAB.setChecked(true);
        }

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    themePreferences.saveTheme("white");
                    finish();
                    startActivity(getIntent());
                } else {
                    themePreferences.saveTheme("black");
                    finish();
                    startActivity(getIntent());
                }
            }
        });*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                // Do something
                startNewActivity(CartAct.class);
                return true;
            }
            case R.id.action_search: {
                startNewActivity(SearchAct.class);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setupBadge() {
        if (textCartItemCount != null) {
            if (cartCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(cartCount, 99)));
                textCartItemCount.setVisibility(View.VISIBLE);
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setupBadge();

        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            databaseCartAdapter.openDatabase();
            Cursor c = databaseCartAdapter.getAllValues();
//            cartCount = c.getCount();
            if(c.getCount() > 0 ) {
                cartCount = Integer.parseInt(c.getString(c.getColumnIndex("total_item")));
            }else {
                cartCount = 0;
            }
//            cartCount = totalqty;           //comment this bcz it increase every time when this page & shipping cart opens
  /*          for (int i = 0; i < c.getCount(); i++) {
                totalitem = totalitem + Integer.parseInt(c.getString(c.getColumnIndex("qty")));
                c.moveToNext();
            }
            cartCount = totalitem;  */
            databaseCartAdapter.closeDatabase();
            setupBadge();
        } else {
            getCartCount();
        }
    }

    public static void getCartCount() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.getCartDownloadCount(customerId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                        if (jsonObject.getInt("total_qty") == 0) {
                            cartCount = jsonObject.getInt("total_qty");
                            setupBadge();
                        } else {
                            cartCount = jsonObject.getInt("total_qty");
                            setupBadge();
                        }

                        if (jsonObject.getInt("download_count") != 0) {
                            downloadCount = jsonObject.getInt("download_count");
                        } else {
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }
}
