package com.example.socketdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socketdemo.adapter.RvMessageAdapter;
import com.example.socketdemo.model.Message;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Socket socket;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button btnSend;
    private RvMessageAdapter rvMessageAdapter;
    private List<Message> listMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editText = findViewById(R.id.edt_input);
        btnSend = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.rv_chat);
        listMessage = new ArrayList<>();
        rvMessageAdapter = new RvMessageAdapter(ChatActivity.this, listMessage);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.setAdapter(rvMessageAdapter);
        socket = SocketManager.getSocket();


        socket.on(Constance.NewMessage, listener);
        socket.connect();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                socket.emit(Constance.NewMessage, message);
                editText.setText("");

                listMessage.add(new Message(message, true));
                rvMessageAdapter.notifyItemInserted(listMessage.size());
                recyclerView.smoothScrollToPosition(listMessage.size());


            }
        });

    }

    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            final String user = jsonObject.optString(Constance.UserName);
            final String message = jsonObject.optString(Constance.Message);
            Log.d(Constance.TAG, "call: " + user + " : " + message);
            listMessage.add(new Message(user, message, false));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rvMessageAdapter.notifyItemInserted(listMessage.size());
                    recyclerView.smoothScrollToPosition(listMessage.size());
                }
            });

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.off(Constance.NewMessage);

    }
}
