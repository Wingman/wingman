package com.wingman.client.api.transformer;

import org.objectweb.asm.tree.ClassNode;

public interface Transformer {

    boolean canTransform(String name);

    ClassNode transform(ClassNode clazz);

    boolean isUsed();
}
