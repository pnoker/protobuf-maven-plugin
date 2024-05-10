/*
 * Copyright 2016-present the IoT DC3 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pnoker.maven.plugin.protobuf;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;

/**
 * This mojo executes the {@code protoc} compiler for generating main python sources
 * from protocol buffer definitions. It also searches dependency artifacts for
 * {@code .proto} files and includes them in the {@code proto_path} so that they can be
 * referenced. Finally, it adds the {@code .proto} files to the project as resources so
 * that they are included in the final artifact.
 *
 * @since 0.3.3
 */
@Mojo(
        name = "compile-python",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        threadSafe = true
)
public final class ProtocCompilePythonMojo extends AbstractProtocCompileMojo {

    /**
     * This is the directory into which the {@code .py} will be created.
     */
    @Parameter(
            required = true,
            property = "pythonOutputDirectory",
            defaultValue = "${project.build.directory}/generated-sources/protobuf/python"
    )
    private File outputDirectory;

    @Override
    protected void addProtocBuilderParameters(final Protoc.Builder protocBuilder) {
        super.addProtocBuilderParameters(protocBuilder);
        protocBuilder.setPythonOutputDirectory(getOutputDirectory());
    }

    @Override
    protected File getOutputDirectory() {
        return outputDirectory;
    }
}
