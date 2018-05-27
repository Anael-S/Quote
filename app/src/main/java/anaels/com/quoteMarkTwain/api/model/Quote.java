
package anaels.com.quotemarktwain.api.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dialogue")
    @Expose
    private Boolean dialogue;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("favorites_count")
    @Expose
    private Integer favoritesCount;
    @SerializedName("upvotes_count")
    @Expose
    private Integer upvotesCount;
    @SerializedName("downvotes_count")
    @Expose
    private Integer downvotesCount;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("author_permalink")
    @Expose
    private String authorPermalink;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("context")
    @Expose
    private String context;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDialogue() {
        return dialogue;
    }

    public void setDialogue(Boolean dialogue) {
        this.dialogue = dialogue;
    }

    public Boolean getPrivate() {
        return _private;
    }

    public void setPrivate(Boolean _private) {
        this._private = _private;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Integer getUpvotesCount() {
        return upvotesCount;
    }

    public void setUpvotesCount(Integer upvotesCount) {
        this.upvotesCount = upvotesCount;
    }

    public Integer getDownvotesCount() {
        return downvotesCount;
    }

    public void setDownvotesCount(Integer downvotesCount) {
        this.downvotesCount = downvotesCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorPermalink() {
        return authorPermalink;
    }

    public void setAuthorPermalink(String authorPermalink) {
        this.authorPermalink = authorPermalink;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getShareMessage(){
        String messageToShare = "";
        messageToShare = "\"" + getBody() + "\"" + " -" + getAuthor();
        if (getTags() != null && !getTags().isEmpty()){
            for (String lTag : getTags()){
                messageToShare += " #"+lTag;
            }
        }
        return messageToShare;
    }

}
