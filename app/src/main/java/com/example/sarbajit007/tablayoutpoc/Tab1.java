package com.example.sarbajit007.tablayoutpoc;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by sarbajit007 on 12/2/2017.
 */

public class Tab1 extends Fragment {
    Button next;
    LinearLayout image_layout;
    ImageView imgview;
    Context context;
    int id;
    ClipboardManager clipboardManager;
    String image_link;
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assetCheck();
        next=(Button)getView().findViewById(R.id.button_next);
        image_layout=(LinearLayout)getView().findViewById(R.id.image_layout);
        imgview=(ImageView)getView().findViewById(R.id.imageView2);
        context =imgview.getContext();
        id = R.drawable.androidtest;
        imgview.setImageResource(id);
         clipboardManager=(ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData clipData=ClipData.newPlainText("url",image_link);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getActivity(),"COPIED!",Toast.LENGTH_SHORT).show();

            }
        });

    }
    public  void assetCheck()
    {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("question.csv")));
            String[] headers=bufferedReader.readLine().split(",");

            SQLiteDatabase sqLiteDatabase=createTable(headers);
            String line="";
            while((line=bufferedReader.readLine())!=null)
            {

                insertDatas(sqLiteDatabase,line);
            }



        }
        catch(IOException e)
        {
            Log.e("FILE ERROR"," CHECK");
        }
    }
    public SQLiteDatabase createTable(String[] headers)
    {
        SQLiteDatabase sqLiteDatabase=getActivity().openOrCreateDatabase("ElementaryDb", Context.MODE_PRIVATE,null);
        deleteTable(sqLiteDatabase);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS question_tbl(level INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question_no VARCHAR,"+
                " image_name VARCHAR," +
                " clue_one TEXT," +
                " clue_two TEXT," +
                " clue_three TEXT," +
                " clue_four TEXT," +
                " hint TEXT," +
                " img_link TEXT,"+
                " answer VARCHAR);");
        return sqLiteDatabase;
    }
    public void insertDatas(SQLiteDatabase sqLiteDatabase,String line)
    {

        sqLiteDatabase.execSQL("INSERT INTO question_tbl(question_no,image_name, clue_one, clue_two, clue_three, clue_four, hint,img_link, answer) VALUES( "+line+" );");
        display_data(sqLiteDatabase);

    }
    public void deleteTable(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS question_tbl;");
        Log.e("DELETE"," SUCCESS ");

    }
    public  void display_data(SQLiteDatabase sqLiteDatabase)
    {
        final Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM question_tbl;",null);
        cursor.moveToFirst();
        image_link=cursor.getString(8).toString();
        String image_name=cursor.getString(2);




    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab1,container,false);
    }

}
