package com.imagine.goldenscent;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * A Button that gives a click simple feeling when pressing it (for the sake of UX)
 */
public class MyCustomClickableButton extends android.support.v7.widget.AppCompatButton
        implements View.OnClickListener {

    public MyCustomClickableButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public MyCustomClickableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public MyCustomClickableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        // give click feeling
        v.setAlpha(0.3f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setAlpha(1.0f);
            }
        }, 300);
    }
}
