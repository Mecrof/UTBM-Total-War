package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.CadreJoueur;
import vue.PlateForme;

import model.Joueur;

public class ControleurPlateForme implements ActionListener , ChangeListener{
	
	private Joueur j1, j2;
	private PlateForme plateforme;
	private int _joueurCourant;
	
	public ControleurPlateForme(PlateForme p, Joueur j1, Joueur j2) {
		this.plateforme = p;
		this.j1 = j1;
		this.j2 = j2;
		this._joueurCourant = 1;
		//this.plateforme.get_champsVisuel().add
		this.plateforme.get_chj1().get_bPasseLeTour().addActionListener(this);
		this.plateforme.get_chj1().get_sNbJetonsEtudiants().addChangeListener(this);
		this.plateforme.get_chj2().get_bPasseLeTour().addActionListener(this);
		this.plateforme.get_chj2().get_sNbJetonsEtudiants().addChangeListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("toto");
		System.out.println("cmd: "+e.getActionCommand());
		if (e.getSource().equals(plateforme.get_chj1().get_bPasseLeTour()))
		{
			_joueurCourant = 2;
			this.plateforme.changementDeJoueur();
		}
		else if (e.getSource().equals(plateforme.get_chj2().get_bPasseLeTour()))
		{
			_joueurCourant = 1;
			this.plateforme.changementDeJoueur();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		//plateforme.get_champsVisuel().dispatchEvent(new ActionEvent(plateforme.get_chj1(), 1, "Chj1"));
		System.out.println("change");
		if (_joueurCourant == 1)
		{
			// ajouter dans Game une methode qui renvoit les salles attaquables
			//this.plateforme.get_champsMap().afficheSalleDispo(Joueur1, effectif) 
		}
		else if (_joueurCourant == 2)
		{
			// ajouter dans Game une methode qui renvoit les salles attaquables
			//this.plateforme.get_champsMap().afficheSalleDispo(Joueur2, effectif) 
		}
		
	}

}
