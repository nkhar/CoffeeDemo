package android.apex.ge.coffee;

/*
This class should display items of chosen CoffeeDoc item, which was chosen in DocumentFragment class
of MainActivity.
 */

import android.apex.ge.coffee.Fragments.DocumentFragment;
import android.apex.ge.coffee.Fragments.MyProducedRecyclerViewAdapter;
import android.apex.ge.coffee.Fragments.RecyclerViewListAdapter;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.DocGoods;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.apex.ge.coffee.UserInterface.SimpleDividerItemDecoration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocGoodsActivity extends AppCompatActivity {

    private final String LOG_TAG = "DocGoodsActivity";

    RecyclerView recyclerView;
    RecyclerViewListAdapter adapter;

    private TextView textView;

    private final int mColumnCount = 1;

    private String docId;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "we are in the onCreate method of DocGoodsActivity activity");
        super.onCreate(savedInstanceState);
           /*
        Here we are using the same layout as with MachineFragment class: fragment_machine
         */
        setContentView(R.layout.fragment_machine);

        docId = getIntent().getStringExtra(DocumentFragment.DOCUMENT_ID);

        // init SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.cofee_machine_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        /*
        Recycler View
         */

        recyclerView = findViewById(R.id.coffee_machines_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

         /*
        Floating Action Button
         */
        FloatingActionButton fab = findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocGoodsFromAPI(docId);
            }
        });

        // implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getDocGoodsFromAPI(docId);


                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }


    private void getDocGoodsFromAPI(String id) {
        CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

        Call<DocGoods> callGoodsDocs = service.listDocGoods(id);

        callGoodsDocs.enqueue(new Callback<DocGoods>() {
            @Override
            public void onResponse(Call<DocGoods> call, Response<DocGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");
                    List<ProductData> goodsDocs = response.body().getResult();

                    Collections.sort(goodsDocs);
                    adapter = new MyProducedRecyclerViewAdapter(goodsDocs);
                    /*adapter.setmListener(DocGoodsActivity.this);*/
                    DocGoodsActivity.this.recyclerView.setAdapter(adapter);

                } else {
                    Log.d(LOG_TAG, response.code() + "");
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<DocGoods> call, Throwable t) {
                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });
    }


}
