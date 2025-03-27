//Método que simula la estructura de datos cola circular
public class Cola<T> {

    //Atributos esenciales para el funcionamiento de la clase
    private T[] cola;
    private int inicio, fin;

    /**
     * Constructor preterminado, donde es vacio y se da un tamaño especifico preterminado
     */
    public Cola()
    {
        cola = (T[]) new Object[1000];
        inicio = -1;
        fin = -1;
    }

    /**
     * Constructor donde se especifica la cantidad de elementos que puede tener la cola
     * @param MAX
     */
    public Cola(int MAX)
    {
        cola = (T[]) new Object[MAX];
        inicio = -1;
        fin = -1;
    }

    /**
     * Método para saber si la cola está llena
     * @return
     */
    public boolean colaLena()
    {
        return (fin + 1) % cola.length == inicio;
    }

    /**
     * Método para saber si la cola esta vacia
     * @return
     */
    public boolean colaVacia()
    {
        return inicio == -1;
    }

    /**
     * Método para insertar elementos a la cola
     * @param object
     */
    public void insertar(T object)
    {
        if (colaLena()) {
            System.out.println("Desbordamiento");
        } else {
            if (fin == cola.length - 1) {
                fin = 0;
            } else {
                ++fin;
            }
            cola[fin] = object;
            if (inicio == -1) {
                inicio = 0;
            }
        }
    }

    /**
     * Método para sacar elementos de la cola
     * @return
     */
    public T eliminar()
    {
        T dato = null;
        if (colaVacia()) {
            System.out.println("Subdesbordamiento");
        } else {
            dato = cola[inicio];
            if (inicio == fin) {
                inicio = -1;
                fin = -1;
            } else {
                if (inicio == cola.length -1) {
                    inicio = 0;
                } else {
                    ++inicio;
                }
            }
        }
        return dato;
    }

    /**
     * Método para regresar el siguiente elemento a ser sacado sin eliminarlo
     * @return
     */
    public T peek()
    {
        if (inicio == -1) {
            System.out.println("Subdesbordamiento");
            return null;
        }
        return cola[inicio];
    }

    /**
     * Método para mostrar los elementos actuales de la cola
     */
    public void mostrar()
    {
        if (inicio == -1) {
            System.out.println("Está vacia");
        } else {
            for (int i = inicio; i <= fin; ++i) {
                System.out.print(cola[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Método que regresa el tamaño actual de la cola
     * @return
     */
    public int tamanio()
    {
        if (colaVacia()) {
            return 0;
        }

        if (fin >= inicio) {
            return fin - inicio + 1;
        } else {
            return cola.length - inicio + fin + 1;
        }
    }
}
