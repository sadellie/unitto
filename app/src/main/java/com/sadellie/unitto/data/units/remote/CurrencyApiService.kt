package com.sadellie.unitto.data.units.remote

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"

private val moshi = Moshi.Builder()
    .add(CurrencyAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CurrencyApiService {
    /**
     * Gets paired currencies for the given currency
     *
     * @param baseCurrency Left side unit
     * @return Call response with date and currencies
     */
    @GET("{baseCurrency}.json")
    suspend fun getCurrencyPairs(
        @Path("baseCurrency") baseCurrency: String
    ) : CurrencyUnitResponse
}

object CurrencyApi {
    val retrofitService: CurrencyApiService by lazy { retrofit.create(CurrencyApiService::class.java) }
}
