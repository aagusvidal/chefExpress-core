import test.HolatestJava;
import org.junit.*; // Importa las anotaciones de JUnit

public class TestAll {
    @Test
    public void testSaludo() {
        HolatestJava o = new HolatestJava();
        assert "Hola, Mundo! (en Java)".equals(o.saluda("Mundo"));
    }
}