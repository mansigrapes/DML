package com.dealermela.listing_and_detail.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.LoginAct;
import com.dealermela.authentication.myaccount.dialog.MaintenanceDialogClass;
import com.dealermela.cart.activity.CartAct;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.interfaces.ProductDetailClickListener;
import com.dealermela.listing_and_detail.adapter.BangleAdapter;
import com.dealermela.listing_and_detail.adapter.BraceletsAdapter;
import com.dealermela.listing_and_detail.adapter.CaratAdapter;
import com.dealermela.listing_and_detail.adapter.DiamondAdapter;
import com.dealermela.listing_and_detail.adapter.DiamondDetailAdapter;
import com.dealermela.listing_and_detail.adapter.DiamondDetailRTSAdapter;
import com.dealermela.listing_and_detail.adapter.GemstoneDetailAdapter;
import com.dealermela.listing_and_detail.adapter.ImageSliderAdapter;
import com.dealermela.listing_and_detail.adapter.MetalAdapter;
import com.dealermela.listing_and_detail.adapter.PendentSetsAdapter;
import com.dealermela.listing_and_detail.adapter.RTSRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.RingAdapter;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.listing_and_detail.model.RTSItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.listing_and_detail.adapter.BangleAdapter.bangleProductId;
import static com.dealermela.listing_and_detail.adapter.BraceletsAdapter.braceletProductId;
import static com.dealermela.listing_and_detail.adapter.CaratAdapter.caratValue;
import static com.dealermela.listing_and_detail.adapter.CaratAdapter.metalValue;
import static com.dealermela.listing_and_detail.adapter.PendentSetsAdapter.pendentProId;
import static com.dealermela.listing_and_detail.adapter.RingAdapter.ringValue;
import static com.dealermela.other.activity.SplashAct.loginFlag;

public class ProductDetailAct extends DealerMelaBaseActivity implements View.OnClickListener, ProductDetailClickListener {

    //Product listing object
    //private ListingItem.ProductImg productImg;

    //using slider images
    private ViewPager viewPagerSlider;

    //using slider images horizontalc
    private HorizontalScrollView hsvSlider;

    //using slider images horizontal item and progress loader
    private LinearLayout linContainer, linProgress;

    //using product name,price,metal
    private TextView tvProductName, tvProductPrice;
    public TextView tvColorGold;

    //using next previous RTS item
    private ImageView imgPrevious, imgNext;

    //using Ring,Diamond,RTS,Metal layout
    private RecyclerView recycleViewReadyToShip, recycleViewRingSize, recycleViewDiamond, recycleViewCarat, recycleViewMetal, recycleViewDiamondDetail, recycleViewBangleSize, recycleViewBraceletSize, recycleViewPendentSets, recycleViewGemstoneDetail;

    //using add to cart and buy now
    private Button btnAddToCart, btnBuyNow;

    //using for product detail
    private TextView tvSku, tvCertificateNo, tvMetalPurity, tvMetalWeightApprox, tvMetalEstimatedTotal, tvMetalPrice, tvDiamondPiecesTitle, tvDiamondPrice, tvGrandTotal, tvEstimatedTotalValue, tvGemStoneDetailTitle;

    //using hide show MTO RTS items
    private View includeCustomise, includeProductDetail;

    private ConstraintLayout constraintRTS;

    //using check current position
    private static int c_position = 0;

    //using slider images
    private List<String> images = new ArrayList<>();
    private MetalAdapter metalAdapter;

    private List<String> metalList;
    private List<String> metalListCopy = new ArrayList<>();
    public String productId = "", productType = "";
    private String productCategoryId = "";

    private View viewRing, viewBangle, viewBracelet, viewPendentSets;
    private TextView tvRingSizeHeading, tvBangleSizeHeading, tvBraceletsHeading, tvPendentHeading;

    public String cProductId, cCategoryId, cProductType, cSku, cRingSize = "", cBangle, cBracelet, cPendentSet, cMetalDetail, cMetalWeight, cStoneDetail, cStoneWeight, cPrice, cQty = "1", cImageUrl, cmetalQualityColor, cmetalCarat, ctotalItem = "1";

    private DatabaseCartAdapter databaseCartAdapter;

    private Button btnSoldOut;
    private LinearLayout linButton;

    private RingAdapter ringAdapter;
    private DiamondAdapter diamondAdapter;
    private SharedPreferences sharedPreferences;
    public static int cartCheckBugNowFlag = 0;
    private RelativeLayout relBelt;
    private TextView tvBeltPrice;
    private View viewBelt;

    public static String diamondValue = "SI-IJ";
    private LinearLayout linDiamondBox;
    private TextView tvDiamondDetailLabel;
    private RelativeLayout relDiamondDetailTotal;
    private CardView cardDiamondBox;

