/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mlproject;
import java.io.Serializable;

/**
 *
 * @author sebas
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String data; //En este campo se almacena la pregunta o la respuesta
    Node yes; //Nodos que contendrán la respuesta o pregunta
    Node no;
    
    public Node(String data){ //Constructor para crear un nuevo nodo, (nueva respuesta o pregunta)
        this.data = data;
        this.yes = null;
        this.no = null;
    }
    
    public boolean isLeaf(){
        return this.yes == null && this.no == null;
    }
    
    
    
}
