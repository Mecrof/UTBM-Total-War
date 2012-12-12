package vue;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

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
		this.add(_bPasseLeTour);
		//_sNbJetonsEtudiants.set
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		//g.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 20, 20);
		
		g.setFont(new Font("monospaced", Font.BOLD, 16));
		g.drawString(joueur.get_nom(), 10, 20);
		g.setFont(new Font("monospaced", Font.PLAIN, 11));
		
		try {
			g.drawString("Etudiants : "+game.getEtudiant(joueur.get_idEtudiantActif()).get_nom(),
					this.getWidth()*3/6-80, 
					25);
			g.drawString("Specialité : "+game.getEtudiant(joueur.get_idEtudiantActif()).get_type()+"-"+game.getSpecialite(joueur.get_idSpecialiteActif()).get_nom(),
					this.getWidth()*3/6-80, 
					50);
		} catch (Exception e) {
			e.printStackTrace();
		}

		g.drawString("Engager :", 
				this.getWidth()*3/6-80, 
				this.getHeight()-25);
		g.drawString("Disponible(s) : ", 
				this.getWidth()*3/6-80, 
				this.getHeight()-50);
		g.drawString(""+joueur.get_effectifDispo(), 
				this.getWidth()*3/6+50, 
				this.getHeight()-50);
		_sNbJetonsEtudiants.setBounds(
				this.getWidth()*3/6+50,
				this.getHeight()-40,
				_sNbJetonsEtudiants.getWidth(), 
				_sNbJetonsEtudiants.getHeight());
		_bPasseLeTour.setBounds(this.getWidth()-70, 
				this.getHeight()-40, 
				60, 
				30);
		g.fillRect(10, 30, this.getHeight()-40, this.getHeight()-40);
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
}
