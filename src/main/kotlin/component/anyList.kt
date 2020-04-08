package component

import hoc.withDisplayName
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.navLink


interface AnyListProps<O> : RProps {
    var objs: Map<Int, O>
    var add: (Event) -> Unit
    var remove: (Int) -> Unit
}

fun <O> fAnyList(
    name: String,
    path: String,
    rObj: RBuilder.(O, String, (Event) -> Unit) -> ReactElement
) =
    functionalComponent<AnyListProps<O>> { props ->
        h2 { +name }
        span("fakeLink") {
            +"Add"
            attrs.onClickFunction = props.add
        }
        table {
            props.objs.map { obj ->
                tr {
                    td {
                        navLink("$path/${obj.key}") {
                            rObj(obj.value, "normal", {})
                        }
                    }
                    td {
                        navLink("$path/${obj.key}/edit") {
                            +" Edit "
                        }
                    }
                    td {
                        span("fakeLink") {
                            +" Delete"
                            attrs.onClickFunction = {
                                props.remove(obj.key)
                            }
                        }
                    }
                }
            }
        }
    }

fun <O> RBuilder.anyList(
    objs: Map<Int, O>,
    name: String,
    path: String,
    rObj: RBuilder.(O, String, (Event) -> Unit) -> ReactElement,
    add: (Event) -> Unit,
    remove: (Int) -> Unit
) = child(
    withDisplayName(name, fAnyList<O>(name, path, rObj))
) {
    attrs.objs = objs
    attrs.add = add
    attrs.remove = remove
}

