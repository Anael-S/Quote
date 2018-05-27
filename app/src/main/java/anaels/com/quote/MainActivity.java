package anaels.com.quote;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import anaels.com.quote.api.FavQsApiHelper;
import anaels.com.quote.api.model.Quote;
import anaels.com.quote.api.model.QuotePage;
import anaels.com.quote.notification.NotificationHelper;
import anaels.com.quote.ui.QuoteAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.listViewQuotes)
    ListView listViewQuote;

    private ArrayList<Quote> quoteList = new ArrayList<>();

    private Activity mActivity;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean stillResultToLoad = true;
    private int positionToScrollTo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivity = this;

        //Load the first page of the quote
        loadQuotes(currentPage);

        //Init the scroll to load more quote
        initScrollListener();

        //Init the ads
        initAds();
    }

    /**
     * MENU
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionNotificationSettings) {
            NotificationHelper.createAndShowNotificationSettingDialog(mActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initScrollListener() {
        listViewQuote.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!isLoading && stillResultToLoad) {
                        isLoading = true;
                        currentPage = currentPage + 1;
                        positionToScrollTo = firstVisibleItem + 1;
                        loadQuotes(currentPage);
                    }
                }
            }
        });
    }

    private void loadQuotes(int pageNumber) {
        FavQsApiHelper.getQuoteByAuthor(this, "Albert+Einstein", pageNumber, new FavQsApiHelper.OnPageRecovered() {
            @Override
            public void onPageRecovered(QuotePage quotePage) {
                if (quotePage != null) {
                    quoteList.addAll(new ArrayList<>(quotePage.getQuotes()));
                    QuoteAdapter adapterListView = new QuoteAdapter(mActivity, quoteList);
                    listViewQuote.setAdapter(adapterListView);
                    listViewQuote.setSelection(positionToScrollTo);
                    isLoading = false;
                }
            }
        }, new FavQsApiHelper.OnNoMoreResult() {
            @Override
            public void onNoMoreResult() {
                stillResultToLoad = false;
                Snackbar.make(listViewQuote, R.string.no_more_result, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initAds(){
        //Ad
        MobileAds.initialize(this, getString(R.string.app_id_admob));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("51CE1F2EACEE1C8EB4FBA9B4F0F2098F") //Phone
                .addTestDevice("3FA0ACCC8A4E195EE4C1BD13BD8BECED") //S2
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) //Emulator
                .build();
        mAdView.loadAd(adRequest);
    }
}
