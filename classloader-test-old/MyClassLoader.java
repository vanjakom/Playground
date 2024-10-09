import java.net.URLClassLoader;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class MyClassLoader extends ClassLoader {
	private ClassLoader parent;

	public MyClassLoader(ClassLoader parent) {
		super(parent);

		this.parent = parent;
	}

	@Override
	protected  Class<?> findClass(String name) throws ClassNotFoundException {
		System.out.println(">>> findClass: " + name);
		
		if (name.contains(".")) {
			System.out.println(">>> using parent");
			return super.findClass(name);
		}

		try {
			Path path = Paths.get("/Users/vanja/projects/Playground/classloader-test/" + name + ".class");
			byte[] data = Files.readAllBytes(path);

			return defineClass(name, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
                System.out.println(">>> loadClass: " + name);

                return findClass(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                System.out.println(">>> loadClass with resolve: " + name);
                return findClass(name);
	}
 
}
