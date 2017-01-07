package com.tochiku85.liketochiku.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;

import java.util.HashMap;

public class UserFeedbackActivity extends ActionBarActivity implements View.OnClickListener {

    TextView mTextMessage ;
    String mTextName, mTextEmail,mTextGraduate ;
    Context mContext ;
    private int requestCode ;
    private Button buttonSubmit ;
    private ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        mContext = this ;
        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("一言どうぞ！");

        buttonSubmit = (Button) findViewById(R.id.button_send_feedback);
        buttonSubmit.setOnClickListener(this);

        ParseUser currentUser = ParseUser.getCurrentUser();
        mTextName  = currentUser.getUsername();
        mTextEmail = currentUser.getEmail();
        mTextGraduate = currentUser.getString("graduate");

        mTextMessage = (TextView) findViewById(R.id.text_feedback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_photo_relay:
                intent = new Intent(this, PhotosActivity.class);
                startActivity(intent);
                break;

            case R.id.action_settings:
            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_send_feedback:
                doSubmit();
                break;
        }
    }

    public void doSubmit() {

        CharSequence cs;
        String mailBody ;

        mailBody = "------------------------\n" ;
        mailBody += "[Name] "  + mTextName + "\n";
        mailBody += "[Email] " + mTextEmail + "\n";
        mailBody += "[Graduate] " + mTextGraduate + "\n";
        mailBody += "[Message] " + mTextMessage.getText().toString() + "\n";
        mailBody += "-----------------------\n" ;

        Log.d("sendMail", mailBody) ;

        HashMap<String, Object> params = new HashMap<String,Object>();
        params.put("toEmail", "yuji.ogihara.85@gmail.com");
        params.put("toName", "Yuji Ogihara");
        params.put("fromEmail",mTextEmail);
        params.put("fromName", mTextName);
        params.put("text", mailBody) ;
        params.put("subject", "[とーち君へ一言][Android]");

        mProgressDialog = ProgressDialog.show(this, "", "Sending email for registration...", true);

        ParseCloud.callFunctionInBackground("sendMailForMessage", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("Registration")
                            .setMessage("Success")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("Registration")
                            .setMessage("Failure")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
}
