
package combatiente;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.Objects;

import efectos.EfectoTemporal;
import equipamiento_y_tecnicas.Equipamiento;
import equipamiento_y_tecnicas.Tecnica;
import excepciones.EquipamientoInvalidoException;
import excepciones.TecnicaNoDisponibleException;


public abstract class Combatiente {
	
	public static final int VIDA_INICIAL = 100; //todos arrancan con esta vida
    public static final int ENERGIA_INICIAL = 40;
    public static final int MAX_EQUIPAMIENTO = 3;
    public static final int USOS_POR_TECNICA = 5;
    public static final int DANIO_ATAQUE_BASICO = 10;
	
    private final int identificador; //ideentificador unico e inmutable
    private final String nombre;
    private int vidaMaxima;	//pude ser mayor a la vida inicial!
    private int vidaActual;
    private int energiaMaxima;
    private int energiaActual;
    private int danio = DANIO_ATAQUE_BASICO; //arranca en 10 y lo suben las armas
    private int reduccionDanio; // la suman la armadura y el muro
    private int defensaActiva;  //cuanto sumo la tecnica defensiva, se saca al final del combate
    private List<Equipamiento> equipamiento = new ArrayList<>();
	
	private EstadoCombatiente estado; //patron State, el combatiente tiene un estado que le indica que pude o no jacer
	private Map<Tecnica, Integer> usosPorTecnica;
	private List<EfectoTemporal> efectosActivos;
	private CriterioDeAccion criterio = new CriterioPorDefecto();
	
	
	
	public Combatiente(int identificador,String nombre){
		if ((nombre == null || nombre.isBlank()) ){
			throw new IllegalArgumentException("Nombre vacío");
		}
			
		this.identificador=identificador;
		this.nombre=nombre;
		this.vidaMaxima=VIDA_INICIAL;
		this.vidaActual=VIDA_INICIAL;
		this.energiaMaxima=ENERGIA_INICIAL;
		this.energiaActual=ENERGIA_INICIAL;
		
		this.estado = new Preparado();

		this.usosPorTecnica = new LinkedHashMap<>();
		this.efectosActivos = new LinkedList<EfectoTemporal>();
	}
	
	
	
