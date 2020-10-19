package com.dealermela.retrofit;

import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.authentication.myaccount.model.BankResponse;
import com.dealermela.authentication.myaccount.model.CountryResponse;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.authentication.myaccount.model.StateResponse;
import com.dealermela.cart.model.CartServerDataItem;
import com.dealermela.cart.model.OrderSummaryItem;
import com.dealermela.cart.model.SelectPaymentItem;
import com.dealermela.download.model.DownloadItem;
import com.dealermela.home.model.BannerSliderItem;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.home.model.MostSellingItem;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.home.model.QrCodeResponse;
import com.dealermela.home.model.SubcategoryItem;
import com.dealermela.inventary.model.InventoryActionItem;
import com.dealermela.inventary.model.InventoryFilterItem;
import com.dealermela.inventary.model.InventoryInvoiceItem;
import com.dealermela.inventary.model.InventoryItem;
import com.dealermela.inventary.model.InventoryPaymentItem;
import com.dealermela.inventary.model.InventoryProductItem;
import com.dealermela.inventary.model.InventoryQuotationItem;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.listing_and_detail.model.ListingItem;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.listing_and_detail.model.RTSItem;
import com.dealermela.my_stock.model.MyStockItem;
import com.dealermela.order.model.OrderDetailItem;
import com.dealermela.order.model.OrderItem;
import com.dealermela.referral.model.ReferralResponse;
import com.dealermela.transaction.model.TransactionItem;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    //MAin SCreen  //
    @GET("dmlapi/home/getAppVersion")
    Call<JsonObject> getAppVersion();

    //    Login API    /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/ValidateUser/")
    Call<LoginResponse> Login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("notification_token") String token,
                              @Field("device_id") String deviceId);

    //    Sign Up API   /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/registercustomer/")
    Call<JsonObject> signUp(@Field("firstname") String firstName,
                            @Field("lastname") String lastName,
                            @Field("email") String email,
                            @Field("telephone") String telephone,
                            @Field("community") String community,
                            @Field("street") String street,
                            @Field("country_id") String countryId,
                            @Field("region") String region,
                            @Field("region_id") String region_id,
                            @Field("city") String city,
                            @Field("postcode") String postcode,
                            @Field("entity_customer") String entityCustomer,
                            @Field("password") String password,
                            @Field("confirmation") String confirmation);

    //    Forgot Password        /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/forgorpassword/")
    Call<JsonObject> forgotPassword(@Field("email") String email);


    @Headers({"X-ECM-API-ID: dac15f77-e3b7-4871-aa55-b3bbf2dfd863",
            "X-ECM-API-KEY: 12fa0ede83365a179ab2dadedc9ad22b3d837ca8",
            "X-CP-API-ID: b7da06a4",
            "X-CP-API-KEY: dea2322dfbd920446d1154a380f42924"})
    @GET("routers/")
    Call<LoginResponse> getResponseData();

    //    Get All Country             /**/
    @GET("dmlapi/customers/getallcountrylist/")
    Call<CountryResponse> getCountry();


    //    Get all state country wise   /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/getallregionlist/")
    Call<StateResponse> getState(@Field("country_id") String countryId);

    /* Get Dealership guideline URL  */
    @GET("dmlapi/customers/geturlfordealership")
    Call<JsonObject> getDealershipURL();

    /*    Using for My Account  */

    // Get All Address         /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/getalladdress/")
    Call<AddressManageResponse> getAllAddress(@Field("customer_id") String customerId);


    // EDIT CUSTOMER      /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/editcustomer/")
    Call<JsonObject> editContactInfo(@Field("notification_token") String notificationToken,
                                        @Field("customer_id") String customerId,
                                        @Field("firstname") String firstName,
                                        @Field("lastname") String lastName,
                                        @Field("email") String email,
                                        @Field("telephone") String telephone,
                                        @Field("street") String street,
                                        @Field("country_id") String countryId,
                                        @Field("region") String region,
                                        @Field("city") String city,
                                        @Field("postcode") String postCode,
                                        @Field("pancardno") String panCardNo,
                                        @Field("gstin") String gstIn);

    // EDIT DEFAULT SHIPPING             /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/editdefaultshipping/")
    Call<JsonObject> editDefaultShipping(@Field("customer_id") String customerId,
                                         @Field("address_id") String addressId,
                                         @Field("firstname") String firstName,
                                         @Field("lastname") String lastName,
                                         @Field("street") String street,
                                         @Field("street1") String street1,
                                         @Field("city") String city,
                                         @Field("region_id") String regionId,
                                         @Field("region") String region,
                                         @Field("postcode") String postcode,
                                         @Field("country_id") String countryId,
                                         @Field("telephone") String telephone);


    // EDIT DEFAULT BILLING     /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/editdefaultbilling/")
    Call<JsonObject> editDefaultBilling(@Field("customer_id") String customerId,
                                        @Field("address_id") String addressId,
                                        @Field("firstname") String firstName,
                                        @Field("lastname") String lastName,
                                        @Field("street") String street,
                                        @Field("street1") String street1,
                                        @Field("city") String city,
                                        @Field("region_id") String regionId,
                                        @Field("region") String region,
                                        @Field("postcode") String postcode,
                                        @Field("country_id") String countryId,
                                        @Field("telephone") String telephone);

    // EDIT ADDITIONAL BILLING               /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/editaddressbyid/")
    Call<JsonObject> editAdditionalBilling(@Field("customer_id") String customerId,
                                           @Field("address_id") String addressId,
                                           @Field("address_billing_flag") String addressBillingFlag,
                                           @Field("address_shipping_flag") String addressShippingFlag,
                                           @Field("firstname") String firstName,
                                           @Field("lastname") String lastName,
                                           @Field("street") String street,
                                           @Field("street1") String street1,
                                           @Field("city") String city,
                                           @Field("region_id") String regionId,
                                           @Field("region") String region,
                                           @Field("postcode") String postcode,
                                           @Field("country_id") String countryId,
                                           @Field("telephone") String telephone);

    //ADD NEW ADDRESS          /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/createaddress/")
    Call<JsonObject> addNewAddress(@Field("customer_id") String customerId,
                                   @Field("address_billing_flag") String addressBillingFlag,
                                   @Field("address_shipping_flag") String addressShippingFlag,
                                   @Field("firstname") String firstName,
                                   @Field("lastname") String lastName,
                                   @Field("street") String street,
                                   @Field("street1") String street1,
                                   @Field("city") String city,
                                   @Field("region_id") String regionId,
                                   @Field("region") String region,
                                   @Field("postcode") String postcode,
                                   @Field("country_id") String countryId,
                                   @Field("telephone") String telephone);


    // Delete address      /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/deleteaddress")
    Call<JsonObject> deleteAddress(@Field("address_id") String addressId, @Field("customer_id") String customerId);


    //    Change Password       /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/changepassword")
    Call<JsonObject> changePassword(@Field("customer_id") String customerId,
                                    @Field("current_password") String currentPassword,
                                    @Field("new_password") String password);


    // Add Bank Detail                   /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/addbankdetails")
    Call<JsonObject> addBankDetail(@Field("customer_id") String customerId,
                                   @Field("bank_name") String bankName,
                                   @Field("bank_account_number") String bankAccountNumber,
                                   @Field("bank_account_holder") String bankAccountHolder,
                                   @Field("ifsc_code") String IFSCCode,
                                   @Field("branch_name") String branchName);

    // Edit Bank Detail          /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/editbankdetails")
    Call<JsonObject> editBankDetail(@Field("bank_id") String bankId,
                                    @Field("customer_id") String customerId,
                                    @Field("bank_name") String bankName,
                                    @Field("bank_account_number") String bankAccountNumber,
                                    @Field("bank_account_holder") String bankAccountHolder,
                                    @Field("ifsc_code") String IFSCCode,
                                    @Field("branch_name") String branchName);

    // Delete Bank Detail           /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/deletebankdetails")
    Call<JsonObject> deleteBankDetail(@Field("bank_id") String bankId);


    // List Bank Detail            /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/listbankdetails")
    Call<BankResponse> listBankDetail(@Field("customer_id") String customerId);


    //REFERRAL

    //List Referral              /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/getallreferrals")
    Call<ReferralResponse> referralList(@Field("customer_id") String customerId);

    //Add Referral                 /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/createreferral")
    Call<JsonObject> addReferral(@Field("franchisee_status") String franchiseeStatus,
                                 @Field("firstname") String firstName,
                                 @Field("lastname") String lastname,
                                 @Field("email") String email,
                                 @Field("group_id") String groupId,
                                 @Field("password") String password,
                                 @Field("referral_comission") String referralCommission,
                                 @Field("parent_franchise_id") String parentFranchiseId,
                                 @Field("telephone") String telephone,
                                 @Field("referral_type") String referralType,
                                 @Field("_isfranchisee") String isFranchisee);

    //Delete Referral               /**/
    @FormUrlEncoded
    @POST("dmlapi/customers/deletereferral")
    Call<JsonObject> deleteReferral(@Field("referral_customer_id") String referralCustomerId);

    //HOME
    //Banner Slider              /**/
    @GET("dmlapi/home/gethomeslider")
    Call<BannerSliderItem> getBannerImage();

    //Header Slider             /**/
    @GET("dmlapi/home/getallcategorys")
    Call<HeaderItem> getHeader();

    //NavigationDrawer Subcategory dropdown
    @GET("dmlapi/product/getAllCategoriesForNav")
    Call<SubcategoryItem> getSubCategory();

    //Most Selling Product      /**/
    @GET("dmlapi/home/bestsellerproduct")
    Call<MostSellingItem> getMostSEllingProduct();

