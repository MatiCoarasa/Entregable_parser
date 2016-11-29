package com.ejemplo.album.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.ejemplo.album.model.Album;
import com.ejemplo.album.model.AlbumContenedor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import util.HTTPConnectionManager;
import util.ResultListener;

/**
 * Created by dh-mob-tm on 07/11/16.
 */
public class AlbumDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AlbumsDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLENAME_ALBUMS = "Albums";
    private static final String ALBUMID = "AlbumId";
    private static final String ID = "Id";
    private static final String TITLE = "Title";
    private static final String URL = "Url";
    private static final String THUMBNAIL_URL = "ThumbnailUrl";


    public AlbumDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void getAlbumsFromWEB(final ResultListener<List<Album>> resultListener){
        RetrieveAlbumsTask retrieveAlbumsTask = new RetrieveAlbumsTask();
        retrieveAlbumsTask.setListener(resultListener);
        retrieveAlbumsTask.execute();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAlbumsTable = "CREATE TABLE "+TABLENAME_ALBUMS+"("
                + ID + " INTEGER PRIMARY KEY, "
                + ALBUMID + " INTEGER, "
                + TITLE + " TEXT, "
                + URL + " TEXT, "
                + THUMBNAIL_URL + " TEXT" + ")";

        db.execSQL(createAlbumsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private Boolean albumExists(int idAlbum){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLENAME_ALBUMS+ " WHERE "+ ID
                + "=" + idAlbum;

        Cursor result = db.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        result.close();
        db.close();

        return count > 0;
    }

    private void addAlbum(Album album){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues row = new ContentValues();

        row.put(ID, album.getId());
        row.put(ALBUMID, album.getAlbumId());
        row.put(TITLE, album.getTitle());
        row.put(URL, album.getUrl());
        row.put(THUMBNAIL_URL, album.getThumbnailUrl());

        sqLiteDatabase.insert(TABLENAME_ALBUMS, null, row);
        sqLiteDatabase.close();
    }

    private void addAlbumsList(List<Album> albumList){
        for(Album item : albumList){
            if(!albumExists(item.getId())){
                addAlbum(item);
            }
        }
    }

    public List<Album> getAlbumsFromDB(){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM "+TABLENAME_ALBUMS;

        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Album> albumList = new ArrayList<>();

        while(cursor.moveToNext()){
            Album album = new Album();
            album.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            album.setAlbumId(cursor.getInt(cursor.getColumnIndex(ALBUMID)));
            album.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            album.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(THUMBNAIL_URL)));
            album.setUrl(cursor.getString(cursor.getColumnIndex(URL)));

            albumList.add(album);
        }

        cursor.close();
        db.close();

        return albumList;
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
                addAlbumsList(albumContenedor.getAlbums());
                return getAlbumsFromDB();
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
