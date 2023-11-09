package factories;

import core.ChefExpress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChefExpressBuilder
{
    private ChefExpressInit chefExpressInit;

    public ChefExpressBuilder()
    {
        this.chefExpressInit =  new ChefExpressInit();
    }

    public ChefExpress build(String propertyPath) throws Exception
    {
        Properties chefExpressProperties = loadProperties(propertyPath);;
        return this.chefExpressInit.initChefExpress(chefExpressProperties);
    }

    private Properties loadProperties(String configPath)
    {
        Properties prop = new Properties();
        try
        {
            prop.load( new FileInputStream(configPath) );
        } catch (IOException e) { e.printStackTrace(); }

        return prop;
    }
}
