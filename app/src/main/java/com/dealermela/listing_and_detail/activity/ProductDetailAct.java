package com.dealermela.listing_and_detail.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.NetworkUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.dealermela.listing_and_detail.adapter.GemstoneDetailRTSAdapter;
import com.dealermela.listing_and_detail.adapter.ImageSliderAdapter;
import com.dealermela.listing_and_detail.adapter.MetalAdapter;
import com.dealermela.listing_and_detail.adapter.PendentSetsAdapter;
import com.dealermela.listing_and_detail.adapter.RTSRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.RingAdapter;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.listing_and_detail.model.RTSItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.ThemePreferences;
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
import static com.dealermela.home.adapter.PopularProductCoverFlowAdapter.screenHeight;
import static com.dealermela.home.adapter.PopularProductCoverFlowAdapter.screenWidth;
import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.listing_and_detail.activity.FilterAct.pagecountflag;
import static com.dealermela.listing_and_detail.adapter.BangleAdapter.bangleProductId;
import static com.dealermela.listing_and_detail.adapter.BraceletsAdapter.braceletProductId;
import static com.dealermela.listing_and_detail.adapter.CaratAdapter.caratValue;
import static com.dealermela.listing_and_detail.adapter.MetalAdapter.metalValue;
import static com.dealermela.listing_and_detail.adapter.PendentSetsAdapter.pendentProId;
import static com.dealermela.listing_and_detail.adapter.RingAdapter.ringValue;
import static com.dealermela.authentication.myaccount.activity.LoginAct.cartbackFlag;
import static com.dealermela.listing_and_detail.adapter.RTSRecyclerAdapter.Rtsflag;

public class ProductDetailAct extends DealerMelaBaseActivity implements View.OnClickListener, ProductDetailClickListener {

    //Product listing object
    //private ListingItem.ProductImg productImg;

    //using slider images
    private ViewPager viewPagerSlider;

    //using slider images horizontalc
    private HorizontalScrollView hsvSlider;

    //using slider images horizontal item and progress loader
    private LinearLayout linContainer, linProgress, layout_customize;

    //using product name,price,metal
    private TextView tvProductName, tvProductPrice;
    public TextView tvColorGold;

    //using next previous RTS item
    private ImageView imgPrevious, imgNext, image_gia, image_igi, btnInfo;

    //using Ring,Diamond,RTS,Metal layout
    public static RecyclerView recycleViewReadyToShip, recycleViewRingSize, recycleViewDiamond, recycleViewCarat, recycleViewMetal, recycleViewDiamondDetail, recycleViewBangleSize, recycleViewBraceletSize, recycleViewPendentSets, recycleViewGemstoneDetail, recycleViewTestPendentsets;

    //For customize popup box
    private LinearLayout lin_Ring_Size,lin_Pendant_Type,lin_Bangle_Size,lin_Bracelet_Size;

    //using add to cart and buy now
    private Button btnAddToCart, btnBuyNow , tvCustomizePrice;

    //using for product detail
    public static TextView tvSku, tvCertificateNo, tvMetalPurity, tvMetalWeightApprox, tvMetalEstimatedTotal, tvMetalPrice, tvDiamondPiecesTitle, tvDiamondPrice, tvGemstonePiecesTitle, tvGemstonePrice,tvGrandTotal, tvEstimatedTotalValue, tvGemStoneDetailTitle, tvGemstoneEstimatedValue, tvMetalDetailTitle;

    //using hide show MTO RTS items
    private View includeCustomise, includeProductDetail;

    private ConstraintLayout constraintRTS;
//    private LinearLayout constraintRTS;

    //using check current position
    private static int c_position = 0;

    //using slider images
    private List<String> images = new ArrayList<>();
//    private MetalAdapter metalAdapter;

    private List<String> metalList;
    private List<String> metalListCopy = new ArrayList<>();
    public String productId = "", productType = "";
    private String productCategoryId = "";

    private View viewRing, viewBangle, viewBracelet, viewPendentSets;
    private TextView tvRingSizeHeading, tvBangleSizeHeading, tvBraceletsHeading, tvPendentHeading;

    public String cProductId, cCategoryId, cProductType, cSku, cRingSize = "", cBangle, cBracelet, cPendentSet, cMetalDetail, cMetalWeight, cStoneDetail, cStoneWeight, cPrice, cQty = "1", cImageUrl, cmetalQualityColor, cmetalCarat, ctotalItem, cProductCategoryType, cProductSKU, cProductCategoryId ;

    private DatabaseCartAdapter databaseCartAdapter;

    private Button btnSoldOut,btncustomize,btnProductDetail,btncancel;
    public static Button btnapply;

    private LinearLayout linButton,linsoldout,productInfoPS;

    private RingAdapter ringAdapter;
    private DiamondAdapter diamondAdapter;
    private SharedPreferences sharedPreferences;
    public static int cartCheckBugNowFlag = 0;
    private RelativeLayout relBelt;
    private TextView tvBeltPrice;
    private View viewBelt;

    public static String diamondValue = "SI-IJ";
    private LinearLayout linDiamondBox,relDetailImage;
    public static TextView tvDiamondDetailLabel, tvBeltDetailTitle;
    public static RelativeLayout relDiamondDetailTotal,relGemstoneDetailTotal, relMetalPurity, relMetalWeight, relMetalTotal;
    private CardView cardDiamondBox,cardGemstoneBox,cardViewGemstone, cardviewbelt ,cardviewProductInfo, cardViewDiamond;
    private String ringOptionId, ringOptionTypeId, stoneOptionId, stoneOptionTypeId, categoryid, List_metal_color, List_carat_value;
    private ThemePreferences themePreferences;
    public static String TypeValue = "";
    public static String SizeValue = "";

    public static BottomSheetDialog mBottomSheetDialog, mBottomDialog_detail;
    public static int customizeflag = 0, CustomizeClick = 0 ;
//    public int clickcustomize = 0, clickmoredetail = 0 ;
    public static View productdetailview;
    public static NestedScrollView ProductDetailScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themePreferences = new ThemePreferences(ProductDetailAct.this);

//        if(themePreferences.getTheme().equalsIgnoreCase("black")){
//            image_igi.setImageResource(R.drawable.igi_logo_full_white);
//            image_gia.setImageResource(R.drawable.gia_white);
//        }else {
//            image_igi.setImageResource(R.drawable.igi_new);
//            image_gia.setImageResource(R.drawable.gia_new);
//        }
        // Comment this define on btn's click event
//        if(themePreferences.getTheme().equalsIgnoreCase("black")){
//            image_igi.setImageResource(R.drawable.ic_igi_white_new);
//            image_gia.setImageResource(R.drawable.ic_gemological_institute_of_america_gia_white);
//        }else {
//            image_igi.setImageResource(R.drawable.ic_igi_new);
//            image_gia.setImageResource(R.drawable.ic_gemological_institute_of_america_gia);
//        }
        AppLogger.e("ProductDetailAct","---Image Display height---" + screenHeight/2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, screenHeight / 2);
        relDetailImage.setLayoutParams(layoutParams);
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
        categoryid = getIntent().getStringExtra(AppConstants.ID);
        List_metal_color = getIntent().getStringExtra(AppConstants.METALCOLOR);
        List_carat_value = getIntent().getStringExtra(AppConstants.CARATVALUE);
        AppLogger.e("product id", "-------------" + productId);

        databaseCartAdapter = new DatabaseCartAdapter(ProductDetailAct.this);
        sharedPreferences = new SharedPreferences(ProductDetailAct.this);
    }

    @Override
    public void initView() {
        bindToolBar("Detail Page");
        linDiamondBox = findViewById(R.id.linDiamondBox);

        cardDiamondBox = findViewById(R.id.cardDiamondBox);
        cardGemstoneBox = findViewById(R.id.cardGemstoneBox);

        viewPagerSlider = findViewById(R.id.viewPagerSlider);
        hsvSlider = findViewById(R.id.hsvSlider);
        linContainer = findViewById(R.id.linContainer);
        linProgress = findViewById(R.id.linProgress);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvColorGold = findViewById(R.id.tvColorGold);
//        imgPrevious = findViewById(R.id.imgPrevious);
//        imgNext = findViewById(R.id.imgNext);
        recycleViewReadyToShip = findViewById(R.id.recycleViewReadyToShip);
//        recycleViewTestPendentsets = findViewById(R.id.recycleViewTest);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        tvMetalPrice = findViewById(R.id.tvMetalPrice);
        tvDiamondPiecesTitle = findViewById(R.id.tvDiamondPiecesTitle);
        tvDiamondPrice = findViewById(R.id.tvDiamondPrice);
        tvGemstonePiecesTitle = findViewById(R.id.tvGemstonePiecesTitle);
        tvGemstonePrice = findViewById(R.id.tvGemstonePrice);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);

        constraintRTS = findViewById(R.id.constraintRTS);

//        viewRing = findViewById(R.id.viewRing);
//        tvRingSizeHeading = findViewById(R.id.tvRingSizeHeading);
//        viewBangle = findViewById(R.id.viewBangle);
//        viewBracelet = findViewById(R.id.viewBracelet);
//        viewPendentSets = findViewById(R.id.viewPendentSets);
//        tvBangleSizeHeading = findViewById(R.id.tvBangleSizeHeading);
//        tvBraceletsHeading = findViewById(R.id.tvBraceletsHeading);
//        tvPendentHeading = findViewById(R.id.tvPendentHeading);

//        linsoldout = findViewById(R.id.linsoldout);
        btnSoldOut = findViewById(R.id.btnSoldOut);
        linButton = findViewById(R.id.linButton);

        btncustomize = findViewById(R.id.btncustomize);
        mBottomSheetDialog = new BottomSheetDialog(ProductDetailAct.this);
        mBottomDialog_detail = new BottomSheetDialog(ProductDetailAct.this);
        btnProductDetail = findViewById(R.id.btnProductDetail);
//        layout_customize = findViewById(R.id.layout_customize);

