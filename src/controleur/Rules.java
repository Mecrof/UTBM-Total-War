package controleur;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.SpinnerNumberModel;

import vue.PlateForme;

import model.Game;
import model.Joueur;
import model.Salle;
import model.Specialite;
import model.TypeEtudiant;
import model.TypeSpecialite;

public class Rules {
	//private static final int MAX_NB_JOUEURS = 5;
	//private static final int MIN_NB_JOUEURS = 2;
	private static final int MIN_NB_JETON_REQUIS = 2;
	private Game _game;
	private PlateForme plateforme;
	public GamePhase CurrentPhase;
	private ArrayList<Integer> _jetonsPtVictoires;

	public Rules(PlateForme p, Game game)
	{
		this._game= game;
		this.plateforme = p;
		initialiseJetonsPtVictoire();
	}
	
	private void initialiseJetonsPtVictoire()
	{
		_jetonsPtVictoires = new ArrayList<Integer>();
		for(int i=0; i <30; i++)
		{
			_jetonsPtVictoires.add(10);
		}
		for(int i=0; i <24; i++)
		{
			_jetonsPtVictoires.add(5);
		}
		for(int i=0; i <20; i++)
		{
			_jetonsPtVictoires.add(3);
		}
		for(int i=0; i <35; i++)
		{
			_jetonsPtVictoires.add(1);
		}
	}
	
	public Integer retirerUnJetonVictoire(int index)
	{
		Integer int1 = new Integer(_jetonsPtVictoires.get(index));
		_jetonsPtVictoires.remove(index);
		return int1;
	}
	
	public int getScoreJoueur(Joueur joueur){//, ArrayList<Integer> listSalleOccupee) {
		int score = 0;
		int index;
		for (int i = 0; i < joueur.get_ptVictoire(); ++i)
		{
			index = (int) (Math.random() * (_jetonsPtVictoires.size()));
			score += this.retirerUnJetonVictoire(index);
		}
		return score;
		/*ArrayList<Salle> salles = this._game.getSalles(currentPlayer.get_listIdSalleOccupee());
		for (Salle salle : salles) {
			//ajoute des points victoires par salle occupée
			int index = (int) (Math.random() * (_jetonsPtVictoires.size()));
			currentPlayer.set_ptVictoire(retirerUnJetonVictoire(index));
			//ajoute des points victoires supplémentaires selon les atouts de la salle
			//ajoute des points victoires supplémentaires selon les atouts de la spécialité DE l'étudiant
			//ajoute des points victoires supplémentaires selon les atous de la spécialité ASSOCIé A l'étudiant
		}*/
	}
	
	private void ajoutePointVictoireAvecAtoutSalle(Joueur joueur, TypeEtudiant typEtu, Specialite atoutSalle)
	{
		if (atoutSalle == typEtu.get_type())
			joueur.set_ptVictoire(joueur.get_ptVictoire() + 1);
	}
	
