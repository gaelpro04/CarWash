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
    private int numeroGenerado;

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
        numeroGenerado = 0;
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
            System.out.println(generarHoraString(i,0));


            //Dos variables, una que genera los minutos para el próximo carro a generar
            numeroGenerado = rd.nextInt(7) + 2;
            int contadorDeNumeroGenerado = 0;
            for (int j = 0; j < 60; ++j) {
                System.out.println(generarHoraString(i,j));

                try {
                    Thread.sleep(1000);

                    contadorDeNumeroGenerado = statusColas(i,j, contadorDeNumeroGenerado);



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

    public int statusColas(int hora, int minuto, int contadorNumeroGenerado)
    {
        int contadorTiempo = statusAcceso(hora, minuto, contadorNumeroGenerado);

        statusLavadoIncersion(hora, minuto);
        statusLavadoEliminacion(hora, minuto);

        statusAspiradoIncersion(hora, minuto);
        statusAspiradoEliminacion(hora, minuto);

        statusSecadoIncersion(hora, minuto);
        statusSecadoEliminacion(hora, minuto);

        return contadorTiempo;
    }

    /**
     * Método para meter los nuevos vehiculos a la cola de espera
     * @param hora
     * @param minuto
     * @param contadorNumeroGenerado
     * @return
     */
    public int statusAcceso(int hora, int minuto, int contadorNumeroGenerado)
    {
        Random rd = new Random();

        if (contadorNumeroGenerado == numeroGenerado) {
            if (!acceso.lineaLlena()) {
                Vehiculo vehiculo = generarVehiculo();
                vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegadaInt(minuto);

                if (vehiculo.isPreferencia()) {
                    ColaVehiculo colaTemp = new ColaVehiculo(10);

                    while (!acceso.lineaVacia()) {
                        colaTemp.insertar(acceso.eliminar());
                    }
                    acceso.insertar(vehiculo);
                    while (!colaTemp.lineaVacia()) {
                        acceso.insertar(colaTemp.eliminar());
                    }
                } else {
                    acceso.insertar(vehiculo);
                }

                System.out.println(vehiculo);
                this.numeroGenerado = rd.nextInt(7) + 2;
                contadorNumeroGenerado = 0;
            } else {
                System.out.println("No hay acceso");
            }
        } else {
            ++contadorNumeroGenerado;
        }

        return contadorNumeroGenerado;
    }

    public void statusLavadoIncersion(int hora, int minuto)
    {
        if (!acceso.lineaVacia()) {
            Vehiculo vehiculo = acceso.peek();
            boolean siHayEspacio = false;

            if (vehiculo.getTipoServicio().equals("Aspirado")) {

                //Meter contadores de cantidad de espacios disponibles para poder meter leugo al de menor cantidad de
                //autos metidos
                for (ColaVehiculo aspirado : aspirado) {
                    if (!aspirado.lineaLlena()) {
                        siHayEspacio = true;
                        break;
                    }
                }

                if (siHayEspacio) {
                    vehiculo.setHoraSalidaInt(minuto);
                    vehiculo.setHoraSalida(generarHoraString(hora,minuto));
                    vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                    vehiculo.setHoraLlegadaInt(minuto);
                    acceso.eliminar();
                    lavado.insertar(vehiculo);
                } else {
                    System.out.println("No hay espacio en el servicio solicitado");
                }
            } else {
                if (!secado.lineaLlena()) {

                } else {

                }
            }
        }
    }

    public void statusLavadoEliminacion(int hora, int minuto)
    {

    }

    public void statusAspiradoIncersion(int hora, int minuto)
    {

    }

    public void statusAspiradoEliminacion(int hora, int minuto)
    {

    }

    public void statusSecadoIncersion(int hora, int minuto)
    {

    }

    public void statusSecadoEliminacion(int hora, int minuto)
    {

    }

    public boolean calcularHoraSalida(String tipoServicio, Vehiculo vehiculo)
    {
        return false;
    }

    private String generarHoraString(int i, int j)
    {
        return horas[i] + ":" + ((j+1) < 10 ? "0" + (j+1) : (j+1)) + (i >= 12 ? " pm" : " am");
    }
}
