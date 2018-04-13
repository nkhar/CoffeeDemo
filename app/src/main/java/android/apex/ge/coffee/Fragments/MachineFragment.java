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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
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

        textView = view.findViewById(R.id.text_view_for_machine);

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


        /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoffeeMachineListFromAPI();

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
