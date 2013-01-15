import java.util.LinkedList;

import lejos.nxt.LightSensor;

class Seeker extends Thread {

	public LightSensor light;
	public int minZeit = 700;
	public int maxZeit = 1000;
	public int tinyMaxZeit = 450;
	public int tinyMinZeit = 20;
	
	LinkedList<Boolean> bools = new LinkedList<Boolean>();
	LinkedList<Long> times = new LinkedList<Long>();
	public boolean used = false;

	public Seeker(LightSensor spezial) {
		super();
		this.setDaemon(true);
		light = spezial;
		this.start();
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
			if (times.get(i) < time - maxZeit) {
				times.remove(i);
				bools.remove(i);
			}else {
				break;
			}
		}
	}
	
	public synchronized boolean test(){
		if(bools.size() >= 6){
			if(times.get(0)- times.get(5) > minZeit && times.get(0)- times.get(5) < maxZeit){
				if(!bools.get(5) && bools.get(4) &&  !bools.get(3) &&  bools.get(2) &&  !bools.get(1) &&  bools.get(0)){
					for (int i = 1; i < 6; i++) {
						if(times.get(i-1) - times.get(i) < tinyMaxZeit && times.get(i-1) - times.get(i) > tinyMinZeit){
							continue;
						}else{
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

}
