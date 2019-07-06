package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    //pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;

    }

    //for each row, inflate the layout and cache references into Viewholder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    //bind the values based on the position

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the data according to position
        Tweet tweet = mTweets.get(position);

        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvFavoriteCount.setText(String.format("%d", tweet.favorite_count));
        holder.tvRetweetCount.setText(String.format("%d", tweet.retweet_count));


        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));

        //get the images to load
        if(tweet.media != ""){
            Glide.with(context).load(tweet.media).into(holder.media);
        }
        else{
            holder.media.setVisibility(View.GONE);
        }



        int radius = 25; // corner radius, higher value = more rounded
        int margin = 1;

        Glide.with(context).load(tweet.user.profileImageUrl).bitmapTransform(new RoundedCornersTransformation(context, radius, margin)).into(holder.ivProfileImage);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public ImageView ivHeart;
        public ImageView ivRetweet;
        public ImageView ivReply;
        public View divider;
        public TextView tvFavoriteCount;
        public TextView tvRetweetCount;
        public TextView tvTimestamp;
        public ImageView media;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            ivHeart = (ImageView) itemView.findViewById(R.id.ivHeart);
            ivRetweet = (ImageView) itemView.findViewById(R.id.ivRetweet);
            ivReply = (ImageView) itemView.findViewById(R.id.ivReply);
            divider = (View) itemView.findViewById(R.id.divider);
            tvFavoriteCount = (TextView) itemView.findViewById(R.id.tvFavoriteCount);
            tvRetweetCount = (TextView) itemView.findViewById(R.id.tvRetweetCount);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            media = (ImageView) itemView.findViewById(R.id.media);
        }
    }


    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, FORMAT_ABBREV_RELATIVE).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] splitDate = relativeDate.split(" ", 2);
        String reconstructedRelDate = String.format("%s%s", splitDate[0], splitDate[1].charAt(0));

        return reconstructedRelDate;
    }



    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
