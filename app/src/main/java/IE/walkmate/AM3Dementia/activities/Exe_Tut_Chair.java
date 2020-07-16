package IE.walkmate.AM3Dementia.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.activities.chair_tut.VideoAdapter_Chair;
import IE.walkmate.AM3Dementia.activities.chair_tut.VideoItem_Chair;
import IE.walkmate.AM3Dementia.activities.chair_tut.VideoTitleAdapter_Chair;
import IE.walkmate.AM3Dementia.activities.chair_tut.YouTubeVideos_Chair;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoAdapter_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoItem_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.VideoTitleAdapter_Pilates;
import IE.walkmate.AM3Dementia.activities.pilates_tut.YouTubeVideos_Pilates;

public class Exe_Tut_Chair extends AppCompatActivity {

    RecyclerView recyclerView;
    private ActionBar actionBar;

    ArrayList<YouTubeVideos_Chair> youtubeVideos = new ArrayList<YouTubeVideos_Chair>();
    final List<VideoItem_Chair> mListTitleChair = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe_chair);


        recyclerView = (RecyclerView) findViewById(R.id._exe_chair_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/9vljJ0u-avg\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Fkl88Nq3BiU\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/-Ts01MC2mIo\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uEihWUbnqdk\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KEjiXtb2hRg\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/GQ9oHip_FdE\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8BcPHWGQO44\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/S710i3tWD1E\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/1YHjBYcqJ5c\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/barPap-8vnw\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Oscnz3KKqcE\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos_Chair("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/k-NBhc2EgX0\" frameborder=\"0\" allowfullscreen></iframe>"));

        VideoTitleAdapter_Chair videoTitleAdapter = new VideoTitleAdapter_Chair(mListTitleChair);
        VideoAdapter_Chair videoAdapter = new VideoAdapter_Chair(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }
}
