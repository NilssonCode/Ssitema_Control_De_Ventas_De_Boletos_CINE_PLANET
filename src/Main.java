import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    // ============================================================
    // VARIABLES GLOBALES
    // ============================================================

    static Scanner sc = new Scanner(System.in);

    static final String CINE = "CINEPLANET";
    static final String LOCAL = "JULIACA";

    // IGV incluido en los precios
    static final double IGV = 0.18;

    // Precios
    static final double PRECIO_ADULTO = 18.00;
    static final double PRECIO_NINO = 12.00;
    static final double PRECIO_ESTUDIANTE = 14.00;

    // ============================================================
    // INFORMACIÓN DE PELÍCULAS
    // ============================================================

    static String[] peliculas = {"Avatar 3", "Toy Story 5", "Spider-Man", "El Conjuro"};
    static String[] categorias = {"Ciencia ficción", "Animación", "Acción", "Terror"};
    static String[] duraciones = {"3h 10min", "1h 45min", "2h 20min", "1h 55min"};
    static String[] horarios = {"14:30", "17:00", "19:30", "22:00"};

    static String[] sinopsis = {"Jake Sully y su familia continúan su aventura en Pandora, " + "enfrentándose a nuevos peligros y descubriendo " + "otras culturas Na'vi.",
            "Woody, Buzz y sus amigos regresan en una nueva aventura " + "que pondrá a prueba su amistad frente a nuevos desafíos.",
            "Peter Parker deberá enfrentar nuevas amenazas mientras intenta " + "equilibrar su vida personal con su responsabilidad como héroe.",
            "Una familia se enfrenta a sucesos paranormales y busca ayuda " + "para descubrir el origen de una presencia aterradora."};


    // ============================================================
    // ASIENTOS
    // ============================================================

    /*
       Tenemos 4 funciones y 50 asientos por función.
       false = DISPONIBLE
       true  = OCUPADO
    */

    static boolean[][] asientos = new boolean[peliculas.length][50];


    // ============================================================
    // VARIABLES DEL SISTEMA
    // ============================================================

    static int opcion;
    static int entradasVendidas = 0;
    static double totalRecaudado = 0;

    // Para pago en efectivo
    static double montoRecibido = 0;
    static double vuelto = 0;


    // ============================================================
    // MÉTODO PRINCIPAL
    // ============================================================

    public static void main(String[] args) {

        do {
            menuPrincipal();
            switch (opcion) {
                case 1:
                    verCartelera();
                    break;
                case 2:
                    peliculasPorCategoria();
                    break;
                case 3:
                    mostrarHorarios();
                    pausaMenu();
                    break;
                case 4:
                    menuMapaAsientos();
                    break;
                case 5:
                    comprarEntradas();
                    break;
                case 6:
                    menuVerificarAsiento();
                    break;
                case 7:
                    menuDisponibilidad();
                    break;
                case 8:
                    mostrarResumenVentas();
                    pausaMenu();
                    break;
                case 9:
                    System.out.println();
                    System.out.println("╔══════════════════════════════════════════════╗");
                    System.out.println("║            SALIENDO DEL SISTEMA              ║");
                    System.out.println("║        Gracias por su preferencia            ║");
                    System.out.println("║               🎬 CINEPLANET 🎬               ║");
                    System.out.println("╚══════════════════════════════════════════════╝");
                    break;
                default:
                    System.out.println("\n⚠\uFE0F Opción incorrecta. Intente nuevamente.");
            }
        } while (opcion != 9);
        sc.close();
    }

    // ============================================================
    // 1. MENÚ PRINCIPAL
    // ============================================================

    public static void menuPrincipal() {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║           🎬 CINEPLANET - JULIACA 🎬         ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("   1. 🎥 Ver cartelera y sinopsis");
        System.out.println("   2. 🎭 Películas por categoría");
        System.out.println("   3. 🕐 Ver horarios");
        System.out.println("   4. 💺 Ver mapa de asientos");
        System.out.println("   5. 🎟 Comprar entradas");
        System.out.println("   6. 🔍 Verificar asiento");
        System.out.println("   7. ✨ Mostrar disponibilidad");
        System.out.println("   8. 📊 Resumen de ventas");
        System.out.println("   9. 🚪 Salir");
        System.out.println("────────────────────────────────────────────────");
        System.out.print("Seleccione una opción: ");
        opcion = sc.nextInt();
    }


    // ============================================================
    // 2. VER CARTELERA
    // ============================================================

    public static void verCartelera() {
        int seleccion;
        do {
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────┐");
            System.out.println("│               🎥 CARTELERA                   │");
            System.out.println("└──────────────────────────────────────────────┘");

            for (int i = 0; i < peliculas.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + peliculas[i]);
                System.out.println("   🎭 Categoría : " + categorias[i]);
                System.out.println("   ⏱ Duración   : " + duraciones[i]);
                System.out.println("   🕐 Horario    : " + horarios[i]);
            }
            System.out.println("\n────────────────────────────────────────────────");
            System.out.println("Seleccione una película para ver su sinopsis.");
            System.out.println("0. Volver al menú principal");
            System.out.print("\nPelícula: ");
            seleccion = sc.nextInt();

            if (seleccion >= 1 && seleccion <= peliculas.length) {
                mostrarDetallePelicula(seleccion - 1);
            } else if (seleccion != 0) {
                System.out.println("❌ Película incorrecta.");
            }
        } while (seleccion != 0);
    }

    // ============================================================
    // 3. MOSTRAR DETALLE / SINOPSIS
    // ============================================================

    public static void mostrarDetallePelicula(int pelicula) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║             INFORMACIÓN DE PELÍCULA          ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("  🎬 Título     : " + peliculas[pelicula]);
        System.out.println("  🎭 Categoría  : " + categorias[pelicula]);
        System.out.println("  ⏱ Duración   : " + duraciones[pelicula]);
        System.out.println("  🕐 Horario    : " + horarios[pelicula]);
        System.out.println("\n────────────── SINOPSIS ───────────────────────");
        System.out.println(sinopsis[pelicula]);
        System.out.println("────────────────────────────────────────────────");

        int volver;

        do {
            System.out.println("\n0. Volver a la cartelera");
            System.out.print("Seleccione: ");

            volver = sc.nextInt();

            if (volver != 0) {
                System.out.println("❌ Ingrese 0 para regresar.");
            }
        } while (volver != 0);
    }

    // ============================================================
    // 4. PELÍCULAS POR CATEGORÍA
    // ============================================================

    public static void peliculasPorCategoria() {

        int opcionCategoria;

        do {
            System.out.println();
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║               🎭 CATEGORÍAS                  ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("   1. 💥 Acción");
            System.out.println("   2. 🧸 Animación");
            System.out.println("   3. 👻 Terror");
            System.out.println("   4. 🚀 Ciencia ficción");
            System.out.println("   5. 🎬 Mostrar todas");
            System.out.println("   0. ↩ Volver al menú principal");
            System.out.println("────────────────────────────────────────────────");
            System.out.print("Seleccione una categoría: ");
            opcionCategoria = sc.nextInt();

            switch (opcionCategoria) {
                case 1:
                    buscarCategoria("Acción");
                    break;
                case 2:
                    buscarCategoria("Animación");
                    break;
                case 3:
                    buscarCategoria("Terror");
                    break;
                case 4:
                    buscarCategoria("Ciencia ficción"
                    );
                    break;
                case 5:
                    mostrarTodasCategorias();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Categoría incorrecta.");
            }
        } while (opcionCategoria != 0);
    }

    // ============================================================
    // 5. BUSCAR POR CATEGORÍA
    // ============================================================

    public static void buscarCategoria(String categoriaBuscada) {

        System.out.println();
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.println("        🎬 PELÍCULAS DE " + categoriaBuscada.toUpperCase());
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");

        boolean encontrado = false;

        for (int i = 0; i < peliculas.length; i++) {
            if (categorias[i].equalsIgnoreCase(categoriaBuscada)) {
                System.out.println("\n★ " + peliculas[i]);
                System.out.println("  Duración : " + duraciones[i]);
                System.out.println("  Horario  : " + horarios[i]);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("\nNo existen películas disponibles.");
        }
        System.out.println("\n────────────────────────────────────────────────");
    }

    // ============================================================
    // 6. MOSTRAR TODAS LAS CATEGORÍAS
    // ============================================================

    public static void mostrarTodasCategorias() {

        System.out.println();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃           🎬 TODAS LAS PELÍCULAS             ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        for (int i = 0; i < peliculas.length; i++) {
            System.out.println("\n★ " + peliculas[i]);
            System.out.println("  🎭 " + categorias[i]);
            System.out.println("  ⏱ " + duraciones[i]);
            System.out.println("  🕐 " + horarios[i]);
        }

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // ============================================================
    // 7. MOSTRAR HORARIOS
    // ============================================================

    public static void mostrarHorarios() {

        System.out.println();
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│               🕐 HORARIOS                    │");
        System.out.println("└──────────────────────────────────────────────┘");

        for (int i = 0; i < peliculas.length; i++) {
            System.out.println((i + 1) + ". " + peliculas[i] + "  →  " + horarios[i]);
        }
        System.out.println("────────────────────────────────────────────────");
    }

    // ============================================================
    // 8. SELECCIONAR FUNCIÓN
    // ============================================================

    public static int seleccionarFuncion() {

        System.out.println();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                 FUNCIONES                    ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        for (int i = 0; i < peliculas.length; i++) {
            System.out.println((i + 1) + ". " + peliculas[i] + " - " + horarios[i]);
        }

        System.out.println("0. Volver al menú principal");
        System.out.print("\nSeleccione una función: ");

        int funcion = sc.nextInt();

        if (funcion == 0) {
            return -1;
        }

        if (funcion < 1 || funcion > peliculas.length) {
            System.out.println("❌ Función incorrecta.");
            return -1;
        }
        return funcion - 1;
    }

    // ============================================================
    // 9. MOSTRAR MAPA DE ASIENTOS
    // ============================================================

    public static void mostrarMapaAsientos(int funcion) {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     💺 MAPA DE ASIENTOS                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("🎬 Película : " + peliculas[funcion]);
        System.out.println("🕐 Horario  : " + horarios[funcion]);
        System.out.println("\n                       PANTALLA");
        System.out.println("──────────────────────────────────────────────────────────────");

        for (int i = 0; i < 50; i++) {
            if (asientos[funcion][i]) {System.out.printf("[ XX ] ");
            } else {
                System.out.printf("[%3d ] ", i + 1);
            }

            // 10 asientos por fila
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }

        System.out.println("\n[XX] = OCUPADO");
        System.out.println("[N°] = DISPONIBLE");
    }

    // ============================================================
    // 10. MENÚ MAPA DE ASIENTOS
    // ============================================================

    public static void menuMapaAsientos() {
        int repetir;
        do {
            int funcion = seleccionarFuncion();
            if (funcion == -1) {
                return;
            }
            mostrarMapaAsientos(funcion);

            System.out.println("\n1. Consultar otra función");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            repetir = sc.nextInt();
        } while (repetir == 1);
    }

    // ============================================================
    // 11. COMPRAR ENTRADAS
    // ============================================================

    public static void comprarEntradas() {

        int nuevaCompra;
        do {
            System.out.println();
            System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
            System.out.println("                      🎟 COMPRA DE ENTRADAS");
            System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

            int funcion = seleccionarFuncion();

            if (funcion == -1) {
                return;
            }

            // Mostrar mapa antes de comprar
            mostrarMapaAsientos(funcion
            );

            // Limpiamos salto pendiente
            sc.nextLine();

            // ====================================================
            // DATOS DEL CLIENTE
            // ====================================================
            System.out.println("\n────────── DATOS DEL CLIENTE ──────────");
            System.out.print("Nombre del cliente: ");
            String cliente = sc.nextLine();
            System.out.print("DNI: ");
            String dni = sc.nextLine();
            System.out.print("Cantidad de entradas (máximo 10): ");
            int cantidad = sc.nextInt();

            if (cantidad <= 0 || cantidad > 10) {
                System.out.println("❌ Cantidad incorrecta.");
                return;
            }

            // ====================================================
            // ARRAYS DE LA COMPRA
            // ====================================================

            int[] asientosComprados = new int[cantidad];
            double[] precios = new double[cantidad];
            String[] tipos = new String[cantidad];
            double total = 0;
            boolean cancelarCompra = false;

            // ====================================================
            // SELECCIONAR ASIENTOS
            // ====================================================

            for (int i = 0; i < cantidad; i++) {
                System.out.println();
                System.out.println("┌──────────────────────────────────────┐");
                System.out.println("│              ENTRADA " + (i + 1) + " │");
                System.out.println("└──────────────────────────────────────┘");

                int numero;

                while (true) {
                    System.out.println("0. Cancelar compra");
                    System.out.print("Número de asiento (1-50): ");
                    numero = sc.nextInt();

                    // CANCELAR
                    if (numero == 0) {
                        cancelarCompra = true;
                        break;
                    }

                    // FUERA DE RANGO
                    if (numero < 1 || numero > 50) {
                        System.out.println("❌ Número de asiento inválido.");
                    }
                    // ASIENTO YA OCUPADO
                    else if (asientos[funcion][numero - 1]) {
                        System.out.println("❌ El asiento " + numero + " está OCUPADO.");
                        System.out.println("Seleccione otro asiento.");
                    }
                    // ASIENTO REPETIDO EN LA MISMA COMPRA
                    else if (asientoYaSeleccionado(asientosComprados, numero, i)) {
                        System.out.println("❌ Ya seleccionó ese asiento.");
                    }
                    // CORRECTO
                    else {
                        break;
                    }
                }

                if (cancelarCompra) {
                    break;
                }

                // =================================================
                // TIPO DE ENTRADA
                // =================================================
                System.out.println("\n──────── TIPO DE ENTRADA ────────");
                System.out.println("1. Adulto     - S/ " + PRECIO_ADULTO);
                System.out.println("2. Niño       - S/ " + PRECIO_NINO);
                System.out.println("3. Estudiante - S/ " + PRECIO_ESTUDIANTE);
                System.out.print("Seleccione: ");

                int tipo = sc.nextInt();
                double precio;
                String nombreTipo;

                switch (tipo) {
                    case 1:
                        precio = PRECIO_ADULTO;
                        nombreTipo = "Adulto";
                        break;
                    case 2:
                        precio = PRECIO_NINO;
                        nombreTipo = "Niño";
                        break;
                    case 3:
                        precio = PRECIO_ESTUDIANTE;
                        nombreTipo = "Estudiante";
                        break;
                    default:
                        System.out.println("⚠ Tipo incorrecto.");
                        System.out.println("Se asignará entrada Adulto.");
                        precio = PRECIO_ADULTO;
                        nombreTipo = "Adulto";
                }

                // Guardamos la información
                asientosComprados[i] = numero;
                precios[i] = precio;
                tipos[i] = nombreTipo;

                // Acumulador
                total = total + precio;
            }

            // ====================================================
            // SI CANCELÓ
            // ====================================================
            if (cancelarCompra) {
                System.out.println();
                System.out.println("❌ COMPRA CANCELADA.");
                System.out.println("No se ocupó ningún asiento.");
            }
            // ====================================================
            // SI TERMINÓ DE ELEGIR
            // ====================================================
            else {
                // ================================================
                // RESUMEN
                // ================================================
                System.out.println();
                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃             RESUMEN DE COMPRA                ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.println("Cliente  : " + cliente);
                System.out.println("DNI      : " + dni);
                System.out.println("Película : " + peliculas[funcion]);
                System.out.println("Horario  : " + horarios[funcion]);
                System.out.println("────────────────────────────────────────────────");

                for (int i = 0; i < cantidad; i++) {
                    System.out.printf("Asiento %-3d %-12s S/ %.2f%n", asientosComprados[i], tipos[i], precios[i]);
                }
                System.out.println("────────────────────────────────────────────────");
                System.out.printf("TOTAL A PAGAR: S/ %.2f%n", total);
                System.out.println("\n1. Confirmar compra");
                System.out.println("0. Cancelar compra");
                System.out.print("Seleccione: ");

                int confirmar = sc.nextInt();

                // ================================================
                // CONFIRMACIÓN
                // ================================================

                if (confirmar == 1) {
                    // MÉTODO DE PAGO
                    String metodoPago = seleccionarMetodoPago(total);
                    if (metodoPago.equals("CANCELADO")) {
                        System.out.println("\n❌ COMPRA CANCELADA.");
                        System.out.println("Los asientos continúan disponibles.");
                    }
                    else {
                        // =========================================
                        // MARCAR ASIENTOS OCUPADOS
                        // =========================================
                        for (int i = 0; i < cantidad; i++) {
                            int posicion = asientosComprados[i] - 1;
                            asientos[funcion][posicion] = true;
                        }

                        // =========================================
                        // ACTUALIZAR ESTADÍSTICAS
                        // =========================================
                        entradasVendidas = entradasVendidas + cantidad;
                        totalRecaudado = totalRecaudado + total;

                        System.out.println();
                        System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
                        System.out.println("                  ✅ COMPRA REALIZADA CON ÉXITO");
                        System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

                        // =========================================
                        // GENERAR BOLETA
                        // =========================================

                        generarBoleta(cliente, dni, funcion, asientosComprados, tipos, precios, total, metodoPago);
                    }
                } else {
                    System.out.println("\n❌ Compra cancelada.");
                    System.out.println("Los asientos continúan disponibles.");
                }
            }

            // ====================================================
            // REALIZAR OTRA COMPRA
            // ====================================================

            System.out.println();
            System.out.println("────────────────────────────────────────────────");
            System.out.println("1. Realizar otra compra");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            nuevaCompra = sc.nextInt();
        } while (nuevaCompra == 1);
    }

    // ============================================================
    // 12. COMPROBAR ASIENTO REPETIDO
    // ============================================================

    public static boolean asientoYaSeleccionado(int[] seleccionados, int numero, int cantidadSeleccionada) {

        for (int i = 0; i < cantidadSeleccionada; i++) {
            if (seleccionados[i] == numero) {
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // 13. MÉTODO DE PAGO
    // ============================================================

    public static String seleccionarMetodoPago(double total) {

        int opcionPago;

        // Reiniciar valores
        montoRecibido = 0;
        vuelto = 0;

        do {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃              💳 MÉTODO DE PAGO               ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("1. 💵 Efectivo");
            System.out.println("2. 💳 Tarjeta");
            System.out.println("3. 📱 Yape / Plin");
            System.out.println("0. Cancelar compra");
            System.out.println("────────────────────────────────────────────────");
            System.out.printf("TOTAL A PAGAR: S/ %.2f%n", total);
            System.out.print("\nSeleccione: ");

            opcionPago = sc.nextInt();

            switch (opcionPago) {
                // ================================================
                // EFECTIVO
                // ================================================
                case 1:
                    System.out.println("\n────────── PAGO EN EFECTIVO ──────────");
                    do {
                        System.out.printf("Monto a pagar: S/ %.2f%n", total);
                        System.out.print("Monto recibido: S/ ");
                        montoRecibido = sc.nextDouble();

                        if (montoRecibido < total) {
                            System.out.println("❌ Monto insuficiente.");
                            System.out.println("Ingrese nuevamente.");
                        }
                    } while (montoRecibido < total);

                    vuelto = montoRecibido - total;

                    System.out.printf("Vuelto: S/ %.2f%n", vuelto);
                    return "Efectivo";

                // ================================================
                // TARJETA
                // ================================================
                case 2:
                    montoRecibido = total;
                    vuelto = 0;
                    System.out.println("\n💳 Pago con tarjeta procesado correctamente.");
                    return "Tarjeta";

                // ================================================
                // YAPE / PLIN
                // ================================================
                case 3:
                    montoRecibido = total;
                    vuelto = 0;
                    System.out.println("\n📱 Pago mediante Yape / Plin registrado.");
                    return "Yape / Plin";

                // ================================================
                // CANCELAR
                // ================================================
                case 0:
                    return "CANCELADO";
                default:
                    System.out.println("❌ Método de pago incorrecto.");
            }
        } while (true);
    }

    // ============================================================
    // 14. GENERAR BOLETA
    // ============================================================
    public static void generarBoleta(String cliente, String dni, int funcion, int[] asientosComprados, String[] tipos, double[] precios, double total, String metodoPago) {

        // ========================================================
        // FECHA Y HORA
        // ========================================================
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fecha = ahora.format(formato);

        // Número de operación
        String numeroBoleta = "CP-" + System.currentTimeMillis();

        // ========================================================
        // CALCULAR IGV
        // ========================================================
        /*
           Los precios YA incluyen IGV.
           Por eso:
           Valor venta = total / 1.18
           IGV = total - valor venta
        */
        double valorVenta = total / (1 + IGV);
        double montoIGV = total - valorVenta;

        // ========================================================
        // MOSTRAR BOLETA
        // ========================================================

        System.out.println();
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║                  CINEPLANET                    ║");
        System.out.println("║                    JULIACA                     ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                 BOLETA DE VENTA                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("N° Boleta : " + numeroBoleta);
        System.out.println("Fecha     : " + fecha);
        System.out.println("Cliente   : " + cliente);
        System.out.println("DNI       : " + dni);
        System.out.println("────────────────────────────────────────────────");
        System.out.println("Película  : " + peliculas[funcion]);
        System.out.println("Categoría : " + categorias[funcion]);
        System.out.println("Horario   : " + horarios[funcion]);
        System.out.println("────────────────────────────────────────────────");
        System.out.printf("%-10s %-15s %12s%n", "ASIENTO", "TIPO", "PRECIO");
        System.out.println("────────────────────────────────────────────────");

        for (int i = 0; i < asientosComprados.length; i++) {
            System.out.printf("%-10d %-15s S/ %8.2f%n", asientosComprados[i], tipos[i], precios[i]);
        }
        System.out.println("────────────────────────────────────────────────");
        System.out.printf("%-28s S/ %8.2f%n", "Valor de venta:", valorVenta);
        System.out.printf("%-28s S/ %8.2f%n", "IGV (18%):", montoIGV);
        System.out.println("────────────────────────────────────────────────");
        System.out.printf("%-28s S/ %8.2f%n", "TOTAL:", total);
        System.out.println("────────────────────────────────────────────────");
        System.out.println("Método de pago : " + metodoPago);

        // Solo efectivo muestra recibido y vuelto
        if (metodoPago.equals("Efectivo")) {
            System.out.printf("Monto recibido  : S/ %.2f%n", montoRecibido);
            System.out.printf("Vuelto          : S/ %.2f%n", vuelto);
        }

        System.out.println("════════════════════════════════════════════════");
        System.out.println("         ★ ¡GRACIAS POR SU COMPRA! ★");
        System.out.println("           🍿 DISFRUTE SU PELÍCULA");
        System.out.println("════════════════════════════════════════════════");

        // ========================================================
        // GUARDAR EN ARCHIVO
        // ========================================================
        guardarBoletaArchivo(numeroBoleta, fecha, cliente, dni, funcion, asientosComprados, tipos, precios, valorVenta, montoIGV, total, metodoPago);
    }

    // ============================================================
    // 15. GUARDAR BOLETA EN ARCHIVO TXT
    // ============================================================

    public static void guardarBoletaArchivo(String numeroBoleta, String fecha, String cliente, String dni, int funcion, int[] asientosComprados, String[] tipos, double[] precios, double valorVenta, double montoIGV, double total, String metodoPago) {

        String nombreArchivo = "Boleta_" + numeroBoleta + ".txt";

        try {
            FileWriter archivo = new FileWriter(nombreArchivo);
            PrintWriter boleta = new PrintWriter(archivo);
            boleta.println("================================================");
            boleta.println("                 CINEPLANET");
            boleta.println("                   JULIACA");
            boleta.println("================================================");
            boleta.println("               BOLETA DE VENTA");
            boleta.println("------------------------------------------------");
            boleta.println("N° Boleta : " + numeroBoleta);
            boleta.println("Fecha     : " + fecha);
            boleta.println("Cliente   : " + cliente);
            boleta.println("DNI       : " + dni);
            boleta.println("------------------------------------------------");
            boleta.println("Película  : " + peliculas[funcion]);
            boleta.println("Categoría : " + categorias[funcion]);
            boleta.println("Horario   : " + horarios[funcion]);
            boleta.println("------------------------------------------------");
            boleta.printf("%-10s %-15s %12s%n", "ASIENTO", "TIPO", "PRECIO");
            boleta.println("------------------------------------------------");

            for (int i = 0; i < asientosComprados.length; i++) {
                boleta.printf("%-10d %-15s S/ %8.2f%n", asientosComprados[i], tipos[i], precios[i]);
            }

            boleta.println("------------------------------------------------");
            boleta.printf("%-28s S/ %8.2f%n", "Valor de venta:", valorVenta);
            boleta.printf("%-28s S/ %8.2f%n", "IGV (18%):", montoIGV);
            boleta.println("------------------------------------------------");
            boleta.printf("%-28s S/ %8.2f%n", "TOTAL:", total);
            boleta.println("------------------------------------------------");
            boleta.println("Método de pago : " + metodoPago);

            if (metodoPago.equals("Efectivo")) {
                boleta.printf("Monto recibido  : S/ %.2f%n", montoRecibido);
                boleta.printf("Vuelto          : S/ %.2f%n", vuelto);
            }
            boleta.println("================================================");
            boleta.println("          ¡GRACIAS POR SU COMPRA!");
            boleta.println("           DISFRUTE SU PELÍCULA");
            boleta.println("================================================");

            // Cerrar archivo
            boleta.close();

            System.out.println();
            System.out.println("📄 Boleta generada correctamente.");
            System.out.println("Archivo: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("❌ Error al generar la boleta.");
        }
    }

    // ============================================================
    // 16. VERIFICAR ASIENTO
    // ============================================================
    public static void menuVerificarAsiento() {
        int repetir;
        do {
            int funcion = seleccionarFuncion();
            if (funcion == -1) {
                return;
            }
            mostrarMapaAsientos(funcion);
            System.out.print("\nNúmero de asiento: ");

            int numero = sc.nextInt();

            if (numero < 1 || numero > 50) {
                System.out.println("❌ Número de asiento inválido.");
            } else {
                int posicion = numero - 1;
                if (asientos[funcion][posicion]) {
                    System.out.println("❌ Asiento " + numero + ": OCUPADO");
                } else {
                    System.out.println("✅ Asiento " + numero + ": DISPONIBLE");
                }
            }
            System.out.println("\n1. Verificar otro asiento");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            repetir = sc.nextInt();
        } while (repetir == 1);
    }

    // ============================================================
    // 17. DISPONIBILIDAD
    // ============================================================
    public static void menuDisponibilidad() {
        int repetir;
        do {
            int funcion = seleccionarFuncion();
            if (funcion == -1) {
                return;
            }
            int disponibles = 0;
            int ocupados = 0;
            for (int i = 0; i < asientos[funcion].length; i++) {
                if (asientos[funcion][i]) {
                    ocupados++;
                } else {
                    disponibles++;
                }
            }

            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃          DISPONIBILIDAD DE ASIENTOS          ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println("🎬 Película    : " + peliculas[funcion]);
            System.out.println("🕐 Horario     : " + horarios[funcion]);
            System.out.println("✨ Disponibles : " + disponibles);
            System.out.println("❌ Ocupados    : " + ocupados);
            System.out.println("💺 Total       : " + asientos[funcion].length);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("\n1. Consultar otra función");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            repetir = sc.nextInt();
        } while (repetir == 1);
    }

    // ============================================================
    // 18. RESUMEN DE VENTAS
    // ============================================================
    public static void mostrarResumenVentas() {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║             📊 RESUMEN DE VENTAS             ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("🎟 Entradas vendidas : " + entradasVendidas);
        System.out.printf("💰 Total recaudado   : S/ %.2f%n", totalRecaudado);

        // IGV total incluido en las ventas
        double valorVentaTotal = totalRecaudado / (1 + IGV);
        double igvTotal = totalRecaudado - valorVentaTotal;

        System.out.printf("📋 Valor de venta    : S/ %.2f%n", valorVentaTotal);
        System.out.printf("📋 IGV incluido      : S/ %.2f%n", igvTotal);
        System.out.println("════════════════════════════════════════════════");
    }

    // ============================================================
    // 19. PAUSA / VOLVER AL MENÚ
    // ============================================================
    public static void pausaMenu() {
        int volver;

        do {
            System.out.println("\n0. Volver al menú principal");
            System.out.print("Seleccione: ");

            volver = sc.nextInt();

            if (volver != 0) {
                System.out.println("❌ Opción incorrecta. Ingrese 0.");
            }
        } while (volver != 0);
    }
}
