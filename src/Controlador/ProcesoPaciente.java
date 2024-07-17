package Controlador;

import Modelo.Consultorio;
import Modelo.Paciente;
import java.util.ArrayList;

public class ProcesoPaciente {
    private ArrayList<Object> a = new ArrayList<Object>();

    public ProcesoPaciente(){}

    public ProcesoPaciente(ArrayList<Object> a){
        this.a = a;
    }

    public void agregarRegistro(Paciente p){
        this.a.add(p);
    }

    public void modificarRegistro(int i, Paciente p){
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i){
        this.a.remove(i);
    }

    public Paciente obtenerRegistro(int i){
        return (Paciente)a.get(i);
    }

    public int cantidadRegistro(){
        return this.a.size();
    }

    public int buscaCodigo(int codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            if(codigo == obtenerRegistro(i).getId())return i;
        }
        return -1;
    }
}
