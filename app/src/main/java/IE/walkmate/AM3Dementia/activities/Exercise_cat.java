package IE.walkmate.AM3Dementia.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import IE.walkmate.AM3Dementia.R;

public class Exercise_cat extends AppCompatActivity {


    private WebView mWebView;

    @Override
   // @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_cat);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeYoga = new Intent(Exercise_cat.this,Exercise_Tutorial.class);
                startActivity(seeYoga);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seePilate = new Intent(Exercise_cat.this,Exe_Tut_Tai.class);
                startActivity(seePilate);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeTai = new Intent(Exercise_cat.this,Exe_Tut_Chair.class);
                startActivity(seeTai);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeChair = new Intent(Exercise_cat.this,Exe_Tut_Pilates.class);
                startActivity(seeChair);
            }
        });


    }
}
