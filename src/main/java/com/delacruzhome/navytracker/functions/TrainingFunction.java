package com.delacruzhome.navytracker.functions;

import java.util.*;

import com.delacruzhome.navytracker.factories.TrainingFactoryImpl;
import com.microsoft.azure.functions.annotation.*;
import com.delacruzhome.navytracker.models.Training;
import com.delacruzhome.navytracker.repositories.IRepository;
import com.delacruzhome.navytracker.repositories.TrainingRepositoryImpl;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import org.bson.types.ObjectId;

import java.util.logging.Logger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class TrainingFunction extends BaseFunction {
    public TrainingFunction() {
        super(TrainingRepositoryImpl.initialize());
    }

    @FunctionName("listTrainings")
    public HttpResponseMessage getAll(
        @HttpTrigger(
            name = "req", 
            methods = { HttpMethod.GET }, 
            authLevel = AuthorizationLevel.ANONYMOUS,
            route = "trainings"
        ) HttpRequestMessage request,
        final ExecutionContext context) {
        
        Logger log = context.getLogger();
        log.info("Java HTTP trigger processed a request.");

        List<Training> trainingList = trainingRepository.getAll();
        return request.createResponseBuilder(HttpStatus.OK).body(trainingList).build();
    }

    @FunctionName("addTrainings")
    public HttpResponseMessage add(
        @HttpTrigger(
            name = "req",
            methods = { HttpMethod.POST },
            authLevel = AuthorizationLevel.ANONYMOUS,
            route = "trainings"
        ) HttpRequestMessage<Optional<Training>> request,
        final ExecutionContext context
    ) {
        try {
            Training training = request.getBody().orElse(null);
            if (training == null) {
                throw new NullPointerException("There was nothing to save or the provided data is not a valid training object");
            }

            training = trainingRepository.add(training);
            return request.createResponseBuilder(HttpStatus.CREATED)
                    .body(training).build();
        }
        catch(NullPointerException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage())
                    .build();
        }
    }

    @FunctionName("updateTrainings")
    public HttpResponseMessage update(
            @HttpTrigger(
                    name = "req",
                    methods = { HttpMethod.PUT },
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "trainings/{id}"
            ) HttpRequestMessage<Optional<Training>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        Logger log = context.getLogger();
        log.info("Java HTTP trigger processed a request." + id);

        try {
            Training training = request.getBody().orElse(null);
            training.setId(id);
            trainingRepository.update(training);
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(training)
                    .build();
        }
        catch (IllegalArgumentException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage())
                    .build();
        }
    }

    @FunctionName("deleteTrainings")
    public HttpResponseMessage delete(
            @HttpTrigger(
                    name = "req",
                    methods = { HttpMethod.DELETE },
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "trainings/{id}"
            ) HttpRequestMessage<Optional<Training>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        Logger log = context.getLogger();
        log.info("Java HTTP trigger processed a request." + id);

        ObjectId objectId = new ObjectId(id);
        trainingRepository.delete(objectId);
        return request.createResponseBuilder(HttpStatus.NO_CONTENT)
                .build();
    }

    @FunctionName("listTrainingsById")
    public HttpResponseMessage getById(
            @HttpTrigger(
                    name = "req",
                    methods = { HttpMethod.GET },
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "trainings/{id}")
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {

        Logger log = context.getLogger();
        log.info("Java HTTP trigger processed a request." + id);
        try {
            Training training = trainingRepository.findById(new ObjectId(id));
            if (training == null) {
                throw new IllegalArgumentException("Could not find anything with id: " + id);
            }

            return request.createResponseBuilder(HttpStatus.OK)
                    .body(training)
                    .header("content-type", "application/json")
                    .build();
        }
        catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage())
                    .build();
        }
    }
}
