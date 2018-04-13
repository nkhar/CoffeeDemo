package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nika on 23/03/2018.
 * This class is an adapter for the list of items in Produced Produceds tab of
 * CoffeeMachineDetailActivity.
 */

public class MyProducedRecyclerViewAdapter extends RecyclerViewListAdapter<MyProducedRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MyProdRecAdapter";
    private Map<String, ProdTransactionData> prodTransactionDataHashMap = new HashMap<>();

    public MyProducedRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyProducedRecyclerViewAdapter   constructor   ");
    }

    public void updateHashMap(HashMap<String, ProdTransactionData> hashMapData) {
        prodTransactionDataHashMap = hashMapData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coffee_produced_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final ProductData value) {

        if (value == null) {
            return;
        }

        holder.producedListResult = value;


        if (prodTransactionDataHashMap.get(value.getProdPPID()) != null) {
            Log.d(LOG_TAG, prodTransactionDataHashMap.get(value.getProdPPID()).getProdPPID());
            holder.mProducedQuantity1TextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getCurICount()));
            holder.mProducedQuantity2TextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getCurSCount()));
        } else {
            holder.mProducedQuantity1TextView.setText("");
            holder.mProducedQuantity2TextView.setText("");
        }


        holder.mProducedBCodeTextView.setText(value.getBCode());
        holder.mProducedNameTextView.setText(value.getName());
        final ILibObjectCrud listener = getmListener();
        if (listener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(value);
                }
            });
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(value);
                    return true;
                }
            });
        }

    }


    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final View mView;
        public final TextView mProducedBCodeTextView;
        public final TextView mProducedNameTextView;
        public final TextView mProducedQuantity1TextView;
        public final TextView mProducedQuantity2TextView;


        public ProductData producedListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mProducedBCodeTextView = itemView.findViewById(R.id.page_produced_BCode);
            mProducedNameTextView = itemView.findViewById(R.id.page_produced_Name);
            mProducedQuantity1TextView = itemView.findViewById(R.id.page_produced_quantity1);
            mProducedQuantity2TextView = itemView.findViewById(R.id.page_produced_quantity2);

        }

    }

}
