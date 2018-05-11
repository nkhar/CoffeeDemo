package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.CoffeeApp;
import android.apex.ge.coffee.CoffeeMachineDetailActivity;
import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.apex.ge.coffee.Retrofit.ProducedGoods;
import android.apex.ge.coffee.Retrofit.RawMaterials;
import android.apex.ge.coffee.Retrofit.SaleGoods;
import android.apex.ge.coffee.UserInterface.SimpleDividerItemDecoration;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

public class VPPageRecViewFragment extends Fragment implements ILibObjectCrud<ProductData>, EditNumberDialogFragment.EditNumberDialogListener {

    protected final String LOG_TAG = "VPPageRecViewFragment";
    RecyclerView recyclerView;
    RecyclerViewAdapterWithFilter adapter;

    private List<ProdTransactionData> listProdTransactionData;
    private HashMap<String, ProdTransactionData> prodPPIDProdTransactionDataHashMap = new HashMap<>();

    private final int mColumnCount = 1;

    private static final String ARG_PAGE = "ARG_PAGE";
    private final int REQUEST_CODE_DIALOG_FRAGMENT = 300;

    private int mPage;
    private TextView textView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VPPageRecViewFragment() {
    }

    public static VPPageRecViewFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        VPPageRecViewFragment fragment = new VPPageRecViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        search(searchView);

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
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the VPPageRecViewFragment class");

        View view = inflater.inflate(R.layout.fragment_vp_page_general, container, false);
        // TextView
        textView = view.findViewById(R.id.text_view_page_sale);

