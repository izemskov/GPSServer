package ru.develgame.gpsdomain;

public class GPSViewerUser {
    private long id;

    private String name;

    private String pwd;

    public GPSViewerUser() {
    }

    public GPSViewerUser(long id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
