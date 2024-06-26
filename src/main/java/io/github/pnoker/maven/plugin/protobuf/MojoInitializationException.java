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

/**
 * An exception to indicate that plugin initialization has failed.
 *
 * @since 0.6.0
 */
public final class MojoInitializationException extends RuntimeException {

    private static final long serialVersionUID = 821723854984670341L;

    public MojoInitializationException(final String message) {
        super(message);
    }

    public MojoInitializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
