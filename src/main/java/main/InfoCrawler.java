package main;

import DB.MYSQLControl;
import Model.InfoModel;
import org.apache.http.ParseException;
import parse.Parse;
import util.HTTPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoCrawler {
    public static List<InfoModel> dataList = new ArrayList<InfoModel>();
    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        //从数据库中取URL
        List<Object> urlList = MYSQLControl.getListOneBySQL("select article_url from article", "article_url");
        //待爬的队列
        for (int i = 0; i < urlList.size(); i++) {
            //获取HTML
            String html = HTTPUtils.getRawHtml(urlList.get(i).toString());
            //解析数据
            Parse.getInfoData(urlList.get(i).toString(), html, dataList);
            //批量存储
            if (dataList.size() == 5) {
                MYSQLControl.executeInsert_Info(dataList);
                dataList.clear();
            }
        }
        //不足批量的，单独存储
        MYSQLControl.executeInsert_Info(dataList);
    }
}
