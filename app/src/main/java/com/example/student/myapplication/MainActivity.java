package com.example.student.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rw;
    MyAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rw = findViewById(R.id.rw);

        list = new ArrayList<>();
//        for(int i = 0; i < 10; i++) {
//            list.add("Element " + i);
//        }

        File rootDir = Environment.getExternalStorageDirectory();
        File[] arrayFiles = rootDir.listFiles();
        if(arrayFiles != null) {
            for (File f : arrayFiles) {
                if(f.isDirectory()) {
                    list.add("[" + f.getName() + "]");
                } else {
                    list.add(f.getName());
                }
            }
        }


        layoutManager = new LinearLayoutManager(this);
        rw.setLayoutManager(layoutManager);
        adapter = new MyAdapter(list);
        rw.setAdapter(adapter);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1
            );
        }



    }
    private boolean isExterenalStorageMounted() {
        String state = Environment.getExternalStorageState();
        return (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY));
    }



}
