package com.sysc3303.scheduler;


import com.sysc3303.communication.FloorButtonMessage;

/**
 * @author Yu Yamanaka Xinrui Zhang
 * 
 * Handles message from floor
 */
public class FloorRequestHandler extends RequestHandler implements Runnable {
	
	/**
	 * @param request
	 * @param message
	 */
	public FloorRequestHandler(Request request, FloorButtonMessage message, SchedulerMessageHandler schedulerMessageHandler) {
		this.request                 = request;
		this.message                 = message;
		this.schedulerMessageHandler = schedulerMessageHandler;
	}
	
	public void run() {
		log.info("FloorRequestHandler starting..");
		log.info(request);
		
		FloorButtonMessage message = (FloorButtonMessage)this.message;

		request.addFloorButtonMessage(message);
		printQueues();
		sendQueuesToGUI();
		generateAndSendGoToFloorMessage();
	}
}
