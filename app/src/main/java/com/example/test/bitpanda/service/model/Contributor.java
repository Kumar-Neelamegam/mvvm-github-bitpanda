package com.example.test.bitpanda.service.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.Map;

import androidx.databinding.BindingAdapter;

public class Contributor {

   public Contributor()
   {

   }

   public Contributor(String login) {
      this.login = login;
   }

   public String login;
   public int id;
   public String node_id;
   public String avatar_url;
   public String gravatar_id;
   public String url;
   public String html_url;
   public String followers_url;
   public String following_url;
   public String gists_url;
   public String starred_url;
   public String subscriptions_url;
   public String organizations_url;
   public String repos_url;
   public String events_url;
   public String received_events_url;
   public String type;
   public boolean site_admin;
   public int contributions;
   public Map<String, Object> additionalProperties = new HashMap<String, Object>();

   @BindingAdapter("profileImage")
   public static void loadImage(ImageView view, String imageUrl) {
      Glide.with(view.getContext())
              .load(imageUrl).apply(new RequestOptions().circleCrop())
              .into(view);
   }

}
