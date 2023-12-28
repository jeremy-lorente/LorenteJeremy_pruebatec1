# LorenteJeremy_pruebaTec1

 ## Objetivo
> [!NOTE]
> El objetivo de esta prueba es evaluar tus conocimientos en Java, incluyendo sintaxis, estructuras repetitivas, estructuras selectivas, manejo de colecciones y operaciones CRUD (Crear, Leer, Actualizar y Borrar) utilizando JPA (Java Persistence API) para interactuar con una base de datos.

## Iniciación proyecto
> [!IMPORTANT]
> - Clonar repositorio
> - Abrir en Netbeans o Intellij
> - Abrir gestor de base de Datos (XAMPP en mi caso)
> - Crear la base de datos con el SQL facilitado en: [Abrir carpeta contenedora](https://github.com/jeremy-lorente/LorenteJeremy_pruebatec1/tree/main/src/main/java/sql)
> - Ejecutar "LorenteJeremy_pruebaTec1.java"

## Usos
> [!NOTE]
> - Ejecute el programa y mostrara un menu con 6 opciones:
>	- Crear Empleados:
>		- Permite la creación de nuevos empleados en la base de datos, verificando que su DNI no existe.
>	- Listar Empleados:
>		- Muestra todos los registros de la tabla empleado de la base de datos.
>	- Borrar Empleados:
>		- Haciendo uso de la ID borra el registro de la base de datos.
>	- Modificar Empleados:
>		- Haciendo uso de la ID modifica todos los campos del registro de la base de datos.
>	- Listar Empleados por Cargo:
>		- Lista todos los registros dentro de la base de datos, que posean el mismo cargo.
>	- Salir 

## Casos Supuestos
> [!WARNING]
> - Formato de Fecha Estandar:
>   - Se supone que las fechas ingresadas siguen el formato "yyyy-MM-dd" en caso contrario el programa seguira solicitando el ingreso de una fecha valida.
> - DNI Único por Empleado:
>   - Se ha asumido que cada empleado tiene un DNI unico. La aplicacion verifica la unicidad del DNI al crear nuevos empleados.  
	
## Documentación 
> [!NOTE]
> Todos los metodos utilizados han sido comentados y se ha generado la javadoc en la carpeta: 
> [Abrir carpeta contenedora](https://github.com/jeremy-lorente/LorenteJeremy_pruebatec1/blob/main/target/site/apidocs/)


