package com.dealermela.home.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.dealermela.home.model.SubcategoryItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.EditProfileAct;
import com.dealermela.authentication.myaccount.activity.LoginAct;
import com.dealermela.authentication.myaccount.activity.SignUpAct;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.cart.activity.CartAct;
import com.dealermela.download.activity.DownloadAct;
import com.dealermela.home.fragment.HomeFrg;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.my_stock.activity.MyStockAct;
import com.dealermela.order.activity.OrderTabActivity;
import com.dealermela.other.activity.ContactUsAct;
import com.dealermela.other.activity.PolicyAct;
import com.dealermela.other.activity.SearchAct;
import com.dealermela.referral.activity.CreateReferralAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.transaction.activity.TransactionAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.ThemePreferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.listing_and_detail.activity.FilterAct.mapFilter;
import static com.dealermela.listing_and_detail.activity.FilterAct.selectFilter;
import static com.dealermela.other.activity.SplashAct.loginFlag;

public class MainActivity extends DealerMelaBaseActivity implements View.OnClickListener {

    private Fragment fragment;
    private TextView tvMyAccount, tvCreateReferral, tvLogin, tvSignUp, tvUserName, tvcollection;
    private ImageView imgDot;
    public static String customerId = "";
    public static String GroupId = "";
    private SharedPreferences sharedPreferences;
    private LoginResponse loginResponse;
    private LinearLayout linContainer,linSubContainer;
    private LinearLayout linInventory, linCollection, linOrders, linTransaction, linDownload, linPolicies, linCart, linFAQ, linContactUs, linLogout, linMyStock;
    private NestedScrollView scrollViewCollection;
    private boolean doubleBackToExitPressedOnce = false;
//    private RelativeLayout relCart;
    private RelativeLayout RLUser;
    private TextView tvDownloadCount, tvCartCount, tvCartCountHome;
    private ThemePreferences themePreferences;
    private View viewBottomMyStock,
            viewBottomOrder,
            viewBottomTransaction,
            viewBottomDownload,
            viewBottomPolicies;

    private View loginview;

    private DrawerLayout drawer;
    SpaceNavigationView spaceNavigationView;
    private boolean isOnce = true;

    private String currentVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawer = findViewById(R.id.drawer_layout);

        spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        fragment = new HomeFrg();
        replaceFragment(fragment);

//        spaceNavigationView.

        //Bottom bar
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_compare));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_referral));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_profile));

        spaceNavigationView.showIconOnly();

        //Set bottom menu onClick listener
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                startNewActivity(CartAct.class);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.e("item index", "-------------" + itemIndex);
                if (itemIndex == 0) {
                    if (isOnce){
                        fragment = new HomeFrg();
                        replaceFragment(fragment);
                    }
                } else if (itemIndex == 1) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(TransactionAct.class);
                    }
                } else if (itemIndex == 2) {

                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(CreateReferralAct.class);
                    }
                } else if (itemIndex == 3) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(EditProfileAct.class);
                    }
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 0) {
//                    fragment = new HomeFrg();
//                    replaceFragment(fragment);
                } else if (itemIndex == 1) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(TransactionAct.class);
                    }
                } else if (itemIndex == 2) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(OrderTabActivity.class);
                    }
                } else if (itemIndex == 3) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        showSnackBar(drawer);
                    } else {
                        startNewActivity(EditProfileAct.class);
                    }
                }
            }
        });

//        Set onLongClick listener
        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
            }
            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
            }
        });
        themePreferences = new ThemePreferences(MainActivity.this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersion = pInfo.versionName;
            AppLogger.e("AppCurrentVersion","-----" + currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //used for display Qr Code Scanning
        MenuItem item1 = menu.findItem(R.id.action_Scancode);
        if(!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            item1.setVisible(true);
        }else{
            item1.setVisible(false);
        }
        //using for theme change
        MenuItem item = (MenuItem) menu.findItem(R.id.switchId);
        item.setActionView(R.layout.switch_layout);
        Switch switchAB = item
                .getActionView().findViewById(R.id.switchAB);
        switchAB.setChecked(false);
        if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            switchAB.setChecked(false);
        } else if (themePreferences.getTheme().equalsIgnoreCase("black")){
            switchAB.setChecked(true);
        } else {
            switchAB.setChecked(false);
        }

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    themePreferences.saveTheme("black");
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                } else {
                    themePreferences.saveTheme("white");
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startNewActivity(SearchAct.class);
            return true;
        }else if(id == R.id.action_Scancode){
            startNewActivity(QRCodeScanningAct.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
    }

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_logo);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu_new);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        tvMyAccount = navigationView.findViewById(R.id.tvMyAccount);
        tvCreateReferral = navigationView.findViewById(R.id.tvCreateReferral);
        tvLogin = headerLayout.findViewById(R.id.tvLogin);
        tvSignUp = headerLayout.findViewById(R.id.tvSignUp);
        tvUserName = headerLayout.findViewById(R.id.tvUserName);
