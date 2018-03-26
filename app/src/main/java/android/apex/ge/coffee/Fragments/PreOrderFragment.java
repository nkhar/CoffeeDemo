package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nika on 26/03/2018.
 * This class should display one of the fragments chosen from navigation drawer.
 */

public class PreOrderFragment extends Fragment{

    protected final String LOG_TAG = "PreOrderFragment";

    RecyclerView recyclerView;
    RecyclerViewListAdapter adapter;

    private final int mColumnCount = 1;

    private TextView textView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PreOrderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the PreOrderFragment class");
        /*
        Here we are using the same layout as with MachineFragment class: fragment_machine
         */

        View view = inflater.inflate(R.layout.fragment_machine, container, false);

        textView = view.findViewById(R.id.text_view_for_machine);
        textView.setText("This is PreOrders");

        /*
        Recycler View
         */
        Context context = view.getContext();

        return view;

    }
}
