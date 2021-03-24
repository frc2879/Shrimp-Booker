/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;


public class Flag extends Subsystem {
  /**
   * Creates a new Flag.
   */

  CANSparkMax flag = new CANSparkMax(RobotMap.flag,MotorType.kBrushless);

  public Flag() {

  }

  @Override
  public void initDefaultCommand() {
    // This method will be called once per scheduler run
  }

  public void point(double angle){
    
  }
}
