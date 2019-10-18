package com.vsa.filmoteca.data.domain.mapper

import com.vsa.filmoteca.data.domain.Movie
import org.jsoup.Jsoup
import java.util.*

/**
 * Created by seldon on 26/03/15.
 */
object MovieHtmlMapper {

    private const val CLASS_EVENT = "contenttype-evento"
    private const val CLASS_DATE = "description"

    fun transformMovie(html: String?): List<Movie> {
        val moviesList = ArrayList<Movie>()

        if (html == null || html.isEmpty())
            return moviesList

        val document = Jsoup.parse(html)
        val events = document.getElementsByClass(CLASS_EVENT)
        val dates = document.getElementsByClass(CLASS_DATE)

        var movie: Movie

        for (x in events.indices) {
            val event = events[x]
            movie = Movie()
            val link = event.getElementsByClass("url").first()
            val title = link.text()
            if (title.indexOf("(") > 0) {
                movie.title = title.substring(0, title.indexOf("(")).trim { it <= ' ' }
                movie.subtitle = title.substring(title.indexOf("("))
            } else {
                movie.title = title.trim { it <= ' ' }
                movie.subtitle = ""
            }

            movie.url = link.attr("href")
            val date = "- " + dates[x].text()
            movie.date = date

            moviesList.add(movie)
        }

        return moviesList
    }

}
