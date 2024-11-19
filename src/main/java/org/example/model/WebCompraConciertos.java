package org.example.model;

import org.example.EjemploTicketMaster;
import org.example.IOperacionesWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCompraConciertos implements IOperacionesWeb {
private static final Logger logger = LoggerFactory.getLogger(WebCompraConciertos.class);
public static int  entradasRestantesRepuestas = 0;
public static boolean hayentradas = false;
public static boolean hayentradasTotales = true;
public static int entradasTotales = EjemploTicketMaster.TOTAL_ENTRADAS;
public static boolean cerrarVenta=false;
public static int entradasVendidas = 0;
	public WebCompraConciertos() {
		super();
	}


	@Override
	public synchronized boolean comprarEntrada() throws InterruptedException {
		//En este metodo controlamos que solo se pueda entrar a intentar comprar entradas mientras que la venta no este cerrada
		//asi evitamos que se nos quede algún hilo en el wait y no nos acabe el programa
		while(entradasRestantesRepuestas==0&&!cerrarVenta) {
			logger.info("WebCompra: SOLD OUT! Esperamos a que repongan entradas");
			wait();
		}
		if (!cerrarVenta) {
			logger.info("WebCompra:  comprada quedan " + entradasRestantes());
			entradasVendidas++;
			notify();
			return true;
		}
		return false;
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) throws InterruptedException {
		//En este metodo reponemos entradas y notificamos a los que esten esperando
		logger.info("WebCompra: Reposicion: Ahora hay "+numeroEntradas);
		entradasRestantesRepuestas = numeroEntradas;
		hayentradas=true;
		notify();
		return numeroEntradas;
	}


	@Override
	public synchronized void cerrarVenta() {
		//En este metodo nos encargamos de cerrar la venta si el numero de entradas totales pasa a ser cero la cerramos y notificamos a todos
		if(entradasTotales==0) {
			cerrarVenta = true;
			notifyAll();
		}
	}


	@Override
	public synchronized boolean hayEntradas() throws InterruptedException {
		//En este metodo controlamos si hay entradas o no y devolvemos true/false en funcion de si hay o no
		if(entradasRestantesRepuestas!=0) {
			hayentradas=true;
			return true;
		}
		return false;
	}


	@Override
	public synchronized int entradasRestantes() {
		//En este metodo vamos restando entradas a las totales y a las disponibles en web al igual que vamos actualizando los booleans segun haya o no entradas y devolvemos
		//las entradas disponibles en web
	if(entradasRestantesRepuestas!=0) {
		entradasRestantesRepuestas--;
		entradasTotales--;
		if (entradasRestantesRepuestas == 0) {
			hayentradas = false;
		}
		if(entradasTotales==0){
			hayentradasTotales=false;
		}
	}
		return entradasRestantesRepuestas;
	}
}
