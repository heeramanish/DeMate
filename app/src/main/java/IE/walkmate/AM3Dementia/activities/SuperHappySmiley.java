package IE.walkmate.AM3Dementia.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

//import com.example.android.moodtracker.R;
//import com.example.android.moodtracker.activitiesrest.MoodHistory;
//import com.example.android.moodtracker.alertdialog.AlertDialogCreator;
//import com.example.android.moodtracker.database.DatabaseHelper;

import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.DatabaseMoodHelper;
import IE.walkmate.AM3Dementia.activitiesrest.MoodHistory;
import IE.walkmate.AM3Dementia.alertdialog.AlertDialogCreator;

/**
 * Created by Diego Fajardo on 13/01/2018.
 */

public class SuperHappySmiley extends AppCompatActivity implements GestureDetector.OnGestureListener{

    /** To see comments about variables and methods, see MainActivity */

    GestureDetectorCompat mGestureDetector;

    ImageButton image_button_super_happy_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;

    DatabaseMoodHelper dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiley_super_happy);
        findViewById(R.id.home_button4).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuperHappySmiley.this, DashboardActivity.class));
            }
        }));

        dbH = new DatabaseMoodHelper(this);

        this.mGestureDetector = new GestureDetectorCompat(this, this);

        image_button_super_happy_face = findViewById(R.id.super_happy_face_button);
        image_button_super_happy_face.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                int cx = (image_button_super_happy_face.getLeft() + image_button_super_happy_face.getRight()) / 2;
                int cy = (image_button_super_happy_face.getTop() + image_button_super_happy_face.getBottom()) / 2;

                int dx = Math.max(cx, image_button_super_happy_face.getWidth() - cx);
                int dy = Math.max(cy, image_button_super_happy_face.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                Animator animator =
                        ViewAnimationUtils.createCircularReveal(image_button_super_happy_face, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

                Toast.makeText(SuperHappySmiley.this, getString(R.string.day_set_superhappy), Toast.LENGTH_LONG).show();

                dbH.updateDataDaysStateInToday(5);

            }
        });

        image_button_history = findViewById(R.id.mood_history_button_super_happy);
        image_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SuperHappySmiley.this, getString(R.string.mood_history_toast), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SuperHappySmiley.this, MoodHistory.class);
                startActivity(intent);
            }
        });

        image_button_add_note = findViewById(R.id.custom_note_button_super_happy);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogCreator alertDialog = new AlertDialogCreator();
                alertDialog.createAlertDialog(SuperHappySmiley.this, dbH);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperHappySmiley.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() < -200) //to differentiate from a tap
        {
            Intent intent = new Intent(SuperHappySmiley.this, MainMoodActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}

