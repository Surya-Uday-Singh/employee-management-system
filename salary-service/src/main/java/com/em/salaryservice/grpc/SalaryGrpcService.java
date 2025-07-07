package com.em.salaryservice.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salary.SalaryResponse;
import salary.SalaryServiceGrpc.SalaryServiceImplBase;

@GrpcService
public class SalaryGrpcService extends SalaryServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(SalaryGrpcService.class);

    @Override
    public void createSalaryAccount(salary.SalaryRequest salaryRequest,
                                    StreamObserver<salary.SalaryResponse> responseObserver) {

        log.info("CreateSalaryAccount request received {}", salaryRequest.toString());


        // Business logic- save to db,calculations etc

        SalaryResponse response = SalaryResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
