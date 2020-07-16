package IE.walkmate.AM3Dementia.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.InputValidation;
import IE.walkmate.AM3Dementia.Model.User;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableUser;
import IE.walkmate.AM3Dementia.custom_control.CustomBackgroundTextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private Toolbar myToolbar;
    private ActionBar actionBar;
    private ArrayList<String> countryList;
    private ArrayAdapter<String> list_country;

    private LinearLayout linearInfo, linearPassword;

    private NestedScrollView nestedScrollView;
    private AutoCompleteTextView autotv_country;
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupQuestion;
    private RadioGroup radioGroupEducation;

    private CustomBackgroundTextInputLayout textInputLayout_email;
    private CustomBackgroundTextInputLayout textInputLayout_password;
    private CustomBackgroundTextInputLayout textInputLayout_Cpassword;
    private CustomBackgroundTextInputLayout textInputLayout_country;
    private CustomBackgroundTextInputLayout textInputLayout_age;
    private CustomBackgroundTextInputLayout textInputLayout_gender;
    private CustomBackgroundTextInputLayout textInputLayout_question;
    private CustomBackgroundTextInputLayout textInputLayout_education;
    private CustomBackgroundTextInputLayout textInputLayout_confirm;

    private CheckBox ckConfirm;

    private TextView tvPassword, tvCpassword;
    private AppCompatButton btnsignup, btnupdate;
    private InputValidation inputValidation;

    //adding image profile
    private CircleImageView imgPImage;
    private AppCompatTextView tveditlink;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    Bitmap thumbnail;

    private TableUser tableUser;

    //load data
    private String email = "";

    String messgage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        initObject();
        initListeners();

        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        btnupdate.setVisibility(View.GONE);
        list_country = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, countryList);
        autotv_country.setAdapter(list_country);

        loaddata();
        Helpers.setupUI(findViewById(R.id.nestedScrollView), activity);
    }





    private void loaddata() {
        email = getIntent().getStringExtra("user_email");
        if (email != null) {
            actionBar.setTitle("Account");

            textInputLayout_email.getEditText().setEnabled(false);
            textInputLayout_confirm.setVisibility(View.GONE);
            btnsignup.setVisibility(View.GONE);

            btnsignup.setText("Update_temp");
            btnupdate.setVisibility(View.GONE);

            linearPassword.setVisibility(View.GONE);
            tveditlink.setVisibility(View.GONE);

            User user = tableUser.getuser(email);
            imgPImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getImageName(), 0, user.getImageName().length));

            textInputLayout_email.getEditText().setText(user.getEmail());
            autotv_country.setText(user.getLocation());
            textInputLayout_age.getEditText().setText(user.getAge() + "");


            String is_dementia = "Yes";
            if (user.getIsDementia() == 0) {
                is_dementia = "No";
            }
            Helpers.setSelectesRadioButtonByUser(radioGroupQuestion, is_dementia);
            Helpers.setSelectesRadioButtonByUser(radioGroupEducation, user.getEducation_level());
            Helpers.setSelectesRadioButtonByUser(radioGroupGender, user.getGender());

            Helpers.setDisableRadioGroup(radioGroupGender);
            Helpers.setDisableRadioGroup(radioGroupEducation);
            Helpers.setDisableRadioGroup(radioGroupQuestion);

            textInputLayout_age.getEditText().setEnabled(false);
            autotv_country.setEnabled(false);

        }

    }


    private void init() {
        linearInfo = findViewById(R.id.linear_info);
        linearPassword = findViewById(R.id.linearPassword);
        myToolbar = findViewById(R.id.myToolbar);
        autotv_country = findViewById(R.id.autotv_country);
        textInputLayout_email = findViewById(R.id.textinput_email);
        textInputLayout_password = findViewById(R.id.textinput_password);
        textInputLayout_Cpassword = findViewById(R.id.textinput_Cpassword);
        textInputLayout_age = findViewById(R.id.textinput_age);
        textInputLayout_country = findViewById(R.id.textinput_country);
        textInputLayout_gender = findViewById(R.id.textinput_gender);
        textInputLayout_question = findViewById(R.id.textinput_question);
        textInputLayout_education = findViewById(R.id.textinput_education);
        textInputLayout_confirm = findViewById(R.id.textinput_confirm);
        ckConfirm = findViewById(R.id.ckConfirm);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupEducation = findViewById(R.id.radioGroupEducation);
        radioGroupQuestion = findViewById(R.id.radioGroupQuestion);
        btnsignup = findViewById(R.id.btnsignup);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        tvPassword = findViewById(R.id.tvPassword);
        tvCpassword = findViewById(R.id.tvCPassword);

        //add image
        imgPImage = findViewById(R.id.imgprofilepicture);
        tveditlink = findViewById(R.id.tvlinkeditimg);
        btnupdate = findViewById(R.id.btnupdate);


    }

    private void initObject() {
        inputValidation = new InputValidation(activity);
        countryList = getCountryName();
        tableUser = new TableUser(activity);
        email = null;
        messgage = "Field Can't be Empty!";
    }

    private void initListeners() {
        btnsignup.setOnClickListener(this);
        tveditlink.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
    }

    private ArrayList<String> getCountryName() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        return countries;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnsignup) {
            verifyInputToPost();
            
        } else if (v.getId() == R.id.tvlinkeditimg) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                imgPImage.setEnabled(false);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return;
            } else {
                imgPImage.setEnabled(true);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Select Image");
            builder.setItems(R.array.uploadImages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                            break;
                        case 1:
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAPTURE_PHOTO);
                            break;
                        case 2:
                            imgPImage.setImageResource(R.drawable.ic_account_circle_black);
                            break;
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (v.getId() == R.id.btnupdate) {
            Button btn = (Button) v;
            String value = btn.getText().toString();
            if (value.equals("Update Password")) {
                if (!inputValidation.isInputEditextField(textInputLayout_password, messgage) |
                        !inputValidation.isInputEditextField(textInputLayout_Cpassword, messgage)) {
                    return;
                }
                if (!inputValidation.isInputEdittextMatches(textInputLayout_password, textInputLayout_Cpassword, getString(R.string.errorpasswordmatch))) {
                    return;
                }
                String newPassword = textInputLayout_password.getEditText().getText().toString().trim();
                if (tableUser.updatePassword(email, newPassword)) {
                    Helpers.showToast(activity, "Updated Successfully");
                    finish();
                } else {
                    Snackbar.make(nestedScrollView, getString(R.string.suddenlyError), Snackbar.LENGTH_LONG).show();
                    return;
                }
            } else if (value.equals("Update Information")) {
                if (!inputValidation.isInputEditextField(textInputLayout_age, messgage) |
                        !inputValidation.isInputValidationAutoCompletextbox(textInputLayout_country, autotv_country, messgage) |
                        !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_gender, radioGroupGender, "Please Select Gender!") |
                        !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_question, radioGroupQuestion, "Please Answer A Question!") |
                        !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_education, radioGroupEducation, "Please Select Education Level!")) {
                    return;
                }
                
                String country = autotv_country.getText().toString().trim();

                if (!inputValidation.isInputCountryValidate(textInputLayout_country, countryList, country, "Please Select Valid Country")) {
                    return;
                }
                int age = Integer.parseInt(textInputLayout_age.getEditText().getText().toString().trim());
                String gender = Helpers.getRadioButtonSelectedName(activity, radioGroupGender);

                //education
                String educationlevel = Helpers.getRadioButtonSelectedName(activity, radioGroupEducation);

                String selectedQuestion = Helpers.getRadioButtonSelectedName(activity, radioGroupQuestion);
                int isDementia = 1;
                if (selectedQuestion.contentEquals("No")) {
                    isDementia = 0;
                }

                //Image
                byte[] imgdata = Helpers.encodeImage(imgPImage);

                User userInfo = new User(country, age, gender, isDementia, imgdata, educationlevel);
                if (tableUser.updateUserInfo(email, userInfo)) {
                    Helpers.showToast(activity, "Updated Successfully");
                    finish();
                } else {
                    Snackbar.make(nestedScrollView, getString(R.string.suddenlyError), Snackbar.LENGTH_LONG).show();
                    return;
                }

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    //set profile picture form gallery
                    imgPImage.setImageBitmap(selectedImage);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAPTURE_PHOTO) {
            if (resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        //set profile picture form camera
        imgPImage.setMaxWidth(200);
        imgPImage.setImageBitmap(thumbnail);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imgPImage.setEnabled(true);
            }
        }
    }

    public void verifyInputToPost() {

        if (!inputValidation.isInputEditextField(textInputLayout_email, messgage) |
                !inputValidation.isInputEditextField(textInputLayout_password, messgage) |
                !inputValidation.isInputEditextField(textInputLayout_Cpassword, messgage) |
                !inputValidation.isInputEditextField(textInputLayout_age, messgage) |
                !inputValidation.isInputValidationAutoCompletextbox(textInputLayout_country, autotv_country, messgage) |
                !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_gender, radioGroupGender, "Please Select Gender!") |
                !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_question, radioGroupQuestion, "Please Answer A Question!") |
                !inputValidation.isInputValidateRadioButtonGroup(textInputLayout_education, radioGroupEducation, "Please Select Education Level!")) {
            Helpers.showToast(activity, "Some Errors Happened! Please Check Your Detail Again!");
            return;
        }

        if (!inputValidation.isInputeditTextEmail(textInputLayout_email, getString(R.string.texterroremail))) {
            Helpers.showToast(activity, "Some Errors Happened! Please Check Your Detail Again!");
            return;
        }
        String email = textInputLayout_email.getEditText().getText().toString().trim();

        if (!inputValidation.isInputEdittextMatches(textInputLayout_password, textInputLayout_Cpassword, getString(R.string.errorpasswordmatch))) {
            Helpers.showToast(activity, "Some Errors Happened! Please Check Your Detail Again!");
            return;
        }

        String country = autotv_country.getText().toString().trim();

        if (!inputValidation.isInputCountryValidate(textInputLayout_country, countryList, country, "Please Select Valid Country")) {
            Helpers.showToast(activity, "Some Errors Happened! Please Check Your Detail Again!");
            return;
        }


        String password = textInputLayout_password.getEditText().getText().toString().trim();
        int age = Integer.parseInt(textInputLayout_age.getEditText().getText().toString().trim());

        String gender = Helpers.getRadioButtonSelectedName(activity, radioGroupGender);

        //education
        String educationlevel = Helpers.getRadioButtonSelectedName(activity, radioGroupEducation);

        String selectedQuestion = Helpers.getRadioButtonSelectedName(activity, radioGroupQuestion);
        int isDementia = 1;
        if (selectedQuestion.contentEquals("No")) {
            isDementia = 0;
        }

        //Image
        byte[] imgdata = Helpers.encodeImage(imgPImage);


        //check if Email is Exist
        if (!inputValidation.isEmailExist(email, textInputLayout_email, tableUser)) {
            Helpers.showToast(activity, "Some Errors Happened! Please Check Your Detail Again!");
            return;
        }

        if (!inputValidation.isInputCheckBoxClick(textInputLayout_confirm, ckConfirm, "Please indicate that you have read and agree our Terms and Contidions")) {
            return;
        }

        // add user
        User user = new User(email, password, country, age, gender, isDementia, imgdata, educationlevel);
        tableUser.addUser(user);

        //show message success
        Snackbar.make(nestedScrollView, getString(R.string.successmessage), Snackbar.LENGTH_LONG).show();
        resetInputFields();
        Intent loginpage = new Intent(activity, LoginActivity.class);
        startActivity(loginpage);
    }

    private void resetInputFields() {
        textInputLayout_email.getEditText().getText().clear();
        textInputLayout_password.getEditText().getText().clear();
        textInputLayout_Cpassword.getEditText().getText().clear();
        autotv_country.getText().clear();
        textInputLayout_age.getEditText().getText().clear();
        imgPImage.setImageResource(R.drawable.ic_account_circle_black);
        Helpers.resetRadioGroup(radioGroupGender);
        Helpers.resetRadioGroup(radioGroupEducation);
        Helpers.resetRadioGroup(radioGroupQuestion);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (btnsignup.getText().toString().equalsIgnoreCase("Update_temp")) {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_update_info) {
            linearPassword.setVisibility(View.GONE);
            linearInfo.setVisibility(View.VISIBLE);
            tveditlink.setVisibility(View.VISIBLE);
            Helpers.setEnableRadioGroup(radioGroupGender);
            Helpers.setEnableRadioGroup(radioGroupEducation);
            Helpers.setEnableRadioGroup(radioGroupQuestion);
            textInputLayout_age.getEditText().setEnabled(true);
            autotv_country.setEnabled(true);
            btnupdate.setVisibility(View.VISIBLE);
            btnupdate.setText("Update Information");

        } else if (item.getItemId() == R.id.action_update_password) {
            linearPassword.setVisibility(View.VISIBLE);
            linearInfo.setVisibility(View.GONE);

            tveditlink.setVisibility(View.INVISIBLE);
            Helpers.setDisableRadioGroup(radioGroupGender);
            Helpers.setDisableRadioGroup(radioGroupEducation);
            Helpers.setDisableRadioGroup(radioGroupQuestion);

            textInputLayout_age.getEditText().setEnabled(false);
            autotv_country.setEnabled(false);

            btnupdate.setVisibility(View.VISIBLE);
            btnupdate.setText("Update Password");

        }
        return super.onOptionsItemSelected(item);
    }
}
