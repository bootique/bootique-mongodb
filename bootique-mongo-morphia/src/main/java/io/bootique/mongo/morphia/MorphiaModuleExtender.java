/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
            packageNames = newSet(String.class, MorphiaModule.MORPHIA_PACKAGE_NAMES_DI_NAME);
        }
        return packageNames;
    }
}
