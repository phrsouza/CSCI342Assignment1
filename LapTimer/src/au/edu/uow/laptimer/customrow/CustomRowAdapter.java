package au.edu.uow.laptimer.customrow;

import java.util.ArrayList;

import au.edu.uow.laptimer.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import au.edu.uow.laptimer.controller.activity.TimerActivity;

public class CustomRowAdapter extends BaseAdapter {

	private static class ViewHolder {

		public final TextView first;
		public final TextView second;
		public final TextView third;

		private ViewHolder(TextView first, TextView second, TextView third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
	}

	Context context;

	ArrayList<CustomRow> customRows = new ArrayList<CustomRow>();

	public CustomRowAdapter(Context context, ArrayList<CustomRow> customRows) {
		this.context = context;
		this.customRows = customRows;
	}

	@Override
	public int getCount() {
		return customRows.size();
	}

	@Override
	public Object getItem(int position) {
		if (customRows.size() == 0) {
			return null;
		}

		return customRows.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = ((TimerActivity) context)
					.getLayoutInflater();
			v = inflater.inflate(R.layout.custom_row, parent, false);

			// Optimisation - store our TextViews in a ViewHolder object:

			TextView first = (TextView) v.findViewById(R.id.custom_row_text1);
			TextView second = (TextView) v.findViewById(R.id.custom_row_text2);
			TextView third = (TextView) v.findViewById(R.id.custom_row_text3);
			v.setTag(new ViewHolder(first, second, third));
		}
		CustomRow customRow = customRows.get(position);

        //Don't call findViewById for each call to getView, store the references in a ViewHolder instead:

        //TextView bigTextView = (TextView) v.findViewById(R.id.bigTextView);
        //TextView smallTextView = (TextView) v.findViewById(R.id.smallTextView);

        ViewHolder viewHolder = (ViewHolder) v.getTag();
        TextView first = viewHolder.first;
        TextView second = viewHolder.second;
        TextView third = viewHolder.third;

        first.setText(customRow.getFirst());
        second.setText(customRow.getSecond());
        third.setText(customRow.getThird());

        return v;
	}

}
