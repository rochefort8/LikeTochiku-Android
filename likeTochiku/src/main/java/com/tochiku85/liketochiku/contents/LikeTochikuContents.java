package com.tochiku85.liketochiku.contents;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikeTochikuContents {
    ParseQuery<ParseObject> mParseQuery ;
    private String className ;
    private String stringKeyArray[] ;
    private String fileKeyArray[] ;
    private Context context ;
    private LikeTochikuContentsCallback _callback;
    private Object _callback_param ;
    private boolean isGetFirstObject ;
    private ArrayList<ParseObject> parseObjects ;

    public LikeTochikuContents(Context context,String className) {
        this.context = context ;
        this.className = className ;
        isGetFirstObject = false ;
        parseObjects = new ArrayList<ParseObject>() ;

        mParseQuery = new ParseQuery<ParseObject>(className);
        mParseQuery.orderByDescending("updatedAt");
    }

    public void setGetFirstObject() { isGetFirstObject = true ;}
    public void setAdditionalQuery(String key, String value) {
        mParseQuery.whereEqualTo( key, value) ;
    }

    public void setCallback(LikeTochikuContentsCallback callback) {
         _callback = callback;
     }
    public void setCallback(LikeTochikuContentsCallback callback,Object callback_param) {
        _callback = callback;
        _callback_param = callback_param ;
    }

    public void setKeyArray(String stringKeyArray[], String fileKeyArray[]) {
        this.stringKeyArray = stringKeyArray ;
        this.fileKeyArray = fileKeyArray ;
    }

    public  ArrayList<ParseObject> getObjectList() {return parseObjects ; }
    public int getObjectCount() { return parseObjects.size() ;}

    public String getObjectString(int position,String key) {
        return parseObjects.get(position).getString(key) ;
    }
    public String getFilePath(int position,String key) {
        ParseObject parseObject = parseObjects.get(position) ;
        final ParseFile parseFile = (ParseFile) parseObject.get(key);
        return parseFile.getName() ;
    }

    public int loadData() {

//        ParseQuery<ParseObject>mParseQuery = new ParseQuery<ParseObject>(className);
//       mParseQuery.orderByDescending("updatedAt");

       mParseQuery.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Log.d("Downloader", "Error: " + e.getMessage());
                    if (_callback != null) {
                        if (_callback_param != null) {
                            _callback.callbackMethod(_callback_param);
                        } else {
                            _callback.callbackMethod();
                        }
                    }
                    return;
                }

                if (isGetFirstObject == true) {
                    ParseObject o = objects.get(0);
                    objects.clear();
                    objects.add(o);
                }

                boolean wasUpdated = false;
                if (parseObjects != null) {
                    if (parseObjects.size() == objects.size()) {
                        for (int i = 0; i < objects.size(); i++) {
                            String src, dst;
                            src = objects.get(i).getObjectId();
                            dst = parseObjects.get(i).getObjectId();
                            if (dst.equals(src)) {
                                ;;
                            } else {
                                wasUpdated = true;
                                break;
                            }
                        }
                    } else {
                        wasUpdated = true;
                    }
                } else {
                    wasUpdated = true;
                }
                if (wasUpdated == false) {
                    if (_callback != null) {
                        if (_callback_param != null) {
                            _callback.callbackMethod(_callback_param);
                        } else {
                            _callback.callbackMethod();
                        }
                    }
                    return;
                }

                parseObjects.clear();
                parseObjects.addAll(objects);

                if (fileKeyArray.length == 0) {
                    if (_callback != null) {
                        if (_callback_param != null) {
                            _callback.callbackMethod(_callback_param);
                        } else {
                            _callback.callbackMethod();
                        }
                    }
                }

                for (int i = 0; i < objects.size(); i++) {

                    ParseObject parseObject = objects.get(i);
                    for (int j = 0; j < fileKeyArray.length; j++) {
                        String key = fileKeyArray[j];
                        final ParseFile parseFile = (ParseFile) parseObject.get(key);

                        parseFile.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {

                                if (e == null) {
                                    String fileName = parseFile.getName();
                                    Log.d(className, "We've got data. name=" + fileName + "/size=" + data.length);

                                    FileOutputStream fileOutputStream = null;
                                    try {
                                        fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                                        fileOutputStream.write(data);
                                        fileOutputStream.close();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    Log.d("Downloader", "There was a problem downloading the data.");
                                }
                                if (_callback != null) {
                                    if (_callback_param != null) {
                                        _callback.callbackMethod(_callback_param);
                                    } else {
                                        _callback.callbackMethod();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        return 0;
    }

    public interface LikeTochikuContentsCallback {
         public void callbackMethod(Object param);
         public void callbackMethod();

    }

}
