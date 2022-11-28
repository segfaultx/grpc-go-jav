package com.example.grpcjavago.rest;

import com.example.grpcjavago.stocks.StockService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StocksRestController {

    StockService stockService;

    @PostMapping("{stock}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void getStockUpdatesFor(@PathVariable String stock) {
        stockService.getStockUpdates(stock);
    }
}
