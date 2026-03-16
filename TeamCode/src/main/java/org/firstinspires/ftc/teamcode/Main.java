package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Mouse HID Reader", group="VisualPath")
public class Main extends LinearOpMode {
    @Override
    public void runOpMode() {
        VisualPathHIDReader reader = new VisualPathHIDReader("dev/idk");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine(reader.getDevice());
            telemetry.update();
        }
    }
}
