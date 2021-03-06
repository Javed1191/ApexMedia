package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.Payments;
import com.apexmediatechnologies.apexmedia.R;
import com.apexmediatechnologies.apexmedia.ViewMilestones;

import java.util.List;

import services.Shared_Preferences_Class;
import services.Utility;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.CustomViewHolder> {
    private List<Payments> viewMilestonesList;
    private Context mContext;
    private String ischild="";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;

    public PaymentAdapter(Context context, List<Payments> viewMilestonesList) {
        this.viewMilestonesList = viewMilestonesList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_view_payment_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        final CustomViewHolder viewHolder = new CustomViewHolder(rootView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Payments viewMilestones = viewMilestonesList.get(i);
        // position = i;


        //Setting text view title
        customViewHolder.tv_amounnt.setText(viewMilestones.getAmount());
        customViewHolder.tv_pay_date.setText(viewMilestones.getPaymentDate());
        customViewHolder.tv_status.setText(viewMilestones.getStatus());
        customViewHolder.tv_project.setText(viewMilestones.getProject());

        customViewHolder.tv_amounnt.setTag(customViewHolder);
        customViewHolder.tv_pay_date.setTag(customViewHolder);
        customViewHolder.tv_project.setTag(customViewHolder);
        customViewHolder.btn_pay.setTag(customViewHolder);
        customViewHolder.lay_view_payment.setTag(customViewHolder);
        customViewHolder.tv_status.setTag(customViewHolder);



        customViewHolder.lay_view_payment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                /*JobProposals jobFeeds = feedList.get(position);

                *//*String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);*//*

                *//*Intent intent = new Intent(mContext, JobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mContext.startActivity(intent);*/
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
        int list_size = viewMilestonesList.size();

        return (null != viewMilestonesList ? viewMilestonesList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

         TextView tv_project,tv_amounnt,
                 tv_status,tv_pay_date;
        LinearLayout lay_view_payment;
        Button btn_pay;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_project = (TextView) view.findViewById(R.id.tv_project);
            this.tv_status = (TextView) view.findViewById(R.id.tv_status);
            this.tv_pay_date = (TextView) view.findViewById(R.id.tv_pay_date);
            this.btn_pay= (Button) view.findViewById(R.id.btn_pay);
            this.lay_view_payment = (LinearLayout) view.findViewById(R.id.lay_view_payment);
            this.tv_amounnt = (TextView) view.findViewById(R.id.tv_amounnt);


        }
    }

}