package com.dealermela.listing_and_detail.activity;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.listing_and_detail.adapter.FilterRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.FilterTitleListAdapter;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public static String skuFilterString;
    public static int filterFlag = 0;
    public static int pagecountflag = 0 ;
    private ProgressBar progressBarFilter;
//    private HorizontalScrollView hsView;
//    private GridLayout linContainer;
    private int pos = 0;
    public static int filterCurrentPosition = 0;
    private ArrayList<FilterItem.OptionDatum> selectFilterArray = new ArrayList<>();
    private String categoryId,Subcategoryid;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_filter;
    }

    @Override
    public void init() {
        Gson gson = new Gson();
        Intent intent = getIntent();
        categoryId = getIntent().getStringExtra(AppConstants.NAME);
        if(getIntent().getStringExtra(AppConstants.SubCategory_ID) != null) {
            Subcategoryid = getIntent().getStringExtra(AppConstants.SubCategory_ID);
        }else {
            Subcategoryid = " ";
        }

        AppLogger.e("CategoryId ","FilterAct---" + categoryId);
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

//        if (mapFilter.isEmpty()) {
//
//        } else {
////            bindSelectFilter();
//            countFilter();
//        }
    }

    @Override
    public void addListener() {
        imgBack.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        btnApply.setOnClickListener(this);

//        if (mapFilter.containsKey(paramKey)) {
//            //key exists
//            String key = mapFilter.get(paramKey);
//            edText.setText(key);
////            adapter.items.get(0).setFiltercount(1);
//        } else {
//            //key does not exists
////            mapFilter.put(paramKey, "");
////            selectFilter.put(paramKey, "");
//        }

        listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectedPosition = position;
                filterCurrentPosition = position;
                AppLogger.e("listViewFilter--Item Click--filterCurrentPosition","---" + filterCurrentPosition);
                adapter.notifyDataSetChanged();

                if (adapter.items.get(position).getOption_type().equalsIgnoreCase("text")) {
                    edText.setVisibility(View.VISIBLE);
                    recycleViewFilterData.setVisibility(View.GONE);

                    if(!edText.getText().toString().isEmpty()) {

//                        paramKey = filterSelectItems.get(position).getOptionName();
//                        if (mapFilter.containsKey(paramKey)) {
//                            //key exists
//                            String key = mapFilter.get(paramKey);
//                            edText.setText(key);
//                            adapter.items.get(position).setFiltercount(1);
//                        } else {
//                            //key does not exists
//                            mapFilter.put(paramKey, "");
//                            selectFilter.put(paramKey, "");
//                            adapter.items.get(position).setFiltercount(0);
//                        }

                        if(!skuFilterString.isEmpty()){
                            edText.setText(skuFilterString);
                            adapter.items.get(position).setFiltercount(1);
                        }else{
                            adapter.items.get(position).setFiltercount(0);
                        }
                    }else {
                        if(skuFilterString.isEmpty()){
                            adapter.items.get(position).setFiltercount(0);
                        }
                    }
                } else {

                    edText.setVisibility(View.GONE);
                    recycleViewFilterData.setVisibility(View.VISIBLE);

//                    paramKey = filterSelectItems.get(position).getOptionName();
//                    if (mapFilter.containsKey(paramKey)) {
//                        //key exists
//                    } else {
//                        //key does not exists
//                        mapFilter.put(paramKey, "");
//                    }
                    pos = position;
                    filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(position).getOptionData());
                    recycleViewFilterData.setAdapter(filterRecyclerAdapter);
                }

//                btnApply.setEnabled(true);
//                btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
//                btnApply.setTextColor(getResources().getColor(R.color.white));
            }
        });

        countFilter();

        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                mapFilter.put(paramKey, edText.getText().toString());
//                selectFilter.put(paramKey, edText.getText().toString());
                skuFilterString = edText.getText().toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnApply.setEnabled(true);
                btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
                btnApply.setTextColor(getResources().getColor(R.color.white));
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(edText.getText().toString().isEmpty()){
                    skuFilterString = "";

                    if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                        adapter.items.get(0).setFiltercount(0);
                    }else {
//                        adapter.items.get(4).setFiltercount(0);
                        if(Subcategoryid.isEmpty()) {
                            if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                                adapter.items.get(4).setFiltercount(0);
                            }else {
                                adapter.items.get(5).setFiltercount(0);
                            }
                        }else {
                            adapter.items.get(4).setFiltercount(0);
                        }
                    }
