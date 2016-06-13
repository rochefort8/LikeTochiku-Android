package com.tochiku85.liketochiku.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.tochiku85.liketochiku.R;
import com.tochiku85.liketochiku.kitakyushu.KitakyushuActivity;
import com.tochiku85.liketochiku.login.LoginActivity;
import com.tochiku85.liketochiku.memories.MemoriesActivity;
import com.tochiku85.liketochiku.news.NewsActivity;
import com.tochiku85.liketochiku.photorelay.PhotosActivity;
import com.tochiku85.liketochiku.quiz.QuizMainActivity;
import com.tochiku85.liketochiku.schoolroute.SchoolRouteActivity;
import com.tochiku85.liketochiku.schoolroute.SchoolRouteVideoViewActivity;
import com.tochiku85.liketochiku.settings.ReunionRegistrationActivity;
import com.tochiku85.liketochiku.settings.SettingsActivity;
import com.tochiku85.liketochiku.tochikuji.TochikujiActivity;
import com.tochiku85.liketochiku.umanatsu.UmanatsuActivity;
import com.tochiku85.liketochiku.utils.parse.ParseApplication;


public class FunctionListActivity extends ActionBarActivity {
    ListView list;
    String[] itemname ={
            "SEP:2015年度東京東筑会懇親会",
            "特設サイト",
            "参加申込",
            "SEP:いいね！一覧",
            "ホーム",
            "写真メッセージ",
            "クイズ",
            "ニュース",
            "とーちくじ!",
            "いいね！東筑応援団",
            "記念品通販",
            "通学路",
            "SEP:設定",
            "設定",
            "ひとこと！",
            "ログアウト"
    };

    String[] item_description ={
            "SEP:",
            "特設サイト",
            "1クリックで申し込めます!",
            "SEP:",
            "カウントダウン、当番期より",
            "みなさんの想い、伝わるかな？",
            "とーちくんからの出題！",
            "卒業生の活躍やイベント満載",
            "運命や、いかに？",
            "OB・北九州の企業・活動を紹介！",
            "懇親会記念品を先行販売します!",
            "東筑への道～あの通学路は、今",
            "SEP:",
            "ユーザ情報と各機能の設定",
            "とーちくんへ一言、お願いします！",
            "またよろしく！"
    };


    Integer[] imgid = {
            0,
            R.drawable.ic_menu_special_site,
            R.drawable.ic_menu_tochiku,
            0,
            R.drawable.ic_menu_home32,
            R.drawable.ic_menu_scketchbook32,
            R.drawable.ic_menu_questions,
            R.drawable.ic_menu_news,
            R.drawable.ic_menu_tochikuji,
            R.drawable.ic_menu_flag,
            R.drawable.ic_menu_shopping,
            R.drawable.ic_menu_schoolroute,
            0,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_feedback,
            R.drawable.ic_menu_logout,
    };

    Class<?>[] activity_list = {
            null,
            ReunionWebsiteActivity.class,
            ReunionRegistrationActivity.class,
            null,
            MainActivity.class,
            PhotosActivity.class,
            QuizMainActivity.class,
            NewsActivity.class,
            TochikujiActivity.class,
            KitakyushuActivity.class,
            SalesActivity.class,
            SchoolRouteActivity.class,
            null,
            SettingsActivity.class,
            UserFeedbackActivity.class,
            null,
    };

    int requestCode ;
    private Context mCtx;
    private ParseApplication parseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_list);
        FunctionListAdapter adapter=new FunctionListAdapter(this, itemname, item_description,imgid);
        list=(ListView)findViewById(R.id.function_listView);
        list.setAdapter(adapter);
        mCtx = this ;
        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle("いいね！東筑");
        parseApplication = (ParseApplication)this.getApplication() ;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               Log.d("ITEM No",Integer.toString(position)) ;
                if (activity_list[position] != null){
                    String Slecteditem = itemname[+position];
                    Intent intent = new Intent(mCtx, activity_list[+position]);
                    startActivityForResult(intent, requestCode);
                } else {
                    if (position == itemname.length - 1) {
                        new AlertDialog.Builder(mCtx)
                                .setTitle("ログアウト")
                                .setMessage("ログアウトしますか?")
                                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ParseUser.logOut();
                                        startActivityForResult(new Intent(mCtx, LoginActivity.class), 0);
                                    }
                                })
                                .setNegativeButton("いいえ", null)
                                .show();


                    }
                }
            }
        });
    }

}
