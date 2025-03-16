import java.util.Random;

public class GeneracionVehiculos {

    private Random rd;
    private String[] color;
    private String[] marca;
    private String[] tamanio;
    private String[] tipoServicio;
    private boolean[] preferencia;

    public GeneracionVehiculos()
    {
        color = new String[]{"Azul", "Verde", "Rojo", "Amarillo", "Blanco", "Negro", "Gris", "Naranja"};
        marca = new String[]{"Honda", "Toyota", "Sentra", "Chevrolet", "Kia", "Ford", "Hyundai", "Mitsubishi"};
        tamanio = new String[]{"Pequeño", "Mediano", "Grande"};
        tipoServicio = new String[]{"Aspirado", "Secado"};
        preferencia = new boolean[]{true,false};
        rd = new Random();
    }

    public Vehiculo generarVehiculo()
    {
        String color;
        String marca;
        String tamanio;
        String tipoServicio;
        boolean preferencia;

        //Generación random del color
        color = this.color[rd.nextInt(8)];

        //Generación random de la marca
        marca = this.marca[rd.nextInt(8)];


        //Generación de probabilidad para tamaño del vehículo
        double probabilidadGenerada = rd.nextDouble();
        if (probabilidadGenerada < 0.5) {
            tamanio = this.tamanio[0];
        } else if (probabilidadGenerada  < 0.85) {
            tamanio = this.tamanio[1];
        } else {
            tamanio = this.tamanio[2];
        }

        //Generación de probabilidad para tipo de servicio
        probabilidadGenerada = rd.nextDouble();
        if (probabilidadGenerada < 0.8) {
            tipoServicio = this.tipoServicio[0];
        } else {
            tipoServicio = this.tipoServicio[1];
        }

        //Generación de preferencia
        probabilidadGenerada = rd.nextInt();
        if (probabilidadGenerada < 0.7) {
            preferencia = false;
        } else {
            preferencia = true;
        }
        return new Vehiculo(tamanio, tipoServicio, preferencia, marca, color);
    }

}
