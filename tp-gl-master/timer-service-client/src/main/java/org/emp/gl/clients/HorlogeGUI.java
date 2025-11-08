package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HorlogeGUI extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private JLabel timeLabel;
    private JLabel titleLabel;

    public HorlogeGUI(String title, TimerService timerService) {
        this.timerService = timerService;

        // Configuration de la fenêtre
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Création de l'interface
        initUI();

        // S'inscrire comme observateur
        if (timerService != null) {
            timerService.addTimeChangeListener(this);
        }

        // Fermer proprement la fenêtre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (timerService != null) {
                    timerService.removeTimeChangeListener(HorlogeGUI.this);
                }
                dispose();
            }
        });
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Titre
        titleLabel = new JLabel("Horloge", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Affichage de l'heure
        timeLabel = new JLabel("00:00:00", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(timeLabel, BorderLayout.CENTER);

        // Mettre à jour l'heure initiale
        updateTime();
    }

    private void updateTime() {
        if (timerService != null) {
            String time = String.format("%02d:%02d:%02d",
                    timerService.getHeures(),
                    timerService.getMinutes(),
                    timerService.getSecondes());
            timeLabel.setText(time);
        }
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Mettre à jour l'interface graphique dans le thread EDT
        SwingUtilities.invokeLater(() -> {
            if (TimerChangeListener.SECONDE_PROP.equals(prop) ||
                TimerChangeListener.MINUTE_PROP.equals(prop) ||
                TimerChangeListener.HEURE_PROP.equals(prop)) {
                updateTime();
            }
        });
    }
}

