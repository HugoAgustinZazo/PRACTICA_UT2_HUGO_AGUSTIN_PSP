package org.example.model;

import org.example.EjemploTicketMaster;

import java.util.Random;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}
	
	@Override
	public void run() {
		System.out.println(tabuladores+"Fan "+numeroFan+" :Hola!");
        try {
		do{
			Thread.sleep(1000);
			System.out.println(tabuladores + "Fan " + numeroFan + " :Intento comprar entrada");
			boolean comprarEntradas = webCompra.comprarEntrada();
			if (comprarEntradas) {
				entradasCompradas += 1;
				System.out.println(tabuladores + "Fan " + numeroFan + " Compré! Llevo: " + entradasCompradas);
				System.out.println(tabuladores + "Fan " + numeroFan + " (dormir zzzz)");
				Random rd = new Random();
				int tiempoDormir = rd.nextInt(1, 4);
				Thread.sleep(tiempoDormir * 1000);
			}
		}while (!WebCompraConciertos.cerrarVenta);
			System.out.println(tabuladores+"Fan "+numeroFan+" -Termino- ");

		} catch(InterruptedException e){
				throw new RuntimeException(e);
			}
    }
	
	public void muestraEntradasCompradas() {
		int total = 0;
		if(entradasCompradas>0) {
			System.out.println(tabuladores + "Fan "+numeroFan+": Solo he conseguido: "+entradasCompradas);
			total +=entradasCompradas;
		}
		System.out.println("Total entradas compradas por los fans "+total);
	}
	

}
