package am.itspace.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class Publisher {


    public static void main(String[] args) throws MqttException {

        String messageString = "ITSpace is the best IT company!";


        MqttClient mqttClient = new MqttClient("tcp://mrjfgwkeg17pb.messaging.solace.cloud:20614", "HelloWorldSub");
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setUserName("solace-cloud-client");
        connectionOptions.setPassword("a2r03n0va50414ijl9fair8f9l".toCharArray());

        System.out.println("Connecting to Solace messaging at mrjfgwkeg17pb.messaging.solace.cloud:20614");
        mqttClient.connect(connectionOptions);
        System.out.println("Connected");

        MqttMessage message = new MqttMessage();
        message.setQos(0);
        message.setPayload(messageString.getBytes());
        mqttClient.publish("ITSpace", message);

        System.out.println("\tMessage '" + messageString + "' to 'ITSpace'");

        mqttClient.disconnect();

        System.out.println("Message published. Exiting");

        System.exit(0);

    }


}
