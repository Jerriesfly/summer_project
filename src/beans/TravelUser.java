package beans;

public class TravelUser {
    private int UID;
    private String Email;
    private String userName;
    private String password;
    private int state;
    private String dateJoined;
    private String DateLastModified;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getDateLastModified() {
        return DateLastModified;
    }

    public void setDateLastModified(String dateLastModified) {
        DateLastModified = dateLastModified;
    }

}
