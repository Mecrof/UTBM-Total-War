package controleur;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vue.CadreJoueur;
import vue.PlateForme;

import model.Game;
import model.Joueur;
import model.Salle;

public class ControleurPlateForme implements ActionListener , ChangeListener, MouseListener, MouseMotionListener{
	
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
		this.plateforme.get_champsMap().addMouseListener(this);
		this.plateforme.get_champsMap().addMouseMotionListener(this);
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
		ArrayList<Salle> listSalleAttaquable;
		ArrayList<Salle> listSallePossedee;
		int effectif;
		try{
			if (_joueurCourant == 1)
			{
				effectif = (int)this.plateforme.get_chj1().get_sNbJetonsEtudiants().getValue();
				listSalleAttaquable = this.game.getSalleAttaquable(j1, effectif);
				listSallePossedee = this.game.getSalles(j1.get_listIdSalleOccupee());
			}
			else
			{
				effectif = (int)this.plateforme.get_chj2().get_sNbJetonsEtudiants().getValue();
				listSalleAttaquable = this.game.getSalleAttaquable(j2, effectif);
				listSallePossedee = this.game.getSalles(j2.get_listIdSalleOccupee());
			}
			System.out.println("lAt: "+listSalleAttaquable);
			if (listSallePossedee != null)
				this.plateforme.get_champsMap().dessinerSallePossedee(listSallePossedee);
			else
				this.plateforme.get_champsMap().dessinerSallePossedee(new ArrayList<Salle>());
			if (listSalleAttaquable != null)
				this.plateforme.get_champsMap().dessinerSalleAttaquable(listSalleAttaquable);
			else
				this.plateforme.get_champsMap().dessinerSalleAttaquable(new ArrayList<Salle>());
			this.plateforme.get_champsMap().repaint();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		//this.plateforme.get_champsMap().afficherSalleAttaquable(listSalle);
	}
	
	public void mouseClicked(MouseEvent e) {
		try
		{
			for (int l = 0; l < this.plateforme.get_champsMap().get_polySalle().length; l++) {
		
				if (this.plateforme.get_champsMap().get_polySalle()[l].contains(e.getX(), e.getY())) {
					System.out.println("Vous avez cliqué sur la salle : "
							+ this.plateforme.get_champsMap().getSalleAttaquable().get(l).get_id());
				}
			}
			//this.plateforme.get_champsMap().repaint();
		}
		catch (NullPointerException n){} 
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
			this.plateforme.get_champsMap().repaint();
		}
		
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


}
