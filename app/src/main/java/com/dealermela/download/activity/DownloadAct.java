package com.dealermela.download.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.download.adapter.DownloadProductAdapter;
import com.dealermela.download.model.DownloadItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.DownloadImages;
import com.dealermela.util.NetworkUtils;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class DownloadAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private CheckBox checkBoxSelectAll;
    private RecyclerView recycleViewDownloadProducts;
    private Button btnDownload, btnDeleteAll;
    private ProgressBar progressBarBottom, progressBarCenter;
    private LinearLayout linNoData;

    //page count
    private int page_count = 1;
    private boolean flag_scroll = false;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private final int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    private GridLayoutManager gridLayout;
    private DownloadProductAdapter downloadProductAdapter;
    private List<DownloadItem.Detail> detailList;

    public String flag = "1";
    private KProgressHUD hud;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private ArrayList<Integer> integerArrayList;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_download;
    }

    @Override
    public void init() {
        detailList = new ArrayList<>();
    }

    @Override
    public void initView() {
        bindToolBar("Download List");
        radioGroup = findViewById(R.id.radioGroupFilter);
        checkBoxSelectAll = findViewById(R.id.checkBoxSelectAll);
        recycleViewDownloadProducts = findViewById(R.id.recycleViewDownloadProducts);
        btnDownload = findViewById(R.id.btnDownload);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        progressBarBottom = findViewById(R.id.progressBarBottom);
        progressBarCenter = findViewById(R.id.progressBarCenter);
        linNoData = findViewById(R.id.linNoData);
        progressBarBottom.setVisibility(View.GONE);
    }

    @Override
    public void postInitView() {
        //Set layout diamond adapter
        gridLayout = new GridLayoutManager(DownloadAct.this, 1);
        gridLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewDownloadProducts.setLayoutManager(gridLayout);
    }

    @Override
    public void addListener() {
        btnDownload.setOnClickListener(this);
        btnDeleteAll.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (rb.getText().equals("Without Price")) {
                    flag = "0";
                } else {
                    flag = "1";
                }
            }
        });

        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int j = 0; j < downloadProductAdapter.itemArrayList.size(); j++) {
                            downloadProductAdapter.itemArrayList.get(j).setSelected(true);
                            downloadProductAdapter.updatecheckbox(j);
                    }
                    AppLogger.e("select all", "----checked");
                } else {
                    for (int j = 0; j < downloadProductAdapter.itemArrayList.size(); j++) {
                        downloadProductAdapter.itemArrayList.get(j).setSelected(false);
                        downloadProductAdapter.updatecheckbox(j);
                    }
                    AppLogger.e("un select all", "----un checked");
                }
            }
        });

        recycleViewDownloadProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                            @Override
                                                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                                super.onScrolled(recyclerView, dx, dy);

                                                                visibleItemCount = recyclerView.getChildCount();
                                                                totalItemCount = gridLayout.getItemCount();
                                                                firstVisibleItem = gridLayout.findFirstVisibleItemPosition();

                                                                if (flag_scroll) {
                                                                    AppLogger.e("flag-Scroll", flag_scroll + "");
                                                                } else {
                                                                    if (loading) {
                                                                        AppLogger.e("flag-Loading", loading + "");
                                                                        if (totalItemCount > previousTotal) {
                                                                            loading = false;
                                                                            previousTotal = totalItemCount;
                                                                            //Log.e("flag-IF", (totalItemCount > previousTotal) + "");
                                                                        }
                                                                    }
                                                                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                                                                        // End has been reached
                                                                        // Do something
                                                                        AppLogger.e("flag-Loading_second_if", loading + "");
                                                                        if (NetworkUtils.isNetworkConnected(DownloadAct.this)) {
                                                                            AppLogger.e("total count", "--------------------" + page_count);
                                                                            page_count++;
                                                                            getDownloadProductList(customerId, String.valueOf(page_count));
                                                                        } else {
                                                                            //internet not connected
                                                                            AppLogger.e("connection", "-------internet connection is off");
                                                                        }
                                                                        loading = true;
                                                                    }
                                                                }
                                                            }
                                                        }
        );
    }

    @Override
    public void loadData() {
        downloadProductAdapter = new DownloadProductAdapter(DownloadAct.this, detailList);
        recycleViewDownloadProducts.setAdapter(downloadProductAdapter);
        getDownloadProductList(customerId, String.valueOf(page_count));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        // Code for above or equal 23 API Oriented Device
                        // Your Permission granted already .Do next code
                        AppLogger.e("init if ", "----call");
                        downloadAll();

                    } else {
                        AppLogger.e("init if else ", "----call");
                        requestPermission(); // Code for permission
                    }
                } else {
                    AppLogger.e("init else ", "----call");
                    // Code for Below 23 API Oriented Device
                    // Do next code
                    downloadAll();
                }
                break;

            case R.id.btnDeleteAll:
                deleteAll();
                break;
        }
    }

    private void getDownloadProductList(String customerId, String page) {

        if (page_count == 1) {
            btnDeleteAll.setVisibility(View.INVISIBLE);
            btnDownload.setVisibility(View.INVISIBLE);
            progressBarCenter.setVisibility(View.VISIBLE);
        } else {
            progressBarBottom.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<DownloadItem> callApi = apiInterface.getDownloadProductList(customerId, page);
        callApi.enqueue(new Callback<DownloadItem>() {
            @Override
            public void onResponse(@NonNull Call<DownloadItem> call, @NonNull Response<DownloadItem> response) {
                assert response.body() != null;

                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                    btnDeleteAll.setVisibility(View.VISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        detailList.addAll(response.body().getDetail());
                        downloadProductAdapter.notifyDataSetChanged();
                    } else {
                        if (detailList.isEmpty()) {
                            linNoData.setVisibility(View.VISIBLE);
                            btnDeleteAll.setVisibility(View.INVISIBLE);
                            btnDownload.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DownloadItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linNoData.setVisibility(View.VISIBLE);
                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
            }
        });
    }

    private void deleteAllProduct(String customerId, String productId) {
        //show progress
        hud = KProgressHUD.create(DownloadAct.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        AppLogger.e("customerId", customerId);
        AppLogger.e("productId", productId);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.deleteAllProductImage(customerId, productId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                hud.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//                        downloadProductAdapter.selectionItemPosition.clear();
//                        downloadProductAdapter.deleteItems.clear();
                        checkBoxSelectAll.setChecked(false);
                        if (integerArrayList.isEmpty()){

                            finish();
                        }else {
                            Collections.reverse(integerArrayList);
                            for (int i = 0; i < integerArrayList.size(); i++) {
                                downloadProductAdapter.removeAt(integerArrayList.get(i));

                                if (i == integerArrayList.size() - 1) {
                                    downloadProductAdapter.notifyDataSetChanged();
                                }

                            }
                        }

                        CommonUtils.showSuccessToast(DownloadAct.this, " Product(s) deleted successfully");
                      /*  page_count = 1;
                        detailList = new ArrayList<>();
                        loadData();*/
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

    private void downloadAllProduct(String customerId, String productId) {
        //show progress
        showProgressDialog(AppConstants.PLEASE_WAIT);
        AppLogger.e("customerId", customerId);
        AppLogger.e("productId", productId);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.downloadAllProductImage(customerId, productId, flag);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
               hideProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                        String rootDir = Environment.getExternalStorageDirectory().toString();
                        // Set the URL to download image
                        String photoPictureDirectoryPath = rootDir + "/DownloadAllImage/";

                        JSONArray jsonArray = jsonObject.getJSONArray("image");
                        // Call this method in a loop to DOwnLoad Multiple Images.
                        AppLogger.e("length", "" + (jsonArray.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            AppLogger.e("image", jsonArray.get(i).toString());

                            AppLogger.e("i", "" + i);
                            new DownloadImages(DownloadAct.this, jsonArray.get(i).toString().trim(), photoPictureDirectoryPath);
//                            if (i == jsonArray.length() - 2) {
                                CommonUtils.showSuccessToast(DownloadAct.this, "All image saved in gallery");
//                            }
//                            if(!detailList.isEmpty()) {       //apply this condition For when product downloaded it remove from the list
//                                detailList.remove(i);
//                            }
//                            checkBoxSelectAll.setSelected(false);
                            checkBoxSelectAll.setChecked(false);
                        }

//                        Collections.reverse(integerArrayList);
//                        for (int i = 0; i < integerArrayList.size(); i++) {
//                            downloadProductAdapter.removeAt(integerArrayList.get(i));
//
//                            if (i == integerArrayList.size() - 1) {
//                                downloadProductAdapter.notifyDataSetChanged();
//                            }
//
//                        }
//                        downloadProductAdapter.selectionItemPosition.clear();
//                        downloadProductAdapter.deleteItems.clear();
                        downloadProductAdapter.updateData();
//                        downloadProductAdapter.notifyDataSetChanged();

                      /*  page_count = 1;
                        detailList = new ArrayList<>();
                        loadData();*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                hud.dismiss();

            }
        });
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DownloadAct.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(DownloadAct.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DownloadAct.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DownloadAct.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppLogger.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    AppLogger.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    private void downloadAll() {
        final StringBuilder listString = new StringBuilder();
        for (int j = 0; j < downloadProductAdapter.itemArrayList.size(); j++) {
            if (downloadProductAdapter.itemArrayList.get(j).isSelected()) {
                listString.append(downloadProductAdapter.itemArrayList.get(j).getProductId()).append(",");
                AppLogger.e("listString", "----------" + listString);
                AppLogger.e("position", "----------" + j);
                downloadProductAdapter.updateDownloadLoad(j);
            }
        }

        if (listString.toString().equals("")) {

            new IOSDialog.Builder(DownloadAct.this)
                    .setTitle(getString(R.string.Alert))
                    .setMessage(R.string.Download_alert)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

            ////add this for minimum select 1 product from the list for download
//            CommonUtils.showWarningToast(DownloadAct.this, "Please select atleast one product for download.");

        } else {
            new IOSDialog.Builder(DownloadAct.this)
                    .setTitle(getString(R.string.Download))
                    .setMessage("Are you sure to download selected products?")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AppLogger.e("string data", "------" + listString);

                            listString.deleteCharAt(listString.length() - 1);
                            AppLogger.e("list string","----------"+listString);
                            downloadAllProduct(customerId, listString.toString());
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }


       /* AlertDialog.Builder alertDownloadAll = new AlertDialog.Builder(DownloadAct.this, R.style.AppCompatAlertDialogStyle);
        alertDownloadAll.setTitle("DOWNLOAD");
        alertDownloadAll.setMessage("Are you sure you want to download all selected item.");
        alertDownloadAll.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
                AppLogger.e("string data", "------" + listString);

                if (listString.toString().equals("")) {
                    CommonUtils.showToast(DownloadAct.this, "Please select at least one item after download all.");
                } else {
                    downloadAllProduct(customerId, listString.toString());
                }

            }
        });
        alertDownloadAll.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alertDownloadAll.show();*/
    }

    private void deleteAll() {

        integerArrayList = new ArrayList<>();
        final StringBuilder listString = new StringBuilder();

        for (int j = 0; j < downloadProductAdapter.itemArrayList.size(); j++) {
            if (downloadProductAdapter.itemArrayList.get(j).isSelected()) {
                listString.append(downloadProductAdapter.itemArrayList.get(j).getProductId()).append(",");
                AppLogger.e("position", "----------" + j);
                integerArrayList.add(j);
//                        downloadProductAdapter.removeAt(j);
            }
        }

        if (listString.toString().equals("")) {
            new IOSDialog.Builder(DownloadAct.this)
                    .setTitle(getString(R.string.Alert))
                    .setMessage(R.string.Delete_alert)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            new IOSDialog.Builder(DownloadAct.this)
                    .setTitle(getString(R.string.delete))
                    .setMessage("Are you sure to delete selected products?")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AppLogger.e("string data", "------" + listString);

                                deleteAllProduct(customerId, listString.toString());
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

      /*  new IOSDialog.Builder(DownloadAct.this)
                .setTitle(getString(R.string.delete))
                .setMessage("Are u sure u want delete selected item?")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppLogger.e("string data", "------" + listString);

                        if (listString.toString().equals("")) {
                            CommonUtils.showToast(DownloadAct.this, "Please select at least one item after delete all.");
                        } else {
                            deleteAllProduct(customerId, listString.toString());
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();*/

       /* AlertDialog.Builder alertDeleteAll = new AlertDialog.Builder(DownloadAct.this, R.style.AppCompatAlertDialogStyle);
        alertDeleteAll.setTitle(getString(R.string.delete));
        alertDeleteAll.setMessage("Are you sure you want to delete selected item.");
        alertDeleteAll.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
                AppLogger.e("string data", "------" + listString);

                if (listString.toString().equals("")) {
                    CommonUtils.showToast(DownloadAct.this, "Please select at least one item after delete all.");
                } else {
                    deleteAllProduct(customerId, listString.toString());
                }
            }
        });

        alertDeleteAll.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alertDeleteAll.show();
*/
    }
}



