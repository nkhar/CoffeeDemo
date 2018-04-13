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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class displays a dialog for providing number of items.
 */
public class EditNumberDialogFragment extends DialogFragment {

    protected final String LOG_TAG = "EditNDialogFragment";

    private EditText mEditNumberText1;
    private EditText mEditNumberText2;


    // Defines the listener interface
    public interface EditNumberDialogListener {
        void onFinishEditDialog(String inputNumber1, String inputNumber2, String prodPPID);
    }

    public EditNumberDialogFragment() {
        Log.d(LOG_TAG, "we are in constructor of EditNumberDialogFragment class");
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNumberDialogFragment newInstance(String title, String prodPPID) {

        EditNumberDialogFragment frag = new EditNumberDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("prodPPID", prodPPID);
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
        Log.d(LOG_TAG, "We are in onViewCreated");


        // Get field from view
        mEditNumberText1 = view.findViewById(R.id.fragment_dialog_number_editText);
        mEditNumberText2 = view.findViewById(R.id.fragment_dialog_number_editText2);
        Button mDialogOKButton = view.findViewById(R.id.fragment_dialog_ok_button);
        Button mDialogCancelButton = view.findViewById(R.id.fragment_dialog_cancel_button);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditNumberText1.requestFocus();
        // This if statement is used to remove lint warning about NullPointerException.
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        else {
            Log.d(LOG_TAG,"However strange it may seem this activity is not visual otherwise it would not have returned null");
        }


        mDialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on success
                sendBackResult();
            }
        });

        mDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDialog() != null) {
                    dismiss();
                }
            }
        });

    }


    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditNumberDialogListener listener = (EditNumberDialogListener) getTargetFragment();

        if (checkIfNumbersWereEntered(mEditNumberText1.getText().toString(), mEditNumberText2.getText().toString())) {
            listener.onFinishEditDialog(mEditNumberText1.getText().toString(), mEditNumberText2.getText().toString(), getArguments().getString("prodPPID"));
            dismiss();
        } else {
            mEditNumberText1.getText().clear();
            mEditNumberText2.getText().clear();
            Toast.makeText(this.getActivity(), "One of the fields entered was not a numeric", Toast.LENGTH_LONG).show();
        }


    }

    private boolean checkIfNumbersWereEntered(String stringNumber1, String stringNumber2) {
        boolean flag = true;
        if (!(isNumeric(stringNumber1)) || !(isNumeric(stringNumber2))) {
            flag = false;
        }

        return flag;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d*\\.?\\d+");
    }
}
