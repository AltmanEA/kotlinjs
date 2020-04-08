import component.app
import data.*
import react.dom.render
import react.router.dom.hashRouter
import redux.*
import wrapper.reduxLogger
import kotlin.browser.document

val store: Store<State, RAction, WrapperAction> = createStore(
    ::rootReducer,
    initialState(),
    compose(
        rEnhancer(),
//        applyMiddleware(
//            reduxLogger.logger as Middleware<State, Action, Action, Action, Action>
//        ),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

val rootDiv =
    document.getElementById("root")

fun render() = render(rootDiv) {
    hashRouter {
        app(store)
    }
}

fun main() {
    render()
    store.subscribe {
        render()
    }
}

