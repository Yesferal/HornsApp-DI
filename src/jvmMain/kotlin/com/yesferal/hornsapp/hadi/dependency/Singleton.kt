package com.yesferal.hornsapp.hadi.dependency

import com.yesferal.hornsapp.hadi.parameter.Parameters

/**
 * Singleton is the basic class that extend of Dependency.
 *
 * In Hadi, a Singleton class represent a dependency that
 * will be created only one time, just the first time that is require.
 */
class Singleton<T>(
    tag: String = "",
    value: (Parameters) -> T
): Dependency<T>(tag, value) {
    private lateinit var params: Parameters
    private val instance by lazy { value(params) }
    override fun resolve(params: Parameters): T {
        this.params = params
        return instance
    }
}
