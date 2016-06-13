package com.tochiku85.liketochiku.news;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

import java.util.ArrayList;

public class NewsContents extends LikeTochikuContents  {

    private String stringKeyArray[] = { "title", "news", "link" } ;
        private String fileKeyArray[] = { "image" } ;

    public NewsContents(Context context) {
        super(context, "News");
        setKeyArray(stringKeyArray, fileKeyArray);
    }
    public String getTitle(int position)     {  return getObjectString(position,"title"); }
    public String getArticle(int position)   {  return getObjectString(position,"news"); }
    public String getLink(int position)      {  return getObjectString(position,"link"); }
    public String getImagePath(int position) {  return getFilePath(position,"image"); }

}
