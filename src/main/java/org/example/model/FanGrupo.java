package org.example.model;

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
            Thread.sleep(1000);
			System.out.println(tabuladores+"Fan "+numeroFan+" :Intento comprar entrada");
			if(webCompra.comprarEntrada()){
				entradasCompradas+=1;
				System.out.println(tabuladores+"Fan "+numeroFan+" Compré! Llevo: "+entradasCompradas);
				System.out.println(tabuladores+"Fan "+numeroFan+" (dormir zzzz)");
				Random rd = new Random();
				int tiempoDormir = rd.nextInt(1,4);
				Thread.sleep(tiempoDormir*1000);
			}
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
	
	public void muestraEntradasCompradas() {
		if(entradasCompradas>0) {
			System.out.println(tabuladores + "Fan "+numeroFan+": Solo he conseguido: "+entradasCompradas);
		}
	}
	

}
