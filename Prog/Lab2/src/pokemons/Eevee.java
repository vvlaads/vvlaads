package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;
public class Eevee extends Pokemon{
	public Eevee(String name, int level){
        super(name, level);
        super.setStats(55, 55, 50, 45, 65, 55);
        super.setType(Type.NORMAL);
        super.addMove(new Rest());
        super.addMove(new WorkUp());
        super.addMove(new BabyDollEyes());
    }
}