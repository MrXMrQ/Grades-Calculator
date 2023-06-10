import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        myFrame.setJMenuBar(new CustomJMenuBar(new Color(142, 68, 173), new Color(155, 89, 182), myFrame));
        new Calculator(myFrame);
    }
}