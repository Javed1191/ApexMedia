package com.apexmediatechnologies.apexmedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by USER on 24-12-2015.
 */
public class EmployerAwardedJobDetailsProposalTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String jobId,proposalId;

    public EmployerAwardedJobDetailsProposalTabAdapter(FragmentManager fm, int NumOfTabs, String jobId, String proposalId)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.jobId=jobId;
        this.proposalId=proposalId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentEmployerAwardedJobDetails fragmentJobDetails = new FragmentEmployerAwardedJobDetails();

                Bundle bundle = new Bundle();
                bundle.putString("jobId", jobId);
                fragmentJobDetails.setArguments(bundle);
                return fragmentJobDetails;
            case 1:
                FragmentEmployerAwardedJobProposals fragmentJobProposals = new FragmentEmployerAwardedJobProposals();

                Bundle bundle1 = new Bundle();
                bundle1.putString("proposalId", proposalId);
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