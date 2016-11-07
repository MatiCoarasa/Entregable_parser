package com.ejemplo.album.controller;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.model.Album;

import java.util.List;

import util.ResultListener;

/**
 * Created by dh-mob-tm on 07/11/16.
 */
public class AlbumController {

    public void getAlbums(final ResultListener<List<Album>> listener){
        AlbumDAO albumDAO = new AlbumDAO();
        albumDAO.getAlbumsFromWEB(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        });
    }
}
