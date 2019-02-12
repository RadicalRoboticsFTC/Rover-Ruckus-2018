package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name="Driver Control Test", group="Tests")
public class DriverControlTest extends OpMode {
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private DcMotor ArmPivot;
    private DcMotor WinchArm;
    private Servo ArmServo;
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static Servo LeftMarker;
    //private static DistanceSensor Dsense;
    //private ColorSensor colorSensor;

    double fr;
    double fl;
    double br;
    double bl;
    double cl;
    double power;

    double count = 0;



    @Override
    public void init() {
        FrontRight = (DcMotor) hardwareMap.get("FrontRight");
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        ArmPivot = (DcMotor) hardwareMap.get("ArmPivot");
        WinchArm = (DcMotor) hardwareMap.get("WinchArm");
        ArmServo = (Servo) hardwareMap.get("ArmServo");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");
        //Dsense = (DistanceSensor) hardwareMap.get("Dsense");
        //colorSensor = (ColorSensor) hardwareMap.get("Pha");

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
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
    public void init_loop() {
    }

    @Override
    public void start() {
    }


    @Override
    public void loop(){
        telemetry.addData("FrontLeft Encoder", FrontLeft.getCurrentPosition());
        telemetry.addData("Arm Encoder", Arm.getCurrentPosition());
        telemetry.addData("Winch Encoder", Winch.getCurrentPosition());
        telemetry.addData("Winch System count", count);

        count = 0;

        /**Latching System*/

        while(gamepad1.dpad_up/*&& count < 1*/){
            Arm.setPower(.1);
            Winch.setPower(1);

            count++;
        }

        if(!gamepad1.dpad_up){
            Arm.setPower(0);
            Winch.setPower(0);
        }

        while(gamepad1.dpad_down/*&& count < 1*/){
            Arm.setPower(-.1);
            Winch.setPower(-1);

            count++;
        }

        if (!gamepad1.dpad_down) {
            Arm.setPower(0);
            Winch.setPower(0);
        }

        while (gamepad1.dpad_left /*&& count < 1*/){
            Winch.setPower(-1);

            count++;
        }

        if (!gamepad1.dpad_left) {
            Winch.setPower(0);
        }

        while (gamepad1.dpad_right/*&& count < 1*/){
            Winch.setPower(1);

            count++;
        }

        if (!gamepad1.dpad_right) {
            Winch.setPower(0);
        }

        /**End Latching System*/


        /**Mineral System*/

        if (!gamepad1.x) {
            WinchArm.setPower(0);
        }else{
            WinchArm.setPower(1);
        }

        if (!gamepad1.b) {
            WinchArm.setPower(0);
        }else{
            WinchArm.setPower(-1);
        }

        if (!gamepad1.a) {
            ArmPivot.setPower(0);
        }else{
            ArmPivot.setPower(-1);
        }

        if (!gamepad1.y) {
            ArmPivot.setPower(0);
        }else{
            ArmPivot.setPower(1);
        }

        if (gamepad1.right_bumper) {
            ArmServo.setPosition(1);
        }

        if(gamepad1.left_bumper){
            ArmServo.setPosition(0);
        }

        if(gamepad1.right_trigger == 1){
            ArmPivot.setPower(1);

            ArmServo.setPosition(count);

            count  = count + .001;
        }

        if(gamepad1.right_trigger > .5){ // to test count and triggers
            count  = count + .001;
        }

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

        /*if(gamepad1.left_stick_x > .8){
            FrontRight.setPower(1 * power);
            FrontLeft.setPower(-1 * power);
            BackRight.setPower(-1 * power);
            BackLeft.setPower(1 * power);
        }else if(gamepad1.left_stick_x < -.8){
            FrontRight.setPower(-1 * power);
            FrontLeft.setPower(1 * power);
            BackRight.setPower(1 * power);
            BackLeft.setPower(-1 * power);
        }

        if(gamepad1.left_stick_y > .8){
            FrontRight.setPower(1 * power);
            FrontLeft.setPower(1 * power);
            BackRight.setPower(1 * power);
            BackLeft.setPower(1 * power);
        }else if(gamepad1.left_stick_y < -.8){
            FrontRight.setPower(-1 * power);
            FrontLeft.setPower(-1 * power);
            BackRight.setPower(-1 * power);
            BackLeft.setPower(-1 * power);
        }
        
        if(gamepad1.right_stick_x > .8){
            FrontRight.setPower(-1 * power);
            FrontLeft.setPower(1 * power);
            BackRight.setPower(-1 * power);
            BackLeft.setPower(1 * power);
        }else if(gamepad1.right_stick_x < -.8){
            FrontRight.setPower(1 * power);
            FrontLeft.setPower(-1 * power);
            BackRight.setPower(1 * power);
            BackLeft.setPower(-1 * power);
        }

        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);*/

        /**End Drive Train*/
    }
}