//        productInfoPS = findViewById(R.id.productInfoPS);
        btnInfo = findViewById(R.id.btnInfo);
//        cardviewProductInfo = findViewById(R.id.cardviewProductInfo);

        relDetailImage = findViewById(R.id.relDetailImage);
    }

    @Override
    public void postInitView() {

        // Set layout RTS adapter
        GridLayoutManager gridLayoutRts = new GridLayoutManager(ProductDetailAct.this, 1);
        gridLayoutRts.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewReadyToShip.setLayoutManager(gridLayoutRts);
        // Scroll RecyclerView a little to make later scroll take effect.
        recycleViewReadyToShip.scrollToPosition(0);

    }

    @Override
    public void addListener() {
        AppLogger.e("sdK version ","-----" +  Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            btncustomize.setTooltipText("Customize product");
        }
        btncustomize.setOnClickListener(this);
//        btnProductDetail.setOnClickListener(this);
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

    @SuppressLint("ResourceType")
    @Override
    public void loadData() {

        if(NetworkUtils.isNetworkConnected(ProductDetailAct.this)) {

            View sheetview = getLayoutInflater().inflate(R.layout.act_customize_product_dialogbox, null);
//        mBottomSheetDialog.setLayoutParams(layoutParams);
            AppLogger.e("MainScreenHeight", "-----" + screenHeight);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, (screenHeight / 2) - 100);
//        AppLogger.e("Checking screen height for bottomsheetdialogbox","---"+ screenHeight / 2);
//        LinearLayout customize_layout = sheetview.findViewById(R.id.customize_layout);
//        customize_layout.setLayoutParams(layoutParams); //comment this because in big screen device & for earrings which includes only 3 customize option box display empty space bottom of buttons
            mBottomSheetDialog.setContentView(sheetview);
            BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetview.getParent());
//        mBehavior.setPeekHeight(700);
            sheetview.requestLayout();

            lin_Ring_Size = sheetview.findViewById(R.id.lin_Ring_Size);
            lin_Pendant_Type = sheetview.findViewById(R.id.lin_Pendant_Type);
            lin_Bangle_Size = sheetview.findViewById(R.id.lin_Bangle_Size);
            lin_Bracelet_Size = sheetview.findViewById(R.id.lin_Bracelet_Size);

            recycleViewRingSize = sheetview.findViewById(R.id.recycleViewRingSize);
            recycleViewBangleSize = sheetview.findViewById(R.id.recycleViewBangleSize);
            recycleViewBraceletSize = sheetview.findViewById(R.id.recycleViewBraceletSize);
            recycleViewPendentSets = sheetview.findViewById(R.id.recycleViewPendentSets);
            recycleViewDiamond = sheetview.findViewById(R.id.recycleViewDiamond);
            recycleViewCarat = sheetview.findViewById(R.id.recycleViewCarat);
            recycleViewMetal = sheetview.findViewById(R.id.recycleViewMetal);

            tvCustomizePrice = sheetview.findViewById(R.id.tvCustomizePrice);

            tvCustomizePrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
            tvCustomizePrice.setTextAppearance(ProductDetailAct.this, R.attr.toolbarTextColor);

//            tvCustomizePrice.setPaintFlags(tvCustomizePrice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);  //Code for underLine the text
            btnapply = sheetview.findViewById(R.id.btnapply);
//        btncancel = sheetview.findViewById(R.id.btncancel);

            GridLayoutManager gridLayoutRing = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutRing.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewRingSize.setLayoutManager(gridLayoutRing);

            GridLayoutManager gridLayoutBangle = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutBangle.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewBangleSize.setLayoutManager(gridLayoutBangle);

            //Set layout pendent & sets adapter
            GridLayoutManager gridLayoutPendentSets = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutPendentSets.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewPendentSets.setLayoutManager(gridLayoutPendentSets);

            //Set layout bracelets adapter
            GridLayoutManager gridLayoutBracelet = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutBracelet.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewBraceletSize.setLayoutManager(gridLayoutBracelet);

            GridLayoutManager gridLayoutDiamond = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutDiamond.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewDiamond.setLayoutManager(gridLayoutDiamond);

            //Set layout carat adapter
            GridLayoutManager gridLayoutCarat = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutCarat.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewCarat.setLayoutManager(gridLayoutCarat);

            //Set layout metal adapter
            GridLayoutManager gridLayoutMetal = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutMetal.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycleViewMetal.setLayoutManager(gridLayoutMetal);

            productdetailview = getLayoutInflater().inflate(R.layout.act_product_detail_include_price_detail, null);
//         final BottomSheetBehavior behavior = BottomSheetBehavior.from(productdetailview);
//         behavior.setPeekHeight(300);
//        layout_productdetail.setLayoutParams(layoutParams);
            mBottomDialog_detail.setContentView(productdetailview);

//        BottomSheetBehavior m1Behavior = BottomSheetBehavior.from((View) productdetailview.getParent());
//        m1Behavior.setPeekHeight(380);
            productdetailview.requestLayout();
            ProductDetailScrollView = productdetailview.findViewById(R.id.detailscrollView);
            tvSku = productdetailview.findViewById(R.id.tvSku);
            tvCertificateNo = productdetailview.findViewById(R.id.tvCertificateNo);
            tvMetalPurity = productdetailview.findViewById(R.id.tvMetalPurity);
            tvMetalWeightApprox = productdetailview.findViewById(R.id.tvMetalWeightApprox);
            tvMetalEstimatedTotal = productdetailview.findViewById(R.id.tvMetalEstimatedTotal);
            tvBeltPrice = productdetailview.findViewById(R.id.tvBeltPrice);
            recycleViewDiamondDetail = productdetailview.findViewById(R.id.recycleViewDiamondDetail);
            tvDiamondDetailLabel = productdetailview.findViewById(R.id.tvDiamondDetail);
            relDiamondDetailTotal = productdetailview.findViewById(R.id.relDiamondDetailTotal);
            tvGemStoneDetailTitle = productdetailview.findViewById(R.id.tvGemStoneDetailTitle);
            recycleViewGemstoneDetail = productdetailview.findViewById(R.id.recycleViewGemstoneDetail);
            relGemstoneDetailTotal = productdetailview.findViewById(R.id.relGemstoneDetailTotal);
            image_gia = productdetailview.findViewById(R.id.image_gia);
            image_igi = productdetailview.findViewById(R.id.image_igi);
            tvEstimatedTotalValue = productdetailview.findViewById(R.id.tvEstimatedValue);
            tvGemstoneEstimatedValue = productdetailview.findViewById(R.id.tvGemstoneEstimatedValue);
            tvMetalDetailTitle = productdetailview.findViewById(R.id.tvMetalDetailTitle);
            relMetalPurity = productdetailview.findViewById(R.id.relMetalPurity);
            relMetalWeight = productdetailview.findViewById(R.id.relMetalWeight);
            relMetalTotal = productdetailview.findViewById(R.id.relMetalTotal);
            cardViewGemstone = productdetailview.findViewById(R.id.cardViewGemstone);
            cardviewbelt = productdetailview.findViewById(R.id.cardviewbelt);
            relBelt = productdetailview.findViewById(R.id.relBelt);
            viewBelt = productdetailview.findViewById(R.id.viewBelt);
            tvBeltDetailTitle = productdetailview.findViewById(R.id.tvBeltDetailTitle);
            cardViewDiamond = productdetailview.findViewById(R.id.cardViewDiamond);

            tvBeltDetailTitle.setOnClickListener(this);
            cardViewGemstone.setOnClickListener(this);
            tvDiamondDetailLabel.setOnClickListener(this);
            tvGemStoneDetailTitle.setOnClickListener(this);
            tvMetalDetailTitle.setOnClickListener(this);

            recycleViewDiamondDetail.setNestedScrollingEnabled(false);

            themePreferences = new ThemePreferences(ProductDetailAct.this);
            if (themePreferences.getTheme().equalsIgnoreCase("black")) {
                image_igi.setImageResource(R.drawable.ic_igi_white_new);
                image_gia.setImageResource(R.drawable.ic_gemological_institute_of_america_gia_white);
            } else {
                image_igi.setImageResource(R.drawable.ic_igi_new);
                image_gia.setImageResource(R.drawable.ic_gemological_institute_of_america_gia);
            }
            ProductDetailScrollView.setFocusable(true);
            ProductDetailScrollView.scrollTo(0, 0);
            //Set layout Diamond adapter
            GridLayoutManager gridLayoutDiamondDetail = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutDiamondDetail.setOrientation(LinearLayoutManager.VERTICAL);
            recycleViewDiamondDetail.setLayoutManager(gridLayoutDiamondDetail);

            GridLayoutManager gridLayoutGemstone = new GridLayoutManager(ProductDetailAct.this, 1);
            gridLayoutGemstone.setOrientation(LinearLayoutManager.VERTICAL);
            recycleViewGemstoneDetail.setLayoutManager(gridLayoutGemstone);

            if (categoryid != null) {
                if (categoryid.equalsIgnoreCase(AppConstants.RING_ID)) {
                    getProductDetail(productId, caratValue, metalValue, ringValue, "SI-IJ", "", "", "", "", "");
                } else if (categoryid.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
                    getProductDetail(productId, List_carat_value, List_metal_color, "", "SI-IJ", "", "", "", "", "");
                } else {
                    getProductDetail(productId, caratValue, metalValue, "", "SI-IJ", "", "", "", "", "");
                }
            } else {
                getProductDetail(productId, caratValue, metalValue, "", "SI-IJ", "", "", "", "", "");
            }
//        getProductDetail(productId, caratValue, metalValue, "", "", productId, "", "");
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart:
                addRecord();
                break;

            case R.id.btnBuyNow:

/*                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    Snackbar snackBar = Snackbar
                            .make(v, "Please Login, to Buy Now", Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (productType.equalsIgnoreCase("simple")) {
                                        ringOptionId = "null";
                                        ringOptionTypeId = "null";
                                        stoneOptionId = "null";
                                        stoneOptionTypeId = "null";

                                    } else {
                                        if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                                            cRingSize = ringValue;
//                                          ringOptionId = ringAdapter.ringOptionId;
//                                          ringOptionTypeId = ringAdapter.ringOptionTypeId;
                                            ringOptionId = ringOptionId.toString();
                                            ringOptionTypeId = ringOptionTypeId.toString();

                                        } else {
                                            ringOptionId = "null";
                                            ringOptionTypeId = "null";
                                        }
                                        stoneOptionId = diamondAdapter.stoneOptionId;
                                        stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;
//                                      if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//                                          cProductId= pendentProId.toString();
//                                          AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                                      }
                                    }
                                    Cursor cursor;
                                    databaseCartAdapter.openDatabase();
                                    cursor = databaseCartAdapter.findRecordCheck(cProductId);

                                    if (cursor.getCount() > 0) {
                                        AppLogger.e("Record", "-------Found");
                                        if (productType.equalsIgnoreCase("simple")) {
                                            CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart");
                                        }else {
                                            AppLogger.e("W/O login AddRecord StoneOptionID","---"+stoneOptionId);
                                            AppLogger.e("W/O login AddRecordstoneOptionTypeId","---"+stoneOptionTypeId);
                                            AppLogger.e("W/O login AddRecordringOptionTypeId","---"+ringOptionTypeId);

                                            cursor = databaseCartAdapter.findSameRecordCheck(stoneOptionId,stoneOptionTypeId);

                                            if(cursor.getCount() > 0){
//                                            CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart ");
                                                //Checking for different RingSIze product
                                                if(productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)){

                                                    cursor = databaseCartAdapter.SameRecordCheck(ringOptionTypeId);

                                                    if(cursor.getCount() > 0){
//                                                        CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart ");
                                                    }else {
                                                        AppLogger.e("Record-------", "Not found for same productID bt diffrent Ring id.");
//                                                        cmetalQualityColor = metalValue;
//                                                        cmetalCarat =  caratValue;
                                                        cProductType = productType;
                                                        cCategoryId = productCategoryId;
                                                        //                              cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                                                        cursor = databaseCartAdapter.getAllValues();
                                                        if(cursor.getCount() > 0 ){

                                                            AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
                                                            //                                  String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                                                            int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                                                            ctotalItem = String.valueOf(totalitem + 1);
                                                            databaseCartAdapter.updateTotalQTY(ctotalItem);

                                                        } else{
                                                            ctotalItem = "1" ;
                                                        }

                                                        databaseCartAdapter.addValues(cProductId,
                                                                cCategoryId,
                                                                cProductType,
                                                                cProductSKU,
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
                                                                ctotalItem,
                                                                cProductCategoryType);

                                                        //  Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
//                                                        CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                                                        cartCount++;
                                                        setupBadge();
                                                        databaseCartAdapter.closeDatabase();
                                                    }
                                                }else {
//                                                    CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart ");
                                                }
                                            }else {
                                                AppLogger.e("Record-------", "Not found for same productID bt diffrent Stone id.");

//                                                    cmetalQualityColor = metalValue;
//                                                    cmetalCarat =  caratValue;
                                                cProductType = productType;
                                                cCategoryId = productCategoryId;
//                                                  cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                                                cursor = databaseCartAdapter.getAllValues();
                                                if(cursor.getCount() > 0 ){

                                                    AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
//                                                      String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                                                    int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                                                    ctotalItem = String.valueOf(totalitem + 1);
                                                    databaseCartAdapter.updateTotalQTY(ctotalItem);

                                                } else{
                                                    ctotalItem = "1" ;
                                                }

                                                databaseCartAdapter.addValues(cProductId,
                                                        cCategoryId,
                                                        cProductType,
                                                        cProductSKU,
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
                                                        ctotalItem,
                                                        cProductCategoryType);

//                                                  Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
//                                                  CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                                                cartCount++;
                                                setupBadge();
                                                databaseCartAdapter.closeDatabase();
                                            }
                                        }
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
//                                          if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//                                            if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
//                                                cProductId= pendentProId.toString();
//                                                AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                                            }
                                        }
//                                        cmetalQualityColor = metalValue;
//                                        cmetalCarat =  caratValue;
                                        cProductType = productType;
                                        cCategoryId = productCategoryId;
//                                      cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                                        cursor = databaseCartAdapter.getAllValues();
                                        if(cursor.getCount() > 0 ){

                                            AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
//                                          String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                                            int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                                            ctotalItem = String.valueOf(totalitem + 1);
                                            databaseCartAdapter.updateTotalQTY(ctotalItem);

                                        } else{
                                            ctotalItem = "1" ;
                                        }

                                        databaseCartAdapter.addValues(cProductId,
                                                cCategoryId,
                                                cProductType,
                                                cProductSKU,
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
                                                ctotalItem,
                                                cProductCategoryType);

//                                      Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
//                                        CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                                        cartCount++;
                                        setupBadge();
                                        databaseCartAdapter.closeDatabase();
                                    }
                                    cursor.close();
                                    loginFlag = 2;
                                    Intent intent = new Intent(ProductDetailAct.this, LoginAct.class);
                                    startActivity(intent);
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
                    TextView textView = snackBarView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackBar.show();
                } else {  */
                    buyNow();
        /*     }  */
                break;

            case R.id.btncustomize:
                //new customize option in bottom Dialog  // Not working this code
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btncustomize.setTooltipText("Customize product");
                }

                btnapply.setOnClickListener(this);
