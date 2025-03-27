//Clase que simula un vehiculo del sistema de lavado
public class Vehiculo {

    //Atributos que lo componen
    private String tamanio;
    private String tipoServicio;
    private boolean preferencia;
    private String marca;
    private String color;
    private String horaLlegada;
    private String horaSalida;
    private int horaLlegadaInt;
    private int horaSalidaInt;


    /**
     * Constructor preterminado donde se necesita de sus características para poder ser instanciado
     * @param tamanio
     * @param tipoServicio
     * @param preferencia
     * @param marca
     * @param color
     */
    public Vehiculo(String tamanio, String tipoServicio, boolean preferencia, String marca, String color)
    {
        this.tamanio = tamanio;
        this.tipoServicio = tipoServicio;
        this.preferencia = preferencia;
        this.marca = marca;
        this.color = color;
        horaLlegadaInt = 0;
        horaSalidaInt = 0;
        horaLlegada = "0";
        horaSalida = "0";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters y setters de clase


    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public boolean isPreferencia() {
        return preferencia;
    }

    public void setPreferencia(boolean preferencia) {
        this.preferencia = preferencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getHoraLlegadaInt() {
        return horaLlegadaInt;
    }

    public void setHoraLlegadaInt(int horaLlegadaInt) {
        this.horaLlegadaInt = horaLlegadaInt;
    }

    public int getHoraSalidaInt() {
        return horaSalidaInt;
    }

    public void setHoraSalidaInt(int horaSalidaInt) {
        this.horaSalidaInt = horaSalidaInt;
    }

    public String toString()
    {
        return marca + " " + color + "\n" + "Tamaño: " + tamanio + "\n" + "Tipo de servicio: " + tipoServicio + "\n"
                + "Preferencia: " + (preferencia ? "Si" : "no") + "\n" + "Hora de llegada: " + horaLlegada + "\n" +
                "hora de salida: " + horaSalida;
    }
}
