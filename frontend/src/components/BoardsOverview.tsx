import {ToDoItem} from "./ToDoItem";
import React, {useState} from "react";
import Board from "./Board";
import "./BoardsOverview.css";

type  OverviewProps = {
    boards:ToDoItem[],
    deleteTodo:(id:string) => void,
    updateTodo: (id:ToDoItem) => void
    }

export default function BoardsOverview(props:OverviewProps) {

    const [filterText, setFilterText] = useState("");

    const result = props.boards.filter((toDo) => toDo.description.toLowerCase().includes(filterText.toLowerCase()));

    return <div>

                <div>
                    <h3>Please type in the ToDo that your looking for:</h3>
                    <input onChange = {(event) => {
                        setFilterText(event.target.value);
                    }}/>
                </div>

                <div className={"overviews"}>
                    {result.map((toDo) =>
                        <div className={"overview"}>
                           <Board toDo={toDo} deleteTodo={props.deleteTodo} updateTodo={props.updateTodo}/>
                       </div>)}
                </div>

           </div>


}