    private String ringOptionId, ringOptionTypeId, stoneOptionId, stoneOptionTypeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_product_detail;
    }

    @Override
    public void init() {
        c_position = 0;
//        productImg = (ListingItem.ProductImg) getIntent().getSerializableExtra(AppConstants.NAME);
        productId = getIntent().getStringExtra(AppConstants.NAME);
        AppLogger.e("product id", "-------------" + productId);

        databaseCartAdapter = new DatabaseCartAdapter(ProductDetailAct.this);
        sharedPreferences = new SharedPreferences(ProductDetailAct.this);
    }

    @Override
    public void initView() {
        bindToolBar("Detail Page");
        linDiamondBox = findViewById(R.id.linDiamondBox);
        tvDiamondDetailLabel = findViewById(R.id.tvDiamondDetail);
        relDiamondDetailTotal = findViewById(R.id.relDiamondDetailTotal);
        cardDiamondBox = findViewById(R.id.cardDiamondBox);
        relBelt = findViewById(R.id.relBelt);
        tvBeltPrice = findViewById(R.id.tvBeltPrice);
        viewBelt = findViewById(R.id.viewBelt);
        viewPagerSlider = findViewById(R.id.viewPagerSlider);
        hsvSlider = findViewById(R.id.hsvSlider);
        linContainer = findViewById(R.id.linContainer);
        linProgress = findViewById(R.id.linProgress);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvColorGold = findViewById(R.id.tvColorGold);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgNext = findViewById(R.id.imgNext);
        recycleViewReadyToShip = findViewById(R.id.recycleViewReadyToShip);
        recycleViewRingSize = findViewById(R.id.recycleViewRingSize);
        recycleViewDiamond = findViewById(R.id.recycleViewDiamond);
        recycleViewCarat = findViewById(R.id.recycleViewCarat);
        recycleViewMetal = findViewById(R.id.recycleViewMetal);
        recycleViewDiamondDetail = findViewById(R.id.recycleViewDiamondDetail);
        recycleViewBangleSize = findViewById(R.id.recycleViewBangleSize);
        recycleViewBraceletSize = findViewById(R.id.recycleViewBraceletSize);
        recycleViewPendentSets = findViewById(R.id.recycleViewPendentSets);
        recycleViewGemstoneDetail = findViewById(R.id.recycleViewGemstoneDetail);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        tvSku = findViewById(R.id.tvSku);
        tvCertificateNo = findViewById(R.id.tvCertificateNo);
        tvMetalPrice = findViewById(R.id.tvMetalPrice);
        tvDiamondPiecesTitle = findViewById(R.id.tvDiamondPiecesTitle);
        tvDiamondPrice = findViewById(R.id.tvDiamondPrice);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        tvEstimatedTotalValue = findViewById(R.id.tvEstimatedValue);
        tvGemStoneDetailTitle = findViewById(R.id.tvGemStoneDetailTitle);

        tvMetalPurity = findViewById(R.id.tvMetalPurity);
        tvMetalWeightApprox = findViewById(R.id.tvMetalWeightApprox);
        tvMetalEstimatedTotal = findViewById(R.id.tvMetalEstimatedTotal);
        includeCustomise = findViewById(R.id.includeCustomise);
        includeProductDetail = findViewById(R.id.includeProductDetail);
        constraintRTS = findViewById(R.id.constraintRTS);

        viewRing = findViewById(R.id.viewRing);
        tvRingSizeHeading = findViewById(R.id.tvRingSizeHeading);

        viewBangle = findViewById(R.id.viewBangle);
        viewBracelet = findViewById(R.id.viewBracelet);
        viewPendentSets = findViewById(R.id.viewPendentSets);

        tvBangleSizeHeading = findViewById(R.id.tvBangleSizeHeading);
        tvBraceletsHeading = findViewById(R.id.tvBraceletsHeading);
        tvPendentHeading = findViewById(R.id.tvPendentHeading);

        btnSoldOut = findViewById(R.id.btnSoldOut);
        linButton = findViewById(R.id.linButton);
    }

    @Override
    public void postInitView() {
        recycleViewDiamondDetail.setNestedScrollingEnabled(false);
        //Set layout ring adapter
        GridLayoutManager gridLayoutRing = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutRing.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewRingSize.setLayoutManager(gridLayoutRing);
        recycleViewRingSize.scrollToPosition(6);

        //Set layout pendent & sets adapter
        GridLayoutManager gridLayoutPendentSets = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutPendentSets.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewPendentSets.setLayoutManager(gridLayoutPendentSets);

        //Set layout bangle adapter
        GridLayoutManager gridLayoutBangle = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutBangle.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewBangleSize.setLayoutManager(gridLayoutBangle);

        //Set layout bracelets adapter
        GridLayoutManager gridLayoutBracelet = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutBracelet.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewBraceletSize.setLayoutManager(gridLayoutBracelet);

        //Set layout diamond adapter
        GridLayoutManager gridLayoutDiamond = new GridLayoutManager(ProductDetailAct.this, 3);
        gridLayoutDiamond.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewDiamond.setLayoutManager(gridLayoutDiamond);

        //Set layout gemstone adapter
        GridLayoutManager gridLayoutGemstone = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutGemstone.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewGemstoneDetail.setLayoutManager(gridLayoutGemstone);

        //Set layout carat adapter
        GridLayoutManager gridLayoutCarat = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutCarat.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewCarat.setLayoutManager(gridLayoutCarat);

        //Set layout metal adapter
        GridLayoutManager gridLayoutMetal = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutMetal.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewMetal.setLayoutManager(gridLayoutMetal);

        // Set layout RTS adapter
        GridLayoutManager gridLayoutRts = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutRts.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewReadyToShip.setLayoutManager(gridLayoutRts);
        // Scroll RecyclerView a little to make later scroll take effect.
        recycleViewReadyToShip.scrollToPosition(0);

        //Set layout Diamond adapter
        GridLayoutManager gridLayoutDiamondDetail = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutDiamondDetail.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewDiamondDetail.setLayoutManager(gridLayoutDiamondDetail);
    }

    @Override
    public void addListener() {
        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        viewPagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                c_position = position;
                inflateThumbnails();
                if (position == 0) {
                    hsvSlider.scrollTo(0, 0);
                } else {
                    int x = 160 * position;
                    hsvSlider.scrollTo(x, 0);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void loadData() {
        getProductDetail(productId, caratValue, metalValue, "", "", "", "", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart:
                addRecord();
                break;

            case R.id.btnBuyNow:
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    Snackbar snackBar = Snackbar
                            .make(v, "Please Login, to Buy Now", Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loginFlag = 1;
                                    Intent intent = new Intent(ProductDetailAct.this, LoginAct.class);
                                    startActivity(intent);
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
                    TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackBar.show();
                } else {
                    buyNow();
                }
                break;
        }
    }

    //get all product detail
    private void getProductDetail(final String productId, String metalCarat, String metalQualityColor, String ringSize, String stoneQuality, String bangleProId, String braceletProId, String pendentProId) {

        AppLogger.e("productId", "--------------" + productId);
        AppLogger.e("metalCarat", "--------------" + metalCarat);
        AppLogger.e("metalQualityColor", "--------------" + metalQualityColor);
        AppLogger.e("ringSize", "--------------" + ringSize);
        AppLogger.e("stoneQuality", "--------------" + stoneQuality);
        AppLogger.e("bangleProId", "--------------" + bangleProId);
        AppLogger.e("braceletProId", "--------------" + braceletProId);
        AppLogger.e("pendentProId", "--------------" + pendentProId);

        linProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ProductDetailItem> callApi = apiInterface.getProductDetail(productId, metalCarat, metalQualityColor, ringSize, stoneQuality, bangleProId, braceletProId, pendentProId);
        callApi.enqueue(new Callback<ProductDetailItem>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ProductDetailItem> call, @NonNull Response<ProductDetailItem> response) {
                images = new ArrayList<>();
                assert response.body() != null;

                if(response.body() != null)
                {
                    AppLogger.e("Detail_Response-1st click on product","-------"+response.body());
                    productCategoryId = response.body().getCategoryId();
                    if (response.body().getStock().equalsIgnoreCase("0")) {
                        linButton.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.VISIBLE);
                    } else {
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }

                    if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                        viewRing.setVisibility(View.VISIBLE);
                        tvRingSizeHeading.setVisibility(View.VISIBLE);

                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);

                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);

                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);

                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);

                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);

                        viewBracelet.setVisibility(View.VISIBLE);
                        tvBraceletsHeading.setVisibility(View.VISIBLE);

                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);

                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);

                        viewBangle.setVisibility(View.VISIBLE);
                        tvBangleSizeHeading.setVisibility(View.VISIBLE);

                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);

                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);

                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);

                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);

                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);

                        viewPendentSets.setVisibility(View.VISIBLE);
                        tvPendentHeading.setVisibility(View.VISIBLE);

                    } else {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);

                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);

                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);

                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);
                    }

                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(ProductDetailAct.this, response.body().getSlider());
                    cImageUrl = response.body().getSlider().get(0);
                    viewPagerSlider.setAdapter(imageSliderAdapter);
                    images.addAll(response.body().getSlider());
                    inflateThumbnails();

                    productType = response.body().getProducts();

                    AppLogger.e("product type", "----------" + productType);

                    //using for Image slider
                    if (response.body().getProducts().equalsIgnoreCase("simple")) {
                        includeCustomise.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getRtsSlider().size(); i++) {
                            if (response.body().getRtsSlider().get(i).getEntityId().equalsIgnoreCase(productId)) {
                                AppLogger.e("scroll position", "----------" + i);
                                recycleViewReadyToShip.scrollToPosition(i - 1);
                            }
                        }
                        //using for rts adapter
                        RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                        recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);

                    } else {
                        cProductId = response.body().getSimpleProductId();
                        includeCustomise.setVisibility(View.VISIBLE);
                        //using for ring adapter
                        ringAdapter = new RingAdapter(ProductDetailAct.this, response.body().getRingsize());
                        recycleViewRingSize.setAdapter(ringAdapter);

                        if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)){
                            cRingSize = ringValue;
                            AppLogger.e("ArrayRing_size","----"+response.body().getRingsize().size());
                            for(int j = 0 ; j < response.body().getRingsize().size(); j++ ){
                                if(response.body().getRingsize().get(j).getTitle().equalsIgnoreCase(ringValue)){
                                    ringOptionId = response.body().getRingsize().get(j).getOptionId();
                                    ringOptionTypeId = response.body().getRingsize().get(j).getOptionTypeId();
                                }
                            }
                            AppLogger.e("DetailpageRefresh_RingId","-----"+ ringOptionId);
                            AppLogger.e("DetailpageRefresh_RingTypeId","-----"+ ringOptionTypeId);
                        }

                        //using for Bangle adapter
                        if (!response.body().getBangleSize().isEmpty()) {
                            response.body().getBangleSize().get(0).setSelected(true);
                            bangleProductId = response.body().getBangleSize().get(0).getProductId();
                            cBangle = response.body().getBangleSize().get(0).getLabel();
                            BangleAdapter bangleAdapter = new BangleAdapter(ProductDetailAct.this, response.body().getBangleSize());
                            recycleViewBangleSize.setAdapter(bangleAdapter);
                        }

                        //using for Bracelets adapter
                        if (!response.body().getBraceletsSize().isEmpty()) {
                            response.body().getBraceletsSize().get(0).setSelected(true);
                            braceletProductId = response.body().getBraceletsSize().get(0).getProductId();
                            cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                            BraceletsAdapter braceletsAdapter = new BraceletsAdapter(ProductDetailAct.this, response.body().getBraceletsSize());
                            recycleViewBraceletSize.setAdapter(braceletsAdapter);
                        }

                        //using for pendent and sets adapter
                        if (!response.body().getPendentEarring().isEmpty()) {
                            AppLogger.e("pendent earning size", "--------------" + response.body().getPendentEarring().size());
//                            if (response.body().getPendentEarring().size() > 1) {
//                                response.body().getPendentEarring().get(0).setSelected(true);
//                            } else if (response.body().getPendentEarring().size() > 0) {
//                                response.body().getPendentEarring().get(1).setSelected(true);
//                            } else {
//                                response.body().getPendentEarring().get(0).setSelected(true);
//                            }

                            for(int j = 0; j < response.body().getPendentEarring().size(); j++ ){
                                if(response.body().getPendentEarring().get(j).getProductId().equalsIgnoreCase(cProductId)){
                                    response.body().getPendentEarring().get(j).setSelected(true);
                                }
                            }

                            PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
                            recycleViewPendentSets.setAdapter(pendentSetsAdapter);
                        }

                        //using for diamond adapter
                        diamondAdapter = new DiamondAdapter(ProductDetailAct.this, response.body().getStoneClarity());
                        recycleViewDiamond.setAdapter(diamondAdapter);

                        //using for carat adapter
                        CaratAdapter caratAdapter = new CaratAdapter(ProductDetailAct.this, response.body().getCarat());
                        recycleViewCarat.setAdapter(caratAdapter);

                        //using for metal adapter
                        metalList = new ArrayList<>();
                        metalList.addAll(response.body().getMetal());
                        metalListCopy.addAll(response.body().getMetal());

                        if (caratValue.equalsIgnoreCase("14K")) {
                            metalList.remove("Platinum(950)");

                        } else if (caratValue.equalsIgnoreCase("18K")) {
                            metalList.remove("Platinum(950)");

                        } else if (caratValue.equalsIgnoreCase("Platinum(950)")) {
                            metalList.remove("Rose Gold");
                            metalList.remove("White Gold");
                            metalList.remove("Yellow Gold");
                            metalList.remove("Two Tone Gold");
                            metalList.remove("Three Tone Gold");
                        }
                        metalAdapter = new MetalAdapter(ProductDetailAct.this, metalList);
                        recycleViewMetal.setAdapter(metalAdapter);

                        //using for rts adapter
                        if (!response.body().getRtsSlider().isEmpty()) {
                            RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                            recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);
                        }
                    }

                    //using for Diamond detail adapter
                    if (!response.body().getDiamonddetails().isEmpty()) {
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        DiamondDetailAdapter diamondDetailAdapter = new DiamondDetailAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                        recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                    } else {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                    }

                    //using for gemstone detail adapter
                    if (!response.body().getGemstonedetails().isEmpty()) {
                        tvGemStoneDetailTitle.setVisibility(View.VISIBLE);
                        recycleViewGemstoneDetail.setVisibility(View.VISIBLE);
                        GemstoneDetailAdapter gemstoneDetailAdapter = new GemstoneDetailAdapter(ProductDetailAct.this, response.body().getGemstonedetails());
                        recycleViewGemstoneDetail.setAdapter(gemstoneDetailAdapter);
                    }

                    //Product Detail
                    tvProductName.setText(response.body().getProductDetails().get(0).getProductName());
                    // bindToolBar(response.body().getProductDetails().get(0).getProductName());
                    tvSku.setText(response.body().getProductDetails().get(0).getSku());
                    cSku = response.body().getProductDetails().get(0).getSku();
                    tvCertificateNo.setText(response.body().getProductDetails().get(0).getCertificateNo());

