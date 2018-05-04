package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.Model.CoffeeMachine;
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
 * Created by Nika on 16/03/2018.
 * adapter for coffee machines recycler view
 */

public class MyCoffeeMachineRecyclerViewAdapter extends RecyclerViewListAdapter<MyCoffeeMachineRecyclerViewAdapter.ViewHolder, CoffeeMachine> implements Filterable {

    protected final String LOG_TAG = "MyCofRecyclerVAdapter";
    private ArrayList<CoffeeMachine> mListHolder;
    private ArrayList<CoffeeMachine> mFilteredList;

    /*private final String accountACC= "ანგ#:";
    private final String accountName= "დასახელება:";
    private final String accountAddress= "მისამართი:";*/


    public MyCoffeeMachineRecyclerViewAdapter(List<CoffeeMachine> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyCoffeeMachineRecyclerViewAdapter   constructor   ");
        mListHolder = (ArrayList<CoffeeMachine>) items;
    }

    @NonNull
    @Override
    public MyCoffeeMachineRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_coffee_machine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final CoffeeMachine value) {
        if (value == null) {
            return;
        }

        holder.coffeeListResult = value;

        holder.mCoffeeMachineNameTextView.setText(value.getName());
        holder.mCoffeeMachineAddressTextView.setText(value.getAddress());

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final View mView;
        public final TextView mCoffeeMachineNameTextView;
        public final TextView mCoffeeMachineAddressTextView;
        public CoffeeMachine coffeeListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mCoffeeMachineNameTextView = itemView.findViewById(R.id.coffee_machine_name);
            mCoffeeMachineAddressTextView = itemView.findViewById(R.id.coffee_machine_address);


            final ILibObjectCrud listener = getmListener();
            if (listener != null) {
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(coffeeListResult);
                    }
                });
                mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.onLongClick(coffeeListResult);
                        return true;
                    }
                });
            }


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

                    ArrayList<CoffeeMachine> filteredList = new ArrayList<>();

                    for (CoffeeMachine coffeeMachine : mListHolder) {

                        if (coffeeMachine.getName().toLowerCase().contains(charString) || coffeeMachine.getAddress().toLowerCase().contains(charString)) {


                            filteredList.add(coffeeMachine);
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
                mFilteredList = (ArrayList<CoffeeMachine>) filterResults.values;
                setmValues(mFilteredList);
                notifyDataSetChanged();
            }
        };
    }
}
