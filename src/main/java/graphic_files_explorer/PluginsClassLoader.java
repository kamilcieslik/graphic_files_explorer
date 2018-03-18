package graphic_files_explorer;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PluginsClassLoader extends ClassLoader {
    private String classesDirectory;
    private List<String> pluginClassNames;
    private List<Class> pluginClasses;

    public PluginsClassLoader(String directory) throws IOException, ClassNotFoundException {
        classesDirectory = directory;
        pluginClasses = new ArrayList<>();
        pluginClassNames = new ArrayList<>();
        setPluginClassesFileNames();
        loadPluginClasses();
    }

    public List<String> getPluginClassNames() {
        return pluginClassNames;
    }

    public Class getPluginClass(String pluginClassName) {
        for (Class pluginClass : pluginClasses) {
            if (pluginClass.getName().equals(classesDirectory + "." + pluginClassName))
                return pluginClass;
        }
        return null;
    }

    public Object getPluginClassObject(String pluginClassName) throws IllegalAccessException, InstantiationException {
        return getPluginClass(pluginClassName).newInstance();
    }

    public Image invokeImageTransformMethod(String pluginClassName, String methodName, Image inputImage)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class pluginClass = getPluginClass(pluginClassName);
        Method method = pluginClass.getMethod(methodName, Image.class);
        return (Image) method.invoke(pluginClass.newInstance(), inputImage);
    }

    private void setPluginClassesFileNames() throws IOException {
        try (InputStream in = getResourceAsStream(classesDirectory);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
                if (resource.contains(".class")) {
                    resource = resource.replaceAll(".class", "");
                    pluginClassNames.add(resource);
                }
            }
        }
    }

    private void loadPluginClasses() throws ClassNotFoundException {
        for (String pluginClassName : pluginClassNames) {
            pluginClasses.add(loadClass(classesDirectory + "." + pluginClassName));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
