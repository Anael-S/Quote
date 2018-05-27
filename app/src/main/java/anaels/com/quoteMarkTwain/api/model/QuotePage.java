
package anaels.com.quotemarktwain.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuotePage {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("last_page")
    @Expose
    private Boolean lastPage;
    @SerializedName("quotes")
    @Expose
    private List<Quote> quotes = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

}
