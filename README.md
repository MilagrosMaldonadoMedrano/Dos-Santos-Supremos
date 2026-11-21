# Dos Santos Supremos — Torneo de los Inmortales

Simulador por turnos de un torneo eliminatorio en parejas desarrollado en **Java 21** para **Paradigmas de Programación (UNLaM)**.

El sistema modela combatientes con distintos arquetipos, escuelas de artes marciales, equipamiento, técnicas con efectos simples, compuestos y temporales, reglas de combate por parejas y un sistema de eventos para narrar y registrar cada torneo de forma determinista.

---

## Tabla de contenidos

- [Características principales](#características-principales)
- [Patrones de diseño aplicados](#patrones-de-diseño-aplicados)
- [Reglas del dominio](#reglas-del-dominio)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Escenarios de ejemplo (`main`)](#escenarios-de-ejemplo-main)
- [Cómo compilar y ejecutar](#cómo-compilar-y-ejecutar)
- [Pruebas unitarias](#pruebas-unitarias)

---

## Características principales

- **Torneo eliminatorio** para **4 u 8 parejas**, con rondas sucesivas hasta proclamar a la pareja campeona como los **Dos Santos Supremos**.
- **Tres arquetipos de combatientes** implementados con polimorfismo puro (sin condicionales sobre tipos):
  - `CultivadorDeLaEspada`: inflige un **+25 %** de daño.
  - `MonjeDelCuerpoCelestial`: recibe un **-25 %** de daño en ataques directos.
  - `HechiceroDelVacio`: paga un **-30 %** de costo de energía al usar técnicas.
- **Escuelas configurables** (`Escuela`): desacoplan la preparación inicial (técnicas y equipamiento) de la clase concreta del combatiente.
- **Equipamiento por categorías**: hasta **3 piezas** por combatiente y como máximo una por categoría (`ARMA`, `ARMADURA`, `ANILLO`, `SELLO`).
- **Técnicas y efectos temporales**: daño directo, drenado de energía, reducción de daño, curación inmediata, regeneración por turnos y veneno por turnos (que ignora defensas).
- **Regla del compañero**: dentro de una pareja, una técnica utilizada por un integrante queda bloqueada para su compañero mientras el primero siga en pie y conserve usos de esa técnica.
- **Ejecución determinista**: con la misma configuración de escuelas, combatientes y parejas, el torneo produce exactamente el mismo desarrollo y resultado.

---

## Patrones de diseño aplicados

| Patrón | Clases involucradas | Propósito en el proyecto |
| :--- | :--- | :--- |
| **Factory** | `CreadorCombatiente` | Centraliza la instanciación de combatientes por tipo, asigna IDs correlativos y delega la preparación a la `Escuela`. |
| **State** | `EstadoCombatiente`, `Preparado`, `EnPie`, `FueraDeCombate` | Modela el ciclo de vida del combatiente (entre combates, en combate o caído) evitando condicionales de estado en `Combatiente`. |
| **Strategy** | `CriterioDeAccion`, `CriterioPorDefecto`, `CriterioAgresivo` | Encapsula la lógica determinista de elección de técnicas en combate y permite intercambiar criterios en tiempo de ejecución. |
| **Composite** | `Efecto`, `EfectoCompuesto`, `Daño`, `Curacion`, `DrenarEnergia`, `Reduccion`, `AplicarEfectoTemporal` | Permite componer múltiples efectos simples y temporales dentro de una misma técnica. |
| **Iterator** | `Pareja` (`Iterable<Combatiente>`), `IteradorEnPie` | Recorre exclusivamente a los integrantes en pie de una pareja, ordenados por identificador. |
| **Observer** | `Notificador`, `Interesado`, `Acontecimiento` (`InicioCombate`, `AccionRealizada`, `Eliminacion`, `ResultadoCombate`, `Proclamacion`), `HeraldoImperial`, `CronistaDeLaSecta` | Desacopla el motor del torneo de la salida por consola y del registro histórico / tabla de posiciones. |
| **Adapter** | `HeraldoImperial` | Adapta las notificaciones del torneo a un `PrintStream` (por ejemplo, `System.out`). |

---

## Reglas del dominio

### Combatientes y estadísticas base
- **Vida inicial:** `100` (extensible mediante un `Sello`).
- **Energía inicial:** `40` (extensible mediante un `Anillo`).
- **Ataque básico:** `10` de daño (extensible mediante un `Arma`).
- **Usos por técnica:** `5` usos en todo el torneo.

### Equipamiento disponible
| Pieza | Categoría | Efecto |
| :--- | :--- | :--- |
| `EspadaCelestial` | `ARMA` | `+5` de daño en ataque básico |
| `ArmaduraDorada` | `ARMADURA` | `-3` de daño recibido |
| `AnilloEspiritual` | `ANILLO` | `+10` de energía máxima |
| `SelloDelFenix` | `SELLO` | `+20` de vida máxima |

### Técnicas disponibles
| Técnica | Tipo | Costo base | Efecto |
| :--- | :--- | :---: | :--- |
| `CorteDeLosMilAmaneceres` | `OFENSIVA` | `15` | `20` de daño |
| `RugidoDeLaBestiaPrimordial` | `OFENSIVA` | `25` | `45` de daño |
| `DominioDeLaNada` | `OFENSIVA` | `34` | `25` de daño + drena `15` de energía + `Veneno` (`5` de daño × `3` turnos) |
| `VenenoDeLaSerpiente` | `OFENSIVA` | `30` | `10` de daño + `Veneno` (`7` de daño × `4` turnos) |
| `MuroDelCuerpoDorado` | `DEFENSIVA` | `40` | Reduce `10` el daño recibido durante el combate |
| `MeditacionDelInmortal` | `RECUPERACION` | `20` | Cura `20` de vida + `Regeneracion` (`+5` de vida × `3` turnos) |

### Combate y puntuación
- Cada combate tiene un límite de **30 asaltos** (`Combate.LIMITE_ASALTOS`) y al inicio de cada asalto todos los combatientes en pie recuperan **10 de energía** (`Combate.ENERGIA_POR_ASALTO`).
- Comienza el asalto la pareja con menor `id`; dentro de cada pareja actúa primero el combatiente de menor `identificador`.
- Se selecciona como objetivo al rival en pie con **menor vida actual** (desempate por menor `identificador`).
- Al llegar al límite de asaltos, gana la pareja con mayor vida total acumulada (desempate por menor `id` de pareja).
- La tabla de posiciones generada por `CronistaDeLaSecta` ordena por: última ronda jugada (descendente), vida final en su último combate (descendente) e `id` de pareja (ascendente).

---

## Estructura del proyecto

```text
Dos-Santos-Supremos/
└── Torneo_de_los_Inmortales/
    ├── src/
    │   ├── combatiente/              # Combatiente, subclases, State y Strategy
    │   ├── efectos/                  # Efecto, Composite y efectos temporales (Veneno, Regeneracion)
    │   ├── equipamiento_y_tecnicas/  # Equipamiento, Categorias, Tecnicas y TipoTecnica
    │   ├── escuela/                  # Escuela (configuración inicial de técnicas y equipo)
    │   ├── excepciones/              # Jerarquía TorneoException (checked exceptions)
    │   ├── interesado/               # Observer (Notificador, Interesado, Acontecimientos, Heraldo, Cronista)
    │   ├── main/                     # Puntos de entrada con distintos escenarios (App, App2, App3)
    │   └── torneo/                   # Torneo, Ronda, Cruce, Combate, Pareja e IteradorEnPie
    └── test/
        ├── combate/                  # CombateTest
        ├── combatiente/              # CombatienteTest
        ├── efectos/                  # CuracionTest
        ├── equipamiento_y_tecnicas/  # CorteDeLosMilAmaneceresTest, EquipamientoTest, SelloDelFenixTest
        ├── interesado/               # AcontecimientoTest, CronistaDeLaSectaTest
        └── torneo/                   # ParejaTest, TorneoTest
```

---

## Escenarios de ejemplo (`main`)

El paquete `main` incluye tres programas ejecutables para observar el comportamiento del sistema:

1. **`main.App`**: Torneo completo con 4 parejas de distintas escuelas, validación de inscripción duplicada y tabla final de posiciones.
2. **`main.App2`**: Escenario con `VenenoDeLaSerpiente`, donde el daño por turno del veneno ignora armaduras, `MuroDelCuerpoDorado` y la reducción natural del Monje.
3. **`main.App3`**: Parejas integradas por miembros de la misma escuela para evidenciar la **regla del compañero** y el impacto del **polimorfismo** entre distintos tipos de combatientes con la misma preparación.

---

## Cómo compilar y ejecutar

### Requisitos
- **Java JDK 21** o superior.
- **JUnit 5** (para ejecutar los tests unitarios; viene configurado en el proyecto de Eclipse).

### Desde Eclipse IDE
1. Abrir Eclipse e importar el proyecto existente (`File` -> `Import...` -> `General` -> `Existing Projects into Workspace`) seleccionando la carpeta `Torneo_de_los_Inmortales`.
2. Ejecutar cualquiera de las clases `App.java`, `App2.java` o `App3.java` como `Java Application`.
3. Para correr la suite de pruebas, hacer clic derecho sobre la carpeta `test` -> `Run As` -> `JUnit Test`.

### Desde la terminal (sin IDE)
Ubicado dentro de la carpeta `Torneo_de_los_Inmortales`:

```powershell
# Compilar los archivos fuente
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)

# Ejecutar alguno de los escenarios
java -cp bin main.App
java -cp bin main.App2
java -cp bin main.App3
```

---

## Pruebas unitarias

El directorio `Torneo_de_los_Inmortales/test` contiene pruebas con **JUnit 5** que cubren:

- Estados y restricciones de `Combatiente` (acciones y objetivos caídos).
- Reglas de `Equipamiento` (límite de 3 piezas, categoría única, desequipado y `SelloDelFenix`).
- Efectos de `Curacion` y técnicas como `CorteDeLosMilAmaneceres`.
- Lógica de `Combate` y `Pareja` (selección de objetivo, desempate por vida, iterador en pie y regla del compañero).
- Validaciones y determinismo de `Torneo`.
- Registro histórico y ordenamiento de tabla en `CronistaDeLaSecta` y `Acontecimiento`.
