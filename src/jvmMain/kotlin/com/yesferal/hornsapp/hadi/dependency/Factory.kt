package com.yesferal.hornsapp.hadi.dependency

import com.yesferal.hornsapp.hadi.parameter.Parameters

/**
 * Factory is the basic class that extend of Dependency.
 *
 * In Hadi, a Factory class represent a dependency that
 * will be created any time that is require.
 */
class Factory<T>(
    tag: String = "",
    value: (Parameters) -> T
): Dependency<T>(tag, value) {
    override fun resolve(params: Parameters): T {
        return value(params)
    }
}
