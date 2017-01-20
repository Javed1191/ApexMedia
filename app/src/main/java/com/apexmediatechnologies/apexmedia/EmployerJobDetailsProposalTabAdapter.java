package com.apexmediatechnologies.apexmedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by USER on 24-12-2015.
 */
public class EmployerJobDetailsProposalTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String jobId;

    public EmployerJobDetailsProposalTabAdapter(FragmentManager fm, int NumOfTabs, String jobId)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.jobId=jobId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentEmployerJobDetails fragmentJobDetails = new FragmentEmployerJobDetails();

                Bundle bundle = new Bundle();
                bundle.putString("jobId", jobId);
                fragmentJobDetails.setArguments(bundle);
                return fragmentJobDetails;
            case 1:
                FragmentEmployerJobProposals fragmentJobProposals = new FragmentEmployerJobProposals();

                Bundle bundle1 = new Bundle();
                bundle1.putString("jobId", jobId);
                fragmentJobProposals.setArguments(bundle1);
                return fragmentJobProposals;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}