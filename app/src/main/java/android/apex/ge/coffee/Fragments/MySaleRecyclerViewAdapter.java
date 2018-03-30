package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.ProductData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nika on 23/03/2018.
 * This is adapter for recycler view to display list of items in Sale tab of
 * CoffeeMachineDetailActivity.
 */

public class MySaleRecyclerViewAdapter extends RecyclerViewListAdapter<MySaleRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MySaleRecyclerVAdapter";

    public MySaleRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MySaleRecyclerViewAdapter   constructor   ");
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

        holder.mSaleProPPIDTextView.setText(value.getProdPPID());
        holder.mSaleNameTextView.setText(value.getName());
        holder.mSaleVanRCountTextView.setText(String.valueOf(value.getVanRCount()));
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
        public final TextView mSaleProPPIDTextView;
        public final TextView mSaleNameTextView;
        public final TextView mSaleVanRCountTextView;
        public ProductData saleListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mSaleProPPIDTextView = itemView.findViewById(R.id.page_sale_ProdPPID);
            mSaleNameTextView = itemView.findViewById(R.id.page_sale_Name);
            mSaleVanRCountTextView = itemView.findViewById(R.id.page_sale_VanRCount);
        }

    }

}
