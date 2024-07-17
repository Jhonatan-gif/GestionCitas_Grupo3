package Modelo;

public class Medicamento {
    private int id;
    private String nombre;
    private String principio;
    private String tipo;
    private double gramaje;
    private int stock;


    public Medicamento(){}

    public Medicamento(int id, String nombre, String principio, String tipo, double gramaje, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.principio = principio;
        this.tipo = tipo;
        this.gramaje = gramaje;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrincipio() {
        return principio;
    }

    public void setPrincipio(String principio) {
        this.principio = principio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getGramaje() {
        return gramaje;
    }

    public void setGramaje(double gramaje) {
        this.gramaje = gramaje;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


}