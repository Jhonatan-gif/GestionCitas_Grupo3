package Modelo;

public class Consultorio {
    private int idConsultorio;
    private String descripcion;

    public Consultorio(){}


    public Consultorio(int idConsultorio, String descripcion) {
        this.idConsultorio = idConsultorio;
        this.descripcion = descripcion;
    }



    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}