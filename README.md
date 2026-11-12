# BusNova-Proyecto-Estructura-de-Datos

Repositorio creado el 7/10/2026
Primer avance Commit 16/10/2026

## Proyecto BusNovaTech

## 1. Integrantes del Grupo
* Victor Villalobos Alpizar
* Densell Brown Duarte
* Zabdiel Brenes Campos
* Santiago Céspedes Calderón

---

## 2. Requerimientos Implementados en esta Entrega

* **Módulo 1.0: Configuración**
  * Creación y lectura de persistencia local utilizando un archivo estructurado `config.json`.
  * Configuración interactiva del nombre de la terminal y la cantidad dinámica de buses en la primera ejecución del programa.
  * Estructuración garantizada de un único bus preferencial, un único bus directo y los buses normales restantes de forma dinámica.
  * Almacenamiento seguro de credenciales de usuario dentro del archivo de configuración, evitando datos quemados en el código fuente.
  * Sistema de autenticación de inicio de sesión obligatorio basado en las credenciales del archivo JSON antes de permitir el acceso a las funciones del sistema.

* **Módulo 1.1: Creación de Tiquetes**
  * Estructuración del nodo de tiquete que contempla los atributos mínimos requeridos: Nombre del cliente, ID/Cédula, Edad, Moneda de cuenta, Hora de compra obtenida del sistema, Hora de abordaje inicializada en `"-1"`, Tipo de servicio y Tipo de bus.
  * Gestión de tipos de servicio: `VIP`, `Regular`, `Carga` y `Ejecutivo`.
  * Exportación automática y persistencia de cada tiquete creado en el archivo `tiquetes.json`.

* **Módulo 1.2: Atención de Tiquetes**
  * Funcionalidad del inspector para procesar la atención de los pasajeros al abordar o vaciar la fila.
  * Cálculo de costos según las reglas de negocio (VIP con $100 adicionales, Regular $20 base, Carga $20 más $10 por lb, Ejecutivo con $1000 adicionales).
  * Validación interactiva mediante interfaz gráfica (`JOptionPane`) donde se pregunta al usuario si desea realizar el pago; en caso de negarse, el pasajero es retirado de la fila.
  * Registro persistente y serialización de los pasajeros atendidos en el archivo `atendidos.json`, guardando la información de la terminal, ID del bus y hora de atención.

* **Módulo 1.3: Llenado de las Colas**
  * Asignación dinámica de tiquetes a los buses según el tipo (Directo y Preferencial se asignan al crearse; los tiquetes normales se distribuyen inteligentemente al bus con menor cantidad de personas en espera).
  * Uso de colecciones estándar de Java (`LinkedList`, `Queue`, `ArrayList`) para la gestión de las filas y flotas de buses.

* **Módulo 1.4: Servicios Complementarios (Grafos)**
  * Implementación de un grafo ponderado dirigido que define las localidades de parada del bus y las distancias o pesos de las rutas.
  * Opción de menú para imprimir y visualizar el grafo construido con sus localidades y pesos de rutas.
  * Generación y serialización de la estructura en el archivo `grafo.json`.
  * Algoritmo de búsqueda de la ruta más corta (Dijkstra) para permitir al usuario conocer la ruta óptima entre dos terminales elegidas sin costo adicional.

* **Módulo 1.5: Consulta BCCR (Web Service)**
  * Conexión en línea e integración directa con el Web Service o API pública del Banco Central de Costa Rica para la obtención automática del tipo de cambio actual del dólar.

---

## 3. Requerimientos Pendientes / No Alcanzados en esta Entrega

* **Ninguno.** Todos los módulos planificados para el proyecto han sido implementados exitosamente.

---

## 4. Detalles de Configuración y Credenciales para Correr la Aplicación

* **Entorno de Desarrollo:** Java SE / NetBeans IDE
* **Interfaz Gráfica:** Librería Swing mediante la clase `JOptionPane`
* **Archivos de Persistencia:** 
  * `config.json`: Configuración general de la terminal y usuarios.
  * `tiquetes.json`: Registro histórico de tiquetes creados.
  * `atendidos.json`: Registro de pasajeros atendidos por los inspectores.
  * `grafo.json`: Definición serializada de las localidades y rutas.
* **Credenciales Iniciales por Defecto:**
  * **Usuario:** `admin` | **Contraseña:** `1234`
  * **Usuario:** `operador` | **Contraseña:** `abcd`

---

## 5. Documentación Breve de Clases y Métodos Principales

* **`Main.java`**: Clase principal que gestiona el flujo de ejecución, el inicio de sesión validando contra la base de datos local y el menú interactivo completo con `JOptionPane`.
* **`GestorJSON.java`**: Controla la inicialización, lectura y serialización de la configuración general, credenciales y el registro automático de tiquetes.
* **`Tiquete.java`**: Modela la información del pasajero, hora de compra, cálculo de tarifas con `calcularCostoServicio(double tipoCambio)` y exportación a JSON.
* **`BusFila.java`**: Administra la flota de buses de forma dinámica y maneja la cola de pasajeros (`Queue`) asignada a cada unidad.
* **`InspectorAtencion.java`**: Implementa la lógica de cobro del inspector, validación de pago, actualización de la hora de abordaje y guardado de transacciones en `atendidos.json`.
* **`GrafoRutas.java`**: Administra el grafo ponderado dirigido de localidades, permite visualizar las rutas y calcula la ruta más corta mediante el algoritmo de Dijkstra.
* **`ConsultaBCCR.java`**: Realiza la consulta del tipo de cambio oficial del dólar en línea.
