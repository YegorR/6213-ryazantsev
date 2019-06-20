package ru.cft.focusstart.ryazantsev.client.widget;

import ru.cft.focusstart.ryazantsev.common.Message;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.text.SimpleDateFormat;


public class OutputWidget {
    private JScrollPane scrollPane;
    private HTMLPrinter HTMLPrinter;

    public OutputWidget() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        HTMLPrinter = new HTMLPrinter(editorPane);

        scrollPane = new JScrollPane(editorPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.setVerticalScrollBar(new JScrollBar());
    }

    public JComponent getWidget() {
        return scrollPane;
    }

    public void printMessage(Message message) {
        HTMLPrinter.printMessage(message);
    }
}

