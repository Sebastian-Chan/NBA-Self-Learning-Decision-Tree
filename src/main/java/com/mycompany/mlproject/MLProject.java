/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mlproject;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author sebas
 */
public class MLProject {
    
    private void loadTree() {
        File file = new File("nba_tree.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                root = (Node) ois.readObject();
                System.out.println("Memory loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Could not load memory. Starting fresh.");
            }
        } else {
            System.out.println("No saved memory found. Starting fresh.");
        }
    }
    
    Node root;
    private Scanner sc; //Creamos variable que nos ayudará a atrapar las respuestas

    public MLProject(){
        sc = new Scanner(System.in); //Inicializamos var Scanner
        loadTree(); //Aqui hacemos la carga automática del árbol si existe
        if (root == null) {
            root = new Node("Is it in the East?");
            root.yes = new Node("Heat");
            root.no = new Node("Lakers");
        }   
    }
    
    //Función que crearemos para desplegar el menú con el bucle:
    public void start(){
        System.out.println("Welcome to the NBA Guessing Game");
        //menú de opciones a desplegar:
        System.out.println("Options: [p]lay, e[x]it, [s]ave, [d]isplay tree");
        
        while(true){
            System.out.println("Input your option (Input any key to continue): ");
            String choice = sc.nextLine().toLowerCase();
            
            switch(choice){
                case "p":
                    playGame();
                    break;
                case "x":
                    System.out.println("Exiting... Thanks for playing!");
                    return;
                case "s":
                    //Logica de guardado de file
                    System.out.println("Feature: Saving game tree...");
                    saveTree();
                    break;
                case "d":
                    //Cargado de árbol?
                    displayTree();
                    break;
                    
                default: //por si pone una key cualquiera
                    playGame();
                    break;  
            }
        }
    }
    private void playGame(){
        Node current = root;
        Node parent = null; //Sirve para llevar rastro del padre para reconectar
        boolean wentYes = false;
        
        while(!current.isLeaf()){ //Se adivina siempre y cuando no sea null
            System.out.println(current.data + "(y/n)");
            String answer = sc.nextLine().toLowerCase(); //leemos la respuesta y aseguramos minúsculas
            parent = current;
            
            if(answer.equals("y")){
                current = current.yes; //Aqui se sobrescribe
                wentYes = true;
            }else{
                current = current.no;
                wentYes = false;
            }
        }
        //Predicción preeliminar
        System.out.println("Are you thinking of " + current.data + "?");
        String response = sc.nextLine().toLowerCase();
        
        if(response.equals("y")){
            System.out.println("Congratulations to me!");
        }else{
            //Aprendemos del usuario
            System.out.println("Okay, what team where you thinking of?");
            String newTeam = sc.nextLine(); //Variable que almacena el equipo respuesta
            System.out.println("Enter a question to distinguish " + newTeam + " from " + current.data + ":");
            String newQuestion = sc.nextLine();
            System.out.println("What is the answer for " + newTeam + "? (y/n)"); //Leemos la respuesta correcta
            boolean answerForNew = sc.nextLine().toLowerCase().equals("y");
            
            Node oldTeamNode = current; // Crear el nuevo nodo de pregunta
            Node newTeamNode = new Node(newTeam);
            Node questionNode = new Node(newQuestion);

            if (answerForNew) { // Reorganizar el árbol
                questionNode.yes = newTeamNode;
                questionNode.no = oldTeamNode;
            } else {
                questionNode.yes = oldTeamNode;
                questionNode.no = newTeamNode;
            }
            
            if (parent == null) { //conectar al apdre
                root = questionNode;
            } else if (wentYes) {
                parent.yes = questionNode;
            } else {
                parent.no = questionNode;
            }
            System.out.println("I have learned something new, thanks");
            
        }
    }
    
    private void saveTree() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("nba_tree.ser"))) {
            oos.writeObject(root);
            System.out.println("Tree saved successfully to 'nba_tree.ser'.");
        } catch (IOException e) {
            System.out.println("Error saving the tree: " + e.getMessage());
        }
    }
    
    private void printTree(Node node, int level, String prefix) {
        //función recursiva que recorre el árbol
        if (node != null) {
            // imprimir la indentación y el prefijo de la rama (Yes o No)
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }
            
            System.out.println(prefix + (node.isLeaf() ? "[Team: " : "? ") + node.data + (node.isLeaf() ? "]" : ""));
            
            if (!node.isLeaf()) {
                printTree(node.yes, level + 1, "Yes -> ");
                printTree(node.no, level + 1, "No  -> ");
            }
        }
    }

    public void displayTree() { //Mostrará el decision Tree
        System.out.println("\n--- NBA Decision Tree ---");
        printTree(root, 0, "Root: ");
        System.out.println("-------------------------\n");
    }
    
    
    public static void main(String[] args) {
        MLProject game = new MLProject();
        game.start();
        
        
    }
}
