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
    private JLabel labelTiempo;
    private JButton botonAceleracion;
    private ArrayList<JLabel> lavado, acceso, secado;
    private ArrayList<ArrayList<JLabel>> aspirado;

    private CarWash carWash;

    public CarWashGUI() {
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
        botonAceleracion.addActionListener(lectura -> botonAceleracion());
        botonAceleracion.setFocusPainted(false);
        botonAceleracion.setPreferredSize(new Dimension(90,20));
        botonAceleracion.setOpaque(true);
        botonAceleracion.setBackground(new Color(236,234,227));

        labelTiempo = new JLabel("8:00", SwingConstants.CENTER);
        labelTiempo.setOpaque(true);
        labelTiempo.setPreferredSize(new Dimension(50,20));
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


        JLabel labelTituloAcceso = new JLabel("Cola de espera", SwingConstants.CENTER);
        labelTituloAcceso.setFont(new Font("Arial", Font.BOLD, 20));
        panelIzquierdo.add(labelTituloAcceso, BorderLayout.NORTH);
        panelIzquierdo.add(panelAcceso, BorderLayout.CENTER);

        JLabel labelTituloLavado = new JLabel("Lavado", SwingConstants.CENTER);
        labelTituloLavado.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(labelTituloLavado, BorderLayout.NORTH);
        panelCentral.add(panelLavado, BorderLayout.CENTER);

        JLabel labelTituloServicio = new JLabel("Secado y aspirado", SwingConstants.CENTER);
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

    private void generadorElementoVehiculoFrame()
    {

    }

    private void botonAceleracion()
    {

    }
}
