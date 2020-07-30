package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CarouselIndicator extends SimpleTagSupport {
    private int count;
    public void setCount(int count){
        this.count = count;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<li data-target='#display-carousel' data-slide-to='0' class='active'></li>");

        if(count > 1){
            for(int i = 1; i < count; i ++){
                out.println("<li data-target='#display-carousel' data-slide-to='" + i + "'></li>");
            }
        }
    }
}
