package net.flow9.firebasechatting;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.flow9.firebasechatting.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.Holder>{
    List<Room> data = new ArrayList<>();

    public void setDataAndRefresh(List<Room> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Room room = data.get(position);
        holder.room = room;
        holder.textFriend.setText(room.getFriendString());
        holder.textTitle.setText(room.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        Room room;
        TextView textTitle;
        TextView textFriend;
        public Holder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textFriend = itemView.findViewById(R.id.textFriend);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RoomActivity.class);
                    intent.putExtra("room_id",room.id);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