	private void ajoutePointVictoireSpecialiteAssocie(Joueur joueur, TypeSpecialite typeSpe, TypeEtudiant typEtu)
	{
		if (typeSpe.get_type() == typEtu.get_type())
		{
			Specialite spe = typeSpe.get_type();
			if (spe.equals(Specialite.GEEK))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.MANAGEMENT))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.MECANO))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.ELEC))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.GRIBOUILLEUR))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.PHYSIQUECHIMIE))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);
			else if (spe.equals(Specialite.INTELLO))
				joueur.set_ptVictoire(joueur.get_ptVictoire() + 3);

			// RESTE A FAIRE POUR LES NOUVELLES SPE !!
			
		}
	}
	
	public void distributionPointVictoire( Joueur joueur )
	{
		TypeEtudiant typEtu = (TypeEtudiant) this._game.getEtudiant(joueur.get_idEtudiantActif());
		TypeSpecialite typeSpe = (TypeSpecialite) this._game.getSpecialite(joueur.get_idSpecialiteActif());
		ArrayList<Salle> salles = this._game.getSalles(joueur.get_listIdSalleOccupee());
		
		joueur.set_ptVictoire(joueur.get_ptVictoire() + salles.size());
		
		for (Salle salle : salles)
		{
			//ajoute des points victoires supplémentaires selon les atouts de la salle
			if (salle.get_nombreOccupant()>0)
				this.ajoutePointVictoireAvecAtoutSalle(joueur, typEtu, salle.get_atoutSalle());
			System.out.println("salle.get_atoutSalle():"+salle.get_atoutSalle());
		}
		//ajoute des points victoires supplémentaires selon les atous de la spécialité ASSOCIé A l'étudiant
		this.ajoutePointVictoireSpecialiteAssocie(joueur, typeSpe, typEtu);
	}
	
	public void action(Move move)
	{
		switch(move.get_type())
		{
			case ConqueteSalle:
				this.attaqueSalle( (MoveAttaque) move);
				break;
			case Attaque:
				break;
			case ConqueteSalleRenfort:
				this.attaqueAvecRenforts( (MoveAttaque) move);
				break;
			case Redeploiement:
				this.redeploiement( (MoveRedeploiement) move);
				break;
			case PasserEnDeclin:
				this.passerEnDeclin( (MoveDeclin) move);
				break;
		
		default:
			return;
		}
	}
	
	private void attaqueAvecRenforts( MoveAttaque mv )
	{
		int d = lancerDeRenfort();
		if (d > 0)
		{
			//mv.set_nbJetons(mv.get_nbJetons()+d);
			mv.set_nbJetons(mv.get_nbJetons()+1);
			this.attaqueSalle(mv);
		}
		if (mv.get_joueur().equals(this._game.getJ1()))
			this.plateforme.get_chj1().get_sNbJetonsEtudiants().setEnabled(false);
		else
			this.plateforme.get_chj2().get_sNbJetonsEtudiants().setEnabled(false);
	}
	
	private void passerEnDeclin(MoveDeclin mv)
	{
		Joueur joueur = mv.get_joueur();
		int nouveauId = mv.get_nouveauIdEtudiant();
		joueur.set_idEtudiantInactif(joueur.get_idEtudiantActif());
		joueur.set_idEtudiantActif(nouveauId);
		joueur.set_effectifDispo(10);
		ArrayList<Integer> listIdAretirer = new ArrayList<Integer>();
		Salle s;
		for (Integer i : joueur.get_listIdSalleOccupee())
		{
			s = _game.getSalle(i);
			if (s.get_nombreOccupant()==0)
				listIdAretirer.add(i);
			s.set_nombreOccupantEnDeclin(s.get_nombreOccupant());
			s.set_nombreOccupant(0);
		}
		for (Integer j : listIdAretirer)
			joueur.get_listIdSalleOccupee().remove((Object)j);
	}
	/*
	public void GetNextPlayer() throws Exception {
		throw new Exception("NOT IMPLEMENTED GetNextPlayer");
	}
*/

	private void redeploiement(MoveRedeploiement mv)
	{
		int idSalle = mv.get_idSalle();
		int nbJetons = mv.get_nbJetons();
		Salle s = this._game.getSalle(idSalle);
		s.set_nombreOccupant(s.get_nombreOccupant()+nbJetons);
	}
	
	public boolean isAttaquable(Salle salle, int effectifEngage)
	{
		if (effectifEngage >= MIN_NB_JETON_REQUIS)
		{
			if (!salle.get_isOccupe())
				return true;
			else if ( (salle.get_nombreOccupant()+salle.get_nombreOccupantEnDeclin()) < effectifEngage)
				return true;
		}
		return false;
	}
	
	
	private ArrayList<Salle> calculSalleAttaquable(Joueur joueur, int effectifEngage)
	{
		ArrayList<Salle> listSalle = new ArrayList<Salle>();
		for (Integer idSalle : joueur.get_listIdSalleOccupee()) {
			Salle salle;
			if ( (salle = this._game.getSalle(idSalle.intValue())) != null)
			{
				if (effectifEngage >= MIN_NB_JETON_REQUIS)
				{
					for (int i = 0; i < salle.get_listeSallesAdjacentes().size(); ++i)
					{
						Salle s = this._game.getSalle(salle.get_listeSallesAdjacentes().get(i));
							if (!joueur.get_listIdSalleOccupee().contains(s.get_id()))
								if (!listSalle.contains(s))
								{
									if (this.isAttaquable(s, effectifEngage))
										listSalle.add(s);
								}
					}
				}
			}
		}
		if (listSalle.isEmpty())
			return null;
		else
			return listSalle;
	}
	
	
	public ArrayList<Salle> getSalleAttaquable(Joueur j, int effectif) throws Exception
	{
		ArrayList<Salle> listSalle = new ArrayList<Salle>();
		if (!j.get_listIdSalleOccupee().isEmpty())
		{
			listSalle = this.calculSalleAttaquable(j, effectif);
		}
		else
		{
			try{
			for (Integer idSalle : this._game.get_batiment().get_listePremiereSalle())
				if (this.isAttaquable(this._game.getSalle(idSalle.intValue()), effectif))
					listSalle.add(this._game.getSalle(idSalle.intValue()));
			}catch (Exception e)
			{
				throw new Exception("Erreur : Aucune premiere salle sur la map !");
			}
		}
		return listSalle;
	}
	
	private void attaqueSalle(MoveAttaque mv)
	{
		Joueur joueur = mv.get_joueur();
		int idSalle = mv.get_idSalle();
		int nombreEngage = mv.get_nbJetons();
		//Salle s = listSalleAttaquable.get(i);
		Salle s = this._game.getSalle(idSalle);
		SpinnerNumberModel sm;
		/*int d = 0;
		if (nombreEngage == (s.get_nombreOccupant()+s.get_nombreOccupantEnDeclin()) )
		{
			d = lancerDeRenfort();
			System.out.println("dé : "+d);
			nombreEngage += d;
		}
		if (nombreEngage > (s.get_nombreOccupant()+s.get_nombreOccupantEnDeclin()) )
		{
			nombreEngage -= d;*/
			if(this._game.getJ1().equals(joueur))
			{
				this._game.getJ1().set_effectifDispo(this._game.getJ1().get_effectifDispo()-nombreEngage);
				this._game.getJ1().get_listIdSalleOccupee().add(s.get_id());
				sm = (SpinnerNumberModel) this.plateforme.get_chj1().get_sNbJetonsEtudiants().getModel();
				sm.setMaximum(this._game.getJ1().get_effectifDispo());
				
				if (this._game.getJ2().get_listIdSalleOccupee().contains(s.get_id()))
				{
					if (s.get_nombreOccupant()>0)
					{
						this._game.getJ2().set_effectifDispo(this._game.getJ2().get_effectifDispo()+s.get_nombreOccupant()-1);
						this.plateforme.get_chj2().get_sNumberModel().setMaximum(this._game.getJ2().get_effectifDispo());
					}
					this._game.getJ2().get_listIdSalleOccupee().remove((Object)s.get_id());
				}
			}
			else
			{
				this._game.getJ2().set_effectifDispo(this._game.getJ2().get_effectifDispo()-nombreEngage);
				this._game.getJ2().get_listIdSalleOccupee().add(s.get_id());
				sm = (SpinnerNumberModel) this.plateforme.get_chj2().get_sNbJetonsEtudiants().getModel();
				sm.setMaximum(this._game.getJ2().get_effectifDispo());
				
				if (this._game.getJ1().get_listIdSalleOccupee().contains(s.get_id()))
				{
					if (s.get_nombreOccupant()>0)
					{
						this._game.getJ1().set_effectifDispo(this._game.getJ1().get_effectifDispo()+s.get_nombreOccupant()-1);
						this.plateforme.get_chj1().get_sNumberModel().setMaximum(this._game.getJ1().get_effectifDispo());
					}
					this._game.getJ1().get_listIdSalleOccupee().remove((Object)s.get_id());
				}
			}
			s.set_isOccupe(true);
			s.set_nombreOccupant(nombreEngage);
			s.set_nombreOccupantEnDeclin(0);
			sm.setValue(0);
		//}
	}
	
	public int lancerDeRenfort()
	{
		//[ 0, 0, 0, 1, 2, 3 ] = dé
		int r = (int) (Math.random() * 6);
		if ( r == 3 )
			return 1;
		else if ( r == 4 )
			return 2;
		else if ( r == 5 )
			return 3;
		return 0;
	}

	public ArrayList<Integer> get_jetonsPtVictoires() {
		return _jetonsPtVictoires;
	}

	public void set_jetonsPtVictoires(ArrayList<Integer> _jetonsPtVictoires) {
		this._jetonsPtVictoires = _jetonsPtVictoires;
	}
		
}
