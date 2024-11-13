package org.example.model;

import org.example.EjemploTicketMaster;
import org.example.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb {

	
	public WebCompraConciertos() {
		super();
	}


	@Override
	public synchronized boolean comprarEntrada() throws InterruptedException {
		int entradasRestantesRepuestas = EjemploTicketMaster.REPOSICION_ENTRADAS;

		if (entradasRestantesRepuestas != 0) {
			while (!hayEntradas()) {
				wait();
			}
			entradasRestantesRepuestas -= 1;
			System.out.println("WebCompra:  comprada quedan"+entradasRestantesRepuestas);
			return true;

		}else {
			System.out.println("WebCompra: SOLD OUT! Esperamos que repongan entradas ");
		}
		return false;
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		System.out.println("WebCompra:Reposicion Ahora hay "+numeroEntradas);
		return numeroEntradas;
	}


	@Override
	public synchronized void cerrarVenta() {
		// TODO Auto-generated method stub
	}


	@Override
	public synchronized boolean hayEntradas() {
			notifyAll();
		return false;
	}


	@Override
	public synchronized int entradasRestantes() {
		int entradasRestantes =  EjemploTicketMaster.REPOSICION_ENTRADAS - 1;
		return entradasRestantes;
	}



}
