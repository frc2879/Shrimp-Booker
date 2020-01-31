

/**
/*----------------------------------------------------------------------------
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        
/* Open Source Software - may be modified and shared by FRC teams. The code   
/* must be accompanied by the FIRST BSD license file in the root directory of 
/* the project.                                                               
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flag;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static Drivetrain drive = new Drivetrain();
  public static Claw claw = new Claw();
  public static Flag flag = new Flag();
  public static Intake intake = new Intake();
  public static Shot shot = new Shot();
  public static OI oi;
  public static AHRS ahrs;
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public int locked = 0;

  public Robot(){
    try {
      /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
      /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
      /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
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


    double motion[] = {0,0,0};
    double st = (1-oi.getStickT())/2;
    double sx = oi.getStickX()*st;
    double sy = oi.getStickY()*st;
    double sa = oi.getStickA()*st;

    intake.elevate(0.05);
    shot.shoot(0,0);
    if(oi.getStickTrig()){
      double shotAngle = sa*45;
      intake.elevate(0.5);
      flag.point(shotAngle);
      if(oi.getStickShoot()){
        intake.elevate(1);
        shot.shoot(1,shotAngle);
      }
    } else {
      
      
      motion[0] = sx;
      motion[1] = sy;
      motion[2] = sa;
      drive.mecanumMove(sx,sy,sa);

      flag.point(0);
      if(oi.getStickIntake()){
        intake.point(0);
        intake.spin(1);
        intake.elevate(0.5);
      } else {
        intake.point(45);
        intake.spin(0);
      }
    }
    if(oi.getStickClaw()){
      claw.reach(1);
    } else {
      claw.pull(1);
    }
    
    if(oi.getStickHat()==-1){
      locked = 0;
    } else {
      if(locked == 0){
        locked = 1;
        drive.setLock();
      } else {
        locked = 2;
      }
      double m = st/2;
      double a = Math.PI*oi.getStickHat()/180;
      System.out.println(Math.sin(a)+" , "+Math.cos(a));
      drive.mecanumMove(m*Math.sin(a),m*Math.cos(a),drive.lock(0.05));
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