//                    adapter.items.get(4).setFiltercount(0);

                    for(int k = 0 ; k < filterSelectItems.size() ; k++){
                        if(filterSelectItems.get(k).getFiltercount() > 0) {
                            btnApply.setEnabled(true);
                            btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
                            btnApply.setTextColor(getResources().getColor(R.color.white));
                        }
//                        else {
//                            btnApply.setEnabled(false);
//                            btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
//                            btnApply.setTextColor(getResources().getColor(R.color.colorBack));
//                        }
                    }
                }else {
                    skuFilterString = edText.getText().toString();
                    if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
                        adapter.items.get(0).setFiltercount(1);
                    }else {
//                        adapter.items.get(4).setFiltercount(1);
                        if(Subcategoryid.isEmpty()) {
//                            adapter.items.get(5).setFiltercount(1);
                            if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                                adapter.items.get(4).setFiltercount(1);
                            }else {
                                adapter.items.get(5).setFiltercount(1);
                            }
                        }else {
                            adapter.items.get(4).setFiltercount(1);
                        }
                    }
//                    adapter.items.get(4).setFiltercount(1);
                    btnApply.setEnabled(true);
                    btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
                    btnApply.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    @Override
    public void loadData() {

//        if (mapFilter.containsKey(paramKey)) {
//            //key exists
//            String key = mapFilter.get(paramKey);
//            edText.setText(key);
////            adapter.items.get(0).setFiltercount(1);
//        } else {
//            //key does not exists
////            mapFilter.put(paramKey, "");
////            selectFilter.put(paramKey, "");
//        }

        if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
//            filterSelectItems.remove(0);
//            filterSelectItems.remove(0);
//            filterSelectItems.remove(0);
//            filterSelectItems.remove(0);

            AppLogger.e("pendent category_filter","---"+ filterSelectItems.size());

            adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
            listViewFilter.setAdapter(adapter);
            recycleViewFilterData.setVisibility(View.GONE);
            edText.setVisibility(View.VISIBLE);
        }else {
            adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
            listViewFilter.setAdapter(adapter);
            filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(0).getOptionData());
            recycleViewFilterData.setAdapter(filterRecyclerAdapter);
        }

//        adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
//        listViewFilter.setAdapter(adapter);
        tvReset.setEnabled(true);

        if(filterFlag == 0) {
            skuFilterString = "";
            if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){       //apply this condition bcz in this category we have only SKU filter no other filter's are available
                adapter.items.get(0).setFiltercount(0);
            }else {
//                adapter.items.get(4).setFiltercount(0);
//                adapter.items.get(5).setFiltercount(0);
                if(Subcategoryid.isEmpty()) {
//                    adapter.items.get(5).setFiltercount(0);
                    if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                        adapter.items.get(4).setFiltercount(0);
                    }else {
                        adapter.items.get(5).setFiltercount(0);
                    }
                }else {
                    adapter.items.get(4).setFiltercount(0);
                }
            }
//            adapter.items.get(4).setFiltercount(0);
            btnApply.setEnabled(false);
            btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
            btnApply.setTextColor(getResources().getColor(R.color.colorBack));
        }else if (filterFlag == 1){
            if(skuFilterString != ""){
                edText.setText(skuFilterString);
                if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                    adapter.items.get(0).setFiltercount(1);
                }else {
//                    adapter.items.get(4).setFiltercount(1);
//                    adapter.items.get(5).setFiltercount(1);
                    if(Subcategoryid.isEmpty()) {
//                        adapter.items.get(5).setFiltercount(1);
                        if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                            adapter.items.get(4).setFiltercount(1);
                        }else {
                            adapter.items.get(5).setFiltercount(1);
                        }
                    }else {
                        adapter.items.get(4).setFiltercount(1);
                    }
                }
//                adapter.items.get(4).setFiltercount(1);
            }
        }
        countFilter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReset:

                resetFilter();

                if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
