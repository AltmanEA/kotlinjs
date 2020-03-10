import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

import data.Student

interface RStudentsProps : RProps {
    var student: Student
}

interface RStudentState : RState {
    var present: Boolean
}

class RStudent : RComponent<RStudentsProps, RStudentState>() {
    init {
        state.apply {
            present = false
        }
    }
    override fun RBuilder.render() {
        div(
            if (state.present) "present" else "absent"
        ) {
            +"${props.student.firstname} ${props.student.surname}"
            attrs.onClickFunction = {
                setState{
                    present = !present
                }
            }
        }
    }
}

fun RBuilder.rstudent(student: Student) =
    child(RStudent::class) {
        attrs.student = student
    }