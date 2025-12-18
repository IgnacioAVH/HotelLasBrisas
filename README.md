# 🏨 Sistema de Gestión Hotelera

Sistema de administración y control hotelero desarrollado en **Java** con interfaz gráfica **Swing**. La aplicación permite gestionar el ciclo completo de un huésped, desde la **reserva y asignación de habitaciones** hasta el **proceso de Check-Out** y la **generación de comprobantes de pago**.

---

## 🌟 Características

### Gestión de Reservas

- Proceso integrado de **registro de huéspedes** mediante RUT.
- **Asignación automática** de habitaciones disponibles.

### Control de Habitaciones

- Visualización de estados en tiempo real:
  - **DISPONIBLE**
  - **OCUPADA**
  - **MANTENIMIENTO**
- Proceso de **Check-Out (Liberación)** que restablece la disponibilidad de la habitación.

### Historial de Huéspedes

- Registro automático de clientes mediante **RUT**.
- Buscador inteligente de clientes frecuentes para agilizar nuevas reservas.
- Opción de **eliminación definitiva** de registros con confirmación de seguridad.

### Múltiples Métodos de Pago

- Soporte para:
  - **Efectivo**
  - **Tarjeta de Crédito**
  - **Tarjeta de Débito / Transferencia**
- Generación automática de **Comprobantes de Pago** con ID único y descripción del servicio.

### Persistencia de Datos

- Almacenamiento automático en archivo binario **hotel.dat** para conservar la información al cerrar el programa.

---

## 🛠 Tecnologías Utilizadas

- **Lenguaje**: Java SE (JDK 8 o superior)
- **Interfaz Gráfica**: Java Swing
- **Persistencia**: Serialización de objetos (Java Object Serialization)
- **Arquitectura**: Patrón MVC (Modelo – Vista – Controlador)

---

## 📂 Estructura del Proyecto

```
SistemaHotel/
├── src/
│   ├── Vista/
│   │   ├── Main.java                # Punto de entrada de la aplicación
│   │   ├── VistaPrincipal.java      # Frame base (CardLayout)
│   │   ├── PanelHabitaciones.java   # Visualización y Check-out
│   │   ├── PanelReserva.java        # Formulario de Check-in
│   │   └── PanelHuespedes.java      # Gestión de clientes
│   ├── Controller/
│   │   └── HotelController.java     # Lógica de negocio y nexo MVC
│   ├── Modelo/
│   │   ├── Hotel.java               # Clase contenedora principal
│   │   ├── Habitacion.java          # Entidad habitación
│   │   ├── Huesped.java             # Entidad huésped
│   │   ├── Reserva.java             # Lógica de estancia
│   │   ├── Pago.java                # Procesamiento de transacciones
│   │   ├── Empleado.java            # Superclase de personal
│   │   └── ... (Comprobante, EstadoHabitacion, Botones)
│   └── Persistencia/
│       └── HotelPersistencia.java   # Manejo de archivos y guardado
```

--- VIDEO SISTEMA DE HOTELERIA:
    https://drive.google.com/file/d/12YY3Ld7gRcobUlNDz6fDb5DwMWJWJqHj/view?usp=sharing

## 📋 Funcionamiento del Sistema

### Flujo de Trabajo

1. **Ingreso**

   - El sistema carga los datos desde `hotel.dat`.
   - Si el archivo no existe o está corrupto, se inicializan las habitaciones base.

2. **Reserva (Check-In)**

   - Se ingresa el **RUT del cliente**.
   - Si el huésped ya existe, el sistema recupera sus datos automáticamente.
   - Se selecciona una habitación desde la lista de **disponibles**.

3. **Ocupación**

   - Al confirmar la reserva, la habitación cambia automáticamente su estado a **OCUPADA**.

4. **Check-Out**

   - Desde el panel de habitaciones se selecciona la habitación ocupada.
   - La habitación se libera y vuelve a estado **DISPONIBLE**.

---

## 🏨 Datos Iniciales (Habitaciones)

Configuración base del hotel al iniciar por primera vez:

