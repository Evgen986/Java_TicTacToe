package org.example.view;

import org.example.game.Ai;
import org.example.game.GameDot;
import org.example.game.Game;
import org.example.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Класс отображения игрового поля. Наследуется от класса JPanel,
 * что позволяет настраивать игровое поле.
 */
public class Map extends JPanel {
    // Ширина окна
    private int panelWidth;
    // Высота окна
    private int panelHeight;
    // Высота ячейки
    private int cellHeight;
    // Ширина ячейки
    private int cellWidth;
    // Константа отступа от края ячейки (5 пикселей)
    private final static int DOT_PADDING = 5;
    // Сообщения по окончанию игры.
    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";
    // Поля игры и искусственного интеллекта.
    private final Game game;
    private final Ai ai;

    /**
     * Конструктор класса.
     * В конструкторе добавляем "слушатель" мыши и переопределяем метод mouseReleased,
     * который вызывается при нажатии на кнопку мыши вызывает метод update() перерисовывающий игровое поле
     */
    public Map(){
        game = new Game();
        ai = new Ai(game);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        game.setInitialized(false);
    }

    private void update(MouseEvent e){
        // Проводим проверку окончена ли игра и инициализировано ли поле.
        if (game.isGameOver() || !game.isInitialized()) return;
        /*  Получаем координаты, где произошел щелчок мыши.
            Полученную координату делим на ширину ячейки.
            Тем самым получая индекс ячейки в которой произошел щелчок.
            Например: X=405, Y=400
                        405/185 = 2 (целочисленное деление (2,189))
                        400/169 = 2 (целочисленное деление (2,366))
         */
        int cellX = e.getX()/cellWidth;
        int cellY = e.getY()/cellHeight;
        System.out.printf("x=%d y=%d\n", cellX, cellY);

        // TODO Возможно объединить в единый метод
        if (!game.isValidCell(cellX, cellY) || !game.isEmptyCell(cellX, cellY)) return;

        // Игрок осуществляет ход
        game.move(cellY, cellX, GameDot.HUMAN);
        // Заново перерисовываем поле.
        repaint();
        if (checkEndGame(GameDot.HUMAN, GameState.STATE_WIN_HUMAN)) return;

        // Бот осуществляет ход
        ai.aiTurn();
        repaint();
        if (checkEndGame(GameDot.AI, GameState.STATE_WIN_AI)) return;
    }

    /**
     * Проверка возможности окончания игры.
     * @param gameDot фишка игрока.
     * @param gameState статус игры.
     * @return true - при завершении игры, иначе false.
     */
    private boolean checkEndGame(GameDot gameDot, GameState gameState){
        // Проверяем победу
        if (game.checkWin(gameDot.ordinal())) {
            game.setGameOverType(gameState);
            game.setGameOver(true);
            repaint();
            return true;
        }
        // Проверяем ничью
        if (game.isMapFull()){
            game.setGameOverType(GameState.STATE_DRAW);
            game.setGameOver(true);
            repaint();
            return true;
        }
        return false;
    }

    /**
     * Запуск игры.
     * @param mode тип игры (пк-пк, человек-пк).
     * @param fSzX размер поля в ширину.
     * @param fSzY размер поля в высоту.
     * @param wLen количество повторений для победы.
     */
    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nLength: %d\n",
                mode, fSzX, fSzY, wLen);
        // Инициализируем игровое поле
        game.initMap(fSzX,fSzY,wLen); // TODO временная инициализация, заменить параметры метода
        game.setGameOver(false);
        game.setInitialized(true);
        // Принудительно заставляем Swing перерисовать игровое поле.
        repaint();
    }

    /**
     * Переопределенный метод отрисовки компонента (игрового поля).
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g){
        if (!game.isInitialized()) return;
        // Получаем ширину окна
        panelWidth = getWidth();
        // Получаем высоту окна
        panelHeight = getHeight();
        // Определяем высоту ячейки
        cellHeight = panelHeight / game.getFieldSizeY();
        // Определяем ширину ячейки
        cellWidth = panelWidth / game.getFieldSizeX();

        // Задаем цвет линии
        g.setColor(Color.BLACK);

        // Рисуем сетку поля для игры
        // Горизонтальные линии. Перебираем в цикле кол-во горизонтальных линии.
        for (int h = 0; h < game.getFieldSizeY(); h++) {
            // Получаем координату на каком удалении от 0 точки по оси Y рисовать линию
            int y = h * cellHeight;
            /*  Рисуем линию от
                (0 координаты X, рассчитанной координаты Y) до
                (конца окна по координатам X, рассчитанной координате Y).
                При окне высотой - 555, шириной 507
                начальная точка - (0, 0) - конечная точка (507, 0)
                начальная точка - (0, 169) - конечная точка (507, 169)
                начальная точка - (0, 338) - конечная точка (507, 338)
             */
            g.drawLine(0, y, panelWidth, y);
        }
        // Вертикальные линии.
        for (int w = 0; w < game.getFieldSizeX(); w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        // Отрисовка фишек участников
        for (int y = 0; y < game.getFieldSizeY(); y++) {
            for (int x = 0; x < game.getFieldSizeX(); x++) {
                if (game.getCell(y, x) == GameDot.EMPTY.ordinal()) continue;

                if (game.getCell(y, x) == GameDot.HUMAN.ordinal()){
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (game.getCell(y, x) == GameDot.AI.ordinal()) {
                    g.setColor(Color.RED);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }else {
                    throw new RuntimeException("Unexpected value " + game.getCell(y, x) +
                            " in cell: x=" + x + " y=" + y);
                }
            }
        }
        if (game.isGameOver()) showMessageGameOver(g);
    }

    /**
     * Вывод сообщения об окончании игры в текущем окне.
     * @param g
     */
    private void showMessageGameOver(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,200,getWidth(),70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        // TODO добавить тип победы
        switch (game.getGameOverType()){
            case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 20, getHeight() /2);
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 70, getHeight() /2);
            default -> throw new RuntimeException("Unexpected gameOver state: " + game.getGameOverType());
        }
    }

}
