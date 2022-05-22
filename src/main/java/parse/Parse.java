package parse;

import Model.ArticleModel;
import Model.InfoModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class Parse {
    /*
     * 解析产品页面
     * 输入参数为:html,以及listPro
     * */
    public static void getArticleData(String html, List<ArticleModel> listpro){
        Document document = Jsoup.parse(html);
        //每一个result是blog-list-box
        Elements results = document.select("div[class=mainContent]").select("article[class=blog-list-box]");
        for(int rank = 0; rank < results.size();rank++){
            Element result = results.get(rank);
            String Date = result.select("a").select("div[class=view-time-box]").text();
            String Title = result.select("a").select("h4").text();
            String ViewNum = result.select("a").select("span[class=view-num]").text();
            String url = result.select("a").attr("href");
            ArticleModel model = new ArticleModel();
            if(Date.length() != 0){
                model.setUrl(url);
                model.setDate(Date);
                model.setTitle(Title);
                model.setViewNum(ViewNum);
                listpro.add(model);
            }
        }
    }

    public static void getInfoData(String url, String html, List<InfoModel> listInfo){
        Document document = Jsoup.parse(html);
        Elements results = document.select("div[id=content_views]");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < results.size();i++){
            Element result = results.get(i);
            sb.append(result.text() +"\n");
        }
        InfoModel model = new InfoModel();
        model.setUrl(url);
        model.setText(sb.toString());
        System.out.println(sb.toString());
        listInfo.add(model);

    }



}