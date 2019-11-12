package observer;

public interface ObjetoObservable {
	
	public void enlazar(Observador observador);
	public void notificar();
}
