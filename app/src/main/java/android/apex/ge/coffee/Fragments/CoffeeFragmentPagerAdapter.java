package android.apex.ge.coffee.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Nika on 23/03/2018.
 * This is a FragmentPagerAdapter for the various tabs in CoffeeMachineDetailActivity
 * @see android.apex.ge.coffee.CoffeeMachineDetailActivity
 */

public class CoffeeFragmentPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Sale", "Sale Produced", "Raw Materials", "Money" };
    private Context context;

    public CoffeeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageRecViewFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
