/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.db2as400;

import java.time.Instant;
import java.util.Arrays;

import io.debezium.annotation.NotThreadSafe;
import io.debezium.connector.common.BaseSourceInfo;
import io.debezium.relational.TableId;

/**
 * Coordinates from the database log to establish the relation between the change streamed and the source log position.
 * Maps to {@code source} field in {@code Envelope}.
 *
 * @author Jiri Pechanec
 *
 */
@NotThreadSafe
public class SourceInfo extends BaseSourceInfo {

    public static final String SNAPSHOT_KEY = "snapshot";
    public static final String JOURNAL_KEY = "journal";
    public static final String JOURNAL_RECEIVER = "journal_receiver";
    public static final String EVENT_SEQUENCE = "event_sequence";
    private Instant sourceTime;
    private String databaseName;
    private String journal;
    private String journalReceiver;
    private TableId tableId;
    private Long eventSequence;

    protected SourceInfo(As400ConnectorConfig connectorConfig) {
        super(connectorConfig);
        this.databaseName = connectorConfig.getDatabaseName();
        this.eventSequence = connectorConfig.getOffset().getOffset().longValue();
        this.journal = Arrays.toString(connectorConfig.getOffset().getJournal());
    }

    public void setSourceTime(Instant instant) {
        sourceTime = instant;
    }

    @Override
    protected Instant timestamp() {
        return sourceTime;
    }

    @Override
    protected String database() {
        return databaseName;
    }

    public TableId getTableId() {
        return tableId;
    }

    public Instant getSourceTime() {
        return sourceTime;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setTableId(TableId tableId) {
        this.tableId = tableId;
    }

    public Long getEventSequence() {
        return eventSequence;
    }

    public void setEventSequence(Long eventSequence) {
        this.eventSequence = eventSequence;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getJournalReceiver() {
        return journalReceiver;
    }

    public void setJournalReceiver(String journalReceiver) {
        this.journalReceiver = journalReceiver;
    }
}