//                    AppLogger.e("MetalPrice","---" + response.body().getMetalprice().getPrice());
//                    //Metal Diamond Detail
//                    if(response.body().getMetalprice().getPrice() == null){
//                        tvMetalPrice.setText(AppConstants.RS + "0");
//                    }else {
//                        Float metalPrice = Float.parseFloat(response.body().getMetalprice().getPrice().toString());
////                        tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
//                        tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));
//                        AppLogger.e("MetalPrice ","---" + response.body().getMetalprice().getPrice());
//                    }

                    AppLogger.e("MetalPrice","---" + response.body().getMetalprice());
                    //Metal Diamond Detail
                    if(response.body().getMetalprice() == null){
                        tvMetalPrice.setText(AppConstants.RS + "0");
                    }else {
                        Float metalPrice = Float.parseFloat(response.body().getMetalprice().toString());
//                        tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
                        tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));
                        AppLogger.e("MetalPrice ","---" + response.body().getMetalprice());
                    }

                    cMetalWeight = response.body().getMetaldetails().get(0).getMetalweight();

    //                metalPrice = metalPrice.substring(1, metalPrice.length() - 1);
    //                tvMetalPrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(metalPrice));

                    if (response.body().getDiamondmainprice().isEmpty()) {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                    } else {
                        tvDiamondPiecesTitle.setText("Diamond (" + response.body().getDiamondmainprice().get(0).getPices() + ")");
                        tvDiamondPrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                        try {
                            tvEstimatedTotalValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    tvMetalPurity.setText(response.body().getMetaldetails().get(0).getMetalquality());
                    if(response.body().getMetaldetails().get(0).getMetalweight() == null){
                        tvMetalWeightApprox.setText(" ");
                    }else {
                        tvMetalWeightApprox.setText(response.body().getMetaldetails().get(0).getMetalweight() + " gms");
                    }

                    if (productCategoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || productCategoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)) {
                        relBelt.setVisibility(View.VISIBLE);
                        viewBelt.setVisibility(View.VISIBLE);
                        float beltPrice = Float.parseFloat(response.body().getBelt_price());
                        tvBeltPrice.setText(AppConstants.RS + CommonUtils.priceFormat(beltPrice));
                    }
//                    tvMetalEstimatedTotal.setText(AppConstants.RS + response.body().getMetaldetails().get(0).getMetalestimatedprice());
                    int value = response.body().getMetaldetails().get(0).getMetalestimatedprice();
                    tvMetalEstimatedTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(String.valueOf(value))));

                    linProgress.setVisibility(View.GONE);

                    cPrice = response.body().getProductDetails().get(0).getPrice();
                    tvGrandTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    if(response.body().getProductDetails().get(0).getPrice() == "0"){
                        tvProductPrice.setText(AppConstants.RS + "0");
                    }else{
                        tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    }
                    tvColorGold.setText("(" + caratValue + " " + metalValue + ")");
                    cMetalDetail = caratValue + " " + metalValue;
                    cStoneDetail = diamondValue;

                    if (!response.body().getDiamonddetails().isEmpty()) {
                        cStoneWeight = response.body().getDiamonddetails().get(0).getTotalweight();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductDetailItem> call, @NonNull Throwable t) {
                linProgress.setVisibility(View.GONE);
                AppLogger.e(AppConstants.ERROR, "------------" + t.getMessage());
                MaintenanceDialogClass dialogClass = new MaintenanceDialogClass(ProductDetailAct.this);
                dialogClass.show();
                Objects.requireNonNull(dialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Objects.requireNonNull(dialogClass.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    private void productDetailRefresh(final String productId, String metalCarat, String metalQualityColor, String ringSize, String stoneQuality, String bangleProId, String braceletProId, String pendentProId, final String clickAction) {
        AppLogger.e("Refresh_product Id", "-------" + productId);
        AppLogger.e("Refresh_metalCarat", "-------" + metalCarat);
        AppLogger.e("Refresh_metalQualityColor", "-------" + metalQualityColor);
        AppLogger.e("Refresh_ringSize", "-------" + ringSize);
        AppLogger.e("Refresh_stoneQuality", "-------" + stoneQuality);
        AppLogger.e("Refresh_bangleProId", "-------" + bangleProId);
        AppLogger.e("Refresh_braceletProId", "-------" + braceletProId);
        AppLogger.e("Refresh_pendentProId", "-------" + pendentProId);
        AppLogger.e("Refresh_clickAction", "-------" + clickAction);

        linProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ProductDetailItem> callApi = apiInterface.getProductDetail(productId, metalCarat, metalQualityColor, ringSize, stoneQuality, bangleProId, braceletProId, pendentProId);
        callApi.enqueue(new Callback<ProductDetailItem>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ProductDetailItem> call, @NonNull Response<ProductDetailItem> response) {
                images = new ArrayList<>();
//                assert response.body() != null;
                if (response.body() != null) {
                    AppLogger.e("Refresh product Response", "---------------" + response.body().toString());
                    AppLogger.e(AppConstants.RESPONSE, "------------" + response.body().getSlider());

                    cProductId = response.body().getSimpleProductId();
                    if (response.body().getStock().equalsIgnoreCase("0")) {
                        linButton.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.VISIBLE);
                    } else {
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }

                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(ProductDetailAct.this, response.body().getSlider());
                    cImageUrl = response.body().getSlider().get(0);
                    viewPagerSlider.setAdapter(imageSliderAdapter);
                    images.addAll(response.body().getSlider());
                    inflateThumbnails();

                    //using for ring adapter
                    ringAdapter = new RingAdapter(ProductDetailAct.this, response.body().getRingsize());
                    recycleViewRingSize.setAdapter(ringAdapter);
                    ringAdapter.notifyItemRangeChanged(0, ringAdapter.getItemCount());

                    if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)){
                        cRingSize = ringValue;
                        AppLogger.e("ArrayRing_size","----"+response.body().getRingsize().size());
                        for(int j = 0 ; j < response.body().getRingsize().size(); j++ ){
                            if(response.body().getRingsize().get(j).getTitle().equalsIgnoreCase(ringValue)){
                                ringOptionId = response.body().getRingsize().get(j).getOptionId();
                                ringOptionTypeId = response.body().getRingsize().get(j).getOptionTypeId();
                            }
                        }
                        AppLogger.e("DetailpageRefresh_RingId","-----"+ ringOptionId);
                        AppLogger.e("DetailpageRefresh_RingTypeId","-----"+ ringOptionTypeId);
                    }

                    //using for rts adapter
                    if (!response.body().getRtsSlider().isEmpty()) {
                        RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                        recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);
                    }

                    //using for diamond adapter
                    if (!response.body().getStoneClarity().isEmpty()) {
                        diamondAdapter = new DiamondAdapter(ProductDetailAct.this, response.body().getStoneClarity());
                        recycleViewDiamond.setAdapter(diamondAdapter);
                    }

                    if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID) || productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {

                        if (clickAction.equalsIgnoreCase("carat")) {
                            //using for Bangle adapter
                            if (!response.body().getBangleSize().isEmpty()) {
                                response.body().getBangleSize().get(0).setSelected(true);
                                bangleProductId = response.body().getBangleSize().get(0).getProductId();
                                cBangle = response.body().getBangleSize().get(0).getLabel();
                                BangleAdapter bangleAdapter = new BangleAdapter(ProductDetailAct.this, response.body().getBangleSize());
                                recycleViewBangleSize.setAdapter(bangleAdapter);
                            }

                            //using for Bracelets adapter
                            if (!response.body().getBraceletsSize().isEmpty()) {
                                response.body().getBraceletsSize().get(0).setSelected(true);
                                braceletProductId = response.body().getBraceletsSize().get(0).getProductId();
                                cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                                BraceletsAdapter braceletsAdapter = new BraceletsAdapter(ProductDetailAct.this, response.body().getBraceletsSize());
                                recycleViewBraceletSize.setAdapter(braceletsAdapter);
                            }
                        }
                    }

                    //Earrings , Pendants , Pendant & sets

//                    if(productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
//
//                    }

//                    //using for pendent and sets adapter
                    if (!response.body().getPendentEarring().isEmpty()) {
                        AppLogger.e("pendent earning size_refresh", "--------------" + response.body().getPendentEarring().size());
   /*                     if (response.body().getPendentEarring().size() > 1) {
                            response.body().getPendentEarring().get(0).setSelected(true);
                        } else if (response.body().getPendentEarring().size() > 0) {
                            response.body().getPendentEarring().get(1).setSelected(true);
                        } else {
                            response.body().getPendentEarring().get(0).setSelected(true);
                        }
                        */      //Old code PendentEarrings Type Selected as on Simpleproductid & in this array id matched

                        for(int j = 0; j < response.body().getPendentEarring().size(); j++ ){
                            if(response.body().getPendentEarring().get(j).getProductId().equalsIgnoreCase(cProductId)){
                                response.body().getPendentEarring().get(j).setSelected(true);
                            }
                        }

                        PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
                        recycleViewPendentSets.setAdapter(pendentSetsAdapter);

                    }

//                    if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)) {
//                        PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
//                        recycleViewPendentSets.setAdapter(pendentSetsAdapter);
//                    }

                    // cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                    //using for Diamond detail adapter

                    if (response.body().getDiamonddetails() != null) {
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        DiamondDetailAdapter diamondDetailAdapter = new DiamondDetailAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                        recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                    } else {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                    }

                    //Product Detail
                    tvProductName.setText(response.body().getProductDetails().get(0).getProductName());
                    tvSku.setText(response.body().getProductDetails().get(0).getSku());
                    cSku = response.body().getProductDetails().get(0).getSku();
                    tvCertificateNo.setText(response.body().getProductDetails().get(0).getCertificateNo());

                    //Metal Diamond Detail
                    //                metalPrice = metalPrice.substring(1, metalPrice.length() - 1);

//                    if (response.body().getMetalprice().getPrice() == null) {
//                        tvMetalPrice.setText(AppConstants.RS + "0");
//                    } else {
//                        Float metalPrice = Float.parseFloat(response.body().getMetalprice().getPrice().toString());
//                        //                tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
//                        tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));
//                    }

                    AppLogger.e("MetalPrice","---" + response.body().getMetalprice());
                    //Metal Diamond Detail
                    if(response.body().getMetalprice() == null){
                        tvMetalPrice.setText(AppConstants.RS + "0");
                    }else {
                        Float metalPrice = Float.parseFloat(response.body().getMetalprice().toString());
//                        tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
                        tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));
                        AppLogger.e("MetalPrice ","---" + response.body().getMetalprice());
                    }

                    tvDiamondPiecesTitle.setText("Diamond (" + response.body().getDiamondmainprice().get(0).getPices() + ")");
                    tvDiamondPrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                    tvEstimatedTotalValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));

                    cMetalWeight = response.body().getMetaldetails().get(0).getMetalweight();

                    AppLogger.e("metal purity", "----------" + response.body().getMetaldetails().get(0).getMetalquality());
                    tvMetalPurity.setText(response.body().getMetaldetails().get(0).getMetalquality());
                    tvMetalWeightApprox.setText(response.body().getMetaldetails().get(0).getMetalweight() + " gms");
                    int value = response.body().getMetaldetails().get(0).getMetalestimatedprice();
                    tvMetalEstimatedTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(String.valueOf(value))));

                    linProgress.setVisibility(View.GONE);

                    //  float grandTotal = metalPrice + Float.parseFloat(response.body().getDiamondmainprice().get(0).getDimondprice());

                    cPrice = response.body().getProductDetails().get(0).getPrice();
                    tvGrandTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    if (response.body().getProductDetails().get(0).getPrice() == "0") {
                        tvProductPrice.setText(AppConstants.RS + "0");
                    } else {
                        tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    }

                    if (caratValue.equalsIgnoreCase("Platinum(950)")) {
                        tvColorGold.setText("(" + caratValue + ")");
                        cMetalDetail = caratValue;
                    } else {
                        tvColorGold.setText("(" + caratValue + " " + metalValue + ")");
                        cMetalDetail = caratValue + " " + metalValue;
                    }
                    cStoneDetail = diamondValue;
                    //                response.body().getDiamonddetails().get(0).getTotalweight();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductDetailItem> call, @NonNull Throwable t) {
                linProgress.setVisibility(View.GONE);
                AppLogger.e(AppConstants.ERROR, "------------" + t.getMessage());
            }
        });
    }

    //add horizontal slide image
    private void inflateThumbnails() {
        linContainer.removeAllViews();
        View imageLayout;
        ImageView imageView;
        ConstraintLayout main_layout;
        for (int i = 0; i < images.size(); i++) {
            if(images.get(i)!=null) {
                imageLayout = getLayoutInflater().inflate(R.layout.act_product_detail_item_image, null);
                imageView = imageLayout.findViewById(R.id.image_linear);
                main_layout = imageLayout.findViewById(R.id.main_layout);

                imageView.setOnClickListener(
                        onChagePageClickListener(i)
                );

                if (c_position == i) {
                    imageView.setBackground(getResources().getDrawable(R.drawable.product_item_select));
                } else {
                    imageView.setBackground(getResources().getDrawable(R.drawable.product_item_un_select));
                }

                    Glide.with(ProductDetailAct.this)
                            .load(images.get(i))
                            .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                            .into(imageView);
    //            imageView.setImageResource(images.get(i).getFile());

                linContainer.addView(imageLayout);
            }
        }
    }

    //horizontal slide image click
    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSlider.setCurrentItem(i);
                c_position = i;
                inflateThumbnails();
            }
        };
    }

    @Override
    public void filterClick(String commonIdName, String clickAction) {
        if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
            productDetailRefresh(productId, caratValue, metalValue, ringValue, diamondValue, "", "", "", clickAction);
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", braceletProductId, "", clickAction);
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID)) {
//            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, bangleProductId, "", "", clickAction);
            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);   // Change for configurable products
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
//            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", pendentProId, clickAction);
            productDetailRefresh(cProductId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);
        } else {
            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);
        }
    }

    @Override
    public void rtsClick(String productId) {
        AppLogger.e("product id", "--------" + productId);
        rtsProductClick(productId);
    }

    @SuppressLint("SetTextI18n")
    public void refreshAdapter() {

        //using for metal adapter
        metalList = new ArrayList<>();
        metalList.addAll(metalListCopy);

        if (caratValue.equalsIgnoreCase("14K")) {
            metalList.remove("Platinum(950)");

        } else if (caratValue.equalsIgnoreCase("18K")) {
            metalList.remove("Platinum(950)");

        } else if (caratValue.equalsIgnoreCase("Platinum(950)")) {
            metalList.remove("Rose Gold");
            metalList.remove("White Gold");
            metalList.remove("Yellow Gold");
            metalList.remove("Two Tone Gold");
            metalList.remove("Three Tone Gold");
//            metalList.remove("Two Tone");
            tvColorGold.setText("(" + caratValue + ")");
            cMetalDetail = caratValue;
        }

        metalAdapter = new MetalAdapter(ProductDetailAct.this, metalList);
        recycleViewMetal.setAdapter(metalAdapter);

    }

    private void rtsProductClick(String productId) {
        AppLogger.e("rts click", "---------" + productId);
        linProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<RTSItem> callApi = apiInterface.getRTSDetail(productId);
        callApi.enqueue(new Callback<RTSItem>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<RTSItem> call, @NonNull Response<RTSItem> response) {

                //using for Diamond detail adapter
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    if (response.body().getStock().equalsIgnoreCase("0")) {
                        linButton.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.VISIBLE);
                    } else {
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }
                    AppLogger.e("getDiamonddetails", "-------" + response.body().getDiamonddetails());
                    if (response.body().getDiamonddetails() != null) {
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        DiamondDetailRTSAdapter diamondDetailAdapter = new DiamondDetailRTSAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                        recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                    } else {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                    }
                    //Product Detail
                    assert response.body() != null;
                    tvProductName.setText(response.body().getProductDetails().get(0).getProductName());
                    tvSku.setText(response.body().getProductDetails().get(0).getSku());
                    cSku = response.body().getProductDetails().get(0).getSku();
                    tvCertificateNo.setText(response.body().getProductDetails().get(0).getCertificateNo());
                    cMetalWeight = response.body().getMetaldetails().get(0).getMetalweight();
                    //Metal Diamond Detail
                    Float metalPrice = Float.parseFloat(response.body().getMetalprice().get(0).toString());
//                metalPrice = metalPrice.substring(1, metalPrice.length() - 1);
//                    tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
                    tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));
                    if (response.body().getDiamondmainprice().isEmpty()) {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);

                    } else {
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondPiecesTitle.setText("Diamond (" + response.body().getDiamondmainprice().get(0).getPices() + ")");
                        tvDiamondPrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                        AppLogger.e("DiamondPieces" ,response.body().getDiamondmainprice().get(0).getPices());
                        AppLogger.e("DiamondPrice ",response.body().getDiamondmainprice().get(0).getDimondprice());
                        tvEstimatedTotalValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                    }
                    tvMetalPurity.setText(response.body().getMetaldetails().get(0).getMetalquality());
                    tvMetalWeightApprox.setText(response.body().getMetaldetails().get(0).getMetalweight() + " gms");
