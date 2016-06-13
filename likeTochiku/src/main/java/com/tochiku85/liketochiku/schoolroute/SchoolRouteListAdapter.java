package com.tochiku85.liketochiku.schoolroute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.news.NewsContents;

public class SchoolRouteListAdapter extends ArrayAdapter<ParseObject> {
    SchoolRouteContents mContents ;

	public SchoolRouteListAdapter(Context context, SchoolRouteContents contents) {
		super(context, R.layout.item_list_schoolroute, contents.getObjectList());
        mContents = contents ;
	}
	
	public View getView(int position,View view,ViewGroup parent) {

        if (view == null) {
            // create a new view from scratch, otherwise use the view passed in
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_list_schoolroute, parent, false);
        }

		TextView txtTitle   = (TextView) view.findViewById(R.id.schoolroute_title);
        TextView txtDescription = (TextView) view.findViewById(R.id.schoolroute_description);

        ImageView imageView = (ImageView) view.findViewById(R.id.schoolroute_image);

        txtTitle.setText(mContents.getTitle(position));
        txtDescription.setText(mContents.getDescription(position));

        // Thumbnail
        Bitmap bitmap = BitmapFactory.decodeFile(getContext().getFilesDir() + "/" + mContents.getImagePath(position));
        imageView.setImageBitmap(bitmap);
        return view ;
	};
}
