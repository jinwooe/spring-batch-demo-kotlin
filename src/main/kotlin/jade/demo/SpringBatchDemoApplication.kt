package jade.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBatchDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchDemoApplication>(*args)
}
