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

import com.apexmediatechnologies.apexmedia.AskAdminActivity;
import com.apexmediatechnologies.apexmedia.EmployerJobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.EmployerMainActivity;
import com.apexmediatechnologies.apexmedia.JobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.JobFeeds;
import com.apexmediatechnologies.apexmedia.JobFeedsEmployer;
import com.apexmediatechnologies.apexmedia.MainActivity;
import com.apexmediatechnologies.apexmedia.OnUploadClickListener;
import com.apexmediatechnologies.apexmedia.PostJobActivity;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class JobFeedsEmployerAdapter extends RecyclerView.Adapter<JobFeedsEmployerAdapter.CustomViewHolder> {
    private List<JobFeedsEmployer> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private EmployerMainActivity mainActivity;
    private OnUploadClickListener onUploadClickListener;

    public JobFeedsEmployerAdapter(Context context, List<JobFeedsEmployer> feedList,OnUploadClickListener onUploadClickListener)
    {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
        this.onUploadClickListener=onUploadClickListener;

        mainActivity = (EmployerMainActivity) mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_employer_jobs, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final JobFeedsEmployer jobFeeds = feedList.get(i);
        // position = i;

        //Setting text view title
       customViewHolder.tv_job_title.setText(jobFeeds.getJobName());
        customViewHolder.tv_dsec.setText(jobFeeds.getJobDesc());
        customViewHolder.tv_post_date.setText(jobFeeds.getPostDate());
        customViewHolder.tv_job_proposals.setText(jobFeeds.getProposals()+" Proposals");

        customViewHolder.tv_visibility.setText(jobFeeds.getVisibility());
        customViewHolder.tv_skills.setText(jobFeeds.getJobSkills());
        customViewHolder.tv_posting_type.setText(jobFeeds.getPosting_type());


        customViewHolder.tv_job_title.setTag(customViewHolder);
        customViewHolder.tv_job_proposals.setTag(customViewHolder);
        customViewHolder.tv_dsec.setTag(customViewHolder);
        customViewHolder.tv_skills.setTag(customViewHolder);
        customViewHolder.tv_price.setTag(customViewHolder);
        customViewHolder.tv_skills.setTag(customViewHolder);
        customViewHolder.tv_post_date.setTag(customViewHolder);
        customViewHolder.tv_visibility.setTag(customViewHolder);
        customViewHolder.tv_posting_type.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);
        customViewHolder.tv_ask_admin.setTag(customViewHolder);
        customViewHolder.img_upload.setTag(customViewHolder);
        customViewHolder.img_edit.setTag(customViewHolder);



        customViewHolder.lay_job_feed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mainActivity, EmployerJobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mainActivity.startActivity(intent);
            }
        });

        customViewHolder.tv_ask_admin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mainActivity, AskAdminActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("project",jobFeeds.getJobName());
                mainActivity.startActivity(intent);
            }
        });


        customViewHolder.img_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                if(onUploadClickListener != null)
                    onUploadClickListener.onUploadClick(position, true);
            }
        });

        customViewHolder.img_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                JobFeedsEmployer jobFeeds = feedList.get(position);

                Intent intent = new Intent(mContext,PostJobActivity.class);
                intent.putExtra("is_update","is_update");
                intent.putExtra("jobId",jobFeeds.getJobId());
                mContext.startActivity(intent);
            }
        });


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

         TextView tv_job_title,tv_job_proposals,tv_dsec,tv_skills,tv_price,tv_post_date,
                 tv_visibility,tv_posting_type,tv_ask_admin;
        LinearLayout lay_job_feed;
        ImageView img_upload,img_edit;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
            this.tv_job_proposals = (TextView) view.findViewById(R.id.tv_job_proposals);
            this.tv_dsec = (TextView) view.findViewById(R.id.tv_dsec);
            this.tv_skills = (TextView) view.findViewById(R.id.tv_skills);
            this.tv_price = (TextView) view.findViewById(R.id.tv_price);
            this.tv_skills = (TextView) view.findViewById(R.id.tv_skills);
            this.tv_post_date = (TextView) view.findViewById(R.id.tv_post_date);
            this.tv_visibility = (TextView) view.findViewById(R.id.tv_visibility);
            this.tv_posting_type = (TextView) view.findViewById(R.id.tv_posting_type);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);
            this.tv_ask_admin = (TextView) view.findViewById(R.id.tv_ask_admin);
            this.img_upload = (ImageView) view.findViewById(R.id.img_upload);
            this.img_edit = (ImageView) view.findViewById(R.id.img_edit);

        }
    }

}