//                    tvMetalEstimatedTotal.setText(AppConstants.RS + response.body().getMetaldetails().get(0).getMetalestimatedprice());
                    int value = response.body().getMetaldetails().get(0).getMetalestimatedprice();
                    tvMetalEstimatedTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(String.valueOf(value))));
                    linProgress.setVisibility(View.GONE);
                    if (productCategoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || productCategoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)) {
                        relBelt.setVisibility(View.VISIBLE);
                        viewBelt.setVisibility(View.VISIBLE);
                        float beltPrice = Float.parseFloat(response.body().getBelt_price());
                        tvBeltPrice.setText(AppConstants.RS + CommonUtils.priceFormat(beltPrice));

                    }
                    tvGrandTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    if(response.body().getProductDetails().get(0).getPrice() == "0"){
                        tvProductPrice.setText(AppConstants.RS + "0");
                    }else{
                        tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    }
//                tvColorGold.setText("(" + caratValue + " " + metalValue + ")");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RTSItem> call, @NonNull Throwable t) {
                linProgress.setVisibility(View.GONE);
            }
        });
    }

    //product add to cart
    public void addRecord() {

        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            Cursor cursor;
            databaseCartAdapter.openDatabase();
            cursor = databaseCartAdapter.findRecordCheck(cProductId);
            if (cursor.getCount() > 0) {
                AppLogger.e("Record", "-------Found");
                CommonUtils.showWarningToast(ProductDetailAct.this, "Product is already in cart");
            } else {
                AppLogger.e("Record", "-------Not found");
                if (productType.equalsIgnoreCase("simple")) {
                    ringOptionId = "";
                    ringOptionTypeId = "";
                    stoneOptionId = "";
                    stoneOptionTypeId = "";
                } else {
                    if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                        cRingSize = ringValue;
                        ringOptionId = ringAdapter.ringOptionId;
                        ringOptionTypeId = ringAdapter.ringOptionTypeId;
                        AppLogger.e("Parameter For CART-", ringOptionId);
                        AppLogger.e("TypeId For Cart", ringOptionTypeId);
                    } else {
                        ringOptionId = "";
                        ringOptionTypeId = "";
                    }
                    stoneOptionId = diamondAdapter.stoneOptionId;
                    stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;
                    if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
                       cProductId= pendentProId.toString();
                        AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
                    }

                }
                cmetalQualityColor = metalValue;
                cmetalCarat =  caratValue;
                cProductType = productType;
                cCategoryId = productCategoryId;
