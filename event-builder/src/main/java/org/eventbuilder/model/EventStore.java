package org.eventbuilder.model;

import com.google.gson.JsonObject;

public interface EventStore {
    void saveEvent(String topicName, JsonObject event);
}
