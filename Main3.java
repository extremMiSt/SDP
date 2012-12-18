import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.Delay;


public class Main3 {

	public static int leftOnLine = 0;
	public static int rightOnLine =1;
	public static int ok = 2;

	// ...........................SETTINGS...........................//
	private static final int schwarz = 50;
	private static int maxSpeed = 400;
	private static int backSpeed = maxSpeed/4;

	private static final NXTRegulatedMotor motorRight = Motor.C;
	private static final NXTRegulatedMotor motorLeft = Motor.A;
	private static final NXTRegulatedMotor motorSpezial = Motor.B; // Noch nicht
																	// vorhanden

	private static final LightSensor lightRight = new LightSensor(SensorPort.S1);
	private static final LightSensor lightLeft = new LightSensor(SensorPort.S4);
	private static final LightSensor lightCenter = new LightSensor(SensorPort.S3);
	
	//private static Seeker seeker;
	// ...........................SETTINGS...........................//

	// ...........................VARS...........................//
	public static boolean v1, v2 = false;
	public static long t1, t2 = 0;
	public static boolean used = false;
	public static int count = 1;
	private static int towerCount = 0;
	private static int status = ok;
	// ...........................VARS...........................//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLightSensors();
		initMotors();
		motorSpezialForward();
		//motorSpezialBack();
		motorRight.setSpeed(maxSpeed);
		motorLeft.setSpeed(maxSpeed);
		motorRight.forward();
		motorLeft.forward();
		//seeker = new Seeker(lightCenter);
		while (Button.readButtons() == 0) {
			int[] values = getLightValues();
			if(status == ok){
				motorRight.setSpeed(maxSpeed);
				motorRight.forward();
				motorLeft.setSpeed(maxSpeed);
				motorLeft.forward();
				if(values[0] < schwarz){
					status = leftOnLine;
				}
				if(values[1] < schwarz){
					status = rightOnLine;
				}
			}else if(status == leftOnLine){
				motorLeft.setSpeed(backSpeed);
				motorLeft.backward();
				motorRight.setSpeed(maxSpeed);
				motorRight.forward();
				if(values[0] > schwarz){
					status = ok;
				}
			}else if(status == rightOnLine){
				motorRight.setSpeed(backSpeed);
				motorRight.backward();
				motorLeft.setSpeed(maxSpeed);
				motorLeft.forward();
				if(values[1] > schwarz){
					status = ok;
				}
			}
		}
	}

	private static void initLightSensors() {
		System.out.println("5s fuer Schwarz!");
		Delay.msDelay(8000);
		lightLeft.calibrateLow();
		lightRight.calibrateLow();
		lightCenter.calibrateLow();
		Sound.beep();
		System.out.println("5s fuer Weiss!");
		Delay.msDelay(3000);
		lightLeft.calibrateHigh();
		lightRight.calibrateHigh();
		lightCenter.calibrateHigh();
		Sound.beep();
	}

	private static int[] getLightValues() {
		int[] i = {lightRight.getLightValue(), lightLeft.getLightValue()};
		return i;

	}

	private static void initMotors() {
	}

	private static void ballUmWerfen() {
		if (towerCount == 0) {

		} else if (towerCount == 1) {

		} else if (towerCount == 2) {

		} else {

		}
	}
	
	private static void motorSpezialBack() {
		motorSpezial.rotateTo(0);
		motorSpezial.stop();
	}
	
	public static void motorSpezialForward() {
		motorSpezial.setSpeed(50);
		motorSpezial.rotateTo(85);
		motorSpezial.stop();
	}
}

//class Seeker extends Thread{
//	
//	public LightSensor light;
//	
//	public boolean v1, v2 = false;
//	public long t1, t2 = 0;
//	public boolean used = false;
//	public int count = 1; 
//	
//	public Seeker(LightSensor spezial){
//		super();
//		light = spezial;
//		this.start();
//	}
//	
//	public void run(){
//		while(true){
//			if(light.getLightValue() > 50){
//				save(false, System.currentTimeMillis());
//			}else{
//				save(true, System.currentTimeMillis());
//			}
//			if(!used && (!v1 && v2)){
//				count++;
//				if(count % 3 ==0){
//					Sound.twoBeeps();
//				}
//				used = true;
//			}
//		}
//	}
//	
//	private void save(boolean neu, long time){
//		if(v2 != neu){
//			v1 = v2;
//			v2 = neu;
//			t1 = t2;
//			t2 = time;
//			used = false;
//		}
//	}
//}
