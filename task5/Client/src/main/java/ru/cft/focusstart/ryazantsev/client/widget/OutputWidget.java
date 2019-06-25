package ru.cft.focusstart.ryazantsev.client.widget;

import ru.cft.focusstart.ryazantsev.common.Message;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.text.SimpleDateFormat;


public class OutputWidget {
    private JScrollPane scrollPane;
    private HTMLPrinter HTMLPrinter;
    private JEditorPane editorPane;

    public OutputWidget() {
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        HTMLPrinter = new HTMLPrinter(editorPane);

        scrollPane = new JScrollPane(editorPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
    }

    public JComponent getWidget() {
        return scrollPane;
    }

    public void printMessage(Message message) {
        HTMLPrinter.printMessage(message);
    }

    public void clear() {
        HTMLPrinter = new HTMLPrinter(editorPane);
    }
}

class HTMLPrinter {
    private static final String CSS = "<style>div{margin-bottom: 5px}" +
            ".username {color: blue;font-weight: bold;font-family: sans-serif;}" +
            ".time {color: silver;}" +
            ".notification {color: gray;font-family: sans-serif;}" +
            "</style>";
    private static final String MESSAGE = "<span class=\"username\">%s</span> <span class=\"time\">%s</span><br/>" +
            "<span class=\"text\">%s</span>";
    private static final String NEW_MEMBER = "<span class=\"notification\">Пользователь " +
            "<span class=\"username\">%s</span> присоединился к нам!</span>";
    private static final String GONE_MEMBER = "<span class=\"notification\">Пользователь " +
            "<span class=\"username\">%s</span> отключился...</span>";
    private static final String SERVER_OUT = "<span class=\"notification\">К сожалению, сервер отключился :(</span>";
    private int idCount = 0;
    private HTMLDocument doc;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    HTMLPrinter(JEditorPane editorPane) {
        editorPane.setContentType("text/html");
        editorPane.setText(CSS);
        doc = (HTMLDocument) editorPane.getDocument();
        try {
            doc.insertAfterStart(doc.getDefaultRootElement(), "<a id=\"0\"></a>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void printMessage(Message message) {
        switch (message.getMessageType()) {
            case MESSAGE:
                appendText(String.format(MESSAGE, escapeText(message.getName()), dateFormat.format(message.getDate()),
                        escapeText(message.getText())));
                break;
            case NEW_MEMBER:
                appendText(String.format(NEW_MEMBER, escapeText(message.getName())));
                break;
            case GONE_MEMBER:
                appendText(String.format(GONE_MEMBER, escapeText(message.getName())));
                break;
            case SERVER_OUT:
                appendText(SERVER_OUT);
        }
    }

    //Метод HTMLDocument insertBeforeEnd работает странно - добавляет лишнюю пустую строку,
    //поэтому приходится действовать сложнее
    private void appendText(String text) {
        try {
            doc.insertAfterEnd(doc.getElement(Integer.toString(idCount)),
                    String.format("<div id=\"%d\">", idCount + 1) + text + "</div>");
            idCount++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String escapeText(String text) {
        text = text.replaceAll("&", "&amp;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\"", "&quot;");
        return text;
    }


}

