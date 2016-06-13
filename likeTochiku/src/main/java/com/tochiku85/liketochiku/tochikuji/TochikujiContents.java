package com.tochiku85.liketochiku.tochikuji;

import android.content.Context;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

public class TochikujiContents extends LikeTochikuContents {

    private String stringKeyArray[] = { } ;
    private String fileKeyArray[] = { "image" } ;

    public TochikujiContents(Context context) {
        super(context, "Tochikuji") ;
        setKeyArray(stringKeyArray, fileKeyArray);
    }
}
