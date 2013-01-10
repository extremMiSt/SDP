import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.util.Delay;


public class Text {
	
	private static final NXTRegulatedMotor motorRight = Motor.C;
	private static final NXTRegulatedMotor motorLeft = Motor.A;
	private static final TouchSensor sens = new TouchSensor(SensorPort.S2);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 207;
		while(Button.readButtons() == 0) {
			if(sens.isPressed()){
				System.out.println(i);
				turn(400, i);
				i+=0;
			}
		}
	}
	
	public static void turn(int speed, int angle){
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.rotate(angle, true);
		motorLeft.rotate(-angle);
	}
	
}
