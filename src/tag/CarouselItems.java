package tag;

import dao.TravelImageDao;
import dao.TravelUserDao;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarouselItems extends SimpleTagSupport {
    private int count;
    private ArrayList<Integer> hotImages= new ArrayList<Integer>();
    private final TravelImageDao travelImageDao = new TravelImageDao();
    private String path;

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            hotImages = new TravelImageDao().getHotImagesID(count, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JspWriter out = getJspContext().getOut();

        try {
            path = travelImageDao.getImageDetail(hotImages.get(0)).getPath();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        out.println("<div class='carousel-item active'>");
        out.println("<a href='Detail.jsp?id=" + hotImages.get(0) + "'>");
        out.println("<img src='../img/travel-images/large/" + path + "'>");
        out.println("</a>");
        out.println("</div>");

        if(count > 1){
            for(int i = 1; i < count - 1; i++){
                try {
                    path = travelImageDao.getImageDetail(hotImages.get(i)).getPath();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                out.println("<div class='carousel-item'>");
                out.println("<a href='Detail.jsp?id=" + hotImages.get(i) + "'>");
                out.println("<img src='../img/travel-images/medium/" + path + "'>");
                out.println("</a>");
                out.println("</div>");
            }
        }
    }
}
