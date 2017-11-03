package net.flow9.firebasechatting.model;

import android.widget.TextView;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Room {
    public String id;
    public String title;
    public String last_msg;
    public long last_msg_time;
    public long msg_count;
    public long creation_time;
    public List<User> member;

    @Exclude
    public String getFriendString() {
        String friendString = "";
        if(member != null && member.size() > 0) {
            for (User friend : member) {
                friendString += ", " + friend.email;
            }
        }
        return friendString;
    }
}
