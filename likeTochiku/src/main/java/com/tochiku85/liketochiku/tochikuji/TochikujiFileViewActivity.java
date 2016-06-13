package com.tochiku85.liketochiku.tochikuji;

import java.io.File;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View; 
import android.content.DialogInterface;

public class TochikujiFileViewActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tochikuji_file_view);
        ListView lv = (ListView) findViewById(R.id.tochikuji_file_listview);
		
        String[] members = { "mhidaka", "rongon_xp", "kacchi0516", "kobashinG",
                "seit", "kei_i_t", "furusin_oriver" };
         
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            	ListView l = (ListView)parent ;
            	String f = (String) l.getItemAtPosition(position) ;
            	
            	Log.d ("LONG","name=" + f) ;
            	File file = new File(getFilesDir(),f) ;
            	file.delete() ;
            	ArrayAdapter<String> adapter = (ArrayAdapter<String>)l.getAdapter();
            	adapter.remove(f);
                return false;
            }
        });
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        
		File dir = getFilesDir() ;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; ++i) {
			File d = new File(files[i].getAbsolutePath()) ;
			if (d.isDirectory()) {
				File[] f = d.listFiles() ;
				for (int j = 0;j < f.length;j++) {
					File _f = f[j] ;
					if (_f.isFile() && (_f.getTotalSpace()!=0)) {
						adapter.add(d.getName() + "/" + _f.getName()) ;
					}
				}
			} else {
				adapter.add(d.getName()) ;
			}
		}
        lv.setAdapter(adapter);
  	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tochikuji_file_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
