package itsu.java.tinycontroller.core;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wiiusej.values.GForce;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class Listener implements WiimoteListener {

    private Robot robot;

    private boolean isAPressing = false;
    private boolean baseSetting = false;
    private boolean isDownPressing = false;

    public Listener() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonsEvent(WiimoteButtonsEvent arg0) {
        if(arg0.isButtonHomeJustPressed()) {
            robot.keyPress(KeyEvent.VK_ESCAPE);
            if(baseSetting) {
                switch(JOptionPane.showConfirmDialog(new JFrame(), "TinyControllerを終了しますか？")) {
                    case JOptionPane.CANCEL_OPTION:
                    case JOptionPane.NO_OPTION:
                        break;

                    case JOptionPane.OK_OPTION:
                        System.exit(0);
                        break;
                }
            }

        } else if(arg0.isButtonHomeJustReleased()) {
            robot.keyRelease(KeyEvent.VK_ESCAPE);

        } else if(arg0.isButtonPlusJustPressed()) {
            if(isDownPressing) {
                robot.mouseWheel(1);
            } else {
                 robot.keyPress(KeyEvent.VK_T);
                 isAPressing = true;
            }

        } else if(arg0.isButtonPlusJustReleased()) {
            robot.keyRelease(KeyEvent.VK_T);
            isAPressing = false;

        } else if(arg0.isButtonMinusJustPressed()) {
            if(isDownPressing) {
                robot.mouseWheel(-1);
            } else {
                robot.keyPress(KeyEvent.VK_E);
                if(isAPressing) {
                    baseSetting = true;
                    Toolkit.getDefaultToolkit().beep();
                }
            }

        } else if(arg0.isButtonMinusJustReleased()) {
            robot.keyRelease(KeyEvent.VK_E);

        } else if(arg0.isButtonOneJustPressed()) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);

        } else if(arg0.isButtonOneJustReleased()) {
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

        } else if(arg0.isButtonTwoJustPressed()) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        } else if(arg0.isButtonTwoJustReleased()) {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } else if(arg0.isButtonAJustPressed()) {
            robot.keyPress(KeyEvent.VK_SHIFT);
            baseSetting = false;

        } else if(arg0.isButtonAJustReleased()) {
            robot.keyRelease(KeyEvent.VK_SHIFT);

        } else if(arg0.isButtonBJustPressed()) {
            robot.keyPress(KeyEvent.VK_SPACE);

        } else if(arg0.isButtonBJustReleased()) {
            robot.keyRelease(KeyEvent.VK_SPACE);

        } else if(arg0.isButtonRightJustPressed()) {
            robot.keyPress(KeyEvent.VK_W);

        } else if(arg0.isButtonRightJustReleased()) {
            robot.keyRelease(KeyEvent.VK_W);

        } else if(arg0.isButtonLeftJustPressed()) {
            robot.keyPress(KeyEvent.VK_S);

        } else if(arg0.isButtonLeftJustReleased()) {
            robot.keyRelease(KeyEvent.VK_S);

        } else if(arg0.isButtonUpJustPressed()) {
            robot.keyPress(KeyEvent.VK_A);

        } else if(arg0.isButtonUpJustReleased()) {
            robot.keyRelease(KeyEvent.VK_A);

        } else if(arg0.isButtonDownJustPressed()) {
            robot.keyPress(KeyEvent.VK_D);
            isDownPressing = true;

        } else if(arg0.isButtonDownJustReleased()) {
            robot.keyRelease(KeyEvent.VK_D);
            isDownPressing = false;

        }
    }

    @Override
    public void onStatusEvent(StatusEvent arg0) {

    }

    @Override public void onMotionSensingEvent(MotionSensingEvent arg0) {
        GForce force = arg0.getGforce();
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        if(baseSetting) {
            //BaseData.baseXCita = (int) Math.round(Math.atan(force.getX() / Math.sqrt(Math.pow(force.getY(), 2) + Math.pow(force.getZ(), 2))) * 180 / 3.14);
            BaseData.baseYCita = (int) Math.round(Math.atan(force.getY() / Math.sqrt(Math.pow(force.getX(), 2) + Math.pow(force.getZ(), 2))) * 180 / 3.14);
            BaseData.baseZCita = (int) Math.round(Math.atan(force.getZ() / Math.sqrt(Math.pow(force.getX(), 2) + Math.pow(force.getY(), 2))) * 180 / 3.14);
        }

        //int citaX = (int) Math.round(Math.atan(force.getX() / Math.sqrt(Math.pow(force.getY(), 2) + Math.pow(force.getZ(), 2))) * 180 / 3.14) - BaseData.baseXCita;
        int citaY = (int) Math.round(Math.atan(force.getY() / Math.sqrt(Math.pow(force.getX(), 2) + Math.pow(force.getZ(), 2))) * 180 / 3.14) - BaseData.baseYCita;
        int citaZ = (int) Math.round(Math.atan(force.getZ() / Math.sqrt(Math.pow(force.getX(), 2) + Math.pow(force.getY(), 2))) * 180 / 3.14) - BaseData.baseZCita;

        if(!baseSetting) robot.mouseMove(mouseX - (int) (Math.PI * 6 * citaY / 360) * 10, mouseY + (int) (Math.PI * 6 * citaZ / 360) * 10);

    }

    @Override  public void onDisconnectionEvent(DisconnectionEvent arg0) {}
    @Override public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {}
    @Override public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {}
    @Override public void onExpansionEvent(ExpansionEvent arg0) {}
    @Override public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {}
    @Override public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {}
    @Override public void onIrEvent(IREvent arg0) {}
    @Override public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {}
    @Override public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {}

}
