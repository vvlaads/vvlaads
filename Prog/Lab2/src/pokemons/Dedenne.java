package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Dedenne extends Pokemon{
	public Dedenne(String name, int level){
		super(name, level);
		super.setStats(67, 58, 57, 81, 67, 101);
		super.setType(Type.ELECTRIC, Type.FAIRY);
		super.addMove(new DoubleTeam());
		super.addMove(new Rest());
		super.addMove(new Confide());
		super.addMove(new TailWhip());
	}
}