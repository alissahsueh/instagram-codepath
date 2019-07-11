package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class PostDetail extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvUsername;
    private TextView tvCaption;
    private TextView tvDate;
    private ImageView ivHeart;
    private ImageView ivComment;
    private TextView tvLikes;
    private int likeCount;
    private EditText comment;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        ivImage = (ImageView) findViewById(R.id.ivPost);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvCaption = (TextView) findViewById(R.id.tvCaption);
        tvDate = (TextView) findViewById(R.id.tvTimeStamp);
        ivHeart = (ImageView) findViewById(R.id.ivHeart);
        tvLikes = (TextView) findViewById(R.id.tvLikeCount);
        likeCount = 0;
        ivComment = (ImageView) findViewById(R.id.ivComment);
        comment = (EditText) findViewById(R.id.etComment);

        tvUsername.setText(post.getUser().getUsername());
        tvCaption.setText(post.getDescription());
        tvDate.setText(post.getTime());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }

        //makes the like button clickable
        ivHeart.setOnClickListener(new View.OnClickListener() {
            boolean like = false;

            @Override
            public void onClick(View v) {
                if (like == false) {
                    likeCount++;
                    tvLikes.setText(Integer.toString(likeCount));
                    ivHeart.setImageResource(R.drawable.ufi_heart_active);
                    post.setLikes(likeCount);
                    like = true;
                } else {
                    likeCount--;
                    tvLikes.setText(Integer.toString(likeCount));
                    ivHeart.setImageResource(R.drawable.ufi_heart);
                    post.setLikes(likeCount);
                    like = false;
                }

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.e("PostDetail", "likes are saved!");
                    }
                });
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setComment(comment.getText().toString());

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.e("PostDetail", "comment is saved!");
                    }
                });
            }
        });


    }


}
