package moves;

import ru.ifmo.se.pokemon.*;

public class BabyDollEyes extends StatusMove {
    protected java.lang.String describe(){
        return "использует Baby-Doll Eyes";
    }
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, -1);
    }
    public BabyDollEyes(){
        super(Type.FAIRY,0, 100, 1, 0);
    }
}
