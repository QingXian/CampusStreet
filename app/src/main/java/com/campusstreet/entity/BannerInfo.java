package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/4.
 */

public class BannerInfo implements Serializable {

    /**
     * id : 1
     * title : 火蚁助力校园生活
     * tag : index
     * image : idx\131382760426360000.jpg
     * url : http://m.baidu.com
     * sort : 3
     */

    private String id;
    private String title;
    private String tag;
    private String image;
    private String url;
    private String sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
