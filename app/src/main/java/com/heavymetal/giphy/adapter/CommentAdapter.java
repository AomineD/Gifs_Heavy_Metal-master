package com.heavymetal.giphy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heavymetal.giphy.entity.Comment;
import com.heavymetal.giphy.ui.UserActivity;
import com.squareup.picasso.Picasso;
import com.heavymetal.giphy.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hsn on 16/01/2018.
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> commentList= new ArrayList<>();
    private Context context;
    public CommentAdapter(List<Comment> commentList, Context context){
        this.context=context;
        this.commentList=commentList;
    }
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, null, false);
        viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CommentAdapter.CommentHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder,final int position) {
        holder.text_view_time_item_comment.setText(commentList.get(position).getCreated());

        byte[] data = Base64.decode(commentList.get(position).getContent(), Base64.DEFAULT);
        String Comment_text = "";
        try {
            Comment_text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Comment_text = commentList.get(position).getContent();
        }

        holder.text_view_name_item_comment.setText(commentList.get(position).getUser());
        Picasso.with(context).load(commentList.get(position).getImage()).placeholder(R.drawable.profile).into(holder.image_view_comment_iten);
        if (!commentList.get(position).getEnabled()){
            holder.text_view_content_item_comment.setText(context.getResources().getString(R.string.comment_hidden));
            holder.text_view_content_item_comment.setTextColor(context.getResources().getColor(R.color.gray_color));
        }else{
            holder.text_view_content_item_comment.setText(Comment_text);
        }
        if (commentList.get(position).getTrusted()){
            holder.image_view_iten_comment_trusted.setVisibility(View.VISIBLE);
        }else{
            holder.image_view_iten_comment_trusted.setVisibility(View.GONE);
        }
        holder.relative_layout_comment_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(context.getApplicationContext(), UserActivity.class);
                intent.putExtra("id",commentList.get(position).getUserid());
                intent.putExtra("image",commentList.get(position).getImage());
                intent.putExtra("name",commentList.get(position).getUser());
                intent.putExtra("trusted",commentList.get(position).getTrusted());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        private final TextView text_view_name_item_comment;
        private final TextView text_view_time_item_comment;
        private final TextView text_view_content_item_comment;
        private final CircleImageView image_view_comment_iten;
        private final ImageView image_view_iten_comment_trusted;
        private final RelativeLayout relative_layout_comment_item;
        public CommentHolder(View itemView) {
            super(itemView);
            this.relative_layout_comment_item=(RelativeLayout) itemView.findViewById(R.id.relative_layout_comment_item);
            this.image_view_iten_comment_trusted=(ImageView) itemView.findViewById(R.id.image_view_iten_comment_trusted);
            this.image_view_comment_iten=(CircleImageView) itemView.findViewById(R.id.image_view_comment_iten);
            this.text_view_name_item_comment=(TextView) itemView.findViewById(R.id.text_view_name_item_comment);
            this.text_view_time_item_comment=(TextView) itemView.findViewById(R.id.text_view_time_item_comment);
            this.text_view_content_item_comment=(TextView) itemView.findViewById(R.id.text_view_content_item_comment);
        }
    }
}
