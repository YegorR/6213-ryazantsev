package ru.cft.focusstart.ryazantsev.view;

import ru.cft.focusstart.ryazantsev.logic.GameStatus;

import javax.swing.*;
import java.awt.*;

public class Top {
    private static final int SIZE = 30;

    private JLabel statusLabel;
    private JLabel flagCountLabel;
    private JLabel timerLabel;

    private JPanel panel;

    public Top() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));
        panel.setLayout(new BorderLayout());


        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        timerLabel = new JLabel("000");
        timerLabel.setFont(timerLabel.getFont().deriveFont(30f));


        flagCountLabel = new JLabel();
        flagCountLabel.setFont(flagCountLabel.getFont().deriveFont(30f));

        panel.add(statusLabel, BorderLayout.CENTER);
        panel.add(timerLabel, BorderLayout.EAST);
        panel.add(flagCountLabel, BorderLayout.WEST);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updateStatusLabel(GameStatus status) {
        switch (status) {
            case CONTINUED:
                statusLabel.setIcon(null);
                break;
            case DEFEAT:
                statusLabel.setIcon(IconManager.getIcon("defeat.png", SIZE));
                break;
            case VICTORY:
                statusLabel.setIcon(IconManager.getIcon("victory.png", SIZE));
                break;
        }
    }

    void updateFlags(int flags) {
        flagCountLabel.setText(String.format("%03d", flags));
    }

    void updateTimer(int time) {
        timerLabel.setText(String.format("%03d", time));
    }
}
