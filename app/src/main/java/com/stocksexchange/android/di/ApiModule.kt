package com.stocksexchange.android.di

import com.google.gson.GsonBuilder
import com.stocksexchange.android.BuildConfig
import com.stocksexchange.android.Constants
import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.services.StexService
import com.stocksexchange.android.api.services.StocksExchangeService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val STOCKS_EXCHANGE_OK_HTTP_CLIENT = "stocks_exchange_ok_http_client"
const val STEX_OK_HTTP_CLIENT = "stex_ok_http_client"

private const val STOCKS_EXCHANGE_RETROFIT = "stocks_exchange_retrofit"
private const val STEX_RETROFIT = "stex_retrofit"

val apiModule = applicationContext {

    bean(STOCKS_EXCHANGE_OK_HTTP_CLIENT) {
        OkHttpClient.Builder().apply {
            connectTimeout(Constants.STOCKS_EXCHANGE_OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.STOCKS_EXCHANGE_OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            certificatePinner(CertificatePinner.Builder()
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_FIRST)
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_SECOND)
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_THIRD)
                .build()
            )

            if(BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }
        }.build()
    }

    bean(STEX_OK_HTTP_CLIENT) {
        OkHttpClient.Builder().apply {
            connectTimeout(Constants.STOCKS_EXCHANGE_OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.STOCKS_EXCHANGE_OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            certificatePinner(CertificatePinner.Builder()
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_FIRST)
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_SECOND)
                .add(Constants.STEX_HOSTNAME, Constants.CERTIFICATE_PUBLIC_KEY_HASH_THIRD)
                .build()
            )

            if(BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }
        }.build()
    }

    bean(STOCKS_EXCHANGE_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(Constants.STEX_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get(STOCKS_EXCHANGE_OK_HTTP_CLIENT))
            .build()
    }

    bean(STEX_RETROFIT) {
        Retrofit.Builder()
            .baseUrl(Constants.STEX_NEW_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get(STEX_OK_HTTP_CLIENT))
            .build()
    }

    bean { get<Retrofit>(STOCKS_EXCHANGE_RETROFIT).create(StocksExchangeService::class.java) }
    bean { get<Retrofit>(STEX_RETROFIT).create(StexService::class.java) }

    bean { StocksExchangeApi }
    bean { StexApi }

    bean {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    bean { GsonBuilder().create() }

}