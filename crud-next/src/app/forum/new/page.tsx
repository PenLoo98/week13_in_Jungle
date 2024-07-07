"use client";
import { useState } from "react";

export default function CreateForum(){
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    async function createForumAPI(e: any){
        e.preventDefault();
        const response = await fetch("http://localhost:8080/api/forum", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title,
                content,
            }),
        });

        if(response.ok){
            alert("글이 작성되었습니다.");
            setTitle("");
            setContent("");
        }
    }

    return(
        <div>
            <h1> 글쓰기 </h1>
            <form onSubmit={createForumAPI}>
                <input type="text" placeholder="제목" value={title} onChange={e => setTitle(e.target.value)}/>
                <textarea placeholder="내용" value={content} onChange={e => setContent(e.target.value)}/>
                <button onClick={e => createForumAPI(e)}>글쓰기</button>
            </form>
        </div>
    )
}