package top.soul.dto;

import java.io.Serializable;

public class DyMarks implements Serializable {
    private String id;
    private String videoid;
    private String videoreserve;
    private String markidreserve;
    private String markdescript;
    private String updatetime;
    private String markpeopleid;
    private String markpeoplename;
    private String markimages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getVideoreserve() {
        return videoreserve;
    }

    public void setVideoreserve(String videoreserve) {
        this.videoreserve = videoreserve;
    }

    public String getMarkidreserve() {
        return markidreserve;
    }

    public void setMarkidreserve(String markidreserve) {
        this.markidreserve = markidreserve;
    }

    public String getMarkdescript() {
        return markdescript;
    }

    public void setMarkdescript(String markdescript) {
        this.markdescript = markdescript;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getMarkpeopleid() {
        return markpeopleid;
    }

    public void setMarkpeopleid(String markpeopleid) {
        this.markpeopleid = markpeopleid;
    }

    public String getMarkpeoplename() {
        return markpeoplename;
    }

    public void setMarkpeoplename(String markpeoplename) {
        this.markpeoplename = markpeoplename;
    }

    public String getMarkimages() {
        return markimages;
    }

    public void setMarkimages(String markimages) {
        this.markimages = markimages;
    }
}
