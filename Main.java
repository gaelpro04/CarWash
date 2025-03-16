public class Main {
    public static void main(String[] args) {
        GeneracionVehiculos generacionVehiculos = new GeneracionVehiculos();
        Vehiculo vehiculo = generacionVehiculos.generarVehiculo();
        System.out.println(vehiculo);
    }
}
