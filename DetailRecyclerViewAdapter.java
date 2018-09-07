package com.example.nj84616.mobilecatalogue;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.nj84616.mobilecatalogue.model.CommonModel;

import java.util.ArrayList;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailRVViewHolder> {

    private final IClickCallback iClickCallack;
    private ArrayList<CommonModel> list;
    private String fontColor;
    private MediaController mediaController;
    // Allows to remember the last item shown on screen
    private int selectedPosition = -1;

    private int currentlySelectedItemPosition = -1;

    private Context context;


    public DetailRecyclerViewAdapter(Context context, ArrayList<CommonModel> borrowList, String fontColor, IClickCallback iClickCallback) {
        this.context = context;
        this.list = borrowList;
        this.fontColor = fontColor;
        this.iClickCallack = iClickCallback;

    }

    @NonNull
    @Override
    public DetailRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_activity, viewGroup, false);
        DetailRVViewHolder detailRVViewHolder = new DetailRVViewHolder(view);
        return detailRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailRVViewHolder detailRVViewHolder, int position) {
        TextView name = detailRVViewHolder.name;
        ImageView angular = detailRVViewHolder.angular;
        //final ImageView android = detailRVViewHolder.android;
        //ImageView ios = detailRVViewHolder.ios;
        final TextView detail = detailRVViewHolder.detail;
        final ImageView ivUpArrow = detailRVViewHolder.ivUpArrow;
        ImageView ivVideo = detailRVViewHolder.ivVideo;
        final VideoView videoView = detailRVViewHolder.videoView;
        final RelativeLayout rlChildLayout = detailRVViewHolder.rvChildLayout;
        rlChildLayout.setVisibility(View.GONE);

        //for (int i = 1; i <= list.size(); i++) {
        name.setText(list.get(position).getName());
        name.setTextColor(Color.parseColor("#" + fontColor));
        detail.setText(list.get(position).getDetail());

        setPlatformIcons(list.get(position).getPlatform(), detailRVViewHolder);

        detailRVViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (iClickCallack != null) {
                    iClickCallack.OnItemClick(position);
                }
            }

        });


        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoViewActivity.class);
                context.startActivity(intent);


            }
        });

    }

    private void setPlatformIcons(final String platforms, DetailRVViewHolder holder) {

            if (platforms.contains("Native")) {
                holder.ios.setVisibility(View.VISIBLE);
                holder.android.setVisibility(View.VISIBLE);
            } else {
                holder.ios.setVisibility(View.GONE);
                holder.android.setVisibility(View.GONE);
            }
            if (platforms.contains("Angular")) {
                holder.angular.setVisibility(View.VISIBLE);
            } else {
                holder.angular.setVisibility(View.GONE);
            }

            if (platforms.contains("Hybrid")) {
                holder.hybrid.setVisibility(View.VISIBLE);
            } else {
                holder.hybrid.setVisibility(View.GONE);
            }
    }


    private void setDownAnimationOfChild(View view, int lastPosition) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        view.startAnimation(animation);
    }

    private void setUpAnimationOfChild(View view, int lastposition) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        view.startAnimation(animation);
    }

    private void setAnimationOfArrow(View view, int lastPosition) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        view.startAnimation(animation);
    }

    /**
     * Here is the key method to apply the animation
     */
/*    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }*/



    public static class DetailRVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout rvParentLayout;
        private RelativeLayout rvChildLayout;
        private TextView name;
        private ImageView angular;
        private ImageView android;
        private ImageView ios;
        private ImageView hybrid;
        private TextView detail;
        private ItemClickListener itemClickListener;
        private ImageView ivUpArrow;
        private ImageView ivVideo;
        private VideoView videoView;

        public DetailRVViewHolder(@NonNull View itemView) {
            super(itemView);

            rvParentLayout = itemView.findViewById(R.id.rl_detail_parent_layout);
            rvChildLayout = itemView.findViewById(R.id.rl_detail_child_layout);
            name = itemView.findViewById(R.id.detail_parent_title);
            angular = itemView.findViewById(R.id.detail_parent_image_angular);
            android = itemView.findViewById(R.id.detail_parent_image_android);
            ios = itemView.findViewById(R.id.detail_parent_image_ios);
            hybrid = itemView.findViewById(R.id.detail_parent_image_hybrid);
            detail = itemView.findViewById(R.id.tv_detail_child);
            ivUpArrow = itemView.findViewById(R.id.detail_parent_arrow);
            ivVideo = itemView.findViewById(R.id.iv_detail_child);
            videoView = itemView.findViewById(R.id.videoView);


            rvParentLayout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
