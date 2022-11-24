import {ToDoItem} from "./ToDoItem";
import "./Board.css";

type BoardProps = {
    toDo:ToDoItem,
    deleteTodo:(id:string) => void,
    updateTodo: (id:ToDoItem) => void
}

export default function Board(props:BoardProps) {
    return(
        <div className={"board"}>
            <h3>
                ({props.toDo.status}) : {props.toDo.description}
            <button onClick={() => props.deleteTodo(props.toDo.id)} >Delete</button>
            <button onClick={() => props.updateTodo(props.toDo)} >Update</button>
            </h3>
        </div>

    )
}

//Dinge aufnehmen, die man nicht sehen kann, wie Status, ID??