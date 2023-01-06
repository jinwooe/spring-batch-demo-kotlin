package jade.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.function.Consumer

@Component
class JobCompletionNotificationListener(val jdbcTemplate: JdbcTemplate): JobExecutionListenerSupport() {
    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status === BatchStatus.COMPLETED) {

            val log: Logger = LoggerFactory.getLogger(JobCompletionNotificationListener::class.java)

            log.info("!!! JOB FINISHED! Time to verify the results")
            jdbcTemplate.query<Person>(
                "SELECT first_name, last_name FROM people"
            ) { rs: ResultSet, row: Int ->
                Person(
                    rs.getString(1),
                    rs.getString(2)
                )
            }.forEach(Consumer<Person> { person: Person -> log.info("Found <$person> in the database.") })
        }
    }
}
