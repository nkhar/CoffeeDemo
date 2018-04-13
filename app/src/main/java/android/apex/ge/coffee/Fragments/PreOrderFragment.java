package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.PreOrderActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.Model.AccountInfo;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.apex.ge.coffee.Retrofit.PreOrderAccounts;
import android.apex.ge.coffee.Retrofit.PreorderGoods;
import android.apex.ge.coffee.UserInterface.SimpleDividerItemDecoration;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nika on 26/03/2018.
 * This class should display one of the fragments chosen from navigation drawer.
 */

public class PreOrderFragment extends Fragment implements ILibObjectCrud {

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

        View view = inflater.inflate(R.layout.fragment_pre_order, container, false);

        textView = view.findViewById(R.id.text_view_for_pre_order);
        textView.setText("This is PreOrders");

        /*
        Recycler View
         */

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.pre_order_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

         /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_pre_order);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getPreOrderAccountsFromAPI();
                //getPreOrderGoodsFromAPI();
                Intent intent = new Intent(PreOrderFragment.this.getActivity(), PreOrderActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }

    private void getPreOrderAccountsFromAPI() {
        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        Call<PreOrderAccounts> preOrderAccounts = service.listPreOrderAccounts();


        preOrderAccounts.enqueue(new Callback<PreOrderAccounts>() {
            @Override
            public void onResponse(Call<PreOrderAccounts> call, Response<PreOrderAccounts> response) {
                if (response.isSuccessful()) {
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
                } else
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

    private void getPreOrderGoodsFromAPI() {
        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        Calendar calendar = new GregorianCalendar(2018, 2, 26);

                /*
                WarehouseAcc: 1610000100
                CoffeeAcc: 1610007800
                VanAcc: 1610007800
                 */
        Call<PreorderGoods> preOrderGoods = service.listPreorderGoods("1610000100", "1610007800", sdf.format(calendar.getTime()));


        preOrderGoods.enqueue(new Callback<PreorderGoods>() {
            @Override
            public void onResponse(Call<PreorderGoods> call, Response<PreorderGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> goods = response.body().getResult();
                            /*
                            This is just for TESTING
                            DON'T FORGET
                            TO
                            DELETE THIS
                            AND USE CORRECT ADAPTER
                             */
                    adapter = new MyProducedRecyclerViewAdapter(goods);
                    adapter.setmListener(PreOrderFragment.this);
                    PreOrderFragment.this.recyclerView.setAdapter(adapter);
                    textView.setText(adapter.getItemCount() + " \n\n " + goods.size() + "\n\n");
                } else
                    textView.setText(response.toString());
                textView.setMovementMethod(new ScrollingMovementMethod());
            }

            @Override
            public void onFailure(Call<PreorderGoods> call, Throwable t) {
                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });

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
