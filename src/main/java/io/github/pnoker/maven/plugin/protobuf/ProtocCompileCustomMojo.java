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

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.toolchain.Toolchain;

import java.io.File;

/**
 * This mojo executes the {@code protoc} compiler with the specified plugin
 * executable to generate main sources from protocol buffer definitions.
 * It also searches dependency artifacts for {@code .proto} files and
 * includes them in the {@code proto_path} so that they can be referenced.
 * Finally, it adds the {@code .proto} files to the project as resources so
 * that they are included in the final artifact.
 *
 * @since 0.4.1
 */
@Mojo(
        name = "compile-custom",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        threadSafe = true
)
public final class ProtocCompileCustomMojo extends AbstractProtocCompileMojo {

    /**
     * A unique id that identifies the plugin to protoc.
     * <strong>Cannot</strong> be one of the built-in protoc plugins:
     * <ul>
     *     <li>java</li>
     *     <li>js</li>
     *     <li>csharp</li>
     *     <li>cpp</li>
     *     <li>python</li>
     *     <li>descriptor-set</li>
     * </ul>
     */
    @Parameter(
            required = true,
            property = "protocPluginId"
    )
    private String pluginId;

    /**
     * This is the base directory for the generated code.
     * If an explicit {@link #outputDirectory} parameter is not specified,
     * an output directory named after {@link #pluginId} will be created
     * inside this base directory.
     */
    @Parameter(
            required = true,
            readonly = true,
            defaultValue = "${project.build.directory}/generated-sources/protobuf"
    )
    private File outputBaseDirectory;

    /**
     * This is the directory where the generated code will be placed.
     * If this parameter is unspecified, then the default location is constructed as follows:<br>
     * <code>${project.build.directory}/generated-sources/protobuf/&lt;pluginId&gt;</code>
     */
    @Parameter(
            required = false,
            property = "protocPluginOutputDirectory"
    )
    private File outputDirectory;

    /**
     * An optional path to plugin executable.
     * If unspecified, alternative options must be used (e.g. toolchains).
     */
    @Parameter(
            required = false,
            property = "protocPluginExecutable"
    )
    private String pluginExecutable;

    /**
     * An optional parameter to be passed to the plugin.
     * <b>Cannot</b> contain colon (<tt>:</tt>) symbols.
     */
    @Parameter(
            required = false,
            property = "protocPluginParameter"
    )
    private String pluginParameter;

    /**
     * A name of an optional custom toolchain that can be used to locate the plugin executable.
     * The toolchain must be registered as a build extension and initialised properly.
     */
    @Parameter(
            required = false,
            property = "protocPluginToolchain"
    )
    private String pluginToolchain;

    /**
     * If {@link #pluginToolchain} is specified, this parameter specifies the tool in the toolchain,
     * which is to be resolved as plugin executable.
     */
    @Parameter(
            required = false,
            property = "protocPluginTool"
    )
    private String pluginTool;

    /**
     * Plugin artifact specification, in {@code groupId:artifactId:version[:type[:classifier]]} format.
     * When this parameter is set, the specified artifact will be resolved as a plugin executable.
     *
     * @since 0.4.1
     */
    @Parameter(
            required = false,
            property = "protocPluginArtifact"
    )
    private String pluginArtifact;

    @Override
    protected void addProtocBuilderParameters(final Protoc.Builder protocBuilder) {
        super.addProtocBuilderParameters(protocBuilder);

        protocBuilder.setNativePluginId(pluginId);
        if (pluginToolchain != null && pluginTool != null) {
            //get toolchain from context
            final Toolchain tc = toolchainManager.getToolchainFromBuildContext(pluginToolchain, session);
            if (tc != null) {
                getLog().info("Toolchain in protobuf-maven-plugin: " + tc);
                //when the executable to use is explicitly set by user in mojo's parameter, ignore toolchains.
                if (pluginExecutable != null) {
                    getLog().warn("Toolchains are ignored, 'pluginExecutable' parameter is set to " + pluginExecutable);
                } else {
                    //assign the path to executable from toolchains
                    pluginExecutable = tc.findTool(pluginTool);
                }
            }
        }
        if (pluginExecutable == null && pluginArtifact != null) {
            final Artifact artifact = createDependencyArtifact(pluginArtifact);
            final File file = resolveBinaryArtifact(artifact);
            pluginExecutable = file.getAbsolutePath();
        }
        if (pluginExecutable != null) {
            protocBuilder.setNativePluginExecutable(pluginExecutable);
        }
        if (pluginParameter != null) {
            protocBuilder.setNativePluginParameter(pluginParameter);
        }
        protocBuilder.setCustomOutputDirectory(getOutputDirectory());
    }

    @Override
    protected File getOutputDirectory() {
        File outputDirectory = this.outputDirectory;
        if (outputDirectory == null) {
            outputDirectory = new File(outputBaseDirectory, pluginId);
        }
        return outputDirectory;
    }
}
