package oneric.bukkit.walls.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.scheduler.BukkitRunnable;

public class CountdownMessenger extends BukkitRunnable{
	
	private List<Long> steps;
	private final long startTime;
	private final CountdownMessage message;
	private final boolean endCounter;
	
	/** 
	 * @param periode The repeating periode in milliseconds
	 * @param duration The duration of the countdown in milliseconds
	 * @param startTime The System time millis at start of the Messenger
	 * @param msg A CountdownMessage Object
	 * @param endCount Determinate if a special one seconds countdown should be used after the last step
	 * */
	public CountdownMessenger(long periode, long duration, long startTime, CountdownMessage msg, boolean endCount)
	{
		
		steps = new ArrayList<Long>();
		for(Long d = duration; d >= 0; d -= periode)
			steps.add(d);
		
		
		this.endCounter = (periode > 20L && steps.size() > 1) ? endCount : false;
		if(steps.size() == 1) {
			this.run();
			this.cancel();
		}
		
		Collections.sort(steps, Collections.reverseOrder());
		this.startTime = startTime;
		this.message = msg;
		
	}
	
	/** 
	 * @param periode The periode in milliseconds
	 * @param duration The duration of the countdown in milliseconds
	 * @param msg A CountdownMessage Object
	 * @param endCount Determinate if a special one seconds countdown should be used after the last step
	 * */
	public CountdownMessenger(long periode, long duration, CountdownMessage msg, boolean endCount)
	{ 
		this(periode, duration, System.currentTimeMillis(), msg, endCount);
	}

	@Override
	public void run()
	{
		for(Long t : steps)
		{
			if(System.currentTimeMillis() >= startTime + t) {
				callMessage(steps.get(0) - t);
				if(this.endCounter && t >= steps.get(1)) {
					new CountdownMessenger(1_000L, this.steps.get(0) - steps.get(1), this.message, false).runTaskTimer(WallsPlugin.me, 0L, 20L);
					this.cancel();
				}
				if(steps.get(0) - t <= 0)
					this.cancel();
				break;
			}
		}
	}
	
	public void callMessage(long t) {
		this.message.callMessage(t);
	}

	
	
}
