package android.apex.ge.coffee;

/*
This class should display items of chosen CoffeeDoc item, which was chosen in DrawerDocumentFragment class
of MainActivity.
 */

import android.apex.ge.coffee.Fragments.DrawerDocumentFragment;
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
import android.support.v7.widget.SearchView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    MyProducedRecyclerViewAdapter adapter;

    private TextView textViewRefresh;
    private TextView textViewDocId;
    private TextView textViewCorrespondAcc;
    private TextView textViewWaybillNumOrNumberIn;


    private final int mColumnCount = 1;

    private String docId;
    private String correspondAcc;
    private String waybillNum;
    private String numberIn;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "we are in the onCreate method of DocGoodsActivity activity");
        super.onCreate(savedInstanceState);
           /*
        Here we are using the same layout as with DrawerMachineFragment class: fragment_nav_drawer_machine
         */
        setContentView(R.layout.activity_doc_goods);

        docId = getIntent().getStringExtra(DrawerDocumentFragment.DOCUMENT_ID);
        correspondAcc = getIntent().getStringExtra(DrawerDocumentFragment.CORRESPOND_ACC);
        waybillNum = getIntent().getStringExtra(DrawerDocumentFragment.WAYBILL_NUM);
        numberIn = getIntent().getStringExtra(DrawerDocumentFragment.NUMBER_IN);

        // init TextViewRefresh.
        textViewRefresh = findViewById(R.id.text_view_for_doc_goods_refresh);

        // init textViewDocId.
        textViewDocId = findViewById(R.id.text_view_for_doc_goods_doc_id);
        textViewDocId.setText(getString(R.string.doc_goods_doc_id) + docId);

        // init textViewCorrespondAcc.
        textViewCorrespondAcc = findViewById(R.id.text_view_for_doc_goods_correspond_acc);
        textViewCorrespondAcc.setText(getString(R.string.doc_goods_correspond_acc) + correspondAcc);

        // init textViewWaybillNum.
        textViewWaybillNumOrNumberIn = findViewById(R.id.text_view_for_doc_goods_waybill_num);
        if (waybillNum != null) {

            textViewWaybillNumOrNumberIn.setText(getString(R.string.doc_goods_waybill_num) + waybillNum);
        } else {
            textViewWaybillNumOrNumberIn.setText(getString(R.string.doc_goods_waybill_num) + numberIn);
        }





        // init SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_doc_goods_activity);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        /*
        Recycler View
         */

        recyclerView = findViewById(R.id.recycler_view_doc_goods_activity);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        //getDocGoodsFromAPI(docId);

         /*
        Floating Action Button
         */
        /*
        FloatingActionButton fab = findViewById(R.id.fab_fragment_machine);
        fab.hide();
        */

        // implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getDocGoodsFromAPI(docId);


                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        search(searchView);
        return true;
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
                if(adapter!=null) {
                    adapter.getFilter().filter(newText);
                }

                return true;
            }
        });
    }


    private void getDocGoodsFromAPI(String id) {
        // CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
        CoffeeService service = CoffeeApp.AppInstance.getRetrofitService();

        Call<DocGoods> callGoodsDocs = service.listDocGoods(id);

        callGoodsDocs.enqueue(new Callback<DocGoods>() {
            @Override
            public void onResponse(Call<DocGoods> call, Response<DocGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");
                    changeViewVisibility(textViewRefresh, View.GONE);


                    List<ProductData> goodsDocs = response.body().getResult();

                    Collections.sort(goodsDocs);
                    adapter = new MyProducedRecyclerViewAdapter(goodsDocs);
                    /*adapter.setmListener(DocGoodsActivity.this);*/
                    DocGoodsActivity.this.recyclerView.setAdapter(adapter);

                } else {
                    Log.d(LOG_TAG, response.code() + "");
                    changeViewVisibility(textViewRefresh, View.VISIBLE);
                    textViewRefresh.setText(response.toString());
                    textViewRefresh.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<DocGoods> call, Throwable t) {
                call.cancel();
                changeViewVisibility(textViewRefresh, View.VISIBLE);
                textViewRefresh.setText(String.format(getString(R.string.nav_drawer_machine_text_view_api_failure_text), call.toString(), t.getStackTrace()));
            }
        });
    }

    private void changeViewVisibility(View view, int visible) {

        view.setVisibility(visible);

    }


}
