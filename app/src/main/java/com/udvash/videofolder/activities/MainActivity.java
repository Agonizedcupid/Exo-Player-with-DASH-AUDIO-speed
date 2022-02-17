package com.udvash.videofolder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.udvash.videofolder.adapters.FolderListAdapter;
import com.udvash.videofolder.R;
import com.udvash.videofolder.adapters.VideoListAdapter;
import com.udvash.videofolder.datamodels.VideoListModel;
import com.udvash.videofolder.fragments.FolderFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(0);

    }

    public void checkPermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted",
                    Toast.LENGTH_SHORT).show();
        }

        viewTheFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted",
                        Toast.LENGTH_SHORT).show();
                viewTheFragment();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewTheFragment() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new FolderFragment())
                .commit();
    }
}