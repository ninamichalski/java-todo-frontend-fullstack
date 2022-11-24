import React, {ChangeEvent, useState} from "react";

type CreateTodoProps = {
    addTodo : (description : string) => void
}

export default function CreateTodo(props : CreateTodoProps) {

    const [description, setDescription] = useState("");

    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => {
        setDescription(event.target.value)
    }

    return(
        <div className="addToDo">
            <input onChange={onDescriptionChange} value={description}/>
            <button onClick={() => props.addTodo(description)}>Add To Do </button>
        </div>


    )
}