//                cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                databaseCartAdapter.addValues(cProductId,
                        cCategoryId,
                        cProductType,
                        cSku,
                        cRingSize,
                        cBangle,
                        cBracelet,
                        cPendentSet,
                        cMetalDetail + "(" + cMetalWeight + "gms)",
                        cStoneDetail + "(" + cStoneWeight + "ct)",
                        cPrice,
                        cQty,
                        cImageUrl,
                        ringOptionId,
                        ringOptionTypeId,
                        stoneOptionId,
                        stoneOptionTypeId,
                        cmetalQualityColor,
                        cmetalCarat,
                        ctotalItem);

//                Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
                CommonUtils.showSuccessToast(ProductDetailAct.this, "Item added in cart.");
                cartCount++;
                setupBadge();
                databaseCartAdapter.closeDatabase();
            }
            cursor.close();
        } else {
            if (productType.equalsIgnoreCase("simple")) {
                ringOptionId = "";
                ringOptionTypeId = "";
                stoneOptionId = "";
                stoneOptionTypeId = "";

            } else {
                if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                    cRingSize = ringValue;
//                    ringOptionId = ringAdapter.ringOptionId;
//                    ringOptionTypeId = ringAdapter.ringOptionTypeId;
                    ringOptionId = ringOptionId.toString();
                    ringOptionTypeId = ringOptionTypeId.toString();

                } else {
                    ringOptionId = "";
                    ringOptionTypeId = "";
                }
                stoneOptionId = diamondAdapter.stoneOptionId;
                stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;
//                if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//                    cProductId= pendentProId.toString();
//                    AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                }
            }

            addToCart(cProductId, customerId, ringOptionId, ringOptionTypeId, stoneOptionId, stoneOptionTypeId, "1",metalValue, caratValue);
        }
    }

    public void buyNow() {
//        String ringOptionId;
//        String ringOptionTypeId;
//        String stoneOptionId;
//        String stoneOptionTypeId;

        if (productType.equalsIgnoreCase("simple")) {
            ringOptionId = "";
            ringOptionTypeId = "";
            stoneOptionId = "";
            stoneOptionTypeId = "";
        } else {
            if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                cRingSize = ringValue;
//                ringOptionId = ringAdapter.ringOptionId;
//                ringOptionTypeId = ringAdapter.ringOptionTypeId;
                ringOptionId = ringOptionId.toString();
                ringOptionTypeId = ringOptionTypeId.toString();

            } else {
                ringOptionId = "";
                ringOptionTypeId = "";
            }
            stoneOptionId = diamondAdapter.stoneOptionId;
            stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;
            if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
                cProductId= pendentProId;
                AppLogger.e("BuyNow","cProductId---pendentProId----" + cProductId);
            }
        }

        buyNow(cProductId, customerId, ringOptionId, ringOptionTypeId, stoneOptionId, stoneOptionTypeId, "1", metalValue, caratValue);
    }

    private void addToCart(String productId, String customerId, String optionId, String optionTypeId, String stoneOptionId, String stoneOptionTypeId, String qty, String metalQualityColor, String metalCarat) {

        AppLogger.e("product Id", "-------" + productId);
        AppLogger.e("customerId", "-------" + customerId);
        AppLogger.e("optionId", "-------" + optionId);
        AppLogger.e("optionTypeId", "-------" + optionTypeId);
        AppLogger.e("stoneOptionId", "-------" + stoneOptionId);
        AppLogger.e("stoneOptionTypeId", "-------" + stoneOptionTypeId);
        AppLogger.e("qty", "-------" + qty);
        AppLogger.e("metalColor", "-------" + metalQualityColor);
        AppLogger.e("caratValue", "-------" + metalCarat);

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addToCart(productId, customerId, optionId, optionTypeId, stoneOptionId, stoneOptionTypeId, qty, metalQualityColor, metalCarat);
        callApi.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                hideProgressDialog();
                String message=null;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        CommonUtils.showSuccessToast(ProductDetailAct.this, "Item added in cart.");
                        cartCount++;
                        setupBadge();
                    }else {
                        message=jsonObject.getString("message");
                        CommonUtils.showWarningToast(ProductDetailAct.this,message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideProgressDialog();
                AppLogger.e("Error","---------"+ t.getMessage());
            }
        });
    }

    private void buyNow(String productId, String customerId, String optionId, String optionTypeId, String stoneOptionId, String stoneOptionTypeId, String qty, String metalQualityColor, String metalCarat) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addToCart(productId, customerId, optionId, optionTypeId, stoneOptionId, stoneOptionTypeId, qty, metalQualityColor, metalCarat);
        callApi.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                hideProgressDialog();
                String message=null;
                try {
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//                        CommonUtils.showToast(ProductDetailAct.this, "Item added in cart.");
                        cartCount++;
                        setupBadge();
                        cartCheckBugNowFlag = 1;
                        startNewActivity(CartAct.class);
                    }else{
                        message=jsonObject.getString("message");
                        CommonUtils.showWarningToast(ProductDetailAct.this,message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideProgressDialog();
            }
        });
    }
}
