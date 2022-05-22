package DB;

import Model.ArticleModel;
import Model.InfoModel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class MYSQLControl {
    //根据自己的数据库地址修改
    static DataSource ds = MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/csdn?useUnicode=true&characterEncoding=UTF8");
    static QueryRunner qr = new QueryRunner(ds);
    //查询一列
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Object> getListOneBySQL (String sql, String id){
        List<Object> list=null;
        try {
            list = (List<Object>) qr.query(sql, new ColumnListHandler(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void executeInsert_Art(List<ArticleModel> datalist){
        Object[][] params = new Object[datalist.size()][4];
        for(int i = 0 ; i < params.length;i++){
            params[i][0] = datalist.get(i).getDate();
            params[i][1] = datalist.get(i).getTitle();
            params[i][2] = datalist.get(i).getViewNum();
            params[i][3] = datalist.get(i).getUrl();
        }
        try{
            qr.batch("insert into article (article_date, article_title, article_viewnum,article_url)"
                    + "values (?,?,?,?)", params);
            System.out.println("执行数据库完毕！"+"成功插入数据：" + datalist.size() + "条");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void executeInsert_Info(List<InfoModel> datalist) {
        Object[][] params = new Object[datalist.size()][2];
        for(int i = 0 ; i < params.length;i++){
            params[i][0] = datalist.get(i).getUrl();
            params[i][1] = datalist.get(i).getText();
        }
        try{
            qr.batch("insert into parameter (para_url, para_text)"
                    + "values (?,?)", params);
            System.out.println("执行数据库完毕！"+"成功插入数据：" + datalist.size() + "条");
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}