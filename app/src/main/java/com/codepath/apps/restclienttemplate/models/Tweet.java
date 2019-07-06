package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    //list out the attributes
    public String body;
    public long uid; // database ID
    public User user;
    public String createdAt;
    public int retweet_count;
    public int favorite_count;

    public Tweet() {}

    //get the images to load
    public String media;

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        JSONObject entities = jsonObject.getJSONObject("entities");

        try {
            JSONArray media1 = entities.getJSONArray("media");
            JSONObject media2 = media1.getJSONObject(0);
            tweet.media = media2.getString("media_url_https");
        }
        catch(Throwable e){
            tweet.media = "oh no";
        }
        return tweet;
    }
}
