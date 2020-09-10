package com.dealermela.order.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.order.adapter.MyOrderAdapter;
import com.dealermela.order.model.OrderItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.cart.activity.OrderSummaryAct.Orderflag;
import static com.dealermela.home.activity.MainActivity.customerId;

public class MyOrderFrg extends DealerMelaBaseFragment {
    private View rootView;
    private RecyclerView recycleViewMyOrder;
    private GridLayoutManager gridLayout;
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
    private List<OrderItem.Datum> detailList;
    private String orderFilter="all";
    private LinearLayout linFilterOrder;
    private TextView tvstatuscount,tvstatus;
    private ImageView orderfilter;

    public MyOrderFrg() {
        // Required empty public constructor
    }

    private String orderType;
    private LoginResponse loginResponse;
    private MyOrderAdapter myOrderAdapter;

    @Override
    public View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        assert getArguments() != null;
        orderType = getArguments().getString(AppConstants.NAME);
        rootView = inflater.inflate(R.layout.frg_my_order, parent, false);
        return rootView;
    }

    @Override
    public void init() {
        detailList = new ArrayList<>();
        SharedPreferences sharedPreferences = new SharedPreferences(Objects.requireNonNull(getActivity()));
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
    }

    @Override
    public void initView() {
        recycleViewMyOrder = rootView.findViewById(R.id.recycleViewMyOrder);
        progressBarBottom = rootView.findViewById(R.id.progressBarBottom);
        progressBarCenter = rootView.findViewById(R.id.progressBarCenter);
        linNoData = rootView.findViewById(R.id.linNoData);
        linFilterOrder=rootView.findViewById(R.id.linFilterOrder);
        progressBarCenter.setVisibility(View.VISIBLE);
        tvstatuscount = rootView.findViewById(R.id.tvstatuscount);
        tvstatus = rootView.findViewById(R.id.tvstatus);
        orderfilter = rootView.findViewById(R.id.orderfilter);
    }

    @Override
    public void postInitView() {
        gridLayout = new GridLayoutManager(getActivity(), 1);
        recycleViewMyOrder.setLayoutManager(gridLayout);
    }

    @Override
    public void addListener() {

        linFilterOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_filter_order, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();

                LinearLayout com =  sheetView.findViewById(R.id.linComp);
                LinearLayout can =  sheetView.findViewById(R.id.linCancel);
                LinearLayout pen =  sheetView.findViewById(R.id.linPen);
                LinearLayout all = sheetView.findViewById(R.id.linAll);

                TextView tvcom = sheetView.findViewById(R.id.tvcomp);
                TextView tvcan = sheetView.findViewById(R.id.tvcan);
                TextView tvpen = sheetView.findViewById(R.id.tvpen);
                TextView tvall = sheetView.findViewById(R.id.tvall);

                if (orderFilter == "complete"){
//                    tvstatuscount.setVisibility(View.VISIBLE);
                    tvcom.setTextColor(getResources().getColor(R.color.dml_logo_color));
                } else if (orderFilter == "canceled") {
                    tvcan.setTextColor(getResources().getColor(R.color.dml_logo_color));
//                    tvstatuscount.setVisibility(View.VISIBLE);
                } else if(orderFilter == "pending"){
                    tvpen.setTextColor(getResources().getColor(R.color.dml_logo_color));
//                    tvstatuscount.setVisibility(View.VISIBLE);
                } else if(orderFilter == "all") {
//                    tvall.setTextColor(getResources().getColor(R.color.black));
                    tvall.setTextColor(getResources().getColor(R.color.dml_logo_color));
//                    tvstatuscount.setVisibility(View.GONE);
                } else {
                    tvcom.setTextColor(getResources().getColor(R.color.black));
                    tvcan.setTextColor(getResources().getColor(R.color.black));
                    tvpen.setTextColor(getResources().getColor(R.color.black));
                    tvall.setTextColor(getResources().getColor(R.color.black));
//                    tvstatuscount.setVisibility(View.GONE);
                }

                com.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        orderFilter="complete";
                        page_count=1;
                        detailList.clear();

                        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
                        recycleViewMyOrder.setAdapter(myOrderAdapter);
                        AppLogger.e("customerId", "----------" + customerId);
                        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
                        AppLogger.e("order", "----------" + orderType);
                        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                        linNoData.setVisibility(View.GONE);
                        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                    }
                });

                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        orderFilter="canceled";
                        page_count=1;
                        detailList.clear();

                        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
                        recycleViewMyOrder.setAdapter(myOrderAdapter);
                        AppLogger.e("customerId", "----------" + customerId);
                        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
                        AppLogger.e("order", "----------" + orderType);
                        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                    }
                });

                pen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        orderFilter="pending";
                        page_count=1;
                        detailList.clear();

                        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
                        recycleViewMyOrder.setAdapter(myOrderAdapter);
                        AppLogger.e("customerId", "----------" + customerId);
                        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
                        AppLogger.e("order", "----------" + orderType);
                        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                    }
                });

                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        orderFilter="all";
                        page_count=1;
                        detailList.clear();

                        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
                        recycleViewMyOrder.setAdapter(myOrderAdapter);
                        AppLogger.e("customerId", "----------" + customerId);
                        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
                        AppLogger.e("order", "----------" + orderType);
                        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
                    }
                });
            }
        });

        recycleViewMyOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                   @Override
                                                   public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                       super.onScrolled(recyclerView, dx, dy);

                                                       visibleItemCount = recyclerView.getChildCount();
                                                       totalItemCount = gridLayout.getItemCount();
                                                       firstVisibleItem = gridLayout.findFirstVisibleItemPosition();

