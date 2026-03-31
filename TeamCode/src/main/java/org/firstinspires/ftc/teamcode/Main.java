package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.HashMap;

import android.hardware.usb.UsbConstants;

@TeleOp(name="Mouse Event Reader", group="VisualPath")
public class Main extends LinearOpMode {
    @Override
    public void runOpMode() {
        VisualPathMouseReader mouse = new VisualPathMouseReader((Activity) hardwareMap.appContext, 420);

        VisualPathVirtualField field = new VisualPathVirtualField(new double[]{20, 20}, new double[]{0, 0});

        if (!mouse.start()) {
            telemetry.addLine("Mouse not found — check USB connection.");
            telemetry.update();
            waitForStart();
            return;
        }

        telemetry.addLine("Using Mouse: " + mouse.getDeviceName());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            int[] delta = mouse.consumeDelta();

            double dx = 0;
            double dy = 0;

            try {
                dx = mouse.convertDeltaToInch(delta[0]);
                dy = mouse.convertDeltaToInch(delta[1]);
            } catch (Exception ignored) {}

            telemetry.addData("dX", dx);
            telemetry.addData("dY", dy);

            field.shiftRobotPosition(dx, dy);

            telemetry.addData("Field Position X", field.getRobotPosition()[0]);
            telemetry.addData("Field Position Y", field.getRobotPosition()[1]);

            telemetry.update();

            sleep(20);
        }

        mouse.stop();
    }
}