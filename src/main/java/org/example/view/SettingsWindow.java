package org.example.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Стартовое окно.
 * Настройки поля, выбор режима игры.
 */
public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 230;

    private static final String SELECT_FIELD_SIZES = "Выбранный размер поля: ";
    private static final String INSTALLED_LENGTH = "Установленная длина: ";

    // Кнопка начала игры
    JButton btnStart = new JButton("Start new game");

    JSlider sizeW = new JSlider(3, 10);
    JSlider sizeF = new JSlider(3,10);
    JRadioButton btn1 = new JRadioButton("Человек против компьютера");
    JRadioButton btn2 = new JRadioButton("Человек против человека");
    GameWindow gameWindow;

    /**
     * Конструктор класса.
     * @param gameWindow игровое окно.
     */
    SettingsWindow (GameWindow gameWindow){
        this.gameWindow = gameWindow;
        // Настраиваем расположение окна
        setLocationRelativeTo(gameWindow);
        // Задаем размеры окна
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);


        // Создание блока настроек
        JPanel settings = new JPanel(new GridLayout(3, 1));

        // Выбор типа игры
        JPanel typeGame = new JPanel(new GridLayout(3,1));
        typeGame.add(new JLabel("Выберите режим игры"));
        ButtonGroup group1 = new ButtonGroup();

        btn1.setSelected(true);

        group1.add(btn1);
        group1.add(btn2);
        typeGame.add(btn1);
        typeGame.add(btn2);

        // Выбор длины повторений для победы
        JPanel sizeWin = new JPanel(new GridLayout(3,1));
        sizeWin.add(new JLabel("Выберите длину для победы"));
        JLabel labelInstalledLength = new JLabel(INSTALLED_LENGTH);
        sizeWin.add(labelInstalledLength);

        sizeW.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int length = sizeW.getValue();
                labelInstalledLength.setText(INSTALLED_LENGTH + length);
            }
        });
        sizeWin.add(sizeW);

        // Выбор размеров поля
        JPanel sizeField = new JPanel(new GridLayout(3,1));
        sizeField.add(new JLabel("Выберите размеры поля"));
        JLabel labelCurrentSize = new JLabel(SELECT_FIELD_SIZES);
        sizeField.add(labelCurrentSize);

        sizeF.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size = sizeF.getValue();
                labelCurrentSize.setText(SELECT_FIELD_SIZES + size);
                sizeW.setMaximum(size);
            }
        });

        sizeField.add(sizeF);

        // Заполнение окна настроек
        settings.add(typeGame);
        settings.add(sizeField);
        settings.add(sizeWin);

        // Добавляем поведение при нажатии на кнопку старта игры
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();

            }
        });

        // Добавляем панель настроек на основное окно
        add(settings);
        // Добавляем кнопку начала игры
        add(btnStart, BorderLayout.SOUTH);

    }

    private void startNewGame(){
        int mode = 0;
        if (btn1.isSelected()){
            mode = 1;
        } else if (btn2.isSelected()) {
            mode = 2;
        }
        int sizeField = sizeF.getValue();
        int sizeWin = sizeW.getValue();
        gameWindow.startNewGame(mode,sizeField,sizeField,sizeWin);
        setVisible(false);
    }
}
