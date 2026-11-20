package combatiente;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
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

/*
 * Clase abstracta que representa a un participante del torneo. Guarda su identidad,
 * su vida y energía (actuales y máximas), su equipamiento, las técnicas que conoce con
 * sus usos, sus efectos temporales, su estado y su criterio de acción.
 * POLIMORFISMO: cada tipo (CultivadorDeLaEspada, MonjeDelCuerpoCelestial,
 * HechiceroDelVacio) redefine uno de los métodos calcular..., sin ningún if de tipos.
 * STATE: le delega a su EstadoCombatiente qué puede hacer en cada momento.
 * STRATEGY: le delega a su CriterioDeAccion qué acción elegir en su turno.
 */
public abstract class Combatiente {

	public static final int VIDA_INICIAL = 100;        // vida con la que arrancan todos
	public static final int ENERGIA_INICIAL = 40;      // energía con la que arrancan todos
	public static final int MAX_EQUIPAMIENTO = 3;      // máximo de piezas equipadas
	public static final int USOS_POR_TECNICA = 5;      // usos de cada técnica en todo el torneo
	public static final int DANIO_ATAQUE_BASICO = 10;  // daño del ataque básico sin armas

	private final int identificador;   // único e inmutable
	private final String nombre;
	private int vidaMaxima;            // puede superar la vida inicial con un Sello
	private int vidaActual;
	private int energiaMaxima;         // puede superar la energía inicial con un Anillo
	private int energiaActual;
	private int danio = DANIO_ATAQUE_BASICO;  // daño del ataque básico; lo suben las armas
	private int reduccionDanio;        // la suman la armadura y la técnica defensiva
	private int defensaActiva;         // cuánto sumó la técnica defensiva; se quita al terminar el combate
	private List<Equipamiento> equipamiento = new ArrayList<>();

	private EstadoCombatiente estado;                // State: indica qué puede hacer
	private Map<Tecnica, Integer> usosPorTecnica;    // técnicas que conoce y usos restantes, en orden de la escuela
	private List<EfectoTemporal> efectosActivos;     // veneno o regeneración activos
	private CriterioDeAccion criterio = new CriterioPorDefecto();  // Strategy: cómo elige su acción

