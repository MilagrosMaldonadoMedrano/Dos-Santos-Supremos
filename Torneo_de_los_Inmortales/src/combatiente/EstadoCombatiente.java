package combatiente;

public interface EstadoCombatiente {

	boolean puedeActuar();
	boolean puedeSerObjetivo();
	boolean puedeModificarEquipamiento();
	void recibirDanio(Combatiente c,int danio);
}
