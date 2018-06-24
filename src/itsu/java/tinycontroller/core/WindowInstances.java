package itsu.java.tinycontroller.core;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    public static JFrame buildOptionWindow() {
        Map<String, Integer> data = Utils.readConfig();
        int horizontal = data.get("HorizontalSensitivity");
        int vertical = data.get("VerticalSensitivity");
        int distance = data.get("Distance");

        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel end = new JLabel("TinyControllerを終了する");
        end.setBounds(10, 10, 200, 20);
        end.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(end);

        JLabel horSen = new JLabel("縦感度");
        horSen.setBounds(10, 40, 200, 20);
        horSen.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(horSen);

        JLabel verSen = new JLabel("横感度");
        verSen.setBounds(10, 70, 200, 20);
        verSen.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(verSen);

        JLabel dis = new JLabel("自分からWiiリモコンとの距離");
        dis.setBounds(10, 100, 200, 20);
        dis.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(dis);

        JButton exit = new JButton("終了");
        exit.setBounds(260, 10, 225, 20);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exit);

        JLabel horSenSliderVal = new JLabel();
        horSenSliderVal.setBounds(225, 40, 40, 20);
        horSenSliderVal.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(horSenSliderVal);

        JSlider horSenSlider = new JSlider(5, 30, horizontal);
        horSenSlider.setBounds(257, 40, 232, 20);
        horSenSliderVal.setText("" + horSenSlider.getValue());
        horSenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                horSenSliderVal.setText("" + horSenSlider.getValue());
            }
        });
        panel.add(horSenSlider);

        JLabel verSenSliderVal = new JLabel();
        verSenSliderVal.setBounds(225, 70, 40, 20);
        verSenSliderVal.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(verSenSliderVal);

        JSlider verSenSlider = new JSlider(5, 30, vertical);
        verSenSlider.setBounds(257, 70, 232, 20);
        verSenSliderVal.setText("" + verSenSlider.getValue());
        verSenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                verSenSliderVal.setText("" + verSenSlider.getValue());
            }
        });
        panel.add(verSenSlider);

        JLabel disSliderVal = new JLabel();
        disSliderVal.setBounds(208, 100, 40, 20);
        disSliderVal.setFont(new Font("メイリオ", Font.PLAIN, 12));
        panel.add(disSliderVal);

        JSlider disSlider = new JSlider(10, 100, distance);
        disSlider.setBounds(257, 100, 232 , 20);
        disSliderVal.setText(disSlider.getValue() + "cm");
        disSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                disSliderVal.setText(disSlider.getValue() + "cm");
            }
        });
        panel.add(disSlider);

        JButton save = new JButton("保存");
        save.setBounds(10, 130, 230, 20);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.writeConfig(horSenSlider.getValue(), verSenSlider.getValue(), disSlider.getValue());
                    BaseData.horizontalSensitivity = horSenSlider.getValue();
                    BaseData.verticalSensitivity = verSenSlider.getValue();
                    BaseData.distance = disSlider.getValue();

                    frame.setVisible(false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(save);

        JButton cancel = new JButton("キャンセル");
        cancel.setBounds(260, 130, 225, 20);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        panel.add(cancel);

        frame.setTitle("TinyController");
        frame.setAlwaysOnTop(true);
        frame.setBounds(0, 0, 500, 190);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        return frame;
    }

}
