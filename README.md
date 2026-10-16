# BusNova-Proyecto-Estructura-de-Datos



Repositorio creado el 7/10/2026

Primer avance Commit 16/10/2026



Proyecto BusNovaTech



\## 1. Integrantes del Grupo

&#x09;Victor Villalobos Alpizar

&#x09;Densell Brown Duarte

&#x09;Zabdiel Brenes Campos

&#x09;Santiago Céspedes Calderón



\---



\## 2. Requerimientos Implementados en esta Entrega



\* \*\*Módulo 1.0: Configuración\*\*

&#x20; \* Creación y lectura de persistencia local utilizando un archivo estructurado `config.json`

&#x20; \* Configuración interactiva del nombre de la terminal y la cantidad dinámica de buses en la primera ejecución del programa

&#x20; \* Estructuración garantizada de un único bus preferencial, un único bus directo y los buses normales restantes de forma dinámica

&#x20; \* Almacenamiento seguro de credenciales de usuario (nombre de usuario y contraseña) dentro del archivo de configuración, evitando datos quemados en el código fuente

&#x20; \* Sistema de autenticación de inicio de sesión obligatorio basado en las credenciales del archivo JSON antes de permitir el acceso a las funciones del sistema



\* \*\*Módulo 1.1: Creación de Tiquetes\*\*

&#x20; \* Estructuración del nodo de tiquete que contempla los atributos mínimos requeridos: Nombre del cliente, ID/Cédula, Edad, Moneda de cuenta (colones o dólares), Hora de compra obtenida del sistema, Hora de abordaje inicializada por defecto en `"-1"` hasta ser atendido, Tipo de servicio y Tipo de bus

&#x20; \* Gestión de tipos de servicio: `VIP`, `Regular`, `Carga` y `Ejecutivo`\[cite: 8, 9].



\* \*\*Módulo 1.2: Atención de Tiquetes\*\*

&#x20; \* Funcionalidad del inspector para procesar la atención de los pasajeros al abordar o vaciar la fila\[cite: 9].

&#x20; \* Cálculo de costos según las reglas de negocio (VIP con $100 adicionales, Regular $20 base, Carga $20 más $10 por lb, Ejecutivo con $1000 adicionales)

&#x20; \* Validación interactiva mediante interfaz gráfica (`JOptionPane`) donde se pregunta al usuario si desea realizar el pago; en caso de negarse, el pasajero es retirado de la fila

&#x20; \* Registro persistente y serialización de los pasajeros atendidos en el archivo `atendidos.json`, guardando la información de la terminal, ID del bus y hora de atención



\* \*\*Módulo 1.3: Llenado de las Colas\*\*

&#x20; \* Asignación dinámica de tiquetes a los buses según el tipo (Directo y Preferencial se asignan al crearse; los tiquetes normales se distribuyen inteligentemente al bus con menor cantidad de personas en espera)

&#x20; \* Uso de colecciones estándar de Java (`LinkedList`, `Queue`, `ArrayList`) autorizadas por la cátedra para la gestión de las filas y flotas de buses.



\---



\## 3. Requerimientos Pendientes / No Alcanzados en esta Entrega



\* \*\*Módulo 1.4



\* \*\*Módulo 1.5: Consulta BCCR (Web Service)\*\*

&#x20; \* Conexión en línea e integración directa con el Web Service del Banco Central de Costa Rica para la obtención automática del tipo de cambio del dólar al día de la presentación \*(Actualmente se maneja un tipo de cambio simulado/fijo para pruebas de cobro)\*.





\---



\## 4. Detalles de Configuración y Credenciales para Correr la Aplicación



\* \*\*Entorno de Desarrollo:\*\* Java SE / NetBeans IDE

\* \*\*Interfaz Gráfica:\*\* Librería Swing mediante la clase `JOptionPane`

\* \*\*Archivos de Persistencia:\*\* 

&#x20; \* `config.json`: Se autogenera en la primera ejecución si no existe en la raíz del proyecto

&#x20; \* `atendidos.json`: Se crea automáticamente al procesar la primera atención de un tiquete

\* \*\*Credenciales Iniciales por Defecto (configurables en el JSON o interfaz):\*\*

&#x20; \* \*\*Usuario:\*\* `admin` | \*\*Contraseña:\*\* `1234`

&#x20; \* \*\*Usuario:\*\* `operador` | \*\*Contraseña:\*\* `abcd`



\---



\## 4. Documentación Breve de Clases y Métodos Principales



\* \*\*`Main.java`\*\*: Clase principal que gestiona el flujo de ejecución, el proceso de inicio de sesión validando contra la base de datos local y despliega el menú interactivo con `JOptionPane`.

\* \*\*`GestorJSON.java`\*\*: Controla la inicialización, lectura y serialización de la configuración general del sistema y credenciales en `config.json`\* \*\*`Tiquete.java`\*\*: Modela la información del pasajero y los tiquetes. Incluye el método `calcularCostoServicio(double tipoCambio)` para computar las tarifas según los servicios contratados

\* \*\*`BusFila.java`\*\*: Administra la flota de buses de forma dinámica y maneja la cola de pasajeros (`Queue`) asignada a cada unidad

\* \*\*`InspectorAtencion.java`\*\*: Implementa la lógica de cobro del inspector, validación de pago, actualización de la hora de abordaje y guardado de transacciones en `atendidos.json`

