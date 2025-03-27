//Clase que simula el almacenamiento de registros de vehiculos
public class Registro {

    //Unico atributo
    private ColaVehiculo vehiculosRegistrados;

    /**
     * Constructor preterminado donde se asigna esa cantidad para que no haya desbordamiento inesperado
     */
    public Registro()
    {
        vehiculosRegistrados = new ColaVehiculo(100000000);
    }

    /**
     * Método para meter vehiculos al registro
     * @param vehiculo
     */
    public void guardarVehiculo(Vehiculo vehiculo)
    {
        vehiculosRegistrados.insertar(vehiculo);
    }

    /**
     * Método para eliminar vehiculos del registro
     * @return
     */
    public Vehiculo eliminarVehiculo()
    {
        return vehiculosRegistrados.eliminar();
    }

    /**
     * Método para saber el tamaño actual del registro
     * @return
     */
    public int lineaTamanio()
    {
        return vehiculosRegistrados.lineaTamanio();
    }

    /**
     * Método para saber si el registro está vacio
     * @return
     */
    public boolean lineaVacia()
    {
        return vehiculosRegistrados.lineaVacia();
    }

    /**
     * Método para saber si el registro está lleno
     * @return
     */
    public boolean lineaLlena()
    {
        return vehiculosRegistrados.lineaLlena();
    }
}
