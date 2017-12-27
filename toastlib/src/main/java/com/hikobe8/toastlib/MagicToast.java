package com.hikobe8.toastlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

/**
 * @author yr
 * @date create at 17-12-26 下午2:33
 * @decription
 */
public class MagicToast {
    private static int checkNotification = 0;
    private Object mToast;

    /**
     *
     * @param context if the notification is closed , this context must be Activity, ApplicationContext will not show !
     * @param message text to be display in toast
     * @param duration LENGTH_SHORT(4000ms), LENGTH_LONG(long 7000ms)
     */
    private MagicToast(Context context, String message, int duration) {
        if(context instanceof Application)
            checkNotification = 0;
        else
            checkNotification = isNotificationEnabled(context) ? 0 : 1;
        if (checkNotification == 1 && context instanceof Activity) {
            mToast = ToastCompat.makeText(context, message, duration);
        } else {
            mToast = android.widget.Toast.makeText(context, message, duration);
        }
    }
    private MagicToast(Context context, int resId, int duration) {
        if(context instanceof Application)
            checkNotification = 0;
        else
            checkNotification = isNotificationEnabled(context) ? 0 : 1;
        if (checkNotification == 1 && context instanceof Activity) {
            mToast = ToastCompat.makeText(context, resId, duration);
        } else {
            mToast = android.widget.Toast.makeText(context, resId, duration);
        }
    }

    public static MagicToast makeText(Context context, CharSequence message, int duration) {
        return new MagicToast(context,message.toString(),duration);
    }

    public static MagicToast makeText(Context context, String message, int duration) {
        return new MagicToast(context,message,duration);
    }
    public static MagicToast makeText(Context context, int resId, int duration) {
        return new MagicToast(context,resId,duration);
    }

    public void show() {
        if(mToast instanceof ToastCompat){
            ((ToastCompat) mToast).show();
        }else if(mToast instanceof android.widget.Toast){
            ((android.widget.Toast) mToast).show();
        }
    }
    public void cancel(){
        if(mToast instanceof ToastCompat){
            ((ToastCompat) mToast).cancel();
        }else if(mToast instanceof android.widget.Toast){
            ((android.widget.Toast) mToast).cancel();
        }
    }
    public void setText(CharSequence s){
        if(mToast instanceof ToastCompat){
            ((ToastCompat) mToast).setText(s);
        }else if(mToast instanceof android.widget.Toast){
            ((android.widget.Toast) mToast).setText(s);
        }
    }
    /**
     * 用来判断是否开启通知权限
     * */
    private static boolean isNotificationEnabled(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public MagicToast setGravity(int gravity, int xOffset, int yOffset) {
        if(mToast instanceof ToastCompat){
            ((ToastCompat) mToast).setGravity(gravity, xOffset, yOffset);
        }else if(mToast instanceof android.widget.Toast){
            ((android.widget.Toast) mToast).setGravity(gravity, xOffset, yOffset);
        }
        return this;
    }
}