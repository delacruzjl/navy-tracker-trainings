package com.delacruzhome.navytracker;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.delacruzhome.navytracker.models.Training;
import com.delacruzhome.navytracker.repositories.IRepository;
import com.delacruzhome.navytracker.repositories.TrainingRepositoryImpl;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import java.util.logging.Logger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Trainings {
    private IRepository<Training> trainingRepository;

    public Trainings() {
        this(TrainingRepositoryImpl.initialize());
    }

    public Trainings(IRepository<Training> trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @FunctionName("listtrainings")
    public HttpResponseMessage getAll(
        @HttpTrigger(
            name = "req", 
            methods = { HttpMethod.GET }, 
            authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context) {
        
            Logger log = context.getLogger();
        log.info("Java HTTP trigger processed a request.");

        List<Training> trainingList = trainingRepository.getAll();
        return request.createResponseBuilder(HttpStatus.OK).body(trainingList).build();
    }

    @FunctionName("createtrainings")
    public HttpResponseMessage add(
        @HttpTrigger(
            name = "req",
            methods = { HttpMethod.POST },
            authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context
    ) {
        Gson gson = new Gson();
        Training training = gson.fromJson(request.getBody().orElse(""), Training.class);
        if (training == null){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
            .body("There was nothing to save or the provided data is not a valid training object")
            .build();
        }
        
        training = trainingRepository.add(training);
        return request.createResponseBuilder(HttpStatus.CREATED)
        .body(training).build();
    }
}
