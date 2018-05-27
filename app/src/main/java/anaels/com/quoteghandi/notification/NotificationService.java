package anaels.com.quoteghandi.notification;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

import anaels.com.quoteghandi.MainActivity;
import anaels.com.quoteghandi.R;
import anaels.com.quoteghandi.api.FavQsApiHelper;
import anaels.com.quoteghandi.api.model.Quote;
import anaels.com.quoteghandi.api.model.QuotePage;

public class NotificationService extends Service {

    private static final int notificationId = 0;
    private static Random randomGenerator;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        randomGenerator = new Random();
        loadAndDisplayRandomQuote();
    }

    private void loadAndDisplayRandomQuote() {
        FavQsApiHelper.getQuoteByAuthor(this, getString(R.string.author_name), 1, new FavQsApiHelper.OnPageRecovered() {
            @Override
            public void onPageRecovered(QuotePage quotePage) {
                if (quotePage != null) {
                    int index = randomGenerator.nextInt(quotePage.getQuotes().size() - 1);
                    Quote randomQuote = quotePage.getQuotes().get(index);
                    String longText = "\"" + randomQuote.getBody() + "\"";
                    String shortText = longText.substring(0, Math.min(longText.length(), 60)); //60First char

                    if (longText.length() > 60) {
                        shortText += "...";
                    }
                    displayQuote(shortText, longText);
                }
                stopSelf();
            }
        }, new FavQsApiHelper.OnError() {
            @Override
            public void onError() {
                //We also need to stop the service if the the call fails
                stopSelf();
            }
        });
    }

    private void displayQuote(String smallText, String longText) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.quote_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(smallText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(longText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Intent intentMainActivity = new Intent(this, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    @Override
    public void onDestroy() {
    }

}