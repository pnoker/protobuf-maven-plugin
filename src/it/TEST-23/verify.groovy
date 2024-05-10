/*
 * Copyright 2016-present the IoT DC3 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

generatedJavaFile = new File(basedir, 'target/classes/a/AaaProtos.class');
assert generatedJavaFile.exists();
assert generatedJavaFile.isFile();

attachedResource = new File(basedir, 'target/classes/a/aaa.proto');
assert !attachedResource.exists();

generatedJavaTestFile = new File(basedir, 'target/test-classes/b/BbbProtos.class');
assert generatedJavaTestFile.exists();
assert generatedJavaTestFile.isFile();

attachedTestResource = new File(basedir, 'target/test-classes/b/bbb.proto');
assert !attachedTestResource.exists();

return true;
