package com.ejemplo.album.view;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejemplo.album.R;

import com.ejemplo.album.model.Album;

import java.util.List;

/**
 * Created by dh-mob-tm on 29/11/16.
 */
public class AdapterAlbums extends RecyclerView.Adapter {

    private List<Album> albumList;
    private Context context;

    public AdapterAlbums(List<Album> albumList, Context context){
        this.albumList = albumList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.album_detalle, parent, false);
        return new AlbumsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlbumsViewHolder albumsViewHolder = (AlbumsViewHolder)holder;
        Album album = albumList.get(position);
        albumsViewHolder.cargarAlbum(album);
    }

    private class AlbumsViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;


        public AlbumsViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageViewDetalle);
            textView = (TextView) itemView.findViewById(R.id.textViewDetalleTitle);

        }

        public void cargarAlbum(Album album){
            Glide.with(context).load(album.getThumbnailUrl()).into(imageView);
            textView.setText(album.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
