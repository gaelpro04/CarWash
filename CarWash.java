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
        for (int i = 0; i < 4; ++i) {
            aspirado.add(new ColaVehiculo(4));
        }
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
        System.out.println("Cola de acceso llena: " + acceso.lineaLlena());
        System.out.println("Cola de lavado llena: " + lavado.lineaLlena());
        System.out.println("Cola de secado llena: " + secado.lineaLlena());

        for (ColaVehiculo aspiradoInd : aspirado) {
            System.out.println("Cola aspirado llena: " + aspiradoInd.lineaLlena());
        }


        int contadorTiempo = statusAcceso(hora, minuto, contadorNumeroGenerado);

        statusLavadoIncersion(hora, minuto);

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
                System.out.println("Se metió un vehiculo a acceso");
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

            if (vehiculo.getTipoServicio().equals("Aspirado") && !lavado.lineaLlena()) {

                //Meter contadores de cantidad de espacios disponibles para poder meter leugo al de menor cantidad de
                //autos metidos
                for (ColaVehiculo aspiradoInd : aspirado) {
                    if (!aspiradoInd.lineaLlena()) {
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
                    System.out.println("Se metió un vehiculo a lavado por aspirado");
                } else {
                    System.out.println("No hay espacio en el servicio solicitado aspirado");
                }
            } else {
                if (!secado.lineaLlena()) {
                    vehiculo.setHoraSalidaInt(minuto);
                    vehiculo.setHoraSalida(generarHoraString(hora,minuto));
                    vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                    vehiculo.setHoraLlegadaInt(minuto);
                    acceso.eliminar();
                    lavado.insertar(vehiculo);
                    System.out.println("Se metió un vehiculoa a lavado por secadora");
                } else {
                    System.out.println("No hay espacio en el servicio solicitado secado");
                }
            }
        }
    }

    public void statusAspiradoIncersion(int hora, int minuto)
    {
        if (!lavado.lineaVacia()) {
            Vehiculo vehiculo = lavado.peek();
            int contadorMenosCarros = 4;
            int indiceMenosCarros = 0;
            boolean siHayEspacio = false;

            if (vehiculo.getTipoServicio().equals("Aspirado") && (minuto - vehiculo.getHoraLlegadaInt() == 3)) {

                //Ciclo para saber que cola tiene menos elementos
                for (int i = 0; i < 4; ++i) {
                    ColaVehiculo aspiradoInd = new ColaVehiculo(4);
                    int contadorInterno = 0;

                    for (int j = 0; j < 4; ++j) {
                        if (!aspirado.get(i).lineaVacia()) {
                            aspiradoInd.insertar(aspirado.get(i).eliminar());
                            ++contadorInterno;
                        } else {
                            break;
                        }
                    }
                    for (int j = 0; j < 4; ++j) {
                        if (!aspiradoInd.lineaVacia()) {
                            aspirado.get(i).insertar(aspiradoInd.eliminar());
                        } else {
                            break;
                        }
                    }
                    if (contadorMenosCarros > contadorInterno) {
                        contadorMenosCarros = contadorInterno;
                        indiceMenosCarros = i;
                    }
                }

                vehiculo.setHoraSalidaInt(minuto);
                vehiculo.setHoraSalida(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegadaInt(minuto);
                lavado.eliminar();
                System.out.println("Se metió un vehiculo a aspirado");
                aspirado.get(indiceMenosCarros).insertar(vehiculo);


            } else if (vehiculo.getTipoServicio().equals("Secado") && vehiculo.getHoraLlegadaInt() - minuto == 3) {

                vehiculo.setHoraSalidaInt(minuto);
                vehiculo.setHoraSalida(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegadaInt(minuto);
                lavado.eliminar();
                System.out.println("Se metió un vehiculo a secado");
                secado.insertar(vehiculo);
            }
        }

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
