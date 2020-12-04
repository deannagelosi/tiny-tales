package com.example.cs160_final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.cs160_final_project.ListenActivity.cancelButton;
import static com.example.cs160_final_project.ListenActivity.confirmButton;
import static com.example.cs160_final_project.ListenActivity.confirmationPopup;
import static com.example.cs160_final_project.ListenActivity.doneButton;
import static com.example.cs160_final_project.ListenActivity.renamePopup;
import static com.example.cs160_final_project.ListenActivity.titleEditText;

public class ListenPageRecycleViewAdapter extends RecyclerView.Adapter<ListenPageRecycleViewAdapter.ViewHolder> {

    private ArrayList<SavedVideo> videosList;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoImage;
        private EditText storyTitle;
        private ImageView videoOptions;
        private ImageView storyOptions;

        public ViewHolder(View view) {
            super(view);

            videoImage = view.findViewById(R.id.videoImage);
            storyTitle = view.findViewById(R.id.storyTitle);
            videoOptions = view.findViewById(R.id.storyOptions);
            storyOptions = view.findViewById(R.id.storyOptions);

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
    public ListenPageRecycleViewAdapter(Context context, ArrayList<SavedVideo> listOfVideos) {
        videosList = listOfVideos;
        this.context = context;
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
        viewHolder.getVideoImageView().setImageBitmap(videosList.get(position).getCoverImage());
        viewHolder.getStoryTitleTextView().setText(videosList.get(position).getTitle());
        viewHolder.storyOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, viewHolder.storyOptions );
                //inflating menu from xml resource
                popup.inflate(R.menu.dropdown_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.rename:
                                renamePopup.setVisibility(View.VISIBLE);

                                doneButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        viewHolder.storyTitle.setText(titleEditText.getText().toString());
                                        renamePopup.setVisibility(View.GONE);
                                    }
                                });
                                break;
                            case R.id.delete:
                                confirmationPopup.setVisibility(View.VISIBLE);
                                confirmButton.setOnClickListener(new View.OnClickListener(){
                                public void onClick(View v) {
                                    videosList.remove(viewHolder.getAdapterPosition());
                                    notifyItemRemoved(viewHolder.getAdapterPosition());
                                    notifyItemRangeChanged(viewHolder.getAdapterPosition(), videosList.size());
                                    confirmationPopup.setVisibility(View.GONE);
                                }
                            });
                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    confirmationPopup.setVisibility(View.GONE);
                                }
                            });
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return videosList.size();
    }

}