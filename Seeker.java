import java.util.LinkedList;

import lejos.nxt.LightSensor;

class Seeker extends Thread {

	public LightSensor light;
	public int zeit = 1500;
	public int r = 300;
	private int[] time; // [0]minZeit, [1]maxZeit, [2]tinyMax, [3] tinyMin
	
	LinkedList<Boolean> bools = new LinkedList<Boolean>();
	LinkedList<Long> times = new LinkedList<Long>();
	public boolean used = false;
	

	public Seeker(LightSensor spezial) {
		super();
		this.setDaemon(true);
		light = spezial;
		time = times();
		this.start();
	}
	
	public int[] times() {
		int speed = Main3.maxSpeed;
		int[] fuick = new int[4];
		fuick[0] = ((zeit - r)*100)/speed;
		fuick[1] = ((zeit + r)*100)/speed;
		fuick[2] = ((zeit + r)/5*100)/speed;
		fuick[3] = ((zeit - r)/5*100)/speed;
		return fuick;		
	}
	
	/**
	 * false = unterbrechung, true = auf der linie
	 */
	public void run() {
		while (true) {
			if (light.getLightValue() > 50) {
				update(false, System.currentTimeMillis());
				Main3.slow = true;
			} else {
				update(true, System.currentTimeMillis());
				Main3.slow = false;
			}

			if (test()) {
				used = true;
				Main3.status = Main3.tower;
			}
			//while(new TouchSensor(SensorPort.S2).isPressed()){}
		}
	}
 
	private void update(boolean neu, long time) {
		if (bools.isEmpty() || bools.get(0) != neu) {
			bools.add(0, neu);
			times.add(0, time);
			used = false;
		} else {
			times.set(0, time);
		}
		for (int i = bools.size() - 1; i >= 0; i--) {
			if (times.get(i) < time - this.time[1]) {
				times.remove(i);
				bools.remove(i);
			}else {
				break;
			}
		}
	}
	
	public synchronized boolean test(){
		if(bools.size() >= 6){
			if(times.get(0)- times.get(5) > time[0] && times.get(0)- times.get(5) < time[1]){
				if(!bools.get(5) && bools.get(4) &&  !bools.get(3) &&  bools.get(2) &&  !bools.get(1) &&  bools.get(0)){
					for (int i = 1; i < 6; i++) {
						if(times.get(i-1) - times.get(i) < time[2] && times.get(i-1) - times.get(i) > time[3]){
							continue;
						}else{
							return false;
						}
					}
					return true;
				}
			}else{
			}
		}
		return false;
	}

}
