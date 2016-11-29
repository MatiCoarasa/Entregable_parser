package com.ejemplo.album.model;

/**
 * Created by dh-mob-tm on 07/11/16.
 */
public class Album {
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public String getTitle() {
        return title;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public Integer getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
