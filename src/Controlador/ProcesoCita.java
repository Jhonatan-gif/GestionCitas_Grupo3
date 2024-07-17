package Controlador;

import Modelo.Cita;
import Modelo.Consultorio;
import java.util.ArrayList;


public class ProcesoCita {
    private ArrayList<Object> a = new ArrayList<Object>();

    public ProcesoCita(){}

    public ProcesoCita(ArrayList<Object> a){
        this.a = a;
    }

    public void agregarRegistro(Cita p){
        this.a.add(p);
    }

    public void modificarRegistro(int i, Cita p){
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i){
        this.a.remove(i);
    }

    public Cita obtenerRegistro(int i){
        return (Cita)a.get(i);
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