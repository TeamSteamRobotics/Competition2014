/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package jerryCode;

import edu.wpi.first.wpilibj.SimpleRobot;

//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Jerry extends SimpleRobot {

    /* Robot-wide variable space for Jerry */

    /**
     * The main joystick for controlling the robot.
     */
    private final Joystick joystick1 = new Joystick(1);

    /**
     * A constant to make switching the compressor channel easily. All that is
     * needed is to change this declaration.
     */
    final int pGChannel = 3;
    
    /**
     * The Digital Input for reading pressure within the robot's pneumatic line.
     * The first parameter marks the digital channel, and the second the module.
     * Default: Jerry uses digital input module 1(param1) and channel 1 (p2).
     */
    private final DigitalInput pressureGauge = new DigitalInput(1,pGChannel);
    
    /**
    * The Relay for commanding the compressor, used to turn it on and off.
     */
    private final Talon compressor = new Talon(10);
    
    /**
     * The Talon for controlling the right drive motor.
     */
    private final Talon rightMotor = new Talon(2);
    
    /**
     * The Talon for controlling the left drive motor.
     */
    private final Talon leftMotor = new Talon(1);
    
    /**
     * The RobotDrive object to simplify driving.
     */
    private final RobotDrive robotDrive = new RobotDrive(leftMotor, rightMotor);
    
    private final Solenoid inflateTires = new Solenoid(1,1);
    private final Solenoid raiseArm = new Solenoid(1,2);
    // SOLENOIDS (1,1), (1,2), (1,3)...
    
    
    //private final Gyro gyroscope = new Gyro(-1);

    /**
     * robotInit() is a method called when the robot starts up, and is used to
     * ready all of the motors (and other hardware) for use.
     */
    public void robotInit(){
    //Use this instead of constructor since I'm not sure that constructors are
    //called from the robot
    /*
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);*/
        //*/
        //robotDrive.setSensitivity(1);
        //robotDrive.setLeftRightMotorOutputs(-1, -1);
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        //compressor.set(Relay.Value.kForward);
        while (isAutonomous() && isEnabled())
            robotDrive.drive(.5, 0); //go backward
        //                  Timer.delay(1);         //for 1 second
        //Timer.delay blocks execution
        
        //inflateTires.set(false);//and deflate tires
        
        //Other stuff...
    }

    /**
     * This function is called once each time the robot enters operator control,
     * and is used to handle all driver control during the Teleoperated period.
     */
    public void operatorControl() {
        System.out.println("In Operator Mode");
        Timer
            t3 = new Timer(),
            t4 = new Timer();
        t3.start();
        t4.start();
        while(isEnabled() && isOperatorControl())
        {
            manageCompressor();
            
            //Handle joystick movement
            robotDrive.arcadeDrive(joystick1.getY(), joystick1.getX() * -1);
            
            //Handle button presses
            
            if(joystick1.getRawButton(3)&&t3.get()>.5){
                t3.reset();
                inflateTires.set(!inflateTires.get()); //Invert inflation
            }
            
            if(joystick1.getRawButton(4)&&t4.get()>.5){
                t4.reset();
                raiseArm.set(!raiseArm.get()); //Invert arm raise
            }
        }
    }

    
    /*
     * This function manages the air compressor, activating it to keep the
     * maximum amount of air pressure possible at any time within the air tanks.
     */
    public void manageCompressor(){
        //First, set up a connection to the compressor pressure (done above).
        
//        System.out.println(pressureGauge.get());
        //Then, while it is giving us a (low pressure) signal,
        System.out.println("Pressure: " + pressureGauge.get());
        if (pressureGauge.get()) {
            compressor.set(0);
        } else {
            compressor.set(1);
        }
        //Then turn the compressor off once you're done.
        
                
    }
    
    /**
     * This function is called once each time the robot enters test mode
     */
    public void test() {
        System.out.println("In Test Mode");
        
        /*
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                try{
                    System.out.println("Relay ("+i+","+j+")");
                    Relay test = new Relay(i,j);
                    
                    System.out.println("Testing forward");
                    test.set(Relay.Value.kForward);

                    Timer.delay(1);

                    System.out.println("Testing off");
                    test.set(Relay.Value.kOff);

                    Timer.delay(1);

                    System.out.println("Testing on");
                    test.set(Relay.Value.kOn);

                    Timer.delay(1);

                    System.out.println("Testing reverse");
                    test.set(Relay.Value.kReverse);

                    Timer.delay(1);
                }catch(Exception e){
                    System.out.println("Relay didn't work");
                }
        */
        
//            compressor.set(Relay.Value.kOn);
        
        while(isEnabled() && isTest())
        {
            manageCompressor();
            ////System.out.println(pressureGuage.get());
//pressureGuage.get()?1.0:0.0);
        }
        
        
            
            /*
        //Test which solenoids werk
        for(int i = 1;i < 2; i++)
            for(int j = 1;j<4;j++){
                try{
                    Relay pleaseWork = new Relay(i,j);
                    System.out.println("Testing module "
                            +i+','+j);
                        pleaseWork.set(Relay.Value.kForward);
                    for(int k = 0; k < 500; k++){
                        Timer.delay(.0001);
                    }
                    pleaseWork.set(Relay.Value.kOff);
                }catch(Throwable t){
                    //Aww, shucks!
                    System.out.println("Module and slot numbers "
                            +i+','+j+" don't work!");
                }
                Timer.delay(.1);
        }
        //*/
    }
    /*
    private double targetTest(double rotation, double distance1, double distance2){
        //boolean inches[][] = new boolean[12*54][12*12];
        double
                x1,
                y1,
                x2,
                y2;
        
        return 0.0;
    }*/
    public void disabled(){
        
            //compressor.set(Relay.Value.kOff);
    }
}
/* =-=-=-=-=-=-=- DEPRECATED CODE AREA -=-=-=-=-=-=-= */
/* = Old and no longer used code below for reference  */

//private RobotState state = RobotState.defaultRobotState;

       
        /*
        leftMotor.startLiveWindowMode();
        rightMotor.startLiveWindowMode();
        
        inflateTires.startLiveWindowMode();
        raiseArm.startLiveWindowMode();
        */
        
//        System.out.println("Turning relay on?");
//        compressor.set(Relay.Value.kForward);
        