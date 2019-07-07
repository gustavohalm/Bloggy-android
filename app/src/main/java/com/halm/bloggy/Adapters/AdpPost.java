package com.halm.bloggy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.halm.bloggy.Activities.MainActivity;
import com.halm.bloggy.Activities.PostActivity;
import com.halm.bloggy.Models.Post;
import com.halm.bloggy.R;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;


public class AdpPost extends RecyclerView.Adapter<AdpPost.MyViewHolder> {
    private List<Post> listPost;

    public AdpPost(List<Post> listPost) {
        this.listPost = listPost;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_posts, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.txtTitle.setText(listPost.get(position).getTitle());
        holder.txtContent.setText(listPost.get(position).getContent());

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listPost.get(position).getId();

            }
        });

        holder.txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                int id = listPost.get(position).getId();
                //Toast.makeText(holder.itemView.getContext(), "POst: " + id, Toast.LENGTH_LONG).show();
                SharedPreferences prefs = context.getSharedPreferences("sharedIdPost", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("id",id);
                editor.commit();
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return listPost.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtContent;
        public MyViewHolder(View itemView) {
            super(itemView);
             this.txtTitle = itemView.findViewById(R.id.txtTitle);
             this.txtContent = itemView.findViewById(R.id.txtContent);
        }
    }

}
