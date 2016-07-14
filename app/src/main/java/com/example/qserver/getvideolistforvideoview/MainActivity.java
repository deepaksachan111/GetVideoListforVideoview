package com.example.qserver.getvideolistforvideoview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ModelData>  arrayList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView =(ListView)findViewById(R.id.list_view);

        //File directory = new File("/mnt/sdcard/folder");
        //  File imgFile = new  File("/sdcard/Images/test_image.jpg");

        File path = Environment.getExternalStorageDirectory();
          File addpath = new File(path.getAbsolutePath()+"/Movies");


        try {

            File[] files = addpath.listFiles();

            for (int i = 0; i < files.length; ++i) {
                if (files[i].getAbsolutePath().contains(".mp4"))
                    arrayList.add(new ModelData(files[i].getAbsolutePath()));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter = new Myadapter(this,R.layout.adapter_view,arrayList);
        listView.setAdapter(arrayAdapter);
       // getVideoThumbnail(this, arrayList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ModelData modelData = (ModelData) parent.getItemAtPosition(position);

                String s = modelData.getVideo_id();
                Intent intent = new Intent(getApplicationContext(),VideoViewActivity.class);
                intent.putExtra("S",s);
                startActivity(intent);

            }
        });
    }

   /* public Bitmap getVideoThumbnail(Context context,List<String> list) {
        Bitmap bitmap = null;
        for(String s : list){
            Uri uri =  Uri.parse(s);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, uri);
           bitmap = retriever.getFrameAtTime(100000
                    , MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        }
       // Uri uri = Uri.parse("android.resource://" + pkgName + "/" + resId);

        return bitmap;
    }*/

    private class Myadapter extends ArrayAdapter {
        Context context;
        List<ModelData> mylist ;
        public Myadapter(Context context, int resource,List list) {
            super(context, resource, list);
            this.context = context;
            this.mylist = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder ;
            if(convertView == null){
                convertView =LayoutInflater.from(getContext()).inflate(R.layout.adapter_view,null);
                holder = new ViewHolder();
                holder. textView =(TextView)convertView.findViewById(R.id.textview_video_id);
                holder. imageView =(ImageView)convertView.findViewById(R.id.video_imag);

                convertView.setTag(holder);
            }else {

                holder=  (ViewHolder)convertView.getTag();
            }



            ModelData data = mylist.get(position);
           holder. textView.setText(data.getVideo_id());

            Uri uri =  Uri.parse(data.getVideo_id());
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, uri);
            Bitmap bitmap = retriever.getFrameAtTime(1000
                    , MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
            holder.imageView.setImageBitmap(bitmap);
            return  convertView;
        }

         class ViewHolder{
             TextView textView ;
             ImageView imageView;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
