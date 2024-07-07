'use client';
import { useState, useEffect } from "react";

export default function Forum({ forum_id }) {
  const [forum, setForum] = useState([]);

  async function getForum(){
    console.log(forum_id);
    const response = await fetch(`http://localhost:8080/api/forum/${forum_id}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    });

    if(response.ok){
        const data = await response.json();
        console.log(data);
        setForum(data);
    }
  }

  useEffect(() => {
    num = getServerSideProps(forum_id);
    getForum();
  }, []);

  return (
    <div>
      <h1>{forum.title}</h1>
      <p>{forum.content}</p>
      <a href="forum/edit">
        <button>
          글 수정
        </button>
      </a>
    </div>
  );
}

export async function getServerSideProps(context) {
    const { forum_id } = context.params;
  
    return {
      props: { forum_id }, // will be passed to the page component as props
    };
  }