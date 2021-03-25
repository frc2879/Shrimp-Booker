

/**
/*----------------------------------------------------------------------------
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        
/* Open Source Software - may be modified and shared by FRC teams. The code   
/* must be accompanied by the FIRST BSD license file in the root directory of 
/* the project.                                                               
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.PWMTalonFX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  

  //public static Drivetrain drive = new Drivetrain();
  public static MecanumDrive mDrive=new MecanumDrive(new PWMTalonFX(RobotMap.frw),new PWMTalonFX(RobotMap.brw),new PWMTalonFX(RobotMap.flw),new PWMTalonFX(RobotMap.blw)); //!Going to use this as issues were observed with Cole's code,
                                                                                                            //!This code is marked as deprciated.
  double calculatedRotation;
  public static OI oi;
  public static AHRS ahrs;
  public static boolean trigFlag=true; //Sets the angle of the trigger then resets
  public static double setpoint; //Setpoint for what PID should aim for
  public static PIDController pid=new PIDController(.1, 0, 0);
  public static int setpointTimer=2;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public int locked = 0;

  public Robot(){
    try {
      ahrs = new AHRS(SPI.Port.kMXP); 
    } catch (RuntimeException ex ) {
        //DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
    }
  }


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    oi = new OI();
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /**
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     **/

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    /*double st = oi.getStickT()/10;
    drive.setEachWheel(st,st,st,st);
    */
    //mecanumMove(double x,double y,double a)




  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    pid.enableContinuousInput(-180, 180); //This may be useful, unknown as of now.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //System.out.println("teleop tick");

    if(oi.isGyroReset()){ //!This will mess up the PID and Trigger mechanism. Therefore,
      ahrs.reset();        //!This should only be used during PRACTICE sessions!
    }

    SmartDashboard.putNumber("Gyro Angle",ahrs.getAngle()); //Is this how you do it?

    double st = (1-oi.getStickT())/2;
    double sx = oi.getStickX()*st;
    double sy = oi.getStickY()*st;
    double sa = oi.getStickA()*st;
    if(oi.getStickTrig()){
      if (trigFlag && setpointTimer>1){
        setpoint=ahrs.getAngle();
        trigFlag=false;
        setpointTimer=0;
      }else if(trigFlag){
        setpointTimer=0;
      }
      SmartDashboard.putNumber("PID Calculated Angle",-pid.calculate(ahrs.getAngle(), setpoint)); //Is this how you do it?
      mDrive.driveCartesian(sy,sx,-pid.calculate(ahrs.getAngle(), setpoint)); 
    } else {
      setpointTimer++;

      trigFlag=true;

      mDrive.driveCartesian(sy,sx,sa);
    }

    
    }



  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  /*
  double st = oi.getStickT()/10;
  drive.setEachWheel(st,st,st,st);
*/
}
