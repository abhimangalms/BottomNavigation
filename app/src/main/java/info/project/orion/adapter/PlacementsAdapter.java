package info.project.orion.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import info.project.orion.fragment.placements.CompanyFragment;
import info.project.orion.fragment.placements.CollegeFragment;

public class PlacementsAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    public PlacementsAdapter(Context c, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);

        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CollegeFragment collegeFragment = new CollegeFragment();
                return collegeFragment;
            case 1:
                CompanyFragment companyFragment = new CompanyFragment();
                return companyFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
