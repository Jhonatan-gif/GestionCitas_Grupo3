package Controlador;

import Modelo.Consultorio;
import java.io.Serializable;
import java.util.ArrayList;


public class ProcesoConsultorio {

    private ArrayList<Object> a = new ArrayList<Object>();

    public ProcesoConsultorio(){}

    public ProcesoConsultorio(ArrayList<Object> a){
        this.a = a;
    }

    public void agregarRegistro(Consultorio p){
        this.a.add(p);
    }

    public void modificarRegistro(int i, Consultorio p){
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i){
        this.a.remove(i);
    }

    public Consultorio obtenerRegistro(int i){
        return (Consultorio)a.get(i);
    }


    public int cantidadRegistro(){
        return this.a.size();
    }

    public int buscaDEs(String codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            String cons=obtenerRegistro(i).getDescripcion();
            if(codigo.equals(cons))return i;
        }
        return -1;
    }

    public int buscaCodigo(int codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            if(codigo == obtenerRegistro(i).getIdConsultorio())return i;
        }
        return -1;
    }

}