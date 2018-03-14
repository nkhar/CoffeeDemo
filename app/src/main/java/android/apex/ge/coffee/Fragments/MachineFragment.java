package android.apex.ge.coffee.Fragments;


import android.apex.ge.coffee.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nika on 14/03/2018.
 * This is a fragment class that is displayed in navigation drawer when corresponding item is
 * clicked in the menu.
 */

public class MachineFragment extends Fragment {

    protected final String LOG_TAG = "MachineFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MachineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the MachineFragment class");

        View view = inflater.inflate(R.layout.fragment_machine, container, false);

        TextView textView = view.findViewById(R.id.text_view_for_machine);
        textView.setText("This is the machine");

        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);


        return view;
    }

}
