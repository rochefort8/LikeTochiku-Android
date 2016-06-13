package com.tochiku85.liketochiku.schoolroute;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

public class SchoolRouteContents extends LikeTochikuContents  {

    private String stringKeyArray[] = { "title", "description", "id" } ;
    private String fileKeyArray[] = { "image"} ;

    public SchoolRouteContents(Context context) {
        super(context, "SchoolRoute");
        setKeyArray(stringKeyArray, fileKeyArray);
    }
    public String getTitle(int position)     {  return getObjectString(position,"title"); }
    public String getDescription(int position)   {  return getObjectString(position,"description"); }
    public String getVideoId(int position)   {  return getObjectString(position, "video_id"); }
    public String getImagePath(int position) {  return getFilePath(position, "image"); }
}
