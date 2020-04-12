package container

import react.*
import redux.*
import react.redux.rConnect
import component.*
import data.*
import hoc.withDisplayName

interface EditOwnProps<O> : RProps {
    var obj: Pair<Int, O>
}

val lessonEditContainerHOC =
    rConnect<
            RAction,
            WrapperAction,
            EditOwnProps<Lesson>,
            LessonEditProps>(
        { dispatch, ownProps ->
            onClick = {
                dispatch(ChangeLesson(ownProps.obj.first, it))
            }
        }
    )

val lessonEditRClass =
    withDisplayName(
        "LessonEdit",
        fLessonEdit
    ).unsafeCast<RClass<LessonEditProps>>()


val lessonEditContainer =
    lessonEditContainerHOC(lessonEditRClass)


val studentEditContainer =
    rConnect<
            RAction,
            WrapperAction,
            EditOwnProps<Student>,
            StudentEditProps>(
        { dispatch, ownProps ->
            onClick = {
                dispatch(ChangeStudent(ownProps.obj.first, it))
            }
        }
    ) (
        withDisplayName(
            "StudentEdit",
            fStudentEdit
        ).unsafeCast<RClass<StudentEditProps>>()
    )
