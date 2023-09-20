package com.savita.nasa_app.net;

import android.util.Log;

import com.savita.nasa_app.configs.NewsConfig;
import com.savita.nasa_app.models.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class NewsController {
    private static final String TAG = "NewsController";
    public static List<News> get() {
        List<News> newsList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(NewsConfig.URL).get();
            Elements newsElements = document.select(NewsConfig.ARTICLE_SELECTOR);

            for (Element element : newsElements) {
                News news = new News();
                Element header = element.selectFirst(NewsConfig.HEADER_SELECTOR);
                if(header != null) {
                    news.setHeader(header.text());
                    news.setLink(header.attr("href"));
                } else {
                    news.setHeader("");
                    news.setLink(null);
                }

                Element body = element.selectFirst(NewsConfig.TEXT_SELECTOR);
                if(body != null) {
                    news.setText(body.text());
                } else {
                    news.setText("");
                }

                Element preview = element.selectFirst(NewsConfig.IMAGE_SELECTOR);
                if(preview != null) {
                    news.setImageUrl(preview.attr("data-src"));
                } else {
                    news.setImageUrl(null);
                }

                Element articleInfo = element.selectFirst(NewsConfig.ARTICLE_INFO_SELECTOR);
                if(articleInfo != null) {
                    String selector = "div:eq(1) div:eq(1) p";

                    Element category = articleInfo.selectFirst(NewsConfig.ARTICLE_INFO_CATEGORY_SELECTOR);
                    if (category != null) {
                        news.setCategory(category.text());
                    } else {
                        news.setCategory("");
                    }

                    Element time = articleInfo.selectFirst(NewsConfig.ARTICLE_INFO_TIME_SELECTOR);
                    if (time != null) {
                        news.setTime(time.text());
                    } else {
                        news.setTime("");
                    }
                }

                newsList.add(news);
            }
        } catch (Exception ex) {
            Log.d(TAG, "get: ", ex);
        }

        return newsList;
    }
}
