package frc.robot.PID;

//code heavily influenced by Techfire's code, frc 225
//see https://github.com/FRCTeam225/Robot2015/blob/master/src/org/techfire/team225/robot/SimplePID.java

//sets the PID CLass
public class PID {
	// initiallizes the variables for the PID LOOP
	public double kP;
	public double kI;
	public double kD;
	public double kF;
	double error;
	double target;
	public double tolerance = 100;
	int loopsStable = 0;
	double errSum = 0;
	double maxInc = 0.01;
	double previousValue = 0;
	boolean targetChangedBeforeCalculate = true;
	double maxOutput = 1, minOutput = -1;

	/**
	 * the PID controller object most of the time, only P will be needed. with uses
	 * that will persist over time like a go set distance with the drivetrain,
	 * adding I, D makes sense. use f for speed controllers google feed forward
	 * 
	 * @param kP
	 *            the gain on the proportional part
	 * @param kI
	 *            the gain on the integral
	 * @param kD
	 *            the gain on the derivative part
	 * @param kF
	 *            the gain on the feed forward, use if doing a velocity PIId, and
	 *            not just a position.
	 */
	public PID(double kP, double kI, double kD, double kF) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
	}

	/**
	 * the PID controller object most of the time, only P will be needed. with uses
	 * that will persist over time like a go set distance with the drivetrain,
	 * adding I, D makes sense. use f for speed controllers google feed forward
	 * 
	 * @param kP
	 *            the gain on the proportional part
	 * @param kI
	 *            the gain on the integral
	 * @param kD
	 *            the gain on the derivative part
	 */
	public PID(double kP, double kI, double kD) {
		this(kP, kI, kD, 0.0);
	}

	public void setOutputConstraints(double max, double min) {
		maxOutput = max;
		minOutput = min;
	}

	public double getTarget() {
		return target;
	}

	public void setMaxInc(double val) {
		this.maxInc = val;
	}

	public void setOkError(double okError) {
		this.tolerance = okError;
	}

	public boolean atTarget() {
		if (Math.abs(getError()) <= tolerance) {
			loopsStable++;
		} else {
			loopsStable = 0;
		}
		return loopsStable > 3;
	}

	public void setP(double kP) {
		this.kP = kP;
	}

	public void setI(double kI) {
		this.kI = kI;
	}

	public void setD(double kD) {
		this.kD = kD;
	}

	public void setF(double kF) {
		this.kF = kF;
	}

	public double getError() {
		if (targetChangedBeforeCalculate) {
			return Double.MAX_VALUE;
		}
		return error;
	}

	public void setTarget(double target) {
		targetChangedBeforeCalculate = true;
		this.target = target;
	}

	/**
	 * this is the core of the PID loop. It calculates the output from the input
	 * call it every time
	 * 
	 * @param input
	 *            this is the feedback device that measures what the robot is
	 *            actually doing.
	 * @return gives a number that is the output from the pid calculation,
	 */
	public double calculate(double input) {
		if (targetChangedBeforeCalculate) {
			targetChangedBeforeCalculate = false;
		}

		// P
		// proportional part of the pid loop
		error = target - input;

		// I
		if (error >= tolerance) {
			if (errSum < 0) {
				errSum = 0;
			}
			if (error < maxInc) {
				errSum += error;
			} else {
				errSum += maxInc;
			}
		} else if (error <= -tolerance) {
			if (errSum < 0) {
				errSum = 0;
			}
			if (errSum > -maxInc) {
				errSum += error;
			} else {
				errSum -= maxInc;
			}
		} else {
			errSum = 0;
		}
		// D
		double velocity = input - previousValue;
		previousValue = input;

		double output = (kP * error) + (kI * errSum) - (kD * velocity) + (kF * target);

		return output;

	}

}