//        relCart = findViewById(R.id.relCart);
//        relCart.setVisibility(View.GONE);
        RLUser = headerLayout.findViewById(R.id.RLUser);
        imgDot = headerLayout.findViewById(R.id.imgDot);
        ImageView imgArrow = headerLayout.findViewById(R.id.imgArrow);
        SimpleDraweeView imgUser = headerLayout.findViewById(R.id.imgUser);
        linContainer = headerLayout.findViewById(R.id.linContainer);
//        linSubContainer = headerLayout.findViewById(R.id.linSubContainer);
        scrollViewCollection = headerLayout.findViewById(R.id.scrollViewCollection);

        linInventory = headerLayout.findViewById(R.id.linInventory);
        linCollection = headerLayout.findViewById(R.id.linCollection);
        tvcollection = headerLayout.findViewById(R.id.tvcollection);
        linOrders = headerLayout.findViewById(R.id.linOrders);
        linTransaction = headerLayout.findViewById(R.id.linTransaction);
        linDownload = headerLayout.findViewById(R.id.linDownload);
        linPolicies = headerLayout.findViewById(R.id.linPolicies);
//        linCart = headerLayout.findViewById(R.id.linCart);
        linFAQ = headerLayout.findViewById(R.id.linFAQ);
        linContactUs = headerLayout.findViewById(R.id.linContactUs);
        linLogout = headerLayout.findViewById(R.id.linLogout);
//        linMyStock = headerLayout.findViewById(R.id.linMyStock);

        tvDownloadCount = headerLayout.findViewById(R.id.tvDownloadCount);
//        tvCartCount = headerLayout.findViewById(R.id.tvCartCount);
        tvCartCountHome = findViewById(R.id.tvCartCountHome);

//        viewBottomMyStock = headerLayout.findViewById(R.id.viewBottomMyStock);
        viewBottomOrder = headerLayout.findViewById(R.id.viewBottomOrder);
        viewBottomTransaction = headerLayout.findViewById(R.id.viewBottomTransaction);
        viewBottomDownload = headerLayout.findViewById(R.id.viewBottomDownload);
        viewBottomPolicies = headerLayout.findViewById(R.id.viewBottomPolicies);

        loginview = headerLayout.findViewById(R.id.loginview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void postInitView() {
        sharedPreferences = new SharedPreferences(MainActivity.this);
        if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            Gson gson = new Gson();
            loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
            customerId = loginResponse.getData().getEntityId();
            GroupId =  loginResponse.getData().getGroupId();
        }

        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            loginview.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            tvSignUp.setVisibility(View.VISIBLE);
            imgDot.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.GONE);
            linInventory.setVisibility(View.GONE);
        } else {
            loginview.setVisibility(View.VISIBLE);
            linInventory.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            tvSignUp.setVisibility(View.GONE);
            imgDot.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText(loginResponse.getData().getFirstname() + " " + loginResponse.getData().getLastname());

            if (loginResponse.getCustomerRole().equalsIgnoreCase("Referral")) {
                tvCreateReferral.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void addListener() {
        tvMyAccount.setOnClickListener(this);
        tvCreateReferral.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvUserName.setOnClickListener(this);
        RLUser.setOnClickListener(this);

        linInventory.setOnClickListener(this);
        linCollection.setOnClickListener(this);
        linOrders.setOnClickListener(this);
        linTransaction.setOnClickListener(this);
        linDownload.setOnClickListener(this);
        linPolicies.setOnClickListener(this);
//        linCart.setOnClickListener(this);
        linFAQ.setOnClickListener(this);
        linContactUs.setOnClickListener(this);
        linLogout.setOnClickListener(this);
//        linMyStock.setOnClickListener(this);

//        buttonSignOut.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        addHeader();
//        addSubCategory();
        Checkversion();
    }

    private void Checkversion() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.getAppVersion();
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                String status = null, message = null;

                if(response.isSuccessful()){

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        if(status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)){
                            String playStoreVersion = jsonObject.getString("app_version");
                            AppLogger.e("LatestVersion","----"+ playStoreVersion);
                            //noinspection StatementWithEmptyBody,StatementWithEmptyBody
                            if (!playStoreVersion.equalsIgnoreCase(currentVersion)) {
//                            if (!playStoreVersion.equalsIgnoreCase("1.0")) {
                                final AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
//                                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
                                    builder = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                                } else {
                                    builder = new AlertDialog.Builder(MainActivity.this);
                                }
                                builder.setCancelable(false);
                                builder.setTitle("Alert")
                                        .setMessage("This apps latest update version available in play store, so please download it.")
                                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete]

                                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                } catch (android.content.ActivityNotFoundException anfe) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                }
                                            }
                                        })
                                        .setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                                dialog.dismiss();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();

