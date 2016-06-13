package com.tochiku85.liketochiku.news;

import android.content.Context;
import android.app.Activity;
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
import com.tochiku85.liketochiku.contents.LikeTochikuContents;
import com.tochiku85.liketochiku.photorelay.InstagramPhoto;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<ParseObject> {
    NewsContents mNewsContents ;

	public NewsListAdapter(Context context,NewsContents contents) {
		super(context, R.layout.item_news, contents.getObjectList());
        mNewsContents = contents ;
	}
	
	public View getView(int position,View view,ViewGroup parent) {

        if (view == null) {
            // create a new view from scratch, otherwise use the view passed in
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        }

		TextView txtTitle   = (TextView) view.findViewById(R.id.news_title);
        TextView txtArticle = (TextView) view.findViewById(R.id.news_article);
        ImageView imageView = (ImageView) view.findViewById(R.id.news_image);

/*
        news = getItem(position) ;
        txtTitle.setText(news.stringArray.get(0));
        txtArticle.setText(news.stringArray.get(1));
        Bitmap bitmap = BitmapFactory.decodeFile(getContext().getFilesDir() + "/" + news.filePathArray.get(0));
*/

        txtTitle.setText(mNewsContents.getTitle(position));
        txtArticle.setText(mNewsContents.getArticle(position));
        Bitmap bitmap = BitmapFactory.decodeFile(getContext().getFilesDir() + "/" + mNewsContents.getImagePath(position));

        imageView.setImageBitmap(bitmap);

        return view ;
	};
}
