import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator {
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<Grade> grades = new ArrayList<>();
    private final Font inter = new Font("Inter", Font.PLAIN, 37);
    private boolean lk = false;

    Calculator() {
        calculator();
    }

    private void calculator() {
        subjects = addSubjects(subjects);

        MyFrame myFrame = new MyFrame();

        JPanel panelNORTH = addComponentsNORTH(new JPanel());
        JPanel panelCENTER = addComponentsCENTER(new JPanel());

        myFrame.add(panelNORTH, BorderLayout.NORTH);
        myFrame.add(panelCENTER, BorderLayout.CENTER);

    }

    public ArrayList<String> addSubjects(ArrayList<String> subjects) {
        subjects.add("Mathe");
        subjects.add("Informatik");
        subjects.add("Deutsch");
        subjects.add("Englisch");
        subjects.add("Spanisch");
        subjects.add("Sport");
        subjects.add("Biologie");
        subjects.add("Gesellschaftslehre");
        subjects.add("Betreibwirtschaftslehre");
        subjects.add("Religion");

        return subjects;
    }

    public JPanel addComponentsNORTH(JPanel panel) {
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setPreferredSize(new Dimension(350, 60));

        JComboBox<String> comboBox = new JComboBox<>();
        for (String item : subjects) {
            comboBox.addItem(item);
        }
        comboBox.setPreferredSize(new Dimension(227, 50));
        comboBox.setFont(inter);

        JCheckBox checkBox = new JCheckBox("LK");
        checkBox.setPreferredSize(new Dimension(100, 50));
        checkBox.setFont(inter);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the JCheckBox is selected
                lk = checkBox.isSelected();

            }
        });

        tempPanel.add(comboBox);
        tempPanel.add(checkBox);

        panel.add(tempPanel);

        return panel;
    }

    public JPanel addComponentsCENTER(JPanel panel) {
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setPreferredSize(new Dimension(350, 60));

        JLabel label = new JLabel("Notenpunkt:", SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(227, 50));
        label.setFont(inter);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 50));
        textField.setFont(inter);

        textField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int grade = Integer.parseInt(textField.getText());
                if(grade >= 1 && grade <= 15) {
                    grades.add(new Grade(grade, lk));
                    textField.setText("");
                }
            }
        });

        tempPanel.add(label);
        tempPanel.add(textField);

        panel.add(tempPanel);

        return panel;
    }
}
