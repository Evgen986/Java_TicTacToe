package org.example.game;

import java.util.Random;

/**
 * Класс искусственного интеллекта.
 */
public class Ai {

    private Game game;
    private final Random RANDOM = new Random();

    public Ai(Game game) {
        this.game = game;
    }

    /**
     * Ход бота. Ставит фишку в рандомную ячейку. Проводится проверка, что ячейка не занята фишкой.
     */
    public void aiTurn() {
        // Попытка победить
        if (aiCellSearch(GameDot.AI.ordinal(), game.getWinCount() - 1)) {
            return;
        }
        // Попытка предотвратить победу игрока
        else if (aiCellSearch(GameDot.HUMAN.ordinal(), game.getWinCount() - 1)) {
            return;
        }
        // Рандомный ход
        else {
            int x, y;
            do {
                x = RANDOM.nextInt(game.getFieldSizeX());
                y = RANDOM.nextInt(game.getFieldSizeY());
            } while (!game.isEmptyCell(x, y));
            game.move(y, x, GameDot.AI);
        }
    }

    /**
     * Расчет клетки для хода искусственного интеллекта.
     *
     * @param dot фишка для поиска оптимального хода
     * @param win сложность игры
     * @return true - если клетка была найдена, иначе false.
     */
    private boolean aiCellSearch(int dot, int win) {
        for (int y = 0; y < game.getFieldSizeY(); y++) {
            for (int x = 0; x < game.getFieldSizeX(); x++) {

                // проверка горизонтали
                if (game.checkHorizontal(y, x, dot, win)) {
                    // ближайшая клетка
                    if (game.isValidCell(x - 1, y) && game.isEmptyCell(x - 1, y)) {
                        game.move(y, x - 1, GameDot.AI);
                        return true;
                    }
                    // дальняя клетка
                    else if (game.isValidCell(x + win, y) && game.isEmptyCell(x + win, y)) {
                        game.move(y, x + win, GameDot.AI);
                        return true;
                    }
                }

                // проверка вертикали вверх
                if (game.checkVerticalUp(y, x, dot, win)) {
                    // ближайшая клетка
                    if (game.isValidCell(x, y + 1) && game.isEmptyCell(x, y + 1)) {
                        game.move(y + 1, x, GameDot.AI);
                        return true;
                    }
                    // дальняя клетка
                    else if (game.isValidCell(x, y - win) && game.isEmptyCell(x, y - win)) {
                        game.move(y - win, x, GameDot.AI);
                        return true;
                    }
                }

                // проверка вертикали вниз
                if (game.checkVerticalDown(y, x, dot, win)) {
                    // ближайшая клетка
                    if (game.isValidCell(x, y - 1) && game.isEmptyCell(x, y - 1)) {
                        game.move(y - 1, x, GameDot.AI);
                        return true;
                    }
                    // дальняя клетка
                    else if (game.isValidCell(x, y + win) && game.isEmptyCell(x, y + win)) {
                        game.move(y + win, x, GameDot.AI);
                        return true;
                    }
                }

                // проверка диагонали вверх
                if (game.checkDiagonalUp(y, x, dot, win)) {
                    // ближайшая клетка
                    if (game.isValidCell(x - 1, y + 1) && game.isEmptyCell(x - 1, y + 1)) {
                        game.move(y + 1, x - 1, GameDot.AI);
                        return true;
                    }
                    // дальняя клетка
                    else if (game.isValidCell(x + win, y - win) && game.isEmptyCell(x + win, y - win)) {
                        game.move(y - win, x + win, GameDot.AI);
                        return true;
                    }
                }

                // проверка диагонали вниз
                if (game.checkDiagonalDown(y, x, dot, win)) {
                    // ближайшая клетка
                    if (game.isValidCell(x - 1, y - 1) && game.isEmptyCell(x - 1, y - 1)) {
                        game.move(y - 1, x - 1, GameDot.AI);
                        return true;
                    }
                    // дальняя клетка
                    else if (game.isValidCell(x + win, y + win) && game.isEmptyCell(x + win, y + win)) {
                        game.move(y + win, x + win, GameDot.AI);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
