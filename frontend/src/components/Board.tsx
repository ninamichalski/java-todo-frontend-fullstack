import {ToDoItem} from "./ToDoItem";
import "./Board.css";

type BoardProps = {toDo:ToDoItem}

export default function Board(props:BoardProps) {
    return(
        <div className={"board"}>
            <h3>{props.toDo.description}</h3>
        </div>

    )
}

//Dinge aufnehmen, die man nicht sehen kann, wie Status, ID??