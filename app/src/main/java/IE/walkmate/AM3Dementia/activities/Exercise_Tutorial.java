package IE.walkmate.AM3Dementia.activities;
/*
Author: Team B40
Version: 02
Exercise Tutorial class is responsible for embedded youtube video playing functionality for the application.
 */
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import IE.walkmate.AM3Dementia.Adapter.VideoAdapter;
import IE.walkmate.AM3Dementia.Adapter.VideoTitleAdapter;
import IE.walkmate.AM3Dementia.Model.VideoItem;
import IE.walkmate.AM3Dementia.Model.YouTubeVideos;
import IE.walkmate.AM3Dementia.R;

//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * * Author: Team B40
 * Version: 01
 * Exercise Tutorial class is responsible for embedded youtube video playing functionality for the application.
 */
public class Exercise_Tutorial extends AppCompatActivity {

    RecyclerView recyclerView;
    private ActionBar actionBar;

  //  Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    ArrayList<YouTubeVideos> youtubeVideos = new ArrayList<YouTubeVideos>();
    final List<VideoItem> mListTitle = new ArrayList<>();


   // ArrayList<VideoItem> videoItems = new ArrayList<VideoItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tutorial);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));



      //  youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/S710i3tWD1E\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/d8uDl0_T5QI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/oQkz_Jd3Jyo\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/oG2gd8QOMFI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QpNmkpbxryM\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/N2wR1OWhD4s\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Inuy8eQIgCk\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/r7pz4lpCi4E\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/kFhG-ZzLNN4\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ujoD1l4fnP4\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vGa5C1Qs8jA\" frameborder=\"0\" allowfullscreen></iframe>") );
//  youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vGa5C1Qs8jA\" frameborder=\"0\" allowfullscreen></iframe>") );

       


        VideoTitleAdapter videoTitleAdapter = new VideoTitleAdapter(mListTitle);
        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

    }
}



