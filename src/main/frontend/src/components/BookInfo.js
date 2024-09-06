import {useParams} from "react-router-dom";
import axios, {get} from "axios";
import {useEffect, useState} from "react";

function BookInfo(props) {
    const {isbn} = useParams()

    useEffect(() => getBook, [])
    const [book, bookSet] = useState({})

    const getBook = () => {
        axios.get(`/api/item/books/${isbn}`)
            .then(res => {
                bookSet(res.data)
            }).catch(err => console.log(err))
    }

    return (
        <div>
            <span>
                <img src={book.img}  alt={book.name}/>
                <div>
                    <p>{book.name}</p>
                    <hr/>
                    <p>{book.author}</p>
                    <hr/>
                    <p>{book.description}</p>
                </div>
            </span>
        </div>
    )
}

export default BookInfo