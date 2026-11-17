package efectos;

import combatiente.Combatiente;

public class AplicarEfectoTemporal implements Efecto{
    private EfectoTemporal prototipo;

    public AplicarEfectoTemporal(EfectoTemporal efectoTemporal){
        prototipo = efectoTemporal;
    }

	@Override
	public void aplicarEfecto(Combatiente afectado) {
        EfectoTemporal copia = prototipo.copiar();
		afectado.agregarEfectoTemporal(copia);
	}   
}
