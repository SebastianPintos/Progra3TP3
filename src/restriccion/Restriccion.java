package restriccion;

public class Restriccion {
	int minimo;
	int maximo;
	int maximoLimite;
	
	public Restriccion(int max) {
		minimo=Integer.MIN_VALUE;
		maximo=Integer.MAX_VALUE;
		maximoLimite=max;
	}

	public void set(int mini,int maxi) {
		if(esValida(mini,maxi)) {
			minimo=mini;
			maximo=maxi;
		}
	}
	
	public boolean esValida(int mini, int maxi) {
		if (esNegativo(mini) || esNegativo(maxi) || mini>maxi || maxi>maximoLimite)
			return false;
		return true;
			
	}
	
	private boolean esNegativo(int mini) {
		return mini<0;
	}
	
	public int getMinimo() {
		return minimo;
	}
	
	public int getMaximo() {
		return maximo;
	}
	
	public int getMaximoLimite() {
		return maximoLimite;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass()!=getClass())
			return false;
		Restriccion otra=(Restriccion)o;
		return otra.getMinimo()==getMinimo()  && otra.getMaximo()==getMaximo() && getMaximoLimite()==otra.getMaximoLimite();
	}
}
