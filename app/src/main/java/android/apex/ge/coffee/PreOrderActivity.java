package android.apex.ge.coffee;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class PreOrderActivity extends AppCompatActivity {

    private final String LOG_TAG = "PreOrderActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        Log.d(LOG_TAG, "we are in the onCreate method of PreOrder activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        Toolbar toolbar = findViewById(R.id.toolbar_pre_order);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }
}
