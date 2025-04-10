package com.gklyphon.gameinsight.activities

import com.gklyphon.gameinsight.adapters.AchievementsResponse
import com.gklyphon.gameinsight.adapters.DlcItem
import com.gklyphon.gameinsight.adapters.DlcResponse
import com.gklyphon.gameinsight.models.GameDetail
import com.gklyphon.gameinsight.models.GameResponse
import com.gklyphon.gameinsight.models.Achievement
import com.gklyphon.gameinsight.models.EsrbRating
import com.gklyphon.gameinsight.models.Game
import com.gklyphon.gameinsight.services.IRetrofitService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class RetrofitClientTest {


    @Mock
    private lateinit var retrofitService: IRetrofitService

    @Mock
    private lateinit var callGames: Call<GameResponse>

    @Mock
    private lateinit var callGameDetails: Call<GameDetail>

    @Mock
    private lateinit var callAchievements: Call<AchievementsResponse>

    @Mock
    private lateinit var callDlcResponse: Call<DlcResponse>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetGames() {
        val games = listOf(
            Game(
                1, "game-1", "Game 1", "2023-01-01", false, "", 4.5f, 5, 100, "10", 50, 85, 10, 2023, "",
                EsrbRating(1, "slug", "Mature"),
                emptyList(), emptyList()
            ),
            Game(
                2, "game-2", "Game 2", "2023-02-01", true, "", 4.0f, 4, 80, "20", 60, 90, 20, 2023,
                "", EsrbRating(2, "slug", "Teen"),
                emptyList(), emptyList()
            )
        )
        val gameResponse = GameResponse(count = 2, next = null, previous = null, results = games)
        `when`(retrofitService.getGames("fakeApiKey", 1)).thenReturn(callGames)
        `when`(callGames.execute()).thenReturn(Response.success(gameResponse))

        val response = retrofitService.getGames("fakeApiKey", 1).execute()

        assert(response.isSuccessful)
        verify(retrofitService).getGames("fakeApiKey", 1)
    }

    @Test
    fun testGetGameDetails() {
        val gameDetail = GameDetail(
            id = 1,
            slug = "game-1",
            name = "Game 1",
            nameOriginal = "Game 1 Original",
            description = "Detailed description of Game 1",
            metacritic = 85,
            released = "2023-01-01",
            tba = false,
            updated = "2023-01-01",
            backgroundImage = "https://example.com/image.jpg",
            website = "https://example.com",
            rating = 4.5f,
            ratingTop = 5,
            ratingsCount = 100,
            suggestionsCount = 10,
            metacriticUrl = "https://metacritic.com/game-1",
            esrbRating = EsrbRating(1, "rating-slug", "ESRB Rating"),
            platforms = emptyList(),
            genres = emptyList(),
            developers = emptyList()
        )
        `when`(retrofitService.getGameDetails(123, "fakeApiKey")).thenReturn(callGameDetails)
        `when`(callGameDetails.execute()).thenReturn(Response.success(gameDetail))

        val response = retrofitService.getGameDetails(123, "fakeApiKey").execute()

        assert(response.isSuccessful)
        assert(response.body()?.id == 1)
        verify(retrofitService).getGameDetails(123, "fakeApiKey")
    }

    @Test
    fun testGetGameAchievements() {
        val achievements = listOf(
            Achievement(1, "Achievement 1", "Description of Achievement 1", "https://example.com/achievement1.jpg", "25%"),
            Achievement(2, "Achievement 2", "Description of Achievement 2", "https://example.com/achievement2.jpg", "50%")
        )
        val achievementsResponse = AchievementsResponse(
            count = achievements.size,
            next = null,
            previous = null,
            results = achievements
        )

        `when`(retrofitService.getGameAchievements(123, "fakeApiKey")).thenReturn(callAchievements)
        `when`(callAchievements.execute()).thenReturn(Response.success(achievementsResponse))

        val response = retrofitService.getGameAchievements(123, "fakeApiKey").execute()

        assert(response.isSuccessful)
        val body = response.body()
        assert(body != null)
        assert(body?.results?.size == 2)
        assert(body?.count == 2)
        verify(retrofitService).getGameAchievements(123, "fakeApiKey")
    }

    @Test
    fun testGetGameAdditions() {
        val dlcs = listOf(
            DlcItem(1, "dlc-1", "DLC 1", "2021-12-01", "https://example.com/dlc1.jpg", 4.5f, 85, "$9.99"),
            DlcItem(2, "dlc-2", "DLC 2", "2022-05-15", "https://example.com/dlc2.jpg", 4.7f, 90, "$14.99")
        )
        val dlcResponse = DlcResponse(
            count = dlcs.size,
            next = null,
            previous = null,
            results = dlcs
        )

        `when`(retrofitService.getGameAdditions(123, "fakeApiKey")).thenReturn(callDlcResponse)
        `when`(callDlcResponse.execute()).thenReturn(Response.success(dlcResponse))

        val response = retrofitService.getGameAdditions(123, "fakeApiKey").execute()

        assert(response.isSuccessful)
        val body = response.body()
        assert(body != null)
        assert(body?.results?.size == 2)
        assert(body?.count == 2)
        verify(retrofitService).getGameAdditions(123, "fakeApiKey")
    }

}