package com.tochiku85.liketochiku.login;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.tochiku85.liketochiku.R;

import java.util.HashMap;

public class LoginRequestPasswordActivity extends ActionBarActivity implements View.OnClickListener {

    String mUserName, mPassword, mEmail,mGraduate ;
    ProgressDialog mProgressDialog ;
    Context mContext ;

    Button button_request_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_request_password);

        mContext = this ;

        final android.app.ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle("ユーザ登録用パスワード要求");



        button_request_password = (Button) findViewById(R.id.button_request_password);
        button_request_password.setOnClickListener(this);
    }

    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {
            case R.id.button_request_password:
                sendEmailForRequestPassword() ;
                break;
            default:
                break ;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_request_password, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void sendEmailForRequestPassword() {

        TextView textView = (TextView) findViewById(R.id.text_request_password_username);
        mUserName = textView.getText().toString();
        textView = (TextView) findViewById(R.id.text_request_password_email);
        mEmail = textView.getText().toString();
        Spinner spinner = (Spinner)findViewById(R.id.spinner_gradiates);
        int idx = spinner.getSelectedItemPosition();

        mGraduate = Integer.toString(50 + idx);

        CharSequence cs;
        String mailBody ;

        mailBody = "------------------------\n" ;
        mailBody += "[Name] "  + mUserName + "\n";
        mailBody += "[Email] " + mEmail + "\n";
        mailBody += "[Graduate] " + mGraduate + "\n";
        mailBody += "-----------------------\n" ;

        Log.d("sendMail", mailBody) ;

        HashMap<String, Object> params = new HashMap<String,Object>();
        params.put("toEmail", "yuji.ogihara.85@gmail.com");
        params.put("toName", "Yuji Ogihara");
        params.put("fromEmail",mEmail);
        params.put("fromName", mUserName);
        params.put("text", mailBody) ;
        params.put("subject", "[パスワード入手][Android]");

        mProgressDialog = ProgressDialog.show(this, "", "パスワード入手依頼のメールを送信しています...", true);

        ParseCloud.callFunctionInBackground("sendMail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("パスワード入手依頼に成功")
                            .setMessage("パスワード送付を依頼しました。確認が取れ次第、メールにてパスワードが送信されます。しばらくお待ちください。")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    mProgressDialog.dismiss();
                    Log.d("A", e.getMessage()) ;
                    new AlertDialog.Builder(mContext)
                            .setTitle("パスワード入手依頼に失敗")
                            .setMessage("送信できませんでした。")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
}
