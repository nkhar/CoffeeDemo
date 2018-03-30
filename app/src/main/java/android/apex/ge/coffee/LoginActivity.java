package android.apex.ge.coffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String LOG_TAG = "LoginActivity";

    //Give your SharedPreferences file a name and save it to a static variable
    public static final String PREFERENCES_NAME = "MyPreferencesFile";
    public static final String IS_USER_LOGGED_IN = "hasLoggedIn";

    private EditText mLoginUsernameEditText;
    private EditText mLoginPasswordEditText;
    private Button mLoginSignInButton;
    private Button mLoginCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginUsernameEditText = findViewById(R.id.login_activity_username_edit_text);
        mLoginPasswordEditText = findViewById(R.id.login_activity_password_edit_text);
        mLoginSignInButton = findViewById(R.id.login_activity_sign_in_button);
        mLoginCancelButton = findViewById(R.id.login_activity_cancel_button);

        mLoginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginUsernameEditText.getText().toString().equals("apex") && mLoginPasswordEditText.getText().toString().equals("coffee")) {
                    checkLoginCredentials();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this, "The login credentials are invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoginCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }


    private void checkLoginCredentials() {
        Log.d(LOG_TAG,"We are checking if user is Logged in from checkLoginCredentials");
        //User has successfully logged in, save this information
        // We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFERENCES_NAME, 0); // 0 - for private mode same as MODE_PRIVATE
        SharedPreferences.Editor editor = settings.edit();

        //Set "hasLoggedIn" to true
        editor.putBoolean(IS_USER_LOGGED_IN, true);

        // Commit the edits!
        editor.commit();
    }
}
