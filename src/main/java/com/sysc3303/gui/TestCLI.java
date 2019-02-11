package com.sysc3303.gui;

import com.sysc3303.commons.Direction;
import com.sysc3303.communication.*;

import java.util.ArrayList;

/**
 * TestCLI is a console-output mock for the GUI. It is meant to act as a
 * bare-bones implementation of the methods for testing purposes, without any
 * reference to Swing. Use it while the GUI is still in development.
 */
public class TestCLI implements UserInterface {

    private GUIMessageHandler handler;

    public TestCLI() {
        this.handler = GUIMessageHandler.getInstance(this);
    }

    /**
     * Respond to an ElevatorMoveMessage, which indicates that one of the
     * elevators have moved or opened/closed doors.
     * @param msg   The message passed on from the MessageHandler.
     */
    public void moveElevator(GUIElevatorMoveMessage msg) {
        System.out.println(msg.toString());
    }

    /**
     * Respond to a press on a floor's up/down button.
     * @param msg   The message passed on from the MessageHandler.
     */
    public void pressFloorButton(GUIFloorMessage msg) {
        System.out.println(msg.toString());
    }

    /**
     * Dummy method for now.
     * @param floor The floor number of the pressed button.
     */
    public void pressElevatorButton(int floor) {
        // Interactive button presses are not supported at this time.
        System.out.println("This method is not supported in this class!");
    }


    public static void main(String[] args) {
        TestCLI t = new TestCLI();
    }

}
