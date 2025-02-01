package moves;

import ru.ifmo.se.pokemon.*;
public class Confide extends StatusMove{
    protected java.lang.String describe(){
        return "использует Confide";
    }
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
    public Confide(){
        super(Type.NORMAL,0, 1);
    }
}
