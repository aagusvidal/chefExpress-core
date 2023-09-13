package finders;

import interfaces.RecipeScorer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class RecipeScorerFinder {
    public String directory;

    public RecipeScorerFinder(String url) {
        this.directory = url;
    }
    public HashMap<String, RecipeScorer> findClasses() throws Exception{
        HashMap<String, RecipeScorer> result = new HashMap<String, RecipeScorer>();

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
            result.put(filenameWithoutDotClass, (RecipeScorer) cls.getDeclaredConstructor().newInstance());
        }
        return result;
    }
}
