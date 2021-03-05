package com.dealermela.other.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.dbhelper.FilterSearchDatabase;
import com.dealermela.dbhelper.SuggestionsDatabase;
import com.dealermela.home.adapter.HeaderMenuAdapter;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.other.adapter.SearchAdapter;
import com.dealermela.other.adapter.SuggestionSimpleCursorAdapter;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.ThemePreferences;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.util.AppConstants.RESPONSE;

public class NewSearchAct extends DealerMelaBaseActivity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, View.OnClickListener {

    public static SuggestionsDatabase database;
    public static SearchView searchView;
    private RecyclerView recycleViewHeader,lastSearchRecyclerView;
    private Cursor cursor;
    private ArrayList<String> searchlist;
    private long insertresult;
    private TextView tvSearchHistoryTitle;
    private ThemePreferences themePreferences;
    public static int searchbackflag = 0 ;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_search;
    }

    @Override
    public void init() {
        database = new SuggestionsDatabase(this);

        searchView = (SearchView) findViewById(R.id.edSearch);

//        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(getResources().getColor(R.color.white));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);

        recycleViewHeader = findViewById(R.id.recycleViewHeader);
        lastSearchRecyclerView = findViewById(R.id.lastSearchRecyclerView);
        tvSearchHistoryTitle = findViewById(R.id.tvSearchHistoryTitle);

//        val backgroundView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate) as View
//        backgroundView.background = null;
//
//        View searchplate = (View)mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
//        searchplate.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.another_color) ,PorterDuff.Mode.MULTIPLY);

        themePreferences = new ThemePreferences(NewSearchAct.this);
        if(themePreferences.getTheme().equalsIgnoreCase("black")){
            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search) + "</font>"));
        }else {
            searchView.setQueryHint(Html.fromHtml("<font color = #949393>" + getResources().getString(R.string.search) + "</font>"));
        }
//        searchView.setQuery(Html.fromHtml("<font>" + getResources().getColor(R.color.transaction_line_color) + "</font>"),false);
    }

    @Override
    public void initView() {
        bindToolBar("");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(NewSearchAct.this, 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewHeader.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(NewSearchAct.this,1);
        gridLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        lastSearchRecyclerView.setLayoutManager(gridLayoutManager1);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {
        if(NetworkUtils.isNetworkConnected(NewSearchAct.this)) {
            addHeader();
            showPreviousSearch();
        }
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        SQLiteCursor cursor = (SQLiteCursor) searchView.getSuggestionsAdapter().getItem(position);
        int indexColumnSuggestion = cursor.getColumnIndex( SuggestionsDatabase.FIELD_SUGGESTION);
        searchView.setQuery(cursor.getString(indexColumnSuggestion), false);

        Cursor c = database.compareSearch(searchView.getQuery().toString());
        AppLogger.e("cursor1 total count","-----"+ c.getCount());
        AppLogger.e("Compare result Which is going to be delete : ","---------" + c.getString(c.getColumnIndex(SuggestionsDatabase.FIELD_ID)));
        if(c.getCount() > 0){
            String id = c.getString(c.getColumnIndex(SuggestionsDatabase.FIELD_ID));
            boolean result = database.deleteItem(id);
            if(result == true) {
                insertresult = database.insertSuggestion(searchView.getQuery().toString());
                AppLogger.e("Which row inserted with new index ", "----------" + insertresult);
            }
        }

        Intent intent=new Intent(NewSearchAct.this, ListAct.class);
        intent.putExtra(AppConstants.ID, "search");
        intent.putExtra(AppConstants.NAME, searchView.getQuery().toString());
        intent.putExtra(AppConstants.bannerListCheck, "");
        startNewActivityWithIntent(intent);
        AppLogger.e("search", searchView.getQuery().toString());

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cursor = database.getAllValues();
        if(cursor.getCount() > 0){
            if(cursor.getCount() > 4){
                AppLogger.e("which data get if first inserted or last one ??","-----" + cursor.getString(cursor.getColumnIndex(SuggestionsDatabase.FIELD_ID)));
                boolean result = database.deleteItem(cursor.getString(cursor.getColumnIndex(SuggestionsDatabase.FIELD_ID)));
                if(result == true ){
                    insertresult = database.insertSuggestion(query);
                    AppLogger.e("Which row inserted with new index ","----------" + insertresult);
                }
            }else {
                insertresult = database.insertSuggestion(query);
                AppLogger.e("Which row inserted with new index ","----------" + insertresult);
            }
        } else {
            insertresult = database.insertSuggestion(query);
            AppLogger.e("Which row inserted with new index ","----------" + insertresult);
        }

        Intent intent=new Intent(NewSearchAct.this, ListAct.class);
        intent.putExtra(AppConstants.ID, "search");
        intent.putExtra(AppConstants.NAME, query);
        intent.putExtra(AppConstants.bannerListCheck, "");
        startNewActivityWithIntent(intent);
        AppLogger.e("search", query);

//        long result = database.insertSuggestion(query);
        return insertresult != -1;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Cursor cursor = database.getSuggestions(newText);
        if(cursor.getCount() != 0)
        {
            String[] columns = new String[] {SuggestionsDatabase.FIELD_SUGGESTION };
            int[] columnTextId = new int[] { android.R.id.text1};

            SuggestionSimpleCursorAdapter simple = new SuggestionSimpleCursorAdapter(getBaseContext(),
//                    android.R.layout.simple_list_item_1, cursor,
                    R.layout.suggestion_adapter_layout, cursor,   //create custom layout for look proper in both theme
                    columns , columnTextId
                    , 0);

            searchView.setSuggestionsAdapter(simple);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
    }

    private void showPreviousSearch(){
        cursor = database.getAllValues();
        searchlist = new ArrayList<String>();

        if(cursor.getCount() > 0) {
            for(int i = 0; i < cursor.getCount(); i++ )
            {
                searchlist.add(cursor.getString(cursor.getColumnIndex(SuggestionsDatabase.FIELD_SUGGESTION)));
                cursor.moveToNext();
//                lastSearchHistory.setText(cursor.getString(cursor.getColumnIndex(SuggestionsDatabase.FIELD_SUGGESTION)));
            }

//          Collections.sort(searchlistmainact, Collections.reverseOrder());
            Collections.reverse(searchlist);
            AppLogger.e("SearchList All Value in reverse Order Like Last searched is display first  ","-----"+ searchlist.toString());
            SearchAdapter adapter = new SearchAdapter(NewSearchAct.this, searchlist);
            lastSearchRecyclerView.setAdapter(adapter);
            tvSearchHistoryTitle.setVisibility(View.VISIBLE);
        }
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
                    HeaderMenuAdapter headerMenuAdapter = new HeaderMenuAdapter(NewSearchAct.this, response.body().getData());
                    recycleViewHeader.setAdapter(headerMenuAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<HeaderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    public void onResume() {
        super.onResume();
        showPreviousSearch();
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

    @Override
    public void onBackPressed() {
//        finish();

// Set this bcz when user click on search icon from any of the category listing page & After apply filter open search through list page & come back from there
        if(searchbackflag == 0) {
            if(filterFlag == 1 || filterFlag == 2){
                searchbackflag = 0;
            }else {
                searchbackflag = 1;
            }
        }
        super.onBackPressed();
    }
}