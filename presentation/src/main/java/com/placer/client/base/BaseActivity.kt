package com.placer.client.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    abstract val viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()
        viewModel.showSnackBar.observe(this, {
            Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
        })
    }
}