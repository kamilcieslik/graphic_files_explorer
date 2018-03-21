package graphic_files_explorer;

import javafx.scene.image.Image;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PluginsClassLoader extends ClassLoader {
    private String classesDirectory;
    private List<String> pluginClassNames;
    private List<Class> pluginClasses;

    public PluginsClassLoader(ClassLoader parent, String directory) throws IOException {
        super(parent);
        classesDirectory = directory + "\\";
        pluginClasses = new LinkedList<>();
        pluginClassNames = new ArrayList<>();
        setPluginClassesFileNames();
    }


    public List<String> getPluginClassNames() {
        return pluginClassNames;
    }

    public Class getPluginClass(String pluginClassName) {
        for (Class pluginClass : pluginClasses) {
            String[] subs = classesDirectory.split("\\\\");
            if (pluginClass.getName().equals(subs[subs.length - 1] + "." + pluginClassName))
                return pluginClass;
        }

        try {
            return loadPluginClass(pluginClassName);
        } catch (IOException e) {
            return null;
        }
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
        URL myUrl = new URL(classesDirectory);
        URLConnection connection = myUrl.openConnection();

        InputStream in = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String resource;
        while ((resource = br.readLine()) != null) {
            if (resource.contains("FxImageConverter"))
                continue;
            if (resource.contains(".class")) {
                resource = resource.replaceAll(".class", "");
                pluginClassNames.add(resource);
            }
        }
    }

    private Class loadPluginClass(String pluginClassName) throws IOException {
        URL directoryUrl = new URL(classesDirectory + pluginClassName + ".class");
        URLConnection urlConnection = directoryUrl.openConnection();
        InputStream input = urlConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int classData = input.read();

        while (classData != -1) {
            byteArrayOutputStream.write(classData);
            classData = input.read();
        }
        input.close();

        byte[] byteClassData = byteArrayOutputStream.toByteArray();
        String[] classPathComponents = classesDirectory.split("\\\\");
        Class pluginClass = defineClass(classPathComponents[classPathComponents.length - 1] + "." + pluginClassName,
                byteClassData, 0, byteClassData.length);
        resolveClass(pluginClass);
        pluginClasses.add(pluginClass);
        return pluginClass;
    }

    private void loadPluginClasses() throws IOException {
        for (String pluginClassName : pluginClassNames)
            loadPluginClass(pluginClassName);
    }

    public void unloadClasses() {
        pluginClasses = null;
        System.gc();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
