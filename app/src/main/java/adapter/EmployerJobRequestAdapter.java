package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.JobProposals;
import com.apexmediatechnologies.apexmedia.JobRequest;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.Application_Constants;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class EmployerJobRequestAdapter extends RecyclerView.Adapter<EmployerJobRequestAdapter.CustomViewHolder> {
    private List<JobRequest> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private String strAwardJob = Application_Constants.Main_URL+"keyword=award_job";


    public EmployerJobRequestAdapter(Context context, List<JobRequest> feedList) {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_job_request_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final JobRequest jobFeeds = feedList.get(i);
        // position = i;


        String strImage = jobFeeds.getContractor_pic();
            //Download image using picasso library
            Picasso.with(mContext).load(jobFeeds.getContractor_pic())
                    .into(customViewHolder.img_user);


        //Setting text view title
        customViewHolder.tv_user_name.setText(jobFeeds.getJob_name());
        customViewHolder.tv_posted.setText("Posted: "+jobFeeds.getPosted_date());
        customViewHolder.tv_contactor_info.setText("Contractor Info: "+jobFeeds.getContractor_info());
        customViewHolder.tv_desc.setText(jobFeeds.getMessage());

        customViewHolder.tv_contactor_info.setTag(customViewHolder);
        customViewHolder.tv_user_name.setTag(customViewHolder);
        customViewHolder.img_user.setTag(customViewHolder);
        customViewHolder.tv_posted.setTag(customViewHolder);
        customViewHolder.tv_desc.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);



        View.OnClickListener clickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();

                //Products Products = ProductsList.get(position);
               // Toast.makeText(mContext, Products.getEmployeeName()+"="+ position, Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    public int getItemCount()
    {
        int list_size = feedList.size();

        return (null != feedList ? feedList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

         TextView tv_posted,tv_contactor_info,
                 tv_user_name,tv_desc;
        LinearLayout lay_job_feed;
        ImageView img_user;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_posted = (TextView) view.findViewById(R.id.tv_posted);
            this.tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);
            this.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            this.tv_contactor_info = (TextView) view.findViewById(R.id.tv_contactor_info);


        }
    }





}