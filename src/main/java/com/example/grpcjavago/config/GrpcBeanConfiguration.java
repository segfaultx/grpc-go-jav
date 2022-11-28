package com.example.grpcjavago.config;

import com.example.grpcjavago.grpc.StockServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcBeanConfiguration {

    @Bean("stockClient")
    StockServiceGrpc.StockServiceBlockingStub setupTestClient() {
        return StockServiceGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build());
    }
}
