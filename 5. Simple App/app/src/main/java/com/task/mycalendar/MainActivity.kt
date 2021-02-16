package com.task.mycalendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.task.mycalendar.databinding.ActivityMainBinding
import com.task.mycalendar.databinding.DialogAddBinding
import com.task.mycalendar.settingscreen.SettingFragment
import com.task.mycalendar.taskscreen.TaskFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bind : ActivityMainBinding
    val taskFragment = TaskFragment()
    val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)

        bind.btnAdd.setOnClickListener(this)
        bind.llTask.setOnClickListener(this)
        bind.llSetting.setOnClickListener(this)
        val home = supportFragment(taskFragment)
        if (home) colorTask()

    }
    fun colorSetting() {
        bind.tvTask.setTextColor(ContextCompat.getColor(this, R.color.grey_1))
        bind.icTask.setColorFilter(ContextCompat.getColor(this, R.color.grey_1), android.graphics.PorterDuff.Mode.SRC_IN)
        bind.tvSettings.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
        bind.icSetting.setColorFilter(ContextCompat.getColor(this, R.color.blue_primary), android.graphics.PorterDuff.Mode.SRC_IN)
    }
    fun colorTask(){
        bind.tvTask.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
        bind.icTask.setColorFilter(ContextCompat.getColor(this, R.color.blue_primary), android.graphics.PorterDuff.Mode.SRC_IN)
        bind.tvSettings.setTextColor(ContextCompat.getColor(this, R.color.grey_1))
        bind.icSetting.setColorFilter(ContextCompat.getColor(this, R.color.grey_1), android.graphics.PorterDuff.Mode.SRC_IN)
    }
    fun supportFragment(fragment: Fragment) : Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
        return true
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ll_task -> {
                supportFragment(taskFragment)
                colorTask()
            }
            R.id.ll_setting ->{
                supportFragment(settingFragment)
                colorSetting()
            }
            R.id.btn_add -> showDialog()
        }
    }

    private fun showDialog(){
        val rootView = DialogAddBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this)
            .setView(rootView.root)
            .setCancelable(true)
            .create()
        dialog.show()
        rootView.btnSave.setOnClickListener { dialog.dismiss() }

    }
}