//                btncancel.setOnClickListener(this);

//                mBottomSheetDialog.setCancelable(false);
                mBottomSheetDialog.show();
                break;


            case R.id.btnapply:

                if(CustomizeClick == 1){
                    addRecord();
                }else {
                    filterClick(productId, "Customize");
                }
//                    addRecord();         //comment bcz without click any option direct user press addtocart then RTS product inserted into cart
                break;

            case R.id.tvMetalDetailTitle:

                if(recycleViewDiamondDetail.getVisibility() == View.VISIBLE || recycleViewGemstoneDetail.getVisibility() == View.VISIBLE || tvBeltDetailTitle.getVisibility() == View.VISIBLE) {
                    tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvDiamondDetailLabel.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewDiamondDetail.setVisibility(View.GONE);
                    relDiamondDetailTotal.setVisibility(View.GONE);

                    tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvGemStoneDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewGemstoneDetail.setVisibility(View.GONE);
                    relGemstoneDetailTotal.setVisibility(View.GONE);

                    tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvBeltDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvBeltDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relBelt.setVisibility(View.GONE);
                }

                if(relMetalPurity.getVisibility() == View.VISIBLE && relMetalWeight.getVisibility() == View.VISIBLE && relMetalTotal.getVisibility() == View.VISIBLE){
                    tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvMetalDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relMetalPurity.setVisibility(View.GONE);
                    relMetalWeight.setVisibility(View.GONE);
                    relMetalTotal.setVisibility(View.GONE);
                }else {
                    tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_24, 0);
                    tvMetalDetailTitle.setTextColor(getResources().getColor(R.color.dml_logo_color));
                    relMetalPurity.setVisibility(View.VISIBLE);
                    relMetalWeight.setVisibility(View.VISIBLE);
                    relMetalTotal.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tvDiamondDetail:

                if(tvMetalDetailTitle.getVisibility() == View.VISIBLE || recycleViewGemstoneDetail.getVisibility() == View.VISIBLE || tvBeltDetailTitle.getVisibility() == View.VISIBLE) {
                    tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvMetalDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relMetalPurity.setVisibility(View.GONE);
                    relMetalWeight.setVisibility(View.GONE);
                    relMetalTotal.setVisibility(View.GONE);

                    tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvGemStoneDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewGemstoneDetail.setVisibility(View.GONE);
                    relGemstoneDetailTotal.setVisibility(View.GONE);

                    tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvBeltDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvBeltDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relBelt.setVisibility(View.GONE);
                }

                if(recycleViewDiamondDetail.getVisibility() == View.VISIBLE){
                    tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvDiamondDetailLabel.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewDiamondDetail.setVisibility(View.GONE);
                    relDiamondDetailTotal.setVisibility(View.GONE);
                }else {
                    tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_24, 0);
                    tvDiamondDetailLabel.setTextColor(getResources().getColor(R.color.dml_logo_color));
                    recycleViewDiamondDetail.setVisibility(View.VISIBLE);
                    relDiamondDetailTotal.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tvGemStoneDetailTitle:
                if(tvMetalDetailTitle.getVisibility() == View.VISIBLE || recycleViewDiamondDetail.getVisibility() == View.VISIBLE || tvBeltDetailTitle.getVisibility() == View.VISIBLE){
                    tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvMetalDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relMetalPurity.setVisibility(View.GONE);
                    relMetalWeight.setVisibility(View.GONE);
                    relMetalTotal.setVisibility(View.GONE);

                    tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvDiamondDetailLabel.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewDiamondDetail.setVisibility(View.GONE);
                    relDiamondDetailTotal.setVisibility(View.GONE);

                    tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvBeltDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvBeltDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relBelt.setVisibility(View.GONE);
                }

                if(recycleViewGemstoneDetail.getVisibility() == View.VISIBLE){
                    tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvGemStoneDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewGemstoneDetail.setVisibility(View.GONE);
                    relGemstoneDetailTotal.setVisibility(View.GONE);
                }else {
                    tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_24, 0);
                    tvGemStoneDetailTitle.setTextColor(getResources().getColor(R.color.dml_logo_color));
                    recycleViewGemstoneDetail.setVisibility(View.VISIBLE);
                    relGemstoneDetailTotal.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tvBeltDetailTitle:

                if(tvMetalDetailTitle.getVisibility() == View.VISIBLE || recycleViewDiamondDetail.getVisibility() == View.VISIBLE || recycleViewGemstoneDetail.getVisibility() == View.VISIBLE ){

                    tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvMetalDetailTitle.setTextAppearance(this, R.attr.transaction_line_color);
                    tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relMetalPurity.setVisibility(View.GONE);
                    relMetalWeight.setVisibility(View.GONE);
                    relMetalTotal.setVisibility(View.GONE);

                    tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvDiamondDetailLabel.setTextAppearance(this, R.attr.transaction_line_color);
                    tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewDiamondDetail.setVisibility(View.GONE);
                    relDiamondDetailTotal.setVisibility(View.GONE);

                    tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvGemStoneDetailTitle.setTextAppearance(this, R.attr.transaction_line_color);
                    tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    recycleViewGemstoneDetail.setVisibility(View.GONE);
                    relGemstoneDetailTotal.setVisibility(View.GONE);

                }

                if(relBelt.getVisibility() ==  View.VISIBLE){
                    tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                    tvBeltDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                    tvBeltDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                    relBelt.setVisibility(View.GONE);
//                    tvBeltDetailTitle.setVisibility(View.GONE);
                }else {
                    tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_24, 0);
                    tvBeltDetailTitle.setTextColor(getResources().getColor(R.color.dml_logo_color));
                    relBelt.setVisibility(View.VISIBLE);
                }
                break;

