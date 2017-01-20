package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apexmediatechnologies.apexmedia.Payments;
import com.apexmediatechnologies.apexmedia.R;

import org.json.JSONArray;
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


public class EmployerPaymentAdapter extends RecyclerView.Adapter<EmployerPaymentAdapter.CustomViewHolder> {
    private List<Payments> viewMilestonesList;
    private Context mContext;
    private String websiteUrl = "";
    private PopupWindow feedMenuPW;
    private View popupView;
    private LayoutInflater inflater;
    private String siteUserID;
    private Utility utility;
    private String strViewInvoice = Application_Constants.Main_URL+"keyword=view_invoice";

    public EmployerPaymentAdapter(Context context, List<Payments> viewMilestonesList) {
        this.viewMilestonesList = viewMilestonesList;
        this.mContext = context;

        siteUserID = Shared_Preferences_Class.readString(mContext, Shared_Preferences_Class.USER_ID, "");
        utility = new Utility(mContext);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       /* View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact_row_new, null);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);*/


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_view_employer_payment_row, null, false);
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
        customViewHolder.tv_amount.setText(viewMilestones.getAmount());
        customViewHolder.tv_pay_date.setText(viewMilestones.getPaymentDate());
        customViewHolder.tv_status.setText(viewMilestones.getStatus());
        customViewHolder.tv_project.setText(viewMilestones.getProject());

        customViewHolder.tv_amount.setTag(customViewHolder);
        customViewHolder.tv_pay_date.setTag(customViewHolder);
        customViewHolder.tv_project.setTag(customViewHolder);
        customViewHolder.btn_pay.setTag(customViewHolder);
        customViewHolder.btn_invoice.setTag(customViewHolder);
        customViewHolder.tv_status.setTag(customViewHolder);



        customViewHolder.btn_invoice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();

                Payments jobFeeds = viewMilestonesList.get(position);
/*
                String str_like_count = jobFeeds.getTokLike();
                String str_is_like = jobFeeds.getIsTokLike();
                String str_tok_id = jobFeeds.getTokId();
                int int_tok_like = Integer.parseInt(str_like_count);

                Intent intent = new Intent(mContext, JobDetailProposalActivity.class);
                intent.putExtra("jobId",jobFeeds.getJobId());
                mContext.startActivity(intent);*/

                getInvoice(siteUserID,jobFeeds.getPaymentId());



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

         TextView tv_project,tv_amount,
                 tv_status,tv_pay_date;
        Button btn_invoice,btn_pay;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_project = (TextView) view.findViewById(R.id.tv_project);
            this.tv_status = (TextView) view.findViewById(R.id.tv_status);
            this.tv_pay_date = (TextView) view.findViewById(R.id.tv_payment_date);
            this.btn_invoice= (Button) view.findViewById(R.id.btn_invoice);
            this.btn_pay= (Button) view.findViewById(R.id.btn_pay);
            this.tv_amount = (TextView) view.findViewById(R.id.tv_amount);


        }
    }




    private void getInvoice(String userId, String payment_id)
    {

        if(utility.checkInternet())
        {

            final Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("payment_id", payment_id);

            ServiceHandler serviceHandler = new ServiceHandler(mContext);
            final ArrayList<Payments> paymentsArrayList = new ArrayList<Payments>();

            serviceHandler.registerUser(params, strViewInvoice, new ServiceHandler.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Json responce" + result);

                    //Toast.makeText(MainActivity.this, "Json responce"+result, Toast.LENGTH_SHORT).show();
                    String str_json = result;
                    String str_status, str_msg,invoice_url;
                    try {
                        if (str_json != null) {
                            JSONObject jobject = new JSONObject(str_json);
                            str_status = jobject.getString("Result");
                            str_msg = jobject.getString("Message");
                            websiteUrl = jobject.getString("invoice_url");
                            if (str_status.equalsIgnoreCase("true"))
                            {
                                try
                                {

                                    if (!websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://"))
                                    {
                                        websiteUrl = "http://" + websiteUrl;
                                    }

                                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl));
                                    mContext.startActivity(viewIntent);

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            } else {
                                Toast.makeText(mContext, str_msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "This may be server issue", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}