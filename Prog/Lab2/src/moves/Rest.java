package moves;

import ru.ifmo.se.pokemon.*;
public class Rest extends StatusMove{
    protected java.lang.String describe(){
        return "использует Rest";
    }
    protected void applySelfEffects(Pokemon p){
        Effect rest = new Effect();
        rest.stat(Stat.HP, 6);
        Effect.sleep(p);
        rest.turns(2);
    }
    public Rest(){
        super(Type.PSYCHIC,0, 1);
    }
}
