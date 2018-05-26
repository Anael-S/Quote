package anaels.com.quote;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

import anaels.com.quote.api.FavQsApiHelper;
import anaels.com.quote.api.model.Quote;
import anaels.com.quote.api.model.QuotePage;
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
}
