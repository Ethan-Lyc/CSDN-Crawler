package main;

import DB.MYSQLControl;
import Model.ArticleModel;
import org.apache.http.ParseException;
import parse.Parse;
import util.HTTPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleCrawler {
    public static void main(String[] args) throws ParseException, IOException {
        List<String> urlList = new ArrayList<String>();
        urlList.add("https://blog.csdn.net/qy20115549");
        List<ArticleModel> listData = new ArrayList<ArticleModel>();
        for (int i = 0; i < urlList.size(); i++) {
            //获取HTML
            String html = HTTPUtils.getRawHtml(urlList.get(i));
            //解析HTML
            Parse.getArticleData(html, listData);
        }
        //存储数据
        MYSQLControl.executeInsert_Art(listData);
    }
}
