package com.dealermela.util;

import android.app.Activity;
import com.dealermela.R;

public final class AppConstants {
    //    API base urlprli

    public static final String BASE_URL = "http://203.112.156.41/dmlmage/index.php/";     //Live Server URL Changed On 18/02/2021
//    public static final String BASE_URL = "http://203.112.156.63/dmlmage/index.php/";   // Test server URL Changed On 18/02/2021
//    public static final String BASE_URL = "http://192.168.0.124/DML-Internal-Software-2.0/public/api/";   //set temp for check On 13/04/2021 set this in new project dmp appNew_Api

    public static final String BASE_URL_LARAVEL = "http://203.112.156.40/dmlsoftware/";      //Live server URL Changed on 05/03/2021
//    public static final String BASE_URL_LARAVEL = "http://203.112.156.61/dmlsoftwaretest/public/";   //Test server URL Changed on 19/02/2021

//public static final String BASE_URL_LARAVEL = "http://203.112.144.109/dmlsoftware/";      //OLD Live server URL
//    public static final String BASE_URL_LARAVEL = "http://203.112.144.109/dmlsoftwaretest/";   //OLd Test server URL

//    public static final String BASE_URL = "http://www.diamondmela.com/";
//    public static final String BASE_URL = "http://203.112.144.7/dmlmage/";    // Live url changed on 17/10/2020 OLD

//    public static final String BASE_URL = "http://123.108.51.11/index.php/";    //OLD  Test server
//    public static final String BASE_URL_LARAVEL = "http://203.112.144.109/dmlsoftwaretest/";   //OLD Test server

    public static final String INVENTORY_IMAGE = "http://203.112.144.7/dmlmage/media/catalog/product";

    public static final String IMAGE_URL = "http://203.112.156.41/dmlmage/media/";    //New ImageUrl
//      public static final String IMAGE_URL = "http://203.112.144.7/dmlmage/media/";

//    public static final String IMAGE_URL = "http://www.diamondmela.com/media/";
//    public static final String IMAGE_URL = "http://123.108.51.11/media/";

    //    using gor random number generator string
    public static final String RANDOM_STR = "0123456789qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUWXYZ";

    // Splash screen timer
    public static final int SPLASH_TIME_OUT = 3000;
    public static final int ADD_TIME_OUT = 10000;  //adding for Login Addtocart API calling in delayed

    public static final String BUNDLE = "BUNDLE";
    public static final String NAME = "name";
    public static final String MAIN_NAME = "Category_name";
    public static final String SubCategory_ID = "Sub_id";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ID = "id";
    public static final String METALCOLOR = "metal_color";
    public static final String CARATVALUE = "carat_value";

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failure";
    public static final String STATUS_CODE_FAIL = "fail";

    public static final String MY_PREF = "MyPrefs";
//    public static final String WHITE_THEME = "whiteTheme";
    public static final String WHITE_THEME = "white";
    public static final String PREF_LOGIN = "login";
    public static final String REMEMBER_PWD = "REMEMBER_PWD";
    public static final String PREF_BILLING_ADDRESS = "billing";
    public static final String PREF_SHIPPING_ADDRESS = "shipping";
    public static final String PREF_PASSWORD = "pwd";
    public static final String PREF_EMAILS = "emails";
    public static final String BLACK_THEME = "blackTheme";

    public static final String RESPONSE = "response";
    public static final String ERROR = "error";
    public static final String RS = "Rs. ";

    //    Product Category Id

    public static final String RING_ID = "14";
    public static final String BRACELETS_ID = "124";
    public static final String BANGLE_ID = "9";
    public static final String EARRINGS_ID = "6";
    public static final String PENDANTS_SETS_ID ="287";
    public static final String PENDANTS_ID = "7";
    public static final String NOSEPIN_ID = "289";
    public static final String NECKLACE_ID = "290";
    public static final String COLLECTION_ID = "291";
    public static final String RUBBER_ID = "295";
    public static final String COLON = " : ";
    // New category Added Necklaces&sets On 17/09/2020
    public static final String NECKLACES_SETS_ID = "8";

    // LIVE
    public static final String MERCHANT_ID="180766";      //live
    public static final String ACCESS_CODE="AVER78FF72BP07REPB";  //LIVE
    public static final String Encryption_Key="E4A7AA53091F2636621704AD319D9C25";

    // NEW LIVE
    public static final String redirectUrl="http://203.112.156.41/dmlmage/RSA/ccavMobileResponseHandler.php";  //Live
    public static final String cancelUrl="http://203.112.156.41/dmlmage/RSA/ccavMobileResponseHandler.php";   //Live
    public static final String rsaKeyUrl="http://203.112.156.41/dmlmage/dmlapi/addtocart/getRSAkey";      //Live

//    public static final String redirectUrl="http://203.112.144.7/dmlmage/RSA/ccavMobileResponseHandler.php";  //Live
//    public static final String cancelUrl="http://203.112.144.7/dmlmage/RSA/ccavMobileResponseHandler.php";   //Live
//    public static final String rsaKeyUrl="http://203.112.144.7/dmlmage/dmlapi/addtocart/getRSAkey";      //Live

//    public static final String redirectUrl="http://www.diamondmela.com/RSA/ccavMobileResponseHandler.php";  //Live
//    public static final String cancelUrl="http://www.diamondmela.com/RSA/ccavMobileResponseHandler.php";   //Live
//    public static final String rsaKeyUrl="http://www.diamondmela.com/dmlapi/addtocart/getRSAkey";      //Live

//    public static final String MERCHANT_ID="180765";    //test
//    public static final String ACCESS_CODE="AVUR78FF89CI31RUIC";  //test
//    public static final String Encryption_Key="103624400069892DCA0674A6254989C0";  //TEST
//    public static final String redirectUrl="http://123.108.51.11/RSA/ccavMobileResponseHandler.php";    //TEST
//    public static final String cancelUrl="http://123.108.51.11/index.php/RSA/ccavMobileResponseHandler.php";      //TEST
//    public static final String rsaKeyUrl="http://123.108.51.11/index.php/dmlapi/addtocart/getRSAkey";            //TEST

    public static final String CURRENCY="INR";

    public static final String bannerListCheck="banner";
    public static final String USER_NAME="dealermela";
    public static final String PASSWORD="4FgTc&8mbv\"D$6eW";
    public static final String PLEASE_WAIT = "Please wait";
}
