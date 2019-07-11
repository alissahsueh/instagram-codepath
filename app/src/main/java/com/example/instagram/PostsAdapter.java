package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Post post =  posts.get(position);
       ParseFile image = post.getImage();
       if (image != null) {
           Glide.with(context).load(posts.get(position).getImage().getUrl()).into(holder.imageIv);
           holder.bind(post);
       } else {
           Toast.makeText(context, "Image has no url!", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvHandle;
        public TextView tvDescription;
        public ImageView imageIv;
        public TextView tvTime;


        public ViewHolder(View itemView) {
            super(itemView);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            imageIv = (ImageView) itemView.findViewById(R.id.ivPostImage);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTime = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            itemView.setOnClickListener(this);
        }



        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
               Glide.with(context).load(image.getUrl()).into(imageIv);
            }
        }

        @Override
        public void onClick(View v) {
            //gets item position
            int position = getAdapterPosition();
            //make sure position  is valid
            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> post) {
        posts.addAll(post);
        notifyDataSetChanged();
    }
}
