package servlets;

import Utils.JdbcUtil;
import dao.TravelImageDao;
import dao.TravelUserDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UploadServlet extends HttpServlet {
    static DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
    static ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileItemFactory.setSizeThreshold(1024 * 1024);
        File file = new File("C:\\tmp");
        fileItemFactory.setRepository(file);
        upload.setSizeMax(1024 * 1024 * 20);
        upload.setHeaderEncoding("UTF-8");

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            HashMap<String, String> map = new HashMap<>();

            for(FileItem fileItem: fileItems){
                if(fileItem.isFormField()){
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    map.put(name, value);
                } else{
                    String fileName;
                    String contentType = fileItem.getContentType();

                    String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    String path = req.getSession().getAttribute("userName") + "-" + time +
                            "." + contentType.split("/")[1];

                    fileName = getServletContext().getRealPath("/") + "/img/travel-images/large/" + path;

                    if(!new File(fileName).exists())
                        new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdir();
                    fileItem.write(new File(fileName));

                    fileName = getServletContext().getRealPath("/") + "/img/travel-images/medium" + path;
                    if(!new File(fileName).exists())
                        new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdir();
                    fileItem.write(new File(fileName));

                    fileName = getServletContext().getRealPath("/") + "/img/travel-images/small" + path;
                    if(!new File(fileName).exists())
                        new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdir();
                    fileItem.write(new File(fileName));

                    TravelImageDao travelImageDao = new TravelImageDao();
                    String sql;
                    String title = map.get("title");
                    String description = map.get("description");
                    int cityCode = travelImageDao.getCityCode(map.get("city"));
                    String countryISO = travelImageDao.getCountryISO(map.get("city"));
                    int UID = new TravelUserDao().getUID((String) req.getSession().getAttribute("UserName"));
                    String content = map.get("content");
                    int imageID = Integer.parseInt(map.get("imageID"));

                    if(map.get("isModify").equals("true")){
                        sql = "UPDATE travelimage SET Title = ?, Description = ?, CityCode = ?, Country_RegionCodeISO = ?, " +
                                "UID = ?, PATH = ?, Content = ?, UploadedTime = ? WHERE ImageID = ?";
                    } else{
                        sql = "INSERT INTO travelimage(Title, Description, CityCode, Country_RegionCodeISO, UID, PATH, Content, UploadedTime)" +
                                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    }
                    Connection connection = JdbcUtil.getConnection();

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, title);
                    preparedStatement.setString(2, description);
                    preparedStatement.setInt(3, cityCode);
                    preparedStatement.setString(4, countryISO);
                    preparedStatement.setInt(5, UID);
                    preparedStatement.setString(6, path);
                    preparedStatement.setString(7, content);
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(time));

                    if(map.get("isModify").equals("true")){
                        preparedStatement.setInt(9, imageID);
                    }

                    preparedStatement.execute();
                    JdbcUtil.close(null, preparedStatement, connection);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/jsp/MyPhotos.jsp");
    }
}
