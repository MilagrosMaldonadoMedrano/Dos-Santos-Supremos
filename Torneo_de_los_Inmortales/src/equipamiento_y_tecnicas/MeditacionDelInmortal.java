package equipamiento_y_tecnicas;

import efectos.Curacion;

public class MeditacionDelInmortal extends Tecnica{
    public MeditacionDelInmortal(){
        super("Meditacion del Inmortal", 20, TipoTecnica.RECUPERACION, new Curacion(30));
    }
}