//            case R.id.btnInfo:
            case R.id.btnProductDetail:
                 AppLogger.e("More Detail button click which display in recyclerview ","-----");

                    if(tvMetalDetailTitle.getVisibility() == View.VISIBLE || recycleViewDiamondDetail.getVisibility() == View.VISIBLE || recycleViewGemstoneDetail.getVisibility() == View.VISIBLE || relBelt.getVisibility() == View.VISIBLE ){

                        tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvMetalDetailTitle.setTextAppearance(this, R.attr.transaction_line_color);
                        tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        relMetalPurity.setVisibility(View.GONE);
                        relMetalWeight.setVisibility(View.GONE);
                        relMetalTotal.setVisibility(View.GONE);

                        tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvDiamondDetailLabel.setTextAppearance(this, R.attr.transaction_line_color);
                        tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        recycleViewDiamondDetail.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);

                        tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvGemStoneDetailTitle.setTextAppearance(this, R.attr.transaction_line_color);
                        tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        recycleViewGemstoneDetail.setVisibility(View.GONE);
                        relGemstoneDetailTotal.setVisibility(View.GONE);

                        tvBeltDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvBeltDetailTitle.setTextAppearance(ProductDetailAct.this, R.attr.transaction_line_color);
                        tvBeltDetailTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        relBelt.setVisibility(View.GONE);
//                        tvBeltDetailTitle.setVisibility(View.GONE);
                    }
//                    mBottomDialog_detail.
                 mBottomDialog_detail.show();
//                ProductDetailScrollView.scrollTo(0, 0);
                break;
        }
    }

    //get all product detail
    private void getProductDetail(final String productId, String metalCarat, String metalQualityColor, String ringSize, String stoneQuality, final String bangleProId, String braceletProId, final String pendentProId, String SelectedBangleValue, String SelectedPEValue) {

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
        Call<ProductDetailItem> callApi = apiInterface.getProductDetail(productId, metalCarat, metalQualityColor, ringSize, stoneQuality, bangleProId, braceletProId, pendentProId, SelectedBangleValue, SelectedPEValue);
        callApi.enqueue(new Callback<ProductDetailItem>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ProductDetailItem> call, @NonNull Response<ProductDetailItem> response) {
                images = new ArrayList<>();
                assert response.body() != null;

                if(response.body() != null)
                {
                    AppLogger.e("Detail_Response-1st click on product","-------" + response.body());
                    productCategoryId = response.body().getCategoryId();
                    cProductCategoryType = response.body().getProductCategoryType();
                    if (response.body().getStock().equalsIgnoreCase("0")) {
                        linButton.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.VISIBLE);
//                        linsoldout.setVisibility(View.VISIBLE);
                    } else {
//                        linsoldout.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }

    /*                if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                        viewRing.setVisibility(View.VISIBLE);
                        tvRingSizeHeading.setVisibility(View.VISIBLE);
                        recycleViewRingSize.setVisibility(View.VISIBLE);
                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);
                        recycleViewBangleSize.setVisibility(View.GONE);
                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);
                        recycleViewBraceletSize.setVisibility(View.GONE);
                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);
                        recycleViewPendentSets.setVisibility(View.GONE);
                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);
                        recycleViewRingSize.setVisibility(View.GONE);
                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);
                        recycleViewBangleSize.setVisibility(View.GONE);
                        viewBracelet.setVisibility(View.VISIBLE);
                        tvBraceletsHeading.setVisibility(View.VISIBLE);
                        recycleViewBraceletSize.setVisibility(View.VISIBLE);
                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);
                        recycleViewPendentSets.setVisibility(View.GONE);
                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);
                        recycleViewRingSize.setVisibility(View.GONE);
                        viewBangle.setVisibility(View.VISIBLE);
                        tvBangleSizeHeading.setVisibility(View.VISIBLE);
                        recycleViewBangleSize.setVisibility(View.VISIBLE);
                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);
                        recycleViewBraceletSize.setVisibility(View.GONE);
                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);
                        recycleViewPendentSets.setVisibility(View.GONE);
                    } else if (productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);
                        recycleViewRingSize.setVisibility(View.GONE);
                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);
                        recycleViewBangleSize.setVisibility(View.GONE);
                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);
                        recycleViewBraceletSize.setVisibility(View.GONE);
                        viewPendentSets.setVisibility(View.VISIBLE);
                        tvPendentHeading.setVisibility(View.VISIBLE);
                        recycleViewPendentSets.setVisibility(View.VISIBLE);
//                        recycleViewTestPendentsets.setVisibility(View.VISIBLE);
                    } else {
                        viewRing.setVisibility(View.GONE);
                        tvRingSizeHeading.setVisibility(View.GONE);
                        recycleViewRingSize.setVisibility(View.GONE);
                        viewBangle.setVisibility(View.GONE);
                        tvBangleSizeHeading.setVisibility(View.GONE);
                        recycleViewBangleSize.setVisibility(View.GONE);
                        viewBracelet.setVisibility(View.GONE);
                        tvBraceletsHeading.setVisibility(View.GONE);
                        recycleViewBraceletSize.setVisibility(View.GONE);
                        viewPendentSets.setVisibility(View.GONE);
                        tvPendentHeading.setVisibility(View.GONE);
                        recycleViewPendentSets.setVisibility(View.GONE);
//                        recycleViewTestPendentsets.setVisibility(View.GONE);
                    }  */

                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(ProductDetailAct.this, response.body().getSlider());
                    cImageUrl = response.body().getSlider().get(0);
                    viewPagerSlider.setAdapter(imageSliderAdapter);
                    images.addAll(response.body().getSlider());
                    inflateThumbnails();

                    productType = response.body().getProducts();
                    AppLogger.e("product type", "----------" + productType);

                    cProductSKU = response.body().getProductDetails().get(0).getProductSku();

                    //using for Image slider
                    if (response.body().getProducts().equalsIgnoreCase("simple")) {
//                        includeCustomise.setVisibility(View.GONE);
//                        recycleViewReadyToShip.setVisibility(View.VISIBLE);
                        constraintRTS.setVisibility(View.VISIBLE);
//                        layout_customize.setVisibility(View.GONE);
                        btncustomize.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getRtsSlider().size(); i++) {
                            if (response.body().getRtsSlider().get(i).getEntityId().equalsIgnoreCase(productId)) {
                                Rtsflag = 0;
                                AppLogger.e("scroll position", "----------" + i);
                                recycleViewReadyToShip.scrollToPosition(i - 1);
                            }
                        }
                        //using for rts adapter
                        RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                        recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);
//                        btnInfo = findViewById(R.id.btnInfo);
//                        btnInfo.setOnClickListener(this);
                    } else {
                        cProductId = response.body().getSimpleProductId();
//                        includeCustomise.setVisibility(View.VISIBLE);
//                        layout_customize.setVisibility(View.VISIBLE);
                        btncustomize.setVisibility(View.VISIBLE);
//                        if(clickcustomize == 1 ){
//                            clickcustomize = 0;
                            //using for ring adapter
                            lin_Ring_Size.setVisibility(View.VISIBLE);
                            ringValue = "12";
                            ringValue = "12";
                            ringAdapter = new RingAdapter(ProductDetailAct.this, response.body().getRingsize());
                            recycleViewRingSize.setAdapter(ringAdapter);

                            if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)){
                                cRingSize = ringValue;
                                AppLogger.e("ArrayRing_size","----"+response.body().getRingsize().size());
                                for(int j = 0 ; j < response.body().getRingsize().size(); j++ ){
                                    if(response.body().getRingsize().get(j).getTitle().equalsIgnoreCase(ringValue)){
                                        ringOptionId = response.body().getRingsize().get(j).getOptionId();
                                        ringOptionTypeId = response.body().getRingsize().get(j).getOptionTypeId();
                                        recycleViewRingSize.scrollToPosition(j);
                                    }
                                }
                                AppLogger.e("DetailpageRefresh_RingId","-----"+ ringOptionId);
                                AppLogger.e("DetailpageRefresh_RingTypeId","-----"+ ringOptionTypeId);
                            }else {
                                lin_Ring_Size.setVisibility(View.GONE);
                            }

                            if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
