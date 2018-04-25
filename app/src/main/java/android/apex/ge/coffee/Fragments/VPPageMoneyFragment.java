package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.apex.ge.coffee.Retrofit.LastCoffeeCupsCount;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nika on 26/03/2018.
 * This class is responsible for displaying fragment in the Money tab of the coffee machine.
 */

public class VPPageMoneyFragment extends Fragment implements ILibObjectCrud {

    protected final String LOG_TAG = "VPPageMoneyFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private TextView textView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VPPageMoneyFragment() {
    }


    public static VPPageMoneyFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        VPPageMoneyFragment fragment = new VPPageMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the VPPageMoneyFragment class");

        View view = inflater.inflate(R.layout.fragment_vp_page_money, container, false);

        // TextView
        textView = view.findViewById(R.id.text_view_page_money);

        CoffeeService coffeeService = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
        getLastCoffeeCupsCountFromAPI(coffeeService);

        return view;
    }

    private void getLastCoffeeCupsCountFromAPI(CoffeeService service) {

        Call<LastCoffeeCupsCount> callLastCoffeeCups = service.listLastCoffeeCupsCount("1610003000");

        callLastCoffeeCups.enqueue(new Callback<LastCoffeeCupsCount>() {
            @Override
            public void onResponse(Call<LastCoffeeCupsCount> call, Response<LastCoffeeCupsCount> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.code() + "");

                   int lastCupsCount = response.body().getResult();
                   textView.setText("Number of cups is: " + lastCupsCount);

                } else {
                    textView.setText(response.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }

            @Override
            public void onFailure(Call<LastCoffeeCupsCount> call, Throwable t) {
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
