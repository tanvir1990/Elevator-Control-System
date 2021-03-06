package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Message sent from an Elevator to the scheduler
 * Indicates that an elevator button has been pressed
 * Contains the floor button clicked, the elevator that it was clicked in and the time
 */
public class ElevatorButtonMessage extends Message{
    private final int destinationFloor;
    private final int elevatorId;
    private final Date time;
    private final long pressedTime;

    /**
     * Constructor
     * @param destinationFloor the floor of the button that was clicked
     * @param elevatorId the id of the elevator in which the button was clicked
     * @param time the time at which the button was clicked
     * @param pressedTime the time at which the module received the button click
     */
    @JsonCreator
    public ElevatorButtonMessage(@JsonProperty("destinationFloor") int destinationFloor, @JsonProperty("elevatorId") int elevatorId, @JsonProperty("time") Date time, long pressedTime) {
        // the opcode for this message type is 5
        super(OpCodes.ELEVATOR_BUTTON.getOpCode());
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
        this.time = time;
        this.pressedTime = pressedTime;
    }

    @Override
    public String toString(){
        return "Elevator Button Request\n\tDesination Floor: " +
                destinationFloor +"\n\tElevator Id: " + elevatorId +
                "\n\tTime: " + time;
    }

    @Override
    public String summary(){
        return "ElevatorButton: floor-"  + destinationFloor  + " elevatorId-" + elevatorId;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public Date getTime() {
        return time;
    }
    
    public long getPressedTime() {
    	return pressedTime;
    }
}