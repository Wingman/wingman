package com.wingman.client.classloader;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.api.transformer.Transformers;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;

/**
 * {@link TransformingClassLoader} enables support for injection by {@link Transformer}s. <br>
 *
 * It only transforms classes residing in the {@link com.wingman} package and its subpackages plus classes owned by the RuneScape gamepack.
 */
public class TransformingClassLoader extends URLClassLoader {

    public TransformingClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.contains(".")) {
            throw new ClassNotFoundException();
        }

        // Do not transform self
        if (name.equals(TransformingClassLoader.class.getName())) {
            throw new ClassNotFoundException();
        }

        byte[] classCode;
        String path = name.replace('.', '/').concat(".class");
        try (InputStream classStream = getResourceAsStream(path)) {
            classCode = ByteStreams.toByteArray(classStream);
        } catch (IOException | NullPointerException e) {
            throw new ClassNotFoundException(null, e);
        }

        LinkedList<Transformer> transformers = new LinkedList<>();
        for (Transformer transformer : Transformers.TRANSFORMERS) {
            try {
                if (transformer.canTransform(name)) {
                    transformers.add(transformer);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (transformers.isEmpty()) {
            if (path.contains("/")) {
                return super.findClass(name);
            } else {
                return defineClass(name, classCode, 0, classCode.length);
            }
        }

        ClassNode classNode = new ClassNode(Opcodes.ASM5);

        ClassReader reader = new ClassReader(classCode);
        reader.accept(classNode, ClassReader.SKIP_DEBUG);

        if (!name.contains(".")) {
            classNode.access = classNode.access | Opcodes.ACC_PUBLIC;
        }

        for (Transformer transformer : transformers) {
            try {
                classNode = transformer.transform(classNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ClassWriter classWriter = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS) {
            @Override
            protected String getCommonSuperClass(String type1, String type2) {
                return "java/lang/Object";
            }
        };
        classNode.accept(classWriter);

        byte[] transformed = classWriter.toByteArray();
        return defineClass(name, transformed, 0, transformed.length);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {

            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    c = findClass(name);
                } catch (Exception e) {
                    try {
                        c = super.loadClass(name, resolve);
                    } catch (Exception e2) {
                        try {
                            c = getSystemClassLoader().loadClass(name);
                        } catch (Exception e3) {
                            ClassNotFoundException exception = new ClassNotFoundException("Class not found", e3);
                            exception.addSuppressed(e);
                            exception.addSuppressed(e2);
                            throw exception;
                        }
                    }
                }
            }

            if (resolve) {
                resolveClass(c);
            }

            return c;
        }
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    static {
        registerAsParallelCapable();
    }
}

