package pokemons;

import moves.*;
public class AegislashBlade extends Doublade{
    public AegislashBlade(String name, int level){
        super(name, level);
        super.setStats(60, 140, 50, 140, 50, 60);
        super.addMove(new AerialAce());
    }
}