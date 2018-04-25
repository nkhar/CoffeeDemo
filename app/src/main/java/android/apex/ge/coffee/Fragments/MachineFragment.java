package android.apex.ge.coffee.Fragments;


import android.apex.ge.coffee.CoffeeMachineDetailActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeMachineList;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.Model.CoffeeMachine;
import android.apex.ge.coffee.UserInterface.SimpleDividerItemDecoration;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Nika on 14/03/2018.
 * This is a fragment class that is displayed in navigation drawer when corresponding item is
 * clicked in the menu.
 */

public class MachineFragment extends Fragment implements ILibObjectCrud {

    protected final String LOG_TAG = "MachineFragment";

    RecyclerView recyclerView;
    MyCoffeeMachineRecyclerViewAdapter adapter;

    private final int mColumnCount = 1;

    private TextView textView;

    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MachineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate( R.menu.options_menu, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        search(searchView);

    }

/*    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        search(searchView);
    }*/

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_TAG, "\n\n New text is: " + newText);

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the MachineFragment class");

        View view = inflater.inflate(R.layout.fragment_nav_drawer_machine, container, false);

        textView = view.findViewById(R.id.text_view_for_machine);

        // init SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.cofee_machine_swipe_refresh_layout);
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


        /*
        Add ItemDecoration Using SimpleDividerItemDecoration class from UserInterface package.
         */
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        getCoffeeMachineListFromAPI();


        /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.hide();


        // implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getCoffeeMachineListFromAPI();


                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private void getCoffeeMachineListFromAPI() {
        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        Call<CoffeeMachineList> coffees = service.listCoffeeMachines("1610003000");


        coffees.enqueue(new Callback<CoffeeMachineList>() {
            @Override
            public void onResponse(Call<CoffeeMachineList> call, Response<CoffeeMachineList> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");


                    List<CoffeeMachine> kofe = response.body().getResult();
                    /*
                    Sorted the CoffeeMachine list.
                     */
                    Collections.sort(kofe);
                    adapter = new MyCoffeeMachineRecyclerViewAdapter(kofe);
                    adapter.setmListener(MachineFragment.this);
                    MachineFragment.this.recyclerView.setAdapter(adapter);
                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<CoffeeMachineList> call, Throwable t) {
                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });
    }

    @Override
    public void onClick(Object value) {
        Log.d(LOG_TAG, "Something was clicked" + value.toString());
        Intent intent = new Intent(getActivity(), CoffeeMachineDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLongClick(Object value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }
}
