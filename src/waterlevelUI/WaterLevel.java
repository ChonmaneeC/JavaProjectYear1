package waterlevelUI;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import waterlevelLogic.Tank;
import waterlevelLogic.BackGround;

public class WaterLevel extends JFrame implements ActionListener, ItemListener, KeyListener {
    JPanel waterTank;
    JPanel information;
    JPanel tankPanel;
    JPanel colorWater;
    JPanel rePanel;
    JLabel netCapacity;
    JLabel addAmount;
    JTextField amount;
    JLabel timeLabel;
    Timer timer1000;
    Timer timer100;
    Tank tank;
    JRadioButton blueWater;
    JRadioButton redWater;
    JButton result;
    JButton reset;
    int addVolume;

    public WaterLevel() {
        setTitle("Water level of the water tank");
        setSize(1200, 950);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        timeLabel = new JLabel("Time : ");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        timer1000 = new Timer(1000, new ActionListener() {
            int time = 0;

            public void actionPerformed(ActionEvent e) {
                time++;
                timeLabel.setText("Time : " + (time < 10 ? "0" : "") + time + "s");
            }
        });

        timer100 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tank.getVolume() < 100) {
                    double newVolume = tank.getVolume() + addVolume * 100.0 / Math.pow(10, 3);
                    if (newVolume > 100) {
                        newVolume = 100;
                    }
                    tank.setVolume(newVolume);
                } else {
                    timer1000.stop();
                }
                tank.repaint();
            }
        });

        waterTank = new JPanel();
        netCapacity = new JLabel("Net capacity of the water tank : 100 L.");
        add(waterTank, BorderLayout.WEST);
        waterTank.addKeyListener(this);

        information = new JPanel();
        information.setLayout(new GridLayout(3, 1));
        addAmount = new JLabel("Add water (L./s.) : ");
        addAmount.setFont(new Font("Arial", Font.PLAIN, 18));
        amount = new JTextField(20);
        amount.addActionListener(this);

        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subPanel.add(addAmount, BorderLayout.WEST);
        subPanel.add(amount);
        information.add(netCapacity);
        information.add(subPanel);
        information.add(timeLabel);
        add(information, BorderLayout.NORTH);

        tankPanel = new BackGround();
        tank = new Tank();
        tankPanel.add(tank);
        add(tankPanel, BorderLayout.CENTER);

        colorWater = new JPanel();
        colorWater.setLayout(new GridLayout(2, 1));
        blueWater = new JRadioButton("Blue");
        colorWater.add(blueWater);
        redWater = new JRadioButton("Red");
        colorWater.add(redWater);
        add(colorWater, BorderLayout.EAST);

        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(blueWater);
        colorGroup.add(redWater);
        blueWater.addItemListener(this);
        redWater.addItemListener(this);

        rePanel = new JPanel();
        result = new JButton("Result");
        result.setSize(100, 100);
        result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addVolume = Integer.parseInt(amount.getText());
                    if (addVolume >= 0 && addVolume <= 100) {
                        setWaterColor();
                        timer100.start();
                    } else {
                        JOptionPane.showMessageDialog(WaterLevel.this, "Volume must be between 0 and 100");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(WaterLevel.this, "Invalid volume");
                }
                timer1000.start();
            }
        });

        reset = new JButton("Reset");
        reset.setSize(100, 100);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("Time : ");
                timer1000.stop();
                timer100.stop();
                tank.setVolume(0);
                tank.repaint();
                timer1000.restart();
            }
        });

        rePanel.setLayout(new BorderLayout());
        rePanel.add(result, BorderLayout.WEST);
        rePanel.add(reset, BorderLayout.EAST);
        add(rePanel, BorderLayout.SOUTH);

        // Ensure the amount JTextField gains focus when the frame is initialized
        amount.requestFocusInWindow();
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == blueWater) {
            tank.setWaterColor(Color.BLUE);
        } else if (e.getSource() == redWater) {
            tank.setWaterColor(Color.RED);
        }
    }

    public void actionPerformed(ActionEvent e) {
        waterTank.grabFocus();
    }

    public void keyTyped(KeyEvent e) {
        try {
            addVolume = Integer.parseInt(amount.getText());
            if (addVolume >= 0 && addVolume <= 100) {
                setWaterColor();
                timer100.start();
            } else {
                JOptionPane.showMessageDialog(WaterLevel.this, "Volume must be between 0 and 100");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(WaterLevel.this, "Invalid volume");
        }
        timer1000.start();
    }

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    private void setWaterColor() {
        if (blueWater.isSelected()) {
            tank.setWaterColor(Color.BLUE);
        } else if (redWater.isSelected()) {
            tank.setWaterColor(Color.RED);
        } else {
            tank.setWaterColor(Color.BLACK);
        }
    }
}
