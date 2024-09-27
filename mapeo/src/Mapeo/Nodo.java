/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapeo;

class Nodo {

    int Fila;
    int columna;
    int Distancia;
    boolean Visto;
    Nodo Anterior;

    public Nodo(int row, int col) {
        this.Fila = row;
        this.columna = col;
        this.Distancia = Integer.MAX_VALUE;
        this.Visto = false;

    }}