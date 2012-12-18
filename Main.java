import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.SoundSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;


public class Main {

	//...........................SETTINGS...........................//
	private static final int dark = 50;
	private static int maxSpeed = 0;
	private static int minSpeed = 0;

	private static final NXTRegulatedMotor motorRight = Motor.A;
	private static final NXTRegulatedMotor motorLeft = Motor.C;
	private static final NXTRegulatedMotor motorSpezial = Motor.B; //Noch nicht vorhanden
	
	private static final LightSensor lightRight = new LightSensor(SensorPort.S1);
	private static final LightSensor lightLeft = new LightSensor(SensorPort.S3);
	private static final LightSensor lightCenter = new LightSensor(SensorPort.S2);
	//...........................SETTINGS...........................//
	
	//...........................VARS...........................//
	private static boolean countedGap = false;
	private static int gapCount = 0;
	private static int towerCount = 0;
	//...........................VARS...........................//
    public static void main(String[] args) {
    	//initMotors();
        //calibrateLight();
        Sound.beep();
        System.out.println(getLightValues() + "-" + maxSpeed);
        while(Button.readButtons() == 0){
        	motorRight.forward();
        	motorLeft.forward();
/*        	int[] lightValues = getLightValues();
        	if(lightValues[0] < dark){				//Rechts auf der Linie
        		motorRight.setSpeed(minSpeed);
        		motorLeft.setSpeed(maxSpeed);
        		countedGap = false;
        	}else if(lightValues[1] < dark){		//Links auf der Linie
        		motorLeft.setSpeed(minSpeed);
        		motorRight.setSpeed(maxSpeed);
        		countedGap = false;
        	}else if(lightValues[2] < dark){		//Linie ok
        		motorRight.setSpeed(maxSpeed);
        		motorLeft.setSpeed(maxSpeed);
        		countedGap = false;
        	}else if(lightValues[2] > dark){		//Linie unterbrochen
        		if(!countedGap){
	        		gapCount++;
	        		countedGap = true;
	        		if(gapCount == 3){
	        			throwBallOff();
	        			gapCount = 0;
	        		}
        		}
        	}*/
        }
    }
    
    private static void throwBallOff() {
    	Sound.beepSequenceUp();
    	Sound.beepSequence();
    	Sound.beepSequenceUp();
    	Sound.beepSequence();
	}

	/**
     * 
     * @return index 0 = rechts, index 1 = links, index 2 = mitte
     */
    private static int[] getLightValues(){
        int[] i =  {lightRight.getLightValue(), lightLeft.getLightValue(), lightCenter.getLightValue()};
        return i;
        
    }
        
    private static void calibrateLight(){
        lightRight.setFloodlight(true);
        lightLeft.setFloodlight(true);
        lightCenter.setFloodlight(true);
        System.out.println("Hell");
        Button.waitForAnyPress();
        Sound.beep();
        lightRight.calibrateHigh();
        lightLeft.calibrateHigh();
        lightCenter.calibrateHigh();
        System.out.println("Dunkel");
        Button.waitForAnyPress();
        Sound.beep();
        lightRight.calibrateLow();
        lightLeft.calibrateLow();
        lightCenter.calibrateLow();
        System.out.println("Lichtsensoren kalibriert");
        Delay.msDelay(500);
    }
    
    private static void initMotors(){
    	if(motorLeft.getMaxSpeed()<motorRight.getMaxSpeed()){
    		if(motorLeft.getMaxSpeed() < motorSpezial.getMaxSpeed()){
    			maxSpeed = (int) motorLeft.getMaxSpeed();
    		}else{
    			maxSpeed = (int) motorSpezial.getMaxSpeed();
    		}
    	}else{
    		if(motorRight.getMaxSpeed() < motorSpezial.getMaxSpeed()){
    			maxSpeed = (int) motorRight.getMaxSpeed();
    		}else{
    			maxSpeed = (int) motorSpezial.getMaxSpeed();
    		}
    	}
    	motorLeft.setSpeed(maxSpeed);
    	motorRight.setSpeed(maxSpeed);
    	motorSpezial.setSpeed(maxSpeed);
    }
}
