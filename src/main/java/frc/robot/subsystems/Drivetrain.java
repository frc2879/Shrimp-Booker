/**----------------------------------------------------------------------------
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             
/* Open Source Software - may be modified and shared by FRC teams. The code   
/* must be accompanied by the FIRST BSD license file in the root directory of 
/* the project.                                                               
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  CANSparkMax frw = new CANSparkMax(RobotMap.frw,MotorType.kBrushless);
  CANSparkMax flw = new CANSparkMax(RobotMap.flw,MotorType.kBrushless);
  CANSparkMax brw = new CANSparkMax(RobotMap.brw,MotorType.kBrushless);
  CANSparkMax blw = new CANSparkMax(RobotMap.blw,MotorType.kBrushless);

  double lockAngle;

  //frw.setIdleMode(IdleMode.kBrake);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  

  public void setEachWheel(double fr,double fl,double br,double bl){
    frw.set(fr);
    flw.set(-fl);
    brw.set(br);
    blw.set(-bl);
  }

  public void setWheels(double[] speeds){
    frw.set(speeds[0]);
    flw.set(-speeds[1]);
    brw.set(speeds[2]);
    blw.set(-speeds[3]);
  }

  public void setWheel(CANSparkMax wheel,double speed){
    wheel.set(speed);
  }



  public double[] mecanumSpeeds(double x,double y, double a){
    double[] motion = {x,y,a};
    int[][] wheelBool = {{-1,1,-1},{1,1,1},{1,1,-1},{-1,1,1}};
    double[] wheelSpeeds = {0,0,0,0};
    for(int w = 0;w < 4;w ++){
      for(int m = 0;m < 3;m ++){
        wheelSpeeds[w]+=wheelBool[w][m]*motion[m];
      }
    }
    return wheelSpeeds;
  }

  public void mecanumMove(double x,double y,double a){
    setWheels(mecanumSpeeds(x,y,a));
  }





  public void setLock(){
    lockAngle = Robot.getYaw();
  }
  public void adjustLockAngle(double angle){
    lockAngle+=angle;
  }
  public double lock(double rate){
    System.out.println(lockAngle-Robot.getYaw());
    return (lockAngle-Robot.getYaw())*rate;
    
  }




}