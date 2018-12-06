package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous; // imports tool from other code
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous
public class AutonomousCode2 extends LinearOpMode { // defines the function

    private DcMotor FrontRight; // defines variables to be used in the code
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private Servo LeftMarker;

    @Override
    public void runOpMode() { // runs code

        FrontRight = (DcMotor) hardwareMap.get("FrontRight"); // this is where we assign variables to parts
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        LeftMarker= (Servo) hardwareMap.get("LeftMarker");

       /*colorSensor = (ColorSensor) hardwareMap.get("Pha");
       telemetry.addData("Red", colorSensor.red());
       telemetry.addData("Blue", colorSensor.blue());*/

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets what mode the motors are in
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  //sets what mode the motors are in
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        Arm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart(); //this autonomous faces the crater

        Arm.setTargetPosition(600); //step 1: land
        Arm.setPower(.3);
        Winch.setPower(1);
        while(Arm.getCurrentPosition() < Arm.getTargetPosition()) {

        }

        Arm.setPower(0);
        Winch.setPower(0);

        FrontLeft.setTargetPosition(50);//step 2: moves forward to detach off of the hook
        FrontLeft.setPower(.5);
        FrontRight.setPower(-.5);
        BackLeft.setPower(-.5);
        BackRight.setPower(.5);
        while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackLeft.setPower(0);

        FrontLeft.setTargetPosition(230); //step 2: drives off latch
        FrontLeft.setPower(.5);
        FrontRight.setPower(.5);
        BackLeft.setPower(.5);
        BackRight.setPower(.5);
        while(FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()){

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackLeft.setPower(0);

        FrontLeft.setTargetPosition(-950); //step 3: turns to right mineral on field
        FrontLeft.setPower(-1);
        FrontRight.setPower(1);
        BackLeft.setPower(-1);
        BackRight.setPower(1);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()){

        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        sleep(1000);

        FrontLeft.setTargetPosition(850); //step 4; drives forward to knock off mineral
        FrontLeft.setPower(1);
        FrontRight.setPower(1);
        BackLeft.setPower(1);
        BackRight.setPower(1);
        while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        sleep(1000);

        FrontLeft.setTargetPosition(550); //step 5: drives backwards to not knock off other minerals
        FrontLeft.setPower(-1);
        FrontRight.setPower(-1);
        BackLeft.setPower(-1);
        BackRight.setPower(-1);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        sleep(1000);

        FrontLeft.setTargetPosition(-1700); //step 6: turns to the left to head to team marker drop zone
        // Ticks per rotation = 1120
        FrontLeft.setPower(-1);
        FrontRight.setPower(1);
        BackLeft.setPower(-1);
        BackRight.setPower(1);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        sleep(1000);

        FrontLeft.setTargetPosition(1900); // step 7: drives forward
        FrontLeft.setPower(1);
        FrontRight.setPower(.9);
        BackLeft.setPower(1);
        BackRight.setPower(.9);
        while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        FrontLeft.setTargetPosition(1650); //step 8: turns directly towards team marker drop zone
        FrontLeft.setPower(-1);
        FrontRight.setPower(1);
        BackLeft.setPower(-1);
        BackRight.setPower(1);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        FrontLeft.setTargetPosition(5250); // drives to drop zone
        FrontLeft.setPower(1);
        FrontRight.setPower(.85);
        BackLeft.setPower(1);
        BackRight.setPower(.85);
        while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        LeftMarker.setPosition(0); //drops marker

        FrontLeft.setTargetPosition(-1030); //drives backwards to park
        FrontLeft.setPower(-1);
        FrontRight.setPower(-.85);
        BackLeft.setPower(-1);
        BackRight.setPower(-.85);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {

        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
}

