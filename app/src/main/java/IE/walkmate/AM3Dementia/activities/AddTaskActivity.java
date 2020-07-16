package IE.walkmate.AM3Dementia.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import IE.walkmate.AM3Dementia.Adapter.Custom_spinner_Task;
import IE.walkmate.AM3Dementia.AlarmService.AlarmReceiver;
import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.Key;
import IE.walkmate.AM3Dementia.Model.ModelTask;
import IE.walkmate.AM3Dementia.Model.SubCategory;
import IE.walkmate.AM3Dementia.Model.Task;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableCategory;
import IE.walkmate.AM3Dementia.Sql.TableSubCategory;
import IE.walkmate.AM3Dementia.Sql.TableTask;
import IE.walkmate.AM3Dementia.Sql.TableTaskDefault;
import IE.walkmate.AM3Dementia.Sql.TableUser;
import IE.walkmate.AM3Dementia.custom_control.ToggleButtonGroupTableLayout;

/** Author: Team B40
 *  Version: 01
 * AddtaskActivity class is responsible ofr the reminder functionality..
 * The class also facilitates the selection of exercises after navigating from listview
 */
public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private AppCompatActivity activity = AddTaskActivity.this;
    private Hashtable<String, Integer> CategoryDic;
    public Hashtable<String, Integer> dayofWeek = new Hashtable<>();
    private Toolbar myToolbar;
    private ActionBar actionBar;

    private Spinner spnTask;
    private Custom_spinner_Task myAdapter;
    private ArrayList<ModelTask> modelTasks;

    private int cat_id;

    private NestedScrollView layout_parent;

    private ToggleButtonGroupTableLayout radioGroupCategory;
    private RadioButton rbPersonal, rbHealth, rbSocial, rbHouseKeeping;

    private RadioGroup radioGroupRepeatType;
    private RadioButton rdDaily, rdWeekly, rdMonthly;

    private RadioGroup radioGroupSubTask;

    private LinearLayout linearLayoutWeeklyCheckOne, linearLayoutWeeklyCheckTwo;
    private LinearLayout LinearTime;

    private TableTaskDefault tableTaskDefault;
    private TableSubCategory tableSubCategory;
    private TableCategory tableCategory;

    private int mYear, mMonth, mDay;
    private long mRepeatTime;
    private int mTaskId;
    private String mTaskDate;
    private String mRepeatType;
    private String mActive;
    private String mRepeatId;
    private String soundName;

    private TextView tvDate, lblDate, lbldateofweek;
    private Button btnTime;
    private Button btnRecord;
    private ImageButton btnAddTime, btnDeleteTime;

    private TimePickerDialog dtpTime;

    private Calendar taskDate;
    private DatePickerDialog dtpDate;

    private TableTask tableTask;

    private EditText edtNote;

    private AlarmReceiver alarmReceiver;

    private CompoundButton.OnCheckedChangeListener SubCategoryClickListener;
    private int counter;

    /**
     *  create savedInstanceState , Bundle class
     *       is used to stored the data of activity whenever above condition occur in app
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        init();
        initObjects();
        intListerers();

        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Task");


        myAdapter = new Custom_spinner_Task(AddTaskActivity.this, R.layout.custom_spinner_add_task, modelTasks);
        spnTask.setAdapter(myAdapter);

        loadData();
        rdDaily.setChecked(true);

        Helpers.setupUI(layout_parent, activity);
    }


    private void clearRadioGroup(RadioGroup group) {
        for (int i = group.getChildCount() - 1; i >= 0; --i) {
            group.removeViewAt(i);
        }
    }


    private void loadData() {
        cat_id = getIntent().getIntExtra("taskid", -1);
        if (cat_id != -1) {
            String categoryName = tableCategory.getCategoryNamebyId(cat_id);
            int count = radioGroupCategory.getChildCount();
            for (int i = 0; i < count; ++i) {
                TableRow row = (TableRow) radioGroupCategory.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); ++j) {
                    RadioButton rd = (RadioButton) row.getChildAt(j);
                    if (rd.getText().toString().equalsIgnoreCase(categoryName)) {
                        rd.setChecked(true);
                        radioGroupCategory.setActiveRadioButton(rd);
                        break;
                    }
                }
            }
        }
    }


    /**
     * @init is method here that is used for intilising the objects
     */
    private void init() {
        layout_parent = findViewById(R.id.nestedScrollView);
        spnTask = findViewById(R.id.spinner_task_name);
        myToolbar = findViewById(R.id.myToolbar);
        lblDate = findViewById(R.id.lblDate);
        lbldateofweek = findViewById(R.id.lblDayOfWeek);


        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        rbPersonal = findViewById(R.id.radioPersonal);
        rbHealth = findViewById(R.id.radioHealth);
        rbSocial = findViewById(R.id.radioSocialLersure);
        rbHouseKeeping = findViewById(R.id.radioHouseKeeping);
        tvDate = findViewById(R.id.tvTaskDate);
        btnTime = findViewById(R.id.tvTaskTime);

        radioGroupRepeatType = findViewById(R.id.radioGroupRepeatType);
        rdDaily = findViewById(R.id.radioDaily);
        rdWeekly = findViewById(R.id.radioWeekly);
        rdMonthly = findViewById(R.id.radioMonthly);

        linearLayoutWeeklyCheckOne = findViewById(R.id.linearDayOfWeek1);
        linearLayoutWeeklyCheckTwo = findViewById(R.id.linearDayOfWeek2);

        linearLayoutWeeklyCheckOne.setVisibility(View.GONE);
        linearLayoutWeeklyCheckTwo.setVisibility(View.GONE);

        tableTask = new TableTask(activity);
        edtNote = findViewById(R.id.edtnotes);
        btnRecord = findViewById(R.id.btnaudio);

        radioGroupSubTask = findViewById(R.id.radioGroupSubCategory);
        LinearTime = findViewById(R.id.LinearTime);
        btnAddTime = findViewById(R.id.btnAddTime);
        btnDeleteTime = findViewById(R.id.btnDeleteTime);
    }

    private void initObjects() {
        counter = 0;
        modelTasks = new ArrayList<ModelTask>();
        tableTaskDefault = new TableTaskDefault(activity);
        tableSubCategory = new TableSubCategory(activity);
        tableCategory = new TableCategory(activity);
        createDatePickerDialog();
        //createTimePickerDialog();
        mActive = "true";
        mRepeatId = "";

        dayofWeek.put("Mon", 2);
        dayofWeek.put("Tue", 3);
        dayofWeek.put("Wed", 4);
        dayofWeek.put("Thu", 5);
        dayofWeek.put("Fri", 6);
        dayofWeek.put("Sat", 7);
        dayofWeek.put("Sun", 1);

        soundName = null;

        CategoryDic = new Hashtable<>();
        CategoryDic.put("Yoga", 1);
        CategoryDic.put("Tai-Chi", 2);
        CategoryDic.put("Chair Based Exercises", 3);
        CategoryDic.put("Brain Exercise", 4);

        mRepeatType = "Daily";

        linearDailyTimes = new Hashtable<>();

    }

    /**
     * @onclicklistener acts as senser when click action is performed.
     * Here, onlicklistener is triggering the change in state (on/off) of buttons in category section
     */
    private void intListerers() {
        btnTime.setOnClickListener(this);

        CompoundButton.OnCheckedChangeListener radioListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearRadioGroup(radioGroupSubTask);
                    ArrayList<SubCategory> subcategoryDB = tableSubCategory.listSubCategoryByCatId(CategoryDic.get(buttonView.getText().toString()));
                    for (SubCategory sub : subcategoryDB) {
                        RadioButton rd1 = Helpers.getRadioButton(sub.getSubCategoryName(), sub.getSubCategoryId(), sub.getSubCategoryIcon(), activity, SubCategoryClickListener);
                        radioGroupSubTask.addView(rd1);
                    }
                    RadioButton firstrd = (RadioButton) radioGroupSubTask.getChildAt(0);
                    firstrd.setChecked(true);
                }

            }
        };
        rbPersonal.setOnCheckedChangeListener(radioListener);
        rbSocial.setOnCheckedChangeListener(radioListener);
        rbHouseKeeping.setOnCheckedChangeListener(radioListener);
        rbHealth.setOnCheckedChangeListener(radioListener);


        radioGroupRepeatType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int count = group.getChildCount();
                for (int i = 0; i < count; i++) {
                    View o = group.getChildAt(i);

                    if (o instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) o;

                        if (radioButton.isChecked()) {
                            if (radioButton.getText().equals("Weekly")) {
                                removeTimeButtons();
                                clearLinearTime();
                                btnTime.setVisibility(View.GONE);
                                mRepeatType = "Weekly";
                                linearLayoutWeeklyCheckOne.setVisibility(View.VISIBLE);
                                linearLayoutWeeklyCheckTwo.setVisibility(View.VISIBLE);
                                lbldateofweek.setVisibility(View.VISIBLE);
                                tvDate.setVisibility(View.GONE);
                                lblDate.setVisibility(View.GONE);
                            } else if (radioButton.getText().equals("Monthly")) {
                                Helpers.resetCheckBoxGroup(linearLayoutWeeklyCheckOne);
                                Helpers.resetCheckBoxGroup(linearLayoutWeeklyCheckTwo);
                                removeTimeButtons();
                                clearLinearTime();
                                btnTime.setVisibility(View.VISIBLE);
                                mRepeatType = "Monthly";
                                linearLayoutWeeklyCheckOne.setVisibility(View.GONE);
                                linearLayoutWeeklyCheckTwo.setVisibility(View.GONE);
                                lbldateofweek.setVisibility(View.GONE);
                                tvDate.setVisibility(View.VISIBLE);
                                lblDate.setVisibility(View.VISIBLE);
                            } else {
                                Helpers.resetCheckBoxGroup(linearLayoutWeeklyCheckOne);
                                Helpers.resetCheckBoxGroup(linearLayoutWeeklyCheckTwo);
                                displayTimeButtons();
                                clearLinearTime();
                                btnTime.setVisibility(View.VISIBLE);
                                mRepeatType = "Daily";
                                linearLayoutWeeklyCheckOne.setVisibility(View.GONE);
                                linearLayoutWeeklyCheckTwo.setVisibility(View.GONE);
                                lbldateofweek.setVisibility(View.GONE);
                                tvDate.setVisibility(View.GONE);
                                lblDate.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });

        SubCategoryClickListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.colorPrimary));
                    spnTask.setSelection(0);
                    resetTaskSpinner((Integer) buttonView.getTag());
                } else {
                    buttonView.setBackgroundTintList(null);
                }

            }
        };

        setOnClickForCheckBoxGroup(linearLayoutWeeklyCheckOne);
        setOnClickForCheckBoxGroup(linearLayoutWeeklyCheckTwo);

    }

    private void setOnClickForCheckBoxGroup(LinearLayout group) {
        for (int i = 0; i < group.getChildCount(); ++i) {
            CheckBox ck = (CheckBox) group.getChildAt(i);
            ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String buttonName = buttonView.getText().toString();
                    if (isChecked) {
                        LinearTime.addView(Helpers.createTextViewLabelWeekDay(buttonName, dayofWeek.get(buttonName), activity));
                        Button newButton = Helpers.createTextButton(dayofWeek.get(buttonName), activity, AddTaskActivity.this);
                        LinearTime.addView(newButton);
                        newButton.performClick();
                    } else {
                        removeButtonByTag(dayofWeek.get(buttonName));
                    }
                }
            });
        }
    }


    private void resetTaskSpinner(int id) {
        modelTasks.clear();
        modelTasks.addAll(tableTaskDefault.listTasksByCatId(id));
        myAdapter.notifyDataSetChanged();
    }

    private void displayTimeButtons() {
        btnAddTime.setVisibility(View.VISIBLE);
        btnDeleteTime.setVisibility(View.VISIBLE);
    }

    private void removeTimeButtons() {
        btnAddTime.setVisibility(View.GONE);
        btnDeleteTime.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        mDay = dayOfMonth;
        mMonth = month;
        mYear = year;
        mTaskDate = dayOfMonth + "/" + month + "/" + year;
        tvDate.setText(mTaskDate);
    }


    private void createDatePickerDialog() {
        taskDate = Calendar.getInstance();
        mYear = taskDate.get(Calendar.YEAR);
        mMonth = taskDate.get(Calendar.MONTH) + 1;
        mDay = taskDate.get(Calendar.DATE);
        mTaskDate = mDay + "/" + mMonth + "/" + mYear;
        tvDate.setText(mTaskDate);

        dtpDate = new DatePickerDialog(activity, this, taskDate.get(Calendar.YEAR), taskDate.get(Calendar.MONTH), taskDate.get(Calendar.DAY_OF_MONTH));
    }


    /**
     * @setdata On clicking Date picker
     */
    public void setDate(View v) {
        dtpDate.show();
    }


    private void gotoDashBoard() {
        Intent DashBoardIntent = new Intent(this, DashboardActivity.class);
        DashBoardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(DashBoardIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void SaveTask(View view) {
        postTaskToDatabase();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void recordAudio(View v) {
        Intent recordAudio = new Intent(activity, RecordAudioActivity.class);
        recordAudio.putExtra("audio", soundName);
        startActivityForResult(recordAudio, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 10) {
                soundName = data.getStringExtra("audio");
                btnRecord.setText("Recorded");

            }
        }
    }

    //On Time Click
    @Override
    public void onClick(View v) {

        final Button btn = (Button) v;
        Calendar now = Calendar.getInstance();
        dtpTime = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int Hour = hourOfDay;
                int Minute = minute;
                String settingTime = "";
                if (minute < 10) {
                    settingTime = Hour + ":" + "0" + Minute;
                } else {
                    settingTime = Hour + ":" + Minute;
                }
                btn.setText(settingTime);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        dtpTime.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (btn.getText().toString().equals("-1")) {
                    int tag = (int) btn.getTag();
                    if (mRepeatType.equals("Weekly")) {
                        setUnCheckedCheckBoxByTag(linearLayoutWeeklyCheckOne, tag);
                        setUnCheckedCheckBoxByTag(linearLayoutWeeklyCheckTwo, tag);
                    } else if (mRepeatType.equals("Daily")) {
                        LinearTime.removeView(linearDailyTimes.get(tag));
                        linearDailyTimes.remove(tag);
                    }
                }
            }
        });
        dtpTime.show();
    }

    public void setUnCheckedCheckBoxByTag(LinearLayout group, int tag) {
        for (int i = 0; i < group.getChildCount(); ++i) {
            CheckBox ck = (CheckBox) group.getChildAt(i);
            int value = dayofWeek.get(ck.getText().toString());
            if (tag == value) {
                ck.setChecked(false);
                break;
            }
        }
    }


    private void removeButtonByTag(int tagName) {
        for (int i = LinearTime.getChildCount() - 1; i >= 0; --i) {
            try {
                int tagvalue = (int) LinearTime.getChildAt(i).getTag();
                if (tagvalue == tagName) {
                    LinearTime.removeViewAt(i);
                }

            } catch (NullPointerException ex) {
                Log.e("Error Remove", ex.getMessage());
            }


        }
    }

    public void clearLinearTime() {
        for (int i = LinearTime.getChildCount() - 1; i >= 2; --i) {
            LinearTime.removeViewAt(i);
        }
    }

    public void AddNewTime(View v) {
        if (mRepeatType.equals("Daily")) {
            counter++;
            LinearLayout newLinear = Helpers.createLinearTimeItem(counter, activity, this);
            LinearTime.addView(newLinear);
            linearDailyTimes.put(counter, newLinear);
            Helpers.activeClickButton(newLinear);

        }
    }

    private int isFirstTime = 0;

    public void activeCheckBoxInGroup(LinearLayout group) {
        int length = group.getChildCount();
        if (length > 2) {
            for (int i = length - 1; i >= 2; --i) {
                if (group.getChildAt(i) instanceof LinearLayout) {
                    LinearLayout linearChildItem = (LinearLayout) group.getChildAt(i);
                    for (int j = 0; j < linearChildItem.getChildCount(); j++) {
                        if (linearChildItem.getChildAt(j) instanceof CheckBox) {
                            CheckBox ck = (CheckBox) linearChildItem.getChildAt(j);
                            ck.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    public void DeleteTime(View v) {
        int length = LinearTime.getChildCount();
        if (length > 2) {
            if (mRepeatType.equals("Daily")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Warning!");
                builder.setMessage("Do you want to Delete Selected Time?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTimeLayout();
                    }
                });

                builder.setNegativeButton("No", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        } else {
            Helpers.showToast(activity, "Could not Delete More Button!");
        }


    }

    private void deleteTimeLayout() {
        int length = LinearTime.getChildCount();
        if (length > 2) {
            boolean isDelete = false;
            for (int i = length - 1; i >= 2; --i) {
                if (LinearTime.getChildAt(i) instanceof LinearLayout) {
                    LinearLayout linearChildItem = (LinearLayout) LinearTime.getChildAt(i);
                    for (int j = 0; j < linearChildItem.getChildCount(); j++) {
                        if (linearChildItem.getChildAt(j) instanceof CheckBox) {
                            CheckBox ck = (CheckBox) linearChildItem.getChildAt(j);
                            if (ck.isChecked()) {
                                int tag = (int) ck.getTag();
                                LinearTime.removeView(linearDailyTimes.get(tag));
                                linearDailyTimes.remove(tag);
                                isDelete = true;
                            }
                        }
                    }
                }

            }
            if (!isDelete) {
                Helpers.showToast(activity, "Please Select Time To Delete! Thanks");
            } else {
                Helpers.showToast(activity, "Deleted");
            }
        }
    }

    private Hashtable<Integer, LinearLayout> linearDailyTimes;


    public void postTaskToDatabase() {
        alarmReceiver = new AlarmReceiver();
        if (mRepeatType.equals("Daily")) {
            for (int i = 0; i < LinearTime.getChildCount(); ++i) {
                if (LinearTime.getChildAt(i) instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) LinearTime.getChildAt(i);
                    for (int j = 0; j < viewGroup.getChildCount(); j++) {
                        if (viewGroup.getChildAt(j) instanceof Button) {
                            Button btnTime = (Button) viewGroup.getChildAt(j);
                            if (btnTime instanceof CheckBox) {

                            } else {
                                String timeValue = btnTime.getText().toString();
                                String[] timeValueSlip = timeValue.split(":");
                                mRepeatType = "Daily";

                                Calendar mCalendar = Calendar.getInstance();
                                int myHour = Integer.parseInt(timeValueSlip[0]);
                                int myMinutes = Integer.parseInt(timeValueSlip[1]);
                                mCalendar.set(Calendar.HOUR_OF_DAY, myHour);
                                mCalendar.set(Calendar.MINUTE, myMinutes);
                                mCalendar.set(Calendar.SECOND, 0);

                                Calendar now = Calendar.getInstance();

                                if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                                    mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                                }

                                TableUser tableUser = new TableUser(activity);
                                ModelTask task_cur = (ModelTask) spnTask.getSelectedItem();
                                mTaskId = task_cur.getTask_Id();
                                String isDelete = "false";

                                String Note = edtNote.getText().toString();
                                if (TextUtils.isEmpty(Note)) {
                                    Note = "";
                                }
                                Task newTask = new Task(mTaskId, mTaskDate, timeValue, Note, mRepeatType, mActive, soundName, isDelete, tableUser.getUserIdbyEmail(Helpers.user_email));
                                int ID = tableTask.InsertTask(newTask);

                                mRepeatTime = Key.milDay;
                                alarmReceiver.setRepeatAlarm(activity, mCalendar, ID, mRepeatTime);
                            }

                        }
                    }
                }

            }
        } else if (mRepeatType.equals("Weekly")) {
            boolean isAdded = false;
            for (int i = 0; i < LinearTime.getChildCount(); ++i) {
                if (LinearTime.getChildAt(i) instanceof Button) {
                    Button btnTime = (Button) LinearTime.getChildAt(i);
                    try {
                        int repeatId = (int) btnTime.getTag();
                        mRepeatType = "Weekly";

                        String timeValue = btnTime.getText().toString();
                        String[] timeValueSlip = timeValue.split(":");

                        Calendar mCalendar = Calendar.getInstance();
                        int myHour = Integer.parseInt(timeValueSlip[0]);
                        int myMinutes = Integer.parseInt(timeValueSlip[1]);
                        mCalendar.set(Calendar.HOUR_OF_DAY, myHour);
                        mCalendar.set(Calendar.MINUTE, myMinutes);
                        mCalendar.set(Calendar.SECOND, 0);

                        TableUser tableUser = new TableUser(activity);
                        ModelTask task_cur = (ModelTask) spnTask.getSelectedItem();
                        mTaskId = task_cur.getTask_Id();
                        String isDelete = "false";

                        String Note = edtNote.getText().toString();
                        if (TextUtils.isEmpty(Note)) {
                            Note = "";
                        }

                        Task newTask = new Task(mTaskId, mTaskDate, timeValue, Note, mRepeatType, mActive, soundName, isDelete, tableUser.getUserIdbyEmail(Helpers.user_email), String.valueOf(repeatId));
                        int ID = tableTask.InsertTask(newTask);

                        mRepeatTime = Key.milWeek;

                        mCalendar.set(Calendar.DAY_OF_WEEK, repeatId);

                        alarmReceiver.setRepeatAlamrbyDay(ID, activity, mCalendar, mRepeatTime);
                        isAdded = true;

                    } catch (NullPointerException ex) {
                        Log.e("No Tag error", ex.getMessage());
                    }

                }
            }
            if (!isAdded) {
                Helpers.showToast(activity, "Please Select Days Of Week! Thank You");
                return;
            }
        } else {
            String timeValue = btnTime.getText().toString();
            String[] timeValueSlip = timeValue.split(":");
            mRepeatType = "Monthly";

            Calendar mCalendar = Calendar.getInstance();
            int myHour = Integer.parseInt(timeValueSlip[0]);
            int myMinutes = Integer.parseInt(timeValueSlip[1]);
            mCalendar.set(Calendar.HOUR_OF_DAY, myHour);
            mCalendar.set(Calendar.MINUTE, myMinutes);
            mCalendar.set(Calendar.SECOND, 0);

            Calendar now = Calendar.getInstance();
            mCalendar.set(Calendar.MONTH, mMonth - 1);
            mCalendar.set(Calendar.YEAR, mYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay);

            if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                Helpers.showToast(activity, "Invalid Date And Time! Please Select Valid Date And Time");
                return;
            }

            TableUser tableUser = new TableUser(activity);
            ModelTask task_cur = (ModelTask) spnTask.getSelectedItem();
            mTaskId = task_cur.getTask_Id();
            String isDelete = "false";

            String Note = edtNote.getText().toString();
            if (TextUtils.isEmpty(Note)) {
                Note = "";
            }
            Task newTask = new Task(mTaskId, mTaskDate, timeValue, Note, mRepeatType, mActive, soundName, isDelete, tableUser.getUserIdbyEmail(Helpers.user_email));
            int ID = tableTask.InsertTask(newTask);

            mRepeatTime = Key.milMonth;
            alarmReceiver.setRepeatAlarm(activity, mCalendar, ID, mRepeatTime);
        }
        Helpers.showToast(activity, "Saved Successfully");
        gotoDashBoard();
    }


}
