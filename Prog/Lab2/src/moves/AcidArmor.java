package moves;

import ru.ifmo.se.pokemon.*;

public class AcidArmor extends StatusMove {
    protected java.lang.String describe(){
        return "использует Acid Armor";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.DEFENSE, 2);
    }
    public AcidArmor(){
        super(Type.POISON,0, 1);
    }
}
