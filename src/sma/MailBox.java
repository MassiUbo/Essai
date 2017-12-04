package sma;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * @author MASSINISSA HADJ-SAID
 * Classe MailBox contien ArrayBlockingQueue qui permet l'échange de messages entre Agents
 */

public class MailBox {

	private ArrayBlockingQueue<Integer> messages = new ArrayBlockingQueue<>(50);
	
	/**
	 * Methode add: ajout message à la mailBox
	 * @param message : entier dans notre cas c'est l'ID de la voiture qui sera ajouté
	 * 
	 */
	public void add (Integer message) throws InterruptedException{
		this.messages.put(message);
		
	}
	/**
	 * Methode getLastMessage : retourne message de la mailBox
	 * @return : int qui represente ID du vehicule
	 *
	 */
	public Integer getLastMessage() throws InterruptedException {
		Integer message = messages.take();
		return message;
	}
}
