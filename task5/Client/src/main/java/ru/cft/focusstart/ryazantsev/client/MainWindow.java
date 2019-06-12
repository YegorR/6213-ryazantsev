package ru.cft.focusstart.ryazantsev.client;

import javax.swing.*;

public class MainWindow {
    private JFrame frame;
    public MainWindow() {
        frame = new JFrame();
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }
}
