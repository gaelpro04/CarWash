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
    private int contadorDeNumeroGenerado;

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
        horas = new int[]{8,9,10,12,1,2,3,4,5,6,7,8};
        numeroGenerado = 0;
        contadorDeNumeroGenerado = 0;
    }

    /**
     * Método que permite simular el car wash automatizado
     */
    public void iniciarSimulacion()
    {

        //Ciclos anidados y Threads que hacen posible la simulación.
        //Primer ciclo corresponde a horas(que en realidad son minutos en la simulacion)
        //Segundo ciclo corresponde a minutos(que en realdia son soegundos en la simulacion)
        //Luego el Thread de 1000 milisegundos que corresponde a un segundo.
        int max = 10;
        for (int i = 0; i < max; ++i) {
            System.out.println(generarHoraString(i,0));


            for (int j = 0; j < 60; ++j) {
                if (j != 0) {
                    System.out.println(generarHoraString(i,j));
                }

                try {
                    Thread.sleep(1000);

                    boolean lineaEstado = false;

                    for (ColaVehiculo aspiradoInd : aspirado) {

                        if (!aspiradoInd.lineaVacia()) {
                            lineaEstado = true;
                            break;
                        }
                    }
                    if (i == (max-1) && j == 59 && (!acceso.lineaVacia() || !lavado.lineaVacia() || !secado.lineaVacia() || lineaEstado)) {
                        statusColas(i,j,false);
                        ++max;
                    } else if ( i == 9 && j >= 44) {
                        statusColas(i,j,false);
                    } else if (i > 9) {
                        statusColas(i,j,false);
                    } else {
                        statusColas(i,j,true);
                    }
                    imprimirColasCorto(j);


                } catch (InterruptedException e) {
                    System.out.println("Error en hilo del segundo: " + (j+1));
                }
            }
        }
    }

    private Vehiculo generarVehiculo()
    {
        GeneracionVehiculos generador = new GeneracionVehiculos();
        return generador.generarVehiculo();
    }

    private void statusColas(int hora, int minuto, boolean accesoBol)
    {
        if (accesoBol) {
            statusAcceso(hora, minuto);
        }

        statusLavadoIncersion(hora, minuto);

        statusAspiradoIncersion(hora, minuto);

        statusSecadoIncersion(hora, minuto);

        statusAspiradoEliminicacion(hora,minuto);

        statusSecadoEliminacion(hora,minuto);

    }

    /**
     * Método para meter los nuevos vehiculos a la cola de espera
     * @param hora
     * @param minuto
     * @return
     */
    private void statusAcceso(int hora, int minuto)
    {
        Random rd = new Random();

        if (contadorDeNumeroGenerado == numeroGenerado) {
            if (!acceso.lineaLlena()) {
                Vehiculo vehiculo = generarVehiculo();

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

                this.numeroGenerado = rd.nextInt(4) + 2;
                contadorDeNumeroGenerado = 0;
            } else {
                System.out.println("No hay acceso");
            }
        } else {
            if (numeroGenerado == 0) {
                this.numeroGenerado = rd.nextInt(4) + 2;
            }
            ++contadorDeNumeroGenerado;
        }
    }

    private void statusLavadoIncersion(int hora, int minuto)
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
                    vehiculo.setHoraLlegadaInt(minuto);
                    lavado.insertar(vehiculo);
                    acceso.eliminar();
                } else {
                    System.out.println("No hay espacio en el servicio solicitado aspirado");
                }
            } else if (vehiculo.getTipoServicio().equals("Secado") && !lavado.lineaLlena()) {
                if (!secado.lineaLlena()) {
                    vehiculo.setHoraLlegadaInt(minuto);
                    lavado.insertar(vehiculo);
                    acceso.eliminar();
                } else {
                    System.out.println("No hay espacio en el servicio solicitado secado");
                }
            } else {
                System.out.println("Está llena el lavado");
            }
        }
    }

    private void statusAspiradoIncersion(int hora, int minuto)
    {
        if (!lavado.lineaVacia()) {
            Vehiculo vehiculo = lavado.peek();
            int contadorMenosCarros = Integer.MAX_VALUE;
            int indiceMenosCarros = -1;

            if (vehiculo.getTipoServicio().equals("Aspirado") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 3)) {

                for (int i = 0; i < 4; ++i) {
                    ColaVehiculo cola = aspirado.get(i);

                    // Si la cola está vacía, asigna el vehículo directamente a esta cola
                    if (cola.lineaVacia()) {
                        indiceMenosCarros = i;
                        break;
                    }

                    int contadorInterno = cola.lineaTamanio();
                    if (contadorInterno < contadorMenosCarros) {
                        contadorMenosCarros = contadorInterno;
                        indiceMenosCarros = i;
                    }
                }

                vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                System.out.println("Eliminado lavado de aspirado");
                aspirado.get(indiceMenosCarros).insertar(vehiculo);
                lavado.eliminar();
                aspirado.get(indiceMenosCarros).peek().setHoraLlegadaInt(-1);
                if (!lavado.lineaVacia()) {
                    lavado.peek().setHoraLlegadaInt(minuto);
                }
            }
        }

    }

    private void statusSecadoIncersion(int hora, int minuto)
    {
        if (!lavado.lineaVacia()) {
            Vehiculo vehiculo = lavado.peek();

            if (vehiculo.getTipoServicio().equals("Secado") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 3)) {

                vehiculo.setHoraLlegada(generarHoraString(hora,minuto));
                vehiculo.setHoraLlegadaInt(minuto);
                System.out.println("Eliminado lavado de secado");
                secado.insertar(vehiculo);
                lavado.eliminar();
                if (!lavado.lineaVacia()) {
                    lavado.peek().setHoraLlegadaInt(minuto);
                }
            }


        }
    }

    private void statusAspiradoEliminicacion(int hora, int minuto)
    {
        ColaVehiculo aspiradoInd;
        for (int i = 0; i < 4; ++i) {
            aspiradoInd = aspirado.get(i);

            if (!aspiradoInd.lineaVacia()) {
                Vehiculo vehiculo = aspiradoInd.peek();

                if (vehiculo.getHoraLlegadaInt() == -1) {
                    vehiculo.setHoraLlegadaInt(minuto);
                }

                if (vehiculo.getTamanio().equals("Pequeño") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 5)) {

                    vehiculo.setHoraSalidaInt(minuto);
                    vehiculo.setHoraSalida(generarHoraString(hora, minuto));
                    aspiradoInd.eliminar();
                    registro.guardarVehiculo(vehiculo);

                } else if (vehiculo.getTamanio().equals("Mediano") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 7)) {

                    vehiculo.setHoraSalidaInt(minuto);
                    vehiculo.setHoraSalida(generarHoraString(hora, minuto));
                    aspiradoInd.eliminar();
                    registro.guardarVehiculo(vehiculo);

                } else if (vehiculo.getTamanio().equals("Grande") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 10)) {

                    vehiculo.setHoraSalidaInt(minuto);
                    vehiculo.setHoraSalida(generarHoraString(hora, minuto));
                    aspiradoInd.eliminar();
                    registro.guardarVehiculo(vehiculo);
                }

            }
        }
    }

    private void statusSecadoEliminacion(int hora, int minuto)
    {
        if (!secado.lineaVacia()) {
            Vehiculo vehiculo = secado.peek();

            if ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 3) {
                vehiculo.setHoraSalidaInt(minuto);
                vehiculo.setHoraSalida(generarHoraString(hora, minuto));
                secado.eliminar();
                if (!secado.lineaVacia()) {
                    System.out.println("Minuto cambiado a: " + minuto);
                    secado.peek().setHoraLlegadaInt(minuto);
                }
                registro.guardarVehiculo(vehiculo);
            }
        }
    }


    private String generarHoraString(int i, int j)
    {
        return horas[i] + ":" + (j < 10 ? "0" + j : j) + (i >= 12 ? " pm" : " am");
    }

    private void imprimirEstadoColas(int minuto)
    {
        System.out.println("=================ACCESO======================");
        System.out.println("Cola de acceso cantidad: " + acceso.lineaTamanio());
        acceso.mostrarCola();
        System.out.println("=================LAVADO=======================");
        System.out.println("Cola de lavado cantidad: " + lavado.lineaTamanio() + " Tiempo: " + (lavado.lineaVacia() ? "vacio" : lavado.peek().getHoraLlegadaInt()) + " Minuto: " + minuto);
        lavado.mostrarCola();
        System.out.println("=================ASPIRADO=====================");
        for (ColaVehiculo aspiradoInd : aspirado) {
            System.out.println("Cola aspirado cantidad: " + aspiradoInd.lineaTamanio() + " Tiempo: " + (aspiradoInd.lineaVacia() ? "vacio" : aspiradoInd.peek().getHoraLlegadaInt()) + " Minuto: " + minuto + " Tamanio: " + (aspiradoInd.lineaVacia() ? "vacio" : aspiradoInd.peek().getTamanio()));
        }
        for (ColaVehiculo aspi : aspirado) {
            aspi.mostrarCola();
            System.out.println("================================================");
        }
        System.out.println("=======================SECADO=======================");
        System.out.println("Cola de secado cantidad: " + secado.lineaTamanio() + " Tiempo: " + (secado.lineaVacia() ? "vacio" : secado.peek().getHoraLlegadaInt()) + " Minuto: " + minuto);
        secado.mostrarCola();

        System.out.println();
        System.out.println();
    }

    private void imprimirColasCorto(int minuto)
    {
        System.out.println("Cola de acceso cantidad: " + acceso.lineaTamanio());
        System.out.println("Cola de lavado cantidad: " + lavado.lineaTamanio() + " Tiempo: " + (lavado.lineaVacia() ? "vacio" : lavado.peek().getHoraLlegadaInt()) + " Minuto: " + minuto);
        for (ColaVehiculo aspiradoInd : aspirado) {
            System.out.println("Cola aspirado cantidad: " + aspiradoInd.lineaTamanio() + " Tiempo: " + (aspiradoInd.lineaVacia() ? "vacio" : aspiradoInd.peek().getHoraLlegadaInt()) + " Minuto: " + minuto + " Tamanio: " + (aspiradoInd.lineaVacia() ? "vacio" : aspiradoInd.peek().getTamanio()));
        }
        System.out.println("Cola de secado cantidad: " + secado.lineaTamanio() + " Tiempo: " + (secado.lineaVacia() ? "vacio" : secado.peek().getHoraLlegadaInt()) + " Minuto: " + minuto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GETTERS Y SETTERS DE LA CLASE


    public ColaVehiculo getAcceso() {
        return acceso;
    }

    public void setAcceso(ColaVehiculo acceso) {
        this.acceso = acceso;
    }

    public ColaVehiculo getLavado() {
        return lavado;
    }

    public void setLavado(ColaVehiculo lavado) {
        this.lavado = lavado;
    }

    public ArrayList<ColaVehiculo> getAspirado() {
        return aspirado;
    }

    public void setAspirado(ArrayList<ColaVehiculo> aspirado) {
        this.aspirado = aspirado;
    }

    public ColaVehiculo getSecado() {
        return secado;
    }

    public void setSecado(ColaVehiculo secado) {
        this.secado = secado;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public int[] getHoras() {
        return horas;
    }

    public void setHoras(int[] horas) {
        this.horas = horas;
    }

    public int getNumeroGenerado() {
        return numeroGenerado;
    }

    public void setNumeroGenerado(int numeroGenerado) {
        this.numeroGenerado = numeroGenerado;
    }

    public int getContadorDeNumeroGenerado() {
        return contadorDeNumeroGenerado;
    }

    public void setContadorDeNumeroGenerado(int contadorDeNumeroGenerado) {
        this.contadorDeNumeroGenerado = contadorDeNumeroGenerado;
    }


}
