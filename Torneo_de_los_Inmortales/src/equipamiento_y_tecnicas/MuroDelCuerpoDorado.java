package equipamiento_y_tecnicas;

import efectos.Reduccion;

public class MuroDelCuerpoDorado extends Tecnica{
    
    public MuroDelCuerpoDorado(){
        super("Muro del Cuerpo Dorado", 40, TipoTecnica.DEFENSIVA, new Reduccion(20));
    }
}
