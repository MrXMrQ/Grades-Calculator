import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Calculator {
    MyFrame myFrame;
    JPanel panelNORTH;
    JPanel panelCENTER;
    JLabel labelTable;

    int count = 2;
    int gradeCount = 0;
    String text = "";

    private ArrayList<String> subjects = new ArrayList<>();
    private final ArrayList<Grade> grades = new ArrayList<>();
    private final Font inter = new Font("Inter", Font.PLAIN, 37);
    private boolean lk = false;
    private JComboBox<String> comboBox;

    Calculator() {
        myFrame = new MyFrame();
        subjects = addSubjects(subjects);

        panelNORTH = addComponentsNORTH(new JPanel());
        panelCENTER = addComponentsCENTER(new JPanel());

        myFrame.add(panelNORTH, BorderLayout.NORTH);
        myFrame.add(panelCENTER, BorderLayout.CENTER);

        myFrame.setVisible(true);
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
        subjects.add("Betriebwirtschaftslehre");
        subjects.add("Religion");

        return subjects;
    }

    public JPanel addComponentsNORTH(JPanel panel) {
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setPreferredSize(new Dimension(300, 60));

        comboBox = new JComboBox<>();
        addSubjects();
        comboBox.setPreferredSize(new Dimension(200, 50));
        comboBox.setFont(inter);

        JCheckBox checkBox = new JCheckBox("LK");
        checkBox.setPreferredSize(new Dimension(85, 50));
        checkBox.setFont(inter);

        checkBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lk = checkBox.isSelected();
            }
        });

        flowPanel.add(comboBox);
        flowPanel.add(checkBox);

        panel.add(flowPanel);

        return panel;
    }

    public JPanel addComponentsCENTER(JPanel panel) {
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setPreferredSize(new Dimension(300, 400));

        JLabel label = new JLabel("Notenpunkt:", SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(230, 50));
        label.setFont(inter);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 50));
        textField.setFont(inter);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();
                if (!Character.isDigit(input)) e.consume();

            }
        });

        textField.addActionListener(e -> {
            String input = textField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(myFrame, "Das Feld darf nicht leer sein!");
            } else {
                try {
                    if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 15) {
                        grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                        text += "<html><br>" + grades.get(gradeCount).getSubject() + ": " + grades.get(gradeCount).getGrade() + "<html>";
                        labelTable.setText(text);
                        gradeCount++;
                        if (lk && count >= 1) {
                            grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                            count--;
                        } else if (lk) {
                            JOptionPane.showMessageDialog(myFrame, "Es wurden bereits zwei LK´s festgelegt!");
                        }
                        textField.setText("");
                    } else {
                        textField.setText("");
                        JOptionPane.showMessageDialog(myFrame, "Die Zahl muss in dem Bereich 1 - 15 liegen!");
                    }
                } catch (NumberFormatException ex) {
                    textField.setText("");
                    JOptionPane.showMessageDialog(myFrame, "Die Zahl muss in dem Bereich 1 - 15 liegen!");
                }
            }
        });

        JButton submitButton = new JButton("submit");
        submitButton.setPreferredSize(new Dimension(285, 50));
        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 1) {
                    myFrame.remove(panelNORTH);
                    myFrame.remove(panelCENTER);
                    SwingUtilities.updateComponentTreeUI(myFrame);
                    calculator();
                } else {
                    JOptionPane.showMessageDialog(myFrame, "Wähle noch " + count + " LK/LK´s!");
                }
            }
        });
        submitButton.setFont(inter);

        labelTable = new JLabel("text");
        labelTable.setPreferredSize(new Dimension(300,300));

        flowPanel.add(label);
        flowPanel.add(textField);
        flowPanel.add(submitButton,Component.CENTER_ALIGNMENT);
        flowPanel.add(labelTable, Component.TOP_ALIGNMENT);

        panel.add(flowPanel);

        return panel;
    }

    private void addSubjects() {
        for (String item : subjects) {
            comboBox.addItem(item);
        }
    }

    private void calculator() {
        double sum = 0;
        for (int i = 0; i < grades.size(); i++) {
            sum += grades.get(i).getGrade();
        }

        panelNORTH.removeAll();
        JLabel label = new JLabel("Notendurchschnitt: " + sum / grades.size());
        label.setFont(inter);

        panelNORTH.add(label);
        myFrame.add(panelNORTH, BorderLayout.NORTH);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }

    private void setText(String text) {

    }
}