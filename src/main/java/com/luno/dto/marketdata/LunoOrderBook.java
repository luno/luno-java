package com.luno.dto.marketdata;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoOrderBook {

    public final long timestamp;
    private final TreeMap<BigDecimal, BigDecimal> bids;
    private final TreeMap<BigDecimal, BigDecimal> asks;
    

    public LunoOrderBook(@JsonProperty(value="timestamp", required=true) long timestamp
            , @JsonProperty(value="asks") Order[] asks
            , @JsonProperty(value="bids") Order[] bids) {
        this.timestamp = timestamp;
        // we merge the orders with the same price together bei adding the volumes
        this.asks = Stream.of(asks).collect(Collectors.toMap(o -> o.price, o -> o.volume, (v1, v2) -> v1.add(v2), () -> new TreeMap<BigDecimal, BigDecimal>()));
        this.bids = Stream.of(bids).collect(Collectors.toMap(o -> o.price, o -> o.volume, (v1, v2) -> v1.add(v2), () -> new TreeMap<BigDecimal, BigDecimal>((k1, k2) -> -k1.compareTo(k2))));
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault());
    }

    public Map<BigDecimal, BigDecimal> getBids() {
        return Collections.unmodifiableMap(bids);
    }

    public Map<BigDecimal, BigDecimal> getAsks() {
        return Collections.unmodifiableMap(asks);
    }
    
    @Override
    public String toString() {
        return "LunoOrderBook [timestamp=" + getTimestamp() + ", bids=" + bids + ", asks=" + asks + "]";
    }

    public static class Order {
        public final BigDecimal price;
        public final BigDecimal volume;
        public Order(@JsonProperty(value="price", required=true) BigDecimal price, @JsonProperty(value="volume", required=true) BigDecimal volume) {
            this.price = price;
            this.volume = volume;
        }
    }
}
