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


public class Shot extends Subsystem {
  /**
   * Creates a new Shot.
   */

  CANSparkMax lshot = new CANSparkMax(RobotMap.lshot,MotorType.kBrushless);
  CANSparkMax rshot = new CANSparkMax(RobotMap.rshot,MotorType.kBrushless);

  public Shot() {

  }

  @Override
  public void initDefaultCommand() {
    // This method will be called once per scheduler run
  }

  public void shoot(double power,double angle){
    lshot.set(power*Math.sin(angle+45));
    rshot.set(power*Math.cos(angle+45));
  }
}
