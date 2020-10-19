package com.dealermela.utility;

import android.content.Context;
import android.graphics.Typeface;
import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;

public class DmlTextInputEditText extends TextInputEditText {
    public DmlTextInputEditText(Context context) {
        super(context);
        setFont();
    }

    public DmlTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public DmlTextInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_regular.ttf");
        setTypeface(font, Typeface.NORMAL);

    }

}
