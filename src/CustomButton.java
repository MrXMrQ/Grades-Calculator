import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private Color startColor;
    private Color endColor;
    private final Color textColor;
    private final Color pressedColor;
    private boolean isPressed;
    private boolean isEntered;

    public CustomButton(String label, Color startColor, Color endColor, Color textColor, Color pressedColor, int width, int height) {
        super(label);
        this.startColor = startColor;
        this.endColor = endColor;
        this.textColor = textColor;
        this.pressedColor = pressedColor;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setPreferredSize(new Dimension(width, height));
        setFont(new Font("inter", Font.BOLD, 20));

        // Add MouseListener to handle button press
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
                int shadowOffset = 3;
                setLocation(getX() + shadowOffset, getY() + shadowOffset);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
                int shadowOffset = 3;
                setLocation(getX() - shadowOffset, getY() - shadowOffset);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isEntered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isEntered = false;
                repaint();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw shadow
        int shadowOffset = 3;
        int shadowArcSize = 20;
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(shadowOffset, shadowOffset, width - shadowOffset, height - shadowOffset, shadowArcSize, shadowArcSize);

        // Draw button background
        if (isPressed) {
            g2d.setColor(pressedColor);
        } else {
            GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, width, height, endColor);
            g2d.setPaint(gradientPaint);
        }
        g2d.fillRoundRect(0, 0, width - shadowOffset, height - shadowOffset, shadowArcSize, shadowArcSize);

        // Draw text
        if (!isEntered) {
            g2d.setColor(textColor);
            g2d.setFont(getFont());
            int stringWidth = g2d.getFontMetrics().stringWidth(getText());
            int stringHeight = g2d.getFontMetrics().getHeight();
            int x = (width - stringWidth) / 2;
            int y = (height - stringHeight) / 2 + g2d.getFontMetrics().getAscent();
            g2d.drawString(getText(), x, y);
        } else {
            g2d.setColor(new Color(0, 255, 0));
            g2d.setFont(getFont());
            int stringWidth = g2d.getFontMetrics().stringWidth(getText());
            int stringHeight = g2d.getFontMetrics().getHeight();
            int x = (width - stringWidth) / 2;
            int y = (height - stringHeight) / 2 + g2d.getFontMetrics().getAscent();
            g2d.drawString(getText(), x, y);
        }

        g2d.dispose();
    }

    public void setColor(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }
}