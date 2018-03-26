package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeMachineList;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.Model.AccountInfo;
import android.apex.ge.coffee.Retrofit.Model.CoffeeMachine;
import android.apex.ge.coffee.Retrofit.PreOrderAccounts;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nika on 26/03/2018.
 * This class should display one of the fragments chosen from navigation drawer.
 */

public class PreOrderFragment extends Fragment implements ILibObjectCrud{

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
        recyclerView = view.findViewById(R.id.coffee_machines_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }


        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);

         /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

                Call<PreOrderAccounts> preOrderAccounts = service.listPreOrderAccounts();




                preOrderAccounts.enqueue(new Callback<PreOrderAccounts>() {
                    @Override
                    public void onResponse(Call<PreOrderAccounts> call, Response<PreOrderAccounts> response) {
                        if(response.isSuccessful()) {
                            Log.d(LOG_TAG, response.code() + "");

                           /* String displayCoffeeResponse = "";*/
                            List<AccountInfo> accounts = response.body().getResult();
                            adapter = new MyPreOrderAccountsRecyclerViewAdapter(accounts);
                            adapter.setmListener(PreOrderFragment.this);
                            PreOrderFragment.this.recyclerView.setAdapter(adapter);
                           /* displayCoffeeResponse += "\n    " + kofe.size() + " \n";*/

                           /* for (CoffeeMachineList.Result coffeeResult : kofe) {
                                displayCoffeeResponse += coffeeResult.toString();
//                                Log.d(LOG_TAG, coffeeResult.getAcc().toString());
                            }*/
                            textView.setText(adapter.getItemCount() + " \n\n " + accounts.size() + "\n\n");
                        }else
                            textView.setText(response.toString());
                        textView.setMovementMethod(new ScrollingMovementMethod());
                    }

                    @Override
                    public void onFailure(Call<PreOrderAccounts> call, Throwable t) {
                        call.cancel();
                        textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
                    }
                });
            }
        });

        return view;

    }


    @Override
    public void onClick(Object value) {
        Log.d(LOG_TAG, "Something was clicked" + value.toString());
    }

    @Override
    public void onLongClick(Object value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }
}
