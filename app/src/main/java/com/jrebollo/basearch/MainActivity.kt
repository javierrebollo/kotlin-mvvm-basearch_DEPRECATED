package com.jrebollo.basearch

import androidx.databinding.DataBindingUtil
import com.jrebollo.basearch.databinding.ActivityMainBinding
import com.jrebollo.basearch.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun bindData() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
