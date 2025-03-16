public class Cola<T> {

    private T[] cola;
    private int inicio, fin;

    public Cola()
    {
        cola = (T[]) new Object[1000];
        inicio = -1;
        fin = -1;
    }

    public Cola(int MAX)
    {
        cola = (T[]) new Object[MAX];
        inicio = -1;
        fin = -1;
    }

    public boolean colaLena()
    {
        return (fin + 1) % cola.length == inicio;
    }

    public boolean colaVacia()
    {
        return inicio == -1;
    }

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

    public T peek()
    {
        if (inicio == -1) {
            System.out.println("Subdesbordamiento");
            return null;
        }
        return cola[inicio];
    }
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
}
