package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        testDuTimeService();
        testCompteARebours();
        testHorlogeGUI();
    }

    private static void testDuTimeService() {
        System.out.println("=== Test des Horloges ===");
        // Instancier le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // Instancier plusieurs Horloges
        Horloge horloge1 = new Horloge("Num 1", timerService);
        Horloge horloge2 = new Horloge("Num 2", timerService);
        Horloge horloge3 = new Horloge("Num 3", timerService);
        
        // Garder le programme en cours d'exécution pour voir les résultats
        try {
            Thread.sleep(10000); // Attendre 10 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCompteARebours() {
        System.out.println("\n=== Test du CompteARebours ===");
        TimerService timerService = new DummyTimeServiceImpl();
        
        // Test avec paramètre 5
        System.out.println("\n--- Test avec paramètre 5 ---");
        CompteARebours compte1 = new CompteARebours("Compte1", timerService, 5);
        
        try {
            Thread.sleep(6000); // Attendre 6 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Test avec 10 instances avec valeurs aléatoires entre 10 et 20
        System.out.println("\n--- Test avec 10 instances (valeurs aléatoires 10-20) ---");
        Random random = new Random();
        CompteARebours[] comptes = new CompteARebours[10];
        
        for (int i = 0; i < 10; i++) {
            int valeurInitiale = 10 + random.nextInt(11); // Valeur entre 10 et 20
            comptes[i] = new CompteARebours("Compte" + (i + 2), timerService, valeurInitiale);
        }
        
        // Attendre assez longtemps pour que tous les comptes se terminent
        try {
            Thread.sleep(25000); // Attendre 25 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testHorlogeGUI() {
        System.out.println("\n=== Test de l'interface graphique (Bonus) ===");
        TimerService timerService = new DummyTimeServiceImpl();
        
        // Créer l'interface graphique dans le thread EDT
        SwingUtilities.invokeLater(() -> {
            HorlogeGUI horlogeGUI = new HorlogeGUI("Horloge GUI", timerService);
            horlogeGUI.setVisible(true);
        });
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}