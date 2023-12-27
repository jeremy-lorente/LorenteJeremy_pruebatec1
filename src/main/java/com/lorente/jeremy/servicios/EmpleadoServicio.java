package com.lorente.jeremy.servicios;

import com.lorente.jeremy.modelos.Empleado;
import com.lorente.jeremy.persistencia.EmpleadoJpaController;
import com.lorente.jeremy.persistencia.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para operaciones relacionadas con la entidad Empleado
 *
 * Este servicio proporciona metodos para realizar operaciones CRUD en la
 * entidad Empleado, como ingresar, listar, listar por cargo, modificar, borrar
 *
 */
public class EmpleadoServicio {

    EmpleadoJpaController empleadoController = new EmpleadoJpaController();
    Scanner sc = new Scanner(System.in);

    /**
     * Ingresa un nuevo empleado en la BD.
     */
    public void ingresarEmpleado() {

        System.out.println("=========================================");
        System.out.println("           Creacion de Empleado          ");
        System.out.println("=========================================");

        System.out.println("Ingresa el nombre del empleado");
        String empleadoNombre = validarString(sc.nextLine());
        System.out.println("Ingresa el apellido del empleado");
        String empleadoApellido = validarString(sc.nextLine());
        System.out.println("Ingresa el cargo del empleado");
        String empleadoCargo = validarString(sc.nextLine().toLowerCase());
        System.out.println("Ingresa el salario del empleado");
        double empleadoSalario = validarSalario(sc);

        LocalDate empleadoFechaInicio = validarFecha(sc);

        Empleado empleadoACrear = new Empleado(empleadoNombre, empleadoApellido, empleadoCargo, empleadoSalario, empleadoFechaInicio);

        System.out.println("\nCreando Empleado...");
        System.out.println("=========================================");
        empleadoController.create(empleadoACrear);
        System.out.println("Empleado creado correctamente");
    }

