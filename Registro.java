public class Registro {

    private ColaVehiculo vehiculosRegistrados;

    public Registro()
    {
        vehiculosRegistrados = new ColaVehiculo(100000000);
    }

    public void guardarVehiculo(Vehiculo vehiculo)
    {
        vehiculosRegistrados.insertar(vehiculo);
    }

    public Vehiculo eliminarVehiculo()
    {
        return vehiculosRegistrados.eliminar();
    }

    public int lineaTamanio()
    {
        return vehiculosRegistrados.lineaTamanio();
    }

    public boolean lineaVacia()
    {
        return vehiculosRegistrados.lineaVacia();
    }

    public boolean lineaLlena()
    {
        return vehiculosRegistrados.lineaLlena();
    }
}
