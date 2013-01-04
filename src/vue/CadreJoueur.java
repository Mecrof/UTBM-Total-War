package vue;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

import model.Game;
import model.Joueur;

public class CadreJoueur extends JPanel {

	private static final Insets insets = new Insets(-10+100, 0, 0, 0); 
	
	//private JLabel _lPseudo = new JLabel();
	//private JLabel _lScore = new JLabel("0");
	//private JL
	private SpinnerNumberModel _sNumberModel = new SpinnerNumberModel(0, 0, 10, 1);
	private JSpinner _sNbJetonsEtudiants = new JSpinner(_sNumberModel);
	private JButton _bDeclin = new JButton("Déclin");
	private JButton _bPasseLeTour = new JButton("OK");
	//private boolean _isJoueurActif = true;

	private Game game;
	private Joueur joueur;
	
	public CadreJoueur(Game g, Joueur j) {
		this.game = g;
		this.joueur = j;
		this.setBackground(Color.LIGHT_GRAY);
		((DefaultEditor) _sNbJetonsEtudiants.getEditor()).getTextField().setEditable(false);
		//this._sNbJetonsEtudiants.setEditor(new JSpinner.DefaultEditor(_sNbJetonsEtudiants));
		this.add(_sNbJetonsEtudiants);
		_bDeclin.setFont(new Font(_bDeclin.getFont().getFontName(), Font.PLAIN, 10));
		this.add(_bDeclin);
		this.add(_bPasseLeTour);
		//_sNbJetonsEtudiants.set
	}
	
	public void reinitialiser()
	{
		_sNumberModel.setMaximum(10);
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (this.game.isAntialiasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.BLACK);
		//g.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		//g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 20, 20);
		
		g.setFont(new Font("monospaced", Font.BOLD, 16));
		g.drawString(joueur.get_nom(), 10, 20);
		g.setFont(new Font("monospaced", Font.PLAIN, 11));
		
		try {
			String s = game.getEtudiant(joueur.get_idEtudiantActif()).get_nom();
			if (s.length()>11)
				s = s.substring(0, 11) + "...";
			g.drawString("Etudiants : "+s,
					this.getWidth()*3/6-80, 
					25);
			s = game.getEtudiant(joueur.get_idEtudiantActif()).get_type().getCode()+"-"+game.getSpecialite(joueur.get_idSpecialiteActif()).get_nom();
			if (s.length()>18)
				s = s.substring(0, 18) + "...";
			g.drawString("Spé' : "+s,
					this.getWidth()*3/6-80, 
					50);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		
				*/

		//*
		g.drawString("Engager :", 
				this.getWidth()*3/6-80, 
				this.getHeight()-25);
		_sNbJetonsEtudiants.setBounds(
				this.getWidth()*3/6-10, 
				this.getHeight()-40,
				_sNbJetonsEtudiants.getWidth(), 
				_sNbJetonsEtudiants.getHeight());
		/*
		g.drawString("PVict :", 
				this.getWidth()*3/6+40, 
				this.getHeight()-25);
		*/
		g.drawString("Point(s) Victoire(s) :", 
				this.getWidth()*3/6-80, 
				this.getHeight()-50);
		g.drawString(""+joueur.get_ptVictoire(), 
				this.getWidth()*3/6+80, 
				this.getHeight()-50);
		
		g.drawString("Dispo : ", 
				this.getWidth()*3/6+45, 
				this.getHeight()-25);
		g.drawString(""+joueur.get_effectifDispo(), 
				this.getWidth()*3/6+100, 
				this.getHeight()-25);
				//*/
		/*
		g.drawString("Engager..", 
				this.getWidth()-70, 
				25);
		g.drawString("..étudiants", 
				this.getWidth()-70, 
				30+_sNbJetonsEtudiants.getHeight()+10);
				
		_sNbJetonsEtudiants.setBounds(
				this.getWidth()-65,
				30,
				_sNbJetonsEtudiants.getWidth(), 
				_sNbJetonsEtudiants.getHeight());
				*/
		
		_bPasseLeTour.setBounds(this.getWidth()-70, 
				10, 
				60, 
				this.getHeight()-20);
		
		g.fillRect(10-2, 30-2, 90+4, 45+4);
		Image img = this.game.getEtudiant(this.joueur.get_idEtudiantActif()).get_image();
		g.drawImage(img, 10, 30, 45, 45, this);
		img = this.game.getSpecialite(this.joueur.get_idSpecialiteActif()).get_image();
		g.drawImage(img, 55, 30, 45, 45, this);
		g.drawLine(54, 30, 54, 75);
		g.drawLine(55, 30, 55, 75);
		_bDeclin.setBounds(10, 
				this.getHeight()-40, 
				90, 
				30);
		
	}
	
    public Insets getInsets() {
        return insets;
    }

	public JSpinner get_sNbJetonsEtudiants() {
		return _sNbJetonsEtudiants;
	}

	public void set_sNbJetonsEtudiants(JSpinner _sNbJetonsEtudiants) {
		this._sNbJetonsEtudiants = _sNbJetonsEtudiants;
	}

	public JButton get_bPasseLeTour() {
		return _bPasseLeTour;
	}

	public void set_bPasseLeTour(JButton _bPasseLeTour) {
		this._bPasseLeTour = _bPasseLeTour;
	}

	public JButton get_bDeclin() {
		return _bDeclin;
	}

	public void set_bDeclin(JButton _bDeclin) {
		this._bDeclin = _bDeclin;
	}

	public SpinnerNumberModel get_sNumberModel() {
		return _sNumberModel;
	}

	public void set_sNumberModel(SpinnerNumberModel _sNumberModel) {
		this._sNumberModel = _sNumberModel;
	}
}
