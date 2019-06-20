package ru.cft.focusstart.ryazantsev.client;

import ru.cft.focusstart.ryazantsev.client.widget.ChatMenuBar;
import ru.cft.focusstart.ryazantsev.client.widget.InputWidget;
import ru.cft.focusstart.ryazantsev.client.widget.MembersListWidget;
import ru.cft.focusstart.ryazantsev.client.widget.OutputWidget;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
    private OutputWidget outputWidget;
    private InputWidget inputWidget;
    private MembersListWidget membersListWidget;

    public MainWindow() {
        frame = new JFrame();
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        createGUI();

        frame.pack();
        frame.setVisible(true);
    }

    private void createGUI() {
        createMenu();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        outputWidget = new OutputWidget();
        c.gridx = 0; c.gridy = 0;
        frame.add(outputWidget.getWidget(), c);

        membersListWidget = new MembersListWidget();
        c.gridx = 1; c.gridy = 0;
        frame.add(membersListWidget.getWidget(), c);

        inputWidget = new InputWidget();
        c.gridx = 0; c.gridy = 1;
        frame.add(inputWidget.getWidget(), c);

        JButton button = new JButton("Отправить");
        c.gridx = 1; c.gridy = 1;
        frame.add(button, c);
    }

    private void createMenu() {
        ChatMenuBar myMenu = new ChatMenuBar();
        frame.setJMenuBar(myMenu.getMenuBar());
        myMenu.addExitListener(e -> System.out.println("Выход"));
        myMenu.addInChatListener(e -> System.out.println("Зашли в чат"));
        myMenu.addOutChatListener(e -> System.out.println("Вышли из чата"));
        myMenu.enableOutChat(false);
    }
}
