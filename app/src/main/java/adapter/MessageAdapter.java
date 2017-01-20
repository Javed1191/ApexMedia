package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.ChatActivity;
import com.apexmediatechnologies.apexmedia.Messages;
import com.apexmediatechnologies.apexmedia.Payments;
import com.apexmediatechnologies.apexmedia.R;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {
    private List<Messages> messagesList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String userId,userType;
    private Utility utility;

    public MessageAdapter(Context context, List<Messages> messagesList) {
        this.messagesList = messagesList;
        this.mContext = context;

        userId = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        userType = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_TYPE, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_view_message_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i)
    {
        final Messages messages = messagesList.get(i);
        // position = i;


        //Setting text view title
        customViewHolder.tv_project.setText(messages.getProject());
        customViewHolder.tv_message.setText(messages.getMessage());
        customViewHolder.tv_date.setText(messages.getDateTime());
        customViewHolder.tv_project.setText(messages.getProject());

        customViewHolder.tv_project.setTag(customViewHolder);
        customViewHolder.tv_message.setTag(customViewHolder);
        customViewHolder.tv_date.setTag(customViewHolder);
        customViewHolder.tv_replay.setTag(customViewHolder);
        customViewHolder.lay_message.setTag(customViewHolder);



        customViewHolder.lay_message.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

               /* JobProposals jobFeeds = feedList.get(position);

                *String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*/

                Intent intent = new Intent(mContext, ChatActivity.class);
               // intent.putExtra("jobId",messages.getJobId());
                intent.putExtra("jobId",messages.getJobId());
                intent.putExtra("fromUserId",messages.getMsgFrom());
                intent.putExtra("project",messages.getProject());
                intent.putExtra("type",userType);
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
        int list_size = messagesList.size();

        return (null != messagesList ? messagesList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

         TextView tv_project,tv_message,
                 tv_date,tv_replay;
        LinearLayout lay_message;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_project = (TextView) view.findViewById(R.id.tv_project);
            this.tv_message = (TextView) view.findViewById(R.id.tv_message);
            this.tv_date = (TextView) view.findViewById(R.id.tv_date);
            this.tv_replay= (TextView) view.findViewById(R.id.tv_replay);
            this.lay_message = (LinearLayout) view.findViewById(R.id.lay_message);


        }
    }

}