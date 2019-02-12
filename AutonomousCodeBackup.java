/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Disabled
@Autonomous(name = "AutonomousCodeBackup", group = "Backup")
public class AutonomousCodeBackup extends LinearOpMode {
    private DcMotor FrontRight; // defines variables to be used in the code
    private DcMotor FrontLeft;
    private DcMotor BackRight;
    private DcMotor BackLeft;
    private DcMotor Arm;
    private DcMotor Winch;
    private Servo LeftMarker;
    private static String Left = "Left";
    private static String Right = "Right";
    private static String left = "left";
    private static String right = "right";

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Afnkb5f/////AAABmb1XgEVr1EQGsetwYZoS+QaEBPHQo9TktUc5pi0vCbPLQ/gjC3zvMXbGlqlvU7sPRxW8LA0x/1mlOkSwTMES0IxHqKA0myhnTALjbfuVVQOjcknwtwdo7B6KgKIRt/EIsVRUcE8gdsJlpl+CB1oWejQT/67qMpZhyR/nPlqyMklcGrR4IGfmaPTO3DVACenXmOnaSK+EWUEG3uPnOPV9O88JoTnP46ZbBKeLIw6E6Zr+f7DJby1w8g10f04a2TBt5WL9Ya3/6X1eWScJwh08uTWWIbnH+ny+ckkp2PY4Ss+Kbel7x3TadkgiX75+bypUwHP+fN7Na1qQMXkAvgyhGOKQm8ONW3iK4eRh6F/cW06+";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
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
        Arm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        LeftMarker.setPosition(.7);

        Arm.setTargetPosition(2100); //step 1: lands on field
        Arm.setPower(.1);
        Winch.setPower(1);
        while (Arm.getCurrentPosition() < Arm.getTargetPosition()) {

        }

        Arm.setPower(0);
        Winch.setPower(0);

        sleep(500);

        strafe("Right", 1050);

        sleep(500);

        straight(170);

        sleep(500);

        turn("Left", -1530);

        straight(4400);

        LeftMarker.setPosition(1);

        backwards(-100);

        turn("Left", -1800);

        straight(1760);

        sleep(500);

        turn("Left", -260);

        sleep(500);

        straight(3200);
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

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

    private void faketurn(String direction, int TargetPosition){

        FrontLeft.setTargetPosition(TargetPosition);

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
            FrontLeft.setPower(.5);
            FrontRight.setPower(-.5);
            BackLeft.setPower(.5);
            BackRight.setPower(-.5);
            while (FrontLeft.getCurrentPosition() < FrontLeft.getTargetPosition()) {

            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }
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
            FrontLeft.setPower(.5);
            FrontRight.setPower(-.5);
            BackLeft.setPower(.5);
            BackRight.setPower(-.5);
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
