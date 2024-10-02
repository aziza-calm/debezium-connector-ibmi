/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.db2as400;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import io.debezium.config.CommonConnectorConfig;
import io.debezium.connector.AbstractSourceInfoStructMaker;
import io.debezium.relational.TableId;

public class As400SourceInfoStructMaker extends AbstractSourceInfoStructMaker<SourceInfo> {

    private final Schema schema;

    public As400SourceInfoStructMaker(String connector, String version, CommonConnectorConfig connectorConfig) {
        init(connector, version, connectorConfig);
        schema = commonSchemaBuilder()
                .name("io.debezium.connector.db2as400.Source")

                // TODO add in table info
                .field(SourceInfo.SCHEMA_NAME_KEY, Schema.STRING_SCHEMA)
                .field(SourceInfo.TABLE_NAME_KEY, Schema.STRING_SCHEMA)
                .field(SourceInfo.EVENT_SEQUENCE, Schema.OPTIONAL_INT64_SCHEMA)
                .field(SourceInfo.JOURNAL_KEY, Schema.OPTIONAL_STRING_SCHEMA)
                .field(SourceInfo.JOURNAL_RECEIVER, Schema.OPTIONAL_STRING_SCHEMA)
                // TODO add in offset
                .build();
    }

    @Override
    public Schema schema() {
        return schema;
    }

    @Override
    public Struct struct(SourceInfo sourceInfo) {
        final TableId tableId = sourceInfo.getTableId();
        final Struct ret = super.commonStruct(sourceInfo)
                .put(SourceInfo.SCHEMA_NAME_KEY, tableId != null ? tableId.schema() : "")
                .put(SourceInfo.TABLE_NAME_KEY, tableId != null ? tableId.table() : "");

        if (sourceInfo.getEventSequence() != null) {
            ret.put(SourceInfo.EVENT_SEQUENCE, sourceInfo.getEventSequence());
        }

        if (sourceInfo.getJournal() != null) {
            ret.put(SourceInfo.JOURNAL_KEY, sourceInfo.getJournal());
        }

        if (sourceInfo.getJournalReceiver() != null) {
            ret.put(SourceInfo.JOURNAL_RECEIVER, sourceInfo.getJournalReceiver());
        }

        return ret;
    }
}
