package tag;

import beans.TravelImage;
import dao.TravelImageDao;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisplayRecent extends SimpleTagSupport {
    private int length;
    private int count;
    private ArrayList<Integer> recentImages= new ArrayList<Integer>();
    private final TravelImageDao travelImageDao = new TravelImageDao();
    private TravelImage travelImage;

    public void setLength(int length){
        this.length = length;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            recentImages = new TravelImageDao().getRecentImagesID(count, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JspWriter out = getJspContext().getOut();

        for(int i = 0; i < count; i ++){
            try {
                travelImage= travelImageDao.getImageDetail(recentImages.get(i));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if(i % length == 0){
                out.println("<div class='row'>");
            }
            out.println("<div class='pic'>");
            out.println("<a href='Detail.jsp?id=" + travelImage.getImageID()  + "'>");
            out.println("<p><img src='../img/travel-images/medium/" + travelImage.getPath() + "'>");
            out.println("<h3>" + travelImage.getTitle() + "</h3>");
            out.println("<p>Uploader: " + travelImage.getUploader() + "</p>");
            out.println("<p>Time: " + travelImage.getUploadedTime() + "</p>");
            out.println("</a>");
            out.println("</div>");

            if((i % length == length - 1) || i == count - 1){
                out.println("</div>");
            }
        }
    }
}
