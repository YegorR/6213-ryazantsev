package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.logic.*;
import ru.cft.focusstart.ryazantsev.timer.GameTimer;
import ru.cft.focusstart.ryazantsev.util.*;
import ru.cft.focusstart.ryazantsev.view.Field;
import ru.cft.focusstart.ryazantsev.view.IconManager;
import ru.cft.focusstart.ryazantsev.view.RecordManager;
import ru.cft.focusstart.ryazantsev.view.Top;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

import static ru.cft.focusstart.ryazantsev.util.GameConstant.*;

class GameManager {
    private FieldLogic fieldLogic;
    private Top top;
    private GameTimer gameTimer;
    private FieldCreator fieldCreator;
    private Field field;
    private RecordManager recordManager;

    private boolean newGame = true;
    private int mode = EASY;
    private JFrame frame;
    private JPanel fieldPanel;

    GameManager() {
        top = new Top();
        gameTimer = new GameTimer(this::pushByTimer);
        createWindow();
        recordManager = new RecordManager(frame);
        startGame(EASY);
    }

    private void createWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Сапёр");
        frame.setResizable(false);
        frame.setIconImage(IconManager.getIcon("icon.png").getImage());
        frame.setJMenuBar(createMenuBar());
        frame.setLayout(new BorderLayout());

        frame.add(top.getPanel(), BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");

        JMenu newGameMenu = new JMenu("Новая игра");
        gameMenu.add(newGameMenu);
        JMenuItem easyModeItem = new JMenuItem("Лёгкий");
        easyModeItem.addActionListener(e -> startGame(EASY));
        newGameMenu.add(easyModeItem);
        JMenuItem middleModeItem = new JMenuItem("Средний");
        middleModeItem.addActionListener(e -> startGame(MIDDLE));
        newGameMenu.add(middleModeItem);
        JMenuItem hardModeItem = new JMenuItem("Сложный");
        hardModeItem.addActionListener(e -> startGame(HARD));
        newGameMenu.add(hardModeItem);

        gameMenu.addSeparator();
        JMenuItem recordsItem = new JMenuItem("Рекорды");
        recordsItem.addActionListener(e -> recordManager.showRecords());
        gameMenu.add(recordsItem);

        menuBar.add(gameMenu);

        return menuBar;
    }

    private void press(IntCouple cell, boolean left) {
        if (newGame) {
            if (!left) {
                return;
            }
            newGame = false;
            fieldCreator.createField(cell);
            fieldLogic = new FieldLogic(fieldCreator.getField(), fieldCreator.getMines());
            gameTimer.run();
        }
        FieldAnswer fieldAnswer = fieldLogic.pressCell(cell, left);

        Set<Map.Entry<IntCouple, ViewCellValue>> updatedCells = fieldAnswer.getCells().entrySet();
        for (Map.Entry<IntCouple, ViewCellValue> updatedCell : updatedCells) {
            field.updateCell(updatedCell.getKey(), updatedCell.getValue());
        }

        GameStatus gameStatus = fieldAnswer.getGameStatus();
        top.updateStatusLabel(gameStatus);
        top.updateFlags(fieldAnswer.getFlags());
        if (gameStatus == GameStatus.DEFEAT) {
            finishGame(false);
        } else if (gameStatus == GameStatus.VICTORY) {
            finishGame(true);
        }
    }

    private void finishGame(boolean victory) {
        field.disableAll();
        gameTimer.stop();
        if (victory) {
            recordManager.takeRecord(gameTimer.getSeconds(), mode);
        }
    }

    private void pushByTimer(int seconds) {
        top.updateTimer(seconds);
    }

    private void startGame(int mode) {
        IntCouple fieldSize;
        int minesCount;
        switch (mode) {
            case EASY:
                fieldSize = EASY_SIZE;
                minesCount = EASY_MINE_COUNT;
                break;
            case MIDDLE:
                fieldSize = MIDDLE_SIZE;
                minesCount = MIDDLE_MINE_COUNT;
                break;
            case HARD:
                fieldSize = HARD_SIZE;
                minesCount = HARD_MINE_COUNT;
                break;
            default:
                return;
        }
        this.mode = mode;
        newGame = true;
        fieldCreator = new FieldCreator(fieldSize, minesCount);
        field = new Field(fieldSize, this::press);
        if (fieldPanel != null) {
            frame.remove(fieldPanel);
        }
        fieldPanel = field.getFieldPanel();
        frame.add(fieldPanel, BorderLayout.CENTER);
        frame.pack();
        top.updateTimer(0);
        top.updateFlags(minesCount);
        top.updateStatusLabel(GameStatus.CONTINUED);
    }
}
