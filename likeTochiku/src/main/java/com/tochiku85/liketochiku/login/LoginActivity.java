/*
 *  Copyright (c) 2014, Parse, LLC. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 *  copy, modify, and distribute this software in source code or binary form for use
 *  in connection with the web services and APIs provided by Parse.
 *
 *  As with any software that integrates with the Parse platform, your use of
 *  this software is subject to the Parse Terms of Service
 *  [https://www.parse.com/about/terms]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.tochiku85.liketochiku.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.quiz.QuizQuestionActivity;

import java.util.Arrays;

/**
 * Shows the user profile. This simple activity can function regardless of whether the user
 * is currently logged in.
 */
public class LoginActivity extends AppCompatActivity  implements OnClickListener {
    private static final int LOGIN_REQUEST = 0;

    Button button_enter_login, button_enter_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        final android.app.ActionBar actionBar = getActionBar();
//        final android.app.ActionBar actionBar =
        getSupportActionBar().setTitle("いいね！東筑");

        button_enter_login = (Button) findViewById(R.id.button_enter_login);
        button_enter_login.setOnClickListener(this);

        button_enter_registration = (Button) findViewById(R.id.button_enter_regstration);
        button_enter_registration.setOnClickListener(this);

   }

    public void onClick(View v) {

        Intent intent ;
        switch (v.getId()) {
            case R.id.button_enter_login:
                intent = new Intent (this,DoLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.button_enter_regstration:
                startActivity(new Intent(this, LoginPasswordActivity.class));
                break;
            default:
                break ;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startActivityForResult(new Intent(this, MainActivity.class) , 0);
            finish() ;
        }
    }
}


