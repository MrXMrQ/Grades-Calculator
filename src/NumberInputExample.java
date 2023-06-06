import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class NumberInputExample {
    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<>();
        test.add("index 0");
        test.add("index 1");
        test.add("index 2");
        test.add("index 3");
        test.add("index 4");
        test.add("index 5");

        System.out.println(test.get(0));

        test.remove(0);

        System.out.println(test.get(0));
    }
}
