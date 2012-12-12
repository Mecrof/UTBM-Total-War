package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.CadreJoueur;
import vue.PlateForme;

import model.Game;
import model.Joueur;
import model.Salle;

public class ControleurPlateForme implements ActionListener , ChangeListener{
	
	private Game game;
	private Joueur j1, j2;
	private PlateForme plateforme;
	private int _joueurCourant;
	
	public ControleurPlateForme(PlateForme p, Game g, Joueur j1, Joueur j2) {
		this.plateforme = p;
		this.game = g;
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
		ArrayList<Salle> listSalle;
		int effectif;
		if (_joueurCourant == 1)
		{
			effectif = (int)this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue();
			listSalle = this.game.getSalleAttaquable(j1, effectif);
		}
		else
		{
			effectif = (int)this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue();
			listSalle = this.game.getSalleAttaquable(j2, effectif);
		}
		//this.plateforme.get_champsMap().afficherSalleAttaquable(listSalle);
	}


}
