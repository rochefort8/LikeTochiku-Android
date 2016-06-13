package com.tochiku85.liketochiku.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tochiku85.liketochiku.R;

public class FunctionListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] itemname;
	private final Integer[] imgid;
    private final String[] item_description;

	public FunctionListAdapter(Activity context, String[] itemname, String[] item_description,Integer[] imgid) {
		super(context, R.layout.item_function, itemname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.itemname=itemname;
        this.item_description=item_description;
		this.imgid=imgid;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView ;

        if (itemname[position].startsWith("SEP:")) {
            rowView = inflater.inflate(R.layout.item_function_separator, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.text_function_list_separator);
            txtTitle.setText(itemname[position].substring("SEP:".length()));
        } else  {
           rowView = inflater.inflate(R.layout.item_function, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.text_function_item_title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.text_function_item_description);

            txtTitle.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);
            extratxt.setText(item_description[position]);
        }
		return rowView;
		
	};
}
