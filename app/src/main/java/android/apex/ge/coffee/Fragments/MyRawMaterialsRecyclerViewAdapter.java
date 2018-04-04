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
 * This is adapter for recycler view to display list of items in Raw Materials tab of
 * CoffeeMachineDetailActivity.
 */

public class MyRawMaterialsRecyclerViewAdapter extends RecyclerViewListAdapter<MyRawMaterialsRecyclerViewAdapter.ViewHolder, ProductData> {

    protected final String LOG_TAG = "MyRMaterialsRecAdapter";

    public MyRawMaterialsRecyclerViewAdapter(List<ProductData> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyRawMaterialsRecyclerViewAdapter   constructor   ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coffee_raw_materials_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final ProductData value) {

        if (value == null) {
            return;
        }

        holder.rawMaterialsListResult = value;

        holder.mRawMaterialsProdPPIDTextView.setText(value.getProdPPID());
        holder.mRawMaterialsNameTextView.setText(value.getName());
        holder.mRawMaterialsBCodeTextView.setText(value.getbCode());
        holder.mRawMaterialsInCodeTextView.setText(value.getInCode());
        holder.mRawMaterialsPackCountTextView.setText(String.valueOf(value.getPackCount()));
        holder.mRawMaterialsRCountTextView.setText(String.valueOf(value.getrCount()));
        holder.mRawMaterialsVanRCountTextView.setText(String.valueOf(value.getVanRCount()));
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
        public final TextView mRawMaterialsProdPPIDTextView;
        public final TextView mRawMaterialsNameTextView;
        public final TextView mRawMaterialsBCodeTextView;
        public final TextView mRawMaterialsInCodeTextView;
        public final TextView mRawMaterialsPackCountTextView;
        public final TextView mRawMaterialsRCountTextView;
        public final TextView mRawMaterialsVanRCountTextView;
        public ProductData rawMaterialsListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mRawMaterialsProdPPIDTextView = itemView.findViewById(R.id.page_raw_materials_ProdPPID);
            mRawMaterialsNameTextView = itemView.findViewById(R.id.page_raw_materials_Name);
            mRawMaterialsBCodeTextView = itemView.findViewById(R.id.page_raw_materials_BCode);
            mRawMaterialsInCodeTextView = itemView.findViewById(R.id.page_raw_materials_InCode);
            mRawMaterialsPackCountTextView = itemView.findViewById(R.id.page_raw_materials_PackCount);
            mRawMaterialsRCountTextView = itemView.findViewById(R.id.page_raw_materials_RCount);
            mRawMaterialsVanRCountTextView = itemView.findViewById(R.id.page_raw_materials_VanRCount);
        }

    }


}
