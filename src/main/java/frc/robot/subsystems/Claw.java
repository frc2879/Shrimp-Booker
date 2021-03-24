/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;




public class Claw extends Subsystem {
  /**
   * Creates a new Claw.
   */

  CANSparkMax linclaw = new CANSparkMax(RobotMap.linclaw,MotorType.kBrushless);
  CANSparkMax rinclaw = new CANSparkMax(RobotMap.rinclaw,MotorType.kBrushless);
  CANSparkMax loutclaw = new CANSparkMax(RobotMap.loutclaw,MotorType.kBrushless);
  CANSparkMax routclaw = new CANSparkMax(RobotMap.routclaw,MotorType.kBrushless);

  public Claw() {

  }

  @Override
  public void initDefaultCommand() {
    // This method will be called once per scheduler run
  }

  public void reach(double power){
    routclaw.set(power);
    loutclaw.set(power);
    rinclaw.set(-power);
    linclaw.set(-power);
  }
  public void pull(double power){
    rinclaw.set(power);
    linclaw.set(power);
    routclaw.set(-power);
    loutclaw.set(-power);
  }
}
