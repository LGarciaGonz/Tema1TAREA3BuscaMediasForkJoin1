import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "Busca Medias ForkJoin"                                                                                                      *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM                                                                                                *
 **********************************************************************************************************************************************
 *   @author  Laia García                                                                                                                     *
 *   @version 1.1 - Retoques en la codificación y en los comentarios.                                                                         *
 *            1.0 - Versión inicial del algoritmo.                                                                                            *
 *   @since   13NOV2023                                                                                                                       *
 *            09NOV2023                                                                                                                       *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *      - Calcular de forma iterativa mediante dos tareas paralelas la media de cada una de las dos mitades de un array.                      *
 *      - Realizado mediante el uso del framework Fork-Join.                                                                                  *
 **********************************************************************************************************************************************/

public class CalculaMedia extends RecursiveTask<Double> {

    // CREAR CLASE CalculaMedia -----------------------------------
    private static final int LONGITUD = 1_000;
    private int a_InicioArray = 0, a_FinArray = LONGITUD;
    private int[] a_ArrayNumeros = null;

    // ASIGNACIÓN DE VALORES -------------------------------------
    public CalculaMedia(int a_InicioArray, int a_FinArray, int[] a_ArrayNumeros) {
        this.a_InicioArray = a_InicioArray;
        this.a_FinArray = a_FinArray;
        this.a_ArrayNumeros = a_ArrayNumeros;
    }

    public CalculaMedia() {

    } // Clase CalculaMedia

    public static void main(String[] args) {

        // TÍTULO ----------------
        System.out.println("\n******[ CÁLCULO DE MEDIAS FORK-JOIN ITERATIVO ]******");

        // DECLARACIÓN DE VARIABLES --------------------------
        int[] l_ArrayNumeros = null;
        int l_InicioArray = 0, l_MitadArray = LONGITUD / 2, l_FinArray = LONGITUD;
        double l_PrimeraMedia, l_SegundaMedia;

        l_ArrayNumeros = rellenarArray();           // Llamada para rellenar el array de números.

        // CREAR TAREAS, INVOCAR Y RECOGER LOS RESULTADOS OBTENIDOS -----------------------------------------------------
        ForkJoinPool l_Pool = new ForkJoinPool();

        CalculaMedia l_Tarea = new CalculaMedia();
        CalculaMedia l_Tarea2 = new CalculaMedia();

        l_Tarea = new CalculaMedia(l_InicioArray, l_MitadArray, l_ArrayNumeros);
        l_PrimeraMedia = l_Pool.invoke(l_Tarea);

        l_Tarea2 = new CalculaMedia(l_MitadArray, l_FinArray, l_ArrayNumeros);
        l_SegundaMedia = l_Pool.invoke(l_Tarea2);

        // Imprimir las medias obtenidas en la ejecución y sus rangos -------------------------------------------
        System.out.println("\n 1ª MITAD: Inicio: " + l_InicioArray + " Final: " + l_MitadArray);
        System.out.println("MEDIA: " + l_PrimeraMedia);

        System.out.println("\n 2ª MITAD: Inicio: " + l_MitadArray + " Final: " + l_FinArray);
        System.out.println("MEDIA: " + l_SegundaMedia);

    } // main()


    public static int[] rellenarArray() {

        int[] l_ArrayNumeros = new int[LONGITUD];       // Crear array del tamaño establecido.

        // Recorrer las posiciones para rellenar el array con los índices.
        for (int l_Contador = 0; l_Contador < l_ArrayNumeros.length; l_Contador++) {
            l_ArrayNumeros[l_Contador] = l_Contador;
        } // for()

        return l_ArrayNumeros;      // Retornar el array ya rellenado con los índices.
    } // rellenarArray()

    @Override
    protected Double compute() {

        // DECLARACIÓN DE VARIABLES
        double l_Media = 0;
        int l_ContadorNumeros = 0;

        // Recorrer todas las posiciones del array y almacenar en la variable la suma de los números.
        for (int l_Contador = a_InicioArray; l_Contador < a_FinArray; l_Contador++) {

            l_Media = l_Media + a_ArrayNumeros[l_Contador];
            l_ContadorNumeros++;

        } // for()

        // Calcular la media con la suma de los números y el contador.
        l_Media = l_Media / l_ContadorNumeros;

        return l_Media;     // Retornar el resultado obtenido en la media.
    } // compute()
}