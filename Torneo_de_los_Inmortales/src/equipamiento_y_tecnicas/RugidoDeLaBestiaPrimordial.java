package equipamiento_y_tecnicas;

import efectos.Daño;

/*
 * Técnica ofensiva, el ataque poderoso de la consigna: cuesta 25 de energía y hace
 * 45 de daño al rival.
 * La usa: la Escuela de la Espada Celestial en el main.
 */
public class RugidoDeLaBestiaPrimordial extends Tecnica{

	public RugidoDeLaBestiaPrimordial (){
		super("Rugido de la Bestia Primordial", 25, TipoTecnica.OFENSIVA, new Daño(45));
	}
}
