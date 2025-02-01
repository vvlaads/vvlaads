package moves;

import ru.ifmo.se.pokemon.*;

public class TailWhip extends StatusMove {
    protected java.lang.String describe(){
        return "использует Tail Whip";
    }
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.DEFENSE, -1);
    }
    public TailWhip(){
        super(Type.NORMAL,0, 100);
    }
}
