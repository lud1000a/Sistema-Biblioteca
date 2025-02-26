package exceptions;

public class RentedNotFoundException extends RuntimeException {
	//TODO integracao com GUI para exibir pop-up
	public RentedNotFoundException() {
		super("O LIVRO NAO FOI ENCONTRADO EXCEPTION");
	}
}
