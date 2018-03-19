package android.apex.ge.coffee.Fragments;


import android.apex.ge.coffee.CoffeeMachineDetailActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.RetrofitClient;
import android.apex.ge.coffee.Retrofit.CoffeeMachine;
import android.apex.ge.coffee.Retrofit.CoffeeService;
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

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Nika on 14/03/2018.
 * This is a fragment class that is displayed in navigation drawer when corresponding item is
 * clicked in the menu.
 */

public class MachineFragment extends Fragment implements ILibObjectCrud{

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
        textView.setText("This is the machine");


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
        /*adapter = new MyCoffeeMachineRecyclerViewAdapter();
        adapter.setmListener(this);
        recyclerView.setAdapter(adapter);*/


        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);


        /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new BasicAuthInterceptor("rpl", "9"))
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://support.apex.ge:83")
                        .client(client)
                        .build();*/

                CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

                Call <CoffeeMachine> coffees = service.listCoffeeMachines();




                coffees.enqueue(new Callback<CoffeeMachine>() {
                    @Override
                    public void onResponse(Call<CoffeeMachine> call, Response<CoffeeMachine> response) {
                        if(response.isSuccessful()) {
                            Log.d(LOG_TAG, response.code() + "");

                           /* String displayCoffeeResponse = "";*/
                            List<CoffeeMachine.Result> kofe = response.body().getResult();
                            adapter = new MyCoffeeMachineRecyclerViewAdapter(kofe);
                            adapter.setmListener(MachineFragment.this);
                            MachineFragment.this.recyclerView.setAdapter(adapter);
                           /* displayCoffeeResponse += "\n    " + kofe.size() + " \n";*/

                           /* for (CoffeeMachine.Result coffeeResult : kofe) {
                                displayCoffeeResponse += coffeeResult.toString();
//                                Log.d(LOG_TAG, coffeeResult.getAcc().toString());
                            }*/
                            textView.setText(adapter.getItemCount() + " \n\n " + kofe.size() + "\n\n");
                        }else
                            textView.setText(response.toString());
                        textView.setMovementMethod(new ScrollingMovementMethod());
                    }

                    @Override
                    public void onFailure(Call<CoffeeMachine> call, Throwable t) {
                        call.cancel();
                    }
                });

/*
                Call<List<Repo>> repos = service.listRepos("nkhar");


                repos.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        Log.d(LOG_TAG, response.code() + "");

                        String displayResponse = "";

                        List<Repo> repo = response.body();
                        displayResponse += "\n    " + repo.size() + " \n";
                        for (Repo rep : repo) {
                            displayResponse += rep.toString();
                            Log.d(LOG_TAG, rep.getDefaultBranch().toString());

                        }
                        textView.setText(displayResponse);
                        textView.setMovementMethod(new ScrollingMovementMethod());

                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        call.cancel();
                    }
                });*/
            }
        });


        return view;
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
