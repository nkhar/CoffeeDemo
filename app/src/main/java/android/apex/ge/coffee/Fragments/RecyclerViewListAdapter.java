package android.apex.ge.coffee.Fragments;

/**
 * Created by Nika on 16/03/2018.
 * This i abstract class that specific RecyclerView adapters will use
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link List of coffee machibnes} and makes a call to the
 * specified {@link MachineFragment}.
 */


public abstract class RecyclerViewListAdapter<T extends RecyclerView.ViewHolder, E> extends RecyclerView.Adapter<T> {
    private List<E> mValues;
    private View.OnClickListener mViewClick;
    private ILibObjectCrud<E> mListener;


    public RecyclerViewListAdapter() {
    }

    public RecyclerViewListAdapter(List<E> items) {
        mValues = items;
    }

    public void updateList(List<E> listData) {
        mValues = listData;
        notifyDataSetChanged();
    }

    @NonNull
    public abstract T onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull final T holder, int position) {
        if (mValues != null)
            onBindViewHolder(holder, mValues.get(position));
        else
            onBindViewHolder(holder, null);
    }

    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        return mValues.size();
    }

    public abstract void onBindViewHolder(final T holder, final E value);

    public View.OnClickListener getmViewClick() {
        return mViewClick;
    }

    public void setmViewClick(View.OnClickListener mViewClick) {
        this.mViewClick = mViewClick;
    }

    public ILibObjectCrud<E> getmListener() {
        return mListener;
    }

    public void setmListener(ILibObjectCrud<E> mListener) {
        this.mListener = mListener;
    }


}

/**
 * This is an interface that classes should implement if they react to clicks or longClicks.
 */
interface ILibObjectCrud<E> {
    void onClick(E value);

    void onLongClick(E value);
}

