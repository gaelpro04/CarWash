import java.util.Random;

//Clase que modela la generación de vehiculos con probabilidad especifica.
public class GeneracionVehiculos {

    //Atributos de la clase donde se harán los bancos de datos
    private Random rd;
    private String[] color;
    private String[] marca;
    private String[] tamanio;
    private String[] tipoServicio;

    /**
     * Constructor preterminado donde se inicializan y crean los bancos de datos y Random
     */
    public GeneracionVehiculos()
    {
        //Atributos con elementos arbitrarios asignados
        color = new String[]{"Azul", "Verde", "Rojo", "Amarillo", "Blanco", "Rosa", "Gris", "Naranja"};
        marca = new String[]{"Honda", "Toyota", "Sentra", "Chevrolet", "Kia", "Ford", "Hyundai", "Mitsubishi"};

        //Atributos con elementos especificados por la prática
        tamanio = new String[]{"Pequeño", "Mediano", "Grande"};
        tipoServicio = new String[]{"Aspirado", "Secado"};

        rd = new Random();
    }

    /**
     * Método que genera un vehiculo de acuerdo a ciertas probabilidades o aleatoriamiete
     * @return
     */
    public Vehiculo generarVehiculo()
    {
        //Se inicializan algunas variables donde se almacenará las características generadas
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

        //Por utlimo se regresa el vehiculo con las características generadas
        return new Vehiculo(tamanio, tipoServicio, preferencia, marca, color);
    }

}
