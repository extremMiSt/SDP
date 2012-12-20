import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Main2 {

	// ...........................SETTINGS...........................//
	private static final int schwarz = 50;
	private static int maxSpeed = 400;
	private static int minSpeed = 0;

	private static final NXTRegulatedMotor motorRight = Motor.A;
	private static final NXTRegulatedMotor motorLeft = Motor.C;
	private static final NXTRegulatedMotor motorSpezial = Motor.B; // Noch nicht
																	// vorhanden

	private static final LightSensor lightRight = new LightSensor(SensorPort.S1);
	private static final LightSensor lightLeft = new LightSensor(SensorPort.S4);
	private static final LightSensor lightCenter = new LightSensor(
			SensorPort.S3);
	// ...........................SETTINGS...........................//

	// ...........................VARS...........................//
	private static boolean vorherDrauf = false;
	private static int zaehler = 0;
	private static int towerCount = 0;

	// ...........................VARS...........................//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLightSensors();
		initMotors();
		motorSpezialForward();
		motorRight.setSpeed(maxSpeed);
		motorLeft.setSpeed(maxSpeed);
		motorRight.forward();
		motorLeft.forward();

		while (Button.readButtons() == 0) {
			int[] lightValue = getLightValues();
			if (lightValue[1] < schwarz) {
				motorRight.forward();
				motorLeft.stop();
				// motorLeft.backward();
			} else {
				motorRight.forward();
				motorLeft.forward();
			}
			if (lightValue[0] < schwarz) {
				motorLeft.forward();
				motorRight.stop( );
				// motorRight.backward();
			} else {
				motorRight.forward();
				motorLeft.forward();
			}
			if (lightValue[2] > schwarz) {
				if (vorherDrauf == false) {
					zaehler++;
					vorherDrauf = true;
				} else {
				}
			} else {
				vorherDrauf = false;
			}
			if (zaehler == 3) {
				zaehler = 0;
				towerCount += 1;
				ballUmWerfen();
			} else {
			}
		}
	}

	private static void initLightSensors() {
		System.out.println("5s fuer Schwarz!");
		Delay.msDelay(5000);
		lightLeft.calibrateLow();
		lightRight.calibrateLow();
		lightCenter.calibrateLow();
		Sound.beep();
		System.out.println("5s fuer Weiss!");
		Delay.msDelay(5000);
		lightLeft.calibrateHigh();
		lightRight.calibrateHigh();
		lightCenter.calibrateHigh();
		Sound.beep();
	}

	private static int[] getLightValues() {
		int[] i = { lightRight.getLightValue(), lightLeft.getLightValue(),
				lightCenter.getLightValue() };
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
		motorSpezial.setSpeed(40);
		motorSpezial.rotateTo(85);
		motorSpezial.stop();
	}
	
	
}
