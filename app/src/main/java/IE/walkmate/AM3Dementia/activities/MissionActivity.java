package IE.walkmate.AM3Dementia.activities;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.io.IOException;

import IE.walkmate.AM3Dementia.AlarmService.AlarmReceiver;
import IE.walkmate.AM3Dementia.AlarmService.SetAlarmSnoozeReceiver;
import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.Key;
import IE.walkmate.AM3Dementia.Model.ModelTask;
import IE.walkmate.AM3Dementia.Model.Task;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableTask;
import IE.walkmate.AM3Dementia.Sql.TableTaskDefault;

public class MissionActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MissionActivity.this;
    private TextView tvTaskName, tvTaskNote, tvTaskTime, lblAudio, tvTaskDate, lblDate;
    private Button btnPlay;
    private int mReceiverID;
    private TableTask tableTask;
    private TableTaskDefault tableTaskDefault;
    private String pathSave;
    Task reminder;
    private MediaPlayer mediaPlayer;
    private ImageView imaRepeatType;
    private TextDrawable mDrawableBuilder;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        init();
        intObjects();
        hideNotification();

        //delete if exist
        alarmReceiver.cancelAlarm(activity, -mReceiverID);

        Intent mIntent = new Intent(MissionActivity.this, SetAlarmSnoozeReceiver.class);
        mIntent.setAction("Dismiss");
        sendBroadcast(mIntent);

    }


    private void init() {
        tvTaskName = findViewById(R.id.tvTaskName);
        tvTaskNote = findViewById(R.id.tvTaskNote);
        btnPlay = findViewById(R.id.btnaudio);
        imaRepeatType = findViewById(R.id.imgRepeatType);
        lblAudio = findViewById(R.id.lblAudio);
        tvTaskDate = findViewById(R.id.tvTaskDate);
        tvTaskTime = findViewById(R.id.tvTaskTime);
        lblDate = findViewById(R.id.lblDate);
        btnPlay.setOnClickListener(this);
    }

    private void intObjects() {
        alarmReceiver=new AlarmReceiver();
        mReceiverID = Integer.parseInt(getIntent().getStringExtra(Key.EXTRA_REMINDER_ID));
        tableTask = new TableTask(activity);
        tableTaskDefault = new TableTaskDefault(activity);
        reminder = tableTask.getTaskById(mReceiverID);
        ModelTask modelTask = tableTaskDefault.getMTaskById(reminder.getTaskID());
        String mTitle = modelTask.getTask_name();
        tvTaskName.setText(mTitle);
        if(reminder.getNote().length()>0){
            tvTaskNote.setText(reminder.getNote());
        }
        else {
            tvTaskNote.setText("N/A");
        }

        pathSave = reminder.getSoundName();
        if (pathSave != null && pathSave.length() > 0 && !pathSave.equals("N/A")) {
            btnPlay.setVisibility(View.VISIBLE);
            lblAudio.setVisibility(View.VISIBLE);
        } else {
            btnPlay.setVisibility(View.GONE);
            lblAudio.setVisibility(View.GONE);
        }

        //set up Repeat Type letter
        String letter = "A";
        String mRepeatType = reminder.getRepeatType();
        if (!TextUtils.isEmpty(mRepeatType)) {
            letter = mRepeatType.substring(0, 1);
        }
        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        imaRepeatType.setImageDrawable(mDrawableBuilder);

        tvTaskTime.setText(reminder.getTime());
        if (reminder.getRepeatType().equals("Monthly")) {
            tvTaskDate.setText(reminder.getDate());
        } else {
            tvTaskDate.setVisibility(View.GONE);
            lblDate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        btnPlay.setEnabled(false);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Helpers.showToast(activity, "Finished!");
                btnPlay.setEnabled(true);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });
        try {
            mediaPlayer.setDataSource(pathSave);
            mediaPlayer.prepare();
            mediaPlayer.start();

            Helpers.showToast(activity, "Playing!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hideNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(mReceiverID);
    }
}
