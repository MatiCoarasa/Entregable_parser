package com.ejemplo.album.dao;

import android.os.AsyncTask;

import com.ejemplo.album.model.Album;
import com.ejemplo.album.model.AlbumContenedor;
import com.google.gson.Gson;

import java.util.List;

import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by dh-mob-tm on 07/11/16.
 */
public class AlbumDAO {


    public void getAlbumsFromWEB(final ResultListener<List<Album>> resultListener){
        RetrieveAlbumsTask retrieveAlbumsTask = new RetrieveAlbumsTask();
        retrieveAlbumsTask.setListener(resultListener);
        retrieveAlbumsTask.execute();
    }


    private class RetrieveAlbumsTask extends AsyncTask<String, Void, List<Album>>{

        private ResultListener<List<Album>> listener;

        public void setListener(ResultListener<List<Album>> listener) {
            this.listener = listener;
        }

        @Override
        protected List<Album> doInBackground(String... params) {
            try{
                HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
                String stringJSON = httpConnectionManager.getRequestString("https://api.myjson.com/bins/25hip");
                Gson gson = new Gson();
                AlbumContenedor albumContenedor = gson.fromJson(stringJSON, AlbumContenedor.class);
                return albumContenedor.getAlbums();

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Album> alba) {
            super.onPostExecute(alba);
            listener.finish(alba);
        }
    }
}
