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
import android.widget.Toast;

import IE.walkmate.AM3Dementia.Class.Helpers;
import IE.walkmate.AM3Dementia.Class.InputValidation;
import IE.walkmate.AM3Dementia.R;
import IE.walkmate.AM3Dementia.Sql.TableUser;


public class ResetPasswordActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ResetPasswordActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayout_password;
    private TextInputLayout textInputLayout_Cpassword;
    private AppCompatButton btnreset;

    private InputValidation inputValidation;
    private TableUser tableUser;
    String email;

    private Toolbar myToolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        init();
        initObjects();
        initListeners();


        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Reset Password");
        Helpers.setupUI(findViewById(R.id.nestedScrollView), activity);

    }

    private void init() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayout_password = findViewById(R.id.textinput_password);
        textInputLayout_Cpassword = findViewById(R.id.textinput_Cpassword);
        btnreset = findViewById(R.id.btnreset);
        myToolbar = findViewById(R.id.myToolbar);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        tableUser = new TableUser(activity);

        email = getIntent().getStringExtra("email");

    }

    private void initListeners() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        if (!inputValidation.isInputEditextField(textInputLayout_password, getString(R.string.texterrorpassword)) |
                !inputValidation.isInputEditextField(textInputLayout_Cpassword, getString(R.string.texterrorpassword))) {
            return;
        }

        if (!inputValidation.isInputEdittextMatches(textInputLayout_password, textInputLayout_Cpassword, getString(R.string.errorpasswordmatch))) {
            return;
        }

        String newPassword = textInputLayout_password.getEditText().getText().toString().trim();

        if (tableUser.checkUser(email)) {
            if (tableUser.updatePassword(email, newPassword)) {
                emptyInput();
                Toast.makeText(activity, getString(R.string.updatesuccess), Toast.LENGTH_LONG).show();
                Intent LoginIntent = new Intent(activity, LoginActivity.class);
                LoginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(LoginIntent);
                finish();
            } else {
                Snackbar.make(nestedScrollView, getString(R.string.suddenlyError), Snackbar.LENGTH_LONG).show();
                return;
            }
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.erroremailexist), Snackbar.LENGTH_LONG).show();
            return;
        }

    }

    private void emptyInput() {
        textInputLayout_password.getEditText().getText().clear();
        textInputLayout_Cpassword.getEditText().getText().clear();
    }
}
