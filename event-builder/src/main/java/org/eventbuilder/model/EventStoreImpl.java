package org.eventbuilder.model;

import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;

public class EventStoreImpl implements EventStore {
    private String directory;

    public EventStoreImpl(String directory) {
        this.directory = directory;
    }

    @Override
    public void saveEvent(String topicName, JsonObject event) {
        System.out.println("Guardando evento en el topic: " + topicName);
        try (FileWriter file = new FileWriter(directory + "\\" + topicName + ".events", true)) {
            file.write(event.toString() + System.lineSeparator());
            System.out.println("Evento guardado: " + event.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
