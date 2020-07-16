package IE.walkmate.AM3Dementia.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import IE.walkmate.AM3Dementia.R;

public class RecordAudioActivity extends AppCompatActivity {
    private ImageButton btnPlay, btnSave, btnStartRecord, btnStopRecord;
    private TextView tvStatus;
    private String pathSave = "";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean permissionToRecordAccepted = false;
    final int REQUEST_PERMISSION_CODE = 1000;
    private String folderName;
    private boolean isRecorded = false;
    private Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);

        init();
        setSupportActionBar(myToolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Record Sound");

        initObjects();
        CreareFolder(folderName);
        btnPlay.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.INVISIBLE);
        initListeners();


    }

    public void initObjects() {
        String audioName = getIntent().getStringExtra("audio");
        folderName = "Audio" + getResources().getString(R.string.app_name);
        Calendar now = Calendar.getInstance();

        if (audioName != null && audioName.length() > 0) {
            pathSave = audioName;
        } else pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + folderName +
                "/" + UUID.randomUUID().toString() + "_audio_record.3gp";
        if (!checkPermisstionfromDevice()) {
            requestPermisstion();
        }

    }

    public void initListeners() {
        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermisstionfromDevice()) {
                    setUpMediaRecorder();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        btnPlay.setEnabled(false);
                        btnStartRecord.setVisibility(View.GONE);
                        btnStopRecord.setVisibility(View.VISIBLE);
                        isRecorded = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tvStatus.setText("Recording Started");

                } else {
                    requestPermisstion();
                }
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                btnStopRecord.setVisibility(View.GONE);
                btnStartRecord.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.VISIBLE);
                btnPlay.setEnabled(true);
                btnSave.setVisibility(View.VISIBLE);
                isRecorded = false;
                tvStatus.setText("Recording Stoped");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }


                Intent mIntent = getIntent();
                mIntent.putExtra("audio", pathSave);
                setResult(10, mIntent);
                finish();

            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartRecord.setEnabled(false);
                btnPlay.setEnabled(false);

                mediaPlayer = new MediaPlayer();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        tvStatus.setText("Finished");
                        btnStartRecord.setEnabled(true);
                        btnPlay.setEnabled(true);
                    }
                });
                try {
                    mediaPlayer.setDataSource(pathSave);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    tvStatus.setText("Playing...");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void CreareFolder(String folderName) {
        File folderObj = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), folderName);
        if (!folderObj.exists()) folderObj.mkdirs();
    }

    private void setUpMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void init() {
        btnStartRecord = findViewById(R.id.btnRecordIcon);
        btnStopRecord = findViewById(R.id.btnStopRecordIcon);
        btnPlay = findViewById(R.id.btnPlayIcon);
        btnSave = findViewById(R.id.btnSaveIcon);
        tvStatus = findViewById(R.id.tvRecordStatus);
        myToolbar = findViewById(R.id.myToolbar);
    }

    private void requestPermisstion() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    permissionToRecordAccepted = true;
                } else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                    permissionToRecordAccepted = false;
                }
            }
            break;
        }
        if (!permissionToRecordAccepted) finish();
    }

    private Boolean checkPermisstionfromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //home back instead of Parent In Manifet
        if(isRecorded){
            mediaRecorder.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
