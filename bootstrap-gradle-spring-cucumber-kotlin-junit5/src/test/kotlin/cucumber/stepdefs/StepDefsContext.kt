package cucumber

import kotlin.reflect.KClass

enum class StepDefsContext {
    CONTEXT;

    private val givenObjects = ThreadLocal.withInitial<MutableMap<String, Any>> { HashMap() }
    private val thenObjects = ThreadLocal.withInitial<MutableMap<String, Any>> { HashMap() }

    fun <T : Any> givenObject(clazz: KClass<T>): Any? {
        return givenObjects.get()[clazz.toString()]
    }

    fun <T : Any> givenObject(obj: Any) {
        givenObjects.get()[obj.javaClass.toString()] = obj
    }

    fun <T : Any> result(KClass: Class<T>): Any? {
        return thenObjects.get()[KClass.name]
    }

    fun <T : Any> result(obj: T) {
        thenObjects.get()[obj.javaClass.toString()] = obj
    }

    fun throwable(): Throwable {
        return thenObjects.get()[Throwable::class.simpleName] as Throwable
    }

    fun throwable(throwable: Throwable) {
        thenObjects.get()[throwable.javaClass.toString()] = throwable
    }

    fun reset() {
        givenObjects.get()
                .clear()
        thenObjects.get()
                .clear()
    }
}
