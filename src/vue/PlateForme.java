package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.Game;
import model.Salle;

public class PlateForme extends JPanel {
	
	private Game game;
	///
	//public JPanel _champsVisuel;
	private CadreMap _champsMap;
	private JPanel _champsJoueur;
	private CadreJoueur _chj1;
	private CadreJoueur _chj2;
	
	private String _phase;
	
	public PlateForme(Game g) {
		this.game = g;
		this.setLayout(new BorderLayout());
		_champsMap = new CadreMap(game);

		_champsJoueur = new JPanel();
		
		_champsJoueur.setLayout(new GridLayout(1, 2));
		_champsJoueur.setBackground(Color.CYAN);
		//////////
		_champsJoueur.add(_chj1 = new CadreJoueur(game, game.getJ1()));
		_champsJoueur.add(_chj2 = new CadreJoueur(game, game.getJ2()));
		this.reinitialiser();
		this.add(_champsMap, BorderLayout.CENTER);
		this.add(_champsJoueur, BorderLayout.SOUTH);
	}
	
	public void reinitialiser()
	{
		this._champsMap.reinitialiser();
		this._chj1.reinitialiser();
		this._chj2.reinitialiser();
		this._phase = "Conquête";
		_chj1.get_sNbJetonsEtudiants().setEnabled(true);
		_chj1.get_bDeclin().setEnabled(false);
		_chj1.get_bPasseLeTour().setEnabled(false);
		_chj2.get_bDeclin().setEnabled(false);
		_chj2.get_bPasseLeTour().setEnabled(false);
		_chj2.get_sNbJetonsEtudiants().setEnabled(false);
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (this.game.isAntialiasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setFont(new Font("monospaced", Font.PLAIN, 14));
		g.setColor(Color.BLACK);
		g.drawString("Tour "+game.get_nombreDeTourEffectue()+"/"+game.get_nombreDeTour(), 9, 16);
		g.drawString("Phase : "+this._phase, 9, 29);
		
		g.fillRect(this.getWidth()-26, 5, 21, 20);
		
		g.setColor(Color.WHITE);
		g.drawString("Tour "+game.get_nombreDeTourEffectue()+"/"+game.get_nombreDeTour(), 10, 15);
		g.drawString("Phase : "+this._phase, 10, 28);
		
		g.fillRect(this.getWidth()-25, 4, 21, 20);
		g.setColor(Color.BLACK);
		g.fillRect(this.getWidth()-20, 4+10-2, 12, 5);
	}
	
	public void phaseConquete()
	{
		this._phase = "Conquête";
	}
	
	public void phaseConqueteAvecRenforts()
	{
		this._phase = "Conquête avec Renforts";
		this._chj1.get_bDeclin().setEnabled(false);
		this._chj2.get_bDeclin().setEnabled(false);
		this._chj1.get_sNbJetonsEtudiants().setValue(0);
		this._chj2.get_sNbJetonsEtudiants().setValue(0);
	}
	
	public void phaseRedeploiement()
	{
		this._phase = "Redéploiement";
		this._chj1.get_bDeclin().setEnabled(false);
		this._chj1.get_sNbJetonsEtudiants().setEnabled(false);
		this._chj1.get_sNbJetonsEtudiants().setValue(0);
		this._chj2.get_bDeclin().setEnabled(false);
		this._chj2.get_sNbJetonsEtudiants().setEnabled(false);
		this._chj2.get_sNbJetonsEtudiants().setValue(0);
	}
	
	public void changementDeJoueur()
	{
		this.phaseConquete();
		ArrayList<Salle> list;
		if (this._chj1.get_bPasseLeTour().isEnabled())
		{
			this._chj1.get_bPasseLeTour().setEnabled(false);
			this._chj1.get_bDeclin().setEnabled(false);
			this._chj1.get_sNbJetonsEtudiants().setEnabled(false);
			this._chj1.get_sNbJetonsEtudiants().setValue(0);
			if (game.getJ2().get_listIdSalleOccupee().isEmpty())
			{
				this._chj2.get_bPasseLeTour().setEnabled(false);
				this._chj2.get_bDeclin().setEnabled(false);
			}
			else
			{
				this._chj2.get_bPasseLeTour().setEnabled(true);
				this._chj2.get_bDeclin().setEnabled(true);
			}
			
			this._chj2.get_sNbJetonsEtudiants().setEnabled(true);
			list = this.game.getSalles(this.game.getJ2().get_listIdSalleOccupee());
			//this._chj2.get_sNbJetonsEtudiants().setModel(new SpinnerNumberModel(0, 0, game.getJ2().get_effectifDispo(), 1));
			//game.getJ1().set_effectifDispo(5);
			//this._champsJoueur.repaint();
		}
		else
		{
			this._chj2.get_bPasseLeTour().setEnabled(false);
			this._chj2.get_bDeclin().setEnabled(false);
			this._chj2.get_sNbJetonsEtudiants().setEnabled(false);
			this._chj2.get_sNbJetonsEtudiants().setValue(0);
			if (game.getJ1().get_listIdSalleOccupee().isEmpty())
			{
				this._chj1.get_bPasseLeTour().setEnabled(false);
				this._chj1.get_bDeclin().setEnabled(false);
			}
			else
			{
				this._chj1.get_bPasseLeTour().setEnabled(true);
				this._chj1.get_bDeclin().setEnabled(true);
			}
			this._chj1.get_bDeclin().setEnabled(true);
			this._chj1.get_sNbJetonsEtudiants().setEnabled(true);
			list = this.game.getSalles(this.game.getJ1().get_listIdSalleOccupee());
			//this._chj1.get_sNbJetonsEtudiants().setModel(new SpinnerNumberModel(0, 0, game.getJ1().get_effectifDispo(), 1));
			//this._champsJoueur.repaint();
		}
		//;
		this.get_champsMap().setSallePossedee(list);
		this.get_champsMap().setSalleAttaquable(new ArrayList<Salle>());
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

	public String get_phase() {
		return _phase;
	}

	public void set_phase(String _phase) {
		this._phase = _phase;
	}
	
	

}
