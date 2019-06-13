package ru.cft.focusstart.ryazantsev.client.widget;

import ru.cft.focusstart.ryazantsev.common.Message;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;


public class OutputWidget {
    private JScrollPane scrollPane;
    private TextAppender textAppender;

    public OutputWidget() {
        JEditorPane widget = new JEditorPane();
        widget.setContentType("text/html");
        widget.setEditable(false);
        textAppender = new TextAppender(widget);

        scrollPane = new JScrollPane(widget);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.setVerticalScrollBar(new JScrollBar());
    }

    public JComponent getWidget() {
        return scrollPane;
    }

    public void writeMessage(Message message) {
        textAppender.appendText("<div>Hello World</div>");
    }

    private class TextAppender {
        private int idCount = 0;
        private HTMLDocument doc;

        TextAppender(JEditorPane widget) {
            doc = (HTMLDocument)widget.getDocument();
            try {
                doc.insertAfterStart(doc.getDefaultRootElement(), "<a id=\"0\"></a>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //Метод HTMLDocument insertBeforeEnd работает странно - добавляет лишнюю пустую строку,
        //поэтому приходиться действовать сложнее
        void appendText(String text) {
            try {
                doc.insertAfterEnd(doc.getElement(Integer.toString(idCount)),
                        String.format("<div id=\"%d\">", idCount + 1) + text + "</div>");
                idCount++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
