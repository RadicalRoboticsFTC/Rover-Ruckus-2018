package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Disabled
@Autonomous(name="AutonomousCodeM1Old", group="Old")
public class AutonomousCodeM1Old extends LinearOpMode { // defines the function

    private static DcMotor FrontRight; // defines variables to be used in the code
    private static DcMotor FrontLeft;
    private static DcMotor BackRight;
    private static DcMotor BackLeft;
    private static DcMotor Arm;
    private static DcMotor Winch;
    private static Servo LeftMarker;
    private static String left = "Left";
    private static String right = "Right";
    private static DistanceSensor Dsense;
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private TFObjectDetector tfod;
    private int goldMineralX = -1;
    private int silverMineral1X = -1;
    private int silverMineral2X = -1;


    @Override
    public void runOpMode() { // runs code

        FrontRight = (DcMotor) hardwareMap.get("FrontRight"); // this is where we assign variables to parts
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");
        Dsense = (DistanceSensor) hardwareMap.get("Dsense");

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);


        waitForStart(); // This autonomous faces the team marker drop zone
        strafe("Left",-2000);

        strafe("Right",0);
    }

    private void turn(String direction, int TargetPosition){

        FrontLeft.setTargetPosition(TargetPosition);

        if(direction.equals(left)){
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
        }else if(direction.equals(right)){
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



        FrontLeft.setTargetPosition(TargetPosition);
        FrontLeft.setPower(1);
        FrontRight.setPower(.85);
        BackLeft.setPower(1);
        BackRight.setPower(.85);
        while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {
                /*if(Dsense < 30){
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                }*/
        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }

    private void backwards(int TargetPosition){

        FrontLeft.setTargetPosition(TargetPosition); //step 7: move towards crator and parks
        FrontLeft.setPower(-1);
        FrontRight.setPower(-.85);
        BackLeft.setPower(-1);
        BackRight.setPower(-.85);
        while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {
                /*if(Dsense < 30){
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                }*/
        }

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }

    private void strafe(String direction, int TargetPosition){

        if(direction.equals(left)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(-1);
            FrontRight.setPower(.85);
            BackLeft.setPower(1);
            BackRight.setPower(-.85);
            while (FrontLeft.getCurrentPosition() > FrontLeft.getTargetPosition()) {
                /*if(Dsense < 30){
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                }*/
            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }
        if(direction.equals(right)){
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

