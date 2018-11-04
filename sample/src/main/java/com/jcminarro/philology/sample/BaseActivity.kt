package com.jcminarro.philology.sample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jcminarro.philology.Philology
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * @author by mugworld
 * @date 2018/11/2
 * @Email 371481855@qq.com
 */
abstract class BaseActivity: AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val language = PreferenceUtil.getString("language", "")
        val wrap = ViewPumpContextWrapper.wrap(Philology.wrap(App.attachBaseContext(newBase, language)))
        super.attachBaseContext(wrap)
    }
}