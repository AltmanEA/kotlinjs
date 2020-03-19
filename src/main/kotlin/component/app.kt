package component

import data.*
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.navLink
import react.router.dom.route
import react.router.dom.switch

interface AppProps : RProps {
    var lessons: Array<Lesson>
    var students: Array<Student>
}

interface AppState : RState {
    var presents: Array<Array<Boolean>>
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.presents = Array(props.lessons.size) {
            Array(props.students.size) { false }
        }
    }

    override fun RBuilder.render() {
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
                render = {
                    lessonListFull(
                        props.lessons,
                        props.students,
                        state.presents,
                        onClickLessonFull
                    )
                })
            route("/students",
                render = {
                    studentListFull(
                        props.lessons,
                        props.students,
                        transform(state.presents),
                        onClickStudentFull
                    )
                })
        }
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size) { row ->
            Array(source.size) { col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    val onClickLessonFull =
        { indexLesson: Int ->
            { indexStudent: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

    val onClickStudentFull =
        { indexStudent: Int ->
            { indexLesson: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

}

fun RBuilder.app(
    lessons: Array<Lesson>,
    students: Array<Student>
) = child(App::class) {
    attrs.lessons = lessons
    attrs.students = students
}