package itsu.java.tinycontroller.core;

import java.awt.Toolkit;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.awvalenti.wiiusej.WiiusejNativeLibraryLoadingException;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class MainAccessPoint {

    private WiiUseApiManager api;
    private Wiimote wiimote;
    private Wiimote[] wiimotes;

    public MainAccessPoint() throws InterruptedException {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Utils.makeConfig();
            WindowInstances.buildConnectWindow().setVisible(true);

            api = new WiiUseApiManager();

            while (wiimotes == null) {
                wiimotes = api.getWiimotes(2);
                if (wiimotes != null && wiimotes.length > 0) {
                    wiimote = wiimotes[0];
                    Toolkit.getDefaultToolkit().beep();
                    WindowInstances.update("Wiiリモコンが見つかりました。\nID: " + wiimote.getId());
                    break;
                }
                wiimotes = null;
                continue;
            }

            Map<String, Integer> data = Utils.readConfig();
            BaseData.horizontalSensitivity = data.get("HorizontalSensitivity");
            BaseData.verticalSensitivity = data.get("VerticalSensitivity");
            BaseData.distance = data.get("Distance");

            wiimote.setLeds(true, false, false, false);

            wiimote.activateMotionSensing();
            wiimote.addWiiMoteEventListeners(new Listener());

        } catch (WiiusejNativeLibraryLoadingException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        new MainAccessPoint();
    }
}