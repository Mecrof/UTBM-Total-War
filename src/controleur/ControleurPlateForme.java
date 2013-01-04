package controleur;

import java.awt.Cursor;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.CadreJoueur;
import vue.ChoixNombreEffectif;
import vue.ChoixNouvelleCombinaison;
import vue.FenetrePrincipale;
import vue.PlateForme;
import vue.SplasherFinPartie;

import model.Game;
import model.Joueur;
import model.Salle;

public class ControleurPlateForme extends ComponentAdapter implements ActionListener , ChangeListener, MouseListener, MouseMotionListener{
	
	private FenetrePrincipale fenetre;
	private Game game;
	private Joueur j1, j2;
	private PlateForme plateforme;
	private ChoixNouvelleCombinaison chNouComb;
	private int _joueurCourant;
	private boolean _isConqueteAvecRenforts;
	private boolean _isRenfortsUtilises;
	private boolean _isRedeploiement;
	
	private Rules _rule;
	
	private int _nbEffectifDeplacement;
	
	public ControleurPlateForme(FenetrePrincipale f, PlateForme p, Rules rule, Game g, Joueur j1, Joueur j2) {
		this.fenetre = f;
		this.plateforme = p;
		this._rule = rule;
		this.game = g;
		this.j1 = j1;
		this.j2 = j2;
		this.reinitialiser();
		//this.plateforme.get_champsVisuel().add
		this.plateforme.get_chj1().get_bPasseLeTour().addActionListener(this);
		this.plateforme.get_chj1().get_bDeclin().addActionListener(this);
		this.plateforme.get_chj1().get_sNbJetonsEtudiants().addChangeListener(this);
		this.plateforme.get_chj2().get_bPasseLeTour().addActionListener(this);
		this.plateforme.get_chj2().get_bDeclin().addActionListener(this);
		this.plateforme.get_chj2().get_sNbJetonsEtudiants().addChangeListener(this);
		this.plateforme.get_champsMap().addMouseListener(this);
		this.plateforme.get_champsMap().addMouseMotionListener(this);
		
	}

	public void reinitialiser()
	{
		this._joueurCourant = 1;
		this._isConqueteAvecRenforts = false;
		this._isRenfortsUtilises = false;
		this._isRedeploiement = false;
		this._nbEffectifDeplacement = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try {
			if (e.getSource().equals(plateforme.get_chj1().get_bPasseLeTour()))
			{
				if (!_isConqueteAvecRenforts && !_isRedeploiement && !this.j1.get_listIdSalleOccupee().isEmpty())
				{
					if (j1.get_effectifDispo()>0)
					{
						_isConqueteAvecRenforts = true;
						plateforme.phaseConqueteAvecRenforts();
					}
					else
					{
						_isRedeploiement = true;
						plateforme.phaseRedeploiement();
					}
				}
				else if (_isConqueteAvecRenforts && !this.j1.get_listIdSalleOccupee().isEmpty())
				{
					_isConqueteAvecRenforts = false;
					_isRedeploiement = true;
					_isRenfortsUtilises = false;
					plateforme.phaseRedeploiement();
				}
				else if (_isRedeploiement)
				{
					this._rule.distributionPointVictoire(j1);
					_joueurCourant = 2;
					_isRedeploiement = false;
					this.plateforme.changementDeJoueur();
				}
			}
			else if (e.getSource().equals(plateforme.get_chj2().get_bPasseLeTour()))
			{
				if (!_isConqueteAvecRenforts && !_isRedeploiement && !this.j2.get_listIdSalleOccupee().isEmpty())
				{
					if (j2.get_effectifDispo()>0)
					{
						_isConqueteAvecRenforts = true;
						plateforme.phaseConqueteAvecRenforts();
					}
					else
					{
						_isRedeploiement = true;
						plateforme.phaseRedeploiement();
					}
				}
				else if (_isConqueteAvecRenforts && !this.j2.get_listIdSalleOccupee().isEmpty() )
				{
					_isConqueteAvecRenforts = false;
					_isRedeploiement = true;
					_isRenfortsUtilises = false;
					plateforme.phaseRedeploiement();
				}
				else if (_isRedeploiement)
				{
					int nb = game.get_nombreDeTourEffectue()+1;
					this._rule.distributionPointVictoire(j2);
					if (game.get_nombreDeTour()>=nb && (j1.get_ptVictoire()+j2.get_ptVictoire())<_rule.get_jetonsPtVictoires().size())
					{
						game.set_nombreDeTourEffectue(nb);
						_joueurCourant = 1;
						_isRedeploiement = false;
						this.plateforme.changementDeJoueur();
					}
					else
						throw new Exception("Fin de partie");
				}				
			}
			else if (e.getSource().equals(plateforme.get_chj1().get_bDeclin()))
			{
				chNouComb = new ChoixNouvelleCombinaison(this.plateforme, this.game, _rule, this.j1);
				this.plateforme.get_chj1().get_sNumberModel().setMaximum(this.j1.get_effectifDispo());
				chNouComb = null;
				this.plateforme.get_chj1().get_bDeclin().setEnabled(false);
			}
			else if (e.getSource().equals(plateforme.get_chj2().get_bDeclin()))
			{
				chNouComb = new ChoixNouvelleCombinaison(this.plateforme, this.game, _rule, this.j2);
				this.plateforme.get_chj2().get_sNumberModel().setMaximum(this.j2.get_effectifDispo());
				chNouComb = null;
				this.plateforme.get_chj2().get_bDeclin().setEnabled(false);
			}
		} catch (Exception ex) {
			this.retourVersMenu();
			//this.plateforme.repaint();
		}
		
		this.plateforme.repaint();
	}
	
