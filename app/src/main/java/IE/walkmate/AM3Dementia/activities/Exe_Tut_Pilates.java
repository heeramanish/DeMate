package IE.walkmate.AM3Dementia.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoAdapter_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoItem_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoTitleAdapter_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.YouTubeVideos_Pilates;

public class Exe_Tut_Pilates extends AppCompatActivity {

    RecyclerView recyclerView;
    private ActionBar actionBar;

    ArrayList<YouTubeVideos_Pilates> youtubeVideos = new ArrayList<YouTubeVideos_Pilates>();
    final List<VideoItem_Pilates> mListTitlePilates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe_pilates);


        recyclerView = (RecyclerView) findViewById(R.id._exe_pilates_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        //youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/9vljJ0u-avg\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/t4X7vWF4UQY\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/DWi9c9neJ3U\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dQyhMgk9Hx8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/sB4lXUhRfMU\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ugQee2os3J0\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/GYe1WeAEbZY\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/jwlNOUnGqYA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/rxT-NU6Upro\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/s8ednSS0FWE\" frameborder=\"0\" allowfullscreen></iframe>") );
       // youtubeVideos.add( new YouTubeVideos_Pilates("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PLseEst8sYZkSVKyHkC9rjkPnSsKThxfbu\" frameborder=\"0\" allowfullscreen></iframe>") );
        VideoTitleAdapter_Pilates videoTitleAdapter = new VideoTitleAdapter_Pilates(mListTitlePilates);
        VideoAdapter_Pilates videoAdapter = new VideoAdapter_Pilates(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

    }
}
