package CustomGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class CustomJMenuBar extends JMenuBar {
    private final Color startColor;
    private final Color endColor;

    public CustomJMenuBar(Color startColor, Color endColor, Component destination) {
        this.startColor = startColor;
        this.endColor = endColor;

        JMenu infoMenu = new JMenu("Info");
        infoMenu.setForeground(Color.WHITE);
        infoMenu.setFont(new Font("Inter", Font.BOLD, 15));

        infoMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                infoMenu.setForeground(new Color(0, 255, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                infoMenu.setForeground(Color.WHITE);
            }
        });

        JMenuItem menuItemSettings = new JMenuItem("Settings");
        JMenuItem menuItemAbout = new JMenuItem("About");
        JMenuItem menuItemExit = new JMenuItem("Exit");

        menuItemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomMessage(destination, "Press \"Enter\" to confirm you grade\nClick \"Submit\" to calculate your average grade\nSelect \"LK\" to confirm the choose subject as an LK", "Setting", "OK");
            }
        });

        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomMessage(destination, "Calculate your grades with an impressive design!\nHow can you utilize it? Find out more in the Settings!\nDouble the weight for advanced courses (LKs)!", "Settings", "Ok");
            }
        });

        menuItemExit.addActionListener(e -> System.exit(0));

        infoMenu.add(menuItemSettings);
        infoMenu.add(menuItemAbout);
        infoMenu.addSeparator();
        infoMenu.add(menuItemExit);

        add(infoMenu);

        JMenu saveMenu = new JMenu("Save");
        saveMenu.setForeground(Color.WHITE);
        saveMenu.setFont(new Font("Inter", Font.BOLD, 15));

        saveMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveMenu.setForeground(new Color(0, 255, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveMenu.setForeground(Color.WHITE);
            }
        });

        add(saveMenu);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        GradientPaint gradient = new GradientPaint(
                new Point2D.Float(0, 0), startColor,
                new Point2D.Float(width, 0), endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
