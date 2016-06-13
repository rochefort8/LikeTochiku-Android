package com.tochiku85.liketochiku.kitakyushu;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

public class KitakyushuContents extends LikeTochikuContents  {

    private String stringKeyArray[] = { "title", "owner", "graduate", "link","caption" } ;
        private String fileKeyArray[] = { "image" } ;

    public KitakyushuContents(Context context) {
        super(context, "Ad");
        setKeyArray(stringKeyArray, fileKeyArray);
    }
    public String getTitle(int position)     {  return getObjectString(position,"title"); }
    public String getLink(int position)      {  return getObjectString(position,"link"); }
    public String getOwner(int position)     {  return getObjectString(position,"owner"); }
    public String getGraduate(int position)     {  return getObjectString(position,"graduate"); }
    public String getCaption(int position)      {  return getObjectString(position,"caption"); }
    public String getImagePath(int position) {  return getFilePath(position,"image"); }
}
