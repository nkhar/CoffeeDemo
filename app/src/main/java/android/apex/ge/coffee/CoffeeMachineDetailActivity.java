package android.apex.ge.coffee;

import android.apex.ge.coffee.Retrofit.CoffeeService;
import android.apex.ge.coffee.Retrofit.ProducedGoods;
import android.apex.ge.coffee.Retrofit.ProductData;
import android.apex.ge.coffee.Retrofit.RetrofitClient;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoffeeMachineDetailActivity extends AppCompatActivity {

    protected final String LOG_TAG= "CoffeeMachDetAct";


    private Button mButtonProducedGoods;
    private TextView mTextViewProducedGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "We are in onCreate method");
        setContentView(R.layout.activity_coffee_machine_detail);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextViewProducedGoods = findViewById(R.id.producedGoodsTextView);
        mTextViewProducedGoods.setMovementMethod(new ScrollingMovementMethod());

        mButtonProducedGoods = findViewById(R.id.buttonProducedGoods);
        mButtonProducedGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffeeService service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);

                Call<ProducedGoods> producedGoods = service.listProducedGoods("1011111111", "2011111111");


                producedGoods.enqueue(new Callback<ProducedGoods>() {
                    @Override
                    public void onResponse(Call<ProducedGoods> call, Response<ProducedGoods> response) {
                        if(response.isSuccessful()) {
                            Log.d(LOG_TAG, response.code() + "");

                           String displayCoffeeResponse = "";
                            List<ProductData> producedGoodsProductData = response.body().getResult();

                           displayCoffeeResponse += "\n    " + producedGoodsProductData.size() + " \n";

                           for (ProductData prodData : producedGoodsProductData) {
                                displayCoffeeResponse += prodData.toString();
                            }
                            mTextViewProducedGoods.setText(displayCoffeeResponse);
                            /*mTextViewProducedGoods.setText(" \n\n " + producedGoodsProductData.size() + "\n\n");*/
                        }else
                            mTextViewProducedGoods.setText(response.toString());
                    }

                    @Override
                    public void onFailure(Call<ProducedGoods> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
    }

}
