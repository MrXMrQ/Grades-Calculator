import CustomGUI.CustomButton;
import CustomGUI.CustomMessage;
import CustomGUI.CustomTextField;
import Objects.Grade;
import Objects.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator {
    private final MyFrame myFrame;
    private final JPanel panelNORTH;
    private final JPanel panelCENTER;
    private JLabel labelTable;

    private int lkCount = 2;
    private String text = "";

    private ArrayList<String> subjects = new ArrayList<>();
    private final ArrayList<Grade> grades = new ArrayList<>();
    private final Font inter = new Font("Inter", Font.PLAIN, 25);
    private boolean lk = false;
    private JComboBox<String> comboBox;

    Calculator(MyFrame myFrame) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());

        JPanel backgroundPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), 0, getHeight(), new Color(100, 100, 0)));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };

        layeredPane.add(backgroundPanel, BorderLayout.CENTER, 2);

        this.myFrame = myFrame;
        subjects = addSubjects(subjects);

        panelNORTH = addComponentsNORTH(new JPanel());
        panelCENTER = addComponentsCENTER(new JPanel());

        layeredPane.add(panelNORTH, BorderLayout.NORTH, 1);
        layeredPane.add(panelCENTER, BorderLayout.CENTER, 1);

        myFrame.add(layeredPane);

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

        CustomButton checkBox = new CustomButton("LK", new Color(142, 68, 173), new Color(155, 89, 182), Color.WHITE, new Color(104, 35, 128), 85, 50);
        checkBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lk) {
                    checkBox.setColor(new Color(33, 215, 33), new Color(33, 215, 33));
                    lk = true;
                } else {
                    checkBox.setColor(new Color(142, 68, 173), new Color(155, 89, 182));
                    lk = false;
                }
                myFrame.repaint();
            }
        });

        flowPanel.add(comboBox);
        flowPanel.add(checkBox);

        panel.add(flowPanel);

        return panel;
    }

    public JPanel addComponentsCENTER(JPanel panel) {
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setPreferredSize(new Dimension(300, 500));

        JLabel textGradePoint = new JLabel("Notenpunkt:", SwingConstants.LEFT);
        textGradePoint.setPreferredSize(new Dimension(230, 50));
        textGradePoint.setFont(new Font("inter", Font.BOLD, 25));

        CustomTextField textField = new CustomTextField(285, 50);

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
                new CustomMessage(myFrame, "Das Feld darf nicht leer sein!", "Error", "OK");
            } else {
                try {
                    if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 15) {
                        if (comboBox.getItemCount() == 0) {
                            new CustomMessage(myFrame, "Alle Fächer vergeben!", "Error", "OK");
                            textField.setText("");
                        } else {
                            if (lk && lkCount >= 1) {
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                setText();
                                removeContent();
                                lkCount--;
                            } else if (lk) {
                                new CustomMessage(myFrame, "Es wurden bereits 2 LK´s festgelegt!", "Error", "OK");
                            } else {
                                grades.add(new Grade(Integer.parseInt(input), lk, (String) comboBox.getSelectedItem()));
                                setText();
                                removeContent();
                            }
                            textField.setText("");

                        }
                    } else {
                        textField.setText("");
                        new CustomMessage(myFrame, "Der Notenpunkt muss zwischen 1 und 15 liegen", "Error", "OK");
                    }
                } catch (NumberFormatException ex) {
                    textField.setText("");
                    new CustomMessage(myFrame, "Der Notenpunkt muss zwischen 1 und 15 liegen", "Error", "OK");
                }
            }
        });

        CustomButton submitButton = new CustomButton("submit", new Color(142, 68, 173), new Color(155, 89, 182), Color.WHITE, new Color(104, 35, 128), 285, 50);
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
                    if (lkCount == 2) {
                        new CustomMessage(myFrame, "Wähle noch " + lkCount + " LK´s!", "Error", "OK");
                    } else {
                        new CustomMessage(myFrame, "Wähle noch " + lkCount + " LK!", "Error", "OK");
                    }
                }
            }
        });

        labelTable = new JLabel();
        labelTable.setFont(new Font("Inter", Font.PLAIN, 18));

        flowPanel.add(textGradePoint);
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

        sum = sum / grades.size();
        sum = (17 - sum) / 3;

        DecimalFormat df = new DecimalFormat("#.#");
        String formattedSum = df.format(sum);


        JLabel labelHeadline = new JLabel("Notendurchschnitt: " + formattedSum);
        labelHeadline.setFont(new Font("inter", Font.BOLD, 26));

        panelNORTH.removeAll();
        panelNORTH.add(labelHeadline);


        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setPreferredSize(new Dimension(300, 500));

        CustomButton returnButton = new CustomButton("return", new Color(142, 68, 173), new Color(155, 89, 182), Color.WHITE, new Color(104, 35, 128), 285, 50);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(panelNORTH);
                myFrame.remove(panelCENTER);
                new Calculator(myFrame);
            }
        });
        flowPanel.add(labelTable);
        flowPanel.add(returnButton);

        panelCENTER.add(flowPanel);


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

    private void removeContent() {
        String subject = (String) comboBox.getSelectedItem();
        comboBox.removeItem(subject);
    }
}