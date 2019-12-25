/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.android.synthetic.main.activity_scan_qr.*
import kotlinx.android.synthetic.main.custom_qr_scanner.*

class CustomScannerActivity : AppCompatActivity(com.share.greencloud.R.layout.activity_scan_qr), DecoratedBarcodeView.TorchListener {

    private val barcodeView: DecoratedBarcodeView by lazy { db_qr }
    private val manager: CaptureManager by lazy { CaptureManager(this, barcodeView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doFullScreen()
        manager.initializeFromIntent(intent, savedInstanceState)
        manager.decode()

        iv_close.setOnClickListener { close() }
    }

    override fun onResume() {
        super.onResume()
        manager.onResume()
    }

    override fun onPause() {
        super.onPause()
        manager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        manager.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        close()
    }

    override fun onTorchOn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTorchOff() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun doFullScreen() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    fun flash_control(view: View) {
        if (view.isSelected) {
            view.isSelected = false
            barcodeView.setTorchOff()
        } else {
            view.isSelected = true
            barcodeView.setTorchOn()
        }
    }

    private fun close() {
        val intent = Intent(this, QRScanActivity::class.java)
        intent.putExtra("finish","close")
        startActivity(intent)
        finish()
    }
}
