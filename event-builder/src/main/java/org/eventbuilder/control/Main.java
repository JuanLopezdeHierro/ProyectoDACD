package org.eventbuilder.control;

import org.eventbuilder.model.EventStore;
import org.eventbuilder.model.EventStoreImpl;
import org.eventbuilder.model.Subscriber;
import org.eventbuilder.model.SubscriberImpl;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Asegurarse de que el directorio eventstore exista
        File directory = new File("C:\\EventStoreJuan");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Imprimir la ruta absoluta del directorio eventstore
        System.out.println("Directorio eventstore: " + directory.getAbsolutePath());

        // Inicializar el EventStore
        EventStore eventStore = new EventStoreImpl(directory.getAbsolutePath());

        // Inicializar el suscriptor y suscribirse al topic
        Subscriber subscriber = new SubscriberImpl("tcp://localhost:61616", eventStore);
        subscriber.subscribe("prediction-provider");

        System.out.println("Suscripción al topic 'prediction-provider' iniciada.");
    }
}
