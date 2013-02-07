import java.util.LinkedList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;


public class MichaelsScheiss {	
	
	public static int leftOnLine = 0;
	public static int rightOnLine =1;
	public static int ok = 2;
	public static int tower = 3;
	
	private static final int schwarz = 50;
	
	private static final NXTRegulatedMotor motorRight = Motor.C;
	private static final NXTRegulatedMotor motorLeft = Motor.A;
	private static final NXTRegulatedMotor motorSpezial = Motor.B;
	private static final TouchSensor debug = new TouchSensor(SensorPort.S2);

	private static final LightSensor lightLinie = new LightSensor(SensorPort.S1); //TOTO
	private static final LightSensor lightUVorne = new LightSensor(SensorPort.S3);
	private static final LightSensor lightUHinten = new LightSensor(SensorPort.S4);
	
	static int status = ok; 
	
	public static void main(String args[]){
		initLightSensors();
		motorSpezialBack();
		motorLeft.setSpeed(900);
		motorRight.setSpeed(900);
		while(Button.readButtons() == 0){
			if(isOnLine(lightUVorne) != isOnLine(lightUHinten)){
				motorLeft.rotate(30, true);
				motorRight.rotate(30, true);
			}
			if(!isOnLine(lightLinie)){
				motorLeft.rotate(30, true);
			}else{
				motorRight.rotate(30, true);
			}
		}
	}
	
	public static boolean isOnLine(LightSensor l){
		return lightLinie.getLightValue() < schwarz;
	}
	
	private static void initLightSensors() {
		System.out.println("Press debug for black!");
		waitForTouch(debug);
		lightLinie.calibrateLow();
		lightUVorne.calibrateLow();
		lightUHinten.calibrateLow();
		Sound.beep();
		waitForTouch(debug);
		System.out.println("Press debug for white!");
		lightLinie.calibrateHigh();
		lightUVorne.calibrateHigh();
		lightUHinten.calibrateHigh();
		Sound.beep();
	}
	
	private static void motorSpezialBack() {
		motorSpezial.rotateTo(0);
		motorSpezial.stop();
	}
	
	public static void motorSpezialForward() {
		motorSpezial.setSpeed(50);
		motorSpezial.rotateTo(70);
		motorSpezial.stop();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	}
	
	public static void waitForTouch(TouchSensor sens){
		while(!sens.isPressed()){};
		while(sens.isPressed()){};
	}

}
