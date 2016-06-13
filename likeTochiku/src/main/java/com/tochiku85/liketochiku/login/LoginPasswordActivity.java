package com.tochiku85.liketochiku.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.main.MainActivity;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginPasswordActivity extends ActionBarActivity implements View.OnClickListener {

    Button button_enter_password, button_get_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("ユーザ登録パスワード");


        button_enter_password = (Button) findViewById(R.id.button_enter_password);
        button_enter_password.setOnClickListener(this);

        button_get_password = (Button) findViewById(R.id.button_get_password);
        button_get_password.setOnClickListener(this);

    }

    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {
            case R.id.button_enter_password:
                verifyPassword() ;
                break;
            case R.id.button_get_password:
                startActivity(new Intent(this, LoginRequestPasswordActivity.class));
                break;
            default:
                break ;

        }
    }

    private void verifyPassword() {
        TextView textView = (TextView) findViewById(R.id.text_secret_password);
        String password = textView.getText().toString();

        String expectedResultString = "yBP/Cl30fQERqHmhToFeXw==\n" ;
        String secretkey = "tochiku85";
        SecretKeySpec key;

        byte[] bytes = new byte[ 128 / 8 ];
        byte[] keys = null;
        try {
            keys = secretkey.getBytes( "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for( int i = 0; i < secretkey.length(); i++ )
        {
            if( i >= bytes.length )
                break;
            bytes[ i ] = keys[ i ];
        }
        key = new SecretKeySpec( bytes, "AES" );
        byte[] de = null;

        try {
            Cipher cipher = Cipher.getInstance( "AES" );
            cipher.init(Cipher.ENCRYPT_MODE, key);
            de = cipher.doFinal(password.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encryptedString = Base64.encodeToString(de, Base64.DEFAULT);

        Log.d("ENC", encryptedString);
        Log.d("EXP", expectedResultString);

        if (encryptedString.equals(expectedResultString)) {
            startActivityForResult(new Intent(this, UserRegistrationActivity.class) , 0);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("パスワードが間違っています。")
                    .setMessage("もう一度入力をお願いします。")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_password, menu);
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
