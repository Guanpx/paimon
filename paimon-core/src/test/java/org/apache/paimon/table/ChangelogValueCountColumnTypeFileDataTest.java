/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.paimon.table;

import org.apache.paimon.CoreOptions;
import org.apache.paimon.file.WriteMode;
import org.apache.paimon.file.schema.SchemaManager;
import org.apache.paimon.file.schema.TableSchema;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Column type evolution for file data in changelog value count table. */
public class ChangelogValueCountColumnTypeFileDataTest extends ColumnTypeFileDataTestBase {

    @BeforeEach
    public void before() throws Exception {
        super.before();
        tableConfig.set(CoreOptions.WRITE_MODE, WriteMode.CHANGE_LOG);
    }

    @Override
    protected List<String> getPrimaryKeyNames() {
        return Collections.emptyList();
    }

    @Override
    protected FileStoreTable createFileStoreTable(Map<Long, TableSchema> tableSchemas) {
        SchemaManager schemaManager = new TestingSchemaManager(tablePath, tableSchemas);
        return new ChangelogValueCountFileStoreTable(
                fileIO, tablePath, schemaManager.latest().get()) {
            @Override
            protected SchemaManager schemaManager() {
                return schemaManager;
            }
        };
    }
}