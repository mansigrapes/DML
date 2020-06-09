package com.dealermela.listing_and_detail.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.listing_and_detail.adapter.FilterRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.FilterTitleListAdapter;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.util.AppLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dealermela.inventary.activity.InventoryListAct.InvfilterSelectItems;
import static com.dealermela.listing_and_detail.activity.ListAct.filterSelectItems;


public class FilterAct extends DealerMelaBaseActivity implements View.OnClickListener {
    private ListView listViewFilter;
    private FilterTitleListAdapter adapter;
    private FilterRecyclerAdapter filterRecyclerAdapter;
    private RecyclerView recycleViewFilterData;
    private LinearLayoutManager linearLayoutManager;
    //    public static List<FilterItem.Datum> filterItems = new ArrayList<>();
//    private List<FilterItem.Datum> filterItems = new ArrayList<>();
    private EditText edText;
    private ImageView imgBack;
    private TextView tvReset;
    private Button btnApply;
    //    public static ArrayList<String> arrayListFilter = new ArrayList<>();
    public static HashMap<String, String> mapFilter = new HashMap<String, String>();
    public static HashMap<String, String> selectFilter = new HashMap<String, String>();
    public static String paramKey;
    public static int filterFlag = 0;
    private ProgressBar progressBarFilter;
//    private HorizontalScrollView hsView;
//    private GridLayout linContainer;
    private int pos = 0;
    public static int filterCurrentPosition = 0;
    private ArrayList<FilterItem.OptionDatum> selectFilterArray = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_filter;
    }

    @Override
    public void init() {
        Gson gson = new Gson();
        Intent intent = getIntent();
        Type listType = new TypeToken<List<FilterItem.Datum>>() {
        }.getType();
//        filterSelectItems= gson.fromJson(intent.getStringExtra(AppConstants.NAME), listType);
    }

    @Override
    public void initView() {
        imgBack = findViewById(R.id.imgBack);
        tvReset = findViewById(R.id.tvReset);
        listViewFilter = findViewById(R.id.listViewFilter);
        recycleViewFilterData = findViewById(R.id.recycleViewFilterData);
        edText = findViewById(R.id.edText);
        btnApply = findViewById(R.id.btnApply);
        progressBarFilter = findViewById(R.id.progressBarFilter);
//        linContainer = findViewById(R.id.linContainer);
//        hsView = findViewById(R.id.hsView);
        btnApply.setEnabled(false);
        progressBarFilter.setVisibility(View.GONE);
    }

    @Override
    public void postInitView() {
        linearLayoutManager = new LinearLayoutManager(FilterAct.this);
        recycleViewFilterData.setLayoutManager(linearLayoutManager);

        if (mapFilter.isEmpty()) {

        } else {
//            bindSelectFilter();
            countFilter();
        }
    }

    @Override
    public void addListener() {
        imgBack.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        btnApply.setOnClickListener(this);

        listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectedPosition = position;
                filterCurrentPosition = position;
                adapter.notifyDataSetChanged();

                if (adapter.items.get(position).getOption_type().equalsIgnoreCase("text")) {
                    edText.setVisibility(View.VISIBLE);
                    recycleViewFilterData.setVisibility(View.GONE);
                    paramKey = filterSelectItems.get(position).getOptionName();
                    if (mapFilter.containsKey(paramKey)) {
                        //key exists
                        String key = mapFilter.get(paramKey);
                        edText.setText(key);
                    } else {
                        //key does not exists
                        mapFilter.put(paramKey, "");
                        selectFilter.put(paramKey, "");
                    }

                } else {

                    edText.setVisibility(View.GONE);
                    recycleViewFilterData.setVisibility(View.VISIBLE);

                    paramKey = filterSelectItems.get(position).getOptionName();
                    if (mapFilter.containsKey(paramKey)) {
                        //key exists
                    } else {
                        //key does not exists
                        mapFilter.put(paramKey, "");
                    }
                    pos = position;
                    filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(position).getOptionData());
                    recycleViewFilterData.setAdapter(filterRecyclerAdapter);
                }
            }
        });

        countFilter();

        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mapFilter.put(paramKey, edText.getText().toString());
                selectFilter.put(paramKey, edText.getText().toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void loadData() {
        adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
        listViewFilter.setAdapter(adapter);
        tvReset.setEnabled(true);
        filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(0).getOptionData());
        recycleViewFilterData.setAdapter(filterRecyclerAdapter);
        btnApply.setEnabled(true);
        countFilter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReset:

                resetFilter();
                adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
                listViewFilter.setAdapter(adapter);
                filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(0).getOptionData());
                recycleViewFilterData.setAdapter(filterRecyclerAdapter);
                edText.setText("");
