import org.apache.log4j.BasicConfigurator

fun main() {
    BasicConfigurator.configure()
    SparkApplication().start()
}