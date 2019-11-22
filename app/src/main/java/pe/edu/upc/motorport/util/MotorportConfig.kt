package pe.edu.upc.motorport.util

import pe.edu.upc.motorport.R

class MotorportConfig {
    companion object {
        const val API_V1_BASE_URL = "https://motorport-core-api.azurewebsites.net/api/v1/"
        const val SHARED_PREFERENCES_NAME = "SP_MOTORPORT"
        const val SHARED_PREFERENCES_FIELD_TOKEN = "TOKEN"
        fun getResourceForFavoriteImageButton(isFavorite: Boolean): Int{
            return when(isFavorite){
                true -> R.drawable.ic_favorite_24px
                else -> R.drawable.ic_favorite_border_24px
            }
        }
    }
}