        // RecyclerView
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.page_recycler_view);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        // CoffeeService coffeeService = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
        CoffeeService coffeeService = CoffeeApp.AppInstance.getRetrofitService();
        setUpCorrectAdapter(coffeeService);


        adapter.setmListener(VPPageRecViewFragment.this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));

        return view;
    }

    private void setUpCorrectAdapter(CoffeeService coffeeService) {
        if (mPage == 1) {
            adapter = new MySaleRecyclerViewAdapter(new ArrayList<ProductData>());
            getSaleGoodsListFromAPI(coffeeService);
            /*
            listProdTransactionData should hold different lists depending on the viewpager tab,
             which is indicated by variable mPage in this example.
             */
            listProdTransactionData = ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().getSaleAndTransit();
            populateSaleHashMap();

        } else if (mPage == 2) {
            adapter = new MyProducedRecyclerViewAdapter(new ArrayList<ProductData>());
            getProducedGoodsListFromAPI(coffeeService);

            listProdTransactionData = ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().getSaleProduced();
            populateSaleHashMap();

        } else if (mPage == 3) {
            adapter = new MyRawMaterialsRecyclerViewAdapter(new ArrayList<ProductData>());
            getRawMaterialsListFromAPI(coffeeService);

            listProdTransactionData = ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().getTransitRawMaterials();
            populateSaleHashMap();
        } else {
            /* return;*/

        }
    }


    private void getRawMaterialsListFromAPI(CoffeeService service) {
        Call<RawMaterials> callRawMaterials = service.listRawMaterials("1610003000", CoffeeApp.AppInstance.getVanAccountFromApp());

        callRawMaterials.enqueue(new Callback<RawMaterials>() {
            @Override
            public void onResponse(Call<RawMaterials> call, Response<RawMaterials> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> rawMaterials = response.body().getResult();

                    /*
                     sorting the list
                      */
                    Collections.sort(rawMaterials);

                    // Here is the real list
                    adapter.updateList(rawMaterials);

                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }

            }

            @Override
            public void onFailure(Call<RawMaterials> call, Throwable t) {
                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());
            }
        });
    }

    private void getProducedGoodsListFromAPI(CoffeeService service) {
        Call<ProducedGoods> callProducedGoods = service.listProducedGoods("1610003000", CoffeeApp.AppInstance.getVanAccountFromApp());

        callProducedGoods.enqueue(new Callback<ProducedGoods>() {
            @Override
            public void onResponse(Call<ProducedGoods> call, Response<ProducedGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> produced = response.body().getResult();

                    /*
                     sorting the list
                      */
                    Collections.sort(produced);

                    // Here is the real list
                    adapter.updateList(produced);

                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<ProducedGoods> call, Throwable t) {

                call.cancel();
                textView.setText("Can't Establish a Connection to the Server\n\n" + call.toString() + "\n\n" + t.getStackTrace());

            }
        });
    }

    private void getSaleGoodsListFromAPI(CoffeeService service) {
        Call<SaleGoods> callSaleGoods = service.listSaleGoods("1610003000", CoffeeApp.AppInstance.getVanAccountFromApp());

        callSaleGoods.enqueue(new Callback<SaleGoods>() {
            @Override
            public void onResponse(Call<SaleGoods> call, Response<SaleGoods> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                    List<ProductData> sales = response.body().getResult();
                    /*
                     sorting the list
                      */
                    Collections.sort(sales);

                    // Here is the real list
                    adapter.updateList(sales);

                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<SaleGoods> call, Throwable t) {

                call.cancel();
                textView.setText(String.format(getString(R.string.nav_drawer_machine_text_view_api_failure_text), call.toString(), t.getStackTrace()));

            }
        });
    }


    @Override
    public void onClick(ProductData value) {
        Log.d(LOG_TAG, "Something was Clicked" + value.toString());
        showEditNumberDialog(value.getProdPPID());
    }

    @Override
    public void onLongClick(ProductData value) {
        Log.d(LOG_TAG, "Something was longClicked" + value.toString());
    }

    // Call this method to launch the edit dialog
    private void showEditNumberDialog(String prodPPID) {

        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            EditNumberDialogFragment editNumberDialogFragment = null;

            // Sending values to present in the EditText of the DialogFragment.
            if (prodPPIDProdTransactionDataHashMap.get(prodPPID) != null) {
                Float inputFloat1 = prodPPIDProdTransactionDataHashMap.get(prodPPID).getCurICount();
                Float inputFloat2 = prodPPIDProdTransactionDataHashMap.get(prodPPID).getCurSCount();
                String inputStr1 = String.valueOf(inputFloat1);
                String inputStr2 = String.valueOf(inputFloat2);
                editNumberDialogFragment = EditNumberDialogFragment.newInstance("Number is here Title", prodPPID, inputStr1, inputStr2);
            } else {
                editNumberDialogFragment = EditNumberDialogFragment.newInstance("Number is here Title", prodPPID);
            }


            // SETS the target fragment for use later when sending results
            editNumberDialogFragment.setTargetFragment(VPPageRecViewFragment.this, REQUEST_CODE_DIALOG_FRAGMENT);
            editNumberDialogFragment.show(fm, "fragment_edit_name");
        }
    }

    @Override
    public void onFinishEditDialog(String inputNumber1, String inputNumber2, String prodPPID) {
        Log.d(LOG_TAG, "Numbers entered were: \n" + Double.parseDouble(inputNumber1) + "\n" + Double.parseDouble(inputNumber2));

        createAndAddProdTransactionData(inputNumber1, inputNumber2, prodPPID);

        updateHashMapOfCorrectAdapter();
        //((MySaleRecyclerViewAdapter) adapter).updateHashMap(prodPPIDProdTransactionDataHashMap);

    }


    private void createAndAddProdTransactionData(String inputNumber1, String inputNumber2, String prodPPID) {

        ProdTransactionData prodTransData = new ProdTransactionData();
        prodTransData.setProdPPID(prodPPID);
        prodTransData.setCurICount(Float.parseFloat(inputNumber1));
        prodTransData.setCurSCount(Float.parseFloat(inputNumber2));
        listProdTransactionData.add(prodTransData);

        setCorrectSaveCoffeeStatsProdTransactionDataArrayList();


        prodPPIDProdTransactionDataHashMap.put(prodPPID, prodTransData);
        Log.d(LOG_TAG, "\nwe are in createAndAddProdTransactionData the size of hashMap is: " + prodPPIDProdTransactionDataHashMap.size() + "\n");
    }

    private void populateSaleHashMap() {
        for (ProdTransactionData prodData : listProdTransactionData) {
            prodPPIDProdTransactionDataHashMap.put(prodData.getProdPPID(), prodData);
        }

        updateHashMapOfCorrectAdapter();

    }

    private void setCorrectSaveCoffeeStatsProdTransactionDataArrayList() {

        if ((getActivity() != null)) {


            switch (mPage) {
                case 1:
                    ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().setSaleAndTransit(listProdTransactionData);
                    break;
                case 2:
                    ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().setSaleProduced(listProdTransactionData);
                    break;
                case 3:
                    ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().setTransitRawMaterials(listProdTransactionData);
                    break;
                default:
                    Log.d(LOG_TAG, "This is one strange arrayList setter switch statement");
                    break;
            }

        }


        /* ((CoffeeMachineDetailActivity) getActivity()).getSaveCoffeeStats().setSaleAndTransit(new ArrayList<ProdTransactionData>(prodPPIDProdTransactionDataHashMap.values()));*/
    }

    private void updateHashMapOfCorrectAdapter() {
        switch (mPage) {
            case 1:
                ((MySaleRecyclerViewAdapter) adapter).updateHashMap(prodPPIDProdTransactionDataHashMap);
                break;
            case 2:
                ((MyProducedRecyclerViewAdapter) adapter).updateHashMap(prodPPIDProdTransactionDataHashMap);
                break;
            case 3:
                ((MyRawMaterialsRecyclerViewAdapter) adapter).updateHashMap(prodPPIDProdTransactionDataHashMap);
                break;
            default:
                Log.d(LOG_TAG, "This is one strange switch statement");
                break;
        }

    }


}
