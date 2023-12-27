package com.lorente.jeremy;

import com.lorente.jeremy.servicios.EmpleadoServicio;
import java.util.Scanner;

public class LorenteJeremy_pruebaTec1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmpleadoServicio empleadoServicio = new EmpleadoServicio();
        while (true) {
            pintarOpciones();
            int opcion = validarOpcion(sc);
            sc.nextLine();

            switch (opcion) {
                case 1:
                    empleadoServicio.ingresarEmpleado();
                    break;
                case 2:
                    empleadoServicio.listarEmpleados();
                    break;
                case 3:
                    empleadoServicio.borrarEmpleado();
                    break;
                case 4:
                    empleadoServicio.modificarEmpleado();
                    break;
                case 5:
                    empleadoServicio.listarEmpladosCargo();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, ingrese una opcion valida.");
                    break;
            }

        }
    }

    /**
     * Muestra las opciones disponibles del menu
     */
    private static void pintarOpciones() {

        System.out.println("=========================================");
        System.out.println("           Menu Principal               ");
        System.out.println("=========================================");
        System.out.println("1. Crear empleados");
        System.out.println("2. Listar empleados");
        System.out.println("3. Borrar empleados");
        System.out.println("4. Modificar empleados");
        System.out.println("5. Listar empleados por cargo");
        System.out.println("0. Salir");
        System.out.println("=========================================");
        System.out.println("Ingrese la opcion deseada:");
    }

    /**
     * Valida y devuelve la opcion ingresada por el usuario
     *
     * @param sc Objeto Scanner para la entrada de datos
     * @return La opcion valida ingresada por el usuario.
     */
    private static int validarOpcion(Scanner sc) {
        int opcion;
        while (true) {

            try {
                opcion = sc.nextInt();
                if (opcion >= 0 && opcion <= 5) {
                    break;
                } else {
                    System.out.println("Opcion no valida. Ingrese un numero entre el 0 y el 5.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un numero entero valido.");
                sc.nextLine();
            }
        }
        return opcion;
    }
}