//                                new IOSDialog.Builder(MainActivity.this)
//                                        .setTitle(getString(R.string.Alert))
//                                        .setMessage("This apps latest update version available in play store, so please download it.")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                                                try {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                                } catch (android.content.ActivityNotFoundException anfe) {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                                                }
//                                            }
//                                        })
//                                        .setNegativeButton("Remind me later",new DialogInterface.OnClickListener(){
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                // do nothing
//                                                dialog.dismiss();
//                                            }
//                                        })
//                                        .show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMyAccount:
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    Snackbar snackBar = Snackbar
                            .make(v, "Please first login", Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loginFlag = 0;
                                    Intent intent = new Intent(MainActivity.this, LoginAct.class);
                                    startActivity(intent);
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
                    TextView textView = snackBarView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackBar.show();

                } else {
                    startNewActivity(EditProfileAct.class);
                    onBackPressed();
                }
                break;
            case R.id.tvCreateReferral:
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    Snackbar snackBar = Snackbar
                            .make(v, "Please first login", Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loginFlag = 0;
                                    Intent intent = new Intent(MainActivity.this, LoginAct.class);
                                    startActivity(intent);
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
                    TextView textView = snackBarView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackBar.show();

                } else {
                    startNewActivity(CreateReferralAct.class);
                    onBackPressed();
                }
                break;

            case R.id.tvLogin:
                startNewActivity(LoginAct.class);
                onBackPressed();
                break;

            case R.id.tvSignUp:
                startNewActivity(SignUpAct.class);
                onBackPressed();
                break;

            case R.id.RLUser:
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {

                }else {
                    startNewActivity(EditProfileAct.class);
                    onBackPressed();
                }
                break;

            case R.id.tvUserName:
                startNewActivity(EditProfileAct.class);
                onBackPressed();
                break;

            case R.id.linInventory:
//                Intent intent = new Intent(MainActivity.this, InventoryListAct.class);
//                startActivity(intent);
                fragment = new HomeFrg();
                replaceFragment(fragment);
                onBackPressed();
                break;

            case R.id.linCollection:

                if (scrollViewCollection.getVisibility() == View.VISIBLE) {
                    scrollViewCollection.setVisibility(View.GONE);
                    tvcollection.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right_black_24dp, 0);
                } else {
                    scrollViewCollection.setVisibility(View.VISIBLE);
                    tvcollection.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
                }
                break;

            case R.id.linOrders:
                startNewActivity(OrderTabActivity.class);
                onBackPressed();
                break;

            case R.id.linTransaction:
                startNewActivity(TransactionAct.class);
                onBackPressed();
                break;

            case R.id.linDownload:
                startNewActivity(DownloadAct.class);
                onBackPressed();
                break;

            case R.id.linPolicies:
                startNewActivity(PolicyAct.class);
                onBackPressed();
                break;

//            case R.id.linCart:
//                startNewActivity(CartAct.class);
//                onBackPressed();
//                break;

            case R.id.linFAQ:
                break;

            case R.id.linContactUs:
                startNewActivity(ContactUsAct.class);
                onBackPressed();
                break;

