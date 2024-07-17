package Controlador;

import Modelo.Consultorio;
import Modelo.Medicamento;
import java.util.ArrayList;

public class ProcesoMedicamento {

    private ArrayList<Object> a = new ArrayList<Object>();

    public ProcesoMedicamento(){}

    public ProcesoMedicamento(ArrayList<Object> a){
        this.a = a;
    }

    public void agregarRegistro(Medicamento p){
        this.a.add(p);
    }

    public void modificarRegistro(int i, Medicamento p){
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i){
        this.a.remove(i);
    }

    public Medicamento obtenerRegistro(int i){
        return (Medicamento)a.get(i);
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
