package com.dealermela.util;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.util.Log;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {
    public static boolean checkEmpty(EditText edt,String msg) {
        if (edt.getText().toString().isEmpty()) {
            edt.setError(msg);
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }



    public static boolean checkEmptyInputLayout(TextInputEditText edt,String msg) {
        if (Objects.requireNonNull(edt.getText()).toString().isEmpty()) {
            edt.setError(msg);
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkEmptyInputEditText(TextInputEditText edt, TextInputLayout til, String msg) {
        if (Objects.requireNonNull(edt.getText()).toString().isEmpty()) {
            edt.setError(msg);
//            til.setError(msg);
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkLimit(EditText edt) {
        if (edt.getText().toString().length() > 50) {
            edt.setError("field required");
            edt.setFocusable(true);
            edt.requestFocus();

            return false;
        }
        return true;
    }

    public static boolean checkDescLimit(EditText edt) {
        if (edt.getText().toString().length() > 300) {
            edt.setError("field required");
            edt.setFocusable(true);
            edt.requestFocus();

            return false;
        }
        return true;
    }

    public static boolean checkPhone(EditText edt) {

        String PHONE_PATTERN = "\\d{4}([- ]*)\\d{6}";
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());
        boolean flg = matcher.matches();
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            edt.setError("Phone Number is not valid");
            return false;
        }
        return true;

    }

    public static boolean checkEmail(EditText edt) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());

        boolean flg = matcher.matches();
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() != 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static boolean isValidZip(CharSequence target) {
        if (target.length() != 6) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkAlphaNumeric(EditText edt) {
        String PATTERN = "[-\\p{Alnum}]+";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());
        boolean flg = matcher.matches();
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean checkOnlyAlpha(EditText edt) {
        String PATTERN = "[a-zA-z\\s]*";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());
        boolean flg = matcher.matches();
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkPin(EditText edt) {
        String PATTERN = "^\\(?([0-9]{3})\\)?[-.\\\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());
        boolean flg = matcher.matches();
        Log.e("---", "-" + matcher.matches());
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkPasswordLength(EditText reg_password) {
        String value = reg_password.getText().toString();
        int password_length = value.length();
        if (password_length < 8) {
            reg_password.setError("Password length is not 8.");
            reg_password.setFocusable(true);
            reg_password.requestFocus();
            return false;
        }
        if (password_length > 25) {
            reg_password.setError("Password length is more 25.");
            reg_password.setFocusable(true);
            reg_password.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkPassword(EditText edt) {
        String PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,25}$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(edt.getText().toString());
        boolean flg = matcher.matches();
        if (!flg) {
            edt.setFocusable(true);
            edt.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean checkAdult(int year, int month, int day) {
        Calendar userAge = new GregorianCalendar(year, month, day);
        Calendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -18);
        if (minAdultAge.before(userAge)) {
            return false;
        }
        return true;
    }
}
