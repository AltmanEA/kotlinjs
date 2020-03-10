import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps : RProps {
    var students: Array<Student>
}

interface RStudentListState : RState {
    var present: Array<Boolean>
}

class RStudentList : RComponent<RStudentListProps, RStudentListState>() {

    override fun componentWillMount() {
        state.apply {
            present = Array(props.students.size){false}
        }
    }

    override fun RBuilder.render() {
        ol {
            props.students.mapIndexed { index, student ->
                li {
                    rstudent(student, state.present[index], onClick(index))
                }
            }
        }
    }

    fun onClick(index: Int): (Event) -> Unit = {
        setState {
            present[index] = !present[index]
        }
    }
}

fun RBuilder.studentList(students: Array<Student>) =
    child(RStudentList::class) {
        attrs.students = students
    }