	private void retourVersMenu()
	{
		j1.set_ptVictoire(_rule.getScoreJoueur(j1));
		j2.set_ptVictoire(_rule.getScoreJoueur(j2));
		if (j1.get_ptVictoire()>=j2.get_ptVictoire())
			new SplasherFinPartie(this.plateforme, this.j1, this.j2, this.game.isAntialiasing());
		else 
			new SplasherFinPartie(this.plateforme, this.j2, this.j1, this.game.isAntialiasing());
		this.game.reinitialiser();
		this.reinitialiser();
		this.fenetre.get_menuJeu().mettreMenuPrincipal();
		this.fenetre.get_menuJeu().reinitialiser();
		this.fenetre.changerVersMenu();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
			ArrayList<Salle> listSalleAttaquable;
			ArrayList<Salle> listSallePossedee;
			int effectif = 0;
			if (_isConqueteAvecRenforts)
				effectif++;
			try{
				if (_joueurCourant == 1)
				{
					effectif += (Integer)this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue();
					//listSalleAttaquable = this.game.getSalleAttaquable(j1, effectif);
					listSalleAttaquable = this._rule.getSalleAttaquable(j1, effectif);
					listSallePossedee = this.game.getSalles(j1.get_listIdSalleOccupee());
				}
				else
				{
					effectif += (Integer)this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue();
					//listSalleAttaquable = this.game.getSalleAttaquable(j2, effectif);
					listSalleAttaquable = this._rule.getSalleAttaquable(j2, effectif);
					listSallePossedee = this.game.getSalles(j2.get_listIdSalleOccupee());
				}
				//System.out.println("lAt: "+listSalleAttaquable);
				if (listSallePossedee != null)
					this.plateforme.get_champsMap().dessinerSallePossedee(listSallePossedee);
				else
					this.plateforme.get_champsMap().dessinerSallePossedee(new ArrayList<Salle>());
				if (listSalleAttaquable != null)
					this.plateforme.get_champsMap().dessinerSalleAttaquable(listSalleAttaquable);
				else
					this.plateforme.get_champsMap().dessinerSalleAttaquable(new ArrayList<Salle>());
				this.plateforme.repaint();//this.plateforme.get_champsMap().repaint();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}

		//this.plateforme.get_champsMap().afficherSalleAttaquable(listSalle);
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if (e.getX()>this.plateforme.getWidth()-26 && e.getY()<25 && e.getX()<this.plateforme.getWidth()-4 && e.getY()>4)
			this.retourVersMenu();
		if (!_isRedeploiement)
		{
			ArrayList<Salle> listSalleAttaquable = this.plateforme.get_champsMap().getSalleAttaquable();
			if (!listSalleAttaquable.isEmpty())
			{
				for (int i = 0; i < this.plateforme.get_champsMap().get_polySalle().length; i++) {
			
					if (this.plateforme.get_champsMap().get_polySalle()[i].contains(e.getX(), e.getY())) {
						//<RULES>
						int idSalle = listSalleAttaquable.get(i).get_id();
						MoveAttaque mv;
						
						int nombreEngage;
						/*
						SpinnerNumberModel sm;
						*/
						if(this._joueurCourant == 1)
						{
							if (!_isConqueteAvecRenforts)
							{
								nombreEngage = (Integer)(this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue());
								mv = new MoveAttaque( MoveType.ConqueteSalle, this.j1, idSalle, nombreEngage);
								this.plateforme.get_chj1().get_bDeclin().setEnabled(true);
							}
							else
							{
								_isRenfortsUtilises = true;
								nombreEngage = (Integer)(this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue()) - 1;
								mv = new MoveAttaque( MoveType.ConqueteSalleRenfort, this.j1, idSalle, nombreEngage);
							}
							
							this.plateforme.get_chj1().get_bPasseLeTour().setEnabled(true);
							/*
							
							this._game.getJ1().set_effectifDispo(this._game.getJ1().get_effectifDispo()-nombreEngage);
							this._game.getJ1().get_listIdSalleOccupee().add(s.get_id());
							sm = (SpinnerNumberModel) this.plateforme.get_chj1().get_sNbJetonsEtudiants().getModel();
							sm.setMaximum(this._game.getJ1().get_effectifDispo());
							if (this._game.getJ2().get_listIdSalleOccupee().contains(s.get_id()))
								this._game.getJ2().get_listIdSalleOccupee().remove((Object)s.get_id());
								*/
						}
						else
						{
							
							if (!_isConqueteAvecRenforts )
							{
								nombreEngage = (Integer)(this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue());
								mv = new MoveAttaque(MoveType.ConqueteSalle, this.j2, idSalle, nombreEngage);
								this.plateforme.get_chj2().get_bDeclin().setEnabled(true);
							}
							else
							{
								_isRenfortsUtilises = true;
								nombreEngage = (Integer)(this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue()) - 1;
								mv = new MoveAttaque(MoveType.ConqueteSalleRenfort, this.j2, idSalle, nombreEngage);
							}
							this.plateforme.get_chj2().get_bPasseLeTour().setEnabled(true);
							/*
							nombreEngage = (Integer)(this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue());
							this._game.getJ2().set_effectifDispo(this._game.getJ2().get_effectifDispo()-nombreEngage);
							this._game.getJ2().get_listIdSalleOccupee().add(s.get_id());
							sm = (SpinnerNumberModel) this.plateforme.get_chj2().get_sNbJetonsEtudiants().getModel();
							sm.setMaximum(this._game.getJ2().get_effectifDispo());
							if (this._game.getJ1().get_listIdSalleOccupee().contains(s.get_id()))
								this._game.getJ1().get_listIdSalleOccupee().remove((Object)s.get_id());
								*/
						}
						this._rule.action(mv);
						/*
						s.set_nombreOccupant(nombreEngage);
						sm.setValue(0);
						*/
						//</RULES>
						
						
						//System.out.println("Vous avez cliqué sur la salle : "
						//		+ this.plateforme.get_champsMap().getSalleAttaquable().get(i).get_id());
					}
				}
				this.plateforme.repaint();
			}
		}
		else if (_isRedeploiement)
		{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				ArrayList<Salle> listSallePossedees = this.plateforme.get_champsMap().getSallePossedee();
				for (int i = 0; i < this.plateforme.get_champsMap().get_polySalle().length; i++) {
					if (this.plateforme.get_champsMap().get_polySalle()[i].contains(e.getX(), e.getY())) {
						Salle salle = listSallePossedees.get(i);
						if (this._nbEffectifDeplacement==0)
						{
							if (salle.get_nombreOccupant()>1)
							{
								ChoixNombreEffectif ch = new ChoixNombreEffectif(salle.get_nombreOccupant()-1, e.getXOnScreen(), e.getYOnScreen(), this.game.isAntialiasing());
								if (ch.isOk())
								{
									this._nbEffectifDeplacement = ch.get_effectifChoisit();
									Joueur joueur;
									if (_joueurCourant == 1)
									{
										joueur = j1;
										this.plateforme.get_chj1().get_bPasseLeTour().setEnabled(false);
									}
									else
									{
										joueur = j2;
										this.plateforme.get_chj2().get_bPasseLeTour().setEnabled(false);
									}
									MoveRedeploiement mv = new MoveRedeploiement(MoveType.Redeploiement, salle.get_id(), this._nbEffectifDeplacement*(-1));
									this.plateforme.get_champsMap().set_effectifEnDeplacement(_nbEffectifDeplacement);
									this._rule.action(mv);
								}
							}
						}
						else
						{
							Joueur joueur;
							if (_joueurCourant == 1)
							{
								joueur = j1;
								this.plateforme.get_chj1().get_bPasseLeTour().setEnabled(true);
							}
							else
							{
								joueur = j2;
								this.plateforme.get_chj2().get_bPasseLeTour().setEnabled(true);
							}
							MoveRedeploiement mv = new MoveRedeploiement(MoveType.Redeploiement, salle.get_id(), this._nbEffectifDeplacement);
							this._nbEffectifDeplacement = 0;
							this.plateforme.get_champsMap().set_effectifEnDeplacement(0);
							this._rule.action(mv);
						}
						this.plateforme.repaint();
					}
				}
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
				
				if(this._nbEffectifDeplacement==0)
				{
					Joueur joueur;
					if (_joueurCourant == 1)
						joueur = j1;
					else
						joueur = j2;
					if (joueur.get_effectifDispo()>0)
					{
						ChoixNombreEffectif ch = new ChoixNombreEffectif(joueur.get_effectifDispo(), e.getXOnScreen(), e.getYOnScreen(), this.game.isAntialiasing());
						if (ch.isOk())
						{
							this._nbEffectifDeplacement = ch.get_effectifChoisit();
							if (_joueurCourant == 1)
							{
								this.plateforme.get_chj1().get_bPasseLeTour().setEnabled(false);
								this.j1.set_effectifDispo(this.j1.get_effectifDispo()-_nbEffectifDeplacement);
								this.plateforme.get_chj1().get_sNumberModel().setMaximum(this.j1.get_effectifDispo());
								//this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue()
							}
							else
							{
								this.plateforme.get_chj2().get_bPasseLeTour().setEnabled(false);
								this.j2.set_effectifDispo(this.j2.get_effectifDispo()-_nbEffectifDeplacement);
								this.plateforme.get_chj2().get_sNumberModel().setMaximum(this.j2.get_effectifDispo());
							}
							//MoveRedeploiement mv = new MoveRedeploiement(MoveType.Redeploiement, salle.get_id(), this._nbEffectifDeplacement*(-1));
							this.plateforme.get_champsMap().set_effectifEnDeplacement(_nbEffectifDeplacement);
							//this._rule.action(mv);
						}
					}
				}
				else
				{
					if (_joueurCourant == 1)
					{
						this.plateforme.get_chj1().get_bPasseLeTour().setEnabled(true);
						this.j1.set_effectifDispo(this.j1.get_effectifDispo()+_nbEffectifDeplacement);
						this.plateforme.get_chj1().get_sNumberModel().setMaximum(this.j1.get_effectifDispo());
						//this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue()
					}
					else
					{
						this.plateforme.get_chj2().get_bPasseLeTour().setEnabled(true);
						this.j2.set_effectifDispo(this.j2.get_effectifDispo()+_nbEffectifDeplacement);
						this.plateforme.get_chj2().get_sNumberModel().setMaximum(this.j2.get_effectifDispo());
					}
					this.plateforme.get_champsMap().set_effectifEnDeplacement(0);
				}
			}
		}
			//this.plateforme.get_champsMap().repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (this.plateforme.get_champsMap().get_polySalle()!=null)
		{
			boolean surligner = false;
			for (int l = 0; l < this.plateforme.get_champsMap().get_polySalle().length; l++) {
				
				if (this.plateforme.get_champsMap().get_polySalle()[l].contains(e.getX(), e.getY())) 
				{
					surligner = true;
					this.plateforme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					this.plateforme.get_champsMap().set_polySurligner(this.plateforme.get_champsMap().get_polySalle()[l]);
				}
			}
			if (!surligner)
			{
				this.plateforme.setCursor(Cursor.getDefaultCursor());
				this.plateforme.get_champsMap().set_polySurligner(null);
			}
			if (_isRedeploiement)
			{
				if (_nbEffectifDeplacement > 0)
				{
					this.plateforme.get_champsMap().setPosCurseurX(e.getX());
					this.plateforme.get_champsMap().setPosCurseurY(e.getY());
				}
			}
			this.plateforme.repaint();
			//this.plateforme.get_champsMap().repaint();
		}
		if (e.getX()>this.plateforme.getWidth()-26 && e.getY()<25 && e.getX()<this.plateforme.getWidth()-4 && e.getY()>4)
			this.plateforme.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//else
		//	this.plateforme.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("toto");
	}


}
