package com.tochiku85.liketochiku.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.main.MainActivity;

import java.util.List;


public class DoLoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_do_login, button_password_forgotten;
    String mUserName;
    String mPassword;
    Context mContext ;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_login);

        mContext = this ;

//        final android.app.ActionBar actionBar = getActionBar();
//        getSupportActionBar().setTitle("ログイン");
        getSupportActionBar().setTitle("ログイン");

        button_do_login = (Button) findViewById(R.id.button_do_login);
        button_do_login.setOnClickListener(this);

        button_password_forgotten = (Button) findViewById(R.id.button_password_forgotten);
        button_password_forgotten.setOnClickListener(this);

    }

    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {
            case R.id.button_do_login:
                doLogin();
                break;
            case R.id.button_password_forgotten:
                doPasswordForgotten();
                break;
            default:
                break ;

        }
    }

    private void doPasswordForgotten() {
        CharSequence cs;

        TextView textView = (TextView) findViewById(R.id.text_username_or_email);

        mUserName = textView.getText().toString();

        Log.d("UserName", mUserName);

        mProgressDialog = ProgressDialog.show(this,"","パスワード再設定のメールを送信しています...",true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", mUserName);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() > 0) {
                    ParseObject object = objects.get(0);
                    mUserName = object.getString("email");
                }
                Log.d("UserName", mUserName);
                ParseUser.requestPasswordResetInBackground(mUserName,new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            mProgressDialog.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setTitle(mUserName + "宛にメール送信")
                                    .setMessage("文中の指示に従いパスワードの再設定を行ってください。")
                                    .setPositiveButton("OK", null)
                                    .show();
                        } else {
                            mProgressDialog.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setTitle(mUserName + "宛にメール送信出来ませんでした。")
                                    .setMessage("メールアドレス、ネットワーク接続状態を確認の上再度お試しください。")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
                });
            }
        });
    }

    private void doLogin() {

        CharSequence cs;

        TextView textView = (TextView) findViewById(R.id.text_username_or_email);

        mUserName = textView.getText().toString();

        Log.d("UserName", mUserName);
        textView = (TextView) findViewById(R.id.text_password);
        mPassword = textView.getText().toString();

        mProgressDialog = ProgressDialog.show(this,"","ログインしています...",true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", mUserName);
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() > 0) {
                    ParseObject object = objects.get(0);
                    mUserName = object.getString("username");
                }
                Log.d("UserName", mUserName);

                ParseUser.logInInBackground(mUserName, mPassword, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            mProgressDialog.dismiss();
                            startActivityForResult(new Intent(mContext, MainActivity.class) , 0);
                            finish() ;
                        } else {
                            mProgressDialog.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setTitle("ログインできませんでした。")
                                    .setMessage("メールアドレスもしくはパスワードが間違っています.")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_do_login, menu);
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
