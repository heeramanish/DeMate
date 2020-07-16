package IE.walkmate.AM3Dementia.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import IE.walkmate.AM3Dementia.R;

/**
 * Author: Team B40
 * Version: 01
 * Launch Activity class is responsible for launching the application.
 * It has Launc_Activity class which extends AppCompactActivity
 */
public class Launch_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        RelativeLayout layout = findViewById(R.id.linearLauncher);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(2500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent loginIntent = new Intent(Launch_Activity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout.startAnimation(animation);

    }
}
