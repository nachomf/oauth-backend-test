package configuration
import com.google.gson.Gson
import spark.ResponseTransformer

class JsonTransformer : ResponseTransformer {
    private val gson: Gson = Gson()
    override fun render(model: Any): String {
        return gson.toJson(model)
    }
}