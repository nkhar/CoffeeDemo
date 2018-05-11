package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SettingsFragment extends Fragment {

    protected final String LOG_TAG = "SettingsFragment";

    // private static
    private static final String PREFERENCES_NAME = "MyPreferencesFile";
    private static final String SERVER_URL = "SERVER_URL";
    private static final String SERVER_USER = "SERVER_USER";
    private static final String SERVER_PASSWORD = "SERVER_PASSWORD";
    private static final String VAN_ACCOUNT = "VAN_ACCOUNT";




    private EditText vanAccEditText;
    private EditText urlEditText;
    private EditText userEditText;
    private EditText passwordEditText;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        vanAccEditText = view.findViewById(R.id.edit_text_fragment_settings_van_account);
        urlEditText = view.findViewById(R.id.edit_text_fragment_settings_url);
        userEditText = view.findViewById(R.id.edit_text_fragment_settings_user);
        passwordEditText = view.findViewById(R.id.edit_text_fragment_settings_password);
        saveButton = view.findViewById(R.id.button_fragment_settings_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "\n\n Save Button in SettingsFragment was clicked.");
                saveValuesToPreferenceFile();
            }
        });

        return view;
    }

    private void saveValuesToPreferenceFile() {
        Log.d(LOG_TAG, "we are in method saveValuesToPreferenceFile");
        SharedPreferences settings = getActivity().getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        //Set values from EditTexts to true
        editor.putString(SERVER_URL, urlEditText.getText().toString());
        editor.putString(SERVER_USER, userEditText.getText().toString());
        editor.putString(SERVER_PASSWORD, passwordEditText.getText().toString());
        editor.putString(VAN_ACCOUNT, vanAccEditText.getText().toString());

        Log.d(LOG_TAG, "Values entered in EditText were: \n" + urlEditText.getText().toString());


        // Commit the edits!
        editor.commit();
    }
}
