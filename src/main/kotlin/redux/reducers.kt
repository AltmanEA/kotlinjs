package redux

import data.*

fun presents(state: Presents, action: RAction) =
    when (action) {
        is ChangePresent ->
            state.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.lessonID)
                    lesson.mapIndexed { indexStudent, student ->
                        if (indexStudent == action.studentID)
                            !student
                        else student
                    }.toTypedArray()
                else
                    lesson
            }.toTypedArray()
        is AddLesson ->
            state + arrayOf(Array(state.size) { false })
        is AddStudent -> {
            val trans = transform(state)
            transform(trans + arrayOf(Array(trans.size) { false }))
        }
        is RemoveLesson ->
            state
                .filterIndexed { index, _ -> index != action.id }
                .toTypedArray()
        is RemoveStudent ->
            transform(
                transform(state)
                    .filterIndexed { index, _ -> index != action.id }
                    .toTypedArray()
            )
        else -> state
    }

fun lesson(state: LessonState, action: RAction) =
    when (action) {
        is AddLesson -> state + (state.newId() to action.lesson)
        is RemoveLesson -> state.minus(action.id)
        is ChangeLesson ->
            state.toMutableMap()
                .apply {
                    remove(action.id)
                    put(action.id, action.newLesson)
                }
        else -> state
    }

fun student(state: StudentState, action: RAction) =
    when (action) {
        is AddStudent -> state + (state.newId() to action.student)
        is RemoveStudent -> state.minus(action.id)
        is ChangeStudent ->
            state.toMutableMap()
                .apply {
                    remove(action.id)
                    put(action.id, action.newStudent)
                }
        else -> state
    }

fun rootReducer(state: State, action: RAction) =
    State(
        lesson(state.lessons, action),
        student(state.students, action),
        presents(state.presents, action)
    )