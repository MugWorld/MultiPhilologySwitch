package com.jcminarro.philology.sample

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.text.TextUtils
import android.util.Log
import com.jcminarro.philology.Philology
import com.jcminarro.philology.PhilologyInterceptor
import com.jcminarro.philology.PhilologyRepository
import com.jcminarro.philology.PhilologyRepositoryFactory
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import java.util.Locale

class App : Application() {

    override fun attachBaseContext(base: Context) {
        PreferenceUtil.init(base)
        val language = PreferenceUtil.getString("language", "")
        val wrap = ViewPumpContextWrapper.wrap(Philology.wrap(App.attachBaseContext(base, language)))
        super.attachBaseContext(wrap)
    }


    override fun onCreate() {
        super.onCreate()
        PropertiesUtil.readProperties("test.properties")
        Philology.init(DuokePhilologyRepositoryFactory)
        // Add PhilologyInterceptor to ViewPump
        // If you are already using Calligraphy you can add both interceptors, there is no problem
        ViewPump.init(ViewPump.builder().addInterceptor(PhilologyInterceptor).build())
    }



    // AppLanguageUtils.java

    companion object {
        @JvmStatic
        fun attachBaseContext(context: Context, language: String): Context {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else {
                switchLanguage(context, language)
                context
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context {
            val resources = context.resources
            var locale: Locale? = null
            if (TextUtils.isEmpty(language)){
                locale = Locale.getDefault()
            }else{
                locale = Locale(language)
            }

            val configuration = resources.configuration
            configuration.setLocale(locale)
            configuration.locales = LocaleList(locale)
            return context.createConfigurationContext(configuration)
        }

        protected fun switchLanguage(base:Context,language: String) {
            //设置应用语言类型
            val resources = base.resources
            val config = resources.configuration
            val dm = resources.displayMetrics
            if (language == "en") {
                config.locale = Locale.ENGLISH
            } else if (language == "zh") {
                config.locale = Locale.SIMPLIFIED_CHINESE
            } else {
                config.locale = Locale.getDefault()
            }
            resources.updateConfiguration(config, dm)
        }
    }






}

object DuokePhilologyRepositoryFactory : PhilologyRepositoryFactory {
    override fun getPhilologyRepository(locale: Locale): PhilologyRepository?{
        Log.e("App","getPhilologyRepository ${locale.language}")
        return when{
            Locale.ENGLISH.language  == locale.language -> EnglishPhilologyRepository
            Locale("es", "ES").language == locale.language -> SpanishPhilologyRepository
            Locale("zh", "CN").language == locale.language -> ChinesPhilogyRespository
            else -> null
        }
    }
}

//英语
object EnglishPhilologyRepository : PhilologyRepository {
    override fun getText(key: String): CharSequence? {
        val value = PropertiesUtil.getProperty(key)
        Log.e("EnglishPhilolo","getText $key $value ")
        return value
    }
}

object SpanishPhilologyRepository : PhilologyRepository {
    override fun getText(key: String): CharSequence? {
        val value = when (key) {
            "hello_world" -> "hello world1"
            else -> null
        }
        Log.e("SpanishPhil","getText $key $value ")
        return value
    }

}
//中文
object ChinesPhilogyRespository : PhilologyRepository {
    override fun getText(key: String): CharSequence? {
        val value = when (key) {
            "hello_world" -> "中文hellow_world"
            "jump" -> "中文jump"
            "switch_language" -> "中文sitch_laungage"
            else -> null
        }
        Log.e("ChinesPhilog","getText $key $value ")
        return value
    }
}

