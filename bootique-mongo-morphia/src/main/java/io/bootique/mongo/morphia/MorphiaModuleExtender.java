package io.bootique.mongo.morphia;

import io.bootique.ModuleExtender;
import io.bootique.di.Binder;
import io.bootique.di.SetBuilder;

public class MorphiaModuleExtender extends ModuleExtender<MorphiaModuleExtender> {

    private SetBuilder<String> packageNames;

    public MorphiaModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public MorphiaModuleExtender initAllExtensions() {
        contributePackageNames();
        return this;
    }

    public MorphiaModuleExtender mapPackage(String packageName) {
        contributePackageNames().addInstance(packageName);
        return this;
    }


    private SetBuilder<String> contributePackageNames() {
        if (packageNames == null) {
            packageNames = newSet(String.class, "morphiaPackageNames");
        }
        return packageNames;
    }
}
