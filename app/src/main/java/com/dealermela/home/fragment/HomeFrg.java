package com.dealermela.home.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.dialog.LoginDialog;
import com.dealermela.home.activity.PopularProductAct;
import com.dealermela.home.activity.ZoomOutPageTransformer;
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
//    private FeatureCoverFlow coverFlow;
    private static int count = 3;
    private Button btnViewAll;
    private LinearLayout linBackGrad,pager_container;
    private List<PopularProductItem.ProductImg> arrayListPopularProduct = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    //Popular product circle slider
//    private FeatureCoverFlow coverFlowPopularProduct;
    ViewPager pager;
    public static int arraycount;

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

//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        Call<PopularProductItem> callApi = apiInterface.getPopularProduct(customerId);
//        callApi.enqueue(new Callback<PopularProductItem>() {
//            @Override
//            public void onResponse(@NonNull Call<PopularProductItem> call, @NonNull Response<PopularProductItem> response) {
//                assert response.body() != null;
//                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
//                assert response.body() != null;
//
//                if (response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//                        arrayListPopularProduct = response.body().getProductImg();
//
//                        SharedPreferences sharedPreferences = new SharedPreferences(getActivity());
//                        Gson gson = new Gson();
//                        sharedPreferences.savePopularProducts(gson.toJson(arrayListPopularProduct));
//                    }
//                }else {
//                    AppLogger.e("error", "------------" + response.body().toString());
//                }
//            }
//            @Override
//            public void onFailure(@NonNull Call<PopularProductItem> call, @NonNull Throwable t) {
//                AppLogger.e("error", "------------" + t.getMessage());
//            }
//        });

        arrayListPopularProduct = gson.fromJson(sharedPreferences.getPopularProducts(), type);
//
//        if (!arrayListPopularProduct.isEmpty()) {
//            coverFlow = rootView.findViewById(R.id.coverflow);
//            PopularProductCoverFlowAdapter adapter = new PopularProductCoverFlowAdapter(getActivity(), arrayListPopularProduct);
//            coverFlow.setAdapter(adapter);
//
//            coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
//                @Override
//                public void onScrolledToPosition(int position) {
//                    //TODO CoverFlow stopped to position
//                    coverFlow.clearCache();
//                }
//
//                @Override
//                public void onScrolling() {
//                    //TODO CoverFlow began scrolling
//                    coverFlow.computeScroll();
//                }
//            });
//        }

        ///////////******************Another example**********************///////////////
        pager_container = (LinearLayout) rootView.findViewById(R.id.pager_container);
        pager = (ViewPager) rootView.findViewById(R.id.viewPager);
//        mContainer.setViewPager(pager);
//        PagerAdapter adapter = new MyPagerAdapter();

        //    A little space between pages
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int pageMargin = ((metrics.widthPixels / 4) * 2);
        int pageMargin = ((metrics.widthPixels / 3));  //This is main which perfect works
        AppLogger.e("CheckPageMargin","ViewPagerActivity-----" + pageMargin);
        pager.setPageMargin(-pageMargin);

        int pagerdisplay = pager.getCurrentItem() + 1;  //set pager to 2nd value for displaying UI as coverflow bt this not works proper -- nothing happens through this code

        AppLogger.e("HomeFrg ViewPager Current Item ","------" + pagerdisplay);
        arraycount = arrayListPopularProduct.size();
        PopularProductCoverFlowAdapter adapter = new PopularProductCoverFlowAdapter(getActivity(),arrayListPopularProduct);
//        adapter = new CrouselPagerAdapter(this, getSupportFragmentManager());
        pager.setPageTransformer(true, new ZoomOutPageTransformer(true));
        pager.setAdapter(adapter);
        pager.setCurrentItem(pagerdisplay,true);

        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
//        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setOffscreenPageLimit(3);

        //A little space between pages
//***//        pager.setPageMargin((int) getResources().getDimension(R.dimen.dimen_20));
        adapter.notifyDataSetChanged();

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);

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
        sharedPreferences = new SharedPreferences(getActivity());
        if(sharedPreferences.getLoginData().isEmpty()) {
            LoginDialog loginDialogClass = new LoginDialog(getActivity());
            loginDialogClass.setCancelable(false);
            loginDialogClass.show();
            Objects.requireNonNull(loginDialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Objects.requireNonNull(loginDialogClass.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
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

