package org.firstinspires.ftc.teamcode;

public class VisualPathPIDController {

    private double kP, kI, kD;

    private double integralX = 0;
    private double integralY = 0;

    private double lastErrorX = 0;
    private double lastErrorY = 0;

    private long lastTime;

    public VisualPathPIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        lastTime = System.currentTimeMillis();
    }

    public double[] calculate(double currentX, double currentY, double targetX, double targetY) {
        long now = System.currentTimeMillis();
        double dt = (now - lastTime) / 1000.0;
        lastTime = now;

        double errorX = targetX - currentX;
        double errorY = targetY - currentY;

        integralX += errorX * dt;
        integralY += errorY * dt;

        double derivativeX = (errorX - lastErrorX) / dt;
        double derivativeY = (errorY - lastErrorY) / dt;

        lastErrorX = errorX;
        lastErrorY = errorY;

        double outputX = kP * errorX + kI * integralX + kD * derivativeX;
        double outputY = kP * errorY + kI * integralY + kD * derivativeY;

        return new double[]{outputX, outputY};
    }

    public void reset() {
        integralX = 0;
        integralY = 0;
        lastErrorX = 0;
        lastErrorY = 0;
    }
}