package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * This class displays a dialog for providing number of items.
 */
public class EditNumberDialogFragment extends DialogFragment{

    protected final String LOG_TAG = "EditNDialogFragment";

    private EditText editNumberText1;
    private EditText editNumberText2;

    public EditNumberDialogFragment() {
        Log.d(LOG_TAG, "we are in constructor of EditNumberDialogFragment class");
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNumberDialogFragment newInstance(String title) {

        EditNumberDialogFragment frag = new EditNumberDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_product_data, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        editNumberText1 =  view.findViewById(R.id.fragment_dialog_number_editText);
        editNumberText2 =  view.findViewById(R.id.fragment_dialog_number_editText2);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        editNumberText1.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
