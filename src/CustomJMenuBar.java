import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class CustomJMenuBar extends JMenuBar {
    private final Color startColor;
    private final Color endColor;

    public CustomJMenuBar(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;

        JMenu fileMenu = new JMenu("info");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Inter", Font.BOLD, 15));

        fileMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fileMenu.setForeground(new Color(33, 215, 33));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fileMenu.setForeground(Color.WHITE);
            }
        });

        JMenuItem menuItemSettings = new JMenuItem("Settings");
        JMenuItem menuItemAbout = new JMenuItem("About");
        JMenuItem menuItemExit = new JMenuItem("Exit");

        menuItemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomMessage(CustomJMenuBar.this, "Enter  = confirm grade\nConfirm = average grade\nLK = confirm subject as LK", "Settings", "Ok");

            }
        });

        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomMessage(CustomJMenuBar.this, "Enter  = confirm grade\nConfirm = average grade\nLK = confirm subject as LK", "Settings", "Ok");
            }
        });

        menuItemExit.addActionListener(e -> System.exit(0));

        fileMenu.add(menuItemSettings);
        fileMenu.add(menuItemAbout);
        fileMenu.addSeparator();
        fileMenu.add(menuItemExit);

        add(fileMenu);
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