//    //Popular Product
//    @GET("dmlapi/home/getpopularproduct")
//    Call<PopularProductItem> getPopularProduct();

    //Popular Product
    @FormUrlEncoded        /**/
    @POST("dmlapi/home/getpopularproduct")
    Call<PopularProductItem> getPopularProduct(@Field("customer_id") String customerId);

    //POLICY
    @FormUrlEncoded         /**/
    @POST("dmlapi/home/policycontent")
    Call<JsonObject> getPolicy(@Field("customer_id") String customerId, @Field("policy") String policy);

    //Listing                      /**/
    @FormUrlEncoded
    @POST("dmlapi/product/getallproductbycategory")
    Call<ListingItem> getCategoryProduct(@Field("customer_id") String customerId,
                                         @Field("category_id") String category_id,
                                         @Field("group_id") String groupId,
                                         @Field("page") String page,
                                         @Field("price") String price,
                                         @Field("gold_purity") String gold_purity,
                                         @Field("diamond_quality") String diamond_quality,
                                         @Field("diamond_shape") String diamond_shape,
                                         @Field("sku") String sku,
                                         @Field("availability") String availability,
                                         @Field("sort_by") String sort_by);


    //Detail Page                             /**/
    @FormUrlEncoded
    @POST("dmlapi/product/getproductdetails")
    Call<ProductDetailItem> getProductDetail(@Field("product_id") String productId,
                                             @Field("metalcarat") String metalCarat,
                                             @Field("metalqualitycolor") String metalQualityColor,
                                             @Field("ringsize") String ringSize,
                                             @Field("stone_quality") String stoneQuality,
                                             @Field("bangle_pro_id") String bangleProId,
                                             @Field("bracelet_pro_id") String braceletProId,
                                             @Field("pendent_pro_id") String pendentProId,
                                             @Field("SelectedBangleValue") String SelectedBangleValue,
                                             @Field("SelectedPEValue") String SelectedPEValue);

    //rtsslider           /**/
    @FormUrlEncoded
    @POST("dmlapi/product/getrtssliderdetails")
    Call<RTSItem> getRTSDetail(@Field("product_id") String productId);

    //DOWNLOAD
    //Download product list                          /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/downloadview/")
    Call<DownloadItem> getDownloadProductList(@Field("customer_id") String customerId, @Field("pagesize") String pageSize);

    //Download product add in download list                 /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/download/")
    Call<JsonObject> downloadProduct(@Field("customer_id") String customerId,
                                     @Field("product_id") String productId);

    //Download product image                 /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/DownloadImage/")
    Call<JsonObject> downloadProductImage(@Field("customer_id") String customerId,
                                          @Field("product_id") String productId,
                                          @Field("price") String price);

    //Download product delete          /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/DeleteProduct/")
    Call<JsonObject> deleteProductImage(@Field("customer_id") String customerId,
                                        @Field("product_id") String productId);


    //Download product delete all               /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/DeleteAllProduct/")
    Call<JsonObject> deleteAllProductImage(@Field("customer_id") String customerId,
                                           @Field("product_ids") String productIds,
                                           @Field("chkselected_all") String chkAllSelected);

    //download All Product              /**/
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/DownloadAllImage/")
    Call<JsonObject> downloadAllProductImage(@Field("customer_id") String customerId,
                                             @Field("product_ids") String productId,
                                             @Field("price") String price,
                                             @Field("chkselected_all") String chkAllSelected);

    //TRANSACTION
    @FormUrlEncoded
