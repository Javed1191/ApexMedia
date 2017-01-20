package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apexmediatechnologies.apexmedia.ChatMessage;
import com.apexmediatechnologies.apexmedia.R;

import java.util.ArrayList;
import java.util.List;

import services.Shared_Preferences_Class;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;
    private String userId;

    @Override
    public void add(ChatMessage object)
    {
        chatMessageList.add(object);

        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
        this.context = context;
        userId = Shared_Preferences_Class.readString(context,Shared_Preferences_Class.USER_ID,"");
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.fromUser.equals(userId))
        {
            row = inflater.inflate(R.layout.list_row_layout_even, parent, false);
        }
        else
        {
            row = inflater.inflate(R.layout.list_row_layout_odd, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.text);
        chatText.setText(chatMessageObj.message);
        return row;
    }
}