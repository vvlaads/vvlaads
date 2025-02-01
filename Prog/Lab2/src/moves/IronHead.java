package moves;

import ru.ifmo.se.pokemon.*;

public class IronHead extends PhysicalMove {
    protected java.lang.String describe(){
        return "использует Iron Head";
    }
    protected void applyOppEffects(Pokemon p){
        Effect iron_head = new Effect().chance(0.3);
        iron_head.flinch(p);

    }
    public IronHead(){
        super(Type.STEEL,80, 100);
    }

}
