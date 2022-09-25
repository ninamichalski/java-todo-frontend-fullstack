import {ToDoItem} from "./ToDoItem";
import {useState} from "react";
import Board from "./Board";
import "./BoardsOverview.css";

type  OverviewProps = {boards:ToDoItem[]}

export default function BoardsOverview(props:OverviewProps) {

    const [filterText, setFilterText] = useState("");

    const result = props.boards.filter((toDo) => toDo.description.toLowerCase().includes(filterText.toLowerCase()));

    return <div>
                <div>
                    <input onChange = {(event) =>
                        setFilterText(event.target.value)}/>
                </div>

                <div className={"overviews"}>
                    {result.map((toDo) =>
                        <div className={"overview"}>
                           <Board toDo={toDo}/>
                       </div>)}
                </div>
           </div>


}