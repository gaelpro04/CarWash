public class Vehiculo {

    private String tamanio;
    private String tipoServicio;
    private boolean preferencia;
    private String marca;
    private String color;
    private int horaLlegada;
    private int horaSalida;

    public Vehiculo(String tamanio, String tipoServicio, boolean preferencia, String marca, String color)
    {
        this.tamanio = tamanio;
        this.tipoServicio = tipoServicio;
        this.preferencia = preferencia;
        this.marca = marca;
        this.color = color;
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

    public int getHoraLlegada() {
        return horaLlegada;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraLlegada(int horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String toString()
    {
        return marca + " " + color + "\n" + "Tamaño: " + tamanio + "\n" + "Tipo de servicio: " + tipoServicio + "\n"
                + "Preferencia: " + (preferencia ? "Si" : "no");
    }
}
