data class CheckTokenResponse(val message: String)

data class BadRequestResponse(val message: String, val cause: String? = "bad_request")