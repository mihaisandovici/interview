package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

import com.squareup.moshi.Json

class LoginResponse(
        @Json(name = "Message") val message: String,
        @Json(name = "User role") val role: String
) {
}