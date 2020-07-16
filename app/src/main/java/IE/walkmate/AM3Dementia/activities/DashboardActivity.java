package IE.walkmate.AM3Dementia.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.Key;
import IE.walkmate.AM3Dementia.Model.User;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableUser;

/**
 * Dashboard Activity class is responsible for the backend dashboard logic
 * @showpopUp is pop up window logic
 * @oncreate is called when the application is started
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = DashboardActivity.this;
    Dialog myDialog;

    private CardView cvAccount, cvDoneTask, cvAddTask, cvActiveTask, cvLogout, cvInformation,cvHospTask,cvExercise,cvMood;

    private TextView tvEmail, tvTermConditions;

    private CircleImageView imgAvatar;
    private TableUser tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        intObjects();
        initListeners();
        myDialog=new Dialog(this);


    }


    /**
     * @param v popup for information, basically gives idea about the application
     *          custom_pop_up is the layout for it
     */
    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.custom_pop_up);
        // myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        // btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    private void init() {
        cvAddTask = findViewById(R.id.cardview_addtask); //View for adding exercsies
        //cvAccount = findViewById(R.id.cardview_account);
        cvActiveTask = findViewById(R.id.cardview_acctivetask);
        cvDoneTask = findViewById(R.id.cardview_taskdone);
        //cvLogout = findViewById(R.id.cardview_logout);
        cvHospTask = findViewById(R.id.cardview_hospitals); //View for the hospital tab
        cvExercise = findViewById(R.id.cardview_tut); //view for Exercise_tuorial tab
        tvEmail = findViewById(R.id.tvEmail);
        imgAvatar = findViewById(R.id.imgAvatar);
        cvMood =findViewById(R.id.cardview_moodview);
       //cvInformation = findViewById(R.id.cardview_information);
        tvTermConditions = findViewById(R.id.tvtermandcondition);

    }

    private void intObjects() {
        if (Helpers.user_email == null) {
            Helpers.user_email = getIntent().getStringExtra("user_email");
        }
        tvEmail.setText(Helpers.user_email);

        tableUser = new TableUser(activity);
        User user = tableUser.getuser(Helpers.user_email);
        imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(user.getImageName(), 0, user.getImageName().length));

    }

    private void initListeners() {
        cvAddTask.setOnClickListener(this);
        //cvAccount.setOnClickListener(this);
        cvDoneTask.setOnClickListener(this);
        cvActiveTask.setOnClickListener(this);
        cvHospTask.setOnClickListener(this);
        cvExercise.setOnClickListener(this);
        cvMood.setOnClickListener(this);
        //cvLogout.setOnClickListener(this);
        //cvInformation.setOnClickListener(this);
        tvTermConditions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_addtask:
                Intent CategoryIntent = new Intent(DashboardActivity.this, CategoryActivity.class);
                startActivity(CategoryIntent);
                break;
           /* case R.id.cardview_logout:
                Helpers.createADialogBacktoLogin(activity, "Log out", "Are you Sure?");
                break; */
//            case R.id.cardview_account:
//                Intent accountIntent = new Intent(activity, RegisterActivity.class);
//                accountIntent.putExtra("user_email", Helpers.user_email);
//                startActivity(accountIntent);
//                break;
            case R.id.cardview_acctivetask:
                Intent displayactivetaskItent = new Intent(activity, DisplayTaskActivity.class);
                startActivity(displayactivetaskItent);
                break;
            case R.id.cardview_moodview:
                Intent  MoodTask = new Intent(activity, MainMoodActivity.class);
                startActivity(MoodTask);
                break;
            case R.id.cardview_taskdone:
                Intent ArchiveTask = new Intent(activity, DataActivity.class);
                startActivity(ArchiveTask);
                break;
            case R.id.cardview_hospitals:
                Intent DementiaCentreActivity = new Intent(activity, DementiaCentreActivity.class);
                startActivity(DementiaCentreActivity);
                break;
//            case R.id.cardview_information:
//                Intent informationIntent = new Intent(activity, Condition_Activity.class);
//                informationIntent.setAction(Key.KEY_INFORMATION);
//                startActivity(informationIntent);
//                break;

            case R.id.cardview_tut:
                Intent exe_tut = new Intent(activity,Exercise_cat.class);
                startActivity(exe_tut);
                break;
            case R.id.tvtermandcondition:
                Intent conditionItent = new Intent(activity, Condition_Activity.class);
                conditionItent.setAction(Key.KEY_TERM_CONDITION);
                startActivity(conditionItent);
                break;

        }
    }


    //write func to deal with this
    @Override
    public void onBackPressed() {

        Helpers.createADialogBacktoLogin(activity, "Log out", "Are you Sure?");
    }


}