//    @POST("dmlapi/transaction/transactionview/")
    @POST("public/api/Transactionview")
    Call<TransactionItem> transactionList(@Field("customer_id") String customerId,
                                          @Field("page") String page);


    //ORDER
    @FormUrlEncoded
    @POST("dmlapi/allorder/orderview/")
    Call<OrderItem> orderList(@Field("customer_id") String customerId,
                              @Field("group_id") String group_id,
                              @Field("order") String order,
                              @Field("pagesize") String page,
                              @Field("status") String orderFilter);

    @FormUrlEncoded
    @POST("dmlapi/allorder/OrderViewDetail/")
    Call<OrderDetailItem> orderDetail(@Field("orderid") String orderid);


    //CART                                                     /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/addcartcustom")
    Call<JsonObject> addToCart(@Field("product_id") String productId,
                               @Field("customer_id") String customerId,
                               @Field("option_id") String optionId,
                               @Field("option_type_id") String optionTypeId,
                               @Field("stone_option_id") String stoneOptionId,
                               @Field("stone_option_type_id") String stoneOptionTypeId,
                               @Field("qty") String qty,
                               @Field("metalqualitycolor") String metalQualityColor,
                               @Field("metalcarat") String metalCarat);
    //Addtocart For multiple products
    @FormUrlEncoded
    @POST("dmlapi/addtocart/bulkaddtocart")
    Call<JsonObject> bulkaddToCart(@Field("product_data") String productdata,
                                   @Field("customer_id") String customerId);

    //List cart product                          /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/listcartitems")
    Call<CartServerDataItem> listCart(@Field("customer_id") String customer_id);

    //remove cart item                      /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/removecartitem")
    Call<JsonObject> removeCartItem(@Field("customer_id") String customerId, @Field("item_id") String itemId);


    @FormUrlEncoded
    @POST("dmlapi/addtocart/updatecartqty")
    Call<JsonObject> updateCartQty(@Field("customer_id") String customerId, @Field("item_id") String itemId, @Field("qty") String qty);

    //display payment method                /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/paymentmethods")
    Call<SelectPaymentItem> getSelectPayment(@Field("customer_id") String customerId);

    //                            /**/
    @GET("dmlapi/product/getreadytoshipfilteroption")
    Call<FilterItem> setFilter();

    // Inventory  Filter
    @GET("dmlapi/product/getreadytoshipfilteroption")
    Call<InventoryFilterItem> setInvFilter();


    //    Save Payment                          /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/paymentsave")
    Call<JsonObject> savePayment(@Field("customer_id") String customerId,
                                 @Field("payment_method") String paymentMethod,
                                 @Field("shipping_method") String shippingMethod,
                                 @Field("shipping_price") String shippingPrice);


    // order summary                /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/orderdetailsview")
    Call<OrderSummaryItem> orderSummary(@Field("customer_id") String customerId);

    // place order                              /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/createorders")
    Call<JsonObject> placeOrder(@Field("customer_id") String customerId,
                                @Field("order_flag") String order_flag);


    //    My Stock
    //Listing                          /**/
    @FormUrlEncoded
    @POST("dmlapi/mystock/MystockView")
    Call<MyStockItem> getMyStockProduct(@Field("customer_id") String customerId,
                                        @Field("group_id") String groupId,
                                        @Field("pagesize") String page,
                                        @Field("price") String price,
                                        @Field("gold_purity") String gold_purity,
                                        @Field("diamonod_quality") String diamonod_quality,
                                        @Field("diamond_shape") String diamond_shape,
                                        @Field("sku") String sku,
                                        @Field("availability") String availability,
                                        @Field("sort_by") String sort_by);


    // sale product       /**/
    @FormUrlEncoded
    @POST("dmlapi/Mystock/addmystockproduct")
    Call<JsonObject> MyStockplaceOrder(@Field("product") String product, @Field("customer_id") String customer_id);


    //Cart ,Download Count                              /** /
    @FormUrlEncoded
    @POST("dmlapi/addtocart/getcountCartandDownload")
    Call<JsonObject> getCartDownloadCount(@Field("customer_id") String customerId);

    //    Set Address Default                              /**/
    @FormUrlEncoded
    @POST("dmlapi/addtocart/setaddresses")
    Call<JsonObject> setAddresses(@Field("customer_id") String customerId,
                                  @Field("address_id") String address_id,
                                  @Field("flag_shipping") String flag_shipping);


    //    Set cancel order
    @FormUrlEncoded
    @POST("dmlapi/allorder/CancelOrder")
    Call<JsonObject> cancelOrder(@Field("orderid") String customerId);


    //Listing                        /**/
    @FormUrlEncoded
    @POST("dmlapi/product/search")
    Call<ListingItem> searchProduct(@Field("customer_id ") String customer_id,
                                    @Field("search_term") String searchTerm,
                                    @Field("page") String page);

    @FormUrlEncoded
    @POST("dmlapi/product/searchnew")
    Call<ListingItem> searchNewProduct(@Field("customer_id ") String customer_id,
                                    @Field("group_id") String group_id,
                                    @Field("search_term") String searchTerm,
                                    @Field("page") String page);

    //print order
    @FormUrlEncoded
    @POST("dmlapi/allorder/downloadpdf")
    Call<JsonObject> printOrder(@Field("order_id") String order_id, @Field("customer_id") String customer_id, @Field("group_id") String group_id);

    //contact us                    /**/
    @FormUrlEncoded
    @POST("dmlapi/home/contactus")
    Call<JsonObject> contactUs(@Field("name") String name, @Field("comment") String comment, @Field("email") String email);

    //add to download
    @FormUrlEncoded
    @POST("dmlapi/downloadproduct/addtodownloadlisting/")
    Call<JsonObject> addToDownloadProduct(@Field("product_ids") String product_ids, @Field("customer_id") String customer_id);


    //**************************************Inventory***********************************************
    @FormUrlEncoded
    @POST("dmlapi/inventory/manageinventory")
    Call<InventoryItem> getManageInventory(@Field("page") String page,
                                           @Field("price") String price,
                                           @Field("gold_purity") String gold_purity,
                                           @Field("diamond_quality") String diamond_quality,
                                           @Field("diamond_shape") String diamond_shape);


    //get Inventory list                        /**/
    @FormUrlEncoded
    @POST("public/api/inventoryactions")
    Call<InventoryActionItem> inventoryAction(@Field("productIds") String productIds, @Field("price") String action);


    //get invoice list                      /**/
    @FormUrlEncoded
    @POST("public/api/getInvoiceList")
    Call<InventoryInvoiceItem> getInvoiseList(@Field("page") String page,
                                              @Field("invoice_status") String invoice_status,
                                              @Field("search_value") String search_value,
                                              @Field("customer_id") String customer_id);

    //get payment list                          /**/
    @FormUrlEncoded
    @POST("public/api/getPaymentList")
    Call<InventoryPaymentItem> getPaymentList(@Field("page") String page, @Field("customer_id") String customer_id);

    //get product list                  /**/
    @FormUrlEncoded
    @POST("public/api/getTryProductsList")
    Call<InventoryProductItem> getTryProductsList(@Field("page") String page,
                                                  @Field("customer_id") String customer_id,
                                                  @Field("date") String date,
                                                  @Field("submit_to_dml") String submitToDml);


    //Try product Add                   /**/
    @FormUrlEncoded
    @POST("public/api/StoreTryProduct")
    Call<JsonObject> storeTryProduct(@Field("product_id") String product_id, @Field("customer_id") String customer_id);

    //delete product from try list                  /**/
    @FormUrlEncoded
    @POST("public/api/deleteProductFromTry")
    Call<JsonObject> deleteProductFromTry(@Field("customer_id") String customer_id,
                                          @Field("product_id") String product_id);


    //************************************Quotation*****************************************
    //quotation list                         /**/
    @FormUrlEncoded
    @POST("public/api/getQuotationList")
    Call<InventoryQuotationItem> getQuotationList(@Field("page") String page, @Field("customer_id") String customer_id);


    //**************************Qr Code Scanning & Get Product Id *******************************
    @FormUrlEncoded
    @POST("public/api/Qrscangetproduct")
    Call<QrCodeResponse> getProductId(@Field("certificate_no") String certificate_no);

}
