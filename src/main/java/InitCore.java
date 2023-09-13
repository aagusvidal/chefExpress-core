import factories.ChefExpressFactory;
import model.ObservableChefExpress;

public class InitCore {
    protected String propertyPath;
    protected String defaultConfig;
        public InitCore(String propertyPath, String defaultConfig) throws Exception {
            this.propertyPath = propertyPath;
            this.defaultConfig = defaultConfig;

        }
        public ObservableChefExpress init() throws Exception {
            ChefExpressFactory factory = new ChefExpressFactory(propertyPath, defaultConfig);
            return factory.createChefExpress();
        }
}
