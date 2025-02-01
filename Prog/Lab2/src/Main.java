import pokemons.*;
import ru.ifmo.se.pokemon.*;
public class Main {
	public static void main(String[] args){
		Battle b = new Battle();
		Dedenne p1 = new Dedenne("Dedenne", -1);
		Eevee p2 = new Eevee("Eevee", 1);
		Vaporeon p3 = new Vaporeon("Vaporeon", 1);
		Honedge p4 = new Honedge("Honedge", 1);
		Doublade p5 = new Doublade("Doublade", 1);
		AegislashBlade p6 = new AegislashBlade("Aegislash-Blade", 1);

		b.addAlly(p4);
		b.addAlly(p1);
		b.addAlly(p6);

		b.addFoe(p3);
		b.addFoe(p2);
		b.addFoe(p5);
		b.go();
	}
}