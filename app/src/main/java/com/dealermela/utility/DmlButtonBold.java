package com.dealermela.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class DmlButtonBold extends androidx.appcompat.widget.AppCompatButton {
    public DmlButtonBold(Context context) {
        super(context);
        setFont();
    }

    public DmlButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public DmlButtonBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}
