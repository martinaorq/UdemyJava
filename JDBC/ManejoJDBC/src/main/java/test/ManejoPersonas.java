
package test;

import datos.PersonaJDBC;
import domain.Persona;
import java.util.ArrayList;
import java.util.List;

public class ManejoPersonas {
    public static void main(String[] args) {
        mostrarPersonas(PersonaJDBC.select());
        
        //INSERTANDO PERSONA
        Persona persona=crearPersona("Steven","Universe","cg@gmail.com","548345959");
        PersonaJDBC.insert(persona);
        //PersonaJDBC.insert(persona);
        
        //UPDATE DE PERSONA
        //Persona personaParaCambiar=crearPersona("Lapiz","Lazuli","blu@gmail.com","238894940");
        //PersonaJDBC.update(personaParaCambiar, 4);
        
        mostrarPersonas(PersonaJDBC.select());
        
        //BORRANDO PERSONA
        PersonaJDBC.delete(5);
        mostrarPersonas(PersonaJDBC.select());
    }
    
    public static Persona crearPersona(String nombre, String apellido,String email,String telefono){
        Persona persona=new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setEmail(email);
        persona.setTelefono(telefono);
        return persona;
    }
    
    
    //Lista de personas para mostrar
    public static void mostrarPersonas(List<Persona> personas){
        for(Persona persona:personas){
            System.out.println(persona.toString());
        }
    }
}
