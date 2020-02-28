import data.studentList
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.js.li
import kotlinx.html.js.option
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear

var ascending = true

fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                attributes += "id" to "header"
                +"Students"
                onClickFunction = onCLickFunction()
            }
            ol {
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        +"${it.firstname} ${it.surname}"
                    }
                }
            }
            select(options = arrayListOf("blue", "green")) {
                attributes += "id" to "selectColor"
                onClickFunction = {
                    val selectColor =
                        document.getElementById("selectColor")!!
                                as HTMLSelectElement
                    val header =
                        document.getElementById("header")!!
                    header.setAttribute("style", "color:${selectColor.value}")
                }
            }
        }
}

private fun H1.onCLickFunction(): (Event) -> Unit {
    return {
        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)
                studentList.sortBy { it.firstname }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending
            studentList.map {
                li {
                    +"${it.firstname} ${it.surname}"
                }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.select(
    classes : String? = null,
    options: List<String>,
    block : SELECT.() -> Unit = {}
) : HTMLSelectElement = select(
    classes
) {
    options.forEach {
        option {
            attributes += "value" to it
            +it
        }
    }
    block()
}