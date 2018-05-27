package anaels.com.quote.notification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.SwitchCompat;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;

import anaels.com.quote.R;

/**
 * Handle the notification settings
 */
public class NotificationHelper {

    private static final String MyPREFERENCES = "GemvisionPrefs";
    private static final String KEY_ENABLE_NOTIFICATION = "keySPNotification";

    public static Dialog createAndShowNotificationSettingDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification_settings);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final SwitchCompat switchNotification = (SwitchCompat) dialog.findViewById(R.id.switchNotification);

        switchNotification.setChecked(isNotificationEnabled(activity));

        if (isNotificationEnabled(activity)){
            NotificationScheduler.scheduleNotificationsUpdates(activity);
        }

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEnableNotification(activity, isChecked);
                if (isChecked){
                    NotificationScheduler.scheduleNotificationsUpdates(activity);
                } else {
                    NotificationScheduler.cancelNotificationSchedule(activity);
                }
            }
        });

        if (!activity.isFinishing() && !activity.isDestroyed()) {
            dialog.show();
        }

        return dialog;
    }

    private static void setEnableNotification(Activity activity, boolean enable) {
        SharedPreferences sharedPref = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_ENABLE_NOTIFICATION, enable);
        editor.apply();
    }

    public static boolean isNotificationEnabled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_ENABLE_NOTIFICATION, false);
    }
}
