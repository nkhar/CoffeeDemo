package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.DocGoodsActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeDocList;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.Model.CoffeeDoc;
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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nika on 26/03/2018.
 * This class is used to display Documents item from NavigationDrawer
 */

public class DrawerDocumentFragment extends Fragment implements ILibObjectCrud<CoffeeDoc> {

    protected final String LOG_TAG = "DrawerDocumentFragment";

    public static final String DOCUMENT_ID = "DOCUMENT_ID";

    RecyclerView recyclerView;
    RecyclerViewListAdapter adapter;

    private final int mColumnCount = 1;

    private TextView textView;

    private SwipeRefreshLayout swipeRefreshLayout;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DrawerDocumentFragment() {
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


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

         /*
        Floating Action Button
         */
        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoffeeDocsFromAPI();
            }
        });

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

        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        Calendar calendar = new GregorianCalendar(2018, 03, 18);
        System.out.println(sdf.format(calendar.getTime()));

        Call<CoffeeDocList> callCoffeeDocs = service.listCoffeeDocs("2018-04-19", "1610007800", "dh:iso8601");
        Log.d(LOG_TAG, "\n\nThis is call docs list \n" + callCoffeeDocs.toString());

        callCoffeeDocs.enqueue(new Callback<CoffeeDocList>() {
            @Override
            public void onResponse(Call<CoffeeDocList> call, Response<CoffeeDocList> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");
                    List<CoffeeDoc> coffeeDocs = response.body().getResult();

                    Collections.sort(coffeeDocs);
                    adapter = new MyCoffeeDocRecyclerViewAdapter(coffeeDocs);
                    adapter.setmListener(DrawerDocumentFragment.this);
                    DrawerDocumentFragment.this.recyclerView.setAdapter(adapter);
                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<CoffeeDocList> call, Throwable t) {
                call.cancel();
                Log.d(LOG_TAG, "Call url is " + call.request().url());
                Log.d(LOG_TAG, "The cause for error" + t.getCause());
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });

    }


    @Override
    public void onClick(CoffeeDoc value) {
        Log.d(LOG_TAG, "Something was clicked" + value.toString());
        Intent intent = new Intent(this.getActivity(), DocGoodsActivity.class);
        intent.putExtra(DOCUMENT_ID, value.getDocsID());
        startActivity(intent);
    }

    @Override
    public void onLongClick(CoffeeDoc value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }

}
