package controller

import BadRequestResponse
import CheckTokenResponse
import com.google.gson.Gson
import configuration.JsonTransformer
import service.LoginService
import spark.Filter
import spark.Spark.*

class ApiController {

    private val loginService = LoginService()

    fun initializeApi() {
        ping()
        checkToken()
        enableCORS()
    }

    private fun ping() = get("/ping") { _, _ -> "pong" }

    private fun checkToken() {
        post(
            "/check-token",
            "application/json",
            { req, res ->
                res.type("application/json")

                val idToken = Gson().fromJson(req.body(), Map::class.java)["idToken"]

                return@post if (idToken != null) {
                    res.status(200)
                    CheckTokenResponse(loginService.getToken(idToken.toString()))
                } else {
                    res.status(400)
                    BadRequestResponse("id token not found")
                }
            },
            JsonTransformer()
        )
    }

    private fun enableCORS() {
        options(
            "/*"
        ) { request, response ->
            val accessControlRequestHeaders: String = request
                .headers("Access-Control-Request-Headers")
            response.header(
                "Access-Control-Allow-Headers",
                accessControlRequestHeaders
            )
            val accessControlRequestMethod: String = request
                .headers("Access-Control-Request-Method")
            response.header(
                "Access-Control-Allow-Methods",
                accessControlRequestMethod
            )
            "OK"
        }

        val filter = Filter { _, response -> response.header("Access-Control-Allow-Origin", "*") }
        before(filter)
    }
}