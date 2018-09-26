package me.yorick.poc.jmh;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.RunnerException;

import com.google.flatbuffers.FlatBufferBuilder;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import me.yorick.poc.protostuff.Order;


/**
 * 
 * Benchmark                          Mode  Cnt        Score        Error  Units
 * BenchMarker.BenchMark.flatbuffer  thrpt    6  1559122.771 ± 101027.103  ops/s
 * BenchMarker.BenchMark.protostuff  thrpt    6  3221609.031 ± 121705.721  ops/s
 * 
 * @author Yorick
 *
 */


public class BenchMarker {

    public static void main(String[] args) throws RunnerException, IOException {
        org.openjdk.jmh.Main.main(args);
    }

    
    @Fork(value = 2)
    @Measurement(iterations=3)
    @Warmup(batchSize=1, iterations=1)
    @BenchmarkMode(Mode.Throughput)
    public static class BenchMark {
        @Benchmark
        public void flatbuffer() {
            FlatBufferBuilder builder = new FlatBufferBuilder(0);
            int symbolOffset = builder.createString("ETH/BTC");
            int exchangeOffset = builder.createString("Binance");
            int offset = me.yorick.poc.flatbuffers.Order.createOrder(builder, me.yorick.poc.flatbuffers.Side.Ask, Math.random(), Math.random(), symbolOffset, exchangeOffset);

            builder.finish(offset);

            ByteBuffer buf = builder.dataBuffer();
            //byte[] bytes = buf.array();

            me.yorick.poc.flatbuffers.Order order = me.yorick.poc.flatbuffers.Order.getRootAsOrder(buf);
            order.exchange();
            order.symbol();
            order.price();
            order.qty();
            me.yorick.poc.flatbuffers.Side.name(order.side());
        }
        
        @Benchmark
        public void protostuff() {
            me.yorick.poc.protostuff.Order order = new me.yorick.poc.protostuff.Order(Math.random(), Math.random(),"ETH/BTC", "Binance", me.yorick.poc.protostuff.Side.Ask);
            io.protostuff.Schema<Order> schema = RuntimeSchema.getSchema(me.yorick.poc.protostuff.Order.class);
            
            LinkedBuffer buffer = LinkedBuffer.allocate(512);

            // ser
            final byte[] protostuff;
            try
            {
                protostuff = ProtostuffIOUtil.toByteArray(order, schema, buffer);
            }
            finally
            {
                buffer.clear();
            }
            me.yorick.poc.protostuff.Order order2 = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(protostuff, order2, schema);
            order2.getExchange();
            order2.getSymbol();
            order2.getPrice();
            order2.getQty();
            order2.getSide();
        }

    
    
    
    }

}
