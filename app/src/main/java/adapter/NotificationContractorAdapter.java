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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.JobDetailProposalActivity;
import com.apexmediatechnologies.apexmedia.JobFeeds;
import com.apexmediatechnologies.apexmedia.MainActivity;
import com.apexmediatechnologies.apexmedia.Notification;
import com.apexmediatechnologies.apexmedia.R;
import com.apexmediatechnologies.apexmedia.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;

public class NotificationContractorAdapter extends RecyclerView.Adapter<NotificationContractorAdapter.CustomViewHolder> {
    private List<Notification> feedList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;

    public NotificationContractorAdapter(Context context, List<Notification> feedList)
    {
        this.feedList = feedList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_notification_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Notification notification = feedList.get(i);
        // position = i;


            /*//Download image using picasso library
            Picasso.with(mContext).load(notification.getUser_image())
                    .into(customViewHolder.img_user);*/




        //Setting text view title
       customViewHolder.tv_title.setText(notification.getTitle());
        customViewHolder.tv_desc.setText(notification.getMessage());
        customViewHolder.tv_date.setText(notification.getDate_time());

        customViewHolder.tv_title.setTag(customViewHolder);
        customViewHolder.tv_desc.setTag(customViewHolder);
        customViewHolder.tv_date.setTag(customViewHolder);
        customViewHolder.lay_view_milstones.setTag(customViewHolder);
        customViewHolder.img_user_image.setTag(customViewHolder);

        customViewHolder.lay_view_milstones.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                Notification notification = feedList.get(position);

                /*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                String str_screen = notification.getScreen();

                if(str_screen.equalsIgnoreCase("job_detail"))
                {
                    Intent intent = new Intent(mContext, JobDetailProposalActivity.class);
                    intent.putExtra("jobId",notification.getJob_id());
                    mContext.startActivity(intent);
                }
                if(str_screen.equalsIgnoreCase("messageDetail"))
                {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("jobId",notification.getJob_id());
                    intent.putExtra("fromUserId",notification.getFrom_user_id());
                    intent.putExtra("project","");
                    mContext.startActivity(intent);

                }
                if(str_screen.equalsIgnoreCase("disput_detail"))
                {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("dispute","dispute");
                    intent.putExtra("jobId",notification.getJob_id());
                    intent.putExtra("toUserId",notification.getTo_user_id());
                    intent.putExtra("project","");

                    mContext.startActivity(intent);

                }



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

         TextView tv_title,tv_desc,tv_date;
        RelativeLayout lay_view_milstones;
        RoundedImageView img_user_image;


        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            this.tv_date = (TextView) view.findViewById(R.id.tv_date);
            this.lay_view_milstones = (RelativeLayout) view.findViewById(R.id.lay_view_milstones);
            this.img_user_image = (RoundedImageView) view.findViewById(R.id.img_user_image);


        }
    }

}