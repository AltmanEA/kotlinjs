package container

import react.*
import react.redux.rConnect
import component.*
import data.*
import hoc.withDisplayName


val appContainer =
    rConnect<State, RProps, AppProps>(
        {state, _ ->
            lessons = state.lessons
            students = state.students
        }
    ) (
        withDisplayName(
            "MyApp",
            fApp()
        )
            .unsafeCast<RClass<AppProps>>()
    )