//                                cardviewProductInfo.setVisibility(View.VISIBLE);
//                                productInfoPS.setVisibility(View.VISIBLE);
//                                btnInfo.setOnClickListener(ProductDetailAct.this);
                                btnProductDetail.setVisibility(View.VISIBLE);
                                btnProductDetail.setOnClickListener(ProductDetailAct.this);
                            }else {
//                                cardviewProductInfo.setVisibility(View.GONE);
//                                productInfoPS.setVisibility(View.GONE);
                                btnProductDetail.setVisibility(View.GONE);
                            }

                            //using for Bangle adapter
                            if (!response.body().getBangleSize().isEmpty()) {
//                              response.body().getBangleSize().get(0).setSelected(true);
//                              bangleProductId = response.body().getBangleSize().get(0).getProductId();
//                              cBangle = response.body().getBangleSize().get(0).getLabel();
                                lin_Bangle_Size.setVisibility(View.VISIBLE);

                                for(int j = 0; j < response.body().getBangleSize().size(); j++ ){
                                    if(response.body().getBangleSize().get(j).getProductId().equalsIgnoreCase(cProductId)){
                                        response.body().getBangleSize().get(j).setSelected(true);
                                        cBangle = response.body().getBangleSize().get(j).getLabel();
                                        bangleProductId = response.body().getBangleSize().get(j).getProductId();
                                        SizeValue = response.body().getBangleSize().get(j).getValue();
                                        recycleViewBangleSize.scrollToPosition(j);
                                    }
                                }

                                BangleAdapter bangleAdapter = new BangleAdapter(ProductDetailAct.this, response.body().getBangleSize());
                                recycleViewBangleSize.setAdapter(bangleAdapter);
                            }else {
                                SizeValue = "";
                                lin_Bangle_Size.setVisibility(View.GONE);
                            }

                            //using for Bracelets adapter
                            if (!response.body().getBraceletsSize().isEmpty()) {
                                lin_Bracelet_Size.setVisibility(View.VISIBLE);
                                response.body().getBraceletsSize().get(0).setSelected(true);
                                braceletProductId = response.body().getBraceletsSize().get(0).getProductId();
//                            cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                                BraceletsAdapter braceletsAdapter = new BraceletsAdapter(ProductDetailAct.this, response.body().getBraceletsSize());
                                recycleViewBraceletSize.setAdapter(braceletsAdapter);
                            }else {
                                lin_Bracelet_Size.setVisibility(View.GONE);
                            }

                            //using for pendent and sets adapter
                            if (!response.body().getPendentEarring().isEmpty()) {
                                lin_Pendant_Type.setVisibility(View.VISIBLE);
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
                                        TypeValue = response.body().getPendentEarring().get(j).getValue();
                                    }
                                }

                                PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
                                recycleViewPendentSets.setAdapter(pendentSetsAdapter);
                            }else{
                                lin_Pendant_Type.setVisibility(View.GONE);
                                TypeValue = "";
                            }

                            //using for diamond adapter
                            diamondValue = "SI-IJ";

                            for(int j = 0 ; j < response.body().getStoneClarity().size(); j++ ){
                                if(response.body().getStoneClarity().get(j).getTitle().equalsIgnoreCase(diamondValue)){
                                    stoneOptionId = response.body().getStoneClarity().get(j).getOptionId();
                                    stoneOptionTypeId = response.body().getStoneClarity().get(j).getOptionTypeId();
                                    recycleViewDiamond.scrollToPosition(j);
                                }
                            }
                            diamondAdapter = new DiamondAdapter(ProductDetailAct.this, response.body().getStoneClarity());
                            recycleViewDiamond.setAdapter(diamondAdapter);

                            if(response.body().getProductDetails().get(0).getProductSku() != null){
                                String[] data = response.body().getProductDetails().get(0).getProductSku().split(" ");

                                //using for carat adapter
                                caratValue = data[1];

                                //using for metal adapter
                                metalValue = data[2] +" "+ data[3];
                            }

                            for(int j = 0 ; j < response.body().getCarat().size(); j++ ){
                                if(response.body().getCarat().get(j).equalsIgnoreCase(caratValue)){
                                    recycleViewCarat.scrollToPosition(j);
                                }
                            }
                            CaratAdapter caratAdapter = new CaratAdapter(ProductDetailAct.this, response.body().getCarat());
                            recycleViewCarat.setAdapter(caratAdapter);

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
                                metalList.remove("Two Tone");
                                metalList.remove("Two Tone Gold");
                                metalList.remove("Three Tone Gold");
                                metalList.remove("Three Tone");
                            }

                            for(int j = 0 ; j < metalList.size(); j++ ){
                                if(metalList.get(j).equalsIgnoreCase(caratValue)){
                                    recycleViewMetal.scrollToPosition(j);
                                }
                            }
                            MetalAdapter metalAdapter = new MetalAdapter(ProductDetailAct.this, metalList);
                            recycleViewMetal.setAdapter(metalAdapter);

                            cmetalCarat = caratValue;
                            cmetalQualityColor = metalValue;

//                        }

                        //using for rts adapter
                        if (!response.body().getRtsSlider().isEmpty()) {
//                            recycleViewReadyToShip.setVisibility(View.VISIBLE);
                            constraintRTS.setVisibility(View.VISIBLE);
//                            recycleViewTestPendentsets.setVisibility(View.GONE);
                            for (int i = 0; i < response.body().getRtsSlider().size(); i++) {
                                if (response.body().getRtsSlider().get(i).getEntityId().equalsIgnoreCase(productId)) {
                                    Rtsflag = 0;
                                    productType=response.body().getRtsSlider().get(i).getTypeId();
                                    AppLogger.e("scroll position", "----------" + i);
                                    recycleViewReadyToShip.scrollToPosition(i - 1);
                                }
                            }
                            RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                            recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);

                        }else {
//                            recycleViewReadyToShip.setVisibility(View.GONE);
                            constraintRTS.setVisibility(View.GONE);
//                            constraintRTS.setVisibility(View.INVISIBLE);
//                            recycleViewTestPendentsets.setVisibility(View.VISIBLE);
                        }
                    }

//                    if(clickmoredetail == 1){
//                        clickmoredetail = 0 ;
                        //using for Diamond detail adapter
                        if (!response.body().getDiamonddetails().isEmpty()) {
                            cardViewDiamond.setVisibility(View.VISIBLE);
                            linDiamondBox.setVisibility(View.VISIBLE);
                            tvDiamondDetailLabel.setVisibility(View.VISIBLE);
//                            relDiamondDetailTotal.setVisibility(View.VISIBLE);
                            cardDiamondBox.setVisibility(View.VISIBLE);
                            DiamondDetailAdapter diamondDetailAdapter = new DiamondDetailAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                            recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                        } else {
                            linDiamondBox.setVisibility(View.GONE);
                            tvDiamondDetailLabel.setVisibility(View.GONE);
//                            relDiamondDetailTotal.setVisibility(View.GONE);
                            cardDiamondBox.setVisibility(View.GONE);
                            cardViewDiamond.setVisibility(View.GONE);
                        }

                        //using for gemstone detail adapter
                        if (!response.body().getGemstonedetails().isEmpty()) {
                            cardViewGemstone.setVisibility(View.VISIBLE);
                            tvGemStoneDetailTitle.setVisibility(View.VISIBLE);
//                            recycleViewGemstoneDetail.setVisibility(View.VISIBLE);
//                            relGemstoneDetailTotal.setVisibility(View.VISIBLE);
                            cardGemstoneBox.setVisibility(View.VISIBLE);
                            GemstoneDetailAdapter gemstoneDetailAdapter = new GemstoneDetailAdapter(ProductDetailAct.this, response.body().getGemstonedetails());
                            recycleViewGemstoneDetail.setAdapter(gemstoneDetailAdapter);
                        }else {
                            cardViewGemstone.setVisibility(View.GONE);
                            tvGemStoneDetailTitle.setVisibility(View.GONE);
//                            recycleViewGemstoneDetail.setVisibility(View.GONE);
//                            relGemstoneDetailTotal.setVisibility(View.GONE);
                            cardGemstoneBox.setVisibility(View.GONE);
                        }

                        // bindToolBar(response.body().getProductDetails().get(0).getProductName());
                        tvSku.setText(response.body().getProductDetails().get(0).getSku());
                        cSku = response.body().getProductDetails().get(0).getSku();
                        tvCertificateNo.setText(response.body().getProductDetails().get(0).getCertificateNo());

                        tvMetalPurity.setText(response.body().getMetaldetails().get(0).getMetalquality());
                        if(response.body().getMetaldetails().get(0).getMetalweight() == null){
                            tvMetalWeightApprox.setText(" ");
                        }else {
                            tvMetalWeightApprox.setText(response.body().getMetaldetails().get(0).getMetalweight() + " gms");
                        }
//                    tvMetalEstimatedTotal.setText(AppConstants.RS + response.body().getMetaldetails().get(0).getMetalestimatedprice());
                        int value = response.body().getMetaldetails().get(0).getMetalestimatedprice();
                        tvMetalEstimatedTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(String.valueOf(value))));
