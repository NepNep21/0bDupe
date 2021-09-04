package me.nepnep.zerodupe

import com.lambda.client.plugin.api.Plugin

@Suppress("UNUSED")
object DupePlugin : Plugin() {
    override fun onLoad() {
        commands.add(DupeCommand)
    }
}