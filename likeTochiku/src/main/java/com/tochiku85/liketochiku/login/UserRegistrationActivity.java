package com.tochiku85.liketochiku.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.main.MainActivity;

public class UserRegistrationActivity extends ActionBarActivity implements View.OnClickListener{

    Button button_registration ;
    String mUserName, mPassword, mEmail,mGraduates ;
    Context mContext ;
    ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        mContext = this ;

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("ユーザ登録");

        button_registration = (Button) findViewById(R.id.button_registration);
        button_registration.setOnClickListener(this);

    }

    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {
            case R.id.button_registration:
                doUserRegistration() ;
                break;
            default:
                break ;

        }
    }

    private void doUserRegistration() {

        CharSequence cs;

        TextView textView = (TextView) findViewById(R.id.text_registration_username);
        mUserName = textView.getText().toString();
        textView = (TextView) findViewById(R.id.text_reunion_registration_email);
        mEmail = textView.getText().toString();
        textView = (TextView) findViewById(R.id.text_registration_password);
        mPassword = textView.getText().toString();

        Spinner spinner = (Spinner)findViewById(R.id.spinner_gradiates);
        int idx = spinner.getSelectedItemPosition();

        mGraduates = Integer.toString(50+idx);

        ParseUser user = new ParseUser();
        user.setUsername(mUserName);
        user.setEmail(mEmail);
        user.setPassword(mPassword);
        user.put("graduate", mGraduates);

        mProgressDialog = ProgressDialog.show(this, "", "ユーザ登録を行っています...", true);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("ユーザ登録")
                            .setMessage("成功しました。ログインしますか？")
                            .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(mContext, MainActivity.class), 0);
                                    finish();
                                }
                            })
                            .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ParseUser.logOut();
                                    startActivityForResult(new Intent(mContext, LoginActivity.class), 0);
                                    finish();
                                }
                            })
                            .show();
                } else {
                    mProgressDialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle("ユーザ登録できませんでした。")
                            .setMessage("ネットワークの接続状態を確認の上、再度実行をお願いします。")
                            .setPositiveButton("OK",null)
                            .show();

                }
            }
        });

    //    mProgressDialog = ProgressDialog.show(this,"","Processing login...",true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_registration, menu);
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
}
