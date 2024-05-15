package com.example;

import com.google.cloud.compute.v1.InstancesClient;
import com.google.cloud.compute.v1.Operation;
import com.google.cloud.compute.v1.ProjectZoneInstanceName;
import com.google.cloud.compute.v1.StartInstanceRequest;
import com.google.cloud.compute.v1.StopInstanceRequest;

import java.io.IOException;

public class ComputeEngineController {

    public static void main(String[] args) {
        String projectId = "gcp202401";
        String zone = "europe-west2-a";
        String instanceName = "inst01";

        try {
            startInstance(projectId, zone, instanceName);
            // Add a delay or logic to determine when to stop the instance
            Thread.sleep(5000); // Example delay of 5 seconds
            stopInstance(projectId, zone, instanceName);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to start or stop the instance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void startInstance(String projectId, String zone, String instanceName) throws IOException {
        try (InstancesClient instancesClient = InstancesClient.create()) {
            ProjectZoneInstanceName instance = ProjectZoneInstanceName.of(instanceName, projectId, zone);

            StartInstanceRequest startRequest = StartInstanceRequest.newBuilder()
                    .setInstance(instance.toString())
                    .setProject(projectId)
                    .setZone(zone)
                    .build();

            Operation response = instancesClient.start(startRequest);
            System.out.println("Starting instance: " + instanceName);
            if (response.hasError()) {
                System.err.println("Failed to start instance: " + response.getError());
            } else {
                System.out.println("Instance started successfully: " + instanceName);
            }
        }
    }

    public static void stopInstance(String projectId, String zone, String instanceName) throws IOException {
        try (InstancesClient instancesClient = InstancesClient.create()) {
            ProjectZoneInstanceName instance = ProjectZoneInstanceName.of(instanceName, projectId, zone);

            StopInstanceRequest stopRequest = StopInstanceRequest.newBuilder()
                    .setInstance(instance.toString())
                    .setProject(projectId)
                    .setZone(zone)
                    .build();

            Operation response = instancesClient.stop(stopRequest);
            System.out.println("Stopping instance: " + instanceName);
            if (response.hasError()) {
                System.err.println("Failed to stop instance: " + response.getError());
            } else {
                System.out.println("Instance stopped successfully: " + instanceName);
            }
        }
    }
}
