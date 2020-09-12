package com.dealermela.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.home.activity.PopularProductAct;
import com.dealermela.home.adapter.BestCategoryAdapter;
import com.dealermela.home.adapter.HeaderMenuAdapter;
import com.dealermela.home.adapter.HomePageSliderAdapter;
import com.dealermela.home.adapter.MostSellingAdapter;
import com.dealermela.home.adapter.PopularProductCoverFlowAdapter;
import com.dealermela.home.model.BannerSliderItem;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.home.model.MostSellingItem;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.ThemePreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rd.PageIndicatorView;
import com.rd.draw.controller.DrawController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.util.AppConstants.RESPONSE;

public class HomeFrg extends DealerMelaBaseFragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView recycleViewHeader, recycleViewBestProducts, recycleViewMostSelling;
    private ViewPager viewpagerSlider;
    private PageIndicatorView pageIndicatorView;
    //Header arrayList
    private ImageView imgSingleBanner;
    private FeatureCoverFlow coverFlow;
    private static int count = 3;
    private Button btnViewAll;
    private LinearLayout linBackGrad;
    private List<PopularProductItem.ProductImg> arrayListPopularProduct = new ArrayList<>();

    //Popular product circle slider
//    private FeatureCoverFlow coverFlowPopularProduct;

    public HomeFrg() {
        // Required empty public constructor
    }

    @Override
    public View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_home, parent, false);
        SharedPreferences sharedPreferences = new SharedPreferences(Objects.requireNonNull(getActivity()));

        Gson gson = new Gson();
        Type type = new TypeToken<List<PopularProductItem.ProductImg>>() {
        }.getType();

        arrayListPopularProduct = gson.fromJson(sharedPreferences.getPopularProducts(), type);

        if (!arrayListPopularProduct.isEmpty()) {
            coverFlow = rootView.findViewById(R.id.coverflow);
            PopularProductCoverFlowAdapter adapter = new PopularProductCoverFlowAdapter(getActivity(), arrayListPopularProduct);
            coverFlow.setAdapter(adapter);
        }
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        count = 3;
        AppLogger.e("init", "----------");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        TextView tvDiamondMela = rootView.findViewById(R.id.tvDiamondMela);
        tvDiamondMela.setText(getString(R.string.u00a92018) + year + " " + getString(R.string.diamond_mela_jewels_ltd));

    }

    @Override
    public void initView() {
        AppLogger.e("initView", "----------");
        viewpagerSlider = rootView.findViewById(R.id.viewpagerSlider);
        pageIndicatorView = rootView.findViewById(R.id.pageIndicatorView);
        recycleViewHeader = rootView.findViewById(R.id.recycleViewHeader);
        recycleViewBestProducts = rootView.findViewById(R.id.recycleViewBestProducts);
        recycleViewMostSelling = rootView.findViewById(R.id.recycleViewMostSelling);
//        imgSingleBanner = rootView.findViewById(R.id.imgSingleBanner);
        linBackGrad = rootView.findViewById(R.id.linBackGrad);
        btnViewAll = rootView.findViewById(R.id.btnViewAll);
//        coverFlowPopularProduct = rootView.findViewById(R.id.coverFlowPopularProduct);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewHeader.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManagerBestCategory = new GridLayoutManager(getActivity(), 2);
        recycleViewBestProducts.setLayoutManager(gridLayoutManagerBestCategory);

        GridLayoutManager gridLayoutManagerBestSelling = new GridLayoutManager(getActivity(), 1);
        gridLayoutManagerBestSelling.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewMostSelling.setLayoutManager(gridLayoutManagerBestSelling);

    }

    @Override
    public void postInitView() {
        AppLogger.e("postInitView", "----------");
        ThemePreferences themePreferences = new ThemePreferences(Objects.requireNonNull(getActivity()));
        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            linBackGrad.setBackground(null);
        }
    }

    @Override
    public void addListener() {
        btnViewAll.setOnClickListener(this);
        AppLogger.e("addListener", "----------");

        pageIndicatorView.setClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                viewpagerSlider.setCurrentItem(position);
            }
        });
    }

    @Override
    public void loadData() {
//        showProgressDialog("Loading", getString(R.string.please_wait));
        AppLogger.e("loadData", "----------");
        addHeader();
        getBanner();
        getMostSellingProduct();
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
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        HeaderMenuAdapter headerMenuAdapter = new HeaderMenuAdapter(getActivity(), response.body().getData());
                        recycleViewHeader.setAdapter(headerMenuAdapter);
//                        SnapHelper snapHelper = new PagerSnapHelper();
//                        snapHelper.attachToRecyclerView(recycleViewHeader);

                        BestCategoryAdapter bestCategoryAdapter = new BestCategoryAdapter(getActivity(), response.body().getData());
                        recycleViewBestProducts.setAdapter(bestCategoryAdapter);
                        count--;
                        if (count == 0) {
                            hideProgressDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HeaderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    private void getBanner() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<BannerSliderItem> callApi = apiInterface.getBannerImage();
        callApi.enqueue(new Callback<BannerSliderItem>() {
            @Override
            public void onResponse(@NonNull Call<BannerSliderItem> call, @NonNull Response<BannerSliderItem> response) {
                assert response.body() != null;
                Log.e(RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)){
                        try {
//                            Glide.with(Objects.requireNonNull(getActivity()))
//                                    .load(response.body().getBannerImage())
//                                    .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
//                                    .into(imgSingleBanner);

                            HomePageSliderAdapter homePageSliderAdapter = new HomePageSliderAdapter(getActivity(), response.body().getSliderImage());
                            viewpagerSlider.setAdapter(homePageSliderAdapter);
                            pageIndicatorView.setViewPager(viewpagerSlider);
                            count--;
                            if (count == 0) {
                                hideProgressDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerSliderItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    private void getMostSellingProduct() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<MostSellingItem> callApi = apiInterface.getMostSEllingProduct();
        callApi.enqueue(new Callback<MostSellingItem>() {
            @Override
            public void onResponse(@NonNull Call<MostSellingItem> call, @NonNull Response<MostSellingItem> response) {
                assert response.body() != null;
                Log.e(RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        MostSellingAdapter mostSellingAdapter = new MostSellingAdapter(getActivity(), response.body().getData());
                        recycleViewMostSelling.setAdapter(mostSellingAdapter);

                        count--;
                        if (count == 0) {
                            hideProgressDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MostSellingItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnViewAll:
                startNewActivity(PopularProductAct.class);
                break;
        }
    }
}

