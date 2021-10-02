package com.yesferal.hornsapp.hadi.container

import com.yesferal.hornsapp.hadi.dependency.Factory
import com.yesferal.hornsapp.hadi.dependency.Singleton
import com.yesferal.hornsapp.hadi.exception.DependencyNotFoundException
import com.yesferal.hornsapp.hadi.exception.DependencyRegisteredTwiceException
import com.yesferal.hornsapp.hadi.exception.ParameterNotFoundException
import com.yesferal.hornsapp.hadi.parameter.Parameters
import org.junit.Assert.*
import org.junit.Test

class HadiTest {
    class Note(
        val title: String,
        val description: String
    )

    private val title = "Title"
    private val description = "Description"

    @Test
    fun resolve_any_dependency_ReturnTrue() {
        val noteExpected = Note(title, description)

        val hadi = Hadi()
        hadi register Singleton {
            noteExpected
        }

        assertSame(noteExpected, hadi.resolve<Note>())
    }

    @Test
    fun resolve_any_dependency_with_tag_ReturnTrue() {
        val tag = "tagExample"
        val noteExpected = Note(title, description)

        val hadi = Hadi()
        hadi register Singleton(tag = tag) {
            noteExpected
        }

        assertSame(noteExpected, hadi.resolve<Note>(tag = tag))
    }

    @Test
    fun resolve_singleton_dependency_ReturnTrue() {
        val hadi = Hadi()
        hadi register Singleton {
            Note(title, description)
        }

        assertSame(hadi.resolve<Note>(), hadi.resolve<Note>())
    }

    @Test
    fun resolve_factory_dependency_ReturnTrue() {
        val hadi = Hadi()
        hadi register Factory {
            Note(title, description)
        }

        assertNotSame(hadi.resolve<Note>(), hadi.resolve<Note>())
        assertSame(hadi.resolve<Note>().title, hadi.resolve<Note>().title)
        assertSame(hadi.resolve<Note>().description, hadi.resolve<Note>().description)
    }

    @Test
    fun register_two_dependency_with_tag_Return_true() {
        val hadi = Hadi()
        val tag = "tagExample"

        hadi register Singleton {
            Note(title, description)
        }

        hadi register Singleton(tag = tag) {
            Note(title, description)
        }

        assertNotSame(hadi.resolve<Note>(), hadi.resolve<Note>(tag = tag))
        assertSame(hadi.resolve<Note>(), hadi.resolve<Note>())
        assertSame(hadi.resolve<Note>(tag = tag), hadi.resolve<Note>(tag = tag))
    }

    @Test
    fun register_two_dependency_without_tag_Throw_dependency_registered_twice_exception() {
        val hadi = Hadi()
        hadi register Factory {
            Note(title, description)
        }

        assertThrows(DependencyRegisteredTwiceException::class.java) {
            hadi register Factory {
                Note(title, description)
            }
        }
    }

    @Test
    fun resolve_dependency_Throw_dependency_not_found_exception() {
        val hadi = Hadi()

        assertThrows(DependencyNotFoundException::class.java) {
            hadi.resolve<Note>()
        }
    }

    @Test
    fun resolve_singleton_dependency_with_parameters_Return_true() {
        val hadi = Hadi()
        hadi register Singleton { (titleParam: String, descriptionParam: String) ->
            Note(title = titleParam, description = descriptionParam)
        }

        assertSame(hadi.resolve<Note>(params = Parameters(title, description)), hadi.resolve<Note>(params = Parameters(title, description)))
        assertSame(title, hadi.resolve<Note>(params = Parameters(title, description)).title)
        assertSame(description, hadi.resolve<Note>(params = Parameters(title, description)).description)
    }

    @Test
    fun resolve_factory_dependency_with_parameters_Return_true() {
        val hadi = Hadi()
        hadi register Factory { (titleParam: String, descriptionParam: String) ->
            Note(title = titleParam, description = descriptionParam)
        }

        assertSame(title, hadi.resolve<Note>(params = Parameters(title, description)).title)
        assertSame(description, hadi.resolve<Note>(params = Parameters(title, description)).description)
    }

    @Test
    fun resolve_factory_dependency_with_parameters_Throw_parameter_not_found_exception() {
        val hadi = Hadi()
        hadi register Factory { (titleParam: String, descriptionParam: String) ->
            Note(title = titleParam, description = descriptionParam)
        }

        assertThrows(ParameterNotFoundException::class.java) {
            hadi.resolve<Note>(params = Parameters(title))
        }
    }
}
