import component.app
import data.*
import react.dom.render
import react.router.dom.hashRouter
import redux.*
import wrapper.reduxLogger
import kotlin.browser.document

val store = createStore(
    ::changeReducer,
    State(Array(lessonsList.size) { Array(studentList.size) { false } }),
    compose(
        rEnhancer(),
        applyMiddleware(
            reduxLogger.logger as Middleware<State, Action, Action, Action, Action>
        )
    )
)

val rootDiv =
    document.getElementById("root")

fun render() = render(rootDiv) {
    hashRouter {
        app(lessonsList, studentList, store)
    }
}

fun main() {
    render()
    store.subscribe {
        render()
    }
}

