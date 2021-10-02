package com.yesferal.hornsapp.hadi.exception

class DependencyRegisteredTwiceException(
    className: String
) : Exception("The Hadi Container already contains an instance of [${className}]")

class DependencyNotFoundException(
    className: String
) : Exception("The Hadi Container could not resolve instance of [${className}]")

class ParameterNotFoundException(
    index: Int,
    className: String
) : Exception(" Parameter #${index} [${className}] not found")
