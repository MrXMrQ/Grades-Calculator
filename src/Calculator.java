import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator {
    MyFrame myFrame;
    JPanel panelNORTH;
    JPanel panelCENTER;
    JLabel labelTable;

    int lkCount = 2;
    String text = "";

    private ArrayList<String> subjects = new ArrayList<>();
    private final ArrayList<Grade> grades = new ArrayList<>();
    private final Font inter = new Font("Inter", Font.PLAIN, 25);
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
        comboBox.setBorder(new LineBorder(Color.BLACK, 1));

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

        RoundedTextField textField = new RoundedTextField(285,50);

        /*JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 50));
        textField.setFont(inter);*/

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
                        if (comboBox.getItemCount() == 0) {
                            JOptionPane.showMessageDialog(myFrame, "Alle Fächer wurden vergeben!");
                            textField.setText("");
                        } else {
                            if (lk && lkCount >= 1) {
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                setText();
                                lkCount--;
                            } else if (lk) {
                                JOptionPane.showMessageDialog(myFrame, "Es wurden bereits zwei LK´s festgelegt!");
                            } else {
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                setText();
                            }
                            textField.setText("");
                            String subject = (String) comboBox.getSelectedItem();
                            comboBox.removeItem(subject);
                        }
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

        RoundedGradientButton submitButton = new RoundedGradientButton("submit", new Color(142, 68, 173), new Color(155, 89, 182), Color.WHITE, 285, 50);
        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lkCount < 1) {
                    myFrame.remove(panelNORTH);
                    myFrame.remove(panelCENTER);
                    panelCENTER.removeAll();
                    SwingUtilities.updateComponentTreeUI(myFrame);
                    calculator();
                } else {
                    JOptionPane.showMessageDialog(myFrame, "Wähle noch " + lkCount + " LK/LK´s!");
                }
            }
        });

        labelTable = new JLabel();
        labelTable.setFont(new Font("Inter", Font.PLAIN, 18));

        flowPanel.add(label);
        flowPanel.add(textField);
        flowPanel.add(submitButton, Component.CENTER_ALIGNMENT);
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

        sum = sum / grades.size();
        sum = (17 - sum) / 3;

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedSum = df.format(sum);

        JLabel label = new JLabel("Notendurchschnitt: " + formattedSum);
        label.setFont(inter);

        panelNORTH.add(label);
        panelCENTER.add(labelTable);
        myFrame.add(panelNORTH, BorderLayout.NORTH);
        myFrame.add(panelCENTER, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(myFrame);
    }

    private void setText() {
        String LK;
        if (grades.get(grades.size() - 1).isLk()) {
            LK = "LK";
        } else {
            LK = "";
        }

        text += "<html><br>" + grades.get(grades.size() - 1).getSubject() + ": " + grades.get(grades.size() - 1).getGrade() + " " + LK + "<html>";
        labelTable.setText(text);
    }
}