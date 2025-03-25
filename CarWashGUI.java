import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ArrayList;

public class CarWashGUI {

    private JFrame frame;
    private JPanel panelIzquierdo, panelCentral, panelDerecho;
    private JPanel panelAcceso, panelLavado, panelAspiradoSecado, panelControl;
    private JLabel labelTiempo, labelTituloAcceso, labelTituloLavado, labelTituloServicio;
    private JButton botonAceleracion;
    private ArrayList<JLabel> lavado, acceso, secado;
    private ArrayList<ArrayList<JLabel>> aspirado;

    private CarWash carWash;

    public CarWashGUI() {

        carWash = new CarWash();

        frame = new JFrame("Car Wash");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelAcceso = new JPanel(new GridBagLayout());
        panelLavado = new JPanel(new GridBagLayout());
        panelAspiradoSecado = new JPanel(new GridBagLayout());

        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelControl.setOpaque(true);
        panelControl.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,5,10,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        botonAceleracion = new JButton("Acelerar");
        botonAceleracion.addActionListener(_ -> botonAceleracion());
        botonAceleracion.setFocusPainted(false);
        botonAceleracion.setPreferredSize(new Dimension(90,20));
        botonAceleracion.setOpaque(true);
        botonAceleracion.setBackground(new Color(236,234,227));

        labelTiempo = new JLabel("8:00", SwingConstants.CENTER);
        labelTiempo.setOpaque(true);
        labelTiempo.setPreferredSize(new Dimension(70,20));
        labelTiempo.setBackground(Color.WHITE);

        acceso = new ArrayList<>(10);
        lavado = new ArrayList<>(3);
        secado = new ArrayList<>(5);
        aspirado = new ArrayList<>(4);

        //Inicializar el arrayList interior de aspirado que corresponde a la cantidad de elementos(JLabels en cada elemento
        // de aspirado)
        for (int i = 0; i < 4; ++i) {
            aspirado.add(new ArrayList<>(4));
        }

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

        panelControl.add(labelTiempo);
        panelControl.add(botonAceleracion);


        labelTituloAcceso = new JLabel("Cola de espera", SwingConstants.CENTER);
        labelTituloAcceso.setFont(new Font("Arial", Font.BOLD, 20));
        panelIzquierdo.add(labelTituloAcceso, BorderLayout.NORTH);
        panelIzquierdo.add(panelAcceso, BorderLayout.CENTER);

        labelTituloLavado = new JLabel("Lavado", SwingConstants.CENTER);
        labelTituloLavado.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(labelTituloLavado, BorderLayout.NORTH);
        panelCentral.add(panelLavado, BorderLayout.CENTER);

        labelTituloServicio = new JLabel("Secado y aspirado", SwingConstants.CENTER);
        labelTituloServicio.setFont(new Font("Arial", Font.BOLD, 20));
        panelDerecho.add(labelTituloServicio, BorderLayout.NORTH);
        panelDerecho.add(panelAspiradoSecado, BorderLayout.CENTER);

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
        //Segundo ciclo corresponde a minutos(que en realdia son soegundos en la simulacion)
        //Luego el Thread de 1000 milisegundos que corresponde a un segundo.
        int max = 10;
        for (int i = 0; i < max; ++i) {
            labelTiempo.setText(carWash.generarHoraString(i,0));

            for (int j = 0; j < 60; ++j) {
                if (j != 0) {
                    labelTiempo.setText(carWash.generarHoraString(i,j));
                }

                try {
                    Thread.sleep(carWash.getAceleracion());

                    boolean lineaEstado = false;

                    for (ColaVehiculo aspiradoInd : carWash.getAspirado()) {

                        if (!aspiradoInd.lineaVacia()) {
                            lineaEstado = true;
                            break;
                        }
                    }

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
        registroGrafico();

    }

    private void generadorElementoVehiculoFrame()
    {

    }

    private void botonAceleracion()
    {
        if (carWash.getAceleracion() == 100) {
            carWash.setAceleracion(1000);
        } else {
            carWash.setAceleracion(carWash.getAceleracion() - 100);
        }
        System.out.println(carWash.getAceleracion());
    }

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

        for (int i = this.acceso.size()-1; i >= 0; --i) {

            if (!acceso.lineaVacia()) {
                Vehiculo vehiculoTemp = acceso.peek();
                Vehiculo vehiculoClon = new Vehiculo(vehiculoTemp.getTamanio(), vehiculoTemp.getTipoServicio(), vehiculoTemp.isPreferencia(), vehiculoTemp.getMarca(), vehiculoTemp.getColor());

                JLabel labelActual = this.acceso.get(i);
                System.out.println("INDICE: " + i);
                labelActual.setFont(new Font("Arial", Font.BOLD,10));
                labelActual.setText("<html>" + vehiculoClon.getMarca() + "<br>" + vehiculoClon.getTamanio() + "<br>" + "Pref: " + vehiculoClon.isPreferencia() + "</html>");
                labelActual.setBackground(asignarColor(vehiculoClon.getColor()));
                accesoTemp.insertar(acceso.eliminar());
            } else {
                JLabel labelActual = this.acceso.get(i);
                labelActual.setFont(new Font("Arial", Font.BOLD,12));
                labelActual.setText(String.valueOf(i));
                labelActual.setBackground(Color.WHITE);
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
            }
        }

        for (int i = this.aspirado.size()-1; i >= 0; --i) {

            while (!aspiradoTemp.get(i).lineaVacia()) {
                aspirado.get(i).insertar(aspiradoTemp.get(i).eliminar());
            }
        }
        panelDerecho.repaint();
        panelDerecho.revalidate();

    }

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
