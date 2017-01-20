package adapter;

import java.util.ArrayList;
import java.util.List;

import com.apexmediatechnologies.apexmedia.FilterSubCatClass;
import com.apexmediatechnologies.apexmedia.MainActivity;
import com.apexmediatechnologies.apexmedia.OnSubFilterCheckedChangeListener;
import com.apexmediatechnologies.apexmedia.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class FillterSubCatAdapter extends BaseAdapter {
	
	private MainActivity context;
	private List<FilterSubCatClass> list = new ArrayList<FilterSubCatClass>();
	private List<String>list_sub_cat;
	private OnSubFilterCheckedChangeListener onCheckedChange;
	
	public FillterSubCatAdapter(MainActivity context,OnSubFilterCheckedChangeListener onCheckedChange)
	{
		this.context = context;
		list_sub_cat = new ArrayList<>();
		this.onCheckedChange = onCheckedChange;
	}
	
	public void setList(List<FilterSubCatClass> list)
	{
		this.list = list;
		notifyDataSetChanged();
	}
	
	public List<FilterSubCatClass> getList()
	{
		return list;
	}
	
	public void addToList(FilterSubCatClass pObj)
	{
		this.list.add(pObj);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if(convertView == null)
		{
			holder = new ViewHolder();
			
			convertView = context.getLayoutInflater().inflate(R.layout.list_sub_filter_item, parent, false);
			holder.chk_sub_cat = (CheckBox) convertView.findViewById(R.id.chk_sub_cat);

			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		String str_comment = list.get(position).subCategory_name;
		
		holder.chk_sub_cat.setText(str_comment);

		holder.chk_sub_cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{

				if (onCheckedChange != null)
					onCheckedChange.OnCartButtonClick(position, isChecked);
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder
	{
		CheckBox chk_sub_cat;
	}
}
