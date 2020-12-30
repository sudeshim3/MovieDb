package com.example.openmoviedbswiggy.datamodel

import com.google.gson.annotations.SerializedName

data class MovieDetailDataModel(
    @SerializedName("imdbID")
    val imbdId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Released")
    val releasedDate: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val posterImage: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("BoxOffice")
    val boxOfficeRevenue: String
)

/*
Example response
{
    "Title": "Castle Rock",
    "Year": "2018–2019",
    "Rated": "TV-MA",
    "Released": "25 Jul 2018",
    "Runtime": "60 min",
    "Genre": "Drama, Fantasy, Horror, Mystery, Sci-Fi, Thriller",
    "Director": "N/A",
    "Writer": "Sam Shaw, Dustin Thomason",
    "Actors": "Bill Skarsgård, André Holland, Lizzy Caplan, Melanie Lynskey",
    "Plot": "Based on the stories of Stephen King, the series intertwines characters and themes from the fictional town of Castle Rock.",
    "Language": "English",
    "Country": "USA",
    "Awards": "Nominated for 1 Primetime Emmy. Another 1 win & 17 nominations.",
    "Poster": "https://m.media-amazon.com/images/M/MV5BYWQ0ZmE0ZjEtZDZjOC00ODQ4LTk1MGQtNTk3ZDdlY2RiODg1XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg",
    "Ratings": [
        {
            "Source": "Internet Movie Database",
            "Value": "7.6/10"
        }
    ],
    "Metascore": "N/A",
    "imdbRating": "7.6",
    "imdbVotes": "37,539",
    "imdbID": "tt6548228",
    "Type": "series",
    "totalSeasons": "2",
    "Response": "True"
}*/