//                    filterSelectItems.remove(0);
//                    filterSelectItems.remove(0);
//                    filterSelectItems.remove(0);
//                    filterSelectItems.remove(0);

                    AppLogger.e("pendent category_filter","---"+ filterSelectItems.size());

                    adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
                    listViewFilter.setAdapter(adapter);
                    recycleViewFilterData.setVisibility(View.GONE);
                    edText.setVisibility(View.VISIBLE);
                }else {
                    edText.setVisibility(View.GONE);
                    recycleViewFilterData.setVisibility(View.VISIBLE);
                    adapter = new FilterTitleListAdapter(FilterAct.this, filterSelectItems);
                    listViewFilter.setAdapter(adapter);
                    filterRecyclerAdapter = new FilterRecyclerAdapter(FilterAct.this, filterSelectItems.get(0).getOptionData());
                    recycleViewFilterData.setAdapter(filterRecyclerAdapter);
                }

                edText.setText("");
                skuFilterString = "";

                if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                    adapter.items.get(0).setFiltercount(0);
                }else {
//                    adapter.items.get(4).setFiltercount(0);
//                    adapter.items.get(5).setFiltercount(0);
                    if(Subcategoryid.isEmpty()) {
//                        adapter.items.get(5).setFiltercount(0);
                        if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                            adapter.items.get(4).setFiltercount(0);
                        }else {
                            adapter.items.get(5).setFiltercount(0);
                        }
                    }else {
                        adapter.items.get(4).setFiltercount(0);
                    }
                }
//                adapter.items.get(4).setFiltercount(0);

//                mapFilter.clear();
//                paramKey = "";
                selectFilter.clear();
                pagecountflag = 1;

                btnApply.setEnabled(false);
                btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
                btnApply.setTextColor(getResources().getColor(R.color.colorBack));

//                mapFilter.put(paramKey, "");
//                selectFilter.put(paramKey, "");
//                bindSelectFilter();
                countFilter();
//                filterFlag = 0;
                break;

            case R.id.imgBack:
                if(filterFlag != 1){
                    resetFilter();
                    edText.setText("");
                    edText.setVisibility(View.GONE);
                    recycleViewFilterData.setVisibility(View.VISIBLE);
                    skuFilterString = "";

                    if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                        adapter.items.get(0).setFiltercount(0);
                    }else {
//                        adapter.items.get(4).setFiltercount(0);
//                        adapter.items.get(5).setFiltercount(0);
                        if(Subcategoryid.isEmpty()) {
//                            adapter.items.get(5).setFiltercount(0);
                            if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                                adapter.items.get(4).setFiltercount(0);
                            }else {
                                adapter.items.get(5).setFiltercount(0);
                            }
                        }else {
                            adapter.items.get(4).setFiltercount(0);
                        }
                    }
//                    adapter.items.get(4).setFiltercount(0);
                }else if(filterFlag == 1){
                    if(!btnApply.isEnabled()){
                        filterFlag = 2;
                    }
                }
                finish();
                break;

            case R.id.btnApply:
//                if(selectFilterArray.size()!=0){
//                    filterFlag = 1;
//                }else{
//                    filterFlag = 0;
//                }

                for(int j = 0 ; j < filterSelectItems.size() ; j++){          // Filter: click reset button & then click apply,without selecting any filter then all listing items display
                    if(filterSelectItems.get(j).getFiltercount()!= 0) {
                        filterFlag = 1;
                        pagecountflag = 1;
                        finish();
                    }

//                    if (mapFilter.containsKey(paramKey)) {
//                        filterFlag =  1;
//                        finish();
//                    }

                    if(!skuFilterString.isEmpty()){
                        filterFlag = 1;
                        pagecountflag = 1;
//                        adapter.items.get(4).setFiltercount(1);
                        finish();
                    }
                }
//                if(edText.getText().toString()!=null){
//                    if(filterSelectItems.get(4).getOptionName().equalsIgnoreCase("sku")){
////                        = edText.getText().toString();
//                    }
//                    filterFlag = 1;
//                }
                break;
        }
    }

    public void resetFilter() {
        filterFlag = 2;
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
            }  else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("categories")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++){
                    filterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(filterFlag != 1){
            resetFilter();
            edText.setText("");
            edText.setVisibility(View.GONE);
            recycleViewFilterData.setVisibility(View.VISIBLE);
            skuFilterString = "";

            if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                adapter.items.get(0).setFiltercount(0);
            }else {
//                adapter.items.get(4).setFiltercount(0);
//                adapter.items.get(5).setFiltercount(0);
                if(Subcategoryid.isEmpty()) {
//                    adapter.items.get(5).setFiltercount(0);
                    if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                        adapter.items.get(4).setFiltercount(0);
                    }else {
                        adapter.items.get(5).setFiltercount(0);
                    }
                }else {
                    adapter.items.get(4).setFiltercount(0);
                }
            }
