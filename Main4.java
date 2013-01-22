import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;


public class Main4 implements Runnable{

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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main4 bot = new Main4();
		bot.run();
	}
	
	/*----------------------------------------------Object-------------------------------------------*/
	
	public int leftOnLine = 0;
	public int rightOnLine =1;
	public int ok = 2;
	public int tower = 3;
	
	private int status = ok;
	private int towerCount = 0;
	private int val = deg90;
	private Seeker2 seeker;
	
	public Main4(){
		initLightSensors();
		motorSpezialBack();
		motorRight.setSpeed(getMaxSpeed());
		motorLeft.setSpeed(getMaxSpeed());
		seeker = new Seeker2(lightCenter, this);
		seeker.start();
	}

	@Override
	public void run() {
		motorRight.forward();
		motorLeft.forward();
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
	private int getMaxSpeed(){
		return maxSpeed;
	}
	
	private int getBackSpeed(){
		return backSpeed;
	}

	private void initLightSensors() {
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

	private int[] getLightValues() {
		int[] i = {lightRight.getLightValue(), lightLeft.getLightValue()};
		return i;

	}

	private void ballUmWerfen() {
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
		status = ok;
	}
	
	private void ausrichten(){
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
	
	private void motorSpezialBack() {
		motorSpezial.rotateTo(0);
		motorSpezial.stop();
	}
	
	public void motorSpezialForward() {
		motorSpezial.setSpeed(50);
		motorSpezial.rotateTo(75);
		motorSpezial.stop();
	}
	
	public void turn(int speed, int angle){
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.rotate(angle,true);
		motorLeft.rotate(-angle);
	}
	
	public void forward(int speed, int distance){
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.rotate(distance,true);
		motorLeft.rotate(distance);
	}
	
	public void waitForTouch(TouchSensor sens){
		while(!sens.isPressed()){};
		while(sens.isPressed()){};
	}
	
	public void interrupt(boolean b){
		if(b){
			status = tower;
			motorLeft.stop();
			motorRight.stop();  
			Sound.beep();
		}
	}
}
