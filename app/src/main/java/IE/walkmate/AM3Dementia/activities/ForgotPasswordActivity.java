package IE.walkmate.AM3Dementia.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.InputValidation;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.DatabaseHelper;
import IE.walkmate.AM3Dementia.Sql.TableUser;


public class ForgotPasswordActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ForgotPasswordActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayout_email;
    private AppCompatButton btnconfirm;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private Toolbar myToolbar;
    private ActionBar actionBar;
    private TableUser tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        init();
        initObjects();
        initListeners();

        Helpers.setupUI(findViewById(R.id.nestedScrollView), activity);

        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Forgot Password?");
    }

    private void init() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayout_email = findViewById(R.id.textinput_email);
        btnconfirm = findViewById(R.id.btnConfirm);
        myToolbar = findViewById(R.id.myToolbar);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        tableUser = new TableUser(activity);

    }

    private void initListeners() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputValidation.isInputEditextField(textInputLayout_email, getString(R.string.texterroremail))) {
                    return;
                }

                if (!inputValidation.isInputeditTextEmail(textInputLayout_email, getString(R.string.texterroremail))) {
                    return;
                }

                String email = textInputLayout_email.getEditText().getText().toString().trim();


                if (tableUser.checkUser(email)) {
                    Intent resetIntent = new Intent(activity, ResetPasswordActivity.class);
                    resetIntent.putExtra("email", email);
                    startActivity(resetIntent);
                    textInputLayout_email.getEditText().getText().clear();
                } else {

                    Snackbar.make(nestedScrollView, getString(R.string.no_exist_email), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
