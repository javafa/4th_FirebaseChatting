package net.flow9.firebasechatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.flow9.firebasechatting.model.Room;
import net.flow9.firebasechatting.util.PreferenceUtil;

public class RoomListActivity extends AppCompatActivity {

    // 파이어베이스 데이터베이스 연결
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference roomRef;

    // 방 생성 팝업 레이아웃
    RelativeLayout popup;
    // 방 생성 버튼
    FloatingActionButton addRoom;
    private RecyclerView recycler;
    private EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        database = FirebaseDatabase.getInstance();
        // 데이터베이스 user 레퍼런스 생성
        userRef = database.getReference("user");
        roomRef = database.getReference("chatting_room");
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.GONE);
            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 타이틀을 입력할 수 있는 레이아웃이 보여지는 상태에 따라 분기
                if (popup.getVisibility() == View.GONE)
                    popup.setVisibility(View.VISIBLE);
                else if (popup.getVisibility() == View.VISIBLE) {
                    String title = editTitle.getText().toString();
                    // DB에 추가
                    String roomKey = roomRef.push().getKey();
                    Room room = new Room();
                    room.id = roomKey;
                    room.title = title;
                    // chatting_room 에 추가
                    roomRef.child(roomKey).setValue(room);
                    // user 에 추가
                    // preference 에서 값 가져오기
                    String user_id = PreferenceUtil.getStringValue(getBaseContext(), "user_id");
                    userRef.child(user_id).child("chatting_room").child(roomKey).setValue(room);
                    popup.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initView() {
        addRoom = findViewById(R.id.fab);
        popup = findViewById(R.id.popup);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        editTitle = (EditText) findViewById(R.id.editTitle);
    }
}
