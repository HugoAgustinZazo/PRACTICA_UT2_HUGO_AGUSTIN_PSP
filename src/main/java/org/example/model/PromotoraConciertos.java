package org.example.model;

import org.example.EjemploTicketMaster;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {
		System.out.println("Repongo " + EjemploTicketMaster.REPOSICION_ENTRADAS);
		webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);

	}

}