//                    }

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
//                        tvDiamondDetailLabel.setVisibility(View.GONE);
//                        relDiamondDetailTotal.setVisibility(View.GONE);
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

                    if(response.body().getGemstonemainprice().isEmpty()){
                        cardGemstoneBox.setVisibility(View.GONE);
//                        relGemstoneDetailTotal.setVisibility(View.GONE);
                    } else {
                        cardGemstoneBox.setVisibility(View.VISIBLE);
//                        relGemstoneDetailTotal.setVisibility(View.VISIBLE);

                        tvGemstonePiecesTitle.setText("Gemstone (" + response.body().getGemstonemainprice().get(0).getPieces() + ")");
                        tvGemstonePrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
                        tvGemstoneEstimatedValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
                    }

                    if (productCategoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || productCategoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)) {
                        cardviewbelt.setVisibility(View.VISIBLE);
//                        relBelt.setVisibility(View.VISIBLE);
//                        viewBelt.setVisibility(View.VISIBLE);
                        tvBeltDetailTitle.setVisibility(View.VISIBLE);
                        float beltPrice = Float.parseFloat(response.body().getBelt_price());
                        tvBeltPrice.setText(AppConstants.RS + CommonUtils.priceFormat(beltPrice));
                    }else {
                        cardviewbelt.setVisibility(View.GONE);
                        tvBeltDetailTitle.setVisibility(View.GONE);
//                        relBelt.setVisibility(View.GONE);
//                        viewBelt.setVisibility(View.GONE);
                    }
                    linProgress.setVisibility(View.GONE);

                    //Product Detail
                    tvProductName.setText(response.body().getProductDetails().get(0).getProductName());
                    cPrice = response.body().getProductDetails().get(0).getPrice();
                    tvGrandTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    if(response.body().getProductDetails().get(0).getPrice() == "0"){
                        tvProductPrice.setText(AppConstants.RS + "0");
                    }else{
                        tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                        tvCustomizePrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
//                        tvCustomizePrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
//                        tvCustomizePrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                        tvCustomizePrice.setTextAppearance(ProductDetailAct.this, R.attr.toolbarTextColor);
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

    private void productDetailRefresh(final String productId, String metalCarat, String metalQualityColor, String ringSize, String stoneQuality, final String bangleProId, String braceletProId, final String pendentProId,String SelectedBangleValue,String SelectedPEValue, final String clickAction) {
        AppLogger.e("Refresh_product Id", "-------" + productId);
        AppLogger.e("Refresh_metalCarat", "-------" + metalCarat);
        AppLogger.e("Refresh_metalQualityColor", "-------" + metalQualityColor);
        AppLogger.e("Refresh_ringSize", "-------" + ringSize);
        AppLogger.e("Refresh_stoneQuality", "-------" + stoneQuality);
        AppLogger.e("Refresh_bangleProId", "-------" + bangleProId);
        AppLogger.e("Refresh_braceletProId", "-------" + braceletProId);
        AppLogger.e("Refresh_pendentProId", "-------" + pendentProId);
        AppLogger.e("Refresh_clickAction", "-------" + clickAction);
        AppLogger.e("Refresh_BangleSizeValue", "-------" + SizeValue);
        AppLogger.e("Refresh_Pendant&SetsTypeValue", "-------" + TypeValue);

        linProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ProductDetailItem> callApi = apiInterface.getProductDetail(productId, metalCarat, metalQualityColor, ringSize, stoneQuality, bangleProId, braceletProId, pendentProId, SelectedBangleValue, SelectedPEValue);
        callApi.enqueue(new Callback<ProductDetailItem>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint({"SetTextI18n", "ResourceType"})
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
//                        linsoldout.setVisibility(View.VISIBLE);
                    } else {
//                        linsoldout.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }

                    productType = response.body().getProducts();
                    AppLogger.e("product type", "----------" + productType);

                    cProductCategoryType = response.body().getProductCategoryType();
                    cProductCategoryId = response.body().getCategoryId();
                    cProductSKU = response.body().getProductDetails().get(0).getProductSku();

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
                                recycleViewRingSize.scrollToPosition(j);
                            }
                        }
                        AppLogger.e("DetailpageRefresh_RingId","-----"+ ringOptionId);
                        AppLogger.e("DetailpageRefresh_RingTypeId","-----"+ ringOptionTypeId);
                    }

                    //Apply this condition for PS category change customize to Earrings Or Pendant than not  display cardView of Product Detail info
                    if(cProductCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
//                        cardviewProductInfo.setVisibility(View.VISIBLE);
//                        productInfoPS.setVisibility(View.VISIBLE);
//                        mBottomDialog_detail.setOnClickListener(ProductDetailAct.this);
                        btnProductDetail.setVisibility(View.VISIBLE);
                        btnProductDetail.setOnClickListener(ProductDetailAct.this);
                    }else {
//                        cardviewProductInfo.setVisibility(View.GONE);
//                        productInfoPS.setVisibility(View.GONE);
                        btnProductDetail.setVisibility(View.GONE);
                    }

                    //using for rts adapter
                    if (!response.body().getRtsSlider().isEmpty()) {
//                        recycleViewReadyToShip.setVisibility(View.VISIBLE);
                        constraintRTS.setVisibility(View.VISIBLE);
//                        recycleViewTestPendentsets.setVisibility(View.GONE);
                        Rtsflag = 1;      // set value of this flag 2 bcz when we customize products than don't need to Bydefault selected any RTS slider
                        RTSRecyclerAdapter rtsRecyclerAdapter = new RTSRecyclerAdapter(ProductDetailAct.this, response.body().getRtsSlider());
                        recycleViewReadyToShip.setAdapter(rtsRecyclerAdapter);
                    }else{
//                        recycleViewReadyToShip.setVisibility(View.GONE);
                        constraintRTS.setVisibility(View.GONE);
//                        recycleViewTestPendentsets.setVisibility(View.VISIBLE);
//                        constraintRTS.setVisibility(View.INVISIBLE);
                    }

                    cmetalCarat = caratValue;
                    cmetalQualityColor = metalValue;

                    //using for diamond adapter   // Comment on 26/12/2020   Why need again call diamond adapter ??
                    if (!response.body().getStoneClarity().isEmpty()) {
                        for(int j = 0 ; j < response.body().getStoneClarity().size(); j++ ){
                            if(response.body().getStoneClarity().get(j).getTitle().equalsIgnoreCase(diamondValue)){
                                stoneOptionId = response.body().getStoneClarity().get(j).getOptionId();
                                stoneOptionTypeId = response.body().getStoneClarity().get(j).getOptionTypeId();
                                recycleViewDiamond.scrollToPosition(j);
                            }
                        }
                        diamondAdapter = new DiamondAdapter(ProductDetailAct.this, response.body().getStoneClarity());
                        recycleViewDiamond.setAdapter(diamondAdapter);
                    }

                    AppLogger.e("BangleValue","------"+response.body().getBangleSize());

                    if (!response.body().getBangleSize().isEmpty()) {
////                            response.body().getBangleSize().get(0).setSelected(true);
////                            bangleProductId = response.body().getBangleSize().get(0).getProductId();
// //                           cBangle = response.body().getBangleSize().get(0).getLabel();
                        for(int j = 0; j < response.body().getBangleSize().size(); j++ ){
                            if(response.body().getBangleSize().get(j).getProductId().equalsIgnoreCase(cProductId)){
                                response.body().getBangleSize().get(j).setSelected(true);
                                cBangle = response.body().getBangleSize().get(j).getLabel();
                                bangleProductId = response.body().getBangleSize().get(j).getProductId();
                                SizeValue = response.body().getBangleSize().get(j).getValue();
                                recycleViewBangleSize.scrollToPosition(j);
                            }else {
                                if(!clickAction.equalsIgnoreCase("Bangle")) {
                                    bangleProductId = response.body().getBangleSize().get(0).getProductId();
                                }
                            }
                        }
                        BangleAdapter bangleAdapter = new BangleAdapter(ProductDetailAct.this, response.body().getBangleSize());
                        recycleViewBangleSize.setAdapter(bangleAdapter);
                    }else {
                        SizeValue = "";
                    }

                    if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID) || productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {

                        if (clickAction.equalsIgnoreCase("carat")) {
                            //using for Bangle adapter
                            if (!response.body().getBangleSize().isEmpty()) {
//                                response.body().getBangleSize().get(0).setSelected(true);
//                                bangleProductId = response.body().getBangleSize().get(0).getProductId();
//                                cBangle = response.body().getBangleSize().get(0).getLabel();
                                for(int j = 0; j < response.body().getBangleSize().size(); j++ ){
                                    if(response.body().getBangleSize().get(j).getProductId().equalsIgnoreCase(cProductId)){
                                        response.body().getBangleSize().get(j).setSelected(true);
                                        cBangle = response.body().getBangleSize().get(j).getLabel();
                                        bangleProductId = response.body().getBangleSize().get(j).getProductId();
                                        SizeValue = response.body().getBangleSize().get(j).getValue();
                                        recycleViewBangleSize.scrollToPosition(j);
                                    }else {
                                        if(!clickAction.equalsIgnoreCase("Bangle")) {
                                            bangleProductId = response.body().getBangleSize().get(0).getProductId();
                                        }
                                    }
                                }
                                BangleAdapter bangleAdapter = new BangleAdapter(ProductDetailAct.this, response.body().getBangleSize());
                                recycleViewBangleSize.setAdapter(bangleAdapter);
                            }else {
                                SizeValue = "";
                            }

                            //using for Bracelets adapter
                            if (!response.body().getBraceletsSize().isEmpty()) {
                                response.body().getBraceletsSize().get(0).setSelected(true);
                                braceletProductId = response.body().getBraceletsSize().get(0).getProductId();
                                cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                                AppLogger.e("Bracelet value at Refresh API ","----"+cBracelet);
                                BraceletsAdapter braceletsAdapter = new BraceletsAdapter(ProductDetailAct.this, response.body().getBraceletsSize());
                                recycleViewBraceletSize.setAdapter(braceletsAdapter);
                            }
                        }
                    }

                    //using for Bracelets adapter   // add this when user only change color & stone quality AT that time -> update Bracelet data
                    if (!response.body().getBraceletsSize().isEmpty()) {
                        response.body().getBraceletsSize().get(0).setSelected(true);
                        braceletProductId = response.body().getBraceletsSize().get(0).getProductId();
                        cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                        AppLogger.e("Bracelet value at Refresh API ","----"+cBracelet);
                        BraceletsAdapter braceletsAdapter = new BraceletsAdapter(ProductDetailAct.this, response.body().getBraceletsSize());
                        recycleViewBraceletSize.setAdapter(braceletsAdapter);
                    }

                    //Call secondtime refreshAPI because firsttime change in color product get not changed //Commented on 31/08/2020
//                    if(productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID)){
//                        if(!cProductId.equals(bangleProductId)){
//                            productDetailRefresh(bangleProductId, caratValue, metalValue, "", diamondValue, bangleProductId, "", "", clickAction);
//                        }
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
//                            if(response.body().getPendentEarring().get(j).getProductId().equalsIgnoreCase(pendentProId)){
                                response.body().getPendentEarring().get(j).setSelected(true);
                                TypeValue = response.body().getPendentEarring().get(j).getValue();
                            }
                        }

                        PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
                        recycleViewPendentSets.setAdapter(pendentSetsAdapter);
                    } else{
                        TypeValue = "";
                    }

