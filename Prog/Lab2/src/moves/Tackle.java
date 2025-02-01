package moves;

import ru.ifmo.se.pokemon.*;

public class Tackle extends PhysicalMove {
    protected java.lang.String describe(){
        return "использует Tackle";
    }
    public Tackle(){
        super(Type.NORMAL,40, 100);
    }
}
