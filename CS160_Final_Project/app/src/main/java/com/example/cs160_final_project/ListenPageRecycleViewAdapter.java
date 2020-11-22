package com.example.cs160_final_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListenPageRecycleViewAdapter extends RecyclerView.Adapter<ListenPageRecycleViewAdapter.ViewHolder> {

    private ArrayList<SavedVideo> videosList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    // NOTE: I (Angel) removed the static so I can reference the videoList variable in order to
    //          identify which saved video was selected
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoImage;
        private TextView storyTitle;
        private ImageView videoOptions;

        public ViewHolder(View view) {
            super(view);

            videoImage = view.findViewById(R.id.videoImage);
            storyTitle = view.findViewById(R.id.storyTitle);
            videoOptions = view.findViewById((R.id.storyOptions));

            videoImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // Playing Video
                    SavedVideo video = videosList.get(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), PlayRecordingActivity.class);
                    intent.putExtra("videoPath", video.getFilename());
                    v.getContext().startActivity(intent);
                }
            });

            videoOptions.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO: display rename, delete, share options
                }
            });
        }

        public ImageView getVideoImageView() {
            return videoImage;
        }

        public TextView getStoryTitleTextView() {
            return storyTitle;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public ListenPageRecycleViewAdapter(ArrayList<SavedVideo> listOfVideos) {
        videosList = listOfVideos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listen_card_view, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        for (int i = 0; i < videosList.size(); i++) {
            viewHolder.getVideoImageView().setImageBitmap(videosList.get(i).getCoverImage());
            viewHolder.getStoryTitleTextView().setText(videosList.get(i).getTitle());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return videosList.size();
    }
}