package com.bhushan.turtlemintassignment.koin

import com.bhushan.turtlemintassignment.BuildConfig
import com.bhushan.turtlemintassignment.network.IssueDataRepository
import com.bhushan.turtlemintassignment.network.IssueDataService
import com.bhushan.turtlemintassignment.utils.ConnectivityHelper
import com.bhushan.turtlemintassignment.utils.NetworkManager
import com.bhushan.turtlemintassignment.utils.networkUtility.AppExecutors
import com.bhushan.turtlemintassignment.utils.networkUtility.LiveDataCallAdapterFactory
import com.bhushan.turtlemintassignment.viewModel.IssueDataViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Used koin to provide dependency for
 * ---------------------------------------------------------------------------------------
 * IssueDataViewModel | IssueDataRepository | AppExecutors | IssueDataService | Retrofit | NetworkManager
 * ----------------------------------------------------------------------------------------
 */

val viewModelModule = module {
    viewModel {
        IssueDataViewModel(get())
    }
}

val repositoryModule = module {
    single {
        IssueDataRepository(get(), get())
    }
}

val appExecutorsModule = module {
    single {
        AppExecutors()
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): IssueDataService {
        return retrofit.create(IssueDataService::class.java)
    }

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        return if (BuildConfig.BUILD_TYPE == "debug") {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            httpClient.build()
        } else {
            httpClient.build()
        }
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/square/okhttp/")
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single { provideRetrofit(get()) }
}

val networkManagerModule = module {
    single {
        NetworkManager()
    }
}

val connectivityHelperModule = module {
    single {
        ConnectivityHelper(get())
    }
}