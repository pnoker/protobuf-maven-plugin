/*
 * Copyright (c) 2018 Maven Protocol Buffers Plugin Authors. All rights reserved.
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

outputDirectory = new File(basedir, 'target/generated-sources/protobuf/js');
assert outputDirectory.exists();
assert outputDirectory.isDirectory();

generatedFile = new File(outputDirectory, 'test_pb.js');
assert generatedFile.exists();
assert generatedFile.isFile();

content = generatedFile.text;
assert content.contains('TestMessage');
assert content.contains('NestedMessage');

return true;
