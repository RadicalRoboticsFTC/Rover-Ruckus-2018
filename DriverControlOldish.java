package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode; // this is where we import tools from other code
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@TeleOp(name="Driver Control", group="Driver Control")
@Disabled
public class DriverControlOldish extends OpMode { // this is where we start the function
    private DcMotor FrontRight; // this is where we define the variables
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private DcMotor ArmPivot;
    private DcMotor WinchArm;
    private Servo ArmServo;
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    //private TFObjectDetector tfod;
    private static Servo LeftMarker;
    private static DistanceSensor Dsense;
    //private ColorSensor colorSensor;

    double fr; // defining variables part 2
    double fl;
    double br;
    double bl;
    double cl;
    double power;



    @Override
    public void init() {
        FrontRight = (DcMotor) hardwareMap.get("FrontRight"); // this is where we assign parts to variables
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        ArmPivot = (DcMotor) hardwareMap.get("ArmPivot");
        WinchArm = (DcMotor) hardwareMap.get("WinchArm");
        ArmServo = (Servo) hardwareMap.get("ArmServo");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");
        Dsense = (DistanceSensor) hardwareMap.get("Dsense");
        //colorSensor = (ColorSensor) hardwareMap.get("Pha");

        FrontRight.setDirection(DcMotor.Direction.REVERSE); // this is where we set modes our code can execute in
        //FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        //BackLeft.setDirection(DcMotor.Direction.REVERSE);
        Arm.setDirection(DcMotor.Direction.REVERSE);

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void init_loop() { // a bunch of logic when the code should execute
    }

    @Override
    public void start() { // a bunch of logic when the code should execute
    }


    @Override
    public void loop(){ // a bunch of logic when the code should execute
        telemetry.addData("FrontLeft Encoder", FrontLeft.getCurrentPosition()); // assigns encoders to parts
        telemetry.addData("Arm Encoder", Arm.getCurrentPosition());
        telemetry.addData("Winch Encoder", Winch.getCurrentPosition());

        if (gamepad1.dpad_up == false) { // checks if where motor is where it needs to be
            Arm.setPower(0); // sets power the motor should turn
            Winch.setPower(0);
        } else {
            Arm.setPower(.2); // sets power the motor should turn
            Winch.setPower(1.0);
        }

        if (gamepad1.dpad_down == false) { // checks if where motor is where it needs to be
            Arm.setPower(0); // sets power the motor should turn
            Winch.setPower(0);
        } else {
            Arm.setPower(-.3); // sets power the motor should turn
            Winch.setPower(-1);
        }
        if (gamepad1.dpad_left == false) {
            Winch.setPower(0);
        } else {
            Winch.setPower(-1);
        }

        if (gamepad1.dpad_right == false) {
            Winch.setPower(0);
        } else {
            Winch.setPower(1);
        }

        if (gamepad1.x == false) { // links controllers to commands the code should execute
            WinchArm.setPower(0);
            LeftMarker.setPosition(.7);
        }else{
            WinchArm.setPower(1);
        }
        if (gamepad1.b == false) { // links controllers to commands the code should execute
            WinchArm.setPower(0);
            LeftMarker.setPosition(.3);
        }else{
            WinchArm.setPower(-1);
        }
        if (gamepad1.a == false) { // links controllers to commands the code should execute
            ArmPivot.setPower(0);
            LeftMarker.setPosition(0);
        }else{
            ArmPivot.setPower(-1);
        }
        if (gamepad1.y == false) { // links controllers to commands the code should execute
            ArmPivot.setPower(0);
            LeftMarker.setPosition(1);
        }else{
            ArmPivot.setPower(1);
        }

        if (gamepad1.right_bumper) { // links controllers to commands the code should execute
            ArmServo.setPosition(1);
        }

        if(gamepad1.left_bumper){
            ArmServo.setPosition(0);
        }


        if (gamepad1.left_bumper) { // links controllers to commands the code should execute
            power = .25;
        } else {
            power = 1;
        }

        double gamepad1LeftY = gamepad1.left_stick_y * power; // assigns variables to inputs
        double gamepad1LeftX = gamepad1.left_stick_x * power;
        double gamepad1RightX = gamepad1.right_stick_x * power;

        double fl = gamepad1LeftY + gamepad1LeftX + gamepad1RightX; // controller logic nightmare scary
        double fr = gamepad1LeftY - gamepad1LeftX - gamepad1RightX; //back left
        double br = gamepad1LeftY - gamepad1LeftX + gamepad1RightX; //back right
        double bl = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        FrontRight.setPower(fl); // assigns power when inputs are triggered
        FrontLeft.setPower(fr);
        BackRight.setPower(br);
        BackLeft.setPower(bl);

        /*List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            if (updatedRecognitions.size() == 3) {
                int goldMineralX = -1;
                int silverMineral1X = -1;
                int silverMineral2X = -1;
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
        }*/
    }
}
