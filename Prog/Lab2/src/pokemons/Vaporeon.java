package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;
public class Vaporeon extends Eevee{
    public Vaporeon(String name, int level){
        super(name, level);
        super.setStats(130, 65, 60, 110, 95, 65);
        super.setType(Type.WATER);
        super.addMove(new AcidArmor());
    }
}