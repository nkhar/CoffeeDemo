package android.apex.ge.coffee.Fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterWithFilter<B extends RecyclerView.ViewHolder, G> extends RecyclerViewListAdapter<B,G> implements Filterable {

    protected final String LOG_TAG = "MyCofRecyclerVAdapter";
     ArrayList<G> mListHolder;
     ArrayList<G> mFilteredList;

    public RecyclerViewAdapterWithFilter(List items) {
        super(items);
        Log.d(LOG_TAG, "We are in RecyclerViewAdapterWithFilter  constructor   ");
        mListHolder = (ArrayList<G>) items;
    }

    @Override
    public void updateList(List listData) {
        super.updateList(listData);
        mListHolder = (ArrayList<G>) getmValues();
    }

    @Override
    public Filter getFilter() {
        return getSpecificFilter();
    }

    public abstract Filter getSpecificFilter();

    public ArrayList<G> getmListHolder() {
        return mListHolder;
    }

    public void setmListHolder(ArrayList<G> mListHolder) {
        this.mListHolder = mListHolder;
    }

    public ArrayList<G> getmFilteredList() {
        return mFilteredList;
    }

    public void setmFilteredList(ArrayList<G> mFilteredList) {
        this.mFilteredList = mFilteredList;
    }
}