	//------------------------------------------------------------------------
	public int getVidaActual() {
		return vidaActual;
	}
	public int getEnergiaActual() {
		return energiaActual;
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
	//------------------------------------------------------------------------
	
	
	
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
	
	//lo usan las tecnicas
	public int infligirDanio(int danioTecnica) { 
		return calcularDanioAtaque(danioTecnica); }
	
	public void recibirDanio(int danio) {
		if(danio <0) {
			throw new IllegalArgumentException("El daño no puede ser negativo");
		}
		int danioFinal = calcularDanioRecibido(danio) - reduccionDanio; 
		estado.recibirDanio(this, Math.max(0, danioFinal));  
		
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
		rival.recibirDanio(calcularDanioAtaque(this.danio)); 
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


	public void perderEnergia(int cantidad) {
		// 0 si le quitan mas energia de la que tiene
		energiaActual = Math.max(0, energiaActual - Math.abs(cantidad));
	}
	
	//------------------------------------------------------------------------
	
	
	
	
	
	
	//----------------------efectos-------------------------------------------

	public void agregarEfectoTemporal(EfectoTemporal e){
		efectosActivos.add(e);
	}

	public void procesarEfectosTurno() {
		for (EfectoTemporal e : efectosActivos) {
			if (!puedeActuar()) break;           // si cayó, no sigue
			e.aplicarPorTurno(this);
		}
		efectosActivos.removeIf(EfectoTemporal::expiro);
	}

	public void limpiarEfectosTemporales(){
		efectosActivos.clear();
	}

	//------------------------------------------------------------------------
	//----------------------tecnicas------------------------------------------

	public void usarTecnica(Tecnica tecnica, Combatiente objetivo) throws TecnicaNoDisponibleException {
		if (!puedeActuar())
			throw new TecnicaNoDisponibleException(nombre + " no puede actuar");
		Integer usos = usosPorTecnica.get(tecnica);
		if (usos == null)
			throw new TecnicaNoDisponibleException(nombre + " no conoce " + tecnica.getNombre());
		if (usos <= 0)
			throw new TecnicaNoDisponibleException(nombre + " no tiene usos de " + tecnica.getNombre());
		tecnica.realizar(this, objetivo);
		usosPorTecnica.put(tecnica, usos - 1);
	}

	public void aprenderTecnicas(List<Tecnica> tecnicas){
		for(Tecnica tec: tecnicas){
			usosPorTecnica.put(tec, USOS_POR_TECNICA);
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
    public void prepararParaCombate() {
    	quitarDefensa();
    	limpiarEfectosTemporales();
    	vidaActual = vidaMaxima;
    	energiaActual = energiaMaxima;
    	cambiarEstado(new EnPie());
    }

    public void finalizarCombate() {
    	quitarDefensa();
    	limpiarEfectosTemporales();
    	cambiarEstado(new Preparado());
    }

    public void recuperarEnergia(int cantidad) {   // la llama el Combate en cada asalto
    	validarPositivo(cantidad);
    	if (puedeActuar()) energiaActual = Math.min(energiaMaxima, energiaActual + cantidad);
    }
	
	//---------------defensa-------------------------------------------	
    public void activarDefensa(int cantidad) {
    	validarPositivo(cantidad);
    	reduccionDanio += cantidad;
    	defensaActiva += cantidad;   // acumula: al final del combate se resta todo lo que sumo
    }
    private void quitarDefensa() {
    	reduccionDanio -= defensaActiva;
    	defensaActiva = 0;
    }

    public boolean tieneDefensaActiva() { return defensaActiva > 0; }
    
    
    
    
    
    //-----------------equipamiento----------------------------------------	
    public void equipar(Equipamiento pieza) throws EquipamientoInvalidoException {
    	Objects.requireNonNull(pieza);          // null = error de programación, queda no verificada
    	validarCambioDeEquipo();
    	if (equipamiento.size() >= MAX_EQUIPAMIENTO)
    		throw new EquipamientoInvalidoException("Máximo " + MAX_EQUIPAMIENTO + " piezas");
    	for (Equipamiento e : equipamiento)
    		if (e.getCategoria() == pieza.getCategoria())
    			throw new EquipamientoInvalidoException("Ya tiene una pieza de categoría " + pieza.getCategoria());
    	equipamiento.add(pieza);
    	pieza.aplicarEfecto(this);
    }

    public void desequipar(Equipamiento pieza) throws EquipamientoInvalidoException {
    	validarCambioDeEquipo();
    	if (!equipamiento.remove(pieza))
    		throw new EquipamientoInvalidoException("No tiene equipada la pieza " + pieza.getNombre());
    	pieza.quitarEfecto(this);
    }

    private void validarCambioDeEquipo() throws EquipamientoInvalidoException {
    	if (!estado.puedeModificarEquipamiento())
    		throw new EquipamientoInvalidoException("No se puede cambiar el equipo durante el combate");
    }

    public List<Equipamiento> getEquipamiento() { return Collections.unmodifiableList(equipamiento); }

    // los llaman las piezas (+ al equipar, - al quitar)
    public void modificarDanio(int c)          { danio += c; }
    public void modificarReduccionDanio(int c) { reduccionDanio += c; }
    public void modificarVidaMaxima(int c) {
    	vidaMaxima += c;
    	vidaActual = Math.min(vidaActual, vidaMaxima);       // no sube; solo se recorta (§4)
    }
    public void modificarEnergiaMaxima(int c) {
    	energiaMaxima += c;
    	energiaActual = Math.min(energiaActual, energiaMaxima);
    }
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

	
	
	
    //------------------------------------------------------------------------	


	public int usosRestantes(Tecnica t) { return usosPorTecnica.getOrDefault(t, 0); }

	public boolean puedeUsar(Tecnica t) {
		return puedeActuar() && usosRestantes(t) > 0 && tieneEnergiaPara(t.getCostoEnergia());
	}

	public List<Tecnica> getTecnicas() { return new ArrayList<>(usosPorTecnica.keySet()); }  // en orden de la escuela

	
	
	
	
	public Optional<Tecnica> elegirTecnica(Set<Tecnica> bloqueadasPorCompanero) {
		return criterio.elegir(this, bloqueadasPorCompanero);
	}
	public void setCriterio(CriterioDeAccion criterio) {
		this.criterio = Objects.requireNonNull(criterio);
	}


}




