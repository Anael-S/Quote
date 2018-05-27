package anaels.com.quotemarktwain.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class NotificationScheduler extends BroadcastReceiver {

    private static Intent mNotificationService;
    private static PendingIntent pending;


    @Override
    public void onReceive(final Context context, final Intent intent) {
        mNotificationService = new Intent(context, NotificationService.class);
        context.startService(mNotificationService);
    }


    private static void stopNotificationUpdates(final Context context) {
        if (context != null && mNotificationService != null) {
            context.stopService(mNotificationService);
        }
    }

    public static void cancelNotificationSchedule(final Context context) {
        stopNotificationUpdates(context);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pending);
    }

    /**
     * Schedule the next update
     *
     * @param context the current application context
     */
    public static void scheduleNotificationsUpdates(final Context context) {
        stopNotificationUpdates(context);
        // create intent for our alarm receiver (or update it if it exists)
        final Intent intent = new Intent(context, NotificationScheduler.class);
        pending = PendingIntent.getBroadcast(context, 0, intent, 0);

        // compute first call time 5s from now
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        long trigger = calendar.getTimeInMillis();

        // set delay between each call : 24 Hours
        long delay = 24 * 60 * 60 * 1000;

        // Set alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, delay, pending);
        // you can use RTC_WAKEUP instead of RTC to wake up the device
    }
}