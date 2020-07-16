package IE.walkmate.AM3Dementia.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.VideoView;

import java.io.IOException;

import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.InputValidation;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.DatabaseHelper;
import IE.walkmate.AM3Dementia.Sql.TableUser;
import IE.walkmate.AM3Dementia.custom_control.CustomBackgroundTextInputLayout;

/**
 * * Author: Team B40
 *  * Version: 01
 *  Login class responsible for login functionality of the app
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private CustomBackgroundTextInputLayout textInputLayoutEmail;
    private CustomBackgroundTextInputLayout textInputLayoutPassword;

    private AppCompatButton btnLogin;
    private AppCompatTextView tvregister;
    private AppCompatTextView tvforgot;

    private InputValidation inputValidation;
    private TableUser tableUser;

    //save email and password
    private CheckBox ckboxSavelogin;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;

//    private VideoView videoView;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        insertExistingDatabase();
        init();
        initObject();
        initListeners();
        getUserLoginDetail();
        Helpers.setupUI(findViewById(R.id.nestedScrollView), activity);
//        videoView = (VideoView) findViewById(R.id.videoView);
//        String path= "android.resource://IE.walkmate.AM3Dementia.activities/"+R.raw.loginbackground;
//        Uri uri =Uri.parse(path);
//        videoView.setVideoURI(uri);
//        videoView.start();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//
//            }
//        });
//
    }
//
//    @Override
//    protected void onResume(){
//        videoView.resume();
//        super.onResume();
//    }
//
//    @Override
//    protected  void onPause(){
//        videoView.suspend();
//        super.onPause();
//    }
//
//    @Override
//    protected  void onDestroy(){
//        videoView.stopPlayback();
//        super.onDestroy();
//    }

    public void getUserLoginDetail() {
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            textInputLayoutEmail.getEditText().setText(loginPreferences.getString("username", ""));
            textInputLayoutPassword.getEditText().setText(loginPreferences.getString("password", ""));
            ckboxSavelogin.setChecked(true);
        }
    }

    private void init() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = findViewById(R.id.textinput_email);
        textInputLayoutPassword = findViewById(R.id.textinput_password);
        btnLogin = findViewById(R.id.btnlogin);
        tvregister = findViewById(R.id.tvlinkRegister);
        tvforgot = findViewById(R.id.tvlinkforgotpassword);
        tvforgot.setClickable(true);
        tvregister.setClickable(true);
        ckboxSavelogin = findViewById(R.id.ckboxRemember);

    }

    private void insertExistingDatabase() {
        DatabaseHelper databaseHelper = null;
        try {
            databaseHelper = new DatabaseHelper(activity);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            databaseHelper.openDatabase();
        } catch (SQLException ex) {
            Log.e("MainActivity", "Unable to Open!");
            throw ex;
        }
//        Helpers.showToast(activity, "Success");
    }

    private void initObject() {
        inputValidation = new InputValidation(activity);
        tableUser = new TableUser(activity);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        tvregister.setOnClickListener(this);
        tvforgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                verifyFromSQLLITE();
                break;
            case R.id.tvlinkRegister:
                Intent RegisterIntent = new Intent(activity, RegisterActivity.class);
                startActivity(RegisterIntent);
                emptyInputEmail();
                break;
            case R.id.tvlinkforgotpassword:
                Intent ForgotIntent = new Intent(activity, ForgotPasswordActivity.class);
                startActivity(ForgotIntent);
                break;
        }
    }

    private void verifyFromSQLLITE() {
        if (!inputValidation.isInputEditextField(textInputLayoutEmail, getResources().getString(R.string.texterroremail)) |
                !inputValidation.isInputEditextField(textInputLayoutPassword, getResources().getString(R.string.texterrorpassword))) {
            return;
        }
        if (!inputValidation.isInputeditTextEmail(textInputLayoutEmail, getResources().getString(R.string.texterroremail))) {
            return;
        }

        String email = textInputLayoutEmail.getEditText().getText().toString().trim();
        String password = textInputLayoutPassword.getEditText().getText().toString().trim();

        //if success
        if (tableUser.checkUser(email, password)) {
            //save email and password
            if (ckboxSavelogin.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", email);
                loginPrefsEditor.putString("password", password);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            Intent accountIntent = new Intent(activity, DashboardActivity.class);
            accountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            accountIntent.putExtra("user_email", email);
            emptyInputEmail();
            startActivity(accountIntent);
            finish();
        } else {
            //??
            Snackbar.make(nestedScrollView, getString(R.string.erroralidemailpassword), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEmail() {
        textInputLayoutEmail.getEditText().getText().clear();
        textInputLayoutPassword.getEditText().getText().clear();
    }

}
