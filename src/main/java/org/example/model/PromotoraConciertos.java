package org.example.model;

import org.example.EjemploTicketMaster;

import java.util.Random;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {
				try {
					int restoNumvueltas=EjemploTicketMaster.TOTAL_ENTRADAS%EjemploTicketMaster.REPOSICION_ENTRADAS;
					int numvueltas=EjemploTicketMaster.TOTAL_ENTRADAS/EjemploTicketMaster.REPOSICION_ENTRADAS;
					int numEntradasfinal = EjemploTicketMaster.TOTAL_ENTRADAS-(numvueltas*EjemploTicketMaster.REPOSICION_ENTRADAS);

					int contador=0;
					do {
						if (!webCompra.hayEntradas()) {
							System.out.println("Promotora: Repongo " + EjemploTicketMaster.REPOSICION_ENTRADAS);
							webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
							Random rd = new Random();
							int numSegundos = rd.nextInt(3, 9);
							Thread.sleep(numSegundos * 1000);
							contador++;
						}
					}while(contador<numvueltas);
					webCompra.cerrarVenta();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
		}
	}