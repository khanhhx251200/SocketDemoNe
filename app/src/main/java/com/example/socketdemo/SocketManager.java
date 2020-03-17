package com.example.socketdemo;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketManager {
    private static Socket socket;

    public static Socket getSocket() {
        if (socket == null) {
            try {
                socket = IO.socket(Constance.SocketPort);
            } catch (Exception e) {
                Log.d(Constance.TAG, "getSocket: ");
            }
        }
        return socket;
    }
}
