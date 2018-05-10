package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.CoffeeApp;
import android.apex.ge.coffee.DocGoodsActivity;
import android.apex.ge.coffee.MainActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeDocList;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.Model.CoffeeDoc;
import android.apex.ge.coffee.UserInterface.SimpleDividerItemDecoration;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nika on 26/03/2018.
 * This class is used to display Documents item from NavigationDrawer
 */

public class DrawerDocumentFragment extends Fragment implements ILibObjectCrud<CoffeeDoc>, DatePickerDialog.OnDateSetListener {

    protected final String LOG_TAG = "DrawerDocumentFragment";

    public static final String DOCUMENT_ID = "DOCUMENT_ID";
    public static final String CORRESPOND_ACC = "CORRESPOND_ACC";
    public static final String WAYBILL_NUM = "WAYBILL_NUM";
    public static final String NUMBER_IN = "NUMBER_IN";
    public static final String D_DATE = "D_DATE";

    private static final String CHOSEN_DATE="CHOSEN_DATE";
    private static final String DEFAULT_DATE_NOT_SAVED="DEFAULT_DATE_NOT_SAVED";

    RecyclerView recyclerView;
    MyCoffeeDocRecyclerViewAdapter adapter;

    private final int mColumnCount = 1;

    private TextView textView;

    private SwipeRefreshLayout swipeRefreshLayout;

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar mCurrentCalendar;
    String mDateString;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DrawerDocumentFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(LOG_TAG, "We are in onSaveInstanceState of DrawerDocumentFragment class");
        outState.putString(CHOSEN_DATE, mDateString);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCalendar = Calendar.getInstance();

        if (savedInstanceState!=null) {
            String localDate= savedInstanceState.getString(CHOSEN_DATE, DEFAULT_DATE_NOT_SAVED);
            Log.d(LOG_TAG, "Date saved to bundle is in string format: " + localDate);
            if (!localDate.equals(DEFAULT_DATE_NOT_SAVED)) {
                Log.d(LOG_TAG, "Date saved is not null inside if statement: " + localDate);

                try {

                    Log.d(LOG_TAG, "Trying to parse String back to Date format \n\n" + mSimpleDateFormat.parse(localDate).toString() + "\n\n");
                    mCurrentCalendar.setTime(mSimpleDateFormat.parse(localDate));
                    Log.d(LOG_TAG, mCurrentCalendar.getTime().toString() + "\n\n");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*
        I use getActivity().getMenuInflater() in some places instead of inflater parameter.
         */
        inflater.inflate(R.menu.options_menu_documents_fragment, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search_documents_fragment);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        search(searchView);

    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_TAG, "\n\n New text is: " + newText);

                // in case search menu action is clicked before adapter is initialized.
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pick_date_documents_fragment:
                showDatePickerDialog();
                
                //showDatePickerDialog(this.getView());
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                mCurrentCalendar.get(Calendar.YEAR),
                mCurrentCalendar.get(Calendar.MONTH),
                mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the DrawerDocumentFragment class");

        /*
        Here we are using the same layout as with DrawerMachineFragment class: fragment_nav_drawer_machine
         */
        View view = inflater.inflate(R.layout.fragment_nav_drawer_machine, container, false);

        textView = view.findViewById(R.id.text_view_for_machine);


        // init SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.coffee_machine_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        /*
        Recycler View
         */

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.coffee_machines_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

        //getCoffeeDocsFromAPI();

         /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.hide();

        // implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getCoffeeDocsFromAPI();


                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;

    }

    private void getCoffeeDocsFromAPI() {

        // CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
        CoffeeService service = CoffeeApp.AppInstance.getRetrofitService();

        mDateString = formatDate();
        Call<CoffeeDocList> callCoffeeDocs = service.listCoffeeDocs(mDateString, "1610007800", "dh:iso8601");


        Log.d(LOG_TAG, "\n\nThis is call docs list \n" + callCoffeeDocs.toString());

        callCoffeeDocs.enqueue(new Callback<CoffeeDocList>() {
            @Override
            public void onResponse(Call<CoffeeDocList> call, Response<CoffeeDocList> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");
                    changeViewVisibility(textView, View.GONE);

                    List<CoffeeDoc> coffeeDocs = response.body().getResult();

                    Collections.sort(coffeeDocs);
                    adapter = new MyCoffeeDocRecyclerViewAdapter(coffeeDocs);
                    adapter.setmListener(DrawerDocumentFragment.this);
                    DrawerDocumentFragment.this.recyclerView.setAdapter(adapter);
                } else {
                    changeViewVisibility(textView, View.VISIBLE);
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<CoffeeDocList> call, Throwable t) {
                call.cancel();
                Log.d(LOG_TAG, "Call url is " + call.request().url());
                Log.d(LOG_TAG, "The cause for error" + t.getCause());
                changeViewVisibility(textView, View.VISIBLE);
                textView.setText(String.format(getString(R.string.nav_drawer_machine_text_view_api_failure_text), call.toString(), t.getStackTrace()));
            }
        });

    }

    private String formatDate() {
        return mSimpleDateFormat.format(mCurrentCalendar.getTime());
    }

    private void changeViewVisibility(View view, int visible) {

        view.setVisibility(visible);

    }


    @Override
    public void onClick(CoffeeDoc value) {
        Log.d(LOG_TAG, "Something was clicked" + value.toString());
        Intent intent = new Intent(this.getActivity(), DocGoodsActivity.class);
        intent.putExtra(DOCUMENT_ID, value.getDocsID());
        intent.putExtra(CORRESPOND_ACC, value.getCorespondAcc());
        intent.putExtra(WAYBILL_NUM, value.getWaybillNum());
        intent.putExtra(NUMBER_IN, value.getNumberIn());
        intent.putExtra(D_DATE, mSimpleDateFormat.format(value.getdDate()));
        startActivity(intent);
    }

    @Override
    public void onLongClick(CoffeeDoc value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        mCurrentCalendar.set(year, month, dayOfMonth);
        getCoffeeDocsFromAPI();

    }









/*

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private Calendar mDateCalendar = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            //final Calendar c = Calendar.getInstance();
            int year = mDateCalendar.get(Calendar.YEAR);
            int month = mDateCalendar.get(Calendar.MONTH);
            int day = mDateCalendar.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mDateCalendar.set(year, month, day);
        }

        public Calendar getmDateCalendar() {
            return mDateCalendar;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        mCurrentCalendar = ((DatePickerFragment) newFragment).getmDateCalendar();
    }
*/


}
