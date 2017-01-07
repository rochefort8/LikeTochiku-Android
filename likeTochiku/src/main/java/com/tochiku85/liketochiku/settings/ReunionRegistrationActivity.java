package com.tochiku85.liketochiku.settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;

import java.util.HashMap;
import java.util.Map;

public class ReunionRegistrationActivity extends ActionBarActivity implements View.OnClickListener {

    TextView mTextName, mTextEmail,mTextGraduate,mTextMessage ;
    Context mContext ;
    private int requestCode ;
    private Button buttonSubmit ;
    private ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion_registration);

        mContext = this ;
        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("懇親会申し込み");

        buttonSubmit = (Button) findViewById(R.id.button_registration_submit);
        buttonSubmit.setOnClickListener(this);

        ParseUser currentUser = ParseUser.getCurrentUser();

        mTextName = (TextView) findViewById(R.id.text_reunion_registration_name);
        mTextName.setText(currentUser.getUsername());
        mTextEmail = (TextView) findViewById(R.id.text_reunion_registration_email);
        mTextEmail.setText(currentUser.getEmail());
        mTextGraduate = (TextView) findViewById(R.id.text_reunion_registration_graduate);
        mTextGraduate.setText(currentUser.getString("graduate"));
        mTextMessage = (TextView) findViewById(R.id.text_reunion_registration_message);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_registration_submit:
                doReunionRegistration();
                break;
        }
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
        Intent intent ;
        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class) ;
                startActivityForResult(intent, requestCode);
                break ;

            case R.id.action_photo_relay:
                intent = new Intent(this, PhotosActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;

            case R.id.action_settings:
            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true ;
    }

    public void doReunionRegistration() {

        CharSequence cs;
        String mailBody ;

        mailBody = "------------------------\n" ;
        mailBody += "[Name] "  + mTextName.getText().toString() + "\n";
        mailBody += "[Email] " + mTextEmail.getText().toString() + "\n";
        mailBody += "[Graduate] " + mTextGraduate.getText().toString() + "\n";
        mailBody += "[Message] " + mTextMessage.getText().toString() + "\n";
        mailBody += "-----------------------\n" ;

        Log.d("sendMail", mailBody) ;

        HashMap<String, Object> params = new HashMap<String,Object>();
        params.put("toEmail", "yuji.ogihara.85@gmail.com");
        params.put("toName", "Yuji Ogihara");
        params.put("fromEmail",mTextEmail.getText().toString());
        params.put("fromName", mTextName.getText().toString());
        params.put("text", mailBody) ;
        params.put("subject", "[懇親会申し込み][Android]");

        mProgressDialog = ProgressDialog.show(this, "", "Sending email for registration...", true);

        ParseCloud.callFunctionInBackground("sendMail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("Registration")
                            .setMessage("Success")
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
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
