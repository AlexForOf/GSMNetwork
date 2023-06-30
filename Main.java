import Functional.Functional;
import Graphical.Application;

public class Main {
    public static void main(String[] args) {
        Functional functional = new Functional();
        functional.init();
        Application application = new Application();
        application.run(functional, functional, functional);
    }
}
