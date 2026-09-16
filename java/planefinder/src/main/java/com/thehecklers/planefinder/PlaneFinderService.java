package com.thehecklers.planefinder;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlaneFinderService {
    private final PlaneRepository repo;
    private final FlightGenerator generator;
    private final URL acURL;
    private final JsonMapper jm;

    @SneakyThrows
    public PlaneFinderService(PlaneRepository repo, FlightGenerator generator, JsonMapper jm) {
        this.repo = repo;
        this.generator = generator;
        this.jm = jm;

        acURL = URI.create("http://192.168.5.139/ajax/aircraft").toURL();
    }

    public Iterable<Aircraft> getAircraft() {
        List<Aircraft> positions = new ArrayList<>();

        JsonNode aircraftNodes;
        try {
            aircraftNodes = jm.readTree(acURL.openStream())
                    .get("aircraft");

            aircraftNodes.iterator().forEachRemaining(node -> {
                try {
                    positions.add(jm.treeToValue(node, Aircraft.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("\n>>> IO Exception: " + e.getLocalizedMessage() +
                    ", generating and providing sample data.\n");
            return saveSamplePositions();
        }

        if (!positions.isEmpty()) {
            positions.forEach(System.out::println);

            repo.deleteAll();
            return repo.saveAll(positions);
        } else {
            System.out.println("\n>>> No positions to report, generating and providing sample data.\n");
            return saveSamplePositions();
        }
    }

    private Iterable<Aircraft> saveSamplePositions() {
        final Random rnd = new Random();

        repo.deleteAll();

        for (int i = 0; i < rnd.nextInt(10); i++) {
            repo.save(generator.generate());
        }

        return repo.findAll();
    }
}