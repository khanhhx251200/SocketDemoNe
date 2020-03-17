package com.example.socketdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private EditText editText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.ed_name);
        btn = findViewById(R.id.btn_login);
        socket = SocketManager.getSocket();

        socket.on(Constance.Login, listener);

        socket.connect();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                socket.emit(Constance.AddUser, name);
            }
        });
    }

    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.off(Constance.Login);

    }
}
