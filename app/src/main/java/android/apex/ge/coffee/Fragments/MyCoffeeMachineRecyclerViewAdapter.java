package android.apex.ge.coffee.Fragments;

import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeMachine;
import android.apex.ge.coffee.Retrofit.CoffeeMachineList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nika on 16/03/2018.
 * adapter for coffee machines recycler view
 */

public class MyCoffeeMachineRecyclerViewAdapter extends RecyclerViewListAdapter<MyCoffeeMachineRecyclerViewAdapter.ViewHolder, CoffeeMachine> {

    protected final String LOG_TAG = "MyCofRecyclerVAdapter";

    /*private final String accountACC= "ანგ#:";
    private final String accountName= "დასახელება:";
    private final String accountAddress= "მისამართი:";*/


    public MyCoffeeMachineRecyclerViewAdapter(List<CoffeeMachine> items) {
        super(items);
        Log.d(LOG_TAG, "We are in MyCoffeeMachineRecyclerViewAdapter   constructor   ");
    }

    @NonNull
    @Override
    public MyCoffeeMachineRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coffee_machine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final CoffeeMachine value) {
        if(value == null) {
            return;
        }

        holder.coffeeListResult = value;

        holder.mCoffeeMachineAccTextView.setText(value.getAcc());
        holder.mCoffeeMachineNameTextView.setText(value.getName());
        holder.mCoffeeMachineAddressTextView.setText(value.getAddress());
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
        public final TextView mCoffeeMachineAccTextView;
        public final TextView mCoffeeMachineNameTextView;
        public final TextView mCoffeeMachineAddressTextView;
        public CoffeeMachine coffeeListResult;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mCoffeeMachineAccTextView = itemView.findViewById(R.id.coffee_machine_acc);
            mCoffeeMachineNameTextView = itemView.findViewById(R.id.coffee_machine_name);
            mCoffeeMachineAddressTextView = itemView.findViewById(R.id.coffee_machine_address);
        }

    }
}