package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Driver Control", group="Tests")
public class DriverControl extends OpMode  {
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private DcMotor ArmPivot;
    private DcMotor ArmChain;
    //private CRServo ArmServo;//The extender to the Minearal intake
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static Servo LeftMarker;
    private CRServo IntakeServo; // spins intake
    private Servo TrayServo; // mineral holder currently doesn't need to move
    //private CRServo ElementIntakeServo;
    //private static DistanceSensor Dsense;
    //private ColorSensor colorSensor;
    int other = 0;
    int other2 = 0;
    int other3 = 0;
    int other4 = 0;

    double fr;
    double fl;
    double br;
    double bl;
    double cl;
    double power;

    double count = 0;

    String position;



    @Override
    public void init() {
        FrontRight = (DcMotor) hardwareMap.get("FrontRight");
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        ArmPivot = (DcMotor) hardwareMap.get("ArmPivot");
        ArmChain = (DcMotor) hardwareMap.get("ArmChain");
        //ArmServo = (CRServo) hardwareMap.get("ArmServo");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");
        IntakeServo = (CRServo) hardwareMap.get("IntakeServo");
        TrayServo = (Servo) hardwareMap.get("TrayServo");
        //ElementIntakeServo = (CRServo) hardwareMap.get("ElementIntakeServo");

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        //FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        //BackLeft.setDirection(DcMotor.Direction.REVERSE);
        Arm.setDirection(DcMotor.Direction.REVERSE);
        ArmPivot.setDirection(DcMotor.Direction.REVERSE);

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ArmChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ArmChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //ArmChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }


    @Override
    public void loop(){
        telemetry.addData("FrontLeft Encoder", FrontLeft.getCurrentPosition());
        telemetry.addData("Arm Encoder", Arm.getCurrentPosition());
        //telemetry.addData("Winch Encoder", Winch.getCurrentPosition());
        //telemetry.addData("Winch System count", count);
        //telemetry.addData("WinchArm Encoder", ArmChain.getCurrentPosition());
        telemetry.addData("ArmPivot Encoder", ArmPivot.getCurrentPosition());
        telemetry.addData("Chain", ArmChain.getCurrentPosition());

        count = 0;

        /**Latching System*/

        if(gamepad1.dpad_right){
            Winch.setPower(1);
        }else if(gamepad1.dpad_left){
            Winch.setPower(-1);
        }else if(gamepad1.dpad_up){
            Arm.setPower(.1);
            Winch.setPower(1);
        }else if(gamepad1.dpad_down){
            Arm.setPower(-.1);
            Winch.setPower(-1);
        }else{
            Arm.setPower(0);
            Winch.setPower(0);
        }

        /**End Latching System*/

        /**Mineral System*/
        if(gamepad1.a){
            other = 0;
            other2 = 0;
            other3 = 0;
            other4 = 0;
            while((other ==0 || other2 == 0) && ArmChain.getCurrentPosition() > -600){
                if( ArmPivot.getCurrentPosition()<450){
                    ArmPivot.setPower(.35);
                    if(ArmPivot.getCurrentPosition() > 300){
                        ArmPivot.setPower(.2);
                    }
                }else{
                    other = 1;
                    ArmPivot.setPower(0);
                }

                if(ArmChain.getCurrentPosition()>-650 && ArmPivot.getCurrentPosition() > 250){
                    ArmChain.setPower(-.4);
                }else{
                    other2 = 1;
                    ArmChain.setPower(0);
                }

                if(gamepad1.start){
                    break;
                }
            }
            if(ArmPivot.getCurrentPosition()< 300){
            ArmPivot.setPower(.2);
            }
        }else if(gamepad1.b){
            other = 0;
            other2 = 0;
            other3 = 0;
            other4 = 0;
            while(other == 0 /*|| other2 == 0*/){
                if(ArmPivot.getCurrentPosition() > 0){
                    if(ArmPivot.getCurrentPosition() < 600 && other3 == 0){
                        ArmPivot.setPower(.2);
                    }else{
                        ArmPivot.setPower(0);
                        other3 = 1;
                    }
                    //ArmPivot.setPower(-.2);
                    if(ArmPivot.getCurrentPosition() < 200 && other4 == 0 && other3 == 1){
                        ArmPivot.setPower(.075);
                        if(ArmPivot.getCurrentPosition() < 250){
                            other4 = 1;
                        }
                    }else if(other4 == 1){
                        ArmPivot.setPower(0);
                    }
                }else{
                    other = 1;
                    ArmPivot.setPower(0);
                }
                if(other3 == 1){
                    ArmChain.setPower(.35);
                }else if(ArmPivot.getCurrentPosition() < 200){
                    ArmChain.setPower(0);
                }else if(ArmChain.getCurrentPosition() < 150){
                    ArmChain.setPower(0);
                    other2 = 1;
                }
                if(gamepad1.start){
                     break;
                }
            }
        }else if(gamepad1.x){
            other = 0;
            other2 = 0;
            other3 = 0;
            other4 = 0;
            while(other == 0||other2 == 0){
                if(ArmPivot.getCurrentPosition() < 650){
                    ArmPivot.setPower(.2);
                }else if(ArmPivot.getCurrentPosition() > 650 && ArmPivot.getCurrentPosition() < 750){
                    ArmPivot.setPower(.05);
                }else{
                    other = 1;
                    ArmPivot.setPower(0);
                }

                if(ArmChain.getCurrentPosition() < -600 || ArmChain.getCurrentPosition() > -500){
                    if(ArmChain.getCurrentPosition() < -650) {
                        ArmChain.setPower(.2);
                    }else if (ArmChain.getCurrentPosition() < -625) {
                        ArmChain.setPower(.075);
                    }else if(ArmChain.getCurrentPosition() < -575){
                        ArmChain.setPower(-.025);
                    }else if(ArmChain.getCurrentPosition() > -510){
                        ArmChain.setPower(-.18);
                    /*}else if(ArmChain.getCurrentPosition() > -500) {
                        ArmChain.setPower(-.125);*/
                    }else if(ArmChain.getCurrentPosition() > -450) {
                        ArmChain.setPower(-.55);
                    }
                }else{
                    other2 = 1;
                    ArmChain.setPower(0);
                }

                if(gamepad1.start){
                    break;
                }
            }
        }else if(gamepad1.y){
            other = 0;
            other2 = 0;
            other3 = 0;
            other4 = 0;
            while(other == 0 || other2 == 0){
                if(ArmPivot.getCurrentPosition() > 750){
                    ArmPivot.setPower(-.15);
                }else if(ArmPivot.getCurrentPosition() < 750 && ArmPivot.getCurrentPosition() > 650){
                    ArmPivot.setPower(-.075);
                }else if(ArmPivot.getCurrentPosition() <= 650 && other4 == 0){
                    ArmPivot.setPower(.075);
                }else{
                    other = 1;
                }

                if(ArmChain.getCurrentPosition() < -700){
                    ArmChain.setPower(-.1);
                }else{
                    other2 = 1;
                }

                if(gamepad1.start){
                    break;
                }
            }
            if(ArmPivot.getCurrentPosition()< 300){
                ArmPivot.setPower(.2);
            }
        }else{
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
        }

        //IntakeServo.setPower(1);

        /*if(gamepad1.x){
            ArmPivot.setTargetPosition(850);
            while(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition()) {
                if(ArmPivot.getCurrentPosition() > 650){
                    ArmPivot.setPower(.075);
                }
                ArmPivot.setPower(.15);
                ArmChain.setPower(.075);
                /* ArmChain.getTargetPosition(?);
                if(ArmChain.getCurrentPosition() > ?? && ArmChain.getCurrentPosition() < ArmChain.getTargetPosition()){
                    ArmChain.setPower(-.2);
                }
                if(gamepad1.start){
                    break;
                }
            }
            while(ArmChain.getCurrentPosition() > -500){
                ArmChain.setPower(-.3);
            }
            while(ArmChain.getCurrentPosition() < -600){
                ArmChain.setPower(.3);
            }
        }else if(gamepad1.b) {
            ArmPivot.setTargetPosition(500);
            while(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition() || ArmChain.getCurrentPosition() < 50){
                ArmPivot.setPower(.2);
                ArmChain.setPower(-.15);

                if(gamepad1.start){
                    break;
                }
            }

            ArmPivot.setTargetPosition(0);
            while(ArmPivot.getCurrentPosition() > ArmPivot.getTargetPosition()) {
                ArmChain.setPower(-.06);
                //ArmPivot.setPower(-.15);

                if(gamepad1.start){
                    break;
                }

                if(ArmPivot.getCurrentPosition() < 100){
                    ArmPivot.setPower(-.075);
                }
            }
        } else if(gamepad1.a){
            //ArmPivot.setTargetPosition(500);
            while(ArmPivot.getCurrentPosition() < 500 || ArmChain.getCurrentPosition() > -741) {
                if(ArmPivot.getCurrentPosition() < 300){
                    //ArmChain.setPower(.2);
                    ArmChain.setPower(0);
                    other = 1;
                }

                ArmPivot.setPower(.075);

                if(other == 1 && ArmChain.getCurrentPosition() < 300){
                    ArmChain.setPower(-.4);
                }

                 if(gamepad1.start){
                     break;
                 }
            }

        } else if(gamepad1.y){
            ArmPivot.setTargetPosition(400);
            while(ArmPivot.getCurrentPosition() > ArmPivot.getTargetPosition() || ArmChain.getCurrentPosition() < -741) {
                ArmChain.setPower(-.2);
                ArmPivot.setPower(-.15);
                /* ArmChain.getTargetPosition(?);
                if(ArmChain.getCurrentPosition() > ?? && ArmChain.getCurrentPosition() < ArmChain.getTargetPosition()){
                    ArmChain.setPower();
                }
                if(gamepad1.start){
                    break;
                }

                /*if(ArmPivot.getCurrentPosition() < 300){
                    ArmChain.setPower(-.2);
                }
            }

        }else{
            ArmPivot.setPower(0);
            ArmChain.setPower(0);
        }

        /*if(gamepad1.x){
            ArmPivot.setPower(-0.5);
            ArmChain.setPower(0.8);
        }else{
            ArmPivot.setPower(0);
            ArmChain.setPower(0);
        }
        if(!gamepad1.a){
            ArmServo.setPower(1);
        }else{
            ArmServo.setPower(0);
        }
        if(!gamepad1.b){
            ArmServo.setPower(1);
        }else{
            ArmServo.setPower(0);
        }
        if(gamepad1.y){
            TrayServo.setPosition(1);
        }else{
            TrayServo.setPosition(0);
        }*/

        /*if (!gamepad1.x) {
            ElementIntakeServo.setPower(1);

        }
        if (!gamepad1.y) {
            ArmServo.setPower(0);
        }

        if(gamepad1.right_trigger > .8){
            ArmPivot.setTargetPosition(-300);
            if(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition()) {
                ArmPivot.setPower(.4);
            }
        }else if(gamepad1.left_trigger > .8){
            ArmChain.setPower(.05);
        }else if(gamepad1.a){
            ArmPivot.setTargetPosition(-295);
            if(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition()) {
                ArmChain.setPower(.03);
                ArmPivot.setPower(.5);
                ArmServo.setPower(1);
            }
        }else if(gamepad1.b){
            ArmPivot.setTargetPosition(-1650);
            if(ArmPivot.getCurrentPosition() > ArmPivot.getTargetPosition()) {
                ArmChain.setPower(-.095);
                ArmPivot.setPower(-.95);
                ArmServo.setPower(0);
            }
        }else if(gamepad1.right_bumper){
            ArmPivot.setPower(-1);
        }else{
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
        }*/

        /**End Mineral System*/


        /**Drive Train*/

        if (gamepad1.left_bumper) {
            power = .25;
        } else {
            power = 1;
        }

        double gamepad1LeftY = gamepad1.left_stick_y * power;
        double gamepad1LeftX = gamepad1.left_stick_x * power;
        double gamepad1RightX = gamepad1.right_stick_x * power;

        double fl = gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
        double fr = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        double br = gamepad1LeftY - gamepad1LeftX + gamepad1RightX;
        double bl = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        FrontRight.setPower(fl);
        FrontLeft.setPower(fr);
        BackRight.setPower(br);
        BackLeft.setPower(bl);

        /**End Drive Train*/
    }
}
