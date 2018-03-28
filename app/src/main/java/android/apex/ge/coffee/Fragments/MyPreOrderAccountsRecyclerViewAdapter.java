package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.AccountInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nika on 26/03/2018.
 * This class is an adapter to display list of PreOrderAccounts
 */

public class MyPreOrderAccountsRecyclerViewAdapter extends RecyclerViewListAdapter<MyPreOrderAccountsRecyclerViewAdapter.ViewHolder, AccountInfo> {

    protected final String LOG_TAG = "MyPreOrderAccRecAdapter";

    public MyPreOrderAccountsRecyclerViewAdapter(List<AccountInfo> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyPreOrderAccountsRecyclerViewAdapter   constructor   ");
    }

    @NonNull
    @Override
    public MyPreOrderAccountsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pre_order_account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final AccountInfo value) {
        if (value == null) {
            return;
        }

        holder.mAcccountInfoResult = value;

        holder.mPreOrderAccountsAccTextView.setText(value.getAcc());
        holder.mPreOrderAccountsNameTextView.setText(value.getName());
        holder.mPreOrderAccountsVatTypeTextView.setText(String.valueOf(value.getVatType()));
      /*  holder.mRawMaterialsBCodeTextView.setText(value.getAccWithName());
        holder.mRawMaterialsInCodeTextView.setText(value.getAddress());
        holder.mRawMaterialsPackCountTextView.setText(value.getsN());
        holder.mRawMaterialsRCountTextView.setText(value.getWarehouseAddress());*/

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
        public final TextView mPreOrderAccountsAccTextView;
        public final TextView mPreOrderAccountsNameTextView;
        public final TextView mPreOrderAccountsVatTypeTextView;
        /* public final TextView mRawMaterialsInCodeTextView;
         public final TextView mRawMaterialsPackCountTextView;
         public final TextView mRawMaterialsRCountTextView;
         public final TextView mRawMaterialsVanRCountTextView;*/
        public AccountInfo mAcccountInfoResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mPreOrderAccountsAccTextView = itemView.findViewById(R.id.pre_order_account_Acc);
            mPreOrderAccountsNameTextView = itemView.findViewById(R.id.pre_order_account_Name);
            mPreOrderAccountsVatTypeTextView = itemView.findViewById(R.id.pre_order_account_VatType);
/*            mRawMaterialsInCodeTextView = itemView.findViewById(R.id.page_raw_materials_InCode);
            mRawMaterialsPackCountTextView = itemView.findViewById(R.id.page_raw_materials_PackCount);
            mRawMaterialsRCountTextView = itemView.findViewById(R.id.page_raw_materials_RCount);
            mRawMaterialsVanRCountTextView = itemView.findViewById(R.id.page_raw_materials_VanRCount);*/
        }

    }
}
