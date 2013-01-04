import model.Game;
import vue.FenetrePrincipale;
import controleur.ControleurMenu;
import controleur.ControleurPlateForme;
import controleur.Rules;


public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		
		FenetrePrincipale f = new FenetrePrincipale(game);
		new ControleurMenu(game, f.get_menuJeu(), f);
		Rules rule = new Rules(f._plateForme, game);
		new ControleurPlateForme(f, f._plateForme, rule, game, game.getJ1(), game.getJ2());
	}
}
