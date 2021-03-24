/**----------------------------------------------------------------------------
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             
/* Open Source Software - may be modified and shared by FRC teams. The code   
/* must be accompanied by the FIRST BSD license file in the root directory of 
/* the project.                                                               
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.RobotMap;
import frc.robot.Robot;


/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  ControlMode po = ControlMode.PercentOutput;


  TalonFX frw = new TalonFX(RobotMap.frw);
  TalonFX flw = new TalonFX(RobotMap.flw);
  TalonFX brw = new TalonFX(RobotMap.brw);
  TalonFX blw = new TalonFX(RobotMap.blw);

  float lockAngle;

  //frw.setIdleMode(IdleMode.kBrake);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  

  public void setEachWheel(double fr,double fl,double br,double bl){
    frw.set(po,fr);
    flw.set(po,-fl);
    brw.set(po,br);
    blw.set(po,-bl);
  }

  public void setWheels(double[] speeds){
    frw.set(po,speeds[0]);
    flw.set(po,-speeds[1]);
    brw.set(po,speeds[2]);
    blw.set(po,-speeds[3]);
  }

  public void setWheel(TalonFX wheel,double speed){
    wheel.set(po,speed);
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
    lockAngle = Robot.ahrs.getYaw();
  }
  public double lock(double rate){
    return (lockAngle-Robot.ahrs.getYaw())*rate;
  }





}