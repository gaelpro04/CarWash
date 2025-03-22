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
}
