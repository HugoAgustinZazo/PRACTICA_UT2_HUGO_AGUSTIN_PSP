package org.example.model;

import org.example.EjemploTicketMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class FanGrupo extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(FanGrupo.class);
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
		logger.info(tabuladores+"Fan "+numeroFan+" :Hola!");
        try {
		do{
			Thread.sleep(1000);
			logger.info(tabuladores + "Fan " + numeroFan + " :Intento comprar entrada");
			boolean comprarEntradas = webCompra.comprarEntrada();
			if (comprarEntradas) {
				entradasCompradas += 1;
				logger.info(tabuladores + "Fan " + numeroFan + " Compré! Llevo: " + entradasCompradas);
				logger.info(tabuladores + "Fan " + numeroFan + " (dormir zzzz)");
				Random rd = new Random();
				int tiempoDormir = rd.nextInt(1, 4);
				Thread.sleep(tiempoDormir * 1000);
			}
		}while (!WebCompraConciertos.cerrarVenta);
			logger.info(tabuladores+"Fan "+numeroFan+" -Termino- ");
		} catch(InterruptedException e){
				throw new RuntimeException(e);
			}
    }
	
	public void muestraEntradasCompradas() {
		int total = 0;
		if(entradasCompradas>0) {
			logger.info(tabuladores + "Fan "+numeroFan+": Solo he conseguido: "+entradasCompradas);
			total +=entradasCompradas;
		}
		logger.info("Total entradas compradas por los fans "+total);

	}
	

}
