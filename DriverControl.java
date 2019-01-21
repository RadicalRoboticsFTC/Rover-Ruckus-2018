package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Driver Control", group="Tests")
public class DriverControl extends OpMode {
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private DcMotor ArmPivot;
    private DcMotor ArmChain;
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
        ArmChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        telemetry.addData("Winch Encoder", Winch.getCurrentPosition());
        telemetry.addData("Winch System count", count);
        telemetry.addData("WinchArm Encoder", ArmChain.getCurrentPosition());
        telemetry.addData("ArmPivot Encoder", ArmPivot.getCurrentPosition());

        count = 0;

        /**Latching System*/

        /*while(gamepad1.dpad_up/*&& count < 1){
            Arm.setPower(.1);
            Winch.setPower(1);

            count++;
        }

        if(!gamepad1.dpad_up){
            Arm.setPower(0);
            Winch.setPower(0);
        }

        while(gamepad1.dpad_down/*&& count < 1){
            Arm.setPower(-.1);
            Winch.setPower(-1);

            count++;
        }

        if (!gamepad1.dpad_down) {
            Arm.setPower(0);
            Winch.setPower(0);
        }

        while (gamepad1.dpad_left /*&& count < 1){
            Winch.setPower(-1);

            count++;
        }

        if (!gamepad1.dpad_left) {
            Winch.setPower(0);
        }

        while (gamepad1.dpad_right/*&& count < 1){
            Winch.setPower(1);

            count++;
        }

        if (!gamepad1.dpad_right) {
            Winch.setPower(0);
        }*/


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

        /*if (!gamepad1.x) {
            ArmServo.setPosition(1);

        }if (!gamepad1.y) {
            ArmServo.setPosition(0);
        }*/

        /*if (!gamepad1.b) { // extends arm to gather minerals from folded position
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
            ArmServo.setPosition(1);
        }else{
            ArmChain.setPower(-.7);
            ArmPivot.setPower(-.85);
            ArmServo.setPosition(0);
        }*/

        /*if (!gamepad1.a) { // folds the chain arm back in
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
            ArmServo.setPosition(0);
        }else{
            ArmChain.setPower(.6);
            ArmPivot.setPower(1);
            ArmServo.setPosition(1);
        }*/

        /*while(gamepad1.a){
            ArmPivot.setTargetPosition(-100);
            ArmChain.setPower(.05);
            ArmPivot.setPower(1);
            ArmServo.setPosition(1);
            while(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition()){

            }
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
            ArmServo.setPosition(0);
        }

        while(gamepad1.b){
            ArmPivot.setTargetPosition(-1918);
            ArmChain.setPower(-.7);
            ArmPivot.setPower(-.85);
            ArmServo.setPosition(0);
            while(ArmPivot.getCurrentPosition() > ArmPivot.getTargetPosition()){

            }
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
            ArmServo.setPosition(0);
        }*/
        /*while() {
            WinchArm.setPower(.6);
            ArmPivot.setPower(.8);
            ArmServo.setPosition(1);
            if(!gamepad1.a){
                break;
            }
        }*/
        /*if (!gamepad1.right_bumper) { // extends arm to gather minerals from folded position
            ArmPivot.setPower(0);
        }else{
            ArmPivot.setPower(-1);
        }

        if (gamepad1.right_trigger > .8) { //moves to put up the minerals in the lander
            ArmPivot.setPower(1);
        }else{
            ArmPivot.setPower(0);
        }

        if (gamepad1.left_trigger > .8) {
            ArmChain.setPower(.6);
        }else{
            ArmChain.setPower(0);
        }*/

        if(gamepad1.right_trigger > .8){
            ArmPivot.setPower(1);
        }else if(gamepad1.left_trigger > .8){
            ArmChain.setPower(.6);
        }else if(gamepad1.a){
            ArmPivot.setTargetPosition(-100);
            if(ArmPivot.getCurrentPosition() < ArmPivot.getTargetPosition()) {
                ArmChain.setPower(.2);
                ArmPivot.setPower(.8);
                ArmServo.setPosition(1);
            }
        }else if(gamepad1.b){
            ArmPivot.setTargetPosition(-1918);
            if(ArmPivot.getCurrentPosition() > ArmPivot.getTargetPosition()) {
                ArmChain.setPower(-.2);
                ArmPivot.setPower(-.65);
                ArmServo.setPosition(0);
            }
        }else if(gamepad1.right_bumper){
            ArmPivot.setPower(-1);
        }else{
            ArmChain.setPower(0);
            ArmPivot.setPower(0);
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
