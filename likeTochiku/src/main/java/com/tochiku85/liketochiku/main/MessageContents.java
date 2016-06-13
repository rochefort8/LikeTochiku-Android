package com.tochiku85.liketochiku.main;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

public class MessageContents extends LikeTochikuContents  {

    private String stringKeyArray[] = { "title", "message"} ;
        private String fileKeyArray[] = { "image" } ;

    public MessageContents(Context context) {
        super(context, "Message");
        setKeyArray(stringKeyArray, fileKeyArray);
        setGetFirstObject() ;
    }
    public String getTitle(int position)      {  return getObjectString(position,"title"); }
    public String getMessage(int position)    {  return getObjectString(position,"message"); }
    public String getImagePath(int position)  {  return getFilePath(position,"image"); }
}
