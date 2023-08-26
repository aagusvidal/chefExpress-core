package entidades;
import java.util.List;

public class Receta {

	protected int id;
	protected List<Ingrediente> ingredientes;
	
	public Receta(int id, List<Ingrediente> ingredientes) {
		this.id = id;
		this.ingredientes = ingredientes;
	}
	
}
