package com.dealermela.util;

//import android.NumberFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermela.R;
import com.dealermela.home.activity.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public final class CommonUtils {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat outputsdf = new SimpleDateFormat("dd-MM-yyyy");

    private static final String ALLOWED_CHARACTERS = AppConstants.RANDOM_STR;

    public static String rupeeFormat(String value) {
        value = value.replace(",", "");
        char lastDigit = value.charAt(value.length() - 1);
        String result = "";
        int len = value.length() - 1;
        int nDigits = 0;

        for (int i = len - 1; i >= 0; i--) {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0)) {
                result = "," + result;
            }
        }
        return (result + lastDigit);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String priceFormat(Float amount) {
        String price;
//        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));       // This Gives symbol of rupees in Higher version of Android
//        NumberFormat formatter = NumberFormat.getInstance();
//        price = formatter.format(amount);  //In this we don't get decimal value
//        price = price.substring(0, price.length() - 3);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "IN"));
        price = formatter.format(amount);
        return price;
//        return formatter.format(amount);
    }

    public static void Logout(Activity from) {
        Intent i = new Intent(from, MainActivity.class);
        from.startActivity(i);
        from.finishAffinity();
    }

    //String encode
    public static String encodeEmoji(String message) {
        try {
            return URLEncoder.encode(message,
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return message;
        }
    }

    //String decode
    public static String decodeEmoji(String message) {
        String myString = null;
        try {
            return URLDecoder.decode(
                    message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return message;
        }
    }

    public static Date convert_date(String date) {
        Date mDate = null;
        try {
            mDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    public static String convert_dateformate(String date) {
        Date mDate = null;
        String formatedDate = null;
        try {
            mDate = sdf2.parse(date);
            formatedDate=outputsdf.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

    public static String giveDate(String time) {
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    public static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static void showToast(Context context, String message) {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view1 = toast.getView();
        toast.getView().setPadding(20, 20, 20, 20);
        view1.setBackgroundResource(R.color.green);
//        view1.setTextColor(Color.WHITE);
        toast.show();

    }

    public static void showSuccessToast(Context context, String message) {
        Toasty.success(context, message, Toast.LENGTH_LONG, true).show();
    }

    public static void showErrorToast(Context context, String message) {
        Toasty.error(context, message, Toast.LENGTH_LONG, true).show();

    }

    public static void showInfoToast(Context context, String message) {
        Toasty.info(context, message, Toast.LENGTH_LONG, true).show();

    }
    public static void showInfoToastShort(Context context, String message) {
        Toasty.info(context, message, Toast.LENGTH_SHORT, true).show();

    }

    public static void showWarningToast(Context context, String message) {
        Toasty.warning(context, message, Toast.LENGTH_LONG, true).show();

    }

    @SuppressLint("HardwareIds")
    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(pInfo).versionName;

    }

    public static void setButtonBackgroundColor(Context context, Button button, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            button.setBackgroundColor(context.getResources().getColor(color, null));
        } else {
            button.setBackgroundColor(context.getResources().getColor(color));
        }
    }

    public static void setButtonTextColor(Context context, Button button, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            button.setTextColor(context.getResources().getColor(color, null));
        } else {
            button.setTextColor(context.getResources().getColor(color));
        }
    }

    public static void setTextViewTextColor(Context context, TextView textView, int color) {

        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextColor(context.getResources().getColor(color, null));
        } else {
            textView.setTextColor(context.getResources().getColor(color));
        }
    }

    public static void setTextViewBackgroundColor(Context context, TextView textView, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setBackgroundColor(context.getResources().getColor(color, null));
        } else {
            textView.setBackgroundColor(context.getResources().getColor(color));
        }
    }

    public static Drawable setDrawableSelector(Context context, int normal, int selected) {


        Drawable state_normal = ContextCompat.getDrawable(context, normal);

        Drawable state_pressed = ContextCompat.getDrawable(context, selected);

        Bitmap state_normal_bitmap = ((BitmapDrawable) Objects.requireNonNull(state_normal)).getBitmap();

        // Setting alpha directly just didn't work, so we draw a new bitmap!
        Bitmap disabledBitmap = Bitmap.createBitmap(
                state_normal.getIntrinsicWidth(),
                state_normal.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(disabledBitmap);

        Paint paint = new Paint();
        paint.setAlpha(126);
        canvas.drawBitmap(state_normal_bitmap, 0, 0, paint);

        BitmapDrawable state_normal_drawable = new BitmapDrawable(context.getResources(), disabledBitmap);


        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[]{android.R.attr.state_selected},
                state_pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                state_normal_drawable);

        return drawable;
    }

    public static StateListDrawable selectorRadioImage(Context context, Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, pressed);
        states.addState(new int[]{}, normal);
        //                imageView.setImageDrawable(states);
        return states;
    }

    public static StateListDrawable selectorRadioButton(Context context, int normal, int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    public static ColorStateList selectorRadioText(Context context, int normal, int pressed) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}}, new int[]{pressed, normal});
    }

    public static StateListDrawable selectorRadioDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static StateListDrawable selectorBackgroundColor(Context context, int normal, int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    public static StateListDrawable selectorBackgroundDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static ColorStateList selectorText(Context context, int normal, int pressed) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}}, new int[]{pressed, normal});
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //character capital of white space
    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
