package ru.cft.focusstart.ryazantsev.view;

import ru.cft.focusstart.ryazantsev.logic.IntCouple;

import java.awt.*;
import javax.swing.*;

public class WindowCreator {
    static public void CreateWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Сапёр");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(IconManager.getIcon("icon.png").getImage());

        frame.setJMenuBar(createMenuBar());

        FieldView fieldView = new FieldView(new IntCouple(20, 20), 40);
        frame.add(fieldView.getPanel());
        frame.pack();
    }

    static private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");
        JMenu helpMenu = new JMenu("Справка");

        JMenu newGameMenu = new JMenu("Новая игра");
        gameMenu.add(newGameMenu);
        JMenuItem easyModeItem = new JMenuItem("Лёгкий");
        newGameMenu.add(easyModeItem);
        JMenuItem middleModeItem = new JMenuItem("Средний");
        newGameMenu.add(middleModeItem);
        JMenuItem hardModeItem = new JMenuItem("Сложный");
        newGameMenu.add(hardModeItem);

        JMenuItem restartItem = new JMenuItem("Рестарт");
        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        JMenuItem recordsItem = new JMenuItem("Рекорды");
        gameMenu.add(recordsItem);

        JMenuItem helpItem = new JMenuItem("Как играть");
        helpMenu.add(helpItem);
        JMenuItem authorItem = new JMenuItem("Об авторе");
        helpMenu.add(authorItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
