package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс графического окна, должен быть унаследован от класса JFrame
 */
public class GameWindow extends JFrame {
    // Константа - высота окна
    private static final int WINDOW_HEIGHT = 555;
    // Константа - ширина окна
    private static final int WINDOW_WIDTH = 507;
    // Константа - положение окна по оси X
    private static final int WINDOW_POSX = 800;
    // Константа - положение окна по оси Y
    private static final int WINDOW_POSY = 300;
    // Игровое поле
    private final Map map;
    private final SettingsWindow settings;

    // Кнопка новой игры
    JButton btnStart = new JButton("New Game");
    // Кнопка выхода из приложения
    JButton btnExit = new JButton("Exit");

    /**
     * Пустой конструктор класса.
     */
    public GameWindow(){
        // Завершение работы приложения при закрытии окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Позиционирование окна на экране
        setLocation(WINDOW_POSX, WINDOW_POSY);
        // Размеры окна
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Название окна
        setTitle("TicTacToe");
        // Запрет изменения размеров окна
        setResizable(false);

        // Инициализация полей класса
        this.map = new Map();
        this.settings = new SettingsWindow(this);

        // Настройка поведения копки выхода при ее нажатии
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Завершение работы приложения
                System.exit(0);
            }
        });
        // Настройка поведения кнопки начала игры при ее нажатии
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Окно с настройками игры становится видимым
                settings.setVisible(true);
            }
        });

        // Добавляем панель задав для нее планировщик GridLayout (выстраивает эл-ты в сетку)
        JPanel panButton = new JPanel(new GridLayout(1, 2));
        panButton.add(btnStart);
        panButton.add(btnExit);
        // Добавление игрового поля
        add(map);
        // Добавляем созданную панель в окно, разместив её внизу окна.
        add(panButton, BorderLayout.SOUTH);
        // Задание видимости окна
        setVisible(true);
    }

    /**
     * Метод запуска игры.
     * @param mode тип игры.
     * @param fSzX размерность игрового поля по горизонтали.
     * @param fSzY размерность игрового поля по вертикали.
     * @param wLen условие победы.
     */
    void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        map.startNewGame(mode, fSzX, fSzY, wLen);
    }
}
