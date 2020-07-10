package com.dealermela.other.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.home.adapter.BestCategoryAdapter;
import com.dealermela.home.adapter.HeaderMenuAdapter;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.util.AppConstants.RESPONSE;

public class SearchAct extends DealerMelaBaseActivity implements View.OnClickListener{

    private RecyclerView recycleViewHeader;
    private EditText edSearch;
    private ImageView imgclose;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        recycleViewHeader = findViewById(R.id.recycleViewHeader);
        edSearch=findViewById(R.id.edSearch);
        imgclose=findViewById(R.id.imgclose);
    }

    @Override
    public void initView() {
        bindToolBar("");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchAct.this, 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewHeader.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
        imgclose.setOnClickListener(this);
        showcloseimg();

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                showcloseimg();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showcloseimg();
            }

            @Override
            public void afterTextChanged(Editable s) {
                showcloseimg();
            }
        });

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showcloseimg();
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    //do something
//                    hideKeyboard(SearchAct.this);
                    if (!Validator.checkEmpty(edSearch, getString(R.string.please_enter_string))) {

                    }
                    else{
                        Intent intent=new Intent(SearchAct.this, ListAct.class);
                        intent.putExtra(AppConstants.ID, "search");
                        intent.putExtra(AppConstants.NAME, edSearch.getText().toString());
                        intent.putExtra(AppConstants.bannerListCheck, "");
                        startNewActivityWithIntent(intent);
                        AppLogger.e("search", edSearch.getText().toString());
                    }

                }
                return false;
            }
        });
    }

    @Override
    public void loadData() {
        addHeader();
        showcloseimg();
    }

    private void showcloseimg() {
        if(!edSearch.getText().toString().isEmpty()){
            imgclose.setVisibility(View.VISIBLE);
        }else{
            imgclose.setVisibility(View.GONE);
        }
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

    private void addHeader() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<HeaderItem> callApi = apiInterface.getHeader();
        callApi.enqueue(new Callback<HeaderItem>() {
            @Override
            public void onResponse(@NonNull Call<HeaderItem> call, @NonNull Response<HeaderItem> response) {
                assert response.body() != null;
                Log.e(RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

                if (response.isSuccessful()) {
                    HeaderMenuAdapter headerMenuAdapter = new HeaderMenuAdapter(SearchAct.this, response.body().getData());
                    recycleViewHeader.setAdapter(headerMenuAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<HeaderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgclose:
                edSearch.getText().clear();
            break;
        }
    }

//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }
}
