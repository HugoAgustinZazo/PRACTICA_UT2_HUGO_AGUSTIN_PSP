package org.example.model;

import org.example.EjemploTicketMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class PromotoraConciertos extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(PromotoraConciertos.class);
	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {
				try {
					int numvueltas=EjemploTicketMaster.TOTAL_ENTRADAS/EjemploTicketMaster.REPOSICION_ENTRADAS;
					int contadorNumEntradasRepuestas = 0;
					int numEntradasRepuestas = 0;
					int contador=0;
					do {
						if (!webCompra.hayEntradas()) {
							if(contador==(numvueltas)){
								numEntradasRepuestas=EjemploTicketMaster.TOTAL_ENTRADAS-contadorNumEntradasRepuestas;

							}else{
								numEntradasRepuestas=EjemploTicketMaster.REPOSICION_ENTRADAS;
							}
							logger.info("Promotora: Repongo " + numEntradasRepuestas);
							webCompra.reponerEntradas(numEntradasRepuestas);
							contadorNumEntradasRepuestas+=EjemploTicketMaster.REPOSICION_ENTRADAS;
							Random rd = new Random();
							int numSegundos = rd.nextInt(3, 9);
							Thread.sleep(numSegundos * 1000);
							contador++;
						}
					}while(contador<=numvueltas);
					logger.info(" Promotora: Ya se han terminado las entradas, qu� pena. Vendí: "+WebCompraConciertos.entradasVendidas);
					webCompra.cerrarVenta();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
		}
	}