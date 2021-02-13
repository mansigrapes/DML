package com.dealermela.inventary.activity;

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
import com.dealermela.inventary.adapter.InventoryFilterRecyclerAdapter;
import com.dealermela.inventary.adapter.InventoryFilterTitleListAdapter;
import com.dealermela.inventary.model.InventoryFilterItem;
import com.dealermela.util.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dealermela.inventary.activity.InventoryListAct.InvfilterSelectItems;

public class InventoryFilterAct extends DealerMelaBaseActivity implements View.OnClickListener {
    private ListView listViewInvFlt;
    private InventoryFilterTitleListAdapter inventoryFilterTitleListAdapter;
    private RecyclerView recycleViewInvFltData;
    private InventoryFilterRecyclerAdapter inventoryFilterRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    private EditText edTextInvFlt;
    private TextView tvInvFltReset;
    private Button btnInvApply;
    private ImageView imgInvFltBack;
    private ProgressBar progressBarInvFlt;
//    private GridLayout linContainerInv;

    public TextView tvinvcount;

    public static HashMap<String, String> mapFilter = new HashMap<String, String>();
    public static HashMap<String, String> selectFilter = new HashMap<String, String>();
    public static String paramKey;
    public static int filterFlag = 0;
    private int pos = 0;
    public static int currentposition=0;
    int count=0;
    private ArrayList<InventoryFilterItem.OptionDatum> selectInvFilterArray = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_inventory_filter;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        listViewInvFlt=findViewById(R.id.listViewInvFlt);
        recycleViewInvFltData=findViewById(R.id.recycleViewInvFltData);
        edTextInvFlt=findViewById(R.id.edTextInvFlt);
        tvInvFltReset=findViewById(R.id.tvInvFltReset);
        btnInvApply=findViewById(R.id.btnInvApply);
        imgInvFltBack=findViewById(R.id.imgInvFltBack);
        progressBarInvFlt=findViewById(R.id.progressBarInvFlt);
//        linContainerInv=findViewById(R.id.linContainerInv);
        btnInvApply.setEnabled(false);
        progressBarInvFlt.setVisibility(View.GONE);
        tvinvcount=findViewById(R.id.tvinvcount);
    }

    @Override
    public void postInitView() {
        linearLayoutManager=new LinearLayoutManager(InventoryFilterAct.this);
        recycleViewInvFltData.setLayoutManager(linearLayoutManager);
        countFilter();
        if (mapFilter.isEmpty()) {
        } else {
//            bindSelectFilter();
            countFilter();
        }
    }

    @Override
    public void addListener()
    {
        imgInvFltBack.setOnClickListener(this);
        tvInvFltReset.setOnClickListener(this);
        btnInvApply.setOnClickListener(this);

        listViewInvFlt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inventoryFilterTitleListAdapter.selectedPosition=position;
                currentposition=position;
                inventoryFilterTitleListAdapter.notifyDataSetChanged();

                if(inventoryFilterTitleListAdapter.inventoryitems.get(position).getOptionType().equalsIgnoreCase("text")){
                    edTextInvFlt.setVisibility(View.VISIBLE);
                    recycleViewInvFltData.setVisibility(View.GONE);
                    paramKey = InvfilterSelectItems.get(position).getOptionName();
                    if (mapFilter.containsKey(paramKey)) {
                        //key exists
                        String key = mapFilter.get(paramKey);
                        edTextInvFlt.setText(key);
                    } else {
                        //key does not exists
                        mapFilter.put(paramKey, "");
                        selectFilter.put(paramKey, "");
                    }
                }else{
                    edTextInvFlt.setVisibility(View.GONE);
                    recycleViewInvFltData.setVisibility(View.VISIBLE);

                    paramKey = InvfilterSelectItems.get(position).getOptionName();
                    if (mapFilter.containsKey(paramKey)) {
                        //key exists
                    } else {
                        //key does not exists
                        mapFilter.put(paramKey, "");
                    }
                    pos = position;
                    inventoryFilterRecyclerAdapter = new InventoryFilterRecyclerAdapter(InventoryFilterAct.this, InvfilterSelectItems.get(position).getOptionData());
                    recycleViewInvFltData.setAdapter(inventoryFilterRecyclerAdapter);
                }
            }
        });

        countFilter();

        edTextInvFlt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mapFilter.put(paramKey, edTextInvFlt.getText().toString());
                selectFilter.put(paramKey, edTextInvFlt.getText().toString());
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
        if(NetworkUtils.isNetworkConnected(InventoryFilterAct.this)) {
            inventoryFilterTitleListAdapter = new InventoryFilterTitleListAdapter(InventoryFilterAct.this, InvfilterSelectItems);
            listViewInvFlt.setAdapter(inventoryFilterTitleListAdapter);
            tvInvFltReset.setEnabled(true);
            inventoryFilterRecyclerAdapter = new InventoryFilterRecyclerAdapter(InventoryFilterAct.this, InvfilterSelectItems.get(0).getOptionData());
            recycleViewInvFltData.setAdapter(inventoryFilterRecyclerAdapter);
            btnInvApply.setEnabled(true);
            countFilter();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvInvFltReset:
                resetFilter();
                inventoryFilterTitleListAdapter=new InventoryFilterTitleListAdapter(InventoryFilterAct.this,InvfilterSelectItems);
                listViewInvFlt.setAdapter(inventoryFilterTitleListAdapter);
                inventoryFilterRecyclerAdapter=new InventoryFilterRecyclerAdapter(InventoryFilterAct.this,InvfilterSelectItems.get(0).getOptionData());
                recycleViewInvFltData.setAdapter(inventoryFilterRecyclerAdapter);
                edTextInvFlt.setText("");
//                bindSelectFilter();
                countFilter();
                break;

            case R.id.imgInvFltBack:
                filterFlag = 0;
                finish();
                break;

            case R.id.btnInvApply:
                filterFlag = 1;
                finish();
                break;
        }
    }

    public void resetFilter() {
        for(int i = 0; i< InvfilterSelectItems.size(); i++){
            if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
                    for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                        InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        filterFlag = 0;
    }

    @Override
    public void onResume(){
        super.onResume();

        if(filterFlag == 0){
            resetFilter();
            countFilter();
        }
    }

//    public void bindSelectFilter() {
//        linContainerInv.removeAllViews();
//        View imageLayout;
//        TextView tvSelectFilterName;
//        ImageView imgClose;
//
//        countFilter();
//
//        selectInvFilterArray.clear();
//
//        for(int i = 0; i< InvfilterSelectItems.size(); i++){
//            if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")){
//                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
//                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
//                    }
//                }
//            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
//                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
//                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
//                    }
//                }
//            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
//                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
//                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
//                    }
//                }
//            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
//                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
//                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
//                    }
//                }
//            }
//        }
//
//        for(int k =0; k < selectInvFilterArray.size(); k++){
//            imageLayout = getLayoutInflater().inflate(R.layout.item_bind_select_filter, null);
//            imgClose = imageLayout.findViewById(R.id.imgClose);
//            tvSelectFilterName = imageLayout.findViewById(R.id.tvSelectFilterName);
//            tvSelectFilterName.setText(selectInvFilterArray.get(k).getLabel());
//            final int finalK = k;
//
//            imgClose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    for (int i = 0; i< InvfilterSelectItems.size(); i++){
//                        if (InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")) {
//                            for (int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ) {
//                                if (selectInvFilterArray.get(finalK).getValue().equalsIgnoreCase(InvfilterSelectItems.get(i).getOptionData().get(j).getValue())){
//                                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
//                                }
//                            }
//                        } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
//                            for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                                if (selectInvFilterArray.get(finalK).getValue().equalsIgnoreCase(InvfilterSelectItems.get(i).getOptionData().get(j).getValue())){
//                                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
//                                }
//                            }
//                        } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
//                            for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                                if (selectInvFilterArray.get(finalK).getValue().equalsIgnoreCase(InvfilterSelectItems.get(i).getOptionData().get(j).getValue())){
//                                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
//                                }
//                            }
//                        } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
//                            for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
//                                if (selectInvFilterArray.get(finalK).getValue().equalsIgnoreCase(InvfilterSelectItems.get(i).getOptionData().get(j).getValue())){
//                                    InvfilterSelectItems.get(i).getOptionData().get(j).setSelected(false);
//                                }
//                            }
//                        }
//                    }
//                    bindSelectFilter();
//                    inventoryFilterRecyclerAdapter=new InventoryFilterRecyclerAdapter(InventoryFilterAct.this,InvfilterSelectItems.get(currentposition).getOptionData());
//                    recycleViewInvFltData.setAdapter(inventoryFilterRecyclerAdapter);
//                }
//            });
//            linContainerInv.addView(imageLayout);
//        }
//    }

    public void countFilter(){

        selectInvFilterArray.clear();

        for(int i = 0; i< InvfilterSelectItems.size(); i++){
            if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                InvfilterSelectItems.get(i).setFiltercount(selectInvFilterArray.size());
                selectInvFilterArray.clear();
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                InvfilterSelectItems.get(i).setFiltercount(selectInvFilterArray.size());
                selectInvFilterArray.clear();
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                InvfilterSelectItems.get(i).setFiltercount(selectInvFilterArray.size());
                selectInvFilterArray.clear();
            } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
                for(int j = 0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++ ){
                    if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                        selectInvFilterArray.add(InvfilterSelectItems.get(i).getOptionData().get(j));
                    }
                }
                InvfilterSelectItems.get(i).setFiltercount(selectInvFilterArray.size());
                selectInvFilterArray.clear();
            }
        }
    }

   public void updateFilterData(int position,boolean selectFlag){
        InvfilterSelectItems.get(currentposition).getOptionData().get(position).setSelected(selectFlag);
   }

//   public void updatefiltercount(int position){
//        countFilter();
//        InvfilterSelectItems.get(currentposition).getOptionData().get(position);
//   }
}
