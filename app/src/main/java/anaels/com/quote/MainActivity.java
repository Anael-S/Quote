package anaels.com.quote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import anaels.com.quote.api.FavQsApiHelper;
import anaels.com.quote.api.model.QuotePage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       loadQuote();
    }

    private void loadQuote(){
        FavQsApiHelper.getQuoteByAuthor(this, "Albert+Einstein", new FavQsApiHelper.OnPageRecovered() {
            @Override
            public void onPageRecovered(QuotePage quotePage) {
                if (quotePage != null){
                    Log.d("test", quotePage.toString());
                }
            }
        }, new FavQsApiHelper.OnError() {
            @Override
            public void onError() {
                Log.d("test", "error");
            }
        });
    }
}
