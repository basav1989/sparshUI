package com.rayappa.basavaraj.sparshnirvana;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CuratedMusicContent extends AppCompatActivity {


    private class queryResult {
        public String mediaId;
        public String artistName;
        public String trackName;
        public String artPath;
        queryResult(Cursor result) {
            mediaId = result.getString(0);
            artistName = result.getString(1);
            trackName = result.getString(2);
            artPath = result.getString(3);
        }
    }

    ArrayList<queryResult> resultSet = new ArrayList<>();

    private class ImageAdapter extends ArrayAdapter<queryResult> {
        public ImageAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ImageAdapter(Context context, int resource, List<queryResult> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            View v = view;
            if (v == null ) {
                LayoutInflater li = LayoutInflater.from(getContext());
                view = li.inflate(R.layout.griditem, null);
            }

            queryResult entry = resultSet.get(position);//[position];

            ImageView imgView =  (ImageView) findViewById(R.id.gridItem);
            //imgView.setImageResource(R.drawable.abstract_music_rock_bw);

            return view;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curated_music_content);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Log.v("curatedMusic", "oncreate");
        GridView contentView  = (GridView) findViewById(R.id.contentView);
        retrieveMetadata();
        contentView.setAdapter(new ImageAdapter(this, R.layout.griditem, resultSet));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void retrieveMetadata() {
        // acquire  contentResolver
        ContentResolver cr = getApplicationContext().getContentResolver();

        // prepare query metadata
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TRACK, MediaStore.Images.Media.DATA};
        Cursor iterator = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);

        if (iterator != null) {
            iterator.moveToFirst();
            while (iterator.moveToNext()) {
                Log.v("grid", iterator.getString(3));
                resultSet.add(new queryResult(iterator));
            }
        }
    }

}
