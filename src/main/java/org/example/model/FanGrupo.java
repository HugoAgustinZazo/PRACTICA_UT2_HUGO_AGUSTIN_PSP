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

		/* En este método vemos la linea de ejecución de cada hilo de los Fans. Cada vez que entramos dormimos un segundo a cada fan para darle tiempo a pensar
		 */
		try {
		Thread.sleep(1000);
		//El fan saluda al entrar
		logger.info(tabuladores+"Fan "+numeroFan+" :Hola!");
		//En este bucle van a estar los fans continuamente intentando comprar entradas hasta que se cierre la venta
		do{
			//Si las entradas de cada fan son menos que las entradas totales que pueden comprar cada uno entra al codigo sino no
			if(entradasCompradas<EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
				logger.info(tabuladores + "Fan " + numeroFan + " :Intento comprar entrada");
				boolean comprarEntradas = webCompra.comprarEntrada();
				//Si hay entradas es decir el metodo nos devuelve true, el fan compra entradas y duerme.
				if (comprarEntradas) {
					entradasCompradas += 1;
					logger.info(tabuladores + "Fan " + numeroFan + " Compré! Llevo: " + entradasCompradas);
					logger.info(tabuladores + "Fan " + numeroFan + " (dormir zzzz)");
					Random rd = new Random();
					int tiempoDormir = rd.nextInt(1, 4);
					Thread.sleep(tiempoDormir * 1000);
				}
			}
		}while (!WebCompraConciertos.cerrarVenta&&entradasCompradas<EjemploTicketMaster.MAX_ENTRADAS_POR_FAN);
		//Cuando se cierra la venta el fan imprime que ha terminado
			logger.info(tabuladores+"Fan "+numeroFan+" -Termino- ");
		} catch(InterruptedException e){
				throw new RuntimeException(e);
			}
    }
	
	public void muestraEntradasCompradas() {
		int total = 0;
		//Solo mostramos a quienes han conseguido entradas
		if(entradasCompradas>0) {
			logger.info(tabuladores + "Fan "+numeroFan+": Solo he conseguido: "+entradasCompradas);
			total +=entradasCompradas;
		}
		//Si el total de entradas compradas por los fans es igual al total de entradas se muestran las entradas compradas por los mismos
		if (total==EjemploTicketMaster.TOTAL_ENTRADAS) {
			logger.info("Total entradas compradas por los fans "+total);
		}
	}
	

}
