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
 * This class is an adapter for the list of items in Produced Produceds tab of
 * CoffeeMachineDetailActivity.
 */

public class MyProducedRecyclerViewAdapter extends RecyclerViewListAdapter<MyProducedRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MyProdRecAdapter";

    public MyProducedRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyProducedRecyclerViewAdapter   constructor   ");
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

        holder.mProducedProPPIDTextView.setText(value.getProdPPID());
        holder.mProducedNameTextView.setText(value.getName());
        holder.mProducedBCodeTextView.setText(value.getbCode());
        holder.mProducedInCodeTextView.setText(value.getInCode());
        holder.mProducedPackCountTextView.setText(String.valueOf(value.getPackCount()));
        holder.mProducedRCountTextView.setText(String.valueOf(value.getrCount()));
        holder.mProducedVanRCountTextView.setText(String.valueOf(value.getVanRCount()));
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
        public final TextView mProducedProPPIDTextView;
        public final TextView mProducedNameTextView;
        public final TextView mProducedBCodeTextView;
        public final TextView mProducedInCodeTextView;
        public final TextView mProducedPackCountTextView;
        public final TextView mProducedRCountTextView;
        public final TextView mProducedVanRCountTextView;

        public ProductData producedListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mProducedProPPIDTextView = itemView.findViewById(R.id.page_produced_ProdPPID);
            mProducedNameTextView = itemView.findViewById(R.id.page_produced_Name);
            mProducedBCodeTextView = itemView.findViewById(R.id.page_produced_BCode);
            mProducedInCodeTextView = itemView.findViewById(R.id.page_produced_InCode);
            mProducedPackCountTextView = itemView.findViewById(R.id.page_produced_PackCount);
            mProducedRCountTextView = itemView.findViewById(R.id.page_produced_RCount);
            mProducedVanRCountTextView = itemView.findViewById(R.id.page_produced_VanRCount);
        }

    }

}
