package com.udvash.videofolder.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udvash.videofolder.R;
import com.udvash.videofolder.activities.PlayerActivity;
import com.udvash.videofolder.datamodels.VideoListModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>
        implements Filterable {

    //Instance variable to get the actual context of the Host activity of the recyclerview:
    private Context context;
    //List to contain the video list
    private List<VideoListModel> listOfVideos;

    //For taking a copy of the Default data:
    private List<VideoListModel> copiedList;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Instantiating the data with public Constructor
    public VideoListAdapter(Context context, List<VideoListModel> listOfVideo) {
        this.context = context;
        this.listOfVideos = listOfVideo;
        //Making a copy:
        copiedList = new ArrayList<>(listOfVideos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //it will be reflect in each row: it's actually represent each single row
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_video_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Modeling the listView with parent model and getting the data by position:
        VideoListModel model = listOfVideos.get(position);
        //setting the title on RecyclerView
        holder.videoTitle.setText(model.videoTitle);
        //Showing the thumbnail or recyclerview:
        holder.thumbnailImage.setImageBitmap(model.getBitmap());

        // Setting the value of size & duration:
        holder.sizeDuration.setText(calculateSize(model.getFileSize()) + " " + convertDuration(model.getDuration()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.videoView.setVideoURI(model.getContentUri());
//                MainActivity.videoView.start();
//                MainExoPlayer.getInstance(context).playVideo(model.getContentUri());
                context.startActivity(new Intent(context, PlayerActivity.class)
                        .putExtra("uri", model.getContentUri().toString()));
                Log.d("URI_TEST", model.getContentUri().toString());
            }
        });
    }

    public String calculateSize(int fileSize) {
        double total = 0.000001 * fileSize;
        return df.format(total) + " MB";
    }

    public String convertDuration(int duration) {
        String converted = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );

        return converted;
    }

    @Override
    public int getItemCount() {
        return listOfVideos.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    //It will return the filter result
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<VideoListModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(copiedList);
            } else {
                //Query String for what value we are searching for
                String query = constraint.toString().toLowerCase().trim();

                //Matching with the model:
                for (VideoListModel model : copiedList) {
                    //If it's match then it will show to the recyclerview:
                    if (model.getVideoTitle().toLowerCase().contains(query)) {
                        filteredList.add(model);
                    } else {
                        //Toast.makeText(context, "No Video found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            //Creating object of Filterable result
            FilterResults results = new FilterResults();
            //Pushing the result:
            results.values = filteredList;

            //Returning the Filtered result:
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //First clearing the default data before showing the filtered data
            listOfVideos.clear();
            //Adding the videos
            listOfVideos.addAll((List) filterResults.values);
            //Notifying the adapter
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {


        //Instance of ImageView to show the Thumbnail
        private ImageView thumbnailImage;
        //For showing the Video Title
        private TextView videoTitle;
        //Instance variable of size and duration:
        private TextView sizeDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Instantiating the UI:
            thumbnailImage = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            sizeDuration = itemView.findViewById(R.id.size);
        }
    }
}
