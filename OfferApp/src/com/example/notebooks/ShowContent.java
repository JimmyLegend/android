package com.example.notebooks;

import com.example.clock.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
public class ShowContent extends Activity implements View.OnClickListener{
	private Button quitBtn,deleteBtn;
    private TextView userTv,contentTv;
    private ImageView img;
    private VideoView video;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_content);
        quitBtn = (Button) findViewById(R.id.clickBtn_quit);
        deleteBtn = (Button) findViewById(R.id.clickBtn_delite);
        userTv = (TextView) findViewById(R.id.clickTv_user);
        contentTv = (TextView) findViewById(R.id.clickTv_content);
        img = (ImageView) findViewById(R.id.clickIv_img);
        video = (VideoView) findViewById(R.id.clickVv_video);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
        quitBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        if (getIntent().getStringExtra(NotesDB.PATH).equals("null")){
            img.setVisibility(View.GONE);
        }else {
            img.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")){
            video.setVisibility(View.GONE);
        }else {
            video.setVisibility(View.VISIBLE);
        }
        userTv.setText(getIntent().getStringExtra(NotesDB.NAME));
        contentTv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NotesDB.PATH));
        img.setImageBitmap(bitmap);
        video.setVideoURI(Uri.parse(getIntent().getStringExtra(NotesDB.VIDEO)));
        video.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clickBtn_quit:
                finish();
                break;
            case R.id.clickBtn_delite:
                deleteData();
                finish();
                break;
            default:
                break;
        }
    }

    public void deleteData(){
        dbReader.delete(NotesDB.TABLE_NAME,"_id = "+getIntent().getIntExtra(NotesDB.ID,0),null);
    }
}