| Número | Tipo          | Precio    | Estado Inicial |
| ------ | ------------- | --------- | -------------- |
| 101    | Simple        | \$35.000  | DISPONIBLE     |
| 102    | Simple        | \$35.000  | DISPONIBLE     |
| 201    | Doble         | \$50.000  | DISPONIBLE     |
| 202    | Doble         | \$50.000  | DISPONIBLE     |
| 301    | Suite         | \$75.000  | DISPONIBLE     |
| 302    | Suite Premium | \$90.000  | DISPONIBLE     |
| 401    | Penthouse     | \$150.000 | DISPONIBLE     |

---

## ✨ Características de la Interfaz

- **Look and Feel**: Estilo nativo del sistema operativo mediante `UIManager`.
- **Navegación Dinámica**: Uso de `CardLayout` para cambiar paneles sin abrir nuevas ventanas.
- **Tablas Dinámicas**: Implementación de `DefaultTableModel` para actualización en tiempo real.
- **Validaciones Visuales**: Mensajes de alerta con `JOptionPane` para acciones inválidas.

---

## ✅ Validaciones Implementadas

- **Buscador de RUT**: Evita la creación de huéspedes duplicados.
- **Control de Disponibilidad**: Solo permite reservar habitaciones en estado **DISPONIBLE**.
- **Integridad de Datos**: Confirmación obligatoria para eliminación de huéspedes.
- **Persistencia Automática**: Cada reserva, liberación o eliminación activa un guardado automático.

---

## ⚠️ Manejo de Errores

- **Archivo Corrupto**: Si `hotel.dat` falla, se crea una nueva instancia del hotel.
- **Datos Incompletos**: Validación de campos obligatorios en el formulario de reserva.
- **Excepciones de Negocio**: Manejo mediante bloques `try-catch` en procesos críticos.

---

## 🚀 Instalación y Ejecución

### Opción 1: Desde la Terminal

1. Clonar o descargar el repositorio.
2. Compilar el proyecto (desde la carpeta `src`):

```powershell
javac Vista/*.java Controller/*.java Modelo/*.java Persistencia/*.java
```

3. Ejecutar la aplicación:

```powershell
java Vista.Main
```

### Opción 2: Usando un IDE

1. Importar el proyecto en **IntelliJ IDEA**, **Eclipse** o **NetBeans**.
2. Configurar **JDK 8 o superior**.
3. Ejecutar la clase `Main.java` ubicada en el paquete **Vista**.

---

## 🏨 Uso del Sistema

### Menú Principal

- **Ver Estado Habitaciones**: Monitoreo y proceso de Check-Out.
- **Nueva Reserva / Check-In**: Registro de huéspedes y asignación de habitaciones.
- **Ver Lista de Huéspedes**: Gestión del historial de clientes.
- **Salir**: Cierre seguro de la aplicación.

### Proceso de Reserva (Check-In)

1. Seleccionar **Nueva Reserva / Check-In**.
2. Ingresar el **RUT** y buscar (🔍).
3. Completar datos si el huésped es nuevo.
4. Seleccionar una **habitación disponible**.
5. Elegir **método de pago**.
6. Confirmar la reserva.

### Gestión de Habitaciones (Check-Out)

- Visualizar habitaciones con su estado y huésped asignado.
- Seleccionar una habitación ocupada.
- Presionar **Liberar Habitación (Check-Out)**.

---

## 🗃️ Modelo de Datos

### Huésped

- RUT (identificador único)
- Nombre, Teléfono, Email
- ID interno autogenerado

### Habitación

- Número
- Tipo
- Precio por noche
- Estado (DISPONIBLE, OCUPADA, MANTENIMIENTO)

### Reserva

- ID de reserva (autoincremental)
- Fechas de inicio y término
- Huésped y habitación asociada

### Pago y Comprobante

- Monto y método de pago
- Comprobante con ID único y descripción del servicio

---

## 💾 Persistencia de Datos

El sistema utiliza **serialización de objetos Java** para almacenar la información en `hotel.dat`.

Los datos se guardan automáticamente al:

- Registrar una nueva reserva.
- Liberar una habitación (Check-Out).
- Eliminar un huésped del historial.

