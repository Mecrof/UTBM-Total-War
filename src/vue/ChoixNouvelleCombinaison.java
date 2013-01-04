package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controleur.MoveDeclin;
import controleur.MoveType;
import controleur.Rules;

import model.Game;
import model.Joueur;
import model.Salle;

public class ChoixNouvelleCombinaison extends JDialog implements ActionListener {
	
	private Game game;
	private Rules rules;
	private Joueur joueur;
	
	private int indexCombinaison = 0;
	private ArrayList<model.Type[]> listCombi;
	
	private JPanel _pNord = new JPanel();
		private JPanel _pNordEtudiant = new JPanel();
			private JLabel _lNomEtudiant = new JLabel();
			private JLabel _lEtudiantSpe = new JLabel();
		private JPanel _pNordSpecialite = new JPanel();
			private JLabel _lNomSpecialite = new JLabel();
			private JLabel _lSpecialiteSpe = new JLabel();
	private JPanel _pCentre = new JPanel();
		private JButton _bCombinaisonSuivante = new JButton("Combinaison suivante (4)");
	private JPanel _pSud = new JPanel();
		private JButton _bAnnuler = new JButton("Annuler");
		private JButton _bChoisir = new JButton("Choisir");
	
	public ChoixNouvelleCombinaison(Component c, Game g, Rules r, Joueur j) {
		this.game = g;
		this.listCombi = this.game.renewVisibleCoupleEtuSpe();
		this.rules = r;
		this.joueur = j;
		
		this.actualiserAffichageCombinaison();
		
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(c);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Nouvelle combinaison");
		
		this.setLayout(new BorderLayout());
			_pNord.setLayout(new GridLayout(2, 1));
				_pNordEtudiant.setLayout(new GridLayout(2,2));
					_pNordEtudiant.add(new JLabel("Nom :"));
					_pNordEtudiant.add(_lNomEtudiant);
					_pNordEtudiant.add(new JLabel("Spé' :"));
					_pNordEtudiant.add(_lEtudiantSpe);
				_pNordEtudiant.setBorder(new TitledBorder("Type d'étudiant"));
				_pNordSpecialite.setLayout(new GridLayout(2,2));
					_pNordSpecialite.add(new JLabel("Nom :"));
					_pNordSpecialite.add(_lNomSpecialite);
					_pNordSpecialite.add(new JLabel("Spé' :"));
					_pNordSpecialite.add(_lSpecialiteSpe);
				_pNordSpecialite.setBorder(new TitledBorder("Type de spécialité"));
			_pNord.add(_pNordEtudiant);
			_pNord.add(_pNordSpecialite);
		this.add(_pNord, BorderLayout.NORTH);
			_pCentre.add(_bCombinaisonSuivante);
		this.add(_pCentre, BorderLayout.CENTER);
			_pSud.setLayout(new GridLayout(1, 2));
				_pSud.add(_bAnnuler);
				_pSud.add(_bChoisir);
		this.add(_pSud, BorderLayout.SOUTH);
		
		this._bAnnuler.addActionListener(this);
		this._bChoisir.addActionListener(this);
		this._bCombinaisonSuivante.addActionListener(this);
		
		this.setModal(true);
		this.setVisible(true);
	}
	
	private void actualiserAffichageCombinaison()
	{
		_lNomEtudiant.setText(this.listCombi.get(indexCombinaison)[0].get_nom());
		_lEtudiantSpe.setText(this.listCombi.get(indexCombinaison)[0].get_type().getCode());
		_lNomSpecialite.setText(this.listCombi.get(indexCombinaison)[1].get_nom());
		_lSpecialiteSpe.setText(this.listCombi.get(indexCombinaison)[1].get_type().getCode());
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (this.game.isAntialiasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(_bAnnuler))
		{
			this.dispose();
		}
		else if (e.getSource().equals(_bCombinaisonSuivante))
		{
			this.indexCombinaison++;
			if (this.indexCombinaison>=4)
				_bCombinaisonSuivante.setEnabled(false);
			_bCombinaisonSuivante.setText("Combinaison suivante ("+(4-this.indexCombinaison)+")");
			this.actualiserAffichageCombinaison();
		}
		else if (e.getSource().equals(_bChoisir))
		{
			rules.action(new MoveDeclin(MoveType.PasserEnDeclin, joueur, this.listCombi.get(indexCombinaison)[0].get_id()));
			this.dispose();
		}
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public JLabel get_lNomEtudiant() {
		return _lNomEtudiant;
	}

	public void set_lNomEtudiant(JLabel _lNomEtudiant) {
		this._lNomEtudiant = _lNomEtudiant;
	}

	public JLabel get_lEtudiantSpe() {
		return _lEtudiantSpe;
	}

	public void set_lEtudiantSpe(JLabel _lEtudiantSpe) {
		this._lEtudiantSpe = _lEtudiantSpe;
	}

	public JLabel get_lNomSpecialite() {
		return _lNomSpecialite;
	}

	public void set_lNomSpecialite(JLabel _lNomSpecialite) {
		this._lNomSpecialite = _lNomSpecialite;
	}

	public JLabel get_lSpecialiteSpe() {
		return _lSpecialiteSpe;
	}

	public void set_lSpecialiteSpe(JLabel _lSpecialiteSpe) {
		this._lSpecialiteSpe = _lSpecialiteSpe;
	}

	public JButton get_bCombinaisonSuivante() {
		return _bCombinaisonSuivante;
	}

	public void set_bCombinaisonSuivante(JButton _bCombinaisonSuivante) {
		this._bCombinaisonSuivante = _bCombinaisonSuivante;
	}

	public JButton get_bAnnuler() {
		return _bAnnuler;
	}

	public void set_bAnnuler(JButton _bAnnuler) {
		this._bAnnuler = _bAnnuler;
	}

	public JButton get_bChoisir() {
		return _bChoisir;
	}

	public void set_bChoisir(JButton _bChoisir) {
		this._bChoisir = _bChoisir;
	}



}
