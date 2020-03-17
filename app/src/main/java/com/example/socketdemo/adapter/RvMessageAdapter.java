package com.example.socketdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socketdemo.R;
import com.example.socketdemo.model.Message;

import java.util.List;

public class RvMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Message> listMessage;
    private int TYPE_ME = 0;
    private int TYPE_THEY = 1;

    public RvMessageAdapter(Context context, List<Message> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_THEY) {
            view = LayoutInflater.from(context).inflate(R.layout.item_they, parent, false);
            return new TheyHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_me, parent, false);
            return new MeHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = listMessage.get(position);
        if (holder instanceof MeHolder) {
            MeHolder myViewHolder = (MeHolder) holder;
            myViewHolder.tvUsername.setText("Me");
            myViewHolder.tvMessage.setText(message.getMessage());

        } else {
            TheyHolder myViewHolder = (TheyHolder) holder;
            myViewHolder.tvUsername.setText(message.getUsername());
            myViewHolder.tvMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = listMessage.get(position);
        if (message.isMe()) {
            return TYPE_ME;
        } else {
            return TYPE_THEY;
        }

    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    class TheyHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvMessage;

        public TheyHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_name);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }

    class MeHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvMessage;

        public MeHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_name);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
