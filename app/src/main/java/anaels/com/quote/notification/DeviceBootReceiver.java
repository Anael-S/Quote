package anaels.com.quote.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Set the notification schedule if necessary when the device bootup
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            if (NotificationHelper.isNotificationEnabled(context)) {
                NotificationScheduler.scheduleNotificationsUpdates(context);
            }
        }
    }
}