package com.mostafa_anter.capstoneproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa_anter on 1/1/17.
 */

public class NewsResponse {
    private String status;
    private String source;
    private String sortBy;
    private List<Article> articles;

    public NewsResponse(String status, String source, String sortBy, List<Article> articles) {
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        List<String> mStrings = new ArrayList<>();
        for (Article article:
             getArticles()) {
            String item = article.getAuthor() + article.getTitle() + "\n";
            mStrings.add(item);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s :
             mStrings) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
