package android.apex.ge.coffee.Fragments;


import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.CoffeeDoc;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Tis class is adapter for the list of CoffeeDocs
 */
public class MyCoffeeDocRecyclerViewAdapter extends RecyclerViewListAdapter<MyCoffeeDocRecyclerViewAdapter.ViewHolder, CoffeeDoc> implements Filterable {

    protected final String LOG_TAG = "MyCofDocRecVAdapter";
    private ArrayList<CoffeeDoc> mListHolder;
    private ArrayList<CoffeeDoc> mFilteredList;


    public MyCoffeeDocRecyclerViewAdapter(List<CoffeeDoc> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyCofDocRecViewAdapter   constructor   ");
        mListHolder = (ArrayList<CoffeeDoc>) items;
    }

    @NonNull
    @Override
    public MyCoffeeDocRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_coffee_machine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final CoffeeDoc value) {
        if (value == null) {
            return;
        }

        holder.coffeeDocListResult = value;


        holder.mCoffeeMachineNameTextView.setText(value.getdDate().toString());
//        holder.mCoffeeMachineNameTextView.setText(value.getCorespondAcc());
        holder.mCoffeeMachineAddressTextView.setText(value.getCorespondName());
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
        public final TextView mCoffeeMachineNameTextView;
        public final TextView mCoffeeMachineAddressTextView;
        public CoffeeDoc coffeeDocListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mCoffeeMachineNameTextView = itemView.findViewById(R.id.coffee_machine_name);
            mCoffeeMachineAddressTextView = itemView.findViewById(R.id.coffee_machine_address);
        }

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mListHolder;

                } else {
                    Log.d(LOG_TAG, "\n\nWE are filtering the list for search");

                    ArrayList<CoffeeDoc> filteredList = new ArrayList<>();

                    for (CoffeeDoc coffeeDoc : mListHolder) {

                        if (coffeeDoc.getCorespondName().toLowerCase().contains(charString) || coffeeDoc.getdDate().toString().contains(charString) ) {


                            filteredList.add(coffeeDoc);
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
                mFilteredList = (ArrayList<CoffeeDoc>) filterResults.values;
                setmValues(mFilteredList);
                notifyDataSetChanged();
            }
        };
    }

}
