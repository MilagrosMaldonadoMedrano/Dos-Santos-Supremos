package equipamiento_y_tecnicas;

import efectos.Reduccion;

/*
 * Técnica defensiva: cuesta 40 de energía. Su efecto Reduccion activa una defensa en el
 * que la usa, que reduce 20 el daño recibido durante el resto del combate.
 * La usa: el Monasterio del Cuerpo Dorado en el main. El criterio la elige si no hay
 * una defensa activa.
 */
public class MuroDelCuerpoDorado extends Tecnica{

	public MuroDelCuerpoDorado(){
		super("Muro del Cuerpo Dorado", 40, TipoTecnica.DEFENSIVA, new Reduccion(20));
	}
}
