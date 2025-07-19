package com.em.employeeservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import salary.SalaryRequest;
import salary.SalaryResponse;
import salary.SalaryServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SalaryServiceGrpcClient {
    private final SalaryServiceGrpc.SalaryServiceBlockingStub blockingStub;
    private static final Logger log = LoggerFactory.getLogger(SalaryServiceGrpcClient.class);
    public SalaryServiceGrpcClient(
            @Value("${salary.service.address:localhost}") String serverAdrress,
            @Value("${salary.service.grpc.port:9001}") int serverPort
    ) {

        log.info("Connecting to Salary Service GRPS service at {}:{}", serverAdrress,serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAdrress, serverPort).usePlaintext().build();

        blockingStub = SalaryServiceGrpc.newBlockingStub(channel);


    }

    public SalaryResponse createSalaryAccount(String employeeID, String name, String email) {
        SalaryRequest request = SalaryRequest.newBuilder().setEmployeeId(employeeID).setName(name).setEmail(email).build();

        SalaryResponse response = blockingStub.createSalaryAccount(request);
        log.info("Response received from salary service via Grpc {}", response);

        return response;
    }
}
