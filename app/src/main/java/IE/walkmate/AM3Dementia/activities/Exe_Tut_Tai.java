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
import IE.walkmate.AM3Dementia.activities.tai_chi_tut.VideoAdapter_Tai;
import IE.walkmate.AM3Dementia.activities.tai_chi_tut.VideoItem_Tai;
import IE.walkmate.AM3Dementia.activities.tai_chi_tut.VideoTitleAdapter_Tai;
import IE.walkmate.AM3Dementia.activities.tai_chi_tut.YouTubeVideos_Tai;

public class Exe_Tut_Tai extends AppCompatActivity {

    RecyclerView recyclerView;
    private ActionBar actionBar;

    ArrayList<YouTubeVideos_Tai> youtubeVideos = new ArrayList<YouTubeVideos_Tai>();
    final List<VideoItem_Tai> mListTitleTai = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe_tai_chi);


        recyclerView = (RecyclerView) findViewById(R.id._exe_tai_chi_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

      // wfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/9vljJ0u-avg\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/4mAezOM5BKM\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5jKsg_ocsXQ\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/J9wnSWB9GPM\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Fci03PQTJV8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bN7LzkA8aA4\" frameborder=\"0\" allowfullscreen></iframe>") );
      //  youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/jxpKT6Rr9i8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/wMcxZjlx3y8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos_Tai("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bN7LzkA8aA4\" frameborder=\"0\" allowfullscreen></iframe>") );
        VideoTitleAdapter_Tai videoTitleAdapter = new VideoTitleAdapter_Tai(mListTitleTai);
        VideoAdapter_Tai videoAdapter = new VideoAdapter_Tai(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

    }
}
