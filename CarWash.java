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
    private int aceleracion;
    private Vehiculo vehiculo;

    /**
     * Constructor preterminado
     */
    public CarWash()
    {
        //Se inicializan las colas de vehiculos, y los demás atributos
        acceso = new ColaVehiculo(10);
        lavado = new ColaVehiculo(3);
        aspirado = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            aspirado.add(new ColaVehiculo(4));
        }
        secado = new ColaVehiculo(5);
        registro = new Registro();

        //Horas será utilizado para asignarle un valor de acuerdo a la hora y minuto especifico por la iteración actual
        //de los ciclos de la iteración
        horas = new int[]{8,9,10,12,1,2,3,4,5,6,7,8};

        //Atributos para la generacion aleatoria de nuevos carros
        numeroGenerado = 0;
        contadorDeNumeroGenerado = 0;

        //Aceleración del sistema y el vehiculo que se esté generando se almacenará ahí
        aceleracion = 1000;
        vehiculo = null;
    }

    /**
     * Método para generar un vehiculo de acuerdo a probabilidades
     * @return
     */
    private Vehiculo generarVehiculo()
    {
        GeneracionVehiculos generador = new GeneracionVehiculos();
        return generador.generarVehiculo();
    }

    /**
     * Método que actualiza las colas del sistema de auto lavado(incersión y eliminación de cada cola)
     * @param hora
     * @param minuto
     * @param accesoBol
     */
    public void statusColas(int hora, int minuto, boolean accesoBol)
    {
        //Si accesoBol es false entonces se utilizará el método de acceso(esto se utilizará a la hora de simular en dado
        // caso que ya pasen de las 5:45 y ya no se necesite meter más carros al sistema de autolavado)
        if (accesoBol) {
            statusAcceso(hora, minuto);
        } else {
            vehiculo = null;
        }

        //Actualización de de las colas
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

        //Si contador de numero generado es igual a numero generado(quiere decir que ya se debe meter) y el contador
        //es igual a cero, quiere decir que es el primer vehiculo generado, por lo tanto se crea un nuevo vehiculo
        if (contadorDeNumeroGenerado == numeroGenerado && contadorDeNumeroGenerado == 0) {
            this.vehiculo = generarVehiculo();
        }

        //Si el contador es igual a numero generado quiere decir que ya se puede meter
        if (contadorDeNumeroGenerado == numeroGenerado) {

            //Se verifica si la accesa está vacia
            if (!acceso.lineaLlena()) {

                //Se checa si el vehiculo generado tiene preferencia
                if (this.vehiculo.isPreferencia()) {
                    ColaVehiculo colaTemp = new ColaVehiculo(10);

                    //Si lo tiene se necestia primero sacar todos los vehiculos de la cola acceso a una cola temporal,
                    //para poder meter ese vehiculo con preferencia
                    boolean insertado = false;
                    while (!acceso.lineaVacia()) {

                        //Si ya hay un vehiculo con preferencia entonces se mete antes de el.
                        if (acceso.peek().isPreferencia()) {
                            acceso.insertar(this.vehiculo);
                            insertado = true;
                            break;

                            //En tal caso que no, se sigue vaciando a la cola temporal
                        } else {
                            colaTemp.insertar(acceso.eliminar());
                        }
                    }

                    //Si es true insertado quiere decir que si hubo vehiculos preferentes después del nuevo vehiculo preferente
                    //por lo mismo, si es false quiere decir que no hubo vehiculos preferentes después, entonces este vehiculo
                    //nuevo se almacena ya al principio
                    if (!insertado) {
                        acceso.insertar(this.vehiculo);
                    }

                    //Ahora se procede a vaciar la cola temporal de los vehiculos no preferentes al acceso original
                    while (!colaTemp.lineaVacia()) {
                        acceso.insertar(colaTemp.eliminar());
                    }

                    //Si el vehiculo no es preferente, entonces simplemente se mete al final de la cola
                } else {
                    acceso.insertar(this.vehiculo);
                }

                //Por utlimo se genera un nuevo numero y el contador se reinicia, además se genera otro vehiculo pero se alamcenará
                //hasta que llegue al tiempo indicado y haya espacio en el acceso
                this.numeroGenerado = rd.nextInt(7) + 2;
                contadorDeNumeroGenerado = 0;
                this.vehiculo = generarVehiculo();

                //Si esta lleno se imprime no hay acceso
            } else {
                System.out.println("No hay acceso");
            }

            //Si el contador y el numero generado no coincide, se suma uno al contador y eventualmente llegará al número generado
        } else {
            //Si el número generado es cero(primera iteración) entonces se genera un vehiculo y se asigna un numerto generado
            if (numeroGenerado == 0) {
                this.numeroGenerado = rd.nextInt(7) + 2;
                this.vehiculo = generarVehiculo();
            }

            //Se incrementa el contador
            ++contadorDeNumeroGenerado;
        }
    }

    /**
     * Método para meter los vehiculos de acceso a lavado
     * @param hora
     * @param minuto
     */
    private void statusLavadoIncersion(int hora, int minuto)
    {
        //Si no está vacia se entra aquí
        if (!acceso.lineaVacia()) {

            //Se hace un peek del primer vehiculo para poder analizarlo y determinar si es ya está disponible para meterse
            //a lavado
            Vehiculo vehiculo = acceso.peek();
            boolean siHayEspacio = false;

            //Si es aspirado
            if (vehiculo.getTipoServicio().equals("Aspirado") && !lavado.lineaLlena()) {

                //Se verifica si hay espacio en alguna cola de las cuatro colas de aspirado para poder acceder al lavado
                for (ColaVehiculo aspiradoInd : aspirado) {
                    if (!aspiradoInd.lineaLlena()) {
                        siHayEspacio = true;
                        break;
                    }
                }

                //Si hay espacio en alguna cola de aspitado, entonces se saca de acceso y se mete a lavado
                if (siHayEspacio) {
                    vehiculo.setHoraLlegadaInt(minuto);
                    lavado.insertar(vehiculo);
                    acceso.eliminar();
                } else {
                    System.out.println("No hay espacio en el servicio solicitado aspirado");
                }

                //Si es secado
            } else if (vehiculo.getTipoServicio().equals("Secado") && !lavado.lineaLlena()) {

                //El mismo procedimiento que aspirado solamente aquí directamente se checa con el método de lineaLlena si esta
                //lleno para determinar si se puede meter a lavado
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

    /**
     * Método para inserción a la cola aspirado
     * @param hora
     * @param minuto
     */
    private void statusAspiradoIncersion(int hora, int minuto)
    {
        if (!lavado.lineaVacia()) {
            Vehiculo vehiculo = lavado.peek();
            int contadorMenosCarros = Integer.MAX_VALUE;
            int indiceMenosCarros = -1;

            //Si el vehiculo retornado de peek es igual aspirado y de acuerdo a la formula planeteada da 3 entonces está listo apra meterse
            if (vehiculo.getTipoServicio().equals("Aspirado") && ((minuto - vehiculo.getHoraLlegadaInt() + 60) % 60 == 3)) {

                //Se checa en que cola hay más espacio para meterlo ahí
                for (int i = 0; i < 4; ++i) {
                    ColaVehiculo cola = aspirado.get(i);

                    // Si la cola está vacía, asigna el vehículo directamente a esta cola
                    if (cola.lineaVacia()) {
                        indiceMenosCarros = i;
                        break;
                    }

                    //Comparacion
                    int contadorInterno = cola.lineaTamanio();
                    if (contadorInterno < contadorMenosCarros) {
                        contadorMenosCarros = contadorInterno;
                        indiceMenosCarros = i;
                    }
                }

                //Ya por ultimo se sacade lavado y se mete a aspirado, además el primer nuevo vehiculo de lavado su hora de
                //llegada cambia al minuto actual para que el tiempo que pase para ese vehiculo sean 3 minutos
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

    /**
     * Método para incersión en secado
     * @param hora
     * @param minuto
     */
    private void statusSecadoIncersion(int hora, int minuto)
    {
        if (!lavado.lineaVacia()) {
            Vehiculo vehiculo = lavado.peek();

            //Si el vehiculo si tipo de servicio es secado y la formula para determinar y si es el tiempo correcto para la deserción
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

    /**
     * Método para sacar un elemento ya pasado de tiempo de aspirado
     * @param hora
     * @param minuto
     */
    private void statusAspiradoEliminicacion(int hora, int minuto)
    {
        //Como son cuatro colas, se utiliza ciclos
        ColaVehiculo aspiradoInd;
        for (int i = 0; i < 4; ++i) {
            aspiradoInd = aspirado.get(i);

            if (!aspiradoInd.lineaVacia()) {
                Vehiculo vehiculo = aspiradoInd.peek();

                //Para que el tiempo pasado para su deserción sea correcto el primer elemento se cambia al minuto actual, esto se logra
                //ya que anteriormente se asigno menos uno a cada elemento para que luego se pueda identificar si ya siguen de salir
                if (vehiculo.getHoraLlegadaInt() == -1) {
                    vehiculo.setHoraLlegadaInt(minuto);
                }

                //Casos para determinar que tiempo se quedará
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

    /**
     * Método para sacar un elemento ya pasado de tiempo
     * @param hora
     * @param minuto
     */
    private void statusSecadoEliminacion(int hora, int minuto)
    {
        if (!secado.lineaVacia()) {
            Vehiculo vehiculo = secado.peek();

            //Se checa si se llego al tiempo especifico
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


    /**
     * Método paras regresar la hora como el formato convencional de hora
     * @param i
     * @param j
     * @return
     */
    public String generarHoraString(int i, int j)
    {
        return horas[i] + ":" + (j < 10 ? "0" + j : j) + (i >= 3 ? " pm" : " am");
    }

    /**
     * Método para tener un control de las clases en consola
     * @param minuto
     */
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

    /**
     * Método para imprimir la cantidad de elementos actuales que hay en las colas
     * @param minuto
     */
    public void imprimirColasCorto(int minuto)
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

    public int getAceleracion() {
        return aceleracion;
    }

    public void setAceleracion(int aceleracion) {
        this.aceleracion = aceleracion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
