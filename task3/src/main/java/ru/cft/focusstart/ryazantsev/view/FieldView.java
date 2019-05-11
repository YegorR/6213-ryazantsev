package ru.cft.focusstart.ryazantsev.view;


import ru.cft.focusstart.ryazantsev.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class FieldView {

    private JPanel gamePanel;
    private CellButton[][] buttons;

    private Top top;

    private boolean newGame = true;
    private FieldCreator fieldCreator;
    private FieldLogic fieldLogic;
    private IntCouple fieldSize;
    private int minesCount;

    public FieldView(IntCouple fieldSize, int minesCount, Top top) {
        this.fieldSize = fieldSize;
        this.minesCount = minesCount;
        this.top = top;

        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gamePanel.setLayout(new GridLayout(fieldSize.getX(), fieldSize.getY()));
        top.updateStatusLabel(GameStatus.CONTINUED);
        createButtons();
    }


    public void restart() {
        if (newGame) {
            return;
        }
        for (CellButton[] row : buttons) {
            for (CellButton button : row) {
                gamePanel.remove(button);
            }
        }
        top.updateStatusLabel(GameStatus.CONTINUED);
        createButtons();
        gamePanel.validate();
        fieldLogic = new FieldLogic(fieldCreator.getField(), this);
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void updateCells(Map<IntCouple, ViewCellValue> cells, GameStatus status) {
        top.updateStatusLabel(status);
        for (Map.Entry<IntCouple, ViewCellValue> cell : cells.entrySet()) {
            ViewCellValue state = cell.getValue();
            int x = cell.getKey().getX();
            int y = cell.getKey().getY();
            buttons[x][y].setState(state);
            if (status == GameStatus.CONTINUED) {
                switch (state) {
                    case ONE:
                    case TWO:
                    case THREE:
                    case FOUR:
                    case FIVE:
                    case SIX:
                    case SEVEN:
                    case EIGHT:
                    case ZERO:
                        buttons[x][y].setEnabled(false);
                        buttons[x][y].setEnabledRight(false);
                        buttons[x][y].setEnabledSimpleClick(true);
                        break;
                    case UNTOUCHED:
                        buttons[x][y].setEnabled(true);
                        buttons[x][y].setEnabledRight(true);
                        break;
                    case FLAG:
                        buttons[x][y].setEnabled(false);
                        buttons[x][y].setEnabledRight(true);
                }
            }
        }
        if (status != GameStatus.CONTINUED) {
            for (CellButton[] buttons : buttons) {
                for (CellButton button : buttons) {
                    button.setEnabled(false);
                    button.setEnabledRight(false);
                    button.setEnabledSimpleClick(false);
                }
            }
        }
    }

    private void createButtons() {
        buttons = new CellButton[fieldSize.getX()][fieldSize.getY()];
        for (int i = 0; i < fieldSize.getX(); ++i) {
            for (int j = 0; j < fieldSize.getY(); ++j) {
                buttons[i][j] = new CellButton(this, new IntCouple(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }
    }

    void pressButton(IntCouple button, boolean left) {
        if (newGame) {
            if (!left) {
                return;
            }
            newGame = false;
            fieldCreator = new FieldCreator(fieldSize, button, minesCount);
            fieldLogic = new FieldLogic(fieldCreator.getField(), this);
        }
        fieldLogic.pressCell(button, left);
    }
}

class CellButton extends JButton {
    private static final int BUTTON_SIZE = 20;

    static private Icon mineIcon = IconManager.getIcon("mine.png", BUTTON_SIZE);
    static private Icon untouchedIcon = IconManager.getIcon("untouched.png", BUTTON_SIZE);
    static private Icon flagIcon = IconManager.getIcon("flag.png", BUTTON_SIZE);
    static private Icon touchedIcon = IconManager.getIcon("touched.png", BUTTON_SIZE);
    static private Icon num1Icon = IconManager.getIcon("num1.png", BUTTON_SIZE);
    static private Icon num2Icon = IconManager.getIcon("num2.png", BUTTON_SIZE);
    static private Icon num3Icon = IconManager.getIcon("num3.png", BUTTON_SIZE);
    static private Icon num4Icon = IconManager.getIcon("num4.png", BUTTON_SIZE);
    static private Icon num5Icon = IconManager.getIcon("num5.png", BUTTON_SIZE);
    static private Icon num6Icon = IconManager.getIcon("num6.png", BUTTON_SIZE);
    static private Icon num7Icon = IconManager.getIcon("num7.png", BUTTON_SIZE);
    static private Icon num8Icon = IconManager.getIcon("num8.png", BUTTON_SIZE);
    static private Icon noMineIcon = IconManager.getIcon("nomine.png", BUTTON_SIZE);

    static private Dimension sizeDimension = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
    private ViewCellValue state = ViewCellValue.UNTOUCHED;
    private boolean enabledRight = true;
    private boolean enabledSimpleClick = true;

    CellButton(FieldView fieldView, IntCouple cell) {
        super();
        this.addActionListener(e -> fieldView.pressButton(cell, true));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getButton() == MouseEvent.BUTTON3) && enabledRight) {
                    fieldView.pressButton(cell, false);
                } else if ((e.getButton() == MouseEvent.BUTTON1) && (!isEnabled()) && (enabledSimpleClick)) {
                    fieldView.pressButton(cell, true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        setPreferredSize(sizeDimension);
        changeIcon();
    }

    void setState(ViewCellValue state) {
        this.state = state;
        changeIcon();
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        setDisabledIcon(icon);
    }

    void setEnabledRight(boolean b) {
        enabledRight = b;
    }

    void setEnabledSimpleClick(boolean b) {
        enabledSimpleClick = b;
    }

    private void changeIcon() {
        Icon icon;
        switch (state) {
            case ZERO:
                icon = touchedIcon;
                break;
            case UNTOUCHED:
                icon = untouchedIcon;
                break;
            case ONE:
                icon = num1Icon;
                break;
            case TWO:
                icon = num2Icon;
                break;
            case THREE:
                icon = num3Icon;
                break;
            case FOUR:
                icon = num4Icon;
                break;
            case FIVE:
                icon = num5Icon;
                break;
            case SIX:
                icon = num6Icon;
                break;
            case SEVEN:
                icon = num7Icon;
                break;
            case EIGHT:
                icon = num8Icon;
                break;
            case FLAG:
                icon = flagIcon;
                break;
            case MINE:
                icon = mineIcon;
                break;
            case NOMINE:
                icon = noMineIcon;
                break;
            default:
                return;
        }
        setIcon(icon);
    }
}