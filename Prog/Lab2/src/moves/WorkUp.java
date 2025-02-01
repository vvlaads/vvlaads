package moves;

import ru.ifmo.se.pokemon.*;
public class WorkUp extends StatusMove{
    protected java.lang.String describe(){
        return "использует Work Up";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, 1);
        p.setMod(Stat.ATTACK, 1);
    }
    public WorkUp(){
        super(Type.NORMAL,0, 1);
    }
}
