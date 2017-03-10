package es.ucm.fdi.tp.practica6.bgame.model;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public class ErrorResponse implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8365380346760616549L;
	private String msg;
	
	public ErrorResponse(String msg){
		this.msg = msg;
	}
	
	public void run(GameObserver o){
		o.onError(msg);
	}
}
