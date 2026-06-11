package com.lihan.lazypizza

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import com.google.firebase.FirebaseApp
import com.lihan.lazypizza.core.di.coreModule
import com.lihan.lazypizza.menu.di.menuModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LazyPizzaApplication: Application(), SingletonImageLoader.Factory{
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin{
            androidContext(this@LazyPizzaApplication)
            androidLogger(level = Level.DEBUG)
            modules(
                coreModule,
                menuModule
            )
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader
            .Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    // 根據手機當前可用記憶體，動態分配 25% 作為圖片快取
                    .maxSizePercent(this, percent = 0.25)
                    // 開啟弱引用（Weak References）
                    // 當強引用被 GC 回收後，若圖片還在被使用，能多一層緩衝避免重複解碼
                    .strongReferencesEnabled(true)
                    .weakReferencesEnabled(true)
                    .build()
            }
            // 2. 磁碟快取設定 (Disk Cache)
            .diskCache {
                DiskCache.Builder()
                    // 指定快取資料夾路徑（建議存放在 app 的 cache 目錄下，系統空間不足時會自動清理）
                    .directory(this.cacheDir.resolve("image_cache"))
                    // 設定最大磁碟佔用空間，這裡設定 50 MB (通常 50MB ~ 100MB 最合適)
                    .maxSizePercent(0.02) // 或者直接指定大小：.maxSizeBytes(50 * 1024 * 1024)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .crossfade(true) // 漸層淡入動畫，體驗更好
            .build()
    }

}