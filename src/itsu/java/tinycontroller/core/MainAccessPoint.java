package itsu.java.tinycontroller.core;

import java.awt.Toolkit;
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
			
			api = new WiiUseApiManager();
			
			System.out.println("Finding wiimotes...");
			
			WindowInstances.buildConnectWindow().setVisible(true);
			
			while(wiimotes == null) {
				wiimotes = api.getWiimotes(2);
				if(wiimotes != null && wiimotes.length > 0) {
					wiimote = wiimotes[0];
					Toolkit.getDefaultToolkit().beep();
					WindowInstances.update("Wiiリモコンが見つかりました。\nID: " + wiimote.getId());
					System.out.println("Id: " + wiimote.getId());
					break;
				}
				wiimotes = null;
				continue;
			}
			
			wiimote.setLeds(true, false, false, false);
			
			wiimote.activateMotionSensing();
			wiimote.addWiiMoteEventListeners(new Listener());
			
		} catch (WiiusejNativeLibraryLoadingException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		MainAccessPoint main = new MainAccessPoint();
	}
}