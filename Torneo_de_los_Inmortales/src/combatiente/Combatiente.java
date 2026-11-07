
package combatiente;
import java.util.List;
import java.util.Objects;

import equipamiento_y_tecnicas.Equipamiento;


public abstract class Combatiente {
	
	public static final int VIDA_INICIAL = 100; //todos arrancan con esta vida
    public static final int ENERGIA_INICIAL = 40;
    public static final int MAX_EQUIPAMIENTO = 3;
    public static final int USOS_POR_TECNICA = 5;
    public static final int DANIO_ATAQUE_BASICO = 10;
	
	int identificador;
	String nombre;
	int vidaMaxima;	//pude ser mayor a la vida inicial!
	int vidaActual;
	int energiaMaxima;
	int energiaActual;
	private int danioExtra;          // lo suman las armas
	private int reduccionEquipo;     // lo suman las armaduras
	private int reduccionDefensiva;  // lo activa una tecnica defensiva
	List<Equipamiento> equipamiento;
	EstadoCombatiente estado;
	//Tecnicas
	//usos por tecnicas
	//estado del combatiente
	//efectos activos
	//criterio de accion criterio;
	
	
	
	public Combatiente(int identificador,String nombre) {
		this.identificador=identificador;
		this.nombre=nombre;
		this.vidaMaxima=VIDA_INICIAL;
		this.vidaActual=VIDA_INICIAL;
		this.energiaMaxima=ENERGIA_INICIAL;
		this.energiaActual=ENERGIA_INICIAL;
		
		this.estado = new Preparado();
	}
	
	
	
	
	public int getVidaActual() {
		return vidaActual;
	}
	public void setVidaActual(int vidaActual) {
		this.vidaActual = vidaActual;
	}
	public int getEnergiaActual() {
		return energiaActual;
	}
	public void setEnergiaActual(int energiaActual) {
		this.energiaActual = energiaActual;
	}
	public int getIdentificador() {
		return identificador;
	}
	public String getNombre() {
		return nombre;
	}
	public int getVidaMaxima() {
		return vidaMaxima;
	}
	public int getEnergiaMaxima() {
		return energiaMaxima;
	}
	
	
	//--------------estos los sobreescriben los tipos de combatientes---------
	protected int calcularDanioAtaque(int danioBase) {		
		return danioBase;
	}
	
	protected int calcularDanioRecibido(int danioBase) {
		return danioBase;
	}
	
	protected int calcularCostoEnergia(int energiaBase) {
		return energiaBase;
	}
	
	
	
	//------------------------------------------------------------------------
	//---------------------danio----------------------------------------------
	
	
	public int infligirDanio(int base) {
		return calcularDanioAtaque(base + danioExtra);
	}
	
	public void recibirDanio(int danio) {
		if(danio <0) {
			throw new IllegalArgumentException("El daño no puede ser negativo");
		}
		int danioFinal = this.calcularDanioRecibido(danio) - this.reduccionEquipo - this.reduccionDefensiva;
		estado.recibirDanio(this, danioFinal);
		
	}
	
	void perderVida(int danio) {//solo lo usa el estado EnPie
    	vidaActual=Math.max(0, vidaActual - danio);
    	//esto es para que si la vida queda negativa se ponga cero por defecto
    }
	
	public void atacar(Combatiente rival) {
		if (!puedeActuar()) {
			throw new IllegalStateException(nombre + " no puede atacar.");
		}
		if (!rival.puedeSerObjetivo()) {
			throw new IllegalStateException(rival.getNombre() + " no puede ser objetivo.");
		}
		rival.recibirDanio(infligirDanio(DANIO_ATAQUE_BASICO));
	}
	
	//------------------------------------------------------------------------
	//------------------------vida y energia------------------------------------------------

	public void curar(int cantidad) {
		validarPositivo(cantidad);
		if (!puedeActuar()) {
			throw new IllegalStateException(nombre + " no puede curarse");
		}
		vidaActual = Math.min(vidaMaxima, vidaActual + cantidad);
	}
	
	public boolean tieneEnergiaPara(int costoBase) {
		return energiaActual >= calcularCostoEnergia(costoBase);
	}

	public void gastarEnergia(int costoBase) {
		if (!tieneEnergiaPara(costoBase)) {
			throw new IllegalStateException(nombre + " no tiene energía suficiente");
		}
		energiaActual -= calcularCostoEnergia(costoBase);
	}

	public void recuperarEnergia(int cantidad) {
		validarPositivo(cantidad);
		if (puedeActuar()) { // solo los que estan en pie
			energiaActual = Math.min(energiaMaxima, energiaActual + cantidad);
		}
	}
	
	//------------------------------------------------------------------------
	//----------------------State---------------------------------------------
	void cambiarEstado(EstadoCombatiente nuevo) {
		this.estado=nuevo;
	}
	
	public boolean puedeActuar(){ 
		return estado.puedeActuar(); 
	}
	
    public boolean puedeSerObjetivo(){
    	return estado.puedeSerObjetivo(); 
    }
	
	//------------------------------------------------------------------------	

    
    
    
    
    
    //------------------------------------------------------------------------	
    //------------------------------------------------------------------------	
	
	
	
	
    private static void validarPositivo(int valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("El valor debe ser positivo");
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(identificador));
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combatiente other = (Combatiente) obj;
		return identificador == other.identificador;
	}
	
	//hacer test de dos combatientes con mismo id y disitnto nombre son iguales !





}




