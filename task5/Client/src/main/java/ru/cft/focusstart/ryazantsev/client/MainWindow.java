package ru.cft.focusstart.ryazantsev.client;

import ru.cft.focusstart.ryazantsev.client.widget.OutputWidget;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
    public MainWindow() {
        frame = new JFrame();
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);


        OutputWidget outputWidget = new OutputWidget();
        frame.setLayout(new BorderLayout());
        frame.add(outputWidget.getWidget());

        frame.pack();
        frame.setVisible(true);
        for(int i = 0; i < 10; ++i) {
            outputWidget.writeMessage(null);
        }
    }
}
