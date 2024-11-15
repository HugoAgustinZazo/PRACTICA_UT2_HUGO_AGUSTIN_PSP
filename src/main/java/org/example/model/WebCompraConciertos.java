package org.example.model;

import org.example.EjemploTicketMaster;
import org.example.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb {
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
		while(entradasRestantesRepuestas==0&&!cerrarVenta) {
			System.out.println("WebCompra: SOLD OUT! Esperamos a que repongan entradas");
			wait();
		}
		if (!cerrarVenta) {
			System.out.println("WebCompra:  comprada quedan " + entradasRestantes());
			entradasVendidas++;
			notify();
			return true;
		}
		return false;
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) throws InterruptedException {
		System.out.println("WebCompra: Reposicion: Ahora hay "+numeroEntradas);
		entradasRestantesRepuestas = numeroEntradas;
		hayentradas=true;
		notify();
		return numeroEntradas;
	}


	@Override
	public synchronized void cerrarVenta() {
		if(entradasTotales==0) {
			cerrarVenta = true;
			notifyAll();
		}
	}


	@Override
	public synchronized boolean hayEntradas() throws InterruptedException {
		if(entradasRestantesRepuestas!=0) {
			hayentradas=true;
			return true;
		}
		return false;
	}


	@Override
	public synchronized int entradasRestantes() {
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
