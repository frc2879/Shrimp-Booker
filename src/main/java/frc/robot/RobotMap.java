/**----------------------------------------------------------------------------
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        
/* Open Source Software - may be modified and shared by FRC teams. The code   
/* must be accompanied by the FIRST BSD license file in the root directory of 
/* the project.                                                               
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  /*
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  */

  public static final int frw = 1;
  public static final int flw = 14;
  public static final int brw = 16;
  public static final int blw = 15;
  public static final int linclaw = 0;
  public static final int rinclaw = 0;
  public static final int loutclaw = 0;
  public static final int routclaw = 0;
  public static final int flag = 0;
  public static final int conveyor = 0;
  public static final int elbow = 0;
  public static final int wrist = 0;
  public static final int lshot = 0;
  public static final int rshot = 0;
  public static final int joystick = 0;

  public static final double xDead=0.2;
  public static final double yDead=0.2;
  public static final double aDead=0.2;

  public static final double drivePk = -0.015;



}
