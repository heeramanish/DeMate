package IE.walkmate.AM3Dementia.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import IE.walkmate.AM3Dementia.custom_control.ToggleButtonGroupTableLayout;

public class UpdateTaskActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private AppCompatActivity activity = UpdateTaskActivity.this;
    private Hashtable<Integer, String> WeekOfday = new Hashtable<>();
    public Hashtable<String, Integer> dayofWeek = new Hashtable<>();
    private Hashtable<String, Integer> CategoryDic;
    private Toolbar myToolbar;
    private ActionBar actionBar;

    private Spinner spnTask;
    private Custom_spinner_Task myAdapter;
    private ArrayList<ModelTask> modelTasks;


    private int Cat_Id;
    private int Sub_Id;
    private ToggleButtonGroupTableLayout radioGroupCategory, radioGroupDay;
    private RadioButton rbPersonal, rbHealth, rbSocial, rbHouseKeeping;
    private RadioGroup radioGroupSubTask;

    private RadioGroup radioGroupRepeatType;
    private RadioButton rdDaily, rdWeekly, rdMonthly;


    private TableTaskDefault tableTaskDefault;

    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private int mTaskId;
    private String mTaskTime;
    private String mTaskDate;
    private String mRepeatType;
    private String mRepeatType_temp;
    private String mActive;
    private String mRepeatId;
    private String mNote;
    private String mSoundName;
    private Calendar mCalendar;
    private String[] mDateSplit;
    private String[] mTimeSplit;


    private TextView tvDate, tvTime, lblDate, lbldateofweek;
    private Calendar taskTime;
    private TimePickerDialog dtpTime;

    private Calendar taskDate;
    private DatePickerDialog dtpDate;

    private TableTask tableTask;
    private TableSubCategory tableSubCategory;
    private TableCategory tableCategory;

    private Button btnRecord;
    private EditText edtNote;

    private AlarmReceiver alarmReceiver;

    private int mReceivedId;
    private Task mReceivedTask;
    private ModelTask mModelTask;
    private SubCategory mSubCategory;
    private NestedScrollView layout_parent;

    private CompoundButton.OnCheckedChangeListener SubCategoryClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        init();
        initObjects();
        intListerers();
        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Task");

        mReceivedId = getIntent().getIntExtra(Key.EXTRA_REMINDER_ID, -1);
        myAdapter = new Custom_spinner_Task(activity, R.layout.custom_spinner_add_task, modelTasks);
        spnTask.setAdapter(myAdapter);

        Helpers.setupUI(layout_parent, activity);

        //get task info
        mReceivedTask = tableTask.getTaskById(mReceivedId);

        //get Sub Cat Id
        mModelTask = tableTaskDefault.getMTaskById(mReceivedTask.getTaskID());
        Sub_Id = mModelTask.getSubCat_id();

        //Get Category Id
        mSubCategory = tableSubCategory.getSubCategoryById(Sub_Id);

        Cat_Id = mSubCategory.getCategoryId();

        //loadData For Category
        loadData();
        //load Data for SubCategory
        Helpers.setSelectesRadioButtonByUser(radioGroupSubTask, mSubCategory.getSubCategoryName());

        spnTask.setSelection(ModelTask.getPositionbyTaskId(mModelTask.getTask_Id(), modelTasks));

        //get values from task
        mRepeatType = mReceivedTask.getRepeatType();
        mRepeatType_temp = mRepeatType;
        mTaskDate = mReceivedTask.getDate();
        mTaskTime = mReceivedTask.getTime();
        mNote = mReceivedTask.getNote();
        mActive = mReceivedTask.getIsActive();
        mSoundName = mReceivedTask.getSoundName();
        if (mSoundName != null && mSoundName.length() > 0) {
            btnRecord.setText("Recorded");
        }
        Helpers.setSelectesRadioButtonByUser(radioGroupRepeatType, mRepeatType);
        tvDate.setText(mTaskDate);
        tvTime.setText(mTaskTime);
        edtNote.setText(mNote);
        mCalendar = Calendar.getInstance();
        mDateSplit = mTaskDate.split("/");
        mTimeSplit = mTaskTime.split(":");

        mDay = Integer.parseInt(mDateSplit[0]);
        mMonth = Integer.parseInt(mDateSplit[1]);
        mYear = Integer.parseInt(mDateSplit[2]);
        mHour = Integer.parseInt(mTimeSplit[0]);
        mMinute = Integer.parseInt(mTimeSplit[1]);

        taskTime = Calendar.getInstance();
        taskTime.set(Calendar.HOUR_OF_DAY, mHour);
        taskTime.set(Calendar.MINUTE, mMinute);
        taskTime.set(Calendar.SECOND, 0);

        taskDate = Calendar.getInstance();
        taskDate.set(Calendar.MONTH, mMonth - 1);
        taskDate.set(Calendar.YEAR, mYear);
        taskDate.set(Calendar.DAY_OF_MONTH, mDay);

        createDatePickerDialog();
        createTimePickerDialog();

        if (mRepeatType.equals("Weekly")) {
            mRepeatId = mReceivedTask.getRepeatId();
            Helpers.setRadioButtonInToggleGroupByName(WeekOfday.get(Integer.parseInt(mRepeatId)), radioGroupDay);

        }

    }


    private void loadData() {
        if (Cat_Id != -1) {
            String categoryName = tableCategory.getCategoryNamebyId(Cat_Id);
            Helpers.setRadioButtonInToggleGroupByName(categoryName, radioGroupCategory);
        }
    }


    private void init() {
        layout_parent = findViewById(R.id.nestedScrollView);
        spnTask = findViewById(R.id.spinner_task_name);
        myToolbar = findViewById(R.id.myToolbar);
        lblDate = findViewById(R.id.lblDate);
        lbldateofweek = findViewById(R.id.lblDayOfWeek);

        taskTime = Calendar.getInstance();

        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        rbPersonal = findViewById(R.id.radioPersonal);
        rbHealth = findViewById(R.id.radioHealth);
        rbSocial = findViewById(R.id.radioSocialLersure);
        rbHouseKeeping = findViewById(R.id.radioHouseKeeping);
        tvDate = findViewById(R.id.tvTaskDate);
        tvTime = findViewById(R.id.tvTaskTime);

        radioGroupRepeatType = findViewById(R.id.radioGroupRepeatType);
        rdDaily = findViewById(R.id.radioDaily);
        rdWeekly = findViewById(R.id.radioWeekly);
        rdMonthly = findViewById(R.id.radioMonthly);


        radioGroupDay = findViewById(R.id.radioGroupDay);
        radioGroupDay.setVisibility(View.GONE);

        tableTask = new TableTask(activity);
        edtNote = findViewById(R.id.edtnotes);
        btnRecord = findViewById(R.id.btnaudio);

        radioGroupSubTask = findViewById(R.id.radioGroupSubCategory);
    }

    private void initObjects() {
        modelTasks = new ArrayList<ModelTask>();
        tableTaskDefault = new TableTaskDefault(activity);
        tableCategory = new TableCategory(activity);
        tableSubCategory = new TableSubCategory(activity);
        alarmReceiver = new AlarmReceiver();
        mActive = "true";
        mRepeatId = "";

        WeekOfday.put(2, "Mon");
        WeekOfday.put(3, "Tue");
        WeekOfday.put(4, "Wed");
        WeekOfday.put(5, "Thu");
        WeekOfday.put(6, "Fri");
        WeekOfday.put(7, "Sat");
        WeekOfday.put(1, "Sun");

        dayofWeek.put("Mon", 2);
        dayofWeek.put("Tue", 3);
        dayofWeek.put("Wed", 4);
        dayofWeek.put("Thu", 5);
        dayofWeek.put("Fri", 6);
        dayofWeek.put("Sat", 7);
        dayofWeek.put("Sun", 1);

        CategoryDic = new Hashtable<>();
        CategoryDic.put("Yoga", 1);
        CategoryDic.put("Tai-Chi", 2);
        CategoryDic.put("Chair Based Exercises", 3);
        CategoryDic.put("Brain Exercise", 4);
    }


    private void intListerers() {
        tvTime.setOnClickListener(this);
        //CATEGORY CLICK
        CompoundButton.OnCheckedChangeListener radioListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Helpers.clearRadioGroup(radioGroupSubTask);
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
                                radioGroupDay.setVisibility(View.VISIBLE);
                                lbldateofweek.setVisibility(View.VISIBLE);
                                tvDate.setVisibility(View.GONE);
                                lblDate.setVisibility(View.GONE);
                            } else if (radioButton.getText().equals("Monthly")) {
                                radioGroupDay.setVisibility(View.GONE);
                                lbldateofweek.setVisibility(View.GONE);
                                tvDate.setVisibility(View.VISIBLE);
                                lblDate.setVisibility(View.VISIBLE);
                            } else {
                                radioGroupDay.setVisibility(View.GONE);
                                lbldateofweek.setVisibility(View.GONE);
                                tvDate.setVisibility(View.GONE);
                                lblDate.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });

        //SUBCATEGORY CLICK
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

    }

    private void resetTaskSpinner(int id) {
        modelTasks.clear();
        modelTasks.addAll(tableTaskDefault.listTasksByCatId(id));
        myAdapter.notifyDataSetChanged();
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;

        if (minute < 10) {
            mTaskTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTaskTime = hourOfDay + ":" + minute;
        }
        tvTime.setText(mTaskTime);
    }

    private void createTimePickerDialog() {
        dtpTime = new TimePickerDialog(activity, this, taskTime.get(Calendar.HOUR_OF_DAY), taskTime.get(Calendar.MINUTE), true);
    }

    private void createDatePickerDialog() {
        dtpDate = new DatePickerDialog(activity, this, taskDate.get(Calendar.YEAR), taskDate.get(Calendar.MONTH), taskDate.get(Calendar.DAY_OF_MONTH));
    }

    // On clicking Date picker
    public void setDate(View v) {
        dtpDate.show();
    }


    public void updaTasktoDatabase() {
        //get Repeat Type
        mRepeatType = Helpers.getRadioButtonSelectedName(activity, radioGroupRepeatType);

        //intialize Calendar for alarm set up
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        //Validate DateTime
        Calendar now = Calendar.getInstance();
        if (mRepeatType.equals("Monthly")) {
            mCalendar.set(Calendar.MONTH, mMonth - 1);
            mCalendar.set(Calendar.YEAR, mYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
            if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                Helpers.showToast(getApplicationContext(), "Invalid Date and Time! Please Select Valid Date and Time");
                return;
            }
        } else if (mRepeatType.equals("Daily")) {
            if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        //cancel alarm first
        alarmReceiver.cancelAlarm(getApplicationContext(), mReceivedId);


        ModelTask task_cur = (ModelTask) spnTask.getSelectedItem();
        mTaskId = task_cur.getTask_Id();
        mReceivedTask.setTaskID(mTaskId);
        mReceivedTask.setDate(mTaskDate);
        mReceivedTask.setTime(mTaskTime);

        mNote = edtNote.getText().toString();
        if (TextUtils.isEmpty(mNote)) {
            mNote = "";
        }
        mReceivedTask.setNote(mNote);
        mReceivedTask.setRepeatType(mRepeatType);
        mReceivedTask.setSoundName(mSoundName);

        if (!mRepeatType.equals("Weekly")) {
            mRepeatId = null;
            mReceivedTask.setRepeatId(mRepeatId);

            //Update Task
            tableTask.updateItembyId(mReceivedTask);

            if (mRepeatType.equals("Monthly")) {
                mRepeatTime = Key.milMonth;
                alarmReceiver.setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedId, mRepeatTime);
            } else if (mRepeatType.equals("Daily")) {
                mRepeatTime = Key.milDay;
                alarmReceiver.setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedId, mRepeatTime);
            }

        } else {
            mRepeatId = String.valueOf(Helpers.getRepeatDayInToggleGroup(radioGroupDay, dayofWeek));
            if (TextUtils.isEmpty(mRepeatId)) {
                Helpers.showToast(activity, "Please Select Day of Week! Thank You");
                return;
            }
            mReceivedTask.setRepeatId(mRepeatId);
//            Update Task
            tableTask.updateItembyId(mReceivedTask);

            mRepeatTime = Key.milWeek;
            mCalendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(mRepeatId));
            alarmReceiver.setRepeatAlamrbyDay(mReceivedId, activity, mCalendar, mRepeatTime);
        }

        Helpers.showToast(activity, "Edited");
        finish();
    }


    public void SaveTask(View view) {
        updaTasktoDatabase();
    }


    public void recordAudio(View v) {
        Intent recordAudio = new Intent(activity, RecordAudioActivity.class);
        recordAudio.putExtra("audio", mSoundName);
        startActivityForResult(recordAudio, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 10) {
                mSoundName = data.getStringExtra("audio");
                btnRecord.setText("Recorded");

            }
        }
    }

    @Override
    public void onClick(View v) {
        dtpTime.show();
    }

    public static class AboutApp {
    }
}
