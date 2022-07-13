package com.example.CryptoBytes.MyModels.NewsModels;

public class NewsModel {
    public String articleTitle;
    public String articleURL;
    public String articleBody;
    public String imageURL;
    public String sourceName;
    public long publishedOn;

    public NewsModel(String articleTitle, String articleURL, String articleBody, String imageURL, String sourceName, long publishedOn) {
        this.articleTitle = articleTitle;
        this.articleURL = articleURL;
        this.articleBody = articleBody;
        this.imageURL = imageURL;
        this.sourceName = sourceName;
        this.publishedOn = publishedOn;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public long getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(long publishedOn) {
        this.publishedOn = publishedOn;
    }
}
