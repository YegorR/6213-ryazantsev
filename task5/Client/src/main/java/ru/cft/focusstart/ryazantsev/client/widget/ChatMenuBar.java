package ru.cft.focusstart.ryazantsev.client.widget;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChatMenuBar {
    private JMenuBar menuBar = new JMenuBar();

    private JMenuItem inChat = new JMenuItem("Войти в чат");
    private JMenuItem outChat = new JMenuItem("Выйти из чата");
    private JMenuItem exit = new JMenuItem("Выйти из приложения");

    public ChatMenuBar() {
        JMenu menu = new JMenu("Чат");
        menu.add(inChat);
        menu.add(outChat);
        menu.addSeparator();
        menu.add(exit);
        menuBar.add(menu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void addInChatListener(ActionListener action) {
        inChat.addActionListener(action);
    }

    public void addOutChatListener(ActionListener action) {
        outChat.addActionListener(action);
    }

    public void addExitListener(ActionListener action) {
        exit.addActionListener(action);
    }

    public void enableInChat(boolean enable) {
        inChat.setEnabled(enable);
    }

    public void enableOutChat(boolean enable) {
        outChat.setEnabled(enable);
    }
}
