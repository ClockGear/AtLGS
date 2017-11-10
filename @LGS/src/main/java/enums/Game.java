package enums;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
public enum Game {
    MTG("MtG"),
    YUGIOH("Yu-Gi-Oh"),
    POKEMON("Pokémon");

    private String game;

    Game(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }
}
