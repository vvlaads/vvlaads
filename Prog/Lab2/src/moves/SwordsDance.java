package moves;

import ru.ifmo.se.pokemon.*;
public class SwordsDance extends StatusMove {
    protected java.lang.String describe(){
        return "использует Swords Dance";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.ATTACK, 2);
    }
    public SwordsDance(){
        super(Type.NORMAL,0, 1);
    }
}
