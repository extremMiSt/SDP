import lejos.nxt.LightSensor;


public class Seeker2 extends Thread{
	
	private static final boolean BTW = false;
	private static final boolean WTB = true;
	
	private LightSensor sens;
	private Main4 bot;
	private int state = 0;
	private boolean last = WTB;
	
	public Seeker2(LightSensor sens, Main4 bot){
		super();
		this.sens = sens;
		this.bot = bot;
		this.setDaemon(true);
	}

	
	/**
	 * false = unterbrechung, true = auf der linie
	 */
	public void run() {
		boolean neu;
		while (true) {
			if (neu = sens.getLightValue() < 50) {
				if(neu != last){
					if(state == 0){
						state = 0;
					}else if(state == 1){
						state = 2;
					}else if(state == 2){
						state = 1;
					}else if(state == 3){
						state = 4;
					}else if(state == 4){
						state = 0;
					}else if(state == 5){
						state = 6;
					}else if(state == 6){
						state = 0;
						bot.interrupt(true);
					}else{
						state = 0;
					}
				}
			} else {
				if(neu != last){
					if(state == 0){
						state = 1;
					}else if(state == 1){
						state = 1;
					}else if(state == 2){
						state = 3;
					}else if(state == 3){
						state = 1;
					}else if(state == 4){
						state = 5;
					}else if(state == 5){
						state = 1;
					}else if(state == 6){
						state = 0;
						bot.interrupt(true);
					}else{
						state = 0;
					}
				}
			}
		}
	}
}