    /**
     * Borra un empleado en la BD dependiendo de su ID.
     */
    public void borrarEmpleado() {
        System.out.println("=========================================");
        System.out.println("      Eliminacion de Empleado Por ID      ");
        System.out.println("=========================================");
        System.out.println("Ingresa el ID del empleado a eliminar");

        int id = validarID(sc);
        if (empleadoController.findEmpleado(id) != null) {
            try {
                System.out.println("\nBorrando Empleado...");
                System.out.println("=========================================");
                empleadoController.destroy(id);
                System.out.println("Empleado borrado correctamente");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(EmpleadoServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No se puede eliminar el usuario con id: " + id + " ya que no existe");
        }
    }

    /**
     * Muestra una lista de todos los empleados existentes en la BD.
     */
    public void listarEmpleados() {
        List<Empleado> listaEmpleados = empleadoController.findEmpleadoEntities();

        System.out.println("=========================================");
        System.out.println("           Listado de Empleados          ");
        System.out.println("=========================================");
        if (listaEmpleados.isEmpty()) {
            System.out.println("La tabla se encuentra vacia actualmente");
        } else {
            for (Empleado empleado : listaEmpleados) {
                System.out.println("ID: " + empleado.getId());
                System.out.println("Nombre: " + empleado.getNombre());
                System.out.println("Apellido: " + empleado.getApellido());
                System.out.println("Cargo: " + empleado.getCargo());
                System.out.println("Salario: " + empleado.getSalario());
                System.out.println("Fecha de inicio: " + empleado.getFechaInicio());
                System.out.println("-----------------------------------------");

            }
        }

    }

    /**
     * Muestra una lista de todos los empleados existentes en la BD segun su
     * cargo
     */
    public void listarEmpladosCargo() {
        System.out.println("=========================================");
        System.out.println("        Listar Empleados por Cargo       ");
        System.out.println("=========================================");

        System.out.println("Ingrese el cargo para listar empleados: ");
        String cargo = sc.nextLine().toLowerCase();

        List<Empleado> listaEmpleados = empleadoController.findEmpleadoByCharge(cargo);

        if (listaEmpleados.isEmpty()) {
            System.out.println("No se encontraron empleados con el cargo: " + cargo);
        } else {
            for (Empleado empleado : listaEmpleados) {
                System.out.println("ID: " + empleado.getId());
                System.out.println("Nombre: " + empleado.getNombre());
                System.out.println("Apellido: " + empleado.getApellido());
                System.out.println("Cargo: " + empleado.getCargo());
                System.out.println("Salario: " + empleado.getSalario());
                System.out.println("Fecha de inicio: " + empleado.getFechaInicio());
                System.out.println("-----------------------------------------");

            }
        }

    }

    /**
     * Modifica todos los campos de un empleado existente en la BD segun su ID.
     */
    public void modificarEmpleado() {
        System.out.println("=========================================");
        System.out.println("        Modificar Empleado Por ID        ");
        System.out.println("=========================================");
        System.out.println("Ingresa el ID del empleado a modificar");
        int id = validarID(sc);

        if (empleadoController.findEmpleado(id) != null) {
            try {
                System.out.println("Ingresa el nuevo nombre del empleado");
                String empleadoNombre = validarString(sc.nextLine());
                System.out.println("Ingresa el nuevo apellido del empleado");
                String empleadoApellido = validarString(sc.nextLine());
                System.out.println("Ingresa el nuevo cargo del empleado");
                String empleadoCargo = validarString(sc.nextLine().toLowerCase());
                System.out.println("Ingresa el nuevo salario del empleado");
                double empleadoSalario = validarSalario(sc);

                LocalDate empleadoFechaInicio = validarFecha(sc);

                Empleado empleadoAModificar = empleadoController.findEmpleado(id);
                empleadoAModificar.setNombre(empleadoNombre);
                empleadoAModificar.setApellido(empleadoApellido);
                empleadoAModificar.setCargo(empleadoCargo);
                empleadoAModificar.setSalario(empleadoSalario);
                empleadoAModificar.setFechaInicio(empleadoFechaInicio);

                System.out.println("\nModiciando Empleado...");
                System.out.println("=========================================");
                empleadoController.edit(empleadoAModificar);
                System.out.println("Empleado modificado correctamente");

            } catch (Exception ex) {
                Logger.getLogger(EmpleadoJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("El empleado con id: " + id + " no existe, y no puede ser modificado ");
        }
    }

    /**
     * Valida que la entrada de datos de tipo String no este vacia.
     *
     * @param string El valor a validar.
     * @return El valor valido sin espacios al inicio y al final.
     */
    private String validarString(String string) {
        while (string == null || string.trim().isEmpty()) {
            System.out.println("El campo no puede estar vacio. Por favor ingrese un valor valido");
            string = sc.nextLine();
        }
        return string.trim();
    }

    /**
     * Valida que la entrada de datos de tipo double no este vacia y sea un
     * numero positivo
     *
     * @param sc Scanner utilizado para la entrada de datos.
     * @return El salario validado.
     */
    private double validarSalario(Scanner sc) {
        double salario = 0;
        boolean valido = false;

        do {
            try {
                salario = sc.nextDouble();
                sc.nextLine();
                if (salario > 0) {
                    valido = true;
                } else {
                    System.out.println("El salario debe ser un numero positivo");
                }
            } catch (Exception e) {
                System.out.println("El dato es invalido. Debe ingresar un numero");
                sc.nextLine();
            }
        } while (!valido);
        return salario;
    }

    /**
     * Valida que la entrada de datos de tipo LocalData sea una fecha valida
     * segun el patron yyyy-MM-dd.
     *
     * @param sc Scanner utilizado para la entrada de datos.
     * @return La Fecha validada.
     */
    private LocalDate validarFecha(Scanner sc) {
        DateTimeFormatter patron = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = null;
        boolean valido = false;

        do {
            try {
                System.out.println("Ingresa la fecha de inicio del empleado(yyyy-MM-dd)");
                String fechaInicioStr = sc.nextLine();
                fecha = LocalDate.parse(fechaInicioStr, patron);
                valido = true;
            } catch (Exception e) {
                System.out.println("Error al convertir la fecha. Asegurese de usar el formato correcto (yyyy-MM-dd) ");
            }
        } while (!valido);
        return fecha;
    }

    /**
     * Valida que la entrada de datos de tipo int sea un ID positivo
     *
     * @param sc Scanner utilizado para la entrada de datos.
     * @return La ID validada.
     */
    private int validarID(Scanner sc) {
        boolean valido = false;
        int id = 0;
        do {
            try {
                id = sc.nextInt();
                sc.nextLine();
                if (id > 0) {
                    valido = true;
                } else {
                    System.out.println("El ID debe ser un numero entero mayor a 0");
                }

            } catch (Exception e) {
                System.out.println("Error al introducir el ID. Debe ser un numero entero");
                sc.nextLine();
            }
        } while (!valido);
        return id;
    }

}
