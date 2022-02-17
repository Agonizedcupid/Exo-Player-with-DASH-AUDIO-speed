package com.udvash.videofolder.fragments;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udvash.videofolder.R;
import com.udvash.videofolder.activities.MainActivity;
import com.udvash.videofolder.adapters.VideoListAdapter;
import com.udvash.videofolder.datamodels.VideoListModel;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";

    private RecyclerView recyclerView;
    private List<VideoListModel> videoList = new ArrayList<>();
    private VideoListAdapter videoListAdapter;
    private String folder;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        folder = getArguments().getString("folder");

        getVideoList();
    }

    private void getVideoList() {

        videoList.clear();

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.RELATIVE_PATH
        };

        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%" + folder + "%"};

        try (Cursor cursor = getActivity().getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                MediaStore.Video.Media.DATE_TAKEN + " DESC"
        )) {
            while (cursor.moveToNext()) {

                int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                String videoTitle = cursor.getString(nameIndex);
                byte[] bytes = videoTitle.getBytes();
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                long id = cursor.getLong(columnIndex);
                Bitmap thumbnailBitmap = MediaStore.Video.Thumbnails.getThumbnail(
                        getActivity().getContentResolver(),
                        id,
                        MediaStore.Video.Thumbnails.FULL_SCREEN_KIND,
                        null
                );
                int durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                VideoListModel model = new VideoListModel(
                        1, videoTitle, bytes, thumbnailBitmap, duration, size, contentUri
                );

                videoList.add(model);
            }

            videoListAdapter = new VideoListAdapter(getActivity(), videoList);
            recyclerView.setAdapter(videoListAdapter);
        }
    }
}