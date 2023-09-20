package com.savita.nasa_app.configs;

public class NewsConfig {
    public static final String URL = "https://phys.org/space-news/";
    public static final String ARTICLE_SELECTOR = "div.sorted-news-list article";
    public static final String IMAGE_SELECTOR = "img";
    public static final String HEADER_SELECTOR = "div.sorted-article-content h3 a";
    public static final String TEXT_SELECTOR = "div.sorted-article-content p";
    public static final String ARTICLE_INFO_SELECTOR = "div.article__info";
    public static final String ARTICLE_INFO_CATEGORY_SELECTOR = "div:first-child p";
    public static final String ARTICLE_INFO_TIME_SELECTOR = "div:eq(1) div:eq(1) p";
}
