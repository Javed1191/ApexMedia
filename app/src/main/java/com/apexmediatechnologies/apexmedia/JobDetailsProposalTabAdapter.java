package com.apexmediatechnologies.apexmedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by USER on 24-12-2015.
 */
public class JobDetailsProposalTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String jobId,porposal_id;

    public JobDetailsProposalTabAdapter(FragmentManager fm, int NumOfTabs, String jobId, String porposal_id)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.jobId=jobId;
        this.porposal_id=porposal_id;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentJobDetails fragmentJobDetails = new FragmentJobDetails();

                Bundle bundle = new Bundle();
                bundle.putString("jobId", jobId);
                fragmentJobDetails.setArguments(bundle);
                return fragmentJobDetails;
            case 1:
                FragmentJobProposals fragmentJobProposals = new FragmentJobProposals();

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