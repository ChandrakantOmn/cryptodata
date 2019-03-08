package com.antonicastejon.cryptodata.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Antoni Castejón on 04/01/2018.
 */
interface CoinMarketCapApi {
/*
    @GET("v1/ticker/")
    fun userList(@Query("start" )start:Int, @Query("limit") limit:Int) : Single<List<Crypto>>
*/
//        private const val BASE_URL = "https://api.github.com/"
    @GET("users")
    fun getCryptoList(@Query("since" )start:Int, @Query("per_page") limit:Int) : Single<List<Crypto>>

    @GET("users")
    fun getCryptoList1(@Query("since" )start:Int, @Query("per_page") limit:Int) : Single<List<User>>


    @GET("users")
    fun getUsers(@Query("since") page: Int, @Query("per_page") pagesize: Int): Call<List<User>>

}


data class Crypto(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("rank") val rank: Int,
        @SerializedName("price_usd") val priceUsd: String,
        @SerializedName("price_btc") val priceBtc: String,
        @SerializedName("24h_volume_usd") val twentyFourHvolumeUsd: String,
        @SerializedName("market_cap_usd") val marketCapUsd: String,
        @SerializedName("available_supply") val availableSupply: String,
        @SerializedName("total_supply") val totalSupply: String,
        @SerializedName("percent_change_1h") val percentChange1h: String,
        @SerializedName("percent_change_24h") val percentChange24h: String,
        @SerializedName("percent_change_7d") val percentChange7d: String,
        @SerializedName("last_updated") val lastUpdated: String
)