//                    if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)) {
//                        PendentSetsAdapter pendentSetsAdapter = new PendentSetsAdapter(ProductDetailAct.this, response.body().getPendentEarring());
//                        recycleViewPendentSets.setAdapter(pendentSetsAdapter);
//                    }

                    // cBracelet = response.body().getBraceletsSize().get(0).getLabel();
                    //using for Diamond detail adapter

                    if (response.body().getDiamonddetails() != null) {
                        cardViewDiamond.setVisibility(View.VISIBLE);
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
//                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        DiamondDetailAdapter diamondDetailAdapter = new DiamondDetailAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                        recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                    } else {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
//                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                        cardViewDiamond.setVisibility(View.GONE);
                    }

                    //using for gemstone detail adapter
                    if (!response.body().getGemstonedetails().isEmpty()) {
                        cardViewGemstone.setVisibility(View.VISIBLE);
                        tvGemStoneDetailTitle.setVisibility(View.VISIBLE);
//                        recycleViewGemstoneDetail.setVisibility(View.VISIBLE);
//                        relGemstoneDetailTotal.setVisibility(View.VISIBLE);
                        cardGemstoneBox.setVisibility(View.VISIBLE);
                        GemstoneDetailAdapter gemstoneDetailAdapter = new GemstoneDetailAdapter(ProductDetailAct.this, response.body().getGemstonedetails());
                        recycleViewGemstoneDetail.setAdapter(gemstoneDetailAdapter);
                    }else {
                        cardViewGemstone.setVisibility(View.GONE);
                        tvGemStoneDetailTitle.setVisibility(View.GONE);
//                        recycleViewGemstoneDetail.setVisibility(View.GONE);
//                        relGemstoneDetailTotal.setVisibility(View.GONE);
                        cardGemstoneBox.setVisibility(View.GONE);
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

                    if(response.body().getGemstonemainprice().isEmpty()){
                        cardGemstoneBox.setVisibility(View.GONE);
//                        relGemstoneDetailTotal.setVisibility(View.GONE);
                    } else {
                        cardGemstoneBox.setVisibility(View.VISIBLE);
//                        relGemstoneDetailTotal.setVisibility(View.VISIBLE);

                        tvGemstonePiecesTitle.setText("Gemstone (" + response.body().getGemstonemainprice().get(0).getPieces() + ")");
                        tvGemstonePrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
                        tvGemstoneEstimatedValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
                    }

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
                        tvCustomizePrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
//                        tvCustomizePrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                        tvCustomizePrice.setTextAppearance(ProductDetailAct.this, R.attr.toolbarTextColor);
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

                    //Apply Addrecord method use here for 1st user open customize box & Without click any option direct press Addtocart
                    if(customizeflag == 1){
                        addRecord();
                    }
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

        if(clickAction.equalsIgnoreCase("Customize")){
//            addRecord();
            customizeflag = 1;
        }

        if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
            productDetailRefresh(productId, caratValue, metalValue, ringValue, diamondValue, "", "", "","","",clickAction);
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", braceletProductId, "","","", clickAction);
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.BANGLE_ID)) {
//            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, bangleProductId, "", "", clickAction);
//            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);   // Change for configurable products
//            productDetailRefresh(bangleProductId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);   // Change for configurable products
            productDetailRefresh(bangleProductId, caratValue, metalValue, "", diamondValue, bangleProductId, "", "",SizeValue,"", clickAction);   // Change for configurable products
        } else if (productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
//            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", pendentProId, clickAction);
//            productDetailRefresh(cProductId, caratValue, metalValue, "", diamondValue, "", "", "", clickAction);
//            productDetailRefresh(pendentProId, caratValue, metalValue, "", diamondValue, "", "", pendentProId, clickAction);
            productDetailRefresh(pendentProId, caratValue, metalValue, "", diamondValue, "", "", "","",TypeValue, clickAction);
        } else {
            productDetailRefresh(productId, caratValue, metalValue, "", diamondValue, "", "", "","", "",clickAction);
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
            metalList.remove("Two Tone");
            metalList.remove("Three Tone Gold");
            metalList.remove("Three Tone");
//            metalList.remove("Two Tone");
            tvColorGold.setText("(" + caratValue + ")");
            cMetalDetail = caratValue;
        }
        MetalAdapter metalAdapter = new MetalAdapter(ProductDetailAct.this, metalList);
        recycleViewMetal.setAdapter(metalAdapter);
    }

    private void rtsProductClick(String productId){
        linProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<RTSItem> callApi = apiInterface.getRTSDetail(productId);
        callApi.enqueue(new Callback<RTSItem>() {
            //            @RequiresApi(api = Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RTSItem> call, Response<RTSItem> response) {

//                //using for Diamond detail adapter
//                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                    if (response.body().getStock().equalsIgnoreCase("0")) {
                        linButton.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.VISIBLE);
//                        linsoldout.setVisibility(View.VISIBLE);
                    } else {
//                        linsoldout.setVisibility(View.GONE);
                        btnSoldOut.setVisibility(View.GONE);
                        linButton.setVisibility(View.VISIBLE);
                    }
                    AppLogger.e("getDiamonddetails", "-------" + response.body().getDiamonddetails());
                    if (response.body().getDiamonddetails() != null) {
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
//                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        DiamondDetailRTSAdapter diamondDetailAdapter = new DiamondDetailRTSAdapter(ProductDetailAct.this, response.body().getDiamonddetails());
                        recycleViewDiamondDetail.setAdapter(diamondDetailAdapter);
                    } else {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
//                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                    }
                    //Product Detail
                    assert response.body() != null;
                    tvProductName.setText(response.body().getProductDetails().get(0).getProductName());
                    cProductSKU = response.body().getProductDetails().get(0).getProductSku();

                    if(response.body().getProductDetails().get(0).getPrice() == "0"){
                        tvProductPrice.setText(AppConstants.RS + "0");
                    }else{
                        tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    }
                    tvSku.setText(response.body().getProductDetails().get(0).getSku());
                    cSku = response.body().getProductDetails().get(0).getSku();
                    tvCertificateNo.setText(response.body().getProductDetails().get(0).getCertificateNo());
                    cMetalWeight = response.body().getMetaldetails().get(0).getMetalweight();

                    //Metal Diamond Detail
//                    metalValue , caratValue
                    String allmetalquality= response.body().getMetaldetails().get(0).getMetalquality();
//                    StringBuilder sb = new StringBuilder();
                    String[] value1 = allmetalquality.split(" ");
//                    for(int i = 0 ; i < allmetalquality.length() ; i++){

                    cmetalCarat = value1[0];
                    AppLogger.e("Split Caratvalue","" + cmetalCarat);
                    cmetalQualityColor = value1[1] + " " + value1[2];
//                        sb.append(value1[1]);
//                    }
                    AppLogger.e("Split metalvalue","" + cmetalQualityColor);
                    Float metalPrice = Float.parseFloat(response.body().getMetalprice().toString());
                    //                metalPrice = metalPrice.substring(1, metalPrice.length() - 1);
                    tvMetalPrice.setText(String.valueOf(CommonUtils.priceFormat(metalPrice)));
                    tvMetalPrice.setText(AppConstants.RS + CommonUtils.priceFormat(metalPrice));

                    if (response.body().getDiamondmainprice().isEmpty()) {
                        linDiamondBox.setVisibility(View.GONE);
                        tvDiamondDetailLabel.setVisibility(View.GONE);
//                        relDiamondDetailTotal.setVisibility(View.GONE);
                        cardDiamondBox.setVisibility(View.GONE);
                        cardViewDiamond.setVisibility(View.GONE);
                    } else {
                        cardViewDiamond.setVisibility(View.VISIBLE);
                        linDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondDetailLabel.setVisibility(View.VISIBLE);
//                        relDiamondDetailTotal.setVisibility(View.VISIBLE);
                        cardDiamondBox.setVisibility(View.VISIBLE);
                        tvDiamondPiecesTitle.setText("Diamond (" + response.body().getDiamondmainprice().get(0).getPices() + ")");
                        tvDiamondPrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                        AppLogger.e("DiamondPieces" ,response.body().getDiamondmainprice().get(0).getPices());
                        AppLogger.e("DiamondPrice ",response.body().getDiamondmainprice().get(0).getDimondprice());
                        tvEstimatedTotalValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getDiamondmainprice().get(0).getDimondprice()));
                    }

//                    if(response.body().getGemstonemainprice().isEmpty()){
//                        cardGemstoneBox.setVisibility(View.GONE);
//                        relGemstoneDetailTotal.setVisibility(View.GONE);
//                    } else {
//                        cardGemstoneBox.setVisibility(View.VISIBLE);
//                        relGemstoneDetailTotal.setVisibility(View.VISIBLE);
//
//                        tvGemstonePiecesTitle.setText("Gemstone (" + response.body().getGemstonemainprice().get(0).getPieces() + ")");
//                        tvGemstonePrice.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
//                        tvGemstoneEstimatedValue.setText(AppConstants.RS + CommonUtils.rupeeFormat(response.body().getGemstonemainprice().get(0).getGemstoneprice()));
//                    }

                    tvMetalPurity.setText(response.body().getMetaldetails().get(0).getMetalquality());
                    tvMetalWeightApprox.setText(response.body().getMetaldetails().get(0).getMetalweight() + " gms");
