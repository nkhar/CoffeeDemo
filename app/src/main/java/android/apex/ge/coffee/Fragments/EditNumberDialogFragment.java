package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class displays a dialog for providing number of items.
 */
public class EditNumberDialogFragment extends DialogFragment {

    protected final String LOG_TAG = "EditNDialogFragment";

    private EditText mEditNumberText1;
    private EditText mEditNumberText2;
    private Button mDialogOKButton;
    private Button mDialogCancelButton;


    // Defines the listener interface
    public interface EditNumberDialogListener {
        void onFinishEditDialog(String inputNumber1, String inputNumber2);
    }

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


  /*  @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateDialog");
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);
        *//*alertDialogBuilder.setMessage("Are you sure?");*//*

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialogBuilder.setView(inflater.inflate(R.layout.fragment_edit_product_data, null));

        // Add action buttons

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                sendBackResult();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }*/

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
        mDialogOKButton = view.findViewById(R.id.fragment_dialog_ok_button);
        mDialogCancelButton = view.findViewById(R.id.fragment_dialog_cancel_button);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditNumberText1.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

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
        listener.onFinishEditDialog(mEditNumberText1.getText().toString(), mEditNumberText2.getText().toString());
        dismiss();
    }
}
