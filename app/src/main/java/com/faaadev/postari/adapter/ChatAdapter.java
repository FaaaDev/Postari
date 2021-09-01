package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.ChatRoomActivity;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Chat;
import com.faaadev.postari.model.Jadwal;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context mContext;
    List<Chat> mData;

    public ChatAdapter(Context mContext, List<Chat> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_chat, parent,false);
        final ChatAdapter.ViewHolder myViewHolder = new ChatAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat data = mData.get(position);
        if (data.getSenderId().equals(Preferences.getUserId(mContext))){
            holder.username.setText(data.getReceiverName());
            holder.message.setText("Anda : "+data.getMessage());
            holder.card.setOnClickListener(v->{
                Intent i = new Intent(mContext, ChatRoomActivity.class);
                i.putExtra("receiverId", data.getReceiverId());
                i.putExtra("receiverName", data.getReceiverName());
                i.putExtra("receiverRole", data.getReceiverRole());
                i.putExtra("from", "list");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            });
        } else {
            holder.username.setText(data.getSenderName());
            holder.message.setText(data.getMessage());
            holder.card.setOnClickListener(v->{
                Intent i = new Intent(mContext, ChatRoomActivity.class);
                i.putExtra("receiverId", data.getSenderId());
                i.putExtra("receiverName", data.getSenderName());
                i.putExtra("receiverRole", data.getSenderRole());
                i.putExtra("from", "list");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, message;
        RelativeLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            message = itemView.findViewById(R.id.message);
            card = itemView.findViewById(R.id.card);
        }
    }
}
