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


/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  CANSparkMax conveyor = new CANSparkMax(RobotMap.conveyor, MotorType.kBrushless);
  CANSparkMax lelbow = new CANSparkMax(RobotMap.lelbow, MotorType.kBrushless);
  CANSparkMax relbow = new CANSparkMax(RobotMap.relbow, MotorType.kBrushless);
  CANSparkMax wrist = new CANSparkMax(RobotMap.wrist, MotorType.kBrushless);
  double elbAngle = 0;

  // frw.setIdleMode(IdleMode.kBrake);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void elevate(double power) {
    conveyor.set(power);
  }
  public void point(double angle){
    elbAngle = angle;
  }
  public void lift(double power){
    relbow.set(-power);
    lelbow.set(-power);
  }
  public void spin(double power){
    wrist.set(power);
  }
}