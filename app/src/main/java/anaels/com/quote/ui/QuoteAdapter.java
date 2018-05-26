package anaels.com.quote.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import anaels.com.quote.R;
import anaels.com.quote.api.model.Quote;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuoteAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<Quote> quoteList;

    //UI
    @BindView(R.id.textviewQuote)
    TextView textviewQuote;
    @BindView(R.id.textViewAuthor)
    TextView textViewAuthor;
    @BindView(R.id.textViewHashtag)
    TextView textViewHashtag;
    @BindView(R.id.imageButtonFacebook)
    ImageButton imageButtonFacebook;
    @BindView(R.id.imageButtonTwitter)
    ImageButton imageButtonTwitter;

    private static int MAX_TAG_DISPLAYED = 2;

    public QuoteAdapter(Activity activity, ArrayList<Quote> quoteList) {
        this.activity = activity;
        this.quoteList = quoteList;
    }

    @Override
    public int getCount() {
        return quoteList.size();
    }

    @Override
    public Object getItem(int position) {
        return quoteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.row_quote, parent, false);
        }
        ButterKnife.bind(this, rowView);

        final Quote currentQuote = quoteList.get(position);
        if (currentQuote != null) {
            //Quote
            textviewQuote.setText(currentQuote.getBody());
            //Author
            textViewAuthor.setText(currentQuote.getAuthor());
            //Tags
            if (currentQuote.getTags() != null && !currentQuote.getTags().isEmpty()) {
                String tagsStr = "#";

                for (int i = 0; i < MAX_TAG_DISPLAYED; i++) {
                    if (i >= currentQuote.getTags().size()) {
                        break;
                    }
                    if (i != 0) tagsStr += "#";
                    tagsStr += currentQuote.getTags().get(i) + " ";
                }
                textViewHashtag.setText(tagsStr);
            }
        }

        imageButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFacebook(currentQuote);
            }
        });

        imageButtonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTwitter(currentQuote);
            }
        });

        return rowView;
    }

    private void shareFacebook(Quote quote) {
        //TODO
    }

    private void shareTwitter(Quote quote) {
        //TODO
    }
}