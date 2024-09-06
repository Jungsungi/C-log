import {useEffect, useState} from "react";
import axios from "axios";
import BookCard from "./BookCard";

function BestSeller() {
    const [books, booksSet] = useState([])
    useEffect(()=> {
            axios.get("/api/item/books/bestSeller")
                .then(res => {
                    booksSet(res.data)
                }).catch(err => console.log(err))
        }
        , [])
    return(
        <div>
            <h1>베스트 셀러 목록입니다.</h1>
            <span style={{float : "left", width : "100%"}}>
                {
                    books.map((book, index) =>
                        <BookCard book = {book}/>
                    )
                }
            </span>
        </div>
    )
}

export default BestSeller