package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.dto.stock.InfluxStockDto;
import com.example.stocksimulation.dto.stock.StockHistoryResponseDto;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockHistoryService {
    private final InfluxDBClient influxDBClient;

    @Async
    public void saveStockHistory(String code, long price) {
        InfluxStockDto history = new InfluxStockDto();
        history.setStockCode(code);
        history.setPrice(price);

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(WritePrecision.MS, history);
    }

    public List<StockHistoryResponseDto> getStockHistory(String stockCode) {
        String query = String.format(
                "from(bucket: \"stocks\")" +
                        "|> range(start: -30d)" +
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"stock_price\")" +
                        "|> filter(fn: (r) => r[\"stockCode\"]) == \"{\\r\\n \\\"stockCode\\\" : \\\"%s\\\"\\r\\n}\")" +
                        "|> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")",
                stockCode
        );

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query);
        List<StockHistoryResponseDto> stockPrices = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(record.getValue());
                StockHistoryResponseDto dto = new StockHistoryResponseDto(stockCode, (double) record.getValueByKey("price"), record.getTime());

                stockPrices.add(dto);
            }
        }

        return stockPrices;
    }
}
