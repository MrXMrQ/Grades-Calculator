import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomMessage extends JOptionPane {
    public CustomMessage(Component destination, String text, String title, String buttonText) {
        CustomButton customButton = new CustomButton(buttonText, new Color(142, 68, 173), new Color(155, 89, 182), Color.WHITE, new Color(104, 35, 128), 285, 50);


        Object[] options = {customButton};
        JOptionPane.showOptionDialog(destination, text, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
