package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.ChatRoomActivity;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Chat;

import java.util.List;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.ViewHolder> {

    Context mContext;
    List<Chat> mData;

    public ChatDetailAdapter(Context mContext, List<Chat> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_chat_detail, parent,false);
        final ChatDetailAdapter.ViewHolder myViewHolder = new ChatDetailAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat data = mData.get(position);

        if (data.getSenderId().equals(Preferences.getUserId(mContext))){
            if (position!=0 && position != mData.size()-1){
                if (!mData.get(position-1).getSenderId().equals(data.getSenderId())){
                    holder.right.setBackgroundResource(R.drawable.right_bubble);
                } else if (mData.get(position-1).getSenderId().equals(data.getSenderId())
                        && mData.get(position+1).getSenderId().equals(data.getSenderId())){
                    holder.spacer.setVisibility(View.GONE);
                    holder.right.setBackgroundResource(R.drawable.same_right_bubble);
                } else {
                    holder.spacer.setVisibility(View.GONE);
                    holder.right.setBackgroundResource(R.drawable.last_right_bubble);
                }
            } else {
                if (position == 0){
                    holder.right.setBackgroundResource(R.drawable.right_bubble);
                } else if (position == mData.size()-1){
                    if (mData.get(position-1).getSenderId().equals(data.getSenderId())){
                        holder.spacer.setVisibility(View.GONE);
                        holder.right.setBackgroundResource(R.drawable.last_right_bubble);
                    }
                }
            }
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.text_right.setText(data.getMessage());
        } else {
            if (position!=0 && position != mData.size()-1){
                if (!mData.get(position-1).getSenderId().equals(data.getSenderId())){
                    holder.left.setBackgroundResource(R.drawable.left_bubble);
                } else if (mData.get(position-1).getSenderId().equals(data.getSenderId())
                        && mData.get(position+1).getSenderId().equals(data.getSenderId())){
                    holder.spacer.setVisibility(View.GONE);
                    holder.left.setBackgroundResource(R.drawable.same_left_bubble);
                } else {
                    holder.spacer.setVisibility(View.GONE);
                    holder.left.setBackgroundResource(R.drawable.last_left_bubble);
                }
            } else {
                if (position == 0){
                    holder.left.setBackgroundResource(R.drawable.left_bubble);
                } else if (position == mData.size()-1){
                    if (mData.get(position-1).getSenderId().equals(data.getSenderId())){
                        holder.spacer.setVisibility(View.GONE);
                        holder.left.setBackgroundResource(R.drawable.last_left_bubble);
                    }
                }
            }
            holder.right.setVisibility(View.GONE);
            holder.left.setVisibility(View.VISIBLE);
            holder.text_left.setText(data.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_left, text_right;
        LinearLayout left, right, spacer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_left = itemView.findViewById(R.id.text_left);
            text_right = itemView.findViewById(R.id.text_right);
            left = itemView.findViewById(R.id.left);
            right = itemView.findViewById(R.id.right);
            spacer = itemView.findViewById(R.id.spacer);
        }
    }
}
