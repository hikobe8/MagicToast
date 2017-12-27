package com.hikobe8.toastlib;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import java.lang.ref.WeakReference;

/**
 * @author yr
 * @date create at 17-12-26 下午5:06
 * @decription　this class is designed for show toast when user close all the notification of App,
 * cause {@link Toast} belongs System Notification
 */
public class ToastCompat {
    private static final int ANIMATION_DURATION = 300;
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private WindowManager manger;
    private Long time = 2000L;
    private View contentView;
    private WindowManager.LayoutParams params;
    private Toast toast;
    private final FOHandler mHandler = new FOHandler(this);

    private static class FOHandler extends Handler {

        WeakReference<ToastCompat> mWeakReferenceActivity;

        public FOHandler(ToastCompat toastCompat) {
            mWeakReferenceActivity = new WeakReference<>(toastCompat);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mWeakReferenceActivity) {
                ToastCompat toastCompat = mWeakReferenceActivity.get();
                if (null != toastCompat)
                    toastCompat.cancel();
            }
        }
    }

    private ToastCompat(Context context, CharSequence text, int HIDE_DELAY){
        manger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(HIDE_DELAY == ToastCompat.LENGTH_SHORT)
            this.time = 2000L;
        else if(HIDE_DELAY == ToastCompat.LENGTH_LONG)
            this.time = 3500L;

        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        contentView = toast.getView();
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = -1;
        params.setTitle("ToastCompat");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.y = 200;
        if (context instanceof Application) {
            params.token = toast.getView().getWindowToken();
        }
    }

    public static ToastCompat makeText(Context context, String text, int HIDE_DELAY){
        return new ToastCompat(context, text, HIDE_DELAY);
    }

    public static ToastCompat makeText(Context context, int resId, int HIDE_DELAY) {
        return makeText(context,context.getText(resId).toString(),HIDE_DELAY);
    }

    public void show(){
        if (contentView.getParent() != null) {
            mHandler.removeMessages(1);
            mHandler.sendEmptyMessageDelayed(1, time);
            return;
        }
        manger.addView(contentView, params);
        ObjectAnimator showAnimation = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
        showAnimation.setDuration(ANIMATION_DURATION);
        showAnimation.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.sendEmptyMessageDelayed(1, time);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        showAnimation.start();
    }

    public void cancel(){
        mHandler.removeCallbacksAndMessages(null);
        ObjectAnimator hideAnimation = ObjectAnimator.ofFloat(contentView, "alpha", 1f, 0f);
        hideAnimation.setDuration(ANIMATION_DURATION);
        hideAnimation.addListener(new Animator.AnimatorListener(){

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    if (contentView.getParent() != null)
                        manger.removeViewImmediate(contentView);
                } catch (IllegalArgumentException e) {
                    //这边由于上下文被销毁后removeView可能会抛出IllegalArgumentException
                    //暂时这么处理，因为ToastCompat是轻量级的，不想和Context上下文的生命周期绑定在一块儿
                    //其实如果真的想这么做，可以参考博文2的第一种实现方式，添加一个空的fragment来做生命周期绑定
                    //http://blog.csdn.net/qq_25867141/article/details/74194503
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        hideAnimation.start();
    }
    public void setText(CharSequence s){
        toast.setText(s);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }

}