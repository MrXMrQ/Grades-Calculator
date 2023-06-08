import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class RoundedTextField extends JTextField {
    private final Color backgroundColor;
    private final Color borderColor;
    private final Color shadowColor;

    public RoundedTextField(int WIDTH, int HEIGHT) {
        setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFont(new Font("inter", Font.PLAIN, 20));

        backgroundColor = new Color(240, 240, 240);
        borderColor = new Color(200, 200, 200);
        shadowColor = new Color(0, 0, 0, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw background
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, width - 1, height - 1, 20, 20);

        // Draw shadow
        g2d.setColor(shadowColor);
        g2d.drawRoundRect(3, 3, width - 7, height - 7, 20, 20);

        g2d.dispose();

        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        int top = 10;
        int left = 10;
        int bottom = 10;
        int right = 10;
        return new Insets(top, left, bottom, right);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to prevent painting the default border
    }
}
