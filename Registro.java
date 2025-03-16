public class Registro {

    private ColaVehiculo vehiculosRegistrados;

    public Registro()
    {
        vehiculosRegistrados = new ColaVehiculo();
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
