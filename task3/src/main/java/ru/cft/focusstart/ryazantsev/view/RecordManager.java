package ru.cft.focusstart.ryazantsev.view;


import ru.cft.focusstart.ryazantsev.util.GameConstant;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class RecordManager {
    private static final int MAX_RECORDS = 10;
    private JFrame frame;

    private Map<String, Integer> easyRecords = new HashMap<>();
    private Map<String, Integer> middleRecords = new HashMap<>();
    private Map<String, Integer> hardRecords = new HashMap<>();

    public RecordManager(JFrame frame) {
        this.frame = frame;
    }

    public void showRecords() {
        new recordWindow();
    }

    public void takeRecord(int record, int mode) {
        Map<String, Integer> recordsMap = chooseRecords(mode);
        if (recordsMap == null) {
            return;
        }
        List<Map.Entry<String, Integer>> recordsList = sortRecords(recordsMap);
        if ((recordsList.size() < MAX_RECORDS) || (recordsList.get(recordsList.size() - 1).getValue() > record)) {
            addRecord(record, recordsList, recordsMap);
        }
    }


    private void addRecord(int record, List<Map.Entry<String, Integer>> recordsList, Map<String, Integer> recordsMap) {
        String name = JOptionPane.showInputDialog(frame, "<html><h1>Поздравляем!</h1>" +
                "<p>Вы совершили новый рекорд!</p>" +
                "<p>Введите своё имя(не более 12 символов):</p>", "Рекорд", JOptionPane.INFORMATION_MESSAGE);
        if (name == null) {
            return;
        }
        if (name.isEmpty()) {
            return;
        }
        name = name.trim().toUpperCase();
        if (name.length() > 12) {
            name = name.substring(0, 11);
        }
        if ((recordsMap.containsKey(name)) && (recordsMap.get(name) <= record)) {
            return;
        }
        recordsMap.put(name, record);
        if (recordsMap.size() > MAX_RECORDS) {
            recordsMap.remove(recordsList.get(MAX_RECORDS - 1).getKey());
        }
    }

    private Map<String, Integer> chooseRecords(int mode) {
        switch (mode) {
            case GameConstant.EASY:
                return easyRecords;
            case GameConstant.MIDDLE:
                return middleRecords;
            case GameConstant.HARD:
                return hardRecords;
            default:
                return new HashMap<>();
        }
    }

    private List<Map.Entry<String, Integer>> sortRecords(Map<String, Integer> records) {
        Comparator<Entry<String, Integer>> comp = Comparator.comparing(Entry::getValue);
        List<Map.Entry<String, Integer>> result = new ArrayList<>(records.entrySet());
        result.sort(comp);
        return result;
    }

    private class recordWindow {
        private JPanel recordPanel;
        private JDialog dialog;

        recordWindow() {
            dialog = new JDialog(frame, "Рекорды", true);
            dialog.setResizable(false);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setLayout(new BorderLayout());

            JRadioButton easyButton = new JRadioButton("Лёгкий");
            easyButton.addActionListener(e -> writeRecord(GameConstant.EASY));
            easyButton.setSelected(true);

            JRadioButton middleButton = new JRadioButton("Средний");
            middleButton.addActionListener(e -> writeRecord(GameConstant.MIDDLE));

            JRadioButton hardButton = new JRadioButton("Сложный");
            hardButton.addActionListener(e -> writeRecord(GameConstant.HARD));

            ButtonGroup buttons = new ButtonGroup();
            buttons.add(easyButton);
            buttons.add(middleButton);
            buttons.add(hardButton);

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            buttonsPanel.add(easyButton);
            buttonsPanel.add(middleButton);
            buttonsPanel.add(hardButton);
            dialog.add(buttonsPanel, BorderLayout.WEST);

            recordPanel = new JPanel();
            dialog.add(recordPanel, BorderLayout.CENTER);
            writeRecord(GameConstant.EASY);
            dialog.setVisible(true);
        }

        private void writeRecord(int mode) {
            List<Map.Entry<String, Integer>> records = sortRecords(chooseRecords(mode));
            recordPanel.removeAll();
            StringBuilder text;
            if (records.isEmpty()) {
                text = new StringBuilder("Рекордов пока нет :)");
            } else {
                text = new StringBuilder("<html><table>");
                for (Map.Entry<String, Integer> record : records) {
                    text.append(String.format("<tr><td><u>%s</u></td><td>%d</td>",
                            record.getKey(), record.getValue()));
                }
                text.append("</table>");
            }
            JLabel recordLabel = new JLabel(text.toString());
            recordLabel.setHorizontalAlignment(SwingConstants.LEFT);
            recordLabel.setVerticalAlignment(SwingConstants.TOP);
            recordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            recordLabel.setPreferredSize(new Dimension(200, 200));
            recordPanel.add(recordLabel, BorderLayout.CENTER);
            recordPanel.validate();
            dialog.pack();
        }
    }
}
