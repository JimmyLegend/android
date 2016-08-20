package com.example.notebooks;

import com.example.clock.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
public class mAdapter extends BaseAdapter{
	 private Context context;
	    private Cursor cursor;

	    public mAdapter(Context context, Cursor cursor) {
	        this.cursor = cursor;
	        this.context = context;
	    }

	    @Override
	    public int getCount() {
	        return cursor.getCount();
	    }

	    @Override
	    public Object getItem(int i) {
	        return cursor.getPosition();
	    }

	    @Override
	    public long getItemId(int i) {
	        return i;
	    }

	    @Override
	    public View getView(int i, View view, ViewGroup viewGroup) {
	        ViewHolder viewHolder;
	        LayoutInflater layoutInflater;
	        View layout;
	        if (view == null) {
	            viewHolder = new ViewHolder();
	            layoutInflater = LayoutInflater.from(context);
	            layout = layoutInflater.inflate(R.layout.list_item, null);
	            viewHolder.contentView = (TextView) layout.findViewById(R.id.listItem_content);
	            viewHolder.timeView = (TextView) layout.findViewById(R.id.listItem_time);
	            viewHolder.imgView = (ImageView) layout.findViewById(R.id.listItem_iv_img);
	            viewHolder.videoView = (ImageView) layout.findViewById(R.id.listItem_Video);
	            viewHolder.bijiImgView = (ImageView) layout.findViewById(R.id.listItem_img_biji);
	            layout.setTag(viewHolder);
	        } else {
	            layout = view;
	            viewHolder = (ViewHolder) layout.getTag();
	        }
	        cursor.moveToPosition(i);
	        String content = cursor.getString(cursor.getColumnIndex("content"));
	        String time = cursor.getString(cursor.getColumnIndex("time"));
	        String url = cursor.getString(cursor.getColumnIndex("path"));
	        String urlVideo = cursor.getString(cursor.getColumnIndex("video"));
	        viewHolder.contentView.setText(content);
	        viewHolder.timeView.setText(time);
	        if (!url.equals("null")) {
	            viewHolder.imgView.setVisibility(View.VISIBLE);
	            viewHolder.bijiImgView.setVisibility(View.GONE);
	            viewHolder.videoView.setVisibility(View.GONE);
	            viewHolder.imgView.setImageBitmap(getImageBitmap(url, 200, 200));
	        }
	        if (!urlVideo.equals("null")){
	            viewHolder.videoView.setVisibility(View.VISIBLE);
	            viewHolder.bijiImgView.setVisibility(View.GONE);
	            viewHolder.imgView.setVisibility(View.GONE);
	            viewHolder.videoView.setImageBitmap(getVideoBitmap(urlVideo, 200, 200, MediaStore.Images.Thumbnails.MICRO_KIND));
	        }
	        if (url.equals("null")&&urlVideo.equals("null")){
	            viewHolder.bijiImgView.setVisibility(View.VISIBLE);
	            viewHolder.imgView.setVisibility(View.GONE);
	            viewHolder.videoView.setVisibility(View.GONE);
	            viewHolder.bijiImgView.setImageResource(R.drawable.notepad);
	        }
	        return layout;
	    }

	    class ViewHolder {
	        ImageView bijiImgView;
	        ImageView imgView;
	        ImageView videoView;
	        TextView contentView;
	        TextView timeView;
	    }

	    public Bitmap getImageBitmap(String uri, int width, int height) {
	        Bitmap bitmap = null;
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        bitmap = BitmapFactory.decodeFile(uri, options);
	        options.inJustDecodeBounds = false;
	        int beWidth = options.outWidth / width;
	        int beHeight = options.outHeight / height;
	        int be = 1;
	        if (beWidth < beHeight) {
	            be = beWidth;
	        } else {
	            be = beHeight;
	        }
	        if (be <= 0) {
	            be = 1;
	        }
	        options.inSampleSize = be;
	        bitmap = BitmapFactory.decodeFile(uri, options);
	        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	        return bitmap;
	    }

	    private Bitmap getVideoBitmap(String uri, int width, int height, int kind) {
	        Bitmap bitmap = null;
	        bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);
	        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	        return bitmap;
	    }
}
