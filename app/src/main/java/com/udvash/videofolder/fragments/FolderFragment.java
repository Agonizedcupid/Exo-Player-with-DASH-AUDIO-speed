package com.udvash.videofolder.fragments;

import android.database.Cursor;
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
import com.udvash.videofolder.adapters.FolderListAdapter;

import java.util.ArrayList;
import java.util.List;


public class FolderFragment extends Fragment {

    private static final String TAG = "FolderFragment";

    private List<String> folderList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FolderListAdapter adapter;

    public FolderFragment() {
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
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.lists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFolderList();

    }

    private void getFolderList() {

        folderList.clear();

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[]{
                MediaStore.Video.Media.RELATIVE_PATH
        };

        try (Cursor cursor = getActivity().getContentResolver().query(
                collection,
                projection,
                null,
                null,
                null
        )) {

            while (cursor.moveToNext()) {

                String folderName = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RELATIVE_PATH));

                if (!folderList.contains(folderName)) {
                    folderList.add(folderName);
                }
            }

            adapter = new FolderListAdapter(getActivity(), folderList);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}