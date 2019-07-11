package com.example.instagram;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetail extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvUsername;
    private TextView tvCaption;
    private TextView tvDate;
    private BottomNavigationView bottomNavigationView;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        ivImage = (ImageView) findViewById(R.id.ivPost);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvCaption = (TextView) findViewById(R.id.tvCaption);
        tvDate = (TextView) findViewById(R.id.tvTimeStamp);

        tvUsername.setText(post.getUser().getUsername());
        tvCaption.setText(post.getDescription());
        tvDate.setText(post.getTime());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }

    }




}
