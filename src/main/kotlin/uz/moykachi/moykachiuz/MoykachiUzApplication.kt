package uz.moykachi.moykachiuz

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["uz.moykachi.moykachiuz.repository"])
@EntityScan(basePackages = ["uz.moykachi.moykachiuz.models"])
@EnableTransactionManagement
class MoykachiApplication {
    @Bean
    fun modelMapper() = ModelMapper().apply { this.configuration.setMatchingStrategy(MatchingStrategies.STRICT) }
}

fun main(args: Array<String>) {
    runApplication<MoykachiApplication>(*args)
}
