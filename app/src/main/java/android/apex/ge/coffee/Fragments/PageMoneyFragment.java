package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nika on 26/03/2018.
 * This class is responsible for displaying fragment in the Money tab of the coffee machine.
 */

public class PageMoneyFragment extends Fragment implements ILibObjectCrud {

    protected final String LOG_TAG = "PageMoneyFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private TextView textView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PageMoneyFragment() {
    }


    public static PageMoneyFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageMoneyFragment fragment = new PageMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the PageRecViewFragment class");

        View view = inflater.inflate(R.layout.fragment_page_money, container, false);

        // TextView
        textView = view.findViewById(R.id.text_view_page_money);

        return view;
    }


    @Override
    public void onClick(Object value) {
        Log.d(LOG_TAG, "Something was Clicked" + value.toString());

    }

    @Override
    public void onLongClick(Object value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }
}
