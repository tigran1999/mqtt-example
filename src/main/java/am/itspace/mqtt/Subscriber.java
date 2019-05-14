package am.itspace.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

public class Subscriber {

    public static void main(String[] args) throws MqttException {
        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("== START SUBSCRIBER ==");
        MqttClient mqttClient = new MqttClient("tcp://mrjfgwkeg17pb.messaging.solace.cloud:20614", "HelloWorldSub");
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setUserName("solace-cloud-client");
        connectionOptions.setPassword("a2r03n0va50414ijl9fair8f9l".toCharArray());

        System.out.println("Connecting to Solace messaging at mrjfgwkeg17pb.messaging.solace.cloud:20614");
        mqttClient.connect(connectionOptions);
        System.out.println("Connected");

        mqttClient.setCallback(new MqttCallback() {

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Called when a message arrives from the server that
                // matches any subscription made by the client
                String time = new Timestamp(System.currentTimeMillis()).toString();
                System.out.println("\nReceived a Message!" +
                        "\n\tTime:    " + time +
                        "\n\tTopic:   " + topic +
                        "\n\tMessage: " + new String(message.getPayload()) +
                        "\n\tQoS:     " + message.getQos() + "\n");
                latch.countDown(); // unblock main thread
            }

            public void connectionLost(Throwable cause) {
                System.out.println("Connection to Solace messaging lost!" + cause.getMessage());
                latch.countDown();
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });

        // Subscribe client to the topic filter and a QoS level of 0
        System.out.println("Subscribing client to topic: ITSpace");
        mqttClient.subscribe("ITSpace", 0);
        System.out.println("Subscribed");

        // Wait for the message to be received
        try {
            latch.await(); // block here until message received, and latch will flip
        } catch (InterruptedException e) {
            System.out.println("I was awoken while waiting");
        }

        // Disconnect the client
        mqttClient.disconnect();
        System.out.println("Exiting");

        System.exit(0);

    }

}
