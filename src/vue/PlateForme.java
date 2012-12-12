package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.Game;

public class PlateForme extends JPanel {
	
	private Game game;
	///
	//public JPanel _champsVisuel;
	private CadreMap _champsMap;
	private JPanel _champsJoueur;
	private CadreJoueur _chj1;
	private CadreJoueur _chj2;
	
	public PlateForme(Game g) {
		this.game = g;
		this.setLayout(new BorderLayout());
		/*_champsVisuel = new JPanel();
		_champsVisuel.setMinimumSize(new Dimension(600, 320));
		_champsVisuel.setMaximumSize(new Dimension(600, 320));
		_champsVisuel.setBackground(Color.RED);*/
		_champsMap = new CadreMap(game);

		_champsJoueur = new JPanel();
		
		_champsJoueur.setLayout(new GridLayout(1, 2));
		_champsJoueur.setBackground(Color.CYAN);
		//////////
		_champsJoueur.add(_chj1 = new CadreJoueur(game, game.getJ1()));
		_champsJoueur.add(_chj2 = new CadreJoueur(game, game.getJ2()));
		_chj2.get_bPasseLeTour().setEnabled(false);
		_chj2.get_sNbJetonsEtudiants().setEnabled(false);
		
		//_champsJoueur.add(_sJetonEtudiants = new JSpinner());
		
		//this.add(_champsVisuel, BorderLayout.CENTER);
		this.add(_champsMap, BorderLayout.CENTER);
		this.add(_champsJoueur, BorderLayout.SOUTH);
		
	}
	
	public void changementDeJoueur()
	{
		if (this._chj1.get_bPasseLeTour().isEnabled())
		{
			this._chj1.get_bPasseLeTour().setEnabled(false);
			this._chj1.get_sNbJetonsEtudiants().setEnabled(false);
			this._chj1.get_sNbJetonsEtudiants().setValue(0);
			this._chj2.get_bPasseLeTour().setEnabled(true);
			this._chj2.get_sNbJetonsEtudiants().setEnabled(true);
			//this._chj2.get_sNbJetonsEtudiants().setModel(new SpinnerNumberModel(0, 0, game.getJ2().get_effectifDispo(), 1));
			//game.getJ1().set_effectifDispo(5);
			//this._champsJoueur.repaint();
		}
		else
		{
			this._chj2.get_bPasseLeTour().setEnabled(false);
			this._chj2.get_sNbJetonsEtudiants().setEnabled(false);
			this._chj2.get_sNbJetonsEtudiants().setValue(0);
			this._chj1.get_bPasseLeTour().setEnabled(true);
			this._chj1.get_sNbJetonsEtudiants().setEnabled(true);
			//this._chj1.get_sNbJetonsEtudiants().setModel(new SpinnerNumberModel(0, 0, game.getJ1().get_effectifDispo(), 1));
			//this._champsJoueur.repaint();
		}
	}

	public CadreJoueur get_chj1() {
		return _chj1;
	}

	public void set_chj1(CadreJoueur _chj1) {
		this._chj1 = _chj1;
	}

	public CadreJoueur get_chj2() {
		return _chj2;
	}

	public void set_chj2(CadreJoueur _chj2) {
		this._chj2 = _chj2;
	}
/*
	public JPanel get_champsVisuel() {
		return _champsVisuel;
	}

	public void set_champsVisuel(JPanel _champsVisuel) {
		this._champsVisuel = _champsVisuel;
	}
*/
	public JPanel get_champsJoueur() {
		return _champsJoueur;
	}

	public void set_champsJoueur(JPanel _champsJoueur) {
		this._champsJoueur = _champsJoueur;
	}

	public CadreMap get_champsMap() {
		return _champsMap;
	}

	public void set_champsMap(CadreMap _champsMap) {
		this._champsMap = _champsMap;
	}
	
	

}
