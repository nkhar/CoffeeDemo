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
 * This is adapter for recycler view to display list of items in Raw Materials tab of
 * CoffeeMachineDetailActivity.
 */

public class MyRawMaterialsRecyclerViewAdapter extends RecyclerViewListAdapter<MyRawMaterialsRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MyRMaterialsRecAdapter";
    private Map<String, ProdTransactionData> prodTransactionDataHashMap = new HashMap<>();

    public MyRawMaterialsRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyRawMaterialsRecyclerViewAdapter   constructor   ");
    }

    public void updateHashMap(HashMap<String, ProdTransactionData> hashMapData) {
        prodTransactionDataHashMap = hashMapData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_coffee_raw_materials_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final ProductData value) {

        if (value == null) {
            return;
        }

        holder.rawMaterialsListResult = value;


        if (prodTransactionDataHashMap.get(value.getProdPPID()) != null) {
            Log.d(LOG_TAG, prodTransactionDataHashMap.get(value.getProdPPID()).getProdPPID());
            holder.mRawMaterialsQuantity1TextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getCurICount()));
            holder.mRawMaterialsQuantity2TextView.setText(String.valueOf(prodTransactionDataHashMap.get(value.getProdPPID()).getCurSCount()));
        } else {
            holder.mRawMaterialsQuantity1TextView.setText("");
            holder.mRawMaterialsQuantity2TextView.setText("");
        }


        holder.mRawMaterialsBCodeTextView.setText(value.getBCode());
        holder.mRawMaterialsNameTextView.setText(value.getName());

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
        public final TextView mRawMaterialsBCodeTextView;
        public final TextView mRawMaterialsNameTextView;
        public final TextView mRawMaterialsQuantity1TextView;
        public final TextView mRawMaterialsQuantity2TextView;

        public ProductData rawMaterialsListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mRawMaterialsBCodeTextView = itemView.findViewById(R.id.page_raw_materials_BCode);
            mRawMaterialsNameTextView = itemView.findViewById(R.id.page_raw_materials_Name);
            mRawMaterialsQuantity1TextView = itemView.findViewById(R.id.page_raw_materials_quantity1);
            mRawMaterialsQuantity2TextView = itemView.findViewById(R.id.page_raw_materials_quantity2);
        }

    }


}
