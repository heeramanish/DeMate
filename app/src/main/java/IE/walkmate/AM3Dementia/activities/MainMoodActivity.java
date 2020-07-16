package IE.walkmate.AM3Dementia.activities;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
//


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.DatabaseContract;
import IE.walkmate.AM3Dementia.Sql.DatabaseHelper;
import IE.walkmate.AM3Dementia.Sql.DatabaseMoodHelper;
import IE.walkmate.AM3Dementia.Sql.DatabaseValues;
import IE.walkmate.AM3Dementia.activitiesrest.MoodHistory;
import IE.walkmate.AM3Dementia.activitiesrest.PieChartActivity;
import IE.walkmate.AM3Dementia.alertdialog.AlertDialogCreator;
import IE.walkmate.AM3Dementia.broadcastreceiver.BroadcastDataUpdate;


/* The main class for the Mood selection
* */
public class MainMoodActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    //Variable for gesture detector
    GestureDetectorCompat mGestureDetector;

    //Variables for buttons
    ImageButton image_button_happy_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;

    private TextView txtSpeechInput;
    ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    //Variable for database helper. Used for creating the database the first time
    DatabaseMoodHelper dbH;
//    AnimationDrawable right_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_main);
//         ImageView right_arr= (ImageView)findViewById(R.id.arrowanimation);
//         right_arr.setBackgroundResource(R.drawable.animation_arrow);
//         right_arrow =(AnimationDrawable) right_arr.getBackground();

        findViewById(R.id.home_button).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMoodActivity.this, DashboardActivity.class));
            }
        }));

        //Database Helper
        dbH = new DatabaseMoodHelper(this);

        //Gesture detector
        this.mGestureDetector = new GestureDetectorCompat(this, this);

        /**
         * This code is used for creating the two tables of the database.
         * It will only run the first time the app is launched
         */
        if (dbH.isTableEmpty(DatabaseContract.Database.DAYS_TABLE_NAME)) {
            for (int i = 0; i < DatabaseValues.days.length; i++){
                dbH.insertFirstDataDays(DatabaseValues.days[i],6,"");
            }

            /** We create the alarm for updating the mood history*/
            createAlarm();

            Toast.makeText(MainMoodActivity.this,
                    getResources().getString(R.string.toast_swipe_to_change),
                    Toast.LENGTH_LONG)
                    .show();
        }

        if (dbH.isTableEmpty(DatabaseContract.Database.STATES_TABLE_NAME)) {
            for (int i = 0; i < DatabaseValues.states.length; i++) {
                dbH.insertFirstDataStates(DatabaseValues.states[i]);
            }
        }

        /** Button Listeners
         *  */
        image_button_happy_face = (ImageButton) findViewById(R.id.happy_face_button);
        image_button_happy_face.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //Used for animation (Animator animator)

            @Override
            public void onClick(View v) {

                /** This code is used for generating a fade effect when the image button
                 * is clicked
                 * */
                // get the center for the clipping circle
                int cx = (image_button_happy_face.getLeft() + image_button_happy_face.getRight()) / 2;
                int cy = (image_button_happy_face.getTop() + image_button_happy_face.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, image_button_happy_face.getWidth() - cx);
                int dy = Math.max(cy, image_button_happy_face.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                // Android native animator
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(image_button_happy_face, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

                //Used for feedback to the user
                Toast.makeText(MainMoodActivity.this, getString(R.string.day_set_happy), Toast.LENGTH_LONG).show();

                //UPDATES the state of the day
                dbH.updateDataDaysStateInToday(4);

            }
        });

        /** Tapping the mood history button will take the used to that activity
         * */
        image_button_history = findViewById(R.id.mood_history_button_main);
        image_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMoodActivity.this, getString(R.string.mood_history_toast), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainMoodActivity.this, MoodHistory.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

            }
        });

        /** Tapping the add_note button will create a dialog for the user to
         * introduce a comment
         * */
        image_button_add_note = findViewById(R.id.custom_note_button_main);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCreator alertDialog = new AlertDialogCreator();
                alertDialog.createAlertDialog(MainMoodActivity.this, dbH);
            }
        });




    }


    /** When back button is pressed, there is a fade effect while returning to the
     * previous activity
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainMoodActivity.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    /** Allows to manage touch events
     *  */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }



    /** Depending on the type of swipe
     * the user will go to one activity or another */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 200) //to differentiate from a tap
        {
            Intent intent = new Intent(MainMoodActivity.this, SuperHappySmiley.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }
        if (e1.getX() - e2.getX() < -200) //to differentiate from a tap
        {
            Intent intent = new Intent(MainMoodActivity.this, NormalSmiley.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
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

    /** This method creates an alarm used to update
     * the information every day at midnight. It calls
     * a broadcast receiver*/
    private void createAlarm () {

        //CREATION OF A CALENDAR to get time in millis and pass it to the AlarmManager to set
        //the time when the alarm has to start working (same day the app runs for the first time
        //at midnight).
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        //DECLARATION OF the AlarmManager and
        // the Intent and PendingIntent necessary for the AlarmManager.setRepeating method.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastDataUpdate.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //timeInMillis: specifies when we have to start the alarm (calendar gives this information).
        //INTERVAL_DAY: makes the alarm be repeated every day.
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

}