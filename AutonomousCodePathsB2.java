package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous(name="AutonomousCodePathsB2", group="Old")
public class AutonomousCodePathsB2 extends LinearOpMode { // defines the function

    private static DcMotor FrontRight; // defines variables to be used in the code
    private static DcMotor FrontLeft;
    private static DcMotor BackRight;
    private static DcMotor BackLeft;
    private static DcMotor Arm;
    private static DcMotor Winch;
    private static Servo LeftMarker;
    private static String Left = "Left";
    private static String Right = "Right";
    private static String left = "left";
    private static String right = "right";

    @Override
    public void runOpMode() { // runs code

        FrontRight = (DcMotor) hardwareMap.get("FrontRight"); // this is where we assign variables to parts
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart(); /** AUTO 1 MIDDLE PATH **/

        LeftMarker.setPosition(.7);

        /*Arm.setTargetPosition(600); //step 1: lands on field
        Arm.setPower(.03);
        Winch.setPower(1);
        while (Arm.getCurrentPosition() < Arm.getTargetPosition()) {

        }

        Arm.setPower(0);
        Winch.setPower(0);

        strafe("Right", 1050);

        straight(170);*/

        turn("Left", -1600);

        straight(1400);

        sleep(500);

        backwards(-800);

        sleep(500);

        turn("Left", -1000);

        straight(2000);

        turn("Right", 700);

        straight(1000);

        sleep(500);

        turn("Left", -1375);

        sleep(500);

        straight(4000);

        LeftMarker.setPosition(0);

        backwards(-5600);
    }

    private void turn(String direction, int TargetPosition){

        FrontLeft.setTargetPosition(TargetPosition);

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(50);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if(direction.equals(left) || direction.equals(Left)){
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

        }else if(direction.equals(right) || direction.equals(Right)){
            FrontLeft.setPower(1);
            FrontRight.setPower(-1);
            BackLeft.setPower(1);
            BackRight.setPower(-1);
            while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }
    }

    private void straight(int TargetPosition){

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(50);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeft.setTargetPosition(TargetPosition);
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
    }

    private void backwards(int TargetPosition){

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(50);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeft.setTargetPosition(TargetPosition); //step 7: move towards crator and parks
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

    private void strafe(String direction, int TargetPosition){

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(50);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if(direction.equals(left) || direction.equals(Left)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(-1);
            FrontRight.setPower(.85);
            BackLeft.setPower(1);
            BackRight.setPower(-.85);
            while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {

            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);

        }else if(direction.equals(right) || direction.equals(Right)){
            FrontLeft.setTargetPosition(TargetPosition);

            FrontLeft.setPower(1);
            FrontRight.setPower(-.85);
            BackLeft.setPower(-1);
            BackRight.setPower(.85);
            while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }
    }
}

