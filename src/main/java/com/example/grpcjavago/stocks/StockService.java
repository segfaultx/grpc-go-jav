package com.example.grpcjavago.stocks;

import com.example.grpcjavago.grpc.StockServiceGrpc;
import com.example.grpcjavago.grpc.stockUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockService {

    StockServiceGrpc.StockServiceBlockingStub stockClient;

    RabbitTemplate rabbitTemplate;

    @Value("#{'${stocks.config}'}")
    @NonFinal
    List<String> configuredStocks;

    @PostConstruct
    public void initGetStockUpdates() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        configuredStocks.forEach(this::getStockUpdates);
    }

    public void getStockUpdates(String stockName) {
        log.info("Fetching stock info for configured stock: {}", stockName);
        var request = stockUpdateRequest.newBuilder().setName(stockName).build();
        CompletableFuture.runAsync(() ->
                stockClient.getStockUpdates(request)
                        .forEachRemaining((item) ->
                                rabbitTemplate.convertAndSend("stocks",
                                        new StocksResponseModel(item.getName(), item.getStockValue()))));
    }

    @Bean
    public Queue stocks() {
        return new Queue("stocks", true);
    }
}
