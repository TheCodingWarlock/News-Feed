/*
 *   Copyright (C) 2018 Eton Otieno Oboch
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.edoubletech.newsfeed.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edoubletech.newsfeed.data.networking.Service
import com.edoubletech.newsfeed.guardian.GuardianMain
import com.edoubletech.newsfeed.guardian.mapToNews
import com.edoubletech.newsfeed.ui.NewsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * This class handles all the data loading logic needed for the app and does it with the use of
 * coroutines.
 */
class Repository(private val service: Service) {

    private val newsLiveData = MutableLiveData<NewsState>()

    private lateinit var call: Response<GuardianMain>

    val newsData: LiveData<NewsState>
        get() = newsLiveData

    suspend fun loadData(sectionName: String) {
        call = service.getNewsAsync(section = sectionName)
        // Perform the actual network call on the IO Dispatcher
        withContext(Dispatchers.IO) {
            newsLiveData.postValue(NewsState.Loading)
            val response = call
            if (response.isSuccessful) {
                response.body()?.mapToNews()?.let {
                    newsLiveData.postValue(NewsState.Success(it))
                }
            } else {
                val errorBody = response.errorBody()
                newsLiveData.postValue(NewsState.Error(errorBody?.string()))
            }
        }
    }

}