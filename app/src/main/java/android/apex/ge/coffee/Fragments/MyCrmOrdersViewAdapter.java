package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.CrmOrderView;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nika on 26/03/2018.
 * This class is an adapter to display list of CrmOrderView
 */

public class MyCrmOrdersViewAdapter extends RecyclerViewAdapterWithFilter<MyCrmOrdersViewAdapter.ViewHolder, CrmOrderView> {


    protected final String LOG_TAG = "MyCrmOrderRecAdapter";

    private ArrayList<CrmOrderView> mListHolder;
    private ArrayList<CrmOrderView> mFilteredList;

    public MyCrmOrdersViewAdapter(List<CrmOrderView> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyCrmOrdersViewAdapter constructor   ");
        mListHolder = (ArrayList<CrmOrderView>) items;
    }


    @NonNull
    @Override
    public MyCrmOrdersViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_crm_order_view_item, parent, false);
        return new MyCrmOrdersViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyCrmOrdersViewAdapter.ViewHolder holder, final CrmOrderView value) {

        if (value == null) {
            return;
        }

        holder.crmOrderViewListResult = value;


        holder.mOrderIdTextView.setText(value.getOrderId());
        holder.mOrderDateTextView.setText(value.getOrderDate().toString());
        holder.mRecipientBranchNameTextView.setText(value.getRecipientBranchName());
        holder.mToAddressTextView.setText(value.getToAddress());
        holder.mTotalAmountTextView.setText(String.valueOf(value.getTotalAmount()));

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

        public final TextView mOrderIdTextView;
        public final TextView mOrderDateTextView;
        public final TextView mRecipientBranchNameTextView;
        public final TextView mToAddressTextView;
        public final TextView mTotalAmountTextView;


        public CrmOrderView crmOrderViewListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mOrderIdTextView = itemView.findViewById(R.id.crm_order_summary);
            mOrderDateTextView = itemView.findViewById(R.id.crm_order_date);
            mRecipientBranchNameTextView = itemView.findViewById(R.id.crm_order_client_name);
            mToAddressTextView = itemView.findViewById(R.id.crm_order_client_address);
            mTotalAmountTextView = itemView.findViewById(R.id.crm_order_amount);
        }

    }


    @Override
    public Filter getSpecificFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mListHolder;

                } else {
                    Log.d(LOG_TAG, "\n\nWE are filtering the list for search");

                    ArrayList<CrmOrderView> filteredList = new ArrayList<>();

                    for (CrmOrderView crmOrderView : mListHolder) {

                        if (crmOrderView.getRecipientBranchName().toLowerCase().contains(charString) || crmOrderView.getToAddress().contains(charString)) {


                            filteredList.add(crmOrderView);
                        }
                    }

                    mFilteredList = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                Log.d(LOG_TAG, "Filtered list toString: " + filterResults.values.toString());
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CrmOrderView>) filterResults.values;
                setmValues(mFilteredList);
                notifyDataSetChanged();
            }
        };
    }

}
