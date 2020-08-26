package com.delacruzhome.navytracker.functions;

import com.delacruzhome.navytracker.models.Training;
import com.delacruzhome.navytracker.repositories.IRepository;

public abstract class BaseFunction {
    protected IRepository<Training> trainingRepository;

    public BaseFunction(IRepository<Training> trainingRepository) {
        this.trainingRepository = trainingRepository;
    }
}
