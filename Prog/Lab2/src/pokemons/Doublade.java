package pokemons;

import moves.*;
public class Doublade extends Honedge{
    public Doublade(String name, int level){
        super(name, level);
        super.setStats(59, 110, 150, 45, 49, 35);
        super.addMove(new Tackle());
    }
}