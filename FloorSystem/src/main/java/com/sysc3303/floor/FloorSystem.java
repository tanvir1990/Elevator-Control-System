/*
 * FloorClass creates an Array of number of Floors. For example 5 Floors.
 * It does the following thing
 * 
 * 	1. It takes the message from Scheduler System and turns on the Lamps accordingly
 * 	2. It sends a message to the simulator stating the arrival of the floor
 */
package com.sysc3303.floor;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sysc3303.commons.ConfigListener;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.RabbitSender;
import com.sysc3303.communication.TelemetryFloorArrivalMessage;
import com.sysc3303.communication.TelemetryFloorButtonMessage;
import com.sysc3303.constants.Constants;

import static com.sysc3303.floor.FloorMessageHandler.floorPort;


public class FloorSystem {
	//TODO change this number to properties file value

	String  inputFilePath = "./src/resource/inputFile.txt";
	static Properties prop = new Properties();
	static String telemetaryQueueName = ConfigProperties.getInstance().getProperty("telemetryQueueName");            	
	private String propFileName = "config.properties";
	private InputStream inputStream =getClass().getClassLoader().getResourceAsStream(propFileName);



	private static int totalNumberofFloors=Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfFloors"));
	private List<Floor> floorList;
	private FloorMessageHandler floorMessageHandler;



	//Constructor
	public FloorSystem(){
		floorList = new ArrayList<>();
		floorMessageHandler = FloorMessageHandler.getInstance(floorPort, this);
		
		for (int i=0; i< FloorSystem.totalNumberofFloors; i++) {
			floorList.add(new Floor(i));
		}
	}
	
	/**
	 * Method getFloorList() is a regular 
	 * Getter Method To get The Floor List
	 */
	
	public List<Floor> getFloorList() {
		return floorList;
	}
	
	
	//Main method Not is use right Now. Left for Future testing
    public static void main(String[] args){
		if(args.length > 0){
			if(args[0] .equals("config")){
				new ConfigListener().run();
			}
		}
        FloorSystem floorSystem = new FloorSystem();
        while(true){
        }
    }

	/**
	 * Method floorArrival method does the follwoing things
	 * 	1. It takes the message from the scheduler
	 * 	2. It sends message to the simulator
	 * 	3. It turns off the lamp once the Elevator Has arrived in the destination Floor
	 * 
	 * @param arrivalFloor Number and Directio
	 */
	public void floorArrival (int arrivalFloor,  Direction direction, int elevatorId) throws InterruptedException {
		
		//If the Elevator has Arrived on the passenger floor where requested
		//FIXME Check out of bounds
		Floor arriveFloor = floorList.get(arrivalFloor);
		long  arrivalTime = System.currentTimeMillis() * Constants.NANO_PER_MILLI;
		
		if (direction == Direction.UP) {
			//turning uplight off
			arriveFloor.getLamps().turnUpLampON();
			arriveFloor.getButtons().setUpButtonLight(false);
			arriveFloor.getLamps().turnUpLampOFF();
		}
		else if(direction == Direction.DOWN) {
			//turning uplight off
			arriveFloor.getLamps().turnDownLampOFF();
			arriveFloor.getButtons().setDownButtonLight(false);
			arriveFloor.getLamps().turnUpLampOFF();
		}

		// Update the UI with the updated states of the buttons on this floor.
//		floorMessageHandler.updateUI(arriveFloor.getButtons().isDownButtonLight(),
//				arriveFloor.getButtons().isUpButtonLight(),
//				arriveFloor.getFloorNum());

		floorMessageHandler.sendFloorArrival(arrivalFloor, direction, elevatorId);
		sendArrivalTelemetryMetric(arrivalFloor, direction, arrivalTime);
	}

	/**
	 * Method buttonPress does the following things
	 * 	1. It sends the information to Scheduler about which button from which floor has been pressedn
	 * 	2. It takes the message from the Simulator or input file
	 * 	3. It turns on the lamp based on the direction of the Elevator 
	 */

	public void buttonPress(int requestFloor, Direction buttonDirection) {
		long pressedTime  = System.currentTimeMillis() * Constants.NANO_PER_MILLI;
		System.out.println(buttonDirection + " button pressed on floor " + requestFloor);
		//FIXME Check for index out of bounds
		Floor arriveFloor = floorList.get(requestFloor);
		if(buttonDirection == Direction.UP) {
			arriveFloor.getButtons().setUpButtonLight(true);
		}
		else if(buttonDirection == Direction.DOWN) {
			arriveFloor.getButtons().setDownButtonLight(true);
		}
		//send floor button request
		floorMessageHandler.sendFloorButton(requestFloor, buttonDirection, pressedTime);
		sendButtonTelemetryMetric(requestFloor, buttonDirection, pressedTime);
		// Update the UI with the updated states of the buttons on this floor.
//		floorMessageHandler.updateUI(arriveFloor.getButtons().isDownButtonLight(),
//				arriveFloor.getButtons().isUpButtonLight(),
//				arriveFloor.getFloorNum());
	}
	
	private void sendButtonTelemetryMetric(int floor, Direction direction, long pressedTime) {
		TelemetryFloorButtonMessage telemetryFloorBtnMsg = new TelemetryFloorButtonMessage(floor, direction, 0, pressedTime);
		RabbitSender rabbitSender = new RabbitSender(telemetaryQueueName, telemetryFloorBtnMsg);
        (new Thread(rabbitSender)).start();
	}

	private void sendArrivalTelemetryMetric(int floor, Direction direction, long pressedTime) {
		TelemetryFloorArrivalMessage telemetryFloorArvMsg = new TelemetryFloorArrivalMessage(floor, direction, 0, pressedTime);
		RabbitSender rabbitSender = new RabbitSender(telemetaryQueueName, telemetryFloorArvMsg);
        (new Thread(rabbitSender)).start();
	}
}


