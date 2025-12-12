package com.appis.android.domain.engine

import com.appis.android.utils.LogUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentLogger @Inject constructor() {
    fun log(agent: String, message: String) {
        LogUtils.agent(agent, message)
        // In future: push to UI via Flow
    }
}
