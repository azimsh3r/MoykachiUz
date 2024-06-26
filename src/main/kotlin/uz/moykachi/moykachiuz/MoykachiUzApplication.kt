package uz.moykachi.moykachiuz

import com.zaxxer.hikari.util.DriverDataSource
import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@EnableJpaRepositories(basePackages = ["uz.moykachi.moykachiuz.repository"])
@SpringBootApplication
@EntityScan(basePackages = ["uz.moykachi.moykachiuz.models"])
@EnableTransactionManagement
class MoykachiApplication {
    @Bean
    fun modelMapper() = ModelMapper()
}

fun main(args: Array<String>) {
    runApplication<MoykachiApplication>(*args)
}
