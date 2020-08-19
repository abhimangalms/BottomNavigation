package info.project.orion.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import info.project.orion.fragment.events.SportsFragment;
import info.project.orion.fragment.events.TechFestFragment;

public class EventsAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    public EventsAdapter(Context c, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);

        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TechFestFragment techFestFragment = new TechFestFragment();
                return techFestFragment;
            case 1:
                SportsFragment sportsFragment = new SportsFragment();
                return sportsFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
