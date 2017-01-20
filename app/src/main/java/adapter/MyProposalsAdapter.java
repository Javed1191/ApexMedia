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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.JobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.JobFeeds;
import com.apexmediatechnologies.apexmedia.JobProposals;
import com.apexmediatechnologies.apexmedia.MainActivity;
import com.apexmediatechnologies.apexmedia.MyProposalContractorTabAdapter;
import com.apexmediatechnologies.apexmedia.MyProposals;
import com.apexmediatechnologies.apexmedia.MyProposalsDetailsContractorActivity;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class MyProposalsAdapter extends RecyclerView.Adapter<MyProposalsAdapter.CustomViewHolder> {
    private List<MyProposals> myProposalsList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private MainActivity mainActivity;

    public MyProposalsAdapter(Context context, List<MyProposals> myProposalsList)
    {
        this.myProposalsList = myProposalsList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);

        mainActivity = (MainActivity) mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_contractor_my_proposals, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final MyProposals myProposals = myProposalsList.get(i);
        // position = i;


        //Setting text view title
       customViewHolder.tv_job_title.setText(myProposals.getJob_title());
        customViewHolder.tv_status.setText(myProposals.getStatus());
        customViewHolder.tv_proposals.setText(myProposals.getTotal_proposals());
        customViewHolder.tv_progress1.setText(myProposals.getJobProgress()+"%");
        customViewHolder.tv_progress.setText(myProposals.getJobProgress()+"%");
        customViewHolder.tv_created_at.setText(myProposals.getCreated_at());

        //int int_progress = Integer.parseInt(myProposals.getJobProgress());
        customViewHolder.prog_job_progress.setProgress(Integer.parseInt(myProposals.getJobProgress()));


        customViewHolder.tv_progress.setTag(customViewHolder);
        customViewHolder.tv_job_title.setTag(customViewHolder);
        customViewHolder.tv_status.setTag(customViewHolder);
        customViewHolder.tv_proposals.setTag(customViewHolder);
        customViewHolder.tv_progress1.setTag(customViewHolder);
        customViewHolder.tv_created_at.setTag(customViewHolder);
        customViewHolder.tv_view_proposals.setTag(customViewHolder);
        customViewHolder.img_chat.setTag(customViewHolder);
        customViewHolder.prog_job_progress.setTag(customViewHolder);
        customViewHolder.lay_job_feed.setTag(customViewHolder);


        customViewHolder.tv_view_proposals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                MyProposals jobFeeds = myProposalsList.get(position);

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                Intent intent = new Intent(mainActivity, MyProposalsDetailsContractorActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("porposal_id",jobFeeds.getPorposal_id());
                mainActivity.startActivity(intent);


            }
        });


        customViewHolder.img_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                MyProposals jobFeeds = myProposalsList.get(position);

                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                intent.putExtra("fromUserId",jobFeeds.getEmployer_id());
                intent.putExtra("project",jobFeeds.getJob_title());
                mainActivity.startActivity(intent);
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
        int list_size = myProposalsList.size();

        return (null != myProposalsList ? myProposalsList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

         TextView tv_progress,tv_job_title,tv_status,tv_proposals,tv_progress1,tv_created_at,
                 tv_view_proposals;
        LinearLayout lay_job_feed;
        ImageView img_chat;
        ProgressBar prog_job_progress;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_progress = (TextView) view.findViewById(R.id.tv_progress);
            this.tv_job_title = (TextView) view.findViewById(R.id.tv_job_title);
            this.tv_status = (TextView) view.findViewById(R.id.tv_status);
            this.tv_proposals = (TextView) view.findViewById(R.id.tv_proposals);
            this.tv_progress1 = (TextView) view.findViewById(R.id.tv_progress1);
            this.tv_created_at = (TextView) view.findViewById(R.id.tv_created_at);
            this.tv_view_proposals = (TextView) view.findViewById(R.id.tv_view_proposals);
            this.img_chat = (ImageView) view.findViewById(R.id.img_chat);
            this.prog_job_progress= (ProgressBar) view.findViewById(R.id.prog_job_progress);
            this.lay_job_feed = (LinearLayout) view.findViewById(R.id.lay_job_feed);

        }
    }

}