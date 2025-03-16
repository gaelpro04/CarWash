import java.util.ArrayList;
import java.util.Random;

//Clase que simula el car wash lógico
public class CarWash {

    //Atributos esenciales, donde simulan las colas de carros de sevicios respectivos, historial y generación de vehiculos
    private ColaVehiculo acceso;
    private ColaVehiculo lavado;
    private ArrayList<ColaVehiculo> aspirado;
    private ColaVehiculo secado;
    private Registro registro;
    private int[] horas;

    /**
     * Constructor preterminado
     */
    public CarWash()
    {
        acceso = new ColaVehiculo(10);
        lavado = new ColaVehiculo(3);
        aspirado = new ArrayList<>(4);
        secado = new ColaVehiculo(5);
        registro = new Registro();
        horas = new int[]{8,9,10,12,1,2,3,4,5,6};
    }

    /**
     * Método que permite simular el car wash automatizado
     */
    public void iniciarSimulacion()
    {
        Random rd = new Random();

        //Ciclos anidados y Threads que hacen posible la simulación.
        //Primer ciclo corresponde a horas(que en realidad son minutos en la simulacion)
        //Segundo ciclo corresponde a minutos(que en realdia son soegundos en la simulacion)
        //Luego el Thread de 1000 milisegundos que corresponde a un segundo.
        for (int i = 0; i < 10; ++i) {
            if (i >= 3) {
                System.out.println("Hora: " + horas[i] + "pm");
            } else {
                System.out.println("Hora: " + horas[i] + "am");
            }


            //Dos variables, una que genera los minutos para el próximo carro a generar
            int numeroGenerado = rd.nextInt(7) + 2;
            int contadorDeNumeroGenerado = 0;
            for (int j = 0; j < 60; ++j) {
                System.out.println("Tiempo: " + horas[i] + ":" + ((j+1) < 10 ? "0" + (j+1) : (j+1)));

                try {
                    Thread.sleep(1000);

                    if (!acceso.lineaLlena()) {
                        if (contadorDeNumeroGenerado == numeroGenerado) {
                            Vehiculo vehiculo = generarVehiculo();
                            vehiculo.setHoraLlegada(horas[i] + ":" + ((j+1) < 10 ? "0" + (j+1) : (j+1)));
                            System.out.println(vehiculo);
                            acceso.insertar(vehiculo);
                            contadorDeNumeroGenerado = 0;
                            numeroGenerado = rd.nextInt(7) + 2;
                        }
                        ++contadorDeNumeroGenerado;
                    } else {
                        System.out.println("Esta lleno el acceso");
                    }


                } catch (InterruptedException e) {
                    System.out.println("Error en hilo del segundo: " + (j+1));
                }
            }
        }




    }

    public Vehiculo generarVehiculo()
    {
        GeneracionVehiculos generador = new GeneracionVehiculos();
        return generador.generarVehiculo();
    }
}
