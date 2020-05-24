package ibmro.datanetworksource.repositories.entities.databaseModels.responseModel

data class LearnItemResponse(
        val status: String,
        val totalResults: Int,
        val articles: List<ArticleItem>
)

data class ArticleItem(
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val content: String
)