public class ColaVehiculo {

    Cola<Vehiculo> colaVehiculo;

    public ColaVehiculo()
    {
        colaVehiculo = new Cola<>(10);
    }

    public ColaVehiculo(int MAX)
    {
        colaVehiculo = new Cola<>(MAX);
    }

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
}
