import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Clase que modela lo gráfico de la clase Car Wash
public class CarWashGUI {

    //Atributos del frame
    private JFrame frame;
    private JPanel panelIzquierdo, panelCentral, panelDerecho;
    private JPanel panelAcceso, panelEsperaAcceso, panelLavado, panelAspiradoSecado, panelControl;
    private JPanel panelIzquierdoEAST;
    private JPanel panelIzquierdoWEST;
    private JLabel labelTiempo, labelTituloAcceso, labelTituloLavado, labelTituloServicio;
    private JLabel vehiculoActual;
    private JButton botonAceleracion, botonTerminar;
    private ArrayList<JLabel> lavado, acceso, secado;
    private ArrayList<ArrayList<JLabel>> aspirado;
    private Color backGroundBASE;

    //Atributos lógicos
    private CarWash carWash;
    private boolean terminado;

    /**
     * Constructor donde configura la interfaz y sus componentes
     */
    public CarWashGUI() {

        carWash = new CarWash();

        frame = new JFrame("Car Wash");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        terminado = false;

        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        backGroundBASE = panelCentral.getBackground();

        panelAcceso = new JPanel(new GridBagLayout());
        panelLavado = new JPanel(new GridBagLayout());
        panelAspiradoSecado = new JPanel(new GridBagLayout());
        panelEsperaAcceso = new JPanel(new GridBagLayout());

        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelControl.setOpaque(true);
        panelControl.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        //Espaciado entre elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,5,10,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Botón acelerar
        botonAceleracion = new JButton("Acelerar");
        botonAceleracion.addActionListener(_ -> botonAceleracion());
        botonAceleracion.setFocusPainted(false);
        botonAceleracion.setPreferredSize(new Dimension(90,20));
        botonAceleracion.setOpaque(true);
        botonAceleracion.setBackground(new Color(236,234,227));

        //Botón terminar
        botonTerminar = new JButton("Terminar");
        botonTerminar.addActionListener(_ -> botonTerminar());
        botonTerminar.setFocusPainted(false);
        botonTerminar.setPreferredSize(new Dimension(90,20));
        botonTerminar.setOpaque(true);
        botonTerminar.setBackground(new Color(236,234,227));

        //Hora
        labelTiempo = new JLabel("8:00", SwingConstants.CENTER);
        labelTiempo.setOpaque(true);
        labelTiempo.setPreferredSize(new Dimension(70,20));
        labelTiempo.setBackground(Color.WHITE);

        acceso = new ArrayList<>(10);
        lavado = new ArrayList<>(3);
        secado = new ArrayList<>(5);
        aspirado = new ArrayList<>(4);

        for (int i = 0; i < 4; ++i) {
            aspirado.add(new ArrayList<>(4));
        }

        //Ciclos donde se configura cada label para luego poder meter la información de los vehiculos ahí
        //Ciclo para acceso
        for (int i = 0; i < 10; ++i) {
            JLabel labelVehiculo = new JLabel(String.valueOf(i),  SwingConstants.CENTER);
            labelVehiculo.setOpaque(true);
            labelVehiculo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            labelVehiculo.setBackground(Color.WHITE);
            labelVehiculo.setPreferredSize(new Dimension(60,60));
            acceso.add(labelVehiculo);
        }

        //Ciclo para lavado
        for (int i = 0;i < 3; ++i) {
            JLabel labelVehiculo = new JLabel(String.valueOf(i),  SwingConstants.CENTER);
            labelVehiculo.setOpaque(true);
            labelVehiculo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            labelVehiculo.setBackground(Color.WHITE);
            labelVehiculo.setPreferredSize(new Dimension(60,60));
            lavado.add(labelVehiculo);
        }

        //Ciclo para secado
        for (int i = 0;i < 5; ++i) {
            JLabel labelVehiculo = new JLabel(String.valueOf(i),  SwingConstants.CENTER);
            labelVehiculo.setOpaque(true);
            labelVehiculo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            labelVehiculo.setBackground(Color.WHITE);
            labelVehiculo.setPreferredSize(new Dimension(60,60));
            secado.add(labelVehiculo);
        }

        //Ciclos para aspirado
        for (int i = 0; i < 4; ++i) {
            ArrayList<JLabel> aspiradoInd = aspirado.get(i);

            for (int j = 0; j < 4; ++j) {
                JLabel labelVehiculo = new JLabel(String.valueOf(j), SwingConstants.CENTER);
                labelVehiculo.setOpaque(true);
                labelVehiculo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                labelVehiculo.setBackground(Color.WHITE);
                labelVehiculo.setPreferredSize(new Dimension(60,60));
                aspiradoInd.add(labelVehiculo);
            }
        }




        //Por cada Jlabel, se mete al panel especifico con su espaciado entre componentes
        for (int i = 0;i < acceso.size(); ++i) {
            panelAcceso.add(acceso.get(i), gbc);
            ++gbc.gridx;
        }

        gbc.gridx = 0;
        for (int i = 0; i < lavado.size(); ++i) {
            panelLavado.add(lavado.get(i), gbc);
            gbc.gridx++;
        }

        gbc.gridx = 0;
        for (int i = 0; i < secado.size(); ++i) {
            panelAspiradoSecado.add(secado.get(i), gbc);
            gbc.gridx++;
        }

        gbc.gridx = 0;
        for (int i = 0; i < aspirado.size(); ++i) {
            ArrayList<JLabel> aspiradoInd = aspirado.get(i);
            gbc.gridy++;
            for (int j = 0; j < aspiradoInd.size(); ++j) {
                panelAspiradoSecado.add(aspiradoInd.get(j), gbc);
                gbc.gridx++;
            }
            gbc.gridx = 0;
        }

        //Para le vehiculo próximoa a meter
        gbc.gridx = 0;
        vehiculoActual = new JLabel("0", SwingConstants.CENTER);
        vehiculoActual.setOpaque(true);
        vehiculoActual.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        vehiculoActual.setBackground(Color.WHITE);
        vehiculoActual.setPreferredSize(new Dimension(60,60));

        //Area de paneles donde y titulos de cada uno, se meten los componentes elaborados en esta sección
        panelEsperaAcceso.add(vehiculoActual, gbc);

        panelControl.add(labelTiempo);
        panelControl.add(botonAceleracion);
        panelControl.add(botonTerminar);

        panelIzquierdoEAST = new JPanel(new BorderLayout());
        panelIzquierdoWEST = new JPanel(new BorderLayout());
        panelIzquierdoWEST.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelIzquierdoEAST.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        labelTituloAcceso = new JLabel("Cola de espera", SwingConstants.CENTER);
        labelTituloAcceso.setFont(new Font("Arial", Font.BOLD, 20));
        panelIzquierdoEAST.add(labelTituloAcceso, BorderLayout.NORTH);
        panelIzquierdoEAST.add(panelAcceso, BorderLayout.CENTER);

        JLabel labelTituloEspera = new JLabel("En espera", SwingConstants.CENTER);
        labelTituloEspera.setFont(new Font("Arial", Font.BOLD, 20));
        panelIzquierdoWEST.add(labelTituloEspera, BorderLayout.NORTH);
        panelIzquierdoWEST.add(panelEsperaAcceso, BorderLayout.CENTER);

        panelIzquierdo.add(panelIzquierdoEAST, BorderLayout.EAST);
        panelIzquierdo.add(panelIzquierdoWEST, BorderLayout.WEST);

        labelTituloLavado = new JLabel("Lavado", SwingConstants.CENTER);
        labelTituloLavado.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(labelTituloLavado, BorderLayout.NORTH);
        panelCentral.add(panelLavado, BorderLayout.CENTER);

        labelTituloServicio = new JLabel("Secado y aspirado", SwingConstants.CENTER);
        labelTituloServicio.setFont(new Font("Arial", Font.BOLD, 20));
        panelDerecho.add(labelTituloServicio, BorderLayout.NORTH);
        panelDerecho.add(panelAspiradoSecado, BorderLayout.CENTER);

        //Se meten todos los paneles al frame y se da la configuración final del panel
        frame.add(panelControl, BorderLayout.NORTH);
        frame.add(panelIzquierdo, BorderLayout.WEST);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelDerecho, BorderLayout.EAST);
        frame.setSize(1350,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }


    /**
     * Método que permite simular el car wash automatizado
     */
    public void iniciarSimulacion()
    {

        //Ciclos anidados y Threads que hacen posible la simulación.
        //Primer ciclo corresponde a horas(que en realidad son minutos en la simulacion)
        //Segundo ciclo corresponde a minutos(que en realidad son segundos en la simulacion)
        //Luego el Thread de 1000 milisegundos que corresponde a un segundo.
        int max = 10;
        for (int i = 0; i < max; ++i) {
            labelTiempo.setText(carWash.generarHoraString(i,0));

            for (int j = 0; j < 60; ++j) {
                if (j != 0) {
                    labelTiempo.setText(carWash.generarHoraString(i,j));
                }

                try {
                    if (!terminado) {
                        Thread.sleep(carWash.getAceleracion());
                    }


                    boolean lineaEstado = false;

                    for (ColaVehiculo aspiradoInd : carWash.getAspirado()) {

                        if (!aspiradoInd.lineaVacia()) {
                            lineaEstado = true;
                            break;
                        }
                    }

                    //Se verifican casos para saber si ya se acabará la simulación
                    if (i >= 9 && (carWash.getAcceso().lineaVacia() && carWash.getLavado().lineaVacia() && carWash.getSecado().lineaVacia() && !lineaEstado)) {
                        break;
                    } else if (i == (max-1) && j == 59 && (!carWash.getAcceso().lineaVacia() || !carWash.getLavado().lineaVacia() || !carWash.getSecado().lineaVacia() || lineaEstado)) {
                        carWash.statusColas(i,j,false);
                        ++max;
                    } else if ( i == 8 && j >= 44) {
                        carWash.statusColas(i,j,false);
                    } else if (i > 8) {
                        carWash.statusColas(i,j,false);
                    } else {
                        carWash.statusColas(i,j,true);
                    }
                    carWash.imprimirColasCorto(j);
                    actualizaColasGraficas();


                } catch (InterruptedException e) {
                    System.out.println("Error en hilo del segundo: " + (j+1));
                }
            }

            //Se verifica con este ciclo si todas las colas aspirados están vacias
            boolean lineaEstado = false;
            for (ColaVehiculo aspiradoInd : carWash.getAspirado()) {

                if (!aspiradoInd.lineaVacia()) {
                    lineaEstado = true;
                    break;
                }
            }
            if (i >= 9 && (carWash.getAcceso().lineaVacia() && carWash.getLavado().lineaVacia() && carWash.getSecado().lineaVacia() && !lineaEstado)) {
                break;
            }
        }
        //Una vez ya terminados los ciclos anidados(es decir la simulación) se procede a mostrar el registro de los carros
        registroGrafico();

    }

