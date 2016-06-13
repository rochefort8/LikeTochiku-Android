package com.tochiku85.liketochiku.main;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

public class CountdownContents extends LikeTochikuContents  {

    private String stringKeyArray[] = { "title"} ;
        private String fileKeyArray[] = { "image" } ;

    public CountdownContents(Context context) {
        super(context, "Countdown");
        setKeyArray(stringKeyArray, fileKeyArray);
    }
    public String getTitle(int position)      {  return getObjectString(position,"title"); }
    public String getImagePath(int position)  {  return   getFilePath(position,"image"); }
}
