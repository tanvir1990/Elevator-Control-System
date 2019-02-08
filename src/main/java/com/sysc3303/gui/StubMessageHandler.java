package com.sysc3303.gui;

import com.sysc3303.communication.GUIFloorMessage;
import com.sysc3303.communication.MessageHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class StubMessageHandler extends MessageHandler {
    private static StubMessageHandler instance;

    public static StubMessageHandler getInstance(int receivePort) {
        if (instance == null) {
            instance = new StubMessageHandler(receivePort);
        }
        return instance;
    }

    private StubMessageHandler(int receivePort) {
        super(receivePort);
    }

    // This class only sends messages, does not need a receive() method.

    public void sendFloorUpdate(boolean down, boolean up, int floor) {
        GUIFloorMessage msg = new GUIFloorMessage(down, up, floor);
        try {
            send(msg, InetAddress.getLocalHost(), 6665); //TODO: Stop hard-coding things, dammit
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}