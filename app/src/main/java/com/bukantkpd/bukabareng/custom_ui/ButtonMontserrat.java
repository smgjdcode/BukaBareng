package com.bukantkpd.bukabareng.custom_ui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.bukantkpd.bukabareng.R;

/**
 * Created by Ibam on 5/24/2017.
 */

public class ButtonMontserrat extends Button {
    public ButtonMontserrat(Context context) {
        super(context);
        init();
    }

    public ButtonMontserrat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonMontserrat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ButtonMontserrat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat.ttf");
        setTypeface(tf);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBackground(Drawable background) {
        Drawable bg = super.getContext().getResources().getDrawable(R.drawable.button_primary);
        super.setBackground(bg);
    }
}
