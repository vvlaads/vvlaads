package moves;

import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {
    protected java.lang.String describe(){
        return "использует Aerial Ace";
    }
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }
    public AerialAce(){
        super(Type.FLYING,60, 100);
    }
}
