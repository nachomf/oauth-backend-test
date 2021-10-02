import configuration.SERVER_PORT
import controller.ApiController
import spark.kotlin.port

class SparkApplication {
    fun start(){
        port(SERVER_PORT)
        ApiController().initializeApi()
    }
}