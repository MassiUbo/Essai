package sma;

import java.util.concurrent.ArrayBlockingQueue;

public class MailBox {

	private ArrayBlockingQueue<Integer> messages = new ArrayBlockingQueue<>(50);
	
	public void add (Integer message) throws InterruptedException{
		this.messages.put(message);
		
	}
	// recuperer le message
	public Integer getLastMessage() throws InterruptedException {
		Integer message = messages.take();
		return message;
	}
}
