package com.sysc3303.gui;

public class TestCLI {

    private GUIMessageHandler handler;

    public TestCLI() {
        this.handler = GUIMessageHandler.getInstance(6665);
    }

    public static void main(String[] args) {
        TestCLI t = new TestCLI();
    }
}