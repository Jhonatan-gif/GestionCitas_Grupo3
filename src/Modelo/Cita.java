package Modelo;

public class Cita {
    private int id;
    private String hora;
    private String desripcion;
    private Consultorio consultorio;

    public Cita(){}

    public Cita(int id, String hora, String desripcion, Consultorio consultorio) {
        this.id = id;
        this.hora = hora;
        this.desripcion = desripcion;
        this.consultorio = consultorio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDesripcion() {
        return desripcion;
    }

    public void setDesripcion(String desripcion) {
        this.desripcion = desripcion;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }


}