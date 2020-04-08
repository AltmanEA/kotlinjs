package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*

interface AppProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

val AppProps.lessons
    get() = this.store.state.lessons
val AppProps.students
    get() = this.store.state.students

interface RouteNumberResult : RProps {
    var number: String
}
fun RouteResultProps<RouteNumberResult>.num()=
    this.match.params.number.toIntOrNull() ?: -1

fun fApp() =
    functionalComponent<AppProps> { props ->

        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(
                        props.lessons,
                        "Lessons",
                        "/lessons",
                        RBuilder::lesson,
                        { props.store.dispatch(AddLesson(Lesson("new lesson"))) },
                        { props.store.dispatch(RemoveLesson(it)) }
                    )
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(
                        props.students,
                        "Students",
                        "/students",
                        RBuilder::student,
                        { props.store.dispatch(AddStudent(Student("new", "student")))},
                        { props.store.dispatch(RemoveStudent(it))}
                    )
                }
            )
            route(
                "/lessons/:number",
                exact = true,
                render = renderLesson(props)
            )
            route(
                "/students/:number",
                exact = true,
                render = renderStudent(props)
            )
            route(
                "/lessons/:number/edit",
                render = renderLessonEdit(props)
            )
            route(
                "/students/:number/edit",
                render = renderStudentEdit(props)
            )
        }
    }

fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.num()
        val lesson = props.lessons[num]
        if (lesson != null)
            anyFull(
                RBuilder::student,
                num to lesson,
                props.store.state.students,
                props.store.state.presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.num()
        val student = props.store.state.students[num]
        if (student != null)
            anyFull(
                RBuilder::lesson,
                num to student,
                props.store.state.lessons,
                props.store.state.presentsStudent(num),
                props.onClickStudent(num)
            )
        else
            p { +"No such student" }
    }


fun RBuilder.renderLessonEdit(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.num()
        val lesson = props.lessons[num]
        if (lesson != null) {
            lessonEdit(
                num to lesson
            ) { props.store.dispatch(ChangeLesson(num, it))}
        }
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudentEdit(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.num()
        val student = props.students[num]
        if (student != null) {
            studentEdit(
                num to student
            ) { props.store.dispatch(ChangeStudent(num, it))}
        }
        else
            p { +"No such lesson" }
    }

fun RBuilder.app(
    store: Store<State, RAction, WrapperAction>
) =
    child(
        withDisplayName("App", fApp())
    ) {
        attrs.store = store
    }


