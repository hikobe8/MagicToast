package com.hikobe8.magictoast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hikobe8.toastlib.MagicToast;
import com.hikobe8.toastlib.ToastCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGo2AppSettingsClick(View view) {
        goToNotificationSettings(this);
    }

    public void onShowToastClick(View view) {
        Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT).show();
    }

    public void onShowMagicToastClick(View view) {
        MagicToast.makeText(this, "Hello Toast!", ToastCompat.LENGTH_SHORT).show();
    }

    /**
     * jump to notification settings page.
     * @param context
     */
    private void goToNotificationSettings(Context context){
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());

        } else if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());

        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);

        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }

        if(intent != null){
            context.startActivity(intent);
        }
    }

}
