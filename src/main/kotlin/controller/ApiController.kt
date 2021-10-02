package controller

import GetTokenResponse
import service.LoginService
import configuration.JsonTransformer
import spark.Spark.get

class ApiController {

    private val loginService = LoginService()

    fun initializeApi() {
        ping()
        getToken()
    }

    private fun ping() = get("/ping") { _, _ -> "pong" }

    private fun getToken() {
        get(
            "/get-token/:grants",
            "application/json",
            { req, res ->
                res.apply {
                    status(200)
                    type("application/json")
                }.let {
                    GetTokenResponse(loginService.getToken(req.params(":grants")))
                }
            },
            JsonTransformer()
        )
    }
}