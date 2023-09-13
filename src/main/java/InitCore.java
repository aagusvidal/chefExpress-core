import factories.ChefExpressFactory;
import model.ObservableChefExpress;

public class InitCore {
    protected String propertyPath;
    protected String defaultConfig;
    protected ChefExpressFactory factory;
        public InitCore(String propertyPath, String defaultConfig) throws Exception {
            this.propertyPath = propertyPath;
            this.defaultConfig = defaultConfig;
             factory = new ChefExpressFactory(propertyPath, defaultConfig);

        }
        public ObservableChefExpress init() throws Exception {
            return factory.createChefExpress();
        }
}