	/*
	 * Crea el combatiente con su identificador y su nombre. Arranca con 100 de vida
	 * y 40 de energía (actuales y máximas) y en estado Preparado (entre combates).
	 * Si el nombre es null o está vacío, lanza IllegalArgumentException.
	 * Lo usan: los constructores de CultivadorDeLaEspada, MonjeDelCuerpoCelestial
	 * y HechiceroDelVacio (con super), que crea CreadorCombatiente.instanciar().
	 */
	public Combatiente(int identificador, String nombre) {
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

	/*
	 * Devuelve la vida actual.
	 * Lo usan: EnPie (ve si llegó a 0), Pareja.vidaTotal (desempate), Combate.elegirObjetivo
	 * (ataca al de menos vida), CriterioPorDefecto (si le falta vida) y AccionRealizada.
	 */
	public int getVidaActual() {
		return vidaActual;
	}

	/*
	 * Devuelve la energía actual.
	 * Lo usan: las pruebas de combatiente, técnicas y equipamiento.
	 */
	public int getEnergiaActual() {
		return energiaActual;
	}

	/*
	 * Devuelve el identificador único e inmutable.
	 * Lo usa: Pareja, que ordena a sus integrantes por id (actúa primero el de menor id).
	 */
	public int getIdentificador() {
		return identificador;
	}

	/*
	 * Devuelve el nombre.
	 * Lo usan: los mensajes de las excepciones, los acontecimientos que muestra el Heraldo
	 * (AccionRealizada, Eliminacion) y Pareja.toString.
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * Devuelve la vida máxima (puede superar 100 con un Sello).
	 * Lo usa: CriterioPorDefecto, que la compara con la actual para saber si le falta vida.
	 */
	public int getVidaMaxima() {
		return vidaMaxima;
	}

	/*
	 * Devuelve la energía máxima (puede superar 40 con un Anillo).
	 * Lo usan: las pruebas de técnicas y equipamiento.
	 */
	public int getEnergiaMaxima() {
		return energiaMaxima;
	}

	/*
	 * Devuelve el daño del ataque básico: 10 más lo que sumen las armas.
	 * Hoy no lo usa ninguna clase; está para consultarlo en pruebas o en el main.
	 */
	public int getDanio() {
		return danio;
	}

	/*
	 * POLIMORFISMO: calcula cuánto daño provoca el combatiente. En esta clase devuelve el
	 * valor sin cambios; CultivadorDeLaEspada lo redefine para pegar un 25 % más.
	 * Lo usan: infligirDanio (daño de las técnicas) y atacar (ataque básico).
	 * Es protected porque solo lo usan esta clase y sus subclases.
	 */
	protected int calcularDanioAtaque(int danioBase){
		return danioBase;
	}

	/*
	 * POLIMORFISMO: calcula cuánto daño recibe el combatiente. En esta clase devuelve el
	 * valor sin cambios; MonjeDelCuerpoCelestial lo redefine para recibir un 25 % menos.
	 * Lo usa: recibirDanio, antes de restar la armadura y la defensa.
	 */
	protected int calcularDanioRecibido(int danioBase) {
		return danioBase;
	}

	/*
	 * POLIMORFISMO: calcula cuánta energía paga por una técnica. En esta clase devuelve
	 * el costo sin cambios; HechiceroDelVacio lo redefine para pagar un 30 % menos.
	 * Lo usan: tieneEnergiaPara y gastarEnergia.
	 */
	protected int calcularCostoEnergia(int energiaBase){
		return energiaBase;
	}

	/*
	 * Calcula el daño que hace este combatiente con una técnica, aplicando el método
	 * redefinido calcularDanioAtaque. Así la misma técnica daña distinto según quién la
	 * usa, sin que la técnica pregunte el tipo del combatiente.
	 * Lo usa: el efecto Daño, que recibe al ejecutor en aplicarEfecto(ejecutor, afectado).
	 */
	public int infligirDanio(int danioTecnica){
		return calcularDanioAtaque(danioTecnica); }

	/*
	 * Recibe un golpe. Aplica el método redefinido calcularDanioRecibido, le resta la
	 * armadura y la defensa (sin bajar de 0) y le DELEGA al estado qué hacer
	 * (patrón State): en EnPie pierde vida; en Preparado o FueraDeCombate lanza
	 * IllegalStateException.
	 * Lo usan: atacar (sobre el rival) y el efecto Daño de las técnicas.
	 */
	public void recibirDanio(int danio){
		if(danio <0) {
			throw new IllegalArgumentException("El daño no puede ser negativo");
		}
		int danioFinal = calcularDanioRecibido(danio) - reduccionDanio;
		estado.recibirDanio(this, Math.max(0, danioFinal));

	}

	/*
	 * Resta vida sin bajar de 0. Tiene visibilidad de paquete: desde afuera solo se puede
	 * dañar con recibirDanio, que siempre pasa por los cálculos y por el estado.
	 * Lo usa: únicamente EnPie.recibirDanio.
	 */
	void perderVida(int danio) {
		vidaActual=Math.max(0, vidaActual - danio);
	}

	/*
	 * Ataque básico: valida que este combatiente pueda actuar y que el rival pueda ser
	 * objetivo; si no, lanza IllegalStateException. Calcula su daño (10 + armas, con
	 * calcularDanioAtaque) y se lo entrega al rival con recibirDanio. No cuesta energía
	 * ni usos.
	 * Lo usa: Combate.actuar, cuando el criterio no elige técnica o la técnica falla.
	 */
	public void atacar(Combatiente rival){
		if (!puedeActuar()) {
			throw new IllegalStateException(nombre + " no puede atacar.");
		}
		if (!rival.puedeSerObjetivo()) {
			throw new IllegalStateException(rival.getNombre() + " no puede ser objetivo.");
		}
		rival.recibirDanio(calcularDanioAtaque(this.danio));
	}

	/*
	 * Recupera vida sin pasar la vida máxima. Solo si está en pie; si no, lanza
	 * IllegalStateException. La cantidad tiene que ser positiva.
	 * Lo usan: los efectos Curacion (Meditación del Inmortal) y Regeneracion.
	 */
	public void curar(int cantidad) {
		validarPositivo(cantidad);
		if (!puedeActuar()) {
			throw new IllegalStateException(nombre + " no puede curarse");
		}
		vidaActual = Math.min(vidaMaxima, vidaActual + cantidad);
	}

	/*
	 * Indica si le alcanza la energía para una técnica, aplicando el método redefinido
	 * calcularCostoEnergia (el Hechicero paga menos).
	 * Lo usan: Tecnica.realizar (valida antes de cobrar), gastarEnergia y puedeUsar.
	 */
	public boolean tieneEnergiaPara(int costoBase) {
		return energiaActual >= calcularCostoEnergia(costoBase);
	}

	/*
	 * Paga el costo de una técnica con su descuento. Si no le alcanza, lanza
	 * IllegalStateException.
	 * Lo usa: Tecnica.realizar, después de validar todo.
	 */
	public void gastarEnergia(int costoBase) {
		if (!tieneEnergiaPara(costoBase)) {
			throw new IllegalStateException(nombre + " no tiene energía suficiente");
		}
		energiaActual -= calcularCostoEnergia(costoBase);
	}

	/*
	 * Pierde energía por un efecto del rival, sin bajar de 0. A diferencia de
	 * gastarEnergia, no valida nada, porque se lo hacen a él.
	 * Lo usa: el efecto DrenarEnergia (Dominio de la Nada).
	 */
	public void perderEnergia(int cantidad) {
		energiaActual = Math.max(0, energiaActual - Math.abs(cantidad));
	}

	/*
	 * BONUS: suma un efecto temporal (veneno o regeneración) a su lista de efectos activos.
	 * Lo usa: AplicarEfectoTemporal, que le entrega una copia nueva del efecto
	 * cada vez que se usa la técnica.
	 */
	public void agregarEfectoTemporal(EfectoTemporal e){
		efectosActivos.add(e);
	}

	/*
	 * BONUS: al comenzar su turno, aplica cada efecto temporal (el veneno daña, la
	 * regeneración cura). Si el veneno lo deja fuera de combate, deja de aplicar los demás.
	 * Al final quita los efectos que ya terminaron.
	 * Lo usa: Combate.jugarTurno, antes de que el combatiente actúe.
	 */
	public void procesarEfectosTurno(){
		for (EfectoTemporal e : efectosActivos) {
			if (!puedeActuar()) break;
			e.aplicarPorTurno(this);
		}
		efectosActivos.removeIf(EfectoTemporal::expiro);
	}

	/*
	 * BONUS: borra todos sus efectos temporales, para que no pasen de un combate a otro.
	 * Lo usan: prepararParaCombate y finalizarCombate de esta misma clase.
	 */
	public void limpiarEfectosTemporales(){
		efectosActivos.clear();
	}

	/*
	 * Usa una técnica sobre un objetivo. Valida que pueda actuar, que conozca la técnica
	 * y que le queden usos; si no, lanza TecnicaNoDisponibleException (verificada).
	 * Después la técnica cobra la energía y aplica su efecto (Tecnica.realizar), y recién
	 * ahí se descuenta un uso, así un intento fallido no gasta usos.
	 * Lo usa: Combate.actuar, que atrapa la excepción y hace un ataque básico.
	 */
	public void usarTecnica(Tecnica tecnica, Combatiente objetivo) throws TecnicaNoDisponibleException{
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

	/*
	 * Aprende técnicas: guarda cada una con 5 usos para todo el torneo. Los usos se
	 * guardan en un LinkedHashMap, que conserva el orden de la escuela (desempate del criterio).
	 * Lo usa: Escuela.preparar, cuando el creador le pide a la escuela que lo prepare.
	 */
	public void aprenderTecnicas(List<Tecnica> tecnicas) {
		for(Tecnica tec: tecnicas){
			usosPorTecnica.put(tec, USOS_POR_TECNICA);
		}
	}

	/*
	 * STATE: cambia el objeto estado del combatiente. Tiene visibilidad de paquete,
	 * así que nadie de afuera puede cambiarle el estado a mano.
	 * Lo usan: EnPie.recibirDanio (pasa a FueraDeCombate al llegar a 0)
	 * y prepararParaCombate / finalizarCombate de esta clase.
	 */
	void cambiarEstado(EstadoCombatiente nuevo){
		this.estado=nuevo;
	}

	/*
	 * STATE: le pregunta a su estado si puede actuar. Solo EnPie responde true.
	 * Lo usan: IteradorEnPie (salta a los caídos), Combate.jugarTurno y Combate.actuar,
	 * la regla del compañero (Pareja) y varios métodos de esta clase (atacar, curar,
	 * usarTecnica, recuperarEnergia, puedeUsar, procesarEfectosTurno).
	 */
	public boolean puedeActuar(){
		return estado.puedeActuar();
	}

	/*
	 * STATE: le pregunta a su estado si puede ser elegido como objetivo. Solo EnPie
	 * responde true.
	 * Lo usan: atacar (valida al rival) y Tecnica.realizar (valida al afectado
	 * antes de cobrar la energía).
	 */
	public boolean puedeSerObjetivo(){
		return estado.puedeSerObjetivo();
	}

	/*
	 * Al comenzar cada combate: quita la defensa y los efectos temporales, vuelve a vida y
	 * energía máximas (también si había caído antes) y pasa al estado EnPie.
	 * NO toca los usos de las técnicas, que se conservan en todo el torneo.
	 * Lo usa: Pareja.prepararParaCombate, que llama Combate.jugar al empezar.
	 */
	public void prepararParaCombate(){
		quitarDefensa();
		limpiarEfectosTemporales();
		vidaActual = vidaMaxima;
		energiaActual = energiaMaxima;
		cambiarEstado(new EnPie());
	}

	/*
	 * Al terminar el combate: quita la defensa y los efectos temporales, y vuelve
	 * al estado Preparado, donde puede cambiar su equipo. La vida no se toca, para que
	 * el Cronista registre la vida final.
	 * Lo usa: Pareja.finalizarCombate, que llama Combate.jugar al terminar.
	 */
	public void finalizarCombate(){
		quitarDefensa();
		limpiarEfectosTemporales();
		cambiarEstado(new Preparado());
	}

	/*
	 * Recupera energía al comenzar cada asalto, sin pasar la energía máxima.
	 * Solo los que están en pie; un caído no recupera.
	 * Lo usa: Combate, al principio de cada asalto (ENERGIA_POR_ASALTO = 10).
	 */
	public void recuperarEnergia(int cantidad){
		validarPositivo(cantidad);
		if (puedeActuar()) energiaActual = Math.min(energiaMaxima, energiaActual + cantidad);
	}

	/*
	 * Activa una defensa: suma la cantidad a la reducción de daño y la anota en
	 * defensaActiva, para poder sacarla al final del combate (dura el resto del combate).
	 * Si se activa dos veces, acumula, y al final se resta todo lo que sumó.
	 * Lo usa: el efecto Reduccion, que aplica la técnica Muro del Cuerpo Dorado.
	 */
	public void activarDefensa(int cantidad) {
		validarPositivo(cantidad);
		reduccionDanio += cantidad;
		defensaActiva += cantidad;
	}

	/*
	 * Quita exactamente lo que sumó la defensa y la vuelve a 0. No le importa qué técnica
	 * fue, así sirve para cualquier técnica defensiva nueva sin modificar esta clase.
	 * Lo usan: prepararParaCombate y finalizarCombate de esta clase.
	 */
	private void quitarDefensa() {
		reduccionDanio -= defensaActiva;
		defensaActiva = 0;
	}

	/*
	 * Indica si tiene una defensa activa en este combate.
	 * Lo usa: CriterioPorDefecto, que elige una defensiva solo si no tiene una activa.
	 */
	public boolean tieneDefensaActiva(){ return defensaActiva > 0; }

	/*
	 * Equipa una pieza. Valida que esté entre combates, que no supere 3 piezas y que no
	 * repita categoría; si algo falla, lanza EquipamientoInvalidoException y el combatiente
	 * queda igual (se rechaza sin cambios). Después le pide a la pieza que aplique su
	 * efecto: cada pieza sabe qué modificar (polimorfismo).
	 * Una pieza null es un error de programación y lanza NullPointerException.
	 * Lo usan: Escuela.preparar y las pruebas de equipamiento.
	 */
	public void equipar(Equipamiento pieza) throws EquipamientoInvalidoException {
		Objects.requireNonNull(pieza);
		validarCambioDeEquipo();
		if (equipamiento.size() >= MAX_EQUIPAMIENTO)
			throw new EquipamientoInvalidoException("Máximo " + MAX_EQUIPAMIENTO + " piezas");
		for (Equipamiento e : equipamiento)
			if (e.getCategoria() == pieza.getCategoria())
				throw new EquipamientoInvalidoException("Ya tiene una pieza de categoría " + pieza.getCategoria());
		equipamiento.add(pieza);
		pieza.aplicarEfecto(this);
	}

	/*
	 * Quita una pieza que lleva puesta y le pide que deshaga su efecto. Solo entre combates.
	 * Si no la tiene equipada, lanza EquipamientoInvalidoException.
	 * Lo usan: las pruebas EquipamientoTest y SelloDelFenixTest.
	 */
	public void desequipar(Equipamiento pieza) throws EquipamientoInvalidoException {
		validarCambioDeEquipo();
		if (!equipamiento.remove(pieza))
			throw new EquipamientoInvalidoException("No tiene equipada la pieza " + pieza.getNombre());
		pieza.quitarEfecto(this);
	}

	/*
	 * Le pregunta al estado si se puede cambiar el equipo (solo Preparado dice que sí);
	 * si no, lanza EquipamientoInvalidoException.
	 * Lo usan: equipar y desequipar.
	 */
	private void validarCambioDeEquipo() throws EquipamientoInvalidoException {
		if (!estado.puedeModificarEquipamiento())
			throw new EquipamientoInvalidoException("No se puede cambiar el equipo durante el combate");
	}

	/*
	 * Devuelve las piezas que lleva, en una lista de solo lectura: nadie puede agregar
	 * piezas desde afuera sin pasar por las reglas de equipar.
	 * Lo usa: EquipamientoTest, para verificar que un rechazo no cambia el equipo.
	 */
	public List<Equipamiento> getEquipamiento() { return Collections.unmodifiableList(equipamiento); }

	/*
	 * Suma (al equipar) o resta (al quitar) daño al ataque básico.
	 * Lo usa: Arma, en aplicarEfecto y quitarEfecto.
	 */
	public void modificarDanio(int c)         { danio += c; }

	/*
	 * Suma (al equipar) o resta (al quitar) reducción del daño recibido.
	 * Lo usa: Armadura, en aplicarEfecto y quitarEfecto.
	 */
	public void modificarReduccionDanio(int c) { reduccionDanio += c; }

	/*
	 * Cambia la vida máxima. La vida actual NO sube; si queda por encima del nuevo
	 * máximo, se recorta.
	 * Lo usa: Sello, en aplicarEfecto y quitarEfecto.
	 */
	public void modificarVidaMaxima(int c){
		vidaMaxima += c;
		vidaActual = Math.min(vidaActual, vidaMaxima);
	}

	/*
	 * Cambia la energía máxima. La energía actual NO sube; si queda por encima del nuevo
	 * máximo, se recorta.
	 * Lo usa: Anillo, en aplicarEfecto y quitarEfecto.
	 */
	public void modificarEnergiaMaxima(int c){
		energiaMaxima += c;
		energiaActual = Math.min(energiaActual, energiaMaxima);
	}

	/*
	 * Ayuda interna: rechaza valores cero o negativos con IllegalArgumentException.
	 * Es static porque no usa atributos del combatiente.
	 * Lo usan: curar, recuperarEnergia y activarDefensa.
	 */
	private static void validarPositivo(int valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("El valor debe ser positivo");
		}
	}

	/*
	 * Calcula el hash solo con el identificador, igual que equals. Es obligatorio
	 * para que funcionen bien los HashSet y HashMap.
	 * Lo usa: el HashSet de inscriptos de Torneo (rechaza un combatiente repetido).
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(identificador));
	}

	/*
	 * Dos combatientes son el mismo si tienen el mismo identificador, sin importar el
	 * nombre.
	 * Lo usan: Torneo (inscriptos), el constructor de Pareja (rechaza dos iguales),
	 * Pareja.contiene y la regla del compañero (compara quién tomó la técnica).
	 */
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

	/*
	 * Devuelve cuántos usos le quedan de una técnica (0 si no la conoce).
	 * Lo usan: CriterioPorDefecto (elige la ofensiva con más usos), Pareja (la regla del
	 * compañero se libera cuando el primero se queda sin usos) y puedeUsar.
	 */
	public int usosRestantes(Tecnica t) { return usosPorTecnica.getOrDefault(t, 0); }

	/*
	 * Indica si puede usar una técnica ahora: en pie, con usos y con energía suficiente.
	 * Es una pregunta: devuelve false en vez de lanzar una excepción.
	 * Lo usan: CriterioPorDefecto y CriterioAgresivo, para descartar las técnicas imposibles.
	 */
	public boolean puedeUsar(Tecnica t) {
		return puedeActuar() && usosRestantes(t) > 0 && tieneEnergiaPara(t.getCostoEnergia());
	}

	/*
	 * Devuelve las técnicas que conoce, en el orden de su escuela (una copia: el mapa
	 * interno no se puede modificar desde afuera).
	 * Lo usan: CriterioPorDefecto y CriterioAgresivo; el orden resuelve los empates.
	 */
	public List<Tecnica> getTecnicas() { return new ArrayList<>(usosPorTecnica.keySet()); }

	/*
	 * STRATEGY: el combatiente no decide qué hacer, le pregunta a su criterio de acción.
	 * Recibe las técnicas que el compañero tiene bloqueadas y devuelve un Optional:
	 * con una técnica, o vacío si corresponde un ataque básico.
	 * Lo usa: Combate.actuar, que le pasa pareja.tecnicasBloqueadasPara(c).
	 */
	public Optional<Tecnica> elegirTecnica(Set<Tecnica> bloqueadasPorCompanero){
		return criterio.elegir(this, bloqueadasPorCompanero);
	}

	/*
	 * STRATEGY: cambia la forma de elegir acciones (por ejemplo, new CriterioAgresivo())
	 * sin modificar la clase Combatiente. Por defecto tiene CriterioPorDefecto.
	 * Lo usa: el main o las pruebas, cuando quieren otro comportamiento.
	 */
	public void setCriterio(CriterioDeAccion criterio) {
		this.criterio = Objects.requireNonNull(criterio);
	}

	/*
	 * BONUS: daño de los efectos temporales. Ignora la armadura, la defensa y la reducción
	 * del Monje, pero pasa por el estado: si está en pie pierde vida y puede caer.
	 * Lo usa: Veneno.ejecutarEfecto, en cada turno del envenenado.
	 */
	public void recibirDanioDirecto(int danio) {
		if (danio < 0) {
			throw new IllegalArgumentException("El daño no puede ser negativo");
		}
		estado.recibirDanio(this, danio);
	}

}
