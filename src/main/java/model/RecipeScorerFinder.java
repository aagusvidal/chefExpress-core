package model;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.io.IOException;

public class RecipeScorerFinder {
    public String directory;

    public RecipeScorerFinder(String url) {
        this.directory = url;
    }
    HashSet<RecipeScorer> findClasses(String path) throws Exception{
        HashSet<RecipeScorer> result = new HashSet<>();

        if(new File(this.directory).isFile()) throw new IllegalArgumentException();

        if(this.directory == null || new File(this.directory).listFiles() == null) throw new IOException();

        for (File f : new File(this.directory).listFiles()) {
            String filename = f.getName();
            if (!filename.endsWith(".class")) continue;

            String[] filename_splited_bar = filename.replace(".class", "").split("/");
            String filenameWithoutDotClass = filename_splited_bar[filename_splited_bar.length-1];
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { f.toURI().toURL() });
            Class<?> cls = Class.forName(filenameWithoutDotClass, true, classLoader);
            if (!RecipeScorer.class.isAssignableFrom(cls)) continue;
            result.add((RecipeScorer) cls.getDeclaredConstructor().newInstance());
        }
        return result;
    }
}
