package finders;

import interfaces.RecipeScorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class RecipeScorerFinder
{
    public String directory;
    private static final String validFileExtension = ".jar";


    public RecipeScorerFinder(String url)
    {
        this.directory = url;
    }

    public  Map<String, RecipeScorer>  find() throws FileNotFoundException
    {
        Map<String, RecipeScorer> scorers = new HashMap<>();
        List<File> filejars = findFileJars(this.directory);

        try
        {
            URL[] urlsList = getUrls(filejars);
            URLClassLoader ClassesLoader = new URLClassLoader(urlsList);
            Set<Class<?>> classes = findClassesInJar(filejars);

            for(Class<?> element : classes)
            {
                if (RecipeScorer.class.isAssignableFrom(element))
                    scorers.put(element.getName(), (RecipeScorer) element.getDeclaredConstructor().newInstance() );
            }
        }
        catch (MalformedURLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }

        return scorers;
    }

    private Set<Class<?>> findClassesInJar(List<File> filejars)
    {
        Set<Class<?>> classes = new HashSet<>();

        for(File file : filejars)
        {
            try (JarFile jarFile = new JarFile(file))
            {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});

                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements())
                {
                    JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory() && entry.getName().endsWith(".class"))
                    {
                        String className = entry.getName().replace("/", ".").replace(".class", "");

                        Class<?> cls = Class.forName(className, true, classLoader);
                        classes.add(cls);
                    }
                }

            } catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }

        return  classes;
    }

    private List<File> findFileJars(String pathFile) throws FileNotFoundException
    {
        File fileJar = new File(pathFile);
        if (!fileJar.exists())
            throw  new FileNotFoundException();

        if(!fileJar.isDirectory())
            throw  new IllegalArgumentException();

        File[] fileList = Optional.of(fileJar).map(File::listFiles)
                .orElse(new File[] {});

        return Arrays.asList(fileList).stream()
                .filter(i -> i.getPath().endsWith(validFileExtension))
                .collect(Collectors.toList());
    }

    private URL[] getUrls(List<File> filejars) throws MalformedURLException
    {
        URL[] urlsList = new URL[filejars.size()];
        for (int i = 0; i < filejars.size(); i++) {
            urlsList[i] = filejars.get(i).toURI().toURL();
        }
        return urlsList;
    }

}
