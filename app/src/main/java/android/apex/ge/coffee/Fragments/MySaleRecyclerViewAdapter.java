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
 * This is adapter for recycler view to display list of items in Sale tab of
 * CoffeeMachineDetailActivity.
 */

public class MySaleRecyclerViewAdapter extends RecyclerViewListAdapter<MySaleRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MySaleRecyclerVAdapter";
    private Map <String, ProdTransactionData>prodTransactionDataHashMap = new HashMap<>();

     MySaleRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MySaleRecyclerViewAdapter   constructor   ");
    }

    public void updateHashMap(HashMap<String, ProdTransactionData> hashMapData) {
        prodTransactionDataHashMap = hashMapData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coffee_sale_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final ProductData value) {

        if (value == null) {
            return;
        }

        holder.saleListResult = value;


        if (prodTransactionDataHashMap.get(value.getProdPPID())!= null) {
            Log.d(LOG_TAG, prodTransactionDataHashMap.get(value.getProdPPID()).getProdPPID());
            holder.mSaleVanRCountTextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getProdPPID()));
            holder.mSaleQuantity2TextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getCurICount()));
        } else {
            holder.mSaleVanRCountTextView.setText("");
            holder.mSaleQuantity2TextView.setText("");
        }

        holder.mSaleProdPPIDTextView.setText(value.getProdPPID());
        holder.mSaleNameTextView.setText(value.getName());
        //holder.mSaleVanRCountTextView.setText(String.valueOf(value.getVanRCount()));
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
     class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
         final View mView;
         final TextView mSaleProdPPIDTextView;
         final TextView mSaleNameTextView;
         final TextView mSaleVanRCountTextView;
         final TextView mSaleQuantity2TextView;
         ProductData saleListResult;

         ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mSaleProdPPIDTextView = itemView.findViewById(R.id.page_sale_ProdPPID);
            mSaleNameTextView = itemView.findViewById(R.id.page_sale_Name);
            mSaleVanRCountTextView = itemView.findViewById(R.id.page_sale_VanRCount);
            mSaleQuantity2TextView = itemView.findViewById(R.id.page_sale_quantity2);
        }

    }

}
