package com.sysc3303.communication;

import com.sysc3303.commons.Direction;

/**
 * Message from Scheduler to Floor indicating that an
 * elevator has arrived at a requested floor
 * Contains floor and the direction that the elevator will travel
 *
 * @author Mattias Lightstone
 */
public class FloorArrivalMessage extends Message{
    private int floor;
    private Direction currentDirection;
    private int elevatorId;

    public FloorArrivalMessage(int floor, Direction currentDirection, int elevatorId) {
        // Opcode for this message is 1
        super(OpCodes.FLOOR_ARRIVAL.getOpCode());
        this.floor = floor;
        this.currentDirection = currentDirection;
        this.elevatorId = elevatorId;
    }

    @Override
    public String toString(){
        return "Floor Arrival Message\n\tfloor: " +
                floor +"\n\tcurrent direction: " + currentDirection;
    }

    @Override
    public String getSummary(){
        return "FloorArrival: floor-"  + floor  + " direction-" + currentDirection + " elevator-" + elevatorId;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getElevatorId() { return elevatorId; }
}