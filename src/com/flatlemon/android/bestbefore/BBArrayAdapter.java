package com.flatlemon.android.bestbefore;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BBArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;

	public BBArrayAdapter(Context context, List<String> items) {
		super(context, R.layout.listview, items);
		this.context = context;
		this.values = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview, parent, false);
		TextView tvItemName = (TextView) rowView.findViewById(R.id.tvItemName);
		TextView tvBbDate = (TextView) rowView.findViewById(R.id.tvBbDate);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.rygIcon);

		String[] strings = (values.get(position)).split(",", 5);

		tvItemName.setText(strings[1]);
		tvBbDate.setText(strings[2] + "/" + Integer.toString((Integer.parseInt(strings[3]) + 1)) + "/" + strings[4]);
		// Change the icon for Windows and iPhone
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), 0, 0, 0);
		final long ONE_HOUR = 60 * 60 * 1000L;
		long dayDiff = ((cal.getTimeInMillis() - (Calendar.getInstance()).getTimeInMillis()) / (ONE_HOUR * 24)) + 1;
		System.out.println(dayDiff);
		if (dayDiff < 0) {
			imageView.setImageResource(R.drawable.red);
		} else if (dayDiff > 2) {
			imageView.setImageResource(R.drawable.green);
		} else {
			imageView.setImageResource(R.drawable.yellow);
		}

		return rowView;
	}
}
