package com.tinkoff.notificationtvserials.configuration

import com.tinkoff.notificationtvserials.service.SerialsProducer
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class Timer(private val serialsProducer: SerialsProducer) {


//    @Scheduled(cron = "1 * * * * ?")
    @Scheduled(cron = "* * 1 * * ?")
    fun produceSerial() {
        serialsProducer.produceSerials()
    }
}