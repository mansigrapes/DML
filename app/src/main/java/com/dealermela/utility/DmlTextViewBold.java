package com.dealermela.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class DmlTextViewBold extends androidx.appcompat.widget.AppCompatTextView {
    public DmlTextViewBold(Context context) {
        super(context);
        setFont();
    }

    public DmlTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public DmlTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