//                                                       if (flag_scroll) {
//                                                           AppLogger.e("flag-Scroll", flag_scroll + "");
//                                                       } else {
//                                                           if (loading) {
//                                                               AppLogger.e("flag-Loading", loading + "");
//                                                               if (totalItemCount > previousTotal) {
//                                                                   loading = false;
//                                                                   previousTotal = totalItemCount;
//                                                                   //Log.e("flag-IF", (totalItemCount > previousTotal) + "");
//                                                               }
//                                                           }
//                                                           if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//                                                               // End has been reached
//                                                               // Do something
//                                                               AppLogger.e("flag-Loading_second_if", loading + "");
//                                                               if (NetworkUtils.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
//                                                                   AppLogger.e("total count", "--------------------" + page_count);
//                                                                   page_count++;
//                                                                   getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
//                                                               } else {
//                                                                   //internet not connected
//                                                                   AppLogger.e("connection", "-------internet connection is off");
//                                                               }
//                                                               loading = true;
//                                                           }
//                                                       }
                                                   }
                                               }
        );
    }

    @Override
    public void loadData() {
       /* myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
        recycleViewMyOrder.setAdapter(myOrderAdapter);
        AppLogger.e("customerId", "----------" + customerId);
        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
        AppLogger.e("order", "----------" + orderType);
        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));*/

        page_count=1;

//        detailList.clear();

        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
        recycleViewMyOrder.setAdapter(myOrderAdapter);
        AppLogger.e("customerId", "----------" + customerId);
        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
        AppLogger.e("order", "----------" + orderType);
        AppLogger.e("page", "----------" + page_count);
//        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));

//        if(detailList.isEmpty()){
//            linFilterOrder.setEnabled(false);
//            tvstatus.setTextColor(getResources().getColor(R.color.in_active_item_color));
//            orderfilter.setColorFilter(getResources().getColor(R.color.in_active_item_color));
//        }
    }

    private void getOrderList(String customerId, String groupId, String order, String page) {
        if (page_count == 1) {
            progressBarCenter.setVisibility(View.VISIBLE);
        } else {
            progressBarBottom.setVisibility(View.VISIBLE);
        }

        if (orderFilter == "complete"){
            tvstatuscount.setVisibility(View.VISIBLE);
        } else if (orderFilter == "canceled") {
            tvstatuscount.setVisibility(View.VISIBLE);
        } else if(orderFilter == "pending"){
            tvstatuscount.setVisibility(View.VISIBLE);
        } else if(orderFilter == "all") {
            tvstatuscount.setVisibility(View.GONE);
        } else {
            tvstatuscount.setVisibility(View.GONE);
        }

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<OrderItem> callApi = apiInterface.orderList(customerId, groupId, order, page,orderFilter);
        callApi.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(@NonNull Call<OrderItem> call, @NonNull Response<OrderItem> response) {
                assert response.body() != null;

                AppLogger.e(AppConstants.RESPONSE, "----------" + response.body());

                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        linNoData.setVisibility(View.GONE);
                        detailList.addAll(response.body().getData());
                        myOrderAdapter.notifyDataSetChanged();

                    }else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
                        linNoData.setVisibility(View.VISIBLE);
                        if(tvstatuscount.getVisibility() != View.VISIBLE){
                            linFilterOrder.setEnabled(false);
                            tvstatus.setTextColor(getResources().getColor(R.color.in_active_item_color));
                            orderfilter.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                        }else {
                            linFilterOrder.setEnabled(true);
                        }

                    } else {
                        if (detailList.isEmpty()) {
                            linNoData.setVisibility(View.VISIBLE);

                            if(orderFilter.isEmpty()){
                                linFilterOrder.setEnabled(false);
                                tvstatus.setTextColor(getResources().getColor(R.color.in_active_item_color));
                                orderfilter.setColorFilter(getResources().getColor(R.color.in_active_item_color));

                            }else {
                                linFilterOrder.setEnabled(true);
                            }
//                            linFilterOrder.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linNoData.setVisibility(View.VISIBLE);
                linFilterOrder.setEnabled(false);
                tvstatus.setTextColor(getResources().getColor(R.color.in_active_item_color));
                orderfilter.setColorFilter(getResources().getColor(R.color.in_active_item_color));

                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
////        page_count=1;
////
////        detailList.clear();
////
////        myOrderAdapter = new MyOrderAdapter(getActivity(), detailList);
////        recycleViewMyOrder.setAdapter(myOrderAdapter);
////        AppLogger.e("customerId", "----------" + customerId);
////        AppLogger.e("groupId", "----------" + loginResponse.getData().getGroupId());
////        AppLogger.e("order", "----------" + orderType);
////        AppLogger.e("page", "----------" + page_count);
//////        getOrderList("984", loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
////        getOrderList(customerId, loginResponse.getData().getGroupId(), orderType, String.valueOf(page_count));
//
//    }

//    public void onBackPressed(){
//        AppLogger.e("MyOrderFrg ","Back Pressed---");
//        if(Orderflag == 1){
//            Orderflag = 0;
//            startNewActivity(MainActivity.class);
//        }else {
//            startNewActivity(MainActivity.class);
//        }
//    }
}
