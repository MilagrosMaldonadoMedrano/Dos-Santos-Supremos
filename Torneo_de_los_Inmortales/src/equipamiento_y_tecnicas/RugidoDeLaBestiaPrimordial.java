package equipamiento_y_tecnicas;

import efectos.Daño;

public class RugidoDeLaBestiaPrimordial extends Tecnica{

    public RugidoDeLaBestiaPrimordial (){
        super("Rugido de la Bestia Primordial", 25, TipoTecnica.OFENSIVA, new Daño(45));
    }
}
