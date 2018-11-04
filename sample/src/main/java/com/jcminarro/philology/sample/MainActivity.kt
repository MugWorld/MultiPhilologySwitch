package com.jcminarro.philology.sample

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jcminarro.philology.Philology
import com.jcminarro.sample.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        mDialog?.dismiss()
        when (v?.getId()) {
            R.id.select_english -> saveLanguage("en")
            R.id.select_chinese -> saveLanguage("zh")
            R.id.follow_system -> saveLanguage("")

            else -> {
            }
        }
        //更新语言后，destroy当前页面，重新绘制
        val it = Intent(this@MainActivity, MainActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    private var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //根据上次的语言设置，重新设置语言
        setContentView(R.layout.activity_main)

        val textView = findViewById(R.id.text) as TextView
		val button = findViewById(R.id.btn) as Button
		val button2 = findViewById(R.id.btn_2) as Button
		textView.setText(application.resources.getString(R.string.hello_world))
		button.setText(resources.getString(R.string.switch_language))

		button.setOnClickListener {
            if (mDialog == null) {
                val inflater = layoutInflater
                val layout = inflater.inflate(R.layout.dialog_select_lanuage, null)
                val english = layout.findViewById(R.id.select_english) as TextView
                val chinese = layout.findViewById(R.id.select_chinese) as TextView
                val follow_system = layout.findViewById(R.id.follow_system) as TextView
                mDialog = Dialog(this@MainActivity, R.style.Custom_Dialog_Theme)
                mDialog?.setCanceledOnTouchOutside(false)
                english.setOnClickListener(this@MainActivity)
                chinese.setOnClickListener(this@MainActivity)
                follow_system.setOnClickListener(this@MainActivity)
                mDialog?.setContentView(layout)
            }
            mDialog?.show()
        }

        button2.setOnClickListener {
            val it = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(it)
        }

    }

    protected fun saveLanguage(language: String) {
        //保存设置语言的类型
        PreferenceUtil.commitString("language", language)
    }
}
