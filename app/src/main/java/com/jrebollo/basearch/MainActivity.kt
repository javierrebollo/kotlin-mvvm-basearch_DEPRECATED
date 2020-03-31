package com.jrebollo.basearch

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.jrebollo.basearch.databinding.ActivityMainBinding
import com.jrebollo.basearch.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
