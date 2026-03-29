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
        VisualPathMouseReader mouse = new VisualPathMouseReader((Activity) hardwareMap.appContext);

        if (!mouse.start()) {
            telemetry.addLine("Mouse not found — check USB connection.");
            telemetry.update();
            waitForStart();
            return;
        }

        telemetry.addData("Mouse", mouse.getDeviceName());
        telemetry.addLine("Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            int[] delta = mouse.consumeDelta();

            telemetry.addData("dX", delta[0]);
            telemetry.addData("dY", delta[1]);
            telemetry.update();

            sleep(0);
        }

        mouse.stop();
    }
}