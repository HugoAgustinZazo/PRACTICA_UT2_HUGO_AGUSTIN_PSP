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

					//La promotora estara continuamente intentando reponer entradas hasta que no pueda reponer más
					do {
						//La promotora solo repondra cuando no haya entradas, si hay entradas simplemente dara vueltas al bucle sin hacer nada
						if (!webCompra.hayEntradas()) {
							//Esta condicion la usamos para cuando las entradas son impares y no repondriamos igual en cada tanda por lo cual lo que controla es que
							//si estamos en la ultima tanda que reponga lo que resta entre el total de entradas y las que llevamos repuestas
							if(contador==(numvueltas)){
								numEntradasRepuestas=EjemploTicketMaster.TOTAL_ENTRADAS-contadorNumEntradasRepuestas;
							//Si no es la ultima vuelta repondremos de manera normal sea o no impar el número de entradas
							}else{
								numEntradasRepuestas=EjemploTicketMaster.REPOSICION_ENTRADAS;
							}
							//Si la promotora repone entradas dormira entre 3 y 8 segundos al poder reponer
							logger.info("Promotora: Repongo " + numEntradasRepuestas);
							webCompra.reponerEntradas(numEntradasRepuestas);
							contadorNumEntradasRepuestas+=EjemploTicketMaster.REPOSICION_ENTRADAS;
							Random rd = new Random();
							int numSegundos = rd.nextInt(3, 9);
							Thread.sleep(numSegundos * 1000);
							//Sumamos el contador para asi pasar a la siguiente tanda
							contador++;
						}
					}while(contador<=numvueltas);
					//Cuando ya hemos repuesto todas las tandas imprimimos un mensaje con las entradas vendidas
					logger.info(" Promotora: Ya se han terminado las entradas, qu� pena. Vendí: "+WebCompraConciertos.entradasVendidas);
					webCompra.cerrarVenta();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
		}
	}