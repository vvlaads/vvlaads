package moves;

import ru.ifmo.se.pokemon.*;
public class DoubleTeam extends StatusMove{
    protected java.lang.String describe(){
        return "использует Double Team";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.EVASION, 1);
    }
    public DoubleTeam(){
        super(Type.NORMAL,0, 1);
    }
}
