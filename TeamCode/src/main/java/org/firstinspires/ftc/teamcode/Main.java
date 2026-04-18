package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.HashMap;

import android.hardware.usb.UsbConstants;

@TeleOp(name="VisualPath Movement Test", group="VisualPath")
public class Main extends LinearOpMode {
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() {
        VisualPathMouseReader mouse = new VisualPathMouseReader((Activity) hardwareMap.appContext, 420);

        VisualPathVirtualField field = new VisualPathVirtualField(new double[]{20, 20}, new double[]{0, 0});
        try {field.readElementJSON(); telemetry.addLine(field.getElements().toString());} catch (Exception e) {telemetry.addLine(e.toString());}

        if (!mouse.start()) {
            telemetry.addLine("Mouse not found — check USB connection.");
            telemetry.update();
            waitForStart();
            return;
        }

        telemetry.addLine("Using Mouse: " + mouse.getDeviceName());
        telemetry.update();

        double targetX = 0;
        double targetY = 0;

        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRightDrive");

        VisualPathPIDController controller = new VisualPathPIDController(0.01, 0.0, 0.0);
        boolean moving = false;

        waitForStart();

        while (opModeIsActive()) {

            int[] delta = mouse.consumeDelta();

            double dx = delta[0];
            double dy = delta[1];

            field.shiftRobotPosition(dx, dy);

            targetX += gamepad1.left_stick_x * 2;
            targetY -= gamepad1.left_stick_y * 2;

            if (gamepad1.a) moving = true;
            if (gamepad1.b) {
                moving = false;
                controller.reset();
            }

            double currentX = field.getRobotPosition()[0];
            double currentY = field.getRobotPosition()[1];

            double xPower = 0;
            double yPower = 0;

            if (moving) {
                double[] output = controller.calculate(currentX, currentY, targetX, targetY);
                xPower = output[0];
                yPower = output[1];
            }

            double fl = yPower + xPower;
            double fr = yPower - xPower;
            double bl = yPower - xPower;
            double br = yPower + xPower;

            double max = Math.max(1.0, Math.abs(fl));
            max = Math.max(max, Math.abs(fr));
            max = Math.max(max, Math.abs(bl));
            max = Math.max(max, Math.abs(br));

            frontLeftDrive.setPower(fl / max);
            frontRightDrive.setPower(fr / max);
            backLeftDrive.setPower(bl / max);
            backRightDrive.setPower(br / max);

            writeTelemetry(dx, dy, targetX, targetY, field);

            sleep(20);
        }

        mouse.stop();
    }

    public void writeTelemetry(double dx, double dy, double targetX, double targetY, VisualPathVirtualField field) {
        telemetry.addData("Field Position X", field.getRobotPosition()[0]);
        telemetry.addData("Field Position Y", field.getRobotPosition()[1]);

        telemetry.addData("Target X", targetX);
        telemetry.addData("Target Y", targetY);

        telemetry.update();
    }
}