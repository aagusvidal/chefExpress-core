package finders;

import interfaces.RecipeScorer;

import java.io.File;
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
    private static final String extentionJars = ".jar";


    public RecipeScorerFinder(String url)
    {
        this.directory = url;
    }

    public Set<RecipeScorer> find()
    {
        HashSet<RecipeScorer> scorers = new HashSet<>();

        List<File> filejars = findFileJars(this.directory);
        if (filejars.isEmpty())
            return null;

        try{
            URL[] urlsList = getUrls(filejars);
            URLClassLoader ClassesLoader = new URLClassLoader(urlsList);
            Set<Class<?>> classes = findClassesInJar(filejars);
            for(Class<?> element : classes)
            {
                if (RecipeScorer.class.isAssignableFrom(element))
                {
                    scorers.add((RecipeScorer) element.getDeclaredConstructor().newInstance() );
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return scorers;

    }

    private Set<Class<?>> findClassesInJar(List<File> filejars)
    {
        Set<Class<?>> classes = new HashSet<>();

        for(File file : filejars) {
            try (JarFile jarFile = new JarFile(file)) {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});

                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace("/", ".").replace(".class", "");

                        Class<?> cls = Class.forName(className, true, classLoader);
                        classes.add(cls);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        return  classes;
    }

    private List<File> findFileJars(String pathFile) {
        File fileJar = new File(pathFile);
        File[] fileList = Optional.of(fileJar).map(File::listFiles)
                .orElse(new File[] {});
        return Arrays.asList(fileList).stream()
                .filter(i -> i.getPath().endsWith(extentionJars))
                .collect(Collectors.toList());
    }

    private URL[] getUrls(List<File> filejars) throws MalformedURLException {
        URL[] urlsList = new URL[filejars.size()];
        for (int i = 0; i < filejars.size(); i++) {
            urlsList[i] = filejars.get(i).toURI().toURL();
        }
        return urlsList;
    }

}
