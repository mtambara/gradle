/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.buildinit.plugins.internal;

import org.gradle.api.internal.file.FileResolver;
import org.gradle.buildinit.plugins.internal.modifiers.BuildInitDsl;
import org.gradle.buildinit.plugins.internal.modifiers.BuildInitTestFramework;

import java.util.Collections;
import java.util.Set;

public class KotlinLibraryProjectInitDescriptor extends LanguageLibraryProjectInitDescriptor {
    public KotlinLibraryProjectInitDescriptor(TemplateOperationFactory templateOperationFactory, FileResolver fileResolver, DefaultTemplateLibraryVersionProvider versionProvider) {
        super("kotlin", templateOperationFactory, fileResolver, versionProvider);
    }

    @Override
    public BuildInitTestFramework getDefaultTestFramework() {
        return BuildInitTestFramework.KOTLIN;
    }

    @Override
    public Set<BuildInitTestFramework> getTestFrameworks() {
        return Collections.singleton(BuildInitTestFramework.KOTLIN);
    }

    @Override
    public void generate(BuildInitDsl dsl, BuildInitTestFramework testFramework) {
        String kotlinVersion = libraryVersionProvider.getVersion("kotlin");
        BuildScriptBuilder buildScriptBuilder = new BuildScriptBuilder(dsl, fileResolver, "build")
            .fileComment("This generated file contains a sample Kotlin library project to get you started.")
            .plugin("Apply the Kotlin JVM plugin to add support for Kotlin on the JVM", "org.jetbrains.kotlin.jvm", kotlinVersion)
            .compileDependency("Use the Kotlin standard library", "org.jetbrains.kotlin:kotlin-stdlib")
            .compileDependency("Use the Kotlin JDK 8 standard library", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            .testCompileDependency("Use the Kotlin test library", "org.jetbrains.kotlin:kotlin-test")
            .testCompileDependency("Use the Kotlin JUnit integration", "org.jetbrains.kotlin:kotlin-test-junit");

        buildScriptBuilder.create().generate();

        TemplateOperation kotlinSourceTemplate = fromClazzTemplate("kotlinlibrary/Library.kt.template", "main");
        TemplateOperation kotlinTestTemplate = fromClazzTemplate("kotlinlibrary/LibraryTest.kt.template", "test");
        whenNoSourcesAvailable(kotlinSourceTemplate, kotlinTestTemplate).generate();
    }
}
