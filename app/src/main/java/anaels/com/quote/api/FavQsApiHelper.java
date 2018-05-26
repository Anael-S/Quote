package anaels.com.quote.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import anaels.com.quote.BuildConfig;
import anaels.com.quote.R;
import anaels.com.quote.api.model.QuotePage;

/**
 * Helper to communicate with FavQs API, see https://favqs.com/api
 */
public class FavQsApiHelper {

    private static RequestQueue queueVolley;

    //URL
    private static final String BASE_URL = "https://favqs.com/api/quotes/?";
    private static final String PARAM_FILTER = "filter=";
    private static final String PARAM_AND = "&";
    private static final String PARAM_TYPE = "type=";
    private static final String PARAM_PAGE = "page=";
    private static final String FILTER_TYPE_AUTHOR = "author";
    private static final String FILTER_TYPE_TAG = "tag";

    //HEADER
    private static final String HEADER_KEY_AUTH = "Authorization";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String HEADER_VALUE_AUTH = "Token token="  + API_KEY;

    /**
     * CALLBACK
     */
    public interface OnPageRecovered {
        void onPageRecovered(QuotePage quotePage);
    }
    public interface OnError {
        void onError();
    }

    /**
     * Make an API call to get the first quote page from an author
     * @param context the context
     * @param author the author
     * @param onPageRecovered the callback that will fire if the page is recovered
     * @param onError the callback that will fire if an error occurs
     */
    public static void getQuoteByAuthor(Context context, String author, OnPageRecovered onPageRecovered, OnError onError) {
        getQuoteByFilter(context, author, FILTER_TYPE_AUTHOR,null, onPageRecovered, onError);
    }

    /**
     * Make an API call to get a specific quote page from an author
     * @param context the context
     * @param author the author
     * @param page the page number
     * @param onPageRecovered the callback that will fire if the page is recovered
     * @param onError the callback that will fire if an error occurs
     */
    public static void getQuoteByAuthor(Context context, String author, int page, final OnPageRecovered onPageRecovered, OnError onError) {
        String strPage = String.valueOf(page);
        getQuoteByFilter(context, author, FILTER_TYPE_AUTHOR,strPage, onPageRecovered, onError);
    }

    /**
     * Make the call to get a quote page according to some filter
     * @param context the context
     * @param filter the filter
     * @param filterType the filter type
     * @param page the page number
     * @param onPageRecovered the callback that will fire if the page is recovered
     * @param onError the callback that will fire if an error occurs
     */
    private static void getQuoteByFilter(final Context context, String filter, String filterType, String page, final OnPageRecovered onPageRecovered, final OnError onError) {
        if (queueVolley == null) {
            queueVolley = Volley.newRequestQueue(context);
        }
        StringRequest requestQuote = new StringRequest(com.android.volley.Request.Method.GET, getUrlQuoteByFilter(filterType,filter,page), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSONReponse", response);
                Type returnType = new TypeToken<QuotePage>() {
                }.getType();
                QuotePage pageResult = SerializeHelper.deserializeJson(response, returnType);
                if (pageResult != null) {
                    onPageRecovered.onPageRecovered(pageResult);
                } else {
                    onError.onError();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError.onError();
                Toast.makeText(context, R.string.error_load, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(HEADER_KEY_AUTH, HEADER_VALUE_AUTH);
                return params;
            }
        };
        requestQuote.setRetryPolicy(new DefaultRetryPolicy(
                30 * 1000, //30sec timeout
                3, // 3 retry before error
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queueVolley.add(requestQuote);

    }

    /**
     * Construct the URL to get a quote page according to some filter
     * @param filterType the filter type
     * @param filter the filter
     * @param page the page number
     * @return the URL
     */
    private static String getUrlQuoteByFilter(String filterType, String filter, String page){
        if (page == null || page.isEmpty()){
            page = "1";
        }
        return BASE_URL + PARAM_FILTER +filter + PARAM_AND + PARAM_TYPE + filterType + PARAM_AND + PARAM_PAGE + page;
    }

}
