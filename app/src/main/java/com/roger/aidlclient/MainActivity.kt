package com.roger.aidlclient

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.databinding.DataBindingUtil
import com.roger.REMOTE_SERVICE_NAME
import com.roger.REMOTE_SERVICE_PKG
import com.roger.aidlclient.databinding.ActivityMainBinding
import com.roger.aidlservice.MyAidl
import com.roger.aidlservice.data.Person


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var mAIDL: MyAidl? = null
    private var connection: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mAIDL = MyAidl.Stub.asInterface(service)
            connection = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mAIDL = null
            connection = false
        }
    }

    private fun initView() {
        Intent().apply {
            setPackage(REMOTE_SERVICE_PKG)
            action = REMOTE_SERVICE_NAME
            val isStart = bindService(this, serviceConnection, Service.BIND_AUTO_CREATE)
            println("-------->$isStart")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        mAIDL = null
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnAdd -> {
                if (connection) {
                    mAIDL?.addPerson(Person("小明", 18))
                    mAIDL?.addPerson(Person("小红", 20))
                    mAIDL?.addPerson(Person("小蓝", 22))
                }
            }
            R.id.btnRead -> {
                if (connection) {
                    val personList = mAIDL?.personList
                    mBinding.mText.text = personList.toString()
                }
            }
        }
    }
}