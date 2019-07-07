package com.halm.bloggy.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halm.bloggy.Models.Comment;
import com.halm.bloggy.R;

import java.util.List;

public class AdpComment extends RecyclerView.Adapter <AdpComment.MyViewHolder> {
    private List<Comment> commentList;

    public AdpComment(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View linearLayout = (LinearLayout) LayoutInflater.from( parent.getContext() ).inflate(R.layout.list_comment, parent, false);
        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.author.setText( commentList.get(position).getAuthor() );
        holder.content.setText( commentList.get(position).getContent() );
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.author = itemView.findViewById( R.id.txtCommentAuthor );
            this.content = itemView.findViewById( R.id.txtCommentContent );
        }
    }
}
