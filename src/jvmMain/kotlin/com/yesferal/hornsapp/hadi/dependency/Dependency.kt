package com.yesferal.hornsapp.hadi.dependency

import com.yesferal.hornsapp.hadi.parameter.Parameters

/**
 * Dependency is the class that we necessary use in any Container implementation.
 *
 * You could extend this class with any class,
 * and do this new dependency work it as you need to.
 */
abstract class Dependency<T>(
    val tag: String,
    val value: (Parameters) -> T
) {
    abstract fun resolve(params: Parameters): T
}