//            adapter.items.get(4).setFiltercount(0);
        }else if(filterFlag == 1){
              if(!btnApply.isEnabled()){
                 filterFlag = 2;
              }
        }
        finish();
//        filterFlag = 0;
//        finish();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(filterFlag == 0){
            resetFilter();
            countFilter();

            btnApply.setEnabled(false);
            btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
            btnApply.setTextColor(getResources().getColor(R.color.colorBack));

        }else if(filterFlag == 1){
            if(skuFilterString != ""){
                edText.setText(skuFilterString);

                if(categoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)){
                    adapter.items.get(0).setFiltercount(1);
                }else {
//                    adapter.items.get(4).setFiltercount(1);
//                    adapter.items.get(5).setFiltercount(1);
                    if(Subcategoryid.isEmpty()) {
//                        adapter.items.get(5).setFiltercount(1);
                        if(categoryId.equalsIgnoreCase(AppConstants.NOSEPIN_ID) || categoryId.equalsIgnoreCase(AppConstants.NECKLACE_ID) || categoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || categoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)){
                            adapter.items.get(4).setFiltercount(1);
                        }else {
                            adapter.items.get(5).setFiltercount(1);
                        }
                    }else {
                        adapter.items.get(4).setFiltercount(1);
                    }
                }
//                adapter.items.get(4).setFiltercount(1);
            }
            btnApply.setEnabled(true);
            btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
            btnApply.setTextColor(getResources().getColor(R.color.white));

        } else if(filterFlag == 2){
            btnApply.setEnabled(false);
            btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
            btnApply.setTextColor(getResources().getColor(R.color.colorBack));
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
            } else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("sku")){
//                if(edText.getText().toString() != null){
//                    adapter.items.get(i).setFiltercount(1);
//                }else{
//
//                }
            } else if(filterSelectItems.get(i).getOptionName().equalsIgnoreCase("categories")){
                for(int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++){
                    if(filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                        selectFilterArray.add(filterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                filterSelectItems.get(i).setFiltercount(selectFilterArray.size());
                selectFilterArray.clear();
            }
        }
    }

    public void updateFilterData(int position, boolean selectFlag) {
        AppLogger.e("listViewFilter--Item Click--filterCurrentPosition","---" + filterCurrentPosition);
//        filterSelectItems.get(filterCurrentPosition).getOptionData().get(position).setSelected(selectFlag);
//        AppLogger.e("Check selected or not","---"+ filterSelectItems.get(filterCurrentPosition).getOptionData().get(position).isSelected());
        countFilter();
//        FilterTitleListAdapter.tvinvcount.setVisibility(View.VISIBLE);
//        FilterTitleListAdapter.tvinvcount.setText(String.valueOf(filterSelectItems.get(filterCurrentPosition).getFiltercount()));
        AppLogger.e("TotalFilterCount","--" + filterSelectItems.get(filterCurrentPosition).getFiltercount());
        int totalselected = 0;
        if(selectFlag == true){
//            for(int k = 0 ; k < filterSelectItems.size() ; k++) {
//                if (filterSelectItems.get(k).getOptionData().get(k).isSelected()) {
//                    totalselected = totalselected + 1;
//                }
//            }
            btnApply.setEnabled(true);
            btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
            btnApply.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            for(int k = 0 ; k < filterSelectItems.size() ; k++) {
//                if(filterSelectItems.get(k).getOptionData().get(k).isSelected()){
//
//                    totalselected = totalselected - 1;
//                }
                if (filterSelectItems.get(k).getFiltercount() == 0) {
//                    filterFlag = 0;
                    btnApply.setEnabled(false);
                    btnApply.setBackgroundColor(getResources().getColor(R.color.in_active_item_color));
                    btnApply.setTextColor(getResources().getColor(R.color.colorBack));
                }else {
                    btnApply.setEnabled(true);
                    btnApply.setBackgroundColor(getResources().getColor(R.color.dml_logo_color));
                    btnApply.setTextColor(getResources().getColor(R.color.white));
                    break;
                }
            }
//            AppLogger.e("Overall Count","-------" + totalselected);
        }
    }
}
