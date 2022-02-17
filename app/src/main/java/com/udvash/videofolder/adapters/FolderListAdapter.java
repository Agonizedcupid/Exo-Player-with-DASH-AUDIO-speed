package com.udvash.videofolder.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.udvash.videofolder.R;
import com.udvash.videofolder.activities.MainActivity;
import com.udvash.videofolder.fragments.VideoFragment;

import java.util.List;

public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.MyViewHolder> {

    private static final String TAG = "FolderListAdapter";

    private Context context;
    private List<String> folderList;
    private MainActivity mainActivity;

    public FolderListAdapter(Context context, List<String> folderList) {
        this.context = context;
        this.folderList = folderList;

        mainActivity = new MainActivity();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.layout_single_folder, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.folderName.setText(folderList.get(position));

        holder.itemView.setOnClickListener(v -> {

            /*String selection = MediaStore.Video.Media.DATA + " like?";
            String[] selectionArgs = new String[]{"%" + folderList.get(position) + "%"};

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
                    MediaStore.Video.Media.SIZE
            };

            Cursor videocursor = context.getContentResolver().query(
                    collection,
                    projection,
                    selection,
                    selectionArgs,
                    MediaStore.Video.Media.DATE_TAKEN + " DESC"
            );

            while (videocursor.moveToNext()) {
                Log.e(TAG, "onBindViewHolder: " +
                        videocursor.getString(videocursor.getColumnIndexOrThrow(
                                MediaStore.Video.Media.DISPLAY_NAME)));
            }*/

            Bundle mBundle = new Bundle();
            mBundle.putString("folder", folderList.get(position));

            Fragment videoFragment = new VideoFragment();
            videoFragment.setArguments(mBundle);

            ((MainActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, videoFragment)
                    .addToBackStack("")
                    .commit();

        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView folderName;

        public MyViewHolder(View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folder_name);
        }
    }
}