    /**
     * Método que permite cambiar la aceleración de la simulación
     */
    private void botonAceleracion()
    {
        if (carWash.getAceleracion() == 100) {
            carWash.setAceleracion(1000);
        } else {
            carWash.setAceleracion(carWash.getAceleracion() - 100);
        }
        System.out.println(carWash.getAceleracion());
    }

    /**
     * Método para terminar la simulación y pasar a lo final
     */
    private void botonTerminar()
    {
       terminado = true;
    }

    /**
     * Método encargado de actualizar las colas en lo gráfico para que se sincronicen bien
     */
    private void actualizaColasGraficas()
    {
        //Se obtiene las colas de la clase lógica del carWash
        ColaVehiculo acceso = carWash.getAcceso();
        ColaVehiculo lavado = carWash.getLavado();
        ColaVehiculo secado = carWash.getSecado();
        ArrayList<ColaVehiculo> aspirado = carWash.getAspirado();

        //Colas temporales donde se podrán mover los elementos y mostrarlos en los JLabels
        ColaVehiculo accesoTemp = new ColaVehiculo(10);
        ColaVehiculo lavadoTemp = new ColaVehiculo(3);
        ColaVehiculo secadoTemp = new ColaVehiculo(5);
        ArrayList<ColaVehiculo> aspiradoTemp = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            aspiradoTemp.add(new ColaVehiculo(4));
        }

