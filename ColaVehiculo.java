//Clase que modela la cola de vehiculos(Importante para la clase CarWash)
public class ColaVehiculo {

    //Atributo donde se almacenarán los vehiculos
    private Cola<Vehiculo> colaVehiculo;

    /**
     * Constructor preterminado
     */
    public ColaVehiculo()
    {
        colaVehiculo = new Cola<>(10);
    }

    /**
     * Constructor con parametro de la cantidad máxima de elementos de la cola de vehiculos
     * @param MAX
     */
    public ColaVehiculo(int MAX)
    {
        colaVehiculo = new Cola<>(MAX);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Métodos de la clase Cola pero sobreescritos para esta clase

    public void insertar(Vehiculo vehiculo)
    {
        colaVehiculo.insertar(vehiculo);
    }

    public Vehiculo eliminar()
    {
        return colaVehiculo.eliminar();
    }

    public Vehiculo peek()
    {
        return colaVehiculo.peek();
    }

    public boolean lineaLlena()
    {
        return colaVehiculo.colaLena();
    }

    public boolean lineaVacia()
    {
        return colaVehiculo.colaVacia();
    }

    public void mostrarCola()
    {
        colaVehiculo.mostrar();
    }

    public int lineaTamanio()
    {
        return colaVehiculo.tamanio();
    }
}
