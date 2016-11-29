package com.ejemplo.album.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.model.Album;

import java.util.List;

import util.ResultListener;

/**
 * Created by dh-mob-tm on 07/11/16.
 */
public class AlbumController {

    private Context context;

    public AlbumController(Context context){
        this.context = context;
    }

    public Boolean hayInternet(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = cm.getActiveNetworkInfo();
        return activeConnection != null && activeConnection.isConnected();
    }

    public void getAlbumsWEB(final ResultListener<List<Album>> listener){
        AlbumDAO albumDAO = new AlbumDAO(context);
        albumDAO.getAlbumsFromWEB(new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public List<Album> getAlbumsDB(){
        AlbumDAO albumDAO = new AlbumDAO(context);
        return albumDAO.getAlbumsFromDB();
    }
}
