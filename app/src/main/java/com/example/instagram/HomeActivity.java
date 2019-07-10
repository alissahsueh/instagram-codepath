package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;
    private Button btnLogout;
    //button to take picture
    private Button btnPicture;
    //actual image
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = findViewById(R.id.description_et);
        createButton = findViewById(R.id.create_btn);
        refreshButton = findViewById(R.id.refresh_btn);
        btnLogout = findViewById(R.id.btnLogOut);
        btnPicture = findViewById(R.id.btnPicture);
        ivImage = findViewById(R.id.ivPostImage);

        //button to refresh
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });

        //button to create post
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                savePost(description, user);
                //just hardcoding.. need to implement camera or way to upload photos
                //final File file = new File(imagePath);
               // final ParseFile parseFile = new ParseFile(file);

                //createPost(description, parseFile, user);
            }
        });

        //button to logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        //queryPost();



    }

    private void savePost(String description, ParseUser parseUser) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
      //  post.setImage();
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d("HomeActivity", "Error while saving");
                    e.printStackTrace();
                    return;
                }
                Log.d("HomeActivity", "Saved successfully!!");
                descriptionInput.setText("");
            }
        });

    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); ++i) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");

                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    private void logOut() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Log.d("Logout", "Sign up successful");
    }


    private void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("HomeActivity", "Error with query");
                    e.printStackTrace();
                    return;
                }
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d("HomeActivity", "Posts: " + post.getDescription() + " , username: " + post.getUser().getUsername());
                }

            }
        });
    }


}
