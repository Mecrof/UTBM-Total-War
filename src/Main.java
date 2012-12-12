import model.Game;
import controleur.ControleurMenu;
import controleur.ControleurPlateForme;
import vue.FenetrePrincipale;


public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		FenetrePrincipale f = new FenetrePrincipale(game);
		new ControleurMenu(game, f.get_menuJeu(), f);
		new ControleurPlateForme(f._plateForme, game, game.getJ1(), game.getJ2());
	}
}