//                    tvMetalEstimatedTotal.setText(AppConstants.RS + response.body().getMetaldetails().get(0).getMetalestimatedprice());
                    int value = response.body().getMetaldetails().get(0).getMetalestimatedprice();
                    tvMetalEstimatedTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(String.valueOf(value))));
                    linProgress.setVisibility(View.GONE);
                    if (productCategoryId.equalsIgnoreCase(AppConstants.COLLECTION_ID) || productCategoryId.equalsIgnoreCase(AppConstants.RUBBER_ID)) {
                        cardviewbelt.setVisibility(View.VISIBLE);
//                        relBelt.setVisibility(View.VISIBLE);
                        tvBeltDetailTitle.setVisibility(View.VISIBLE);
//                        viewBelt.setVisibility(View.VISIBLE);
                        float beltPrice = Float.parseFloat(response.body().getBelt_price());
// //                       tvBeltPrice.setText(AppConstants.RS + CommonUtils.priceFormat(beltPrice));

                    }else {
                        tvBeltDetailTitle.setVisibility(View.GONE);
                        cardviewbelt.setVisibility(View.GONE);
//                        relBelt.setVisibility(View.GONE);
//                        viewBelt.setVisibility(View.GONE);
                    }

                    tvGrandTotal.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    if(response.body().getProductDetails().get(0).getPrice() == "0"){
                        tvProductPrice.setText(AppConstants.RS + "0");
                    }else{
// //                       tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(response.body().getProductDetails().get(0).getPrice())));
                    }
//                tvColorGold.setText("(" + caratValue + " " + metalValue + ")");

                    if(!response.body().getGemstonedetails().isEmpty()){
                        cardViewGemstone.setVisibility(View.VISIBLE);
                        tvGemStoneDetailTitle.setVisibility(View.VISIBLE);
//                        recycleViewGemstoneDetail.setVisibility(View.VISIBLE);
//                        relGemstoneDetailTotal.setVisibility(View.VISIBLE);
                        cardGemstoneBox.setVisibility(View.VISIBLE);
                        GemstoneDetailRTSAdapter gemstoneDetailAdapter = new GemstoneDetailRTSAdapter(ProductDetailAct.this, response.body().getGemstonedetails());
                        recycleViewGemstoneDetail.setAdapter(gemstoneDetailAdapter);
                    }else {
                        cardViewGemstone.setVisibility(View.GONE);
                        tvGemStoneDetailTitle.setVisibility(View.GONE);
//                        recycleViewGemstoneDetail.setVisibility(View.GONE);
//                        relGemstoneDetailTotal.setVisibility(View.GONE);
                        cardGemstoneBox.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RTSItem> call, Throwable t) {
                linProgress.setVisibility(View.GONE);
                AppLogger.e("Error","--------" + t.getMessage());
            }
        });
    }

    //product add to cart
    public void addRecord() {
        //Comment Addtocart without login code bcz fromnow User is not access any of the page W/O login
/*        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {

//            if(Rtsflag == 1 ){
//                rtsClick(cProductId);
//                RTSRecyclerAdapter.onClick
//            }

            if (productType.equalsIgnoreCase("simple")) {
                ringOptionId = "null";
                ringOptionTypeId = "null";
                stoneOptionId = "null";
                stoneOptionTypeId = "null";

            } else {
                if (productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)) {
                    cRingSize = ringValue;
//                    ringOptionId = ringAdapter.ringOptionId;
//                    ringOptionTypeId = ringAdapter.ringOptionTypeId;
                    ringOptionId = ringOptionId.toString();
                    ringOptionTypeId = ringOptionTypeId.toString();

                } else {
                    ringOptionId = "null";
                    ringOptionTypeId = "null";
                }
                stoneOptionId = diamondAdapter.stoneOptionId;
                stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;
//                if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//                    cProductId= pendentProId.toString();
//                    AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                }
            }

            Cursor cursor;
            databaseCartAdapter.openDatabase();
            cursor = databaseCartAdapter.findRecordCheck(cProductId);

            if (cursor.getCount() > 0) {
                AppLogger.e("Record", "-------Found");

                if (productType.equalsIgnoreCase("simple")) {
                    CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart");
                }else {
                    AppLogger.e("W/O login AddRecord StoneOptionID","---"+stoneOptionId);
                    AppLogger.e("W/O login AddRecord StoneOptionTypeId","---"+stoneOptionTypeId);
                    AppLogger.e("W/O login AddRecord RingOptionTypeId","---"+ringOptionTypeId);

                    cursor = databaseCartAdapter.findSameRecordCheck(stoneOptionId,stoneOptionTypeId);

                    if(cursor.getCount() > 0){

                        //Checking for different RingSIze product
                        if(productCategoryId.equalsIgnoreCase(AppConstants.RING_ID)){

                            cursor = databaseCartAdapter.SameRecordCheck(ringOptionTypeId);

                            if(cursor.getCount() > 0){
                                CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart ");
                            }else {
//                                    if(productCategoryId.equalsIgnoreCase(AppConstants.BRACELETS_ID)){
//                                        cBracelet = ProductDetailItem.RtsSlider
//
//                                        AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                                    }

                                AppLogger.e("Record-------", "Not found for same productID bt diffrent Ring id.");
//                                    cmetalQualityColor = metalValue;
//                                    cmetalCarat =  caratValue;
                                cProductType = productType;
                                cCategoryId = productCategoryId;
                                //                              cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                                cursor = databaseCartAdapter.getAllValues();
                                if(cursor.getCount() > 0 ){

                                    AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
                                    //                                  String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                                    int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                                    ctotalItem = String.valueOf(totalitem + 1);
                                    databaseCartAdapter.updateTotalQTY(ctotalItem);

                                } else{
                                    ctotalItem = "1" ;
                                }

                                databaseCartAdapter.addValues(cProductId,
                                        cCategoryId,
                                        cProductType,
                                        cProductSKU,
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
                                        ctotalItem,
                                        cProductCategoryType);

                                //                              Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
                                CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                                cartCount++;
                                setupBadge();
                                databaseCartAdapter.closeDatabase();
                            }
                        }else {
                            CommonUtils.showWarningToast(ProductDetailAct.this, "Product has already in cart ");
                        }
                    }else {
                        AppLogger.e("Record-------", "Not found for same productID bt diffrent Stone id. Or RingID");

//                                cmetalQualityColor = metalValue;
//                                cmetalCarat =  caratValue;
                        cProductType = productType;
                        cCategoryId = productCategoryId;
                        //                          cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                        cursor = databaseCartAdapter.getAllValues();
                        if(cursor.getCount() > 0 ){

                            AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
                            //                              String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                            int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                            ctotalItem = String.valueOf(totalitem + 1);
                            databaseCartAdapter.updateTotalQTY(ctotalItem);

                        } else{
                            ctotalItem = "1" ;
                        }

                        databaseCartAdapter.addValues(cProductId,
                                cCategoryId,
                                cProductType,
                                cProductSKU,
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
                                ctotalItem,
                                cProductCategoryType);

                        //  Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
                        CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                        cartCount++;
                        setupBadge();
                        databaseCartAdapter.closeDatabase();
                    }
                }
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
//                    if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//                    if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)){
//                        cProductId= pendentProId.toString();
//                        AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
//                    }
                }
//                cmetalQualityColor = metalValue;
//                cmetalCarat =  caratValue;
                cProductType = productType;
                cCategoryId = productCategoryId;
//                cImageUrl = "https://images-na.ssl-images-amazon.com/images/I/41fDhXqeURL.jpg";

                cursor = databaseCartAdapter.getAllValues();
                if(cursor.getCount() > 0 ){

                    AppLogger.e("item","----" + cursor.getString(cursor.getColumnIndex("total_item")));
//                    String totalitem = cursor.getString(cursor.getColumnIndex("total_item"));
                    int totalitem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_item")));
                    ctotalItem = String.valueOf(totalitem + 1);
                    databaseCartAdapter.updateTotalQTY(ctotalItem);

                } else{
                    ctotalItem = "1" ;
                }

                databaseCartAdapter.addValues(cProductId,
                        cCategoryId,
                        cProductType,
                        cProductSKU,
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
                        ctotalItem,
                        cProductCategoryType);

//                Toast.makeText(getApplicationContext(), "item added to cart", Toast.LENGTH_SHORT).show();
                CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                cartCount++;
                setupBadge();
                databaseCartAdapter.closeDatabase();
            }
            cursor.close();
        } else {  */

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
                //Comment this two lines bcz here Both Id sets zero
                stoneOptionId = stoneOptionId;
                stoneOptionTypeId = stoneOptionTypeId;

////                if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
////                    cProductId= pendentProId.toString();
////                    AppLogger.e("AddToCart","cProductId---pendentProId----" + cProductId);
////                }
            }

            addToCart(cProductId, customerId, ringOptionId, ringOptionTypeId, stoneOptionId, stoneOptionTypeId, "1",metalValue, caratValue);
//        }
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
//            stoneOptionId = diamondAdapter.stoneOptionId;
//            stoneOptionTypeId = diamondAdapter.stoneOptionTypeId;

            stoneOptionId = stoneOptionId;
            stoneOptionTypeId = stoneOptionTypeId;

//            if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_ID) || productCategoryId.equalsIgnoreCase(AppConstants.EARRINGS_ID)){
//            if(productCategoryId.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
//                cProductId= pendentProId;
//                AppLogger.e("BuyNow","cProductId---pendentProId----" + cProductId);
//            }
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
                        CommonUtils.showSuccessToast(ProductDetailAct.this, "Product added in cart.");
                        cartCount++;
                        setupBadge();
                        CustomizeClick = 0;
                        customizeflag = 0;
                        if(mBottomSheetDialog.isShowing()){
                            mBottomSheetDialog.dismiss();
                        }
                    }else {
                        message=jsonObject.getString("message");
                        CommonUtils.showWarningToast(ProductDetailAct.this,message);
                        CustomizeClick = 0;
                        customizeflag = 0;
                        if(mBottomSheetDialog.isShowing()){
                            mBottomSheetDialog.dismiss();
                        }
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
                        cartbackFlag = 1;
                        cartCheckBugNowFlag = 1;
                        CustomizeClick = 0;
                        customizeflag = 0;
                        startNewActivity(CartAct.class);
                    }else{
                        CustomizeClick = 0;
                        customizeflag = 0;
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

    public void onBackPressed(){
        if(filterFlag == 1){
            if(pagecountflag == 1){
                pagecountflag = 0;
            }
        }
        finish();
//        return setupBadge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
}
