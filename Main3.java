import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.util.Delay;


public class Main3 {

	public static int leftOnLine = 0;
	public static int rightOnLine =1;
	public static int ok = 2;
	public static int tower = 3;

	// ...........................SETTINGS...........................//
	private static final int schwarz = 50;
	private static final int maxSpeed = 400;
	private static final int backSpeed = maxSpeed/4;
	private static final int deg90 = 207;

	private static final NXTRegulatedMotor motorRight = Motor.C;
	private static final NXTRegulatedMotor motorLeft = Motor.A;
	private static final NXTRegulatedMotor motorSpezial = Motor.B;
	private static final TouchSensor debug = new TouchSensor(SensorPort.S2);

	private static final LightSensor lightRight = new LightSensor(SensorPort.S1);
	private static final LightSensor lightLeft = new LightSensor(SensorPort.S4);
	private static final LightSensor lightCenter = new LightSensor(SensorPort.S3);
	
	private static Seeker seeker;
	// ...........................SETTINGS...........................//

	// ...........................VARS...........................//
	public static int status = ok;
	public static boolean slow = false;
	private static int towerCount = 0;
	private static int val = deg90;
	// ...........................VARS...........................//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLightSensors();
		//motorSpezialForward();
		motorSpezialBack();
		motorRight.setSpeed(getMaxSpeed());
		motorLeft.setSpeed(getMaxSpeed());
		motorRight.forward();
		motorLeft.forward();
		seeker = new Seeker(lightCenter);
		while (Button.readButtons() == 0) {
			int[] values = getLightValues();
			if(status == ok){
				motorRight.setSpeed(getMaxSpeed());
				motorRight.forward();
				motorLeft.setSpeed(getMaxSpeed());
				motorLeft.forward();
				if(values[0] < schwarz){
					status = leftOnLine;
				}
				if(values[1] < schwarz){
					status = rightOnLine;
				}
			}else if(status == leftOnLine){
				motorLeft.setSpeed(getBackSpeed());
				motorLeft.backward();
				motorRight.setSpeed(getMaxSpeed());
				motorRight.forward();
				if(values[0] > schwarz){
					status = ok;
				}
			}else if(status == rightOnLine){
				motorRight.setSpeed(getMaxSpeed());
				motorRight.backward();
				motorLeft.setSpeed(getMaxSpeed());
				motorLeft.forward();
				if(values[1] > schwarz){
					status = ok;
				}
			}else if(status == tower){
				ballUmWerfen();
			}
		}
	}
	
	private static int getMaxSpeed(){
		if(slow){
			return maxSpeed/4;
		}
		return maxSpeed;
	}
	
	private static int getBackSpeed(){
		if(slow){
			return backSpeed/4;
		}
		return backSpeed;
	}

	private static void initLightSensors() {
		System.out.println("Press debug for black!");
		waitForTouch(debug);
		lightLeft.calibrateLow();
		lightRight.calibrateLow();
		lightCenter.calibrateLow();
		Sound.beep();
		waitForTouch(debug);
		System.out.println("Press debug for white!");
		lightLeft.calibrateHigh();
		lightRight.calibrateHigh();
		lightCenter.calibrateHigh();
		Sound.beep();
	}

	private static int[] getLightValues() {
		int[] i = {lightRight.getLightValue(), lightLeft.getLightValue()};
		return i;

	}

	private static void ballUmWerfen() {
		motorRight.stop();
		motorLeft.stop();
//		int val = deg90;
		turn(getMaxSpeed(), val);
		ausrichten();
		motorSpezialForward();
		forward(getMaxSpeed(), 310);
		waitForTouch(debug);
		forward(getMaxSpeed(), -310);
		turn(getMaxSpeed(), -val);
		motorSpezialBack();
		val*=(-1);
		status = Main3.ok;
	}
	
	private static void ausrichten(){
		forward(maxSpeed,30);
		motorRight.setSpeed(maxSpeed/8);
		motorLeft.setSpeed(maxSpeed/8);
		motorLeft.backward();
		motorRight.backward();
		while(motorLeft.isMoving() && motorRight.isMoving()){
			if(lightRight.getLightValue() < schwarz){
				motorLeft.stop();
			}
			if(lightLeft.getLightValue() < schwarz){
				motorRight.stop();
			}
		}
	}
	
	private static void motorSpezialBack() {
		motorSpezial.rotateTo(0);
		motorSpezial.stop();
	}
	
	public static void motorSpezialForward() {
		motorSpezial.setSpeed(50);
		motorSpezial.rotateTo(75);
		motorSpezial.stop();
	}
	
	public static void turn(int speed, int angle){
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.rotate(angle,true);
		motorLeft.rotate(-angle);
	}
	public static void forward(int speed, int distance){
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.rotate(distance,true);
		motorLeft.rotate(distance);
	}
	
	public static void waitForTouch(TouchSensor sens){
		while(!sens.isPressed()){};
		while(sens.isPressed()){};
	}
}