//                bindSelectFilter();
                countFilter();
                break;

            case R.id.imgBack:

                filterFlag = 0;
                finish();
                break;

            case R.id.btnApply:
//                if(selectFilterArray.size()!=0){
//                    filterFlag = 1;
//                }else{
//                    filterFlag = 0;
//                }
                filterFlag = 1;
                finish();
                break;
        }
    }

    public void resetFilter() {
        filterFlag = 0;
        for (int i = 0; i < filterSelectItems.size(); i++) {
            if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        filterFlag = 0;
//        finish();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(filterFlag == 0){
            resetFilter();
            countFilter();
        }
    }

    //add horizontal slide image
/*    public void bindSelectFilter() {
//        linContainer.removeAllViews();
        View imageLayout;
        TextView tvSelectFilterName;
        ImageView imgClose;

        selectFilterArray.clear();

        for (int i = 0; i < filterSelectItems.size(); i++) {
            if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
            } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")) {
                for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                    if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
            }
        }
////  }  //method closing braket
 /*       for (int k = 0; k < selectFilterArray.size(); k++) {
            imageLayout = getLayoutInflater().inflate(R.layout.item_bind_select_filter, null);
            imgClose = imageLayout.findViewById(R.id.imgClose);
            tvSelectFilterName = imageLayout.findViewById(R.id.tvSelectFilterName);
            tvSelectFilterName.setText(selectFilterArray.get(k).getLabel());
            final int finalK = k;
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < filterSelectItems.size(); i++) {
                        if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")) {
                            for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                                if (selectFilterArray.get(finalK).getValue().equalsIgnoreCase(filterSelectItems.get(i).getOptionData().get(j).getValue())) {
                                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                                }
                            }
                        } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")) {
                            for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                                if (selectFilterArray.get(finalK).getValue().equalsIgnoreCase(filterSelectItems.get(i).getOptionData().get(j).getValue())) {
                                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                                }
                            }
                        } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")) {
                            for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                                if (selectFilterArray.get(finalK).getValue().equalsIgnoreCase(filterSelectItems.get(i).getOptionData().get(j).getValue())) {
                                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                                }
                            }
                        } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")) {
                            for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                                if (selectFilterArray.get(finalK).getValue().equalsIgnoreCase(filterSelectItems.get(i).getOptionData().get(j).getValue())) {
                                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                                }
                            }
                        }
                    }

                    AppLogger.e("click", "-----------------" + filterCurrentPosition + "---" + finalK);
//                    filterSelectItems.get(filterCurrentPosition).getOptionData().get(finalK).setSelected(false);
                    bindSelectFilter();
                    filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(filterCurrentPosition).getOptionData());
                    recycleViewFilterData.setAdapter(filterRecyclerAdapter);

                }
            });
//            linContainer.addView(imageLayout);
        }

    } */

    public void countFilter(){

        selectFilterArray.clear();

        for(int i = 0; i< filterSelectItems.size(); i++){
            if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(filterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                filterSelectItems.get(i).setFiltercount(selectFilterArray.size());
                selectFilterArray.clear();
            } else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(filterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                filterSelectItems.get(i).setFiltercount(selectFilterArray.size());
                selectFilterArray.clear();
            } else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(filterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                filterSelectItems.get(i).setFiltercount(selectFilterArray.size());
                selectFilterArray.clear();
            } else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(filterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                filterSelectItems.get(i).setFiltercount(selectFilterArray.size());
                selectFilterArray.clear();
            }
        }
    }

    public void updateFilterData(int position, boolean selectFlag) {
        filterSelectItems.get(filterCurrentPosition).getOptionData().get(position).setSelected(selectFlag);
    }
}
