package org.example.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Стартовое окно.
 * Настройки поля, выбор режима игры.
 */
public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;

    // Кнопка начала игры
    JButton btnStart = new JButton("Start new game");

    /**
     * Конструктор класса.
     * @param gameWindow игровое окно.
     */
    SettingsWindow (GameWindow gameWindow){
        // Настраиваем расположение окна
        setLocationRelativeTo(gameWindow);
        // Задаем размеры окна
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Добавляем поведение при нажатии на кнопку старта игры
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0,3,3,3);
                setVisible(false);
            }
        });

        // Добавляем кнопку начала игры
        add(btnStart);

    }

}
