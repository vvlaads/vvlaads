package pokemons;

import moves.SwordsDance;
import ru.ifmo.se.pokemon.*;
import moves.IronHead;
public class Honedge extends Pokemon{
    public Honedge(String name, int level){
        super(name, level);
        super.setStats(45, 80, 100, 35, 37, 28);
        super.setType(Type.STEEL, Type.GHOST);
        super.addMove(new SwordsDance());
        super.addMove(new IronHead());
    }
}