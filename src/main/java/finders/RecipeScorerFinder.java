package finders;

import interfaces.RecipeScorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        File file = new File(this.directory);

        if (!file.exists())
            throw  new FileNotFoundException();

        if(!file.isDirectory())
            throw  new IllegalArgumentException();

        List<File> jars = getJars(file);

        try
        {
            Set<Class<?>> classes = findClassesInJar(jars);

            for(Class<?> aClass : classes)
            {
                if (RecipeScorer.class.isAssignableFrom(aClass))
                    scorers.put(aClass.getName(), (RecipeScorer) aClass.getDeclaredConstructor().newInstance() );
            }
        }
        catch ( Exception e) { throw new RuntimeException(e); }

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
                        String[] filename_splited_bar = entry.getName().replace(".class", "").split("/");
                        String className = filename_splited_bar[filename_splited_bar.length-1];

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

    private List<File> getJars(File target)
    {
        File[] fileList = Optional.of(target).map(File::listFiles)
                .orElse(new File[] {});

        return Arrays.asList(fileList).stream()
                .filter(i -> i.getPath().endsWith(validFileExtension))
                .collect(Collectors.toList());
    }
}
