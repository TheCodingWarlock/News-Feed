/*
 *  Copyright (C) 2018 Eton Otieno Oboch
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.edoubletech.newsfeed

import android.app.Application
import com.edoubletech.newsfeed.data.networking.appModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * Created by EtonOtieno on 3/13/2018
 */

class NewsFeed : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        JodaTimeAndroid.init(this)
        
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}