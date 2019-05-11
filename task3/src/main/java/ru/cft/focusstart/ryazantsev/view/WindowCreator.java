package ru.cft.focusstart.ryazantsev.view;

import ru.cft.focusstart.ryazantsev.logic.IntCouple;

import java.awt.*;
import javax.swing.*;

import static ru.cft.focusstart.ryazantsev.GameConstant.*;

public class WindowCreator {

    private JFrame frame;
    private JPanel panel;

    public void CreateWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Сапёр");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(IconManager.getIcon("icon.png").getImage());
        frame.setJMenuBar(createMenuBar());

        startGame(EASY_SIZE, EASY_MINE_COUNT);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");
        JMenu helpMenu = new JMenu("Справка");

        JMenu newGameMenu = new JMenu("Новая игра");
        gameMenu.add(newGameMenu);
        JMenuItem easyModeItem = new JMenuItem("Лёгкий");
        easyModeItem.addActionListener(e -> startGame(EASY_SIZE, EASY_MINE_COUNT));
        newGameMenu.add(easyModeItem);
        JMenuItem middleModeItem = new JMenuItem("Средний");
        middleModeItem.addActionListener(e -> startGame(MIDDLE_SIZE, MIDDLE_MINE_COUNT));
        newGameMenu.add(middleModeItem);
        JMenuItem hardModeItem = new JMenuItem("Сложный");
        hardModeItem.addActionListener(e -> startGame(HARD_SIZE, HARD_MINE_COUNT));
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

    private void startGame(IntCouple fieldSize, int minesCount) {
        FieldView fieldView = new FieldView(fieldSize, minesCount);
        if (panel != null) {
            frame.remove(panel);
        }
        panel = fieldView.getPanel();
        frame.add(panel);
        frame.pack();
    }
}
