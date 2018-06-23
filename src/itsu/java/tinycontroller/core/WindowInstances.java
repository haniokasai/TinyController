package itsu.java.tinycontroller.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WindowInstances {
	
	private static JFrame frame = new JFrame();
	private static JLabel label = new JLabel();
	
	public static JFrame buildConnectWindow() {	
		try {
			JPanel panel = new JPanel();
			panel.setLayout(null);
			
			JLabel image = new JLabel(new ImageIcon(ImageIO.read(WindowInstances.class.getClassLoader().getResourceAsStream("tinycontroller.jpg"))));
			image.setBounds(0, 0, 753, 392);
			panel.add(image);
			
			label.setBounds(0, 392, 753, 20);
			label.setText("Wiiリモコンを探しています...  +と-を同時押ししてホームを押すことで終了できます。");
			label.setFont(new Font("メイリオ", Font.PLAIN, 12));
			panel.add(label);
			
			frame.setTitle("TinyController");
			frame.setAlwaysOnTop(true);
			frame.setBounds(0, 0, 753, 443);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setLayout(new BorderLayout());
			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return frame;
	}
	
	public static void update(String text) {
		try {
			label.setText(text);
			Thread.sleep(2000);
			frame.setVisible(false);
			frame = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
