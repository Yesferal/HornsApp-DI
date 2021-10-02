package com.yesferal.hornsapp.hadi.container

import com.yesferal.hornsapp.hadi.dependency.Dependency
import com.yesferal.hornsapp.hadi.exception.DependencyNotFoundException
import com.yesferal.hornsapp.hadi.exception.DependencyRegisteredTwiceException
import com.yesferal.hornsapp.hadi.parameter.Parameters

/**
 * Hadi is the basic implementation of Container.
 *
 * In hadi, you can register any class that implement Dependency.
 * In that moment you can register Singleton and Factory dependencies
 * in any class that implement the Container interface.
 */
class Hadi: Container() {
    private val dependencies: HashMap<String, Dependency<*>> = hashMapOf()

    override fun <T> _register(className: String, dependency: Dependency<T>) {
        if(dependencies.containsKey(className)) {
            throw DependencyRegisteredTwiceException(className)
        }

        dependencies[className] = dependency
    }

    override fun _resolve(className: String, params: Parameters): Any {
        return dependencies[className]?.resolve(params)
            ?: throw DependencyNotFoundException(className)
    }
}
