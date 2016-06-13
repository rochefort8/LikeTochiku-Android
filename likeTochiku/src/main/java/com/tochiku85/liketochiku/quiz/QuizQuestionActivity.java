package com.tochiku85.liketochiku.quiz;

import com.tochiku85.liketochiku.main.FunctionListActivity;
import com.tochiku85.liketochiku.main.MainActivity;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.R.id;
import com.tochiku85.liketochiku.R.layout;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizQuestionActivity extends ActionBarActivity implements OnClickListener {
    Button button_quiz_a1, button_quiz_a2, button_quiz_a3,button_quiz_a4;
    int requestCode;
    int quiz_number = 0 ; // 0-2
    int quiz_position = 0 ;
    int number_of_collect = 0 ;
    int table[] ;
    private QuizContents mQuizContents ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("クイズ");

        setButton() ;
        ParseApplication parseApplication = (ParseApplication)this.getApplication();
        mQuizContents = parseApplication.getQuizContents() ;
        table = getIntent().getIntArrayExtra("QUIZLIST") ;
        setQuiz() ;
    }

    private void setButton() {
        button_quiz_a1 = (Button) findViewById(R.id.button_quiz_a1);
        button_quiz_a1.setOnClickListener(this);
        button_quiz_a2 = (Button) findViewById(R.id.button_quiz_a2);
        button_quiz_a2.setOnClickListener(this);
        button_quiz_a3 = (Button) findViewById(R.id.button_quiz_a3);
        button_quiz_a3.setOnClickListener(this);
    }

    private void setQuiz()  {
        quiz_position = table[quiz_number] ;


        TextView textView ;
        textView = (TextView)findViewById(R.id.quiz_question_title);
        textView.setText(mQuizContents.getTitle(quiz_position));
        textView = (TextView) findViewById(R.id.text_quiz_question_content);
        textView.setText(mQuizContents.getQuestion(quiz_position));

        ImageView imageView = (ImageView)findViewById(id.image_question) ;
        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + mQuizContents.getQuestionImagePath(quiz_position));
        imageView.setImageBitmap(bitmap);

        button_quiz_a1.setText(mQuizContents.getChoices1(quiz_position));
        button_quiz_a2.setText(mQuizContents.getChoices2(quiz_position));
        button_quiz_a3.setText(mQuizContents.getChoices3(quiz_position));
    }

    private void setAnswer(int answer)  {
        TextView textView ;

        textView = (TextView)findViewById(id.quiz_question_result);
        Integer correct_answer = Integer.parseInt(mQuizContents.getAnswer(quiz_position)) ;

        if (answer == correct_answer) {
            textView.setText("正解！");
            number_of_collect++ ;
        } else {
            textView.setText("残念..");
        }
        textView = (TextView)findViewById(R.id.text_quiz_answer);

        switch (correct_answer) {
            case 1:
                textView.setText(mQuizContents.getChoices1(quiz_position));
                break;
            case 2:
                textView.setText(mQuizContents.getChoices2(quiz_position));
                break;
            case 3:
                textView.setText(mQuizContents.getChoices3(quiz_position));
                break;
            default:
                textView.setText("???");
                break;
        }
        textView = (TextView) findViewById(id.text_answer_description);
        textView.setText(mQuizContents.getDescription(quiz_position)) ;
        ImageView imageView = (ImageView)findViewById(id.image_answer) ;
        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + mQuizContents.getAnswerImagePath(quiz_position));
        imageView.setImageBitmap(bitmap);
    }

    private void setFinalResult()  {

        TextView textView ;
        String comment[] = {
                "残念やなぁ～",
                "まぁまぁやな。",
                "お、もちょいやん",
                "おぬし、やるのう"
        } ;
        textView = (TextView)findViewById(id.text_quiz_final_result);
        textView.setText(Integer.toString(number_of_collect) + "問正解(3問中)");
        textView = (TextView)findViewById(id.text_quiz_comment);
        textView.setText(comment[number_of_collect]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setAnswerView() {
        setContentView(layout.activity_quiz_answer);
        button_quiz_a1 = (Button) findViewById(R.id.button_quiz_answer_stop);
        button_quiz_a1.setOnClickListener(this);
        button_quiz_a2 = (Button) findViewById(R.id.button_quiz_answer_next);
        button_quiz_a2.setOnClickListener(this);
    }

    public void onClick(View v) {
         Intent intent;
        int answer = 0 ;

        switch (v.getId()) {
            case R.id.button_quiz_a1:
                setAnswerView();
                setAnswer(1) ;
                break ;
            case R.id.button_quiz_a2:
                setAnswerView();
                setAnswer(2) ;
                break ;
            case R.id.button_quiz_a3:
                setAnswerView();
                setAnswer(3) ;
                break ;
            case id.button_quiz_answer_next:
                if (quiz_number < 2) {
                    quiz_number++;
                    quiz_position = table[quiz_number] ;
                    setContentView(layout.activity_quiz_question);
                    setButton() ;
                    setQuiz();

                } else {
                    setContentView(layout.activity_quiz_result);
                    button_quiz_a1 = (Button) findViewById(id.button_quiz_back);
                    button_quiz_a1.setOnClickListener(this);
                    setFinalResult() ;
                    break;
                }
                break;

            case id.button_quiz_answer_stop:
                intent = new Intent(this, QuizMainActivity.class);
                startActivityForResult(intent, requestCode);
                break ;

            case id.button_quiz_back:
                intent = new Intent(this, QuizMainActivity.class);
                startActivityForResult(intent, requestCode);
                break ;
        }
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
                break;

            case R.id.action_others:
                intent = new Intent(this, FunctionListActivity.class) ;
                startActivityForResult(intent, requestCode);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true ;
    }

   /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz_question,
                    container, false);
            return rootView;
        }
    }
}
