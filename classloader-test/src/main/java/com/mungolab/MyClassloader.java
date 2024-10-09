package com.mungolab;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vanja Komadinovic ( vanja@vast.com )
 */
public class MyClassloader extends ClassLoader {
    private ClassLoader parent;

    public MyClassloader(ClassLoader parent) {
        super(parent);
        
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("loading class '" + name + "'");
        if (name.startsWith("com.mungolab.classes.")) {
            System.out.println("loading our class '" + name + "'");
            return getClass(name);
        } else {
            return this.parent.loadClass(name);
        }
    }

    private Class<?> getClass(String name)
            throws ClassNotFoundException {
        // We are getting a name that looks like
        // javablogging.package.ClassToLoad
        // and we have to convert it into the .class file name
        // like javablogging/package/ClassToLoad.class
        String file = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassData(file);
            // defineClass is inherited from the ClassLoader class
            // and converts the byte array into a Class
            Class<?> c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] loadClassData(String name) throws IOException {
        // Opening the file
        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream(name);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        // Reading the binary data
        in.readFully(buff);
        in.close();
        return buff;
    }

}
