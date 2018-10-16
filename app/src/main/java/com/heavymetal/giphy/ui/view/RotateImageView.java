package com.heavymetal.giphy.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.heavymetal.giphy.R;

/**
 * Created by hsn on 26/06/2018.
 */

public class RotateImageView extends ImageView {

    private Animation mRotation;
    public boolean isAnimating = false;

    public RotateImageView(Context context) {
        super(context);
        Init(null);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init(attrs);
    }

    private void Init(AttributeSet attrs) {
        startAnimation();
    }

    public void startAnimation() {
        if (mRotation == null) {
            mRotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            mRotation.setRepeatCount(Animation.INFINITE);
        }
        this.startAnimation(mRotation);
        isAnimating  = true;
    }

    public void stopAnimation() {
        if (mRotation != null)
            clearAnimation();
        isAnimating  = false;
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            clearAnimation();
        } else if (visibility == VISIBLE) {
            startAnimation(mRotation);
        }
        super.setVisibility(visibility);
    }
}