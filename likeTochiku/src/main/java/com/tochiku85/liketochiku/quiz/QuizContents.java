package com.tochiku85.liketochiku.quiz;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.tochiku85.liketochiku.contents.LikeTochikuContents;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizContents extends LikeTochikuContents {

    private String stringKeyArray[] = { "title", "quiz", "difficulty", "choice1",
                                            "choice2","choice3", "answer", "description"} ;
    private String fileKeyArray[] = { "quiz_image" , "answer_image" } ;

    public QuizContents(Context context) {
        super(context, "Quiz") ;
        setKeyArray(stringKeyArray, fileKeyArray);
    }
    public String getTitle(int position)      {  return getObjectString(position,"title"); }
    public String getQuestion(int position)   {  return getObjectString(position, "quiz");}
    public String getDifficulty(int position) {  return getObjectString(position,"difficulty"); }
    public String getChoices1(int position)   {  return getObjectString(position, "choice1");}
    public String getChoices2(int position)   {  return getObjectString(position, "choice2");}
    public String getChoices3(int position)   {  return getObjectString(position, "choice3"); }
    public String getAnswer(int position)     {  return getObjectString(position, "answer"); }
    public String getDescription(int position){  return getObjectString(position, "description"); }
    public String getQuestionImagePath(int position){  return getFilePath(position, "quiz_image"); }
    public String getAnswerImagePath(int position)  { return getFilePath(position, "answer_image"); }

}
