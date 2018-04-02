package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.apex.ge.coffee.Retrofit.ProducedGoods;
import android.apex.ge.coffee.Retrofit.RawMaterials;
import android.apex.ge.coffee.Retrofit.SaleGoods;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class PageRecViewFragment extends Fragment implements ILibObjectCrud<ProductData>, EditNumberDialogFragment.EditNumberDialogListener {

    protected final String LOG_TAG = "PageRecViewFragment";
    RecyclerView recyclerView;
    RecyclerViewListAdapter adapter;

    private final int mColumnCount = 1;

    public static final String ARG_PAGE = "ARG_PAGE";

    private final int REQUEST_CODE_DIALOG_FRAGMENT = 300;

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

        CoffeeService coffeeService = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
        setUpCorrectAdapter(coffeeService);


        adapter.setmListener(PageRecViewFragment.this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(localItemDecoration);

        return view;
    }

    private void setUpCorrectAdapter(CoffeeService coffeeService) {
        if (mPage == 1) {
            adapter = new MySaleRecyclerViewAdapter(new ArrayList<ProductData>());
            getSaleGoodsListFromAPI(coffeeService);
        } else if (mPage == 2) {
            adapter = new MyProducedRecyclerViewAdapter(new ArrayList<ProductData>());
            getProducedGoodsListFromAPI(coffeeService);
        } else if (mPage == 3) {
            adapter = new MyRawMaterialsRecyclerViewAdapter(new ArrayList<ProductData>());
            getRawMaterialsListFromAPI(coffeeService);
        } else {
            return;

        }
    }

    private void getRawMaterialsListFromAPI(CoffeeService service) {
        Call<RawMaterials> callRawMaterials = service.listRawMaterials("1610003000", "1610007800");

        callRawMaterials.enqueue(new Callback<RawMaterials>() {
            @Override
            public void onResponse(Call<RawMaterials> call, Response<RawMaterials> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> rawMaterials = response.body().getResult();
                    // Here is the real list
                    adapter.updateList(rawMaterials);

                    textView.setText(textView.getText() + " \n\n " + rawMaterials.size() + "\n\n");
                } else
                    textView.setText(response.toString());
                textView.setMovementMethod(new ScrollingMovementMethod());

            }

            @Override
            public void onFailure(Call<RawMaterials> call, Throwable t) {
                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });
    }

    private void getProducedGoodsListFromAPI(CoffeeService service) {
        Call<ProducedGoods> callProducedGoods = service.listProducedGoods("1610003000", "1610007800");

        callProducedGoods.enqueue(new Callback<ProducedGoods>() {
            @Override
            public void onResponse(Call<ProducedGoods> call, Response<ProducedGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> produced = response.body().getResult();
                    // Here is the real list
                    adapter.updateList(produced);

                    textView.setText(textView.getText() + " \n\n " + produced.size() + "\n\n");
                } else
                    textView.setText(response.toString());
                textView.setMovementMethod(new ScrollingMovementMethod());
            }

            @Override
            public void onFailure(Call<ProducedGoods> call, Throwable t) {

                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());

            }
        });
    }

    private void getSaleGoodsListFromAPI(CoffeeService service) {
        Call<SaleGoods> callSaleGoods = service.listSaleGoods("1610003000", "1610007800");

        callSaleGoods.enqueue(new Callback<SaleGoods>() {
            @Override
            public void onResponse(Call<SaleGoods> call, Response<SaleGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> sales = response.body().getResult();
                    // Here is the real list
                    adapter.updateList(sales);

                    textView.setText(textView.getText() + " \n\n " + sales.size() + "\n\n");
                } else
                    textView.setText(response.toString());
                textView.setMovementMethod(new ScrollingMovementMethod());
            }

            @Override
            public void onFailure(Call<SaleGoods> call, Throwable t) {

                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());

            }
        });
    }


    @Override
    public void onClick(ProductData value) {
        Log.d(LOG_TAG, "Something was Clicked" + value.toString());
        showEditNumberDialog();
    }

    @Override
    public void onLongClick(ProductData value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }

    // Call this method to launch the edit dialog
    private void showEditNumberDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditNumberDialogFragment editNumberDialogFragment = EditNumberDialogFragment.newInstance("Number is here Title");
        // SETS the target fragment for use later when sending results
        editNumberDialogFragment.setTargetFragment(PageRecViewFragment.this, REQUEST_CODE_DIALOG_FRAGMENT);
        editNumberDialogFragment.show(fm, "fragment_edit_name");
    }
    @Override
    public void onFinishEditDialog(String inputNumber1, String inputNumber2) {
        Log.d(LOG_TAG, "Numbers entered were: \n" + Integer.parseInt(inputNumber1) + "\n" + Integer.parseInt(inputNumber2));

    }
}