//            case R.id.linMyStock:
//                startNewActivity(MyStockAct.class);
//                onBackPressed();
//                break;

            case R.id.linLogout:
                onBackPressed();
//                LogoutDialogClass logoutDialogClass = new LogoutDialogClass(MainActivity.this);
//                logoutDialogClass.show();
//                Objects.requireNonNull(logoutDialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                Objects.requireNonNull(logoutDialogClass.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

           /*     new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.logout))
                        .setMessage(R.string.please_logout)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();   */

                new IOSDialog.Builder(this)
                        .setTitle(getString(R.string.logout))
                        .setMessage(R.string.please_logout)
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
        }
    }

    private void logout() {
        SharedPreferences sharedPreferences = new SharedPreferences(this);
        sharedPreferences.saveLoginData("");
        sharedPreferences.saveShipping("");
        sharedPreferences.saveBillingAddress("");
//        sharedPreferences.saveRemember("");
        customerId="";
        this.startActivity(new Intent(this,LoginAct.class));
//        activity.finishAffinity();
    }

    @SuppressLint("InflateParams")
    private void inflateTextViews(int length, final List<HeaderItem.Datum> list) {
        View textLayout;
        for (int i = 0; i < length; i++) {
            textLayout = getLayoutInflater().inflate(R.layout.drawer_our_collection_item, null);
            TextView tvCollectionItem = textLayout.findViewById(R.id.tvCollectionItem);
            tvCollectionItem.setText(list.get(i).getName());

            final int finalI = i;
            tvCollectionItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ListAct.class);
                    intent.putExtra(AppConstants.ID, list.get(finalI).getEntityId());
                    intent.putExtra(AppConstants.NAME, list.get(finalI).getName());
                    intent.putExtra(AppConstants.bannerListCheck, "");
                    startNewActivityWithIntent(intent);
                }
            });
            linContainer.addView(textLayout);
        }
    }

//    @SuppressLint("InflateParams")
//    private void inflateTextViews(int length, final List<SubcategoryItem.Datum> list) {
//        View textLayout,subcategoryLayout;
//        for (int i = 0; i < length; i++) {
//            textLayout = getLayoutInflater().inflate(R.layout.drawer_our_collection_item, null);
//            TextView tvCollectionItem = textLayout.findViewById(R.id.tvCollectionItem);
//            tvCollectionItem.setText(list.get(i).getName());
//            AppLogger.e("","----" + list.get(i).toString());
//
//            subcategoryLayout = getLayoutInflater().inflate(R.layout.drawer_collection_subcategory_item,null);
//            final TextView tvCollectionSubCategoryItem = subcategoryLayout.findViewById(R.id.tvCollectionSubCategoryItem);
//
//            final int finalI = i;
//            tvCollectionItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(MainActivity.this, ListAct.class);
////                    intent.putExtra(AppConstants.ID, list.get(finalI).getId());
////                    intent.putExtra(AppConstants.NAME, list.get(finalI).getName());
////                    intent.putExtra(AppConstants.bannerListCheck, "");
////                    startNewActivityWithIntent(intent);
//                    for(int j = 0; j < list.get(finalI).getSubcategories().size(); j++){
//                        AppLogger.e("MainSubcategory","---"+list.get(finalI).getSubcategories().get(j).getName());
//                        tvCollectionSubCategoryItem.setText(list.get(finalI).getSubcategories().get(j).getName());
//                    }
//                }
//            });
//            linContainer.addView(textLayout);
//            linSubContainer.addView(subcategoryLayout);
//        }
//    }

