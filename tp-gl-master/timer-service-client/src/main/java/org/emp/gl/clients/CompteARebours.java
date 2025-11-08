package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class CompteARebours implements TimerChangeListener {

    String name;
    TimerService timerService;
    int compte;

    public CompteARebours(String name, TimerService timerService, int compteInitial) {
        this.name = name;
        this.timerService = timerService;
        this.compte = compteInitial;

        // S'inscrire comme observateur
        if (timerService != null) {
            timerService.addTimeChangeListener(this);
        }

        System.out.println("CompteARebours " + name + " initialisé avec " + compte + " secondes!");
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Décrémenter à chaque seconde
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            if (compte > 0) {
                compte--;
                System.out.println(name + " : " + compte + " secondes restantes");
                
                // Se désinscrire quand le compte arrive à 0
                if (compte == 0) {
                    if (timerService != null) {
                        timerService.removeTimeChangeListener(this);
                        System.out.println(name + " : Compte terminé! Désinscription effectuée.");
                    }
                }
            }
        }
    }

    public int getCompte() {
        return compte;
    }
}

