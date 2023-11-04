package org.example.game;

import lombok.Data;

import java.util.Random;

/**
 * Класс с логикой игры
 */
@Data
public class Game {
    // Генератор случайных чисел
    private static final Random random = new Random();

    // Размерность поля
    private int fieldSizeX;  // по вертикали
    private int fieldSizeY;  // по горизонтали

    private int winCount;

    // Игровое поле в виде массива
    private int[][] field;

    // Тип окончания игры (победа одного из игроков или ничья)
    private GameState gameOverType;

    // Статусы игры
    private boolean isGameOver;  // Игра окончена
    private boolean isInitialized; // Поле инициализировано

    /**
     * Инициализация игрового поля.
     */
    public void initMap(int sizeX, int sizeY, int winCount){
        fieldSizeX = sizeX;
        fieldSizeY = sizeY;
        this.winCount = winCount;
        field = new int[fieldSizeX][fieldSizeY];
        for(int i = 0; i < fieldSizeX; i++){
            for (int j = 0; j < fieldSizeY; j++) {
                field[i][j] = GameDot.EMPTY.ordinal();
            }
        }
    }

    public int getCell(int y, int x){
        return field[y][x];
    }

    /**
     * Проверка корректности хода.
     * @param x ячейка по оси X
     * @param y ячейка по оси Y
     * @return true - при корректных значениях, иначе false.
     */
    public boolean isValidCell(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка, что ячейке присвоена пустая фишка
     * @param x ячейка по оси X
     * @param y ячейка по оси Y
     * @return true - если в ячейке пустая фишка, иначе false.
     */
    public boolean isEmptyCell(int x, int y){
        return field[y][x] == GameDot.EMPTY.ordinal();
    }

    /**
     * Осуществление хода
     * @param y ячейка по горизонтали
     * @param x ячейка по вертикали
     * @param gameDot тип фишки
     */
    public void move(int y, int x, GameDot gameDot){
        field[y][x] = gameDot.ordinal();
    }

    /**
     * Проверка ничьей.
     * @return true - если на поле не осталось свободных клеток, иначе false.
     */
    public boolean isMapFull(){
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == GameDot.EMPTY.ordinal()) return false;
            }
        }
        return true;
    }

    /**
     * Проверка победы.
     * @param c фишка игрока (х или О)
     * @return true - в случае победы, иначе - false.
     */
    public boolean checkWin(int c){
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkHorizontal(y, x, c, winCount)) return true; // горизонталь
                if (checkVerticalUp(y, x, c, winCount)) return true;  // верхняя вертикаль
                if (checkVerticalDown(y, x, c, winCount)) return true;  // нижняя вертикаль
                if (checkDiagonalUp(y, x, c, winCount)) return true;  // верхняя диагональ
                if (checkDiagonalDown(y, x, c, winCount)) return true;  // нижняя диагональ
            }
        }
        return false;
    }

    /**
     * Проверка горизонтали
     * @param y координата Y стартовой точки
     * @param x координата X стартовой точки
     * @param dot фишка для проверки
     * @param win количество повторений подряд для победы
     * @return true - при совпадении кол-ва повторений и условия победы, иначе false.
     */
    public boolean checkHorizontal(int y, int x, int dot, int win){
        int count = 0;  // счетчик повторений фишки
        for (int i = 0; i < win; i++) {  // Перебираем с ограничением условий победы
            if (x + i > fieldSizeX - 1) break;  // Прерываем перебор в случае выхода за пределы массива
            if (field[y][x + i] != dot) return false; // Возвращаем ложь если фишка в ячейки не соответствует проверяемой
            count++;  // увеличиваем счетчик
        }
        return count == win;  // сравниваем счетчик с условием победы
    }
    /**
     * Проверка вертикали вверх
     * @param y координата Y стартовой точки
     * @param x координата X стартовой точки
     * @param dot фишка для проверки
     * @param win количество повторений подряд для победы
     * @return true - при совпадении кол-ва повторений и условия победы, иначе false.
     */
    public boolean checkVerticalUp(int y, int x, int dot, int win){
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (y - i < 0) break;
            if (field[y - i][x] != dot) return false;
            count ++;
        }
        return count == win;
    }
    /**
     * Проверка вертикали вниз
     * @param y координата Y стартовой точки
     * @param x координата X стартовой точки
     * @param dot фишка для проверки
     * @param win количество повторений подряд для победы
     * @return true - при совпадении кол-ва повторений и условия победы, иначе false.
     */
    public boolean checkVerticalDown(int y, int x, int dot, int win){
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (y + i > fieldSizeX - 1) break;
            if (field[y + i][x] != dot) return false;
            count++;
        }
        return count == win;
    }
    /**
     * Проверка диагонали вниз
     * @param y координата Y стартовой точки
     * @param x координата X стартовой точки
     * @param dot фишка для проверки
     * @param win количество повторений подряд для победы
     * @return true - при совпадении кол-ва повторений и условия победы, иначе false.
     */
    public boolean checkDiagonalDown(int y, int x, int dot, int win){
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (y + i > fieldSizeY - 1 || x + i > fieldSizeX - 1) break;
            if (field[y + i][x + i] != dot) return false;
            count++;
        }
        return count == win;
    }
    /**
     * Проверка диагонали вверх
     * @param y координата Y стартовой точки
     * @param x координата X стартовой точки
     * @param dot фишка для проверки
     * @param win количество повторений подряд для победы
     * @return true - при совпадении кол-ва повторений и условия победы, иначе false.
     */
    public boolean checkDiagonalUp(int y, int x, int dot, int win){
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (y - i < 0 || x - i < 0) break;
            if (field[y - i][x - i] != dot) return false;
            count++;
        }
        return count == win;
    }
}
