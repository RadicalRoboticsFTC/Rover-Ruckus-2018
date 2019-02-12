package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@TeleOp(name="Driver Control Advanced", group="Driver Control")
@Disabled
public class DriverControlAdvanced extends OpMode { // this is where we start the function
    private DcMotor FrontRight; // this is where we define the variables
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    /*private DcMotor leftLauncher;
    private DcMotor rightLauncher;*/
    private static Servo LeftMarker;
    /*private static CRServo Intake;
    private static CRServo Transport;*/
    private static DistanceSensor Dsense;
    private ColorSensor colorSensor;

    double fr; // defining variables part 2
    double fl;
    double br;
    double bl;
    double cl;
    double power;

    private static int LLPosition;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Afnkb5f/////AAABmb1XgEVr1EQGsetwYZoS+QaEBPHQo9TktUc5pi0vCbPLQ/gjC3zvMXbGlqlvU7sPRxW8LA0x/1mlOkSwTMES0IxHqKA0myhnTALjbfuVVQOjcknwtwdo7B6KgKIRt/EIsVRUcE8gdsJlpl+CB1oWejQT/67qMpZhyR/nPlqyMklcGrR4IGfmaPTO3DVACenXmOnaSK+EWUEG3uPnOPV9O88JoTnP46ZbBKeLIw6E6Zr+f7DJby1w8g10f04a2TBt5WL9Ya3/6X1eWScJwh08uTWWIbnH+ny+ckkp2PY4Ss+Kbel7x3TadkgiX75+bypUwHP+fN7Na1qQMXkAvgyhGOKQm8ONW3iK4eRh6F/cW06+";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void init() {
        FrontRight = (DcMotor) hardwareMap.get("FrontRight"); // this is where we assign parts to variables
        FrontLeft = (DcMotor) hardwareMap.get("FrontLeft");
        BackRight = (DcMotor) hardwareMap.get("BackRight");
        BackLeft = (DcMotor) hardwareMap.get("BackLeft");
        Arm = (DcMotor) hardwareMap.get("Arm");
        Winch = (DcMotor) hardwareMap.get("Winch");
        LeftMarker = (Servo) hardwareMap.get("LeftMarker");
        Dsense = (DistanceSensor) hardwareMap.get("Dsense");
        colorSensor = (ColorSensor) hardwareMap.get("Pha");
        /*Intake = (CRServo) hardwareMap.get("Intake");
        Transport = (CRServo) hardwareMap.get("Transport");*/
        /*leftLauncher = (DcMotor) hardwareMap.get("leftLauncher");
        rightLauncher = (DcMotor) hardwareMap.get("rightLauncher");*/

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

    }

    @Override
    public void init_loop() { // a bunch of logic when the code should execute

    }

    @Override
    public void start() { // a bunch of logic when the code should execute
    }


    @Override
    public void loop() { // a bunch of logic when the code should execute
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

        if (!gamepad1.dpad_down) { // checks if where motor is where it needs to be
            Arm.setPower(0); // sets power the motor should turn
            Winch.setPower(0);
        } else {
            Arm.setPower(-.3); // sets power the motor should turn
            Winch.setPower(-1);
        }
        if (gamepad1.dpad_left) {
            Winch.setPower(-1);
        } else {
            Winch.setPower(0);
        }

        /*while (gamepad1.x) { // links controllers to commands the code should execute
            //sense gold mineral
            //collect gold mineral
            //transport mineral to launcher while collecting other mineral

            while(colorSensor.blue() <= (colorSensor.red())){//collects first gold mineral
                Intake.setPower(1.0);
                Transport.setPower(1.0);
            }
            Intake.setPower(0);

            FrontLeft.setPower(-.3);
            FrontRight.setPower(.3);
            BackLeft.setPower(.3);
            BackRight.setPower(-.3);

            while(colorSensor.blue() > colorSensor.red()){// moves while it can't see gold

            }
            Intake.setPower(1.0);
        }

        Intake.setPower(0);
        Transport.setPower(0);

        while (gamepad1.x) { // links controllers to commands the code should execute
            //sense silver mineral
            //collect silver mineral
            //transport mineral to launcher while collecting other mineral

            while(colorSensor.blue() <= (colorSensor.red() - 25)){//collects first silver mineral
                Intake.setPower(1.0);
                Transport.setPower(1.0);
            }
            Intake.setPower(0);

            FrontLeft.setPower(-.3);
            FrontRight.setPower(.3);
            BackLeft.setPower(.3);
            BackRight.setPower(-.3);

            while(colorSensor.blue() < colorSensor.red()){// moves while it can't see silver

            }
            Intake.setPower(1.0);
        }

        Intake.setPower(0);
        Transport.setPower(0);

        while (gamepad1.a) { // links controllers to commands the code should execute
            leftLauncher.setPower(1.0);
            rightLauncher.setPower(1.0);

            LLPosition = leftLauncher.getCurrentPosition() + 100;

            while(leftLauncher.getCurrentPosition() < LLPosition){

            }

            Transport.setPower(1.0);
        }

        if (gamepad1.y) { // links controllers to commands the code should execute
            Intake.setPower(1.0);
            Transport.setPower(1.0);
        }*/

        if (gamepad1.right_bumper) { // links controllers to commands the code should execute
        } else {
        }

        if (gamepad1.left_bumper) { // links controllers to commands the code should execute
            power = .25;
        } else {
            power = 1;
        }

        double gamepad1LeftY = gamepad1.left_stick_y * power; // assigns variables to inputs
        double gamepad1LeftX = gamepad1.left_stick_x * power;
        double gamepad1RightX = gamepad1.right_stick_x * power;

        double fl = gamepad1LeftY + gamepad1LeftX + gamepad1RightX; //controller logic nightmare scary
        double fr = gamepad1LeftY - gamepad1LeftX - gamepad1RightX; //back left
        double br = gamepad1LeftY - gamepad1LeftX + gamepad1RightX; //back right
        double bl = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        FrontRight.setPower(fl); // assigns power when inputs are triggered
        FrontLeft.setPower(fr);
        BackRight.setPower(br);
        BackLeft.setPower(bl);

        telemetry.addData("Distance Sensor", Dsense);
        telemetry.addData("Color Senser Data: ", colorSensor);





        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        if (tfod != null) {
            tfod.activate();
        }



    }


    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
