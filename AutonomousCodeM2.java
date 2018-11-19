package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;



import org.firstinspires.ftc.robotcore.internal.tfod.Timer;


@Autonomous
public class AutonomousCodeM2 extends LinearOpMode { // defines the function

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
        LeftMarker= (Servo) hardwareMap.get("LeftMarker");
        Dsense = (DistanceSensor) hardwareMap.get("Dsense");


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
        //Arm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart(); //this autonomous faces the crater

        LeftMarker.setPosition(.7);

        Arm.setTargetPosition(900); //step 1: lands on field
        Arm.setPower(.05);
        Winch.setPower(-1);
        while (Arm.getCurrentPosition() < Arm.getTargetPosition()) {

        }

        Arm.setPower(0);
        Winch.setPower(0);

        turn("Left", 100);

        strafe("Right", 150);

        while(goldMineralX == -1 || silverMineral1X == -1 || silverMineral2X == -1){
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 3) {
                    goldMineralX = -1;
                    silverMineral1X = -1;
                    silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                        }
                    }
                }
                telemetry.update();
            }
        }

        if(goldMineralX < silverMineral1X && goldMineralX < silverMineral2X){ //Left
            straight(170);

            turn("Left", -1800);

            straight(0);

            backwards(-300);

            turn("Left", -950);

            straight(2850);

            turn("Left", 2400);

            straight(6000);

            backwards(-280);
        }else if(goldMineralX > silverMineral1X && goldMineralX > silverMineral2X){ //Right
            straight(170);

            turn("Left", -1000);

            straight(800);

            backwards(500);

            turn("Left", -1750);

            straight(1950);

            turn("Left", 1600);

            straight(5200);

            backwards(-1080);
        }else{ //Middle
            straight(170);

            turn("Left", -1400);

            straight(400);

            backwards(100);

            turn("Left", -1350);

            straight(2350);

            turn("Left", 2000);

            straight(5300);

            backwards(-680);
        }

    }

    private static void turn(String direction, int TargetPosition){
        if(direction.equals(left)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(-1);
            FrontRight.setPower(1);
            BackLeft.setPower(-1);
            BackRight.setPower(1);
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
        if(direction.equals(right)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(1);
            FrontRight.setPower(-1);
            BackLeft.setPower(1);
            BackRight.setPower(-1);
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
    }

    private static void straight(int TargetPosition){
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

    private static void backwards(int TargetPosition){
        FrontLeft.setTargetPosition(TargetPosition); //step 7: move towards crator and parks
        FrontLeft.setPower(-1);
        FrontRight.setPower(-.85);
        BackLeft.setPower(-1);
        BackRight.setPower(-.85);
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

    private static void strafe(String direction, int TargetPosition){
        if(direction.equals(left)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(-1);
            FrontRight.setPower(.85);
            BackLeft.setPower(1);
            BackRight.setPower(-.85);
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
        if(direction.equals(right)){
            FrontLeft.setTargetPosition(TargetPosition);
            FrontLeft.setPower(1);
            FrontRight.setPower(-.85);
            BackLeft.setPower(-1);
            BackRight.setPower(-.85);
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
    }


}

