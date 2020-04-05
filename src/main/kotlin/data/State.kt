package data

typealias LessonState = Map<Int, Lesson>

typealias StudentState = Map<Int, Student>

typealias Presents = Array<Array<Boolean>>

class State(
    val lessons: LessonState,
    val students: StudentState,
    val presents: Presents
)

fun <T> Map<Int, T>.newId() =
    this.maxBy { it.key }?.key ?: 0 + 1

fun transform(source: Array<Array<Boolean>>) =
    Array(source[0].size) { row ->
        Array(source.size) { col ->
            source[col][row]
        }
    }

fun initialState() =
    State(
        lessonsList().mapIndexed { index, lesson ->
            index to lesson
        }.toMap(),
        studentList().mapIndexed { index, student ->
            index to student
        }.toMap(),
        Array(lessonsList().size) { Array(studentList().size) { false } }
    )
