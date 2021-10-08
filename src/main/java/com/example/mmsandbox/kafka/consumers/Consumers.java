package com.example.mmsandbox.kafka.consumers;

import com.example.mmsandbox.domain.TestPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class Consumers {

    String siteName;

    @Bean
    public Consumer<KStream<String, TestPayload>> tanalystInvoiceConsumer() {
        return c -> c.foreach((key, tAnalystPayload) -> {
            log.debug("kafka tanalystinvoiceconsumer consumer called with key " + key);
            if (siteName.equals(key)) {
                log.info(String.format("Current invoice: key %s \t GBA %s storagePath %s", key, tAnalystPayload.getGba(),tAnalystPayload.getStoragePath()));
                if (tAnalystPayload.getStoragePath() != null) {
                    try {
                        log.info("processing task");
                    } catch (Exception e) {
                        log.warn("Skipped processing TAnalystPayload message "+ tAnalystPayload.getId()  + " " + tAnalystPayload.getGba());
                    }
                }
            } else {
                log.warn(siteName + " kafka tanalystinvoiceconsumer discarding message with key " + key);
            }
        });
    }


    /**
     * refer to application-kafka.yml on how this consumer is wired to the topic
     * @return
     */
    @Bean
    public Consumer<KStream<String, TestPayload>> rerunCompletedConsumer() {
        return c -> c.foreach((key, rerunStatus) -> {
            log.debug("kafka reruncompleted consumer called with key " + key);
            if (siteName.equals(key)) {
                log.info(String.format("RerunStatus received : key %s \t batchId %s \t period %s", key, rerunStatus.getBatchId(), rerunStatus.getPeriod()));
                if (rerunStatus.getBatchId() != null) {
                    try {
                        // post message to update the rerun folder
                        log.info("processing task");
                        log.info("updateRerunRequest batchid " + rerunStatus.getBatchId() + " updateStatus return ");
                    } catch (Exception e) {
                        log.warn(siteName + " skipped processing RerunStatus batchId " + rerunStatus.getBatchId() + " period " + rerunStatus.getPeriod());
                    }
                }
            } else {
                log.warn(siteName + " kafka reruncompleted discarding message with key " + key);
            }
        });
    }

}
