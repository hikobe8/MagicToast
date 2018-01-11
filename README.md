# MagicToast
resolution of Toast doesn't show when App's Notification is closed above Android version 5.0
## How to Use
1. download or copy ToastCompat.java and MagicToast.java to your own project
2. just replace your 'Toast.makeText(Context context, CharSequence message, int duration)'
to 'MagicToast. makeText(Context context, CharSequence message, int duration)'
### Note
MagicToast will judge using which toast(android.widget.Toast or ToastCompat) depends on App's notification is closed.
### what' more?
this version just work on Activity Context, ApplicationContext support is coming soon;
