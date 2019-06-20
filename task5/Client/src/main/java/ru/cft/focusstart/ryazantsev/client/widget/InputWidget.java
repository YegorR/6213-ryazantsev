package ru.cft.focusstart.ryazantsev.client.widget;

import javax.swing.*;
import java.awt.*;

public class InputWidget {
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public InputWidget() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(300, 70));
        scrollPane.setVerticalScrollBar(new JScrollBar());
    }

    public JComponent getWidget() {
        return scrollPane;
    }

    public String readText() {
        String text = textArea.getText();
        textArea.setText("");
        return text;
    }
}
