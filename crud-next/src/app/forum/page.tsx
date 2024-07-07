'use client';
import Link from "next/link";
import { useState, useEffect } from "react";
import Forum from "./[forum_id]";

export default function ForumList() {
  const [forumList, setForumList] = useState([]);

  async function getForumList(){
    const response = await fetch("http://localhost:8080/api/forum", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    });

    if(response.ok){
        const data = await response.json();
        console.log(data);
        setForumList(data);
    }
  }

  useEffect(() => {
    getForumList();
  }, []);

  return (
    <div>
      <h1> 글 목록 </h1>
      <ul>
        {forumList.map((forum) => (
          <li key={forum.id}>
            <Link href={`/forum/${forum.id}`}>
              {forum.title}
            </Link>
          </li>
        ))}
      </ul>  
      <a href="forum/new">
        <button>
          글쓰기
        </button>
      </a>
    </div>
  );
}
