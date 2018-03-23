package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.CoffeeMachine;
import android.apex.ge.coffee.Retrofit.CoffeeMachineList;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
 * Created by Nika on 23/03/2018.
 * This class is used to display one of the tabs in CoffeeMachineDetailActivity class.
 *
 * @see android.apex.ge.coffee.CoffeeMachineDetailActivity
 */

public class PageRecViewFragment extends Fragment implements ILibObjectCrud {

    protected final String LOG_TAG = "PageRecViewFragment";
    RecyclerView recyclerView;
    RecyclerViewListAdapter adapter;

    private final int mColumnCount = 1;

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private TextView textView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PageRecViewFragment() {
    }

    public static PageRecViewFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageRecViewFragment fragment = new PageRecViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the PageRecViewFragment class");

        View view = inflater.inflate(R.layout.fragment_page_sale, container, false);
        // TextView
        textView = view.findViewById(R.id.text_view_page_sale);
        textView.setText("Fragment #" + mPage);

        // RecyclerView
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.page_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        setUpCorrectAdapter();

        //adapter = new MyCoffeeMachineRecyclerViewAdapter(new ArrayList<CoffeeMachine>());
        // Method to get a list of CoffeeMachines
        getCoffeeListFromAPI();


        adapter.setmListener(PageRecViewFragment.this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);

        return view;
    }

    private void setUpCorrectAdapter() {
        if (mPage == 1) {
            adapter = new MySaleRecyclerViewAdapter(new ArrayList<CoffeeMachine>());
        } else if (mPage == 2) {
            adapter = new MyProducedRecyclerViewAdapter(new ArrayList<CoffeeMachine>());
        } else if (mPage == 3) {
            adapter = new MyRawMaterialsRecyclerViewAdapter (new ArrayList<CoffeeMachine>());
        } else {
            adapter = new MyCoffeeMachineRecyclerViewAdapter(new ArrayList<CoffeeMachine>());
        }
    }

    private void getCoffeeListFromAPI() {

        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        Call<CoffeeMachineList> callCoffees = service.listCoffeeMachines(null);

        callCoffees.enqueue(new Callback<CoffeeMachineList>() {


            @Override
            public void onResponse(Call<CoffeeMachineList> call, Response<CoffeeMachineList> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<CoffeeMachine> coffee = response.body().getResult();
                    // Here is the real list
                    adapter.updateList(coffee);

                    textView.setText(textView.getText() + " \n\n " + coffee.size() + "\n\n");
                } else
                    textView.setText(response.toString());
                textView.setMovementMethod(new ScrollingMovementMethod());
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
        Log.d(LOG_TAG, "Something was Clicked" + value.toString());

    }

    @Override
    public void onLongClick(Object value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }
}
