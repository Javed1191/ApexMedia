package com.apexmediatechnologies.apexmedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by USER on 24-12-2015.
 */
public class MyProposalContractorTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String jobId,porposal_id;

    public MyProposalContractorTabAdapter(FragmentManager fm, int NumOfTabs, String jobId, String porposal_id)
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
                FragmentMyProposalsJobDetails fragmentJobDetails = new FragmentMyProposalsJobDetails();

                Bundle bundle = new Bundle();
                bundle.putString("jobId", jobId);
                fragmentJobDetails.setArguments(bundle);
                return fragmentJobDetails;
            case 1:
                FragmentMyProposalsDetails fragmentJobProposals = new FragmentMyProposalsDetails();

                Bundle bundle1 = new Bundle();
                bundle1.putString("porposal_id", porposal_id);
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