import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.Delay;


public class Text {
	
	private static final NXTRegulatedMotor motorRight = Motor.A;
	private static final NXTRegulatedMotor motorLeft = Motor.C;

	private static final LightSensor light = new LightSensor(SensorPort.S3);
	private static final LightSensor leuchte = new LightSensor(SensorPort.S1);

	public static boolean v1, v2 = false;
	public static long t1, t2 = 0;
	public static boolean used = false;
	public static int count = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		motorLeft.setSpeed(600);
		motorRight.setSpeed(600);
		Delay.msDelay(7000);
		light.calibrateLow();
		Sound.beep();
		Delay.msDelay(3000);
		light.calibrateHigh();
		Sound.beep();
		motorLeft.forward();
		motorRight.forward();
		
		while(Button.readButtons() == 0) {
			if(light.getLightValue() > 50){
				leuchte.setFloodlight(true);
				save(false, System.currentTimeMillis());
			}else{
				leuchte.setFloodlight(false);
				save(true, System.currentTimeMillis());
			}
			if(!used && (!v1 && v2)){
				count++;
				if(count % 3 ==0){
					Sound.twoBeeps();
				}
				used = true;
			}
			
		}

	}
	
	private static void save(boolean neu, long time){
		if(v2 != neu){
			v1 = v2;
			v2 = neu;
			t1 = t2;
			t2 = time;
			used = false;
		}
	}

}