        //Ciclo para determinar a que JLabel se le metará información del vehiculo existente
        for (int i = this.acceso.size()-1; i >= 0; --i) {

            //Si la linea no está vacia entonces se procede a sacar un peek de acceso y se crea un vehiculoClon para no referenciar el de peek y hacer
            //un cambio inesperado
            if (!acceso.lineaVacia()) {
                Vehiculo vehiculoTemp = acceso.peek();
                Vehiculo vehiculoClon = new Vehiculo(vehiculoTemp.getTamanio(), vehiculoTemp.getTipoServicio(), vehiculoTemp.isPreferencia(), vehiculoTemp.getMarca(), vehiculoTemp.getColor());

                JLabel labelActual = this.acceso.get(i);
                System.out.println("INDICE: " + i);
                labelActual.setFont(new Font("Arial", Font.BOLD,10));
                labelActual.setText("<html>" + vehiculoClon.getMarca() + "<br>" + vehiculoClon.getTamanio() + "<br>" + "Pref: " + vehiculoClon.isPreferencia() + "</html>");
                labelActual.setBackground(asignarColor(vehiculoClon.getColor()));
                accesoTemp.insertar(acceso.eliminar());

                //En dado caso que este vacia acceso, simplemente se cambia al JLabel preterminado que se configuro en el constructor
                //Para no dejar un carro como residuo de no haber actualizado los gráfico
            } else {

                JLabel labelActual = this.acceso.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,12));
                labelActual.setText(String.valueOf(i));
                labelActual.setBackground(Color.WHITE);
            }

            if (accesoTemp.lineaLlena()) {
                panelIzquierdoEAST.setBackground(new Color(255,105,97));
            } else {
                panelIzquierdoEAST.setBackground(backGroundBASE);
            }

        }

        while (!accesoTemp.lineaVacia()) {
            acceso.insertar(accesoTemp.eliminar());
        }
        panelIzquierdo.repaint();
        panelIzquierdo.revalidate();

        for (int i = this.lavado.size()-1; i >= 0; --i) {

            if (!lavado.lineaVacia()) {
                Vehiculo vehiculoTemp = lavado.peek();
                Vehiculo vehiculoClon =  new Vehiculo(vehiculoTemp.getTamanio(), vehiculoTemp.getTipoServicio(), vehiculoTemp.isPreferencia(), vehiculoTemp.getMarca(), vehiculoTemp.getColor());

                JLabel labelActual = this.lavado.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,10));
                labelActual.setText("<html>" + vehiculoClon.getMarca() + "<br>" + vehiculoClon.getTamanio() + "<br>" + "Pref: " + vehiculoClon.isPreferencia() + "</html>");
                labelActual.setBackground(asignarColor(vehiculoClon.getColor()));
                lavadoTemp.insertar(lavado.eliminar());
            } else {
                JLabel labelActual = this.lavado.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,12));
                labelActual.setText(String.valueOf(i));
                labelActual.setBackground(Color.WHITE);
            }
            if (lavadoTemp.lineaLlena()) {
                panelCentral.setBackground(new Color(255,105,97));
            } else {
                panelCentral.setBackground(backGroundBASE);
            }
        }

        while (!lavadoTemp.lineaVacia()) {
            lavado.insertar(lavadoTemp.eliminar());
        }
        panelCentral.repaint();
        panelCentral.revalidate();

        for (int i = this.secado.size()-1; i >= 0; --i) {

            if (!secado.lineaVacia()) {
                Vehiculo vehiculoTemp = secado.peek();
                Vehiculo vehiculoClon =  new Vehiculo(vehiculoTemp.getTamanio(), vehiculoTemp.getTipoServicio(), vehiculoTemp.isPreferencia(), vehiculoTemp.getMarca(), vehiculoTemp.getColor());

                JLabel labelActual = this.secado.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,10));
                labelActual.setText("<html>" + vehiculoClon.getMarca() + "<br>" + vehiculoClon.getTamanio() + "<br>" + "Pref: " + vehiculoClon.isPreferencia() + "</html>");
                labelActual.setBackground(asignarColor(vehiculoClon.getColor()));
                secadoTemp.insertar(secado.eliminar());
            } else {
                JLabel labelActual = this.secado.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,12));
                labelActual.setText(String.valueOf(i));
                labelActual.setBackground(Color.WHITE);
            }
            if (secadoTemp.lineaLlena()) {
                panelDerecho.setBackground(new Color(255,105,97));
            } else {
                panelDerecho.setBackground(backGroundBASE);
            }
        }
        while (!secadoTemp.lineaVacia()) {
            secado.insertar(secadoTemp.eliminar());
        }
        panelDerecho.repaint();
        panelDerecho.revalidate();

        for (int i = this.aspirado.size()-1; i >= 0; --i) {

            ArrayList<JLabel> aspiradoIndLabel = this.aspirado.get(i);

            for (int j = aspiradoIndLabel.size()-1; j>= 0; --j) {

                if (!aspirado.get(i).lineaVacia()) {
                    Vehiculo vehiculoTemp = aspirado.get(i).peek();
                    Vehiculo vehiculoClon =  new Vehiculo(vehiculoTemp.getTamanio(), vehiculoTemp.getTipoServicio(), vehiculoTemp.isPreferencia(), vehiculoTemp.getMarca(), vehiculoTemp.getColor());

                    JLabel labelActual = aspiradoIndLabel.get(j);
                    labelActual.setFont(new Font("Arial", Font.BOLD,10));
                    labelActual.setText("<html>" + vehiculoClon.getMarca() + "<br>" + vehiculoClon.getTamanio() + "<br>" + "Pref: " + vehiculoClon.isPreferencia() + "</html>");
                    labelActual.setBackground(asignarColor(vehiculoClon.getColor()));
                    aspiradoTemp.get(i).insertar(aspirado.get(i).eliminar());
                } else {
                    JLabel labelActual = aspiradoIndLabel.get(j);
                    labelActual.setFont(new Font("Arial", Font.BOLD,12));
                    labelActual.setText(String.valueOf(j));
                    labelActual.setBackground(Color.WHITE);
                }
                if (aspiradoTemp.get(i).lineaLlena()) {
                    panelDerecho.setBackground(new Color(255,105,97));
                } else {
                    panelDerecho.setBackground(backGroundBASE);
                }
            }
        }

        for (int i = this.aspirado.size()-1; i >= 0; --i) {

            while (!aspiradoTemp.get(i).lineaVacia()) {
                aspirado.get(i).insertar(aspiradoTemp.get(i).eliminar());
            }
        }
        panelDerecho.repaint();
        panelDerecho.revalidate();

        if (carWash.getAcceso().lineaLlena()) {
            if (carWash.getVehiculo() != null) {
                Vehiculo vehiculoTemp = carWash.getVehiculo();
                vehiculoActual.setFont(new Font("Arial", Font.BOLD,10));
                vehiculoActual.setText("<html>" + vehiculoTemp.getMarca() + "<br>" + vehiculoTemp.getTamanio() + "<br>" + "Pref: " + vehiculoTemp.isPreferencia() + "</html>");
                vehiculoActual.setBackground(asignarColor(vehiculoTemp.getColor()));
            } else {
                vehiculoActual.setFont(new Font("Arial", Font.BOLD,10));
                vehiculoActual.setText("0");
                vehiculoActual.setBackground(Color.WHITE);
            }
        } else {
            vehiculoActual.setFont(new Font("Arial", Font.BOLD,10));
            vehiculoActual.setText("0");
            vehiculoActual.setBackground(Color.WHITE);
        }


    }

    /**
     * Método para crear el registro gráfico
     */
    private void registroGrafico()
    {
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBackground(Color.white);

        int tamanio = carWash.getRegistro().lineaTamanio();
        for (int i = 0; i < tamanio; ++i) {
            if (!carWash.getRegistro().lineaVacia()) {
                System.out.println(i);
                Vehiculo vehiculoTemp = carWash.getRegistro().eliminarVehiculo();
                panelRegistro.add(new JLabel("[" + (i+1) + "] " + "Vehiculo: "  + vehiculoTemp.getMarca() + " Tipo de servicio: " + vehiculoTemp.getTipoServicio() + " Tamaño: " + vehiculoTemp.getTamanio() + " Hora salida: " + vehiculoTemp.getHoraSalida()));
            }

        }
        System.out.println(carWash.getRegistro().lineaTamanio());

        JScrollPane scrollPane = new JScrollPane(panelRegistro);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(panelControl, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

    }

    /**
     * Método que regresa el color en función del parametro colorS
     * @param colorS
     * @return
     */
    private Color asignarColor(String colorS)
    {
        Color color = null;
        switch (colorS) {
            case "Azul":
                color = Color.BLUE;
                break;
            case "Verde":
                color = Color.GREEN;
                break;
            case "Rojo":
                color = Color.RED;
                break;
            case "Blanco":
                color = Color.WHITE;
                break;
            case "Morado":
                color = Color.PINK;
                break;
            case "gris":
                color = Color.GRAY;
                break;
            case "Naranja":
                color = Color.ORANGE;
                break;
        }
        return color;
    }


}