//    private void addSubCategory(){
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        Call<SubcategoryItem> callApi = apiInterface.getSubCategory();
//        callApi.enqueue(new Callback<SubcategoryItem>() {
//            @Override
//            public void onResponse(Call<SubcategoryItem> call, Response<SubcategoryItem> response) {
//                assert response.body() != null;
//                if(response.isSuccessful()) {
//                    inflateTextViews(response.body().getData().size(), response.body().getData());
//                }else {
//                    CommonUtils.showWarningToast(MainActivity.this,"Something went wrong! Please try later.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SubcategoryItem> call, Throwable t) {
//            }
//        });
//    }

    private void addHeader() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<HeaderItem> callApi = apiInterface.getHeader();
        callApi.enqueue(new Callback<HeaderItem>() {
            @Override
            public void onResponse(@NonNull Call<HeaderItem> call, @NonNull Response<HeaderItem> response) {
                assert response.body() != null;
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                inflateTextViews(response.body().getData().size(), response.body().getData());
            }

            @Override
            public void onFailure(@NonNull Call<HeaderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {  //if label is true than exit from the app
                finishAffinity();
//                super.onBackPressed();        //before code
//                return;
            }

            this.doubleBackToExitPressedOnce = true;
            CommonUtils.showWarningToast(MainActivity.this, getString(R.string.double_back));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        invalidateOptionsMenu();    //Add for QR CODE Scan Visibility Update After Logout

        isOnce = false;
        spaceNavigationView.changeCurrentItem(0);
        postInitView();
        mapFilter.clear();
        selectFilter.clear();
        filterFlag = 0;

        if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            getCount();
            linOrders.setVisibility(View.VISIBLE);
            linTransaction.setVisibility(View.VISIBLE);
            linDownload.setVisibility(View.VISIBLE);
//            linMyStock.setVisibility(View.VISIBLE);
            linPolicies.setVisibility(View.VISIBLE);
            linLogout.setVisibility(View.VISIBLE);

//            viewBottomMyStock.setVisibility(View.VISIBLE);
            viewBottomOrder.setVisibility(View.VISIBLE);
            viewBottomTransaction.setVisibility(View.VISIBLE);
            viewBottomDownload.setVisibility(View.VISIBLE);
            viewBottomPolicies.setVisibility(View.VISIBLE);

        } else {
            linOrders.setVisibility(View.GONE);
            linTransaction.setVisibility(View.GONE);
            linDownload.setVisibility(View.GONE);
//            linMyStock.setVisibility(View.GONE);
            linPolicies.setVisibility(View.GONE);
            linLogout.setVisibility(View.GONE);

//            viewBottomMyStock.setVisibility(View.GONE);
            viewBottomOrder.setVisibility(View.GONE);
            viewBottomTransaction.setVisibility(View.GONE);
            viewBottomDownload.setVisibility(View.GONE);
            viewBottomPolicies.setVisibility(View.GONE);

            setupBadge();
            if (cartCount == 0) {
//                tvCartCount.setVisibility(View.GONE);
                tvCartCountHome.setVisibility(View.GONE);
            } else {
//                tvCartCount.setVisibility(View.VISIBLE);
                tvCartCountHome.setVisibility(View.VISIBLE);
//                tvCartCount.setText(String.valueOf(cartCount));
                tvCartCountHome.setText(String.valueOf(cartCount));
            }
        }
    }

    private void getCount() {
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

                        if (jsonObject.getInt("total_qty") != 0) {
//                            tvCartCount.setVisibility(View.VISIBLE);
                            tvCartCountHome.setVisibility(View.VISIBLE);
                            cartCount = jsonObject.getInt("total_qty");
//                            tvCartCount.setText(String.valueOf(jsonObject.getInt("total_qty")));
                            tvCartCountHome.setText(String.valueOf(jsonObject.getInt("total_qty")));
                        } else {
//                            tvCartCount.setVisibility(View.GONE);
                            tvCartCountHome.setVisibility(View.GONE);
                        }

                        if (jsonObject.getInt("download_count") != 0) {
                            if (jsonObject.getInt("download_count") > 99) {

                                tvDownloadCount.setVisibility(View.VISIBLE);
                                tvDownloadCount.setText("99+");
                                downloadCount = jsonObject.getInt("download_count");
                            } else {
                                tvDownloadCount.setVisibility(View.VISIBLE);
                                tvDownloadCount.setText(String.valueOf(jsonObject.getInt("download_count")));
                                downloadCount = jsonObject.getInt("download_count");
                            }
                        } else {
                            tvDownloadCount.setVisibility(View.GONE);
                        }
                    } else {
//                        tvCartCount.setVisibility(View.GONE);
                        tvDownloadCount.setVisibility(View.GONE);
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

    private void showSnackBar(View v) {
        Snackbar snackBar = Snackbar
                .make(v, "Please first login", Snackbar.LENGTH_LONG)
                .setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginFlag = 0;
                        Intent intent = new Intent(MainActivity.this, LoginAct.class);
                        startActivity(intent);
                    }
                });
        snackBar.setActionTextColor(Color.RED);
        View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackBar.show();
    }
}
