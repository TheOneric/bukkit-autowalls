package oneric.bukkit.walls.utils;

public interface CountdownMessage {
	
	/** 
	 * @param t The remaining time in milliseconds until the countdown is finished
	 * @return The Message to display
	 *  */ 
	public String getMessage(long t);
	
	/** 
	 * @param t The remaining time in milliseconds until the countdown is finished
	 * @return The Message to display
	 *  */ 
	public void callMessage(long t);

}
