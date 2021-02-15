package Profile

import com.google.gson.annotations.SerializedName

data class Profile(
	val id: Int,
	val username: String,
	val profile: DataProfile?,
	@SerializedName("articles:") val articles: List<Articles>
)
{
	data class DataProfile(
		val full_name: String,
		val birthday: String,
		val phones: List<String>
	)

	data class Articles(
		val id: Int,
		val title: String,
		val published_at: String
	)
}
