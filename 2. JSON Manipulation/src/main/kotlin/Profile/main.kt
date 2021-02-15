package Profile

import ProfileList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun main() {
    do {
        println(
                "1. Find users who doesn't have any phone numbers. \n" +
                        "2. Find users who have articles. \n" +
                        "3. Find users who have \"annis\" on their name. \n" +
                        "4. Find users who have articles on year 2020. \n" +
                        "5. Find users who are born on 1986. \n" +
                        "6. Find articles that contain \"tips\" on the title. \n" +
                        "7. Find articles published before August 2019.\n"
        )

        print("Masukan nomor pilihan pencarian (0 to stop) : ")
        val input = readLine()?.toInt()
        val gson = Gson()

        val listType = object : TypeToken<List<Profile>>() {}.type
        val json = ProfileList().jsonProfile
        val dataProfile: List<Profile> = gson.fromJson(json, listType)

        var nameUser: String
        var nameArticles: String
        var have = 0
        for (user in dataProfile.indices) {
            when (input) {
                1 -> {
                    val phone = dataProfile[user].profile?.phones?.size
                    if (phone == 0) {
                        nameUser = dataProfile[user].username
                        println("$nameUser don't have any phone Number")
                    }
                }

                2 -> {
                    val articles = dataProfile[user].articles.size
                    if (articles == 0) {
                        nameUser = dataProfile[user].username
                        println("$nameUser don't have Articles")
                    }
                }

                3 -> {
                    val fullname = dataProfile[user].profile?.full_name
                    val splitName = fullname?.split(" ")
                    if (splitName != null) {
                        for (word in 0 until splitName.size) {
                            val splitWord = splitName[word].split("")
                            var name = ""

                            for (char in 1 until splitWord.size) {
                                name += splitWord[char]
                                if (name.toLowerCase() == "annis") {
                                    println("$fullname have a word annis")
                                }
                            }
                        }
                    }
                }

                4 -> {
                    val articles = dataProfile[user].articles.size
                    if (articles != 0) {
                        val publish = dataProfile[user].articles
                        for (count in publish.indices) {
                            val splitPublish = publish[count].published_at.split("")
                            var yearPublish = ""
                            for (number in 1..4) {
                                yearPublish += splitPublish[number]
                            }
                            if (yearPublish.toInt() == 2020) {
                                have++
                                nameUser = dataProfile[user].username
                                println("$nameUser have Articles at 2020")
                            }
                        }
                        if (have == 0 && user == dataProfile.size - 1) println("Anyone don't have articles published at 2020")
                    }
                }

                5 -> {
                    val born = dataProfile[user].profile?.birthday
                    val bornSplit = born?.split("")
                    var bornYear = ""
                    for (count in 1..4) {
                        bornYear += bornSplit?.get(count)
                        if (bornYear.toInt() == 1986) {
                            have++
                            nameUser = dataProfile[user].username
                            println("$nameUser born at 1986")
                        }
                    }
                    if (have == 0 && user == dataProfile.size - 1) println("Anyone not born at 1986")
                }

                6 -> {
                    val articles = dataProfile[user].articles
                    val totalArticles = articles.size
                    if (totalArticles != 0) {
                        for (count in articles.indices) {
                            nameArticles = articles[count].title
                            val splitName = nameArticles.split(" ")
                            for (word in 0..splitName.size - 1) {
                                val splitWord = splitName[word].split("")
                                var wordTips = ""
                                for (char in 1..splitWord.size - 1) {
                                    wordTips += splitWord[char]
                                }
                                if (wordTips.toLowerCase() == "tips") {
                                    have++
                                    nameUser = dataProfile[user].username
                                    println("$nameUser have articles with contains \"tips\" with title $nameArticles ")
                                    continue
                                }
                            }

                        }
                        if (have == 0 && user == dataProfile.size - 1) println("Anyone don't have articles with contain tips in the title")
                    }
                }

                7 -> {
                    val articles = dataProfile[user].articles
                    val countArticles = articles.size
                    if (countArticles != 0) {
                        for (article in 0..countArticles - 1) {
                            var month = ""
                            var year = ""
                            val publish = articles[article].published_at
                            val splitPublish = publish.split("-")
                            for (i in 0..1) {
                                if (i == 0) year += splitPublish[i]
                                if (i == 1) month += splitPublish[i]
                            }
                            if (year.toInt() < 2020 && month.toInt() < 8) {
                                have++
                                nameArticles = articles[article].title
                                nameUser = dataProfile[user].username
                                println("$nameUser have articles before August 2019 with title $nameArticles")
                            }
                        }
                    }
                    if (have == 0 && user == dataProfile.size - 1) println("Anyone don't have articles before August 2019")
                }
            }
        }
        println("=========================================\n")
    } while (input != 0)
}
