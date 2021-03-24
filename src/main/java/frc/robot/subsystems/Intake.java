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
  CANSparkMax elbow = new CANSparkMax(RobotMap.elbow, MotorType.kBrushless);
  CANSparkMax wrist = new CANSparkMax(RobotMap.wrist, MotorType.kBrushless);

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

  }
  public void spin(double power){
    wrist.set